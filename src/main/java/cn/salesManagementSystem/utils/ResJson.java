package cn.salesManagementSystem.utils;

/**
 * @author 闫铁鹰
 * @program salesManagementSystem
 * @description 存储返回值的常量字典
 * @date 2021-02-24 13:36
 **/

public class ResJson {
    /**
     * 参数为空的返回值
     */
    public static final String IS_NULL_RETURN_JSON = "{\"code\":400,\"msg\":\"缺少参数\"}";
    /**
     * 成功的返回值
     */
    public static final String SUCCESS_RETURN_JSON = "{\"code\":200,\"msg\":\"操作成功\"}";
    /**
     * 失败的返回值
     */
    public static final String FAIL_RETURN_JSON = "{\"code\":500,\"msg\":\"操作失败\"}";
    /**
     * 没有登录的返回值
     */
    public static final String NO_LOGIN_RETURN_JSON = "{\"code\":401,\"msg\":\"没有登录或无权访问\"}";
    /**
     * 数据已存在的返回值
     */
    public static final String DATA_ALREADY_EXISTS = "{\"code\":20001,\"msg\":\"数据已存在\"}";
}
