
var MESSAGE_TYPE_INVITATION = "#invitation#";
var MESSAGE_TYPE_TALK = "#talk#";
var MESSAGE_TYPE_CREATE_LINK = "#link#";
var MESSAGE_TYPE_READY = "#ready#";
var MESSAGE_TYPE_INGAME = "#ingame#";
var MESSAGE_TYPE_SYSTEM = "#system#";
var MESSAGE_TYPE_STATE = "#state#";
var num = 0;
/**
 * 建立socket连接
 * @param username 当前连接用户名
 */
function connect(username) {
    var socket = new SockJS('endpointWisely'); //1
    stompClient = Stomp.over(socket);//2
    stompClient.connect({}, function(frame) {//3
        //console.log('开始进行连接Connected: ' + frame);
        $("#response").text("");
        sendName(username+"进入了聊天室","系统","all",MESSAGE_TYPE_TALK);
        sendName(username+"进入了游戏:游戏过程中请不要刷新页面，刷新页面将导致游戏无法正常进行!\n","系统","all",MESSAGE_TYPE_SYSTEM);
        /**
         * 聊天连接
         * 游戏邀请连接
         * 创建游戏
         */
        stompClient.subscribe('/topic/getResponse', function(respnose){ //4
            var entity = JSON.parse(respnose.body);
            if(username == entity.toAddress || "all" == entity.toAddress) {
                if (entity.sendType == MESSAGE_TYPE_INVITATION) {
                    showInvitation(entity.fromAddress, entity.message,entity.toAddress);
                } else if(entity.sendType == MESSAGE_TYPE_TALK) {
                    showResponse(entity.fromAddress, entity.message);
                } else if(entity.sendType == MESSAGE_TYPE_CREATE_LINK){
                    createLink(entity.fromAddress,entity.toAddress);
                } else if(entity.sendType == MESSAGE_TYPE_READY){
                    $("#state").html(entity.message);
                } else if(entity.sendType == MESSAGE_TYPE_INGAME){
                    showInGame(entity.fromAddress,entity.message);
                } else if(entity.sendType == MESSAGE_TYPE_SYSTEM){
                    showSystem(entity.fromAddress,entity.message);
                    var systemMessage = $("#systemMessage");
                    systemMessage.animate({scrollTop: $(document).height()}, 50);
                } else if(entity.sendType == MESSAGE_TYPE_STATE){
                    var systemMessage = $("#systemMessage");
                    systemMessage.text(systemMessage.text() + "  " + entity.message + "\n");
                    systemMessage.animate({scrollTop: $(document).height()}, 50);
                }
            }
        });
        /**
         * 初始化游戏
         */
        stompClient.subscribe('/topic/initGetResponse', function(respnose){ //4
            var game = JSON.parse(respnose.body);
            var id = game.gameId;
            if(username == game.user_one.username) {
                toGame(game.user_one.username,game.user_two.username,id);
            }else if(username == game.user_two.username){
                toGame(game.user_two.username,game.user_one.username,id);
            }
        });
        /**
         * 开始游戏
         */
        stompClient.subscribe('/topic/startGetResponse', function(response){
            var user = JSON.parse(response.body);
            console.log(JSON.stringify(user));
            console.log(user.data.user1_num);
            console.log(user.data.user2_num);
            //alert(user.data.data.username);
            var json = {};
            json.obj1 = "0";
            json.obj2 = "0";
            json.obj3 = "0";
            json.opp_obj1 = "0";
            json.opp_obj2 = "0";
            json.opp_obj3 = "0";
            json.shengyu = "3";
            json.shengyu_opp = "3";
            json.btn = "";
            if(user.data.data.username == username){
                beforeAttack(username,user.data.beAttack,user.data.gameId);
                sendName("请进行攻击",username,user.data.data.username,MESSAGE_TYPE_READY);
                changeTable(json);
            }else{
                sendName("请等待.....",user.data.data.username,username,MESSAGE_TYPE_READY);
                changeTable(json);
            }
        });
        /**
         * 攻击反馈
         */
        stompClient.subscribe('/topic/attackGetResponse', function(response){
            var ret = JSON.parse(response.body);
            var attackReturn = ret.data.attackReturn;
            var hutrStatus = attackReturn.hurtStatus;
            var gameInfo = ret.data.gameInfo;
            var color = "black";
            var systemMessage = $("#systemMessage");
            var result = "";
            if(attackReturn.beAttack == username){
                if(hutrStatus == 0){
                    color = "blue";
                    result = "未被击中"
                }else if(hutrStatus == 1){
                    result = "被击中"
                }else if(hutrStatus == 2){
                    result = "被击毁"
                }
                sendName("状态："+result,attackReturn.attacker,attackReturn.beAttack,MESSAGE_TYPE_STATE);
                beAttack1(attackReturn.hurtCoordinate,attackReturn.beAttack,attackReturn.attacker,gameInfo.gameId,color);
                sendName("请等待.....",username,attackReturn.attacker,MESSAGE_TYPE_READY);
            }else if(attackReturn.attacker == username){
                if(hutrStatus == 0){
                    result = "未击中对方";
                    color = "blue";
                }else if(hutrStatus == 1){
                    result = "击中对方";
                }else if(hutrStatus == 2){
                    result = "击毁对方";
                }
                sendName("状态："+result,attackReturn.beAttack,attackReturn.attacker,MESSAGE_TYPE_STATE);
                retAttack1(attackReturn.hurtCoordinate,color);
                sendName("请进行攻击",username,attackReturn.beAttack,MESSAGE_TYPE_READY);
            }
            var json = {};
            json.btn = "";
            var objuid = "";
            if(gameInfo.user_one.username == username){
                objuid = gameInfo.user_one.objuid;
                json = tableDataInit(gameInfo.user_one.status,gameInfo.user_two.status);
            }else{
                objuid = gameInfo.user_two.objuid;
                json = tableDataInit(gameInfo.user_two.status,gameInfo.user_one.status);
            }
            changeTable(json);
            var result = victoryorDefeat(gameInfo);
            if(result != null){
                if(result.winner == username){
                    alert("游戏结束：你赢了！");
                    gameOver(gameInfo.gameId,gameInfo.user_one.objuid,gameInfo.user_two.objuid);
                    gotoMainMenu(objuid+"&username="+encodeURIComponent(encodeURIComponent(username)));
                }else if(result.loser == username){
                    alert("游戏结束：你输了！");
                    gotoMainMenu(objuid+"&username="+encodeURIComponent(encodeURIComponent(username)));
                }
                $("#left_table_opponent td").unbind();
            }
        });
    });
    return stompClient;
}

function gameOver(gameId,user1,user2){
    $.ajax({
        type:"post",//type可以为post也可以为get
        url:getRootPath() + "gameController/gameover",
        async:false,
        data:{
            "gameId":gameId,
            "objuid1":user1,
            "objuid2":user2
        },//这行不能省略，如果没有数据向后台提交也要写成data:{}的形式
        dataType:"json",//这里要注意如果后台返回的数据不是json格式，那么就会进入到error:function(data){}中
        success:function(data){
            console.log("游戏结束初始化完成");
        },
        error:function(data){
            console.error("游戏结束初始化失败");
        }
    });
}

function tableDataInit(my,opp){
    var json = {};
    json.obj1 = pdTableDat(my.aircraftEntityList[0]);
    json.obj2 = pdTableDat(my.aircraftEntityList[1]);
    json.obj3 = pdTableDat(my.aircraftEntityList[2]);
    json.opp_obj1 = pdTableDat(opp.aircraftEntityList[0]);
    json.opp_obj2 = pdTableDat(opp.aircraftEntityList[1]);
    json.opp_obj3 = pdTableDat(opp.aircraftEntityList[2]);
    json.shengyu = my.aricraftNum;
    json.shengyu_opp = opp.aricraftNum;
    return json;
}
function pdTableDat(aircraft){
    if(aircraft != undefined){
        if(aircraft.hurtNum != "10"){
            return aircraft.hurtNum;
        }else{
            return "击毁";
        }
    }else{
        return "*"
    }
}

/**
 * 断开连接
 */
function disconnect() {
    if (stompClient != null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

/**
 * 发送信息
 * @param message 信息内容
 * @param fromAddress 信息来源
 * @param toAddress 信息前往地址
 * @param sendType 信息类型
 */
function sendName(message,fromAddress,toAddress,sendType) {
    var json = {};
    json.fromAddress = fromAddress;
    json.toAddress = toAddress;
    json.message = message;
    json.sendType = sendType;
    stompClient.send("/welcome", {}, JSON.stringify(json));//5
}

function gameInit(user1,user2){
    var json = {};
    var user_one = {};
    var user_two = {};
    user_one.username = user1;
    user_two.username = user2;
    json.user_one = user_one;
    json.user_two = user_two;
    stompClient.send("/init",{},JSON.stringify(json));
}

function gameStart(gameId){
    stompClient.send("/start",{},gameId);
}
function sendAttack(coo,gameId,attack,beAttack){
    var json = {};
    json.coordinate = coo;
    json.attacker = attack;
    json.beAttacker = beAttack;
    json.gameId = gameId;
    stompClient.send("/attack",{},JSON.stringify(json));
}

/**
 * 在文本域显示聊天内容
 * @param name 发送人
 * @param message 发送来的信息
 */
function showResponse(name,message) {
    var response = $("#response");
    response.text(response.text() + name + " " +getTime() + "\n" + message+"\n");
}

function showInGame(name,message){
    var gameTalk = $("#gameTalk");
    gameTalk.text(gameTalk.text() + name + " " + getTime() + "\n" + message + "\n");
}

function showSystem(name,message){
    var systemMessage = $("#systemMessage");
    systemMessage.text(systemMessage.text() + "系统 " + getTime() + "\n" + message);
}

/**
 * 显示邀请信息
 * @param fromname 邀请人
 * @param message  XX
 * @param name 被邀请人
 */
function showInvitation(fromAddress,message,toAddress) {
    if(confirm("收到来自 "+fromAddress+" 的游戏邀请,是否接受?")){
        sendName("对方接受了您的邀请",fromAddress,toAddress,MESSAGE_TYPE_TALK);
        sendCreateMessage(fromAddress,toAddress);
    }else{
        sendName("对方拒绝了您的邀请",fromAddress,toAddress,MESSAGE_TYPE_TALK);
    }
}

/**
 * 向双方发送建立连接的邀请
 * @param user1 玩家1
 * @param user2 玩家2
 */
function sendCreateMessage(user1,user2){
    sendName("",user1,user2,MESSAGE_TYPE_CREATE_LINK);
    // sendName("",user2,user1,MESSAGE_TYPE_CREATE_LINK);
}

function pz(s) {
    return s < 10 ? '0' + s: s;
}

function getTime(){

    var myDate = new Date();
//获取当前年
    var year=myDate.getFullYear();
//获取当前月
    var month=myDate.getMonth()+1;
//获取当前日
    var date=myDate.getDate();
    var h=myDate.getHours();       //获取当前小时数(0-23)
    var m=myDate.getMinutes();     //获取当前分钟数(0-59)
    var s=myDate.getSeconds();

    //var now=year+'-'+p(month)+"-"+p(date)+" "+p(h)+':'+p(m)+":"+p(s);
    var time=pz(h)+':'+pz(m)+":"+pz(s);
    return time;
}


function getRootPath() {
    var pathName = window.location.pathname.substring(1);
    var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
    return window.location.protocol + '//' + window.location.host + '/' + webName + '/';
}