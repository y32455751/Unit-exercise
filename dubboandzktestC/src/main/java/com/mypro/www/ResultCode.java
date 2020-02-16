package com.mypro.www;

public enum ResultCode {

    SUCCESS(200,"操作成功"),
    LOGIN_SUCCESS(200,"登录成功"),
    PWD_EDIT_SUCCESS(200,"密码修改成功"),
    WECHAT_BIND_SUCCESS(200,"微信绑定成功"),
    WECHAT_UNBIND_SUCCESS(200,"微信解绑成功"),
    FOLLOW_SUCCESS(200,"关注成功"),
    CANCEL_FOLLOW_SUCCESS(200,"取消关注成功"),
    FABULOUS_SUCCESS(200,"点赞成功"),
    CANCEL_FABULOUS_SUCCESS(200,"取消点赞成功"),
    CANCEL_COLLECTION_SUCCESS(200,"取消收藏成功"),
    COLLECTION_SUCCESS(200,"收藏成功"),
    DELETE_VIEWRECORD_SUCCESS(200,"删除播放记录成功"),
    ADD_CART_SUCCESS(200,"课程已经添加到购物车,请在购物车中查看"),
    ADD_CART_ALREADY_EXISTS(200,"该商品已经存在于购物车中"),
    DELETE_CART_SUCCESS(200,"删除购物车信息成功"),
    ADD_ADDRESS_SUCCESS(200,"添加收货地址成功"),
    UPDATE_ADDRESS_SUCCESS(200,"修改收获地址成功"),
    DELETE_ADDRESS_SUCCESS(200,"删除收货地址成功"),
    SET_DEFAULT_ADDRESS_SUCCESS(200,"设置默认收货地址成功"),
    DELETE_ORDER_SUCCESS(200,"订单删除成功"),



    ERROR(999,"操作失败:未知错误,请稍后重试"),
    TIME_OUT(909,"超时"),
    NET_WORK_ERR(400,"网络通信失败，请联系管理员"),
    LOGIN_ERR(10900,"登录失败，请检查网络通信后重试"),
    TOKEN_ERR(10901,"Token验证失败"),
    ALREADY_EXISTS(10902,"用户名已存在"),
    VERIFICATION_CODE_ERR(10903,"验证码验证失败"),
    USN_PWD_ERR(10904,"用户名或密码错误"),
    USER_NOT_FOUND(10905,"未找到该用户"),
    ADD_CART_ERROR(10906,"添加到购物车失败"),
    DISTRICTS_NOT_FOUND(10907,"地址定位失败，请手动选择地址")
    ;

    private Integer code;
    private String msg;


    ResultCode(){
    }

    ResultCode(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer code(){
        return this.code;
    }

    public String msg(){
        return this.msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
