/**
 * 检查表单
 * @returns {number}
 *      1 -》 检测通过
 *      -1 -》 文本框为空
 *      -2 -》 登录名有特殊字符
 */
function checkForm(){
    var ret = 1;
    $(".login-form").find('input[type="text"], input[type="password"]').each(function(){
        if( $(this).val() == "" ) {
            $(this).addClass('input-error');
            ret = -1;
        }
        else {
            $(this).removeClass('input-error');
            ret = 1;
        }
    });
    if($("#form-loginname").val().length != 0) {
        if ($("#form-loginname").val().match(/^[\u4E00-\u9FA5a-zA-Z0-9]{1,}$/)==null) {
            ret = -2;
            $("#form-loginname").addClass('input-error');
        }
    }
    return ret;
}

/**
 * 显示加载动画
 */
function showLoading(){
    showMaskDiv();
    $("#maskDiv").append("<div style=\"height:100px;width:100px;margin:0 auto;\" id=\"loader\"></div>");
    $("#loader").shCircleLoader();
}

/**
 * 隐藏加载动画
 */
function hideLoading(){
    $("#maskDiv").remove();
}

/**
 * 隐藏遮罩层
 */
function hideMaskDiv(){
    $("#maskDiv").remove();
}

/**
 * 显示遮罩层
 */
function showMaskDiv(){
    $("body").append("<div id=\"maskDiv\" style=\"height: 100%;width: 100%;padding-top:20%;position: fixed;top: 0;background: black;filter:alpha(opacity=50);-moz-opacity:0.5;-khtml-opacity: 0.5;opacity: 0.5;\">\n" +
        "</div>");
}

/**
 * 前往主菜单，玩家列表
 * @param objuid 当前登陆人主键
 */
function gotoMainMenu(objuid){
    window.location.href = "menu.html?objuid="+objuid;
}

function toGame(own,opponent,gameId){
    window.location.href = "game.html?my=" + decodeURIComponent(own) + "&opponent=" + decodeURIComponent(opponent) + "&id=" + gameId;
}

/**
 * 登录方法
 * @param loginname 账号
 * @param password 密码
 * @returns {number} 1 -》 登录成功
 *                    0 -》 账号或者密码不正确
 *                    -1 -》 报错
 *                    -2 -》 账号为空
 *                    -3 -》 密码为空
 */
function login(loginname,password){
    var ret = 1;
    if("" == loginname){
        ret = -2;
        return ret;
    }
    if("" == password){
        ret = -3;
        return ret;
    }
    $.ajax({
        type:"post",//type可以为post也可以为get
        url: getRootPath()+"User/login",
        async:false,
        data:{
            "loginname":loginname,
            "password":password
        },//这行不能省略，如果没有数据向后台提交也要写成data:{}的形式
        dataType:"json",//这里要注意如果后台返回的数据不是json格式，那么就会进入到error:function(data){}中
        success:function(data){
            if( "error" == data.status){
                ret = 0;
            }else if("success" == data.status){
                ret = data.data.objuid+"&username="+encodeURIComponent(encodeURIComponent(data.data.username));
            }
        },
        error:function(data){
            ret = -1;
        }
    });
    return ret;
}
