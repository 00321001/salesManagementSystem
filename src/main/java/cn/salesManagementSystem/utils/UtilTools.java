package cn.salesManagementSystem.utils;

import lombok.extern.log4j.Log4j;

import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * 工具类
 *
 * @author yty
 */
@Log4j
public class UtilTools {

    /**
     * 用于密码加盐的盐值
     */
    private static final String SALT = "c5bfddf4ea0f43aa8d6e1112f2536dca";

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
     * @param type    期望的用户类型：1-系统管理员；2-门店管理员；3-系统管理员+门店管理员；4-销售人员；5-系统管理员+销售人员；6-门店管理员+销售人员；7-所有登录用户
     * @return 用户已经登录返回true，否则返回false
     */
    public static boolean checkLogin(HttpSession session, int type) {
        String useridStr = (String) session.getAttribute("userId");
        String roleIdStr = (String) session.getAttribute("roleId");
        if (useridStr == null || roleIdStr == null) {
            return false;
        }
        switch (type) {
            case 1:
            case 2:
            case 4:
                return Integer.parseInt(roleIdStr) == type;
            case 3:
                return (Integer.parseInt(roleIdStr) == 1 || Integer.parseInt(roleIdStr) == 2);
            case 5:
                return (Integer.parseInt(roleIdStr) == 1 || Integer.parseInt(roleIdStr) == 4);
            case 6:
                return (Integer.parseInt(roleIdStr) == 2 || Integer.parseInt(roleIdStr) == 4);
            default:
                return false;
        }
    }

    public static String passwordEncryption(String password, String userName){
        password = password + userName + SALT;
        byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(passwordBytes);
            byte[] byteBuffer = messageDigest.digest();
            StringBuilder strHexString = new StringBuilder();
            for (byte b : byteBuffer) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    strHexString.append('0');
                }
                strHexString.append(hex);
            }
            password = strHexString.toString().toUpperCase();
        }catch (Exception e){
            log.error(e);
        }
        return password;
    }

}
