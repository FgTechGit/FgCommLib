package cn.sighttechnology.armscomponent.commonres.utils;

import com.umeng.socialize.sina.helper.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Description:
 * Data：2018/7/16-15:50
 * Author: yangjichao
 */
public class CustomBase64 {
    public static String encodeToString(String str) {
        char[] chars = {};
        String result = "";
        try {
            chars = Base64.encode(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (chars.length > 0) {
            result = String.valueOf(chars);
        }
        return result;
    }
}
