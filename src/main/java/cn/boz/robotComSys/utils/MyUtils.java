package cn.boz.robotComSys.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MyUtils {

    /**
     * 加密密码
     * @param password
     * @return
     */
    public static String encryptPassword(String password) {
        byte[] md5s = new byte[0];
        try {
            md5s = MessageDigest.getInstance("MD5").digest(password.getBytes());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return password;
        }
        //直接以16进制的形式存储
        String md5code = new BigInteger(1, md5s).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }
}
