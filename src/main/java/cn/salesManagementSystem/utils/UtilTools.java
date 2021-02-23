package cn.salesManagementSystem.utils;

import javax.servlet.http.HttpSession;

/**
 * 工具类
 *
 * @author yty
 */
public class UtilTools {
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
     * 用于判空的方法
     *
     * @param args 将参数整合为String数组传入
     * @return 有空值时返回false，没有空值返回true
     */
    public static boolean checkNull(String[] args) {
        for (String arg : args) {
            if (arg == null || arg.trim().length() <= 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 用于检查是否登录的方法
     *
     * @param session 一个HttpSession对象
     * @param type    期望的用户类型：0-管理员；1-教师；2-学生；3-所有登录用户；4-管理员+教师；5-教师+学生；6-管理员+学生
     * @return 用户已经登录返回true，否则返回false
     */
    public static boolean checkLogin(HttpSession session, int type) {
        String useridStr = (String) session.getAttribute("userid");
        String userTypeStr = (String) session.getAttribute("userType");
        if (useridStr == null || userTypeStr == null) {
            return false;
        }
        switch (type) {
            case 0:
            case 1:
            case 2:
                return Integer.parseInt(userTypeStr) == type;
            case 3:
                return true;
            case 4:
                return (Integer.parseInt(userTypeStr) == 0 || Integer.parseInt(userTypeStr) == 1);
            case 5:
                return (Integer.parseInt(userTypeStr) == 1 || Integer.parseInt(userTypeStr) == 2);
            case 6:
                return (Integer.parseInt(userTypeStr) == 0 || Integer.parseInt(userTypeStr) == 2);
            default:
                return false;
        }

    }

}
