package cn.sighttechnology.armscomponent.commonsdk.utils;

import com.umeng.socialize.sina.helper.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 对称utils
 */
public class AesUtil {

    /**
     * 加密
     *
     * @param sSrc   需加密串
     * @param aesStr 加密盐
     * @return
     * @throws Exception
     */
    public static String encrypt(String sSrc, String aesStr) throws Exception {
        SecretKeySpec skeySpec = new SecretKeySpec(aesStr.getBytes("utf-8"), "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));

        char[] chars = Base64.encode(encrypted);
        return String.valueOf(chars);
    }

    /**
     * 解密
     *
     * @param sSrc   需解密串
     * @param aesStr 加密盐
     * @return
     * @throws Exception
     */
//    public static String decrypt(String sSrc, String aesStr) throws Exception {
//        SecretKeySpec skeySpec = new SecretKeySpec(aesStr.getBytes("utf-8"), "AES");
//        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
//        cipher.init(Cipher.DECRYPT_MODE, skeySpec);
//        byte[] encrypted1 = new Base64().decode(sSrc);
//        byte[] original = cipher.doFinal(encrypted1);
//        String originalString = new String(original, "utf-8");
//
//        return originalString;
//    }

}
