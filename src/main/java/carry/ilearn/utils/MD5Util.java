package carry.ilearn.utils;

import sun.misc.BASE64Encoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Carry
 * @since 2021/5/16
 */
public class MD5Util {
    
    public static String encodeByMD5(String str) throws NoSuchAlgorithmException {
        // 确定计算方法
        MessageDigest md5Digest = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        // 加密字符串
        return base64en.encode(md5Digest.digest(str.getBytes()));
    }
    
}
