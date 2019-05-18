
var one = "3,1;1,2;2,2;3,2;4,2;5,2;3,3;2,4;3,4;4,4";
var two = "5,1;6,1;7,1;6,2;4,3;5,3;6,3;7,3;8,3;6,4";
var three = "5,4;3,5;5,5;3,6;4,6;5,6;6,6;3,7;5,7;5,8";

var DIRECTION_LEFT = 0;
var DIRECTION_UP = 1;
var DIRECTION_RIGHT = 2;
var DIRECTION_DOWN = 3;

// var lefttd = new Array();
// var uptd = new Array();
// var righttd = new Array();
// var downtd = new Array();

var p = new Array();
var color = ["red","yellow","green","blue","pink"];

function getPNum(){
    var num = 0;
    for(var i=0;i<p.length;i++){
        if(p[num]!=undefined){
            num++;
        }
    }
    return num;
}

function complete(request){
    var num = getPNum();
    if(num == 0){
        alert("您必须至少部署一架飞机");
        return false;
    }
    if(num < 3){
        if(!confirm("您的飞机还没全部部署，确定要完成部署吗？")){
            return false;
        }
    }else{
        if(!confirm("确定要部署飞机吗？部署之后不可更改！")){
            return false;
        }
    }
    var c = "";
    var head = "";
    var aId = "";
    for(var i=0;i<p.length;i++){
        if(p[i] != undefined) {
            c += p[i].coordinate.coordinate + "#";
            head += p[i].coordinate.isHead + "#";
            aId += p[i].aId + "#";
        }
    }
    com(request.id,request.my,c.substring(0,c.length-1),head.substring(0,head.length-1),request.opponent,aId);
}

function com(gameId,username,coordinate,isHead,opponent,aId){
    $.ajax({
        type:"post",//type可以为post也可以为get
        url:getRootPath() + "gameController/complete",
        async:false,
        data:{
            "gameId":gameId,
            "username":username,
            "coordinate":coordinate,
            "isHead":isHead,
            "aId": aId
        },//这行不能省略，如果没有数据向后台提交也要写成data:{}的形式
        dataType:"json",//这里要注意如果后台返回的数据不是json格式，那么就会进入到error:function(data){}中
        success:function(data){
            if( "error" == data.status){
                alert("error");
            }else if("success" == data.status){
                var user_one_state = data.data.user_one.status.gameStatus;
                var user_two_state = data.data.user_two.status.gameStatus;
                if(user_one_state == 2 && user_two_state == 2){
                    //gamestart();
                    gameStart(gameId);
                }else{
                    sendName("对方已经完成部署",username,opponent,MESSAGE_TYPE_READY);
                    $("#comBtn").html("");
                }
            }
        },
        error:function(data){
            alert(-1);
        }
    });
}

function changeTable(json){
    $("#obj1").html(json.obj1);
    $("#obj2").html(json.obj2);
    $("#obj3").html(json.obj3);
    $("#opp_obj1").html(json.opp_obj1);
    $("#opp_obj2").html(json.opp_obj2);
    $("#opp_obj3").html(json.opp_obj3);
    $("#shengyu").html(json.shengyu);
    $("#shengyu_opp").html(json.shengyu_opp);
    $("#comBtn").html(json.btn);
}

function beforeAttack(attack,beAttack,gameId){
    unLockTable("left_table_opponent",gameId,attack,beAttack);
}
function attack(row,col,this_,gameId,attack,beAttack){
    $(this_).attr("attacked", "1");
    sendAttack(row+","+col,gameId,attack,beAttack);
    sendName(attack+"对"+beAttack+"的("+row+","+col+")位置进行了攻击",attack,"all",MESSAGE_TYPE_SYSTEM);
}
function unLockTable(table,gameId,attacker,beAttack){
    $("#"+table+" td").unbind();
    $("#"+table+" td:not([attacked])").bind("mouseover",function(){
        var row = $(this).prevAll().length;
        var col = $(this).parent().prevAll().length;
        if(row != 0 && col != 0)
        $(this).css("background", "red");
    });
    $("#"+table+" td:not([attacked])").bind("mouseout",function(){
        var row = $(this).prevAll().length;
        var col = $(this).parent().prevAll().length;
        if(row != 0 && col != 0)
        $(this).css("background", "none");
    });
    $("#"+table+" td:not([attacked])").bind("click",function(){
        if($(this).attr("edge") != "1") {
            var row = $(this).prevAll().length;
            var col = $(this).parent().prevAll().length;
            if (row != 0 && col != 0)
                attack(row, col, $(this), gameId, attacker, beAttack);
            $("#" + table + " td").unbind();
        }
    });
}

function lockTable(table){

}

function startBeforeLoading(request){
    $("#username").html(request.my);
    $("#opponent").html(request.opponent);
}

function createLink(user1,user2){
    gameInit(user1,user2);
    console.log("建立连接,user1:"+user1+" user2:"+user2);
}

function cancelExist(num){
    if(p[num] != undefined){
        var td = getTd($(p[num].element),p[num].direction);
        for (var i = 0; i < td.length; i++) {
            $(td[i]).css("background", "none");
            $(td[i]).removeAttr("ischeck");
        }
        p[num] = undefined;
    }
}

function getTd(this_,direction){
    var td = new Array();
    if(direction == DIRECTION_UP){
        td = getUpTd($(this_));
    }else if(direction == DIRECTION_DOWN){
        td = getDownTd($(this_));
    }else if(direction == DIRECTION_LEFT){
        td = getLeftTd($(this_));
    }else if(direction == DIRECTION_RIGHT){
        td = getRightTd($(this_));
    }
    return td;
}

//0左 1上 2右 3下
function setObj(num,direction){
    $("#left_table td").unbind();
    cancelExist(num);
    var incolor = color[num];
    var td = new Array();
    $("#left_table td:not([ischeck])").bind("mouseover",function(){
        td = getTd($(this),direction);
        if(checkTd(td)) {
            for (var i = 0; i < td.length; i++) {
                $(td[i]).css("background", incolor);
            }
        }
    });
    $("#left_table td:not([ischeck])").bind("mouseout",function(){
        td = getTd($(this),direction);
        if(checkTd(td)) {
            for (var i = 0; i < td.length; i++) {
                $(td[i]).css("background", "none");
            }
        }
    });
    $("#left_table td").bind("click",function(){
        td = getTd($(this),direction);
        if(checkTd(td)) {
            for (var i = 0; i < td.length; i++) {
                $(td[i]).attr("ischeck", "1");
                $(td[i]).attr("aId", num);
            }
            var pl = {};
            pl.aId = num;
            pl.element = $(this);
            pl.direction = direction;
            pl.coordinate = getCoordinate($(this), direction);
            p[num] = pl;
            $("#left_table td").unbind();
        }
    });

}

function getCoordinate(this_,direction){
    var row = $(this_).prevAll().length * 1;
    var col = $(this_).parent().prevAll().length * 1;
    var coordinateJson = {};
    var c = "";
    var head = "";
    if(direction == DIRECTION_UP){
        head = getC(row,col - 1);
        c += getC(row,col - 1);
        c += getC(row-2,col);
        c += getC(row-1,col);
        c += getC(row,col);
        c += getC(row+1,col);
        c += getC(row+2,col);
        c += getC(row,col+1);
        c += getC(row-1,col+2);
        c += getC(row,col+2);
        c += getC(row+1,col+2);
    }else if(direction == DIRECTION_RIGHT){
        head = getC(row+1,col);
        c += getC(row+1,col);
        c += getC(row,col-2);
        c += getC(row,col-1);
        c += getC(row,col);
        c += getC(row,col+1);
        c += getC(row,col+2);
        c += getC(row-1,col);
        c += getC(row-2,col-1);
        c += getC(row-2,col);
        c += getC(row-2,col+1);
    }else if(direction == DIRECTION_DOWN){
        head = getC(row,col+1);
        c += getC(row,col+1);
        c += getC(row+2,col);
        c += getC(row+1,col);
        c += getC(row,col);
        c += getC(row-1,col);
        c += getC(row-2,col);
        c += getC(row,col-1);
        c += getC(row+1,col-2);
        c += getC(row,col-2);
        c += getC(row-1,col-2);
    }else if(direction == DIRECTION_LEFT){
        head = getC(row-1,col);
        c += getC(row-1,col);
        c += getC(row,col+2);
        c += getC(row,col+1);
        c += getC(row,col);
        c += getC(row,col-1);
        c += getC(row,col-2);
        c += getC(row+1,col);
        c += getC(row+2,col+1);
        c += getC(row+2,col);
        c += getC(row+2,col-1);
    }
    c = c.substring(0,c.length-1);
    coordinateJson.coordinate = c;
    coordinateJson.isHead = head.replace(/;/g,"");
    return coordinateJson;
}

function getC(row,col){
    return row +","+ col+";";
}

function getDownTd(this_){
    var downtd = new Array();
    var prevAllNum = $(this_).prevAll().length;
    //机头
    downtd[0] = $(this_).parent().next().find("td:eq("+prevAllNum+")");
    //机翼行
    downtd[1] = $(this_).next().next();
    downtd[2] = $(this_).next();
    downtd[3] = $(this_);
    downtd[4] = $(this_).prev();
    downtd[5] = $(this_).prev().prev();
    //机身
    downtd[6] = $(this_).parent().prev().find("td:eq("+prevAllNum+")");
    //机尾翼
    downtd[7] = $(this_).parent().prev().prev().find("td:eq("+prevAllNum+")").next();
    downtd[8] = $(this_).parent().prev().prev().find("td:eq("+prevAllNum+")");
    downtd[9] = $(this_).parent().prev().prev().find("td:eq("+prevAllNum+")").prev();
    return downtd;
}

function getRightTd(this_){
    var righttd = new Array();
    var prevAllNum = $(this_).prevAll().length;
    //机头
    righttd[0] = $(this_).next();
    //机翼行
    righttd[1] = $(this_).parent().prev().prev().find("td:eq("+prevAllNum+")");
    righttd[2] = $(this_).parent().prev().find("td:eq("+prevAllNum+")");
    righttd[3] = $(this_);
    righttd[4] = $(this_).parent().next().find("td:eq("+prevAllNum+")");
    righttd[5] = $(this_).parent().next().next().find("td:eq("+prevAllNum+")");
    //机身
    righttd[6] = $(this_).prev();
    //机尾翼
    righttd[7] = $(this_).parent().prev().find("td:eq("+prevAllNum+")").prev().prev();
    righttd[8] = $(this_).prev().prev();
    righttd[9] = $(this_).parent().next().find("td:eq("+prevAllNum+")").prev().prev();
    return righttd;
}

function getLeftTd(this_){
    var lefttd = new Array();
    var prevAllNum = $(this_).prevAll().length;
    //机头
    lefttd[0] = $(this_).prev();
    //机翼行
    lefttd[1] = $(this_).parent().next().next().find("td:eq("+prevAllNum+")");
    lefttd[2] = $(this_).parent().next().find("td:eq("+prevAllNum+")");
    lefttd[3] = $(this_);
    lefttd[4] = $(this_).parent().prev().find("td:eq("+prevAllNum+")");
    lefttd[5] = $(this_).parent().prev().prev().find("td:eq("+prevAllNum+")");
    //机身
    lefttd[6] = $(this_).next();
    //机尾翼
    lefttd[7] = $(this_).parent().prev().find("td:eq("+prevAllNum+")").next().next();
    lefttd[8] = $(this_).next().next();
    lefttd[9] = $(this_).parent().next().find("td:eq("+prevAllNum+")").next().next();
    return lefttd;
}

function getUpTd(this_){
    var uptd = new Array();
    var prevAllNum = $(this_).prevAll().length;
    //机头
    uptd[0] = $(this_).parent().prev().find("td:eq("+prevAllNum+")");
    //机翼行
    uptd[1] = $(this_).prev().prev();//拖拽
    uptd[2] = $(this_).prev();
    uptd[3] = $(this_);
    uptd[4] = $(this_).next();
    uptd[5] = $(this_).next().next();
    //机身
    uptd[6] = $(this_).parent().next().find("td:eq("+prevAllNum+")");
    //机尾翼
    uptd[7] = $(this_).parent().next().next().find("td:eq("+prevAllNum+")").prev();
    uptd[8] = $(this_).parent().next().next().find("td:eq("+prevAllNum+")");
    uptd[9] = $(this_).parent().next().next().find("td:eq("+prevAllNum+")").next();
    return uptd;
}

function checkTd(td){
    for(var i=0;i<td.length;i++){
        if($(td[i]).length == 0){
            return false;
        }
        if($(td[i]).attr("ischeck") == "1"){
            return false;
        }
        if($(td[i]).attr("edge") == "1"){
            return false;
        }
    }
    return true;
}



function beAttack1(coo,beAttack,attacker,gameId,color){
    for(var i=0;i<coo.length;i++){
        $("#left_table").find("tr:eq("+coo[i].split(",")[1]+") td:eq("+coo[i].split(",")[0]+")").css("background",color);
        $("#left_table").find("tr:eq("+coo[i].split(",")[1]+") td:eq("+coo[i].split(",")[0]+")").attr("attacked","1");
    }
    unLockTable("left_table_opponent",gameId,beAttack,attacker);
}
function retAttack1(coo,color){
    for(var i=0;i<coo.length;i++){
        $("#left_table_opponent").find("tr:eq("+coo[i].split(",")[1]+") td:eq("+coo[i].split(",")[0]+")").css("background",color);
        $("#left_table_opponent").find("tr:eq("+coo[i].split(",")[1]+") td:eq("+coo[i].split(",")[0]+")").attr("attacked","1");
    }
}


function victoryorDefeat(gameInfo){
    var json = {};
    var user_one = gameInfo.user_one.status.aircraftEntityList;
    var user_two = gameInfo.user_two.status.aircraftEntityList;
    var user_num_one = 0;
    var user_num_two = 0;
    for(var i=0;i<user_one.length;i++){
        if(user_one[i].aircraftStatus == "0"){
            user_num_one++;
        }
    }
    if(user_num_one == 3){
        json.winner = gameInfo.user_two.username;
        json.loser = gameInfo.user_one.username;
        return json;
    }
    for(var i=0;i<user_two.length;i++){
        if(user_two[i].aircraftStatus == "0"){
            user_num_two++;
        }
    }
    if(user_num_two == 3){
        json.loser = gameInfo.user_two.username;
        json.winner = gameInfo.user_one.username;
        return json;
    }
    return null;
}















// function testData(){
//     var feiji = [];
//     var all = {};
//     feiji.push(one);
//     feiji.push(two);
//     feiji.push(three);
//     all.data = feiji;
//     return all;
// }
//
// function loadAircraft(){
//     var color = "red,yellow,green";
//     var color_ = color.split(",");
//     var all = testData();
//     if(all != null && all.data.length > 0){
//         for(var i=0;i<all.data.length;i++){
//             var position = all.data[i].split(";");
//             var c = color_[i];
//             for(var f = 0;f<position.length;f++){
//                 var inPosition = position[f].split(",");
//                 var col = inPosition[0];
//                 var row = inPosition[1];
//                 $("#left_table tr").each(function(trNum,tr){
//                     $(this).find("td").each(function(tdNum,td){
//                         if(trNum == row && tdNum == col){
//                             $(this).css("background",c);
//                         }
//                     });
//                 });
//             }
//         }
//     }
// }