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
    public static final String IS_NULL_RETURN_JSON = "{\"code\":\"9999\",\"msg\":\"缺少参数\"}";
    /**
     * 成功的返回值
     */
    public static final String SUCCESS_RETURN_JSON = "{\"code\":\"0000\",\"msg\":\"操作成功\"}";
    /**
     * 失败的返回值
     */
    public static final String FAIL_RETURN_JSON = "{\"code\":\"9999\",\"msg\":\"操作失败\"}";
    /**
     * 没有登录的返回值
     */
    public static final String NO_LOGIN_RETURN_JSON = "{\"code\":\"7777\",\"msg\":\"没有登录\"}";
    /**
     * 数据已存在的返回值
     */
    public static final String DATA_ALREADY_EXISTS = "{\"code\":\"8888\",\"msg\":\"数据已存在\"}";
}
