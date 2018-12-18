package cn.sighttechnology.armscomponent.commonres.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.CallLog;
import android.provider.MediaStore;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.FileUtils;
import com.google.gson.Gson;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import cn.sighttechnology.armscomponent.commonres.bean.TimeInfo;
import cn.sighttechnology.armscomponent.commonsdk.core.GlobalConfiguration;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static cn.sighttechnology.armscomponent.commonsdk.utils.Utils.getStatusBarHeight;

public class SightUtils {

    private static Context appContext = GlobalConfiguration.appContext;

    //    public static void setTopPadding(Context context, AutoLinearLayout autoLinearLayout) {
//        //padding margins
//        int statusBarHeight = getStatusBarHeight(context);
//
//        autoLinearLayout.setPadding(0,statusBarHeight,0,0);
//    }
    //注意此方法会重置其他的padding
    public static void setTopPadding(Context context, ViewGroup viewGroup) {
        //padding margins
        int statusBarHeight = getStatusBarHeight(context);
        viewGroup.setPadding(0, statusBarHeight, 0, 0);
    }

    /**
     * directionType 朝向
     * houseType 房屋类型
     *
     * @param context
     * @param cmap
     * @param ckey
     * @return
     */
    public static String getSharedPreferencesMapString(Context context, String cmap, String ckey) {
        if (ckey == null || ckey.equals("")) {
            return "";
        }
        if (cmap.equals("directionType") && ckey.equals("0")) {
            return "无朝向";
        }
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = appContext.getSharedPreferences(appContext.getPackageName(), Context.MODE_PRIVATE);
//        sharedPreferences.getString();
        String mapStr = sharedPreferences.getString(cmap, "");
        HashMap hashMap = gson.fromJson(mapStr, HashMap.class);
        String[] keys = ckey.split(",");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < keys.length; i++) {
            //如果map中存在这个key值
            if (hashMap.containsKey(keys[i])) {
                stringBuffer.append(hashMap.get(keys[i]).toString());
                if (i != keys.length - 1) {
                    stringBuffer.append(",");
                }
            }
        }
        return stringBuffer.toString();
    }

    public static String getSharedPreferencesMapString(String cmap, String ckey) {
        if (ckey == null || ckey.equals("")) {
            return "";
        }
        if (cmap.equals("directionType") && ckey.equals("0")) {
            return "无朝向";
        }
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = appContext.getSharedPreferences(appContext.getPackageName(), Context.MODE_PRIVATE);
//        sharedPreferences.getString();
        String mapStr = sharedPreferences.getString(cmap, "");
        HashMap hashMap = gson.fromJson(mapStr, HashMap.class);
        String[] keys = ckey.split(",");
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < keys.length; i++) {
            //如果map中存在这个key值
            if (hashMap.containsKey(keys[i])) {
                stringBuffer.append(hashMap.get(keys[i]).toString());
                if (i != keys.length - 1) {
                    stringBuffer.append(",");
                }
            }
        }
        return stringBuffer.toString();
    }

    public static String getSharedPreferencesMapString(String cmap, String ckey,String mark) {
        if (ckey == null || ckey.equals("")) {
            return "";
        }
        if (cmap.equals("directionType") && ckey.equals("0")) {
            return "无朝向";
        }
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = appContext.getSharedPreferences(appContext.getPackageName(), Context.MODE_PRIVATE);
//        sharedPreferences.getString();
        String mapStr = sharedPreferences.getString(cmap, "");
        HashMap hashMap = gson.fromJson(mapStr, HashMap.class);
        String[] keys = ckey.split(mark);
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < keys.length; i++) {
            //如果map中存在这个key值
            if (hashMap.containsKey(keys[i])) {
                stringBuffer.append(hashMap.get(keys[i]).toString());
                if (i != keys.length - 1) {
                    stringBuffer.append(",");
                }
            }
        }
        return stringBuffer.toString();
    }

    public static String getSharedPreferencesString(Context context, String ckey) {
        SharedPreferences sharedPreferences = appContext.getSharedPreferences(appContext.getPackageName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(ckey, "");
    }

    /**
     * 获取本地存储的数据
     * 去掉不必要的 Context 参数后的方法
     */
    public static String getSharedPreferencesString(String ckey) {
        SharedPreferences sharedPreferences = appContext.getSharedPreferences(appContext.getPackageName(), Context.MODE_PRIVATE);
        return sharedPreferences.getString(ckey, "");
    }

    public static void setSharedPreferencesString(Context context, String ckey, String cValue) {
        SharedPreferences sharedPreferences = appContext
                .getSharedPreferences(appContext.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ckey, cValue);
        editor.commit();
    }

    /**
     * 将String类型数据存至本地
     * 去掉不必要的 Context 参数后的方法
     */
    public static void setSharedPreferencesString(String ckey, String cValue) {
        SharedPreferences sharedPreferences = appContext
                .getSharedPreferences(appContext.getPackageName(), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ckey, cValue);
        editor.commit();
    }

    /**
     * 根据文件路径，获取文件名
     *
     * @param filePath
     * @return
     */

    //      举例：
//    String fName =" G:\\Java_Source\\navigation_tigra_menu\\demo1\\img\\lev1_arrow.gif ";
////      方法一：
//    File tempFile =new File( fName.trim());
//    String fileName = tempFile.getName();
//        System.out.println("fileName = " + fileName);
////      方法二：
//    String fName = fName.trim();
//    String fileName = fName.substring(fName.lastIndexOf("/")+1);
//    //或者
//    String fileName = fName.substring(fName.lastIndexOf("\\")+1);
////      方法三：
//    String fName = fName.trim();
//    String temp[] = fName.split("\\\\"); /**split里面必须是正则表达式，"\\"的作用是对字符串转义*/
//    String fileName = temp[temp.length-1];
    public static String getFileName(String filePath) {
        if (filePath != null) {
            filePath = filePath.trim();
            String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
            return fileName;
        }
        return "";
    }

    /*
     * Java文件操作 获取文件的扩展名
     * */
    public static String getExtensionName(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length() - 1))) {
                return filename.substring(dot + 1);
            }
        }
        return filename;
    }


    /*
     * Java文件操作 获取不带扩展名的文件名
     * */
    public static String getFileNameNoEx(String filename) {
        if ((filename != null) && (filename.length() > 0)) {
            int dot = filename.lastIndexOf('.');
            if ((dot > -1) && (dot < (filename.length()))) {
                return filename.substring(0, dot);
            }
        }
        return filename;
    }

    public static void hideKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(((Activity) context).getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 使用此方法获取HashMap值，若值不存在，返回空串
     *
     * @param map 数据源 HashMap
     * @param key 用以取值的 key
     * @return 对应的 Value 或 空字符串
     */
    public static String getMapValue(Map<String, String> map, String key) {
        return map.get(key) == null ? "" : map.get(key);
    }

    /**
     * @return 返回系统当前年份
     */
    public static Integer getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return Integer.valueOf(sdf.format(date));
    }

    public static String getFormatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    public static Date getFormatDate(String str) {
        Date date = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getFormatDateStr(String str) {
        String strReturn = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        strReturn   =   sdf.format(new Date(
                    Long.valueOf(str)
            ));
        return strReturn;
    }
    //当天 返回 十分 例如 14:10 非当天 返回日期 如 08/23 非当年返回 年月日 2018/02/32
    public static String getSpecialFormatDateStr(String str) {
        Date    date    =   new Date(Long.valueOf(str));

        if (isSameDay(date.getTime())){
            SimpleDateFormat sdfTemp = new SimpleDateFormat("HH:mm");
            return sdfTemp.format(date);
        }else if (isSameYear(Calendar.getInstance().get(Calendar.YEAR))){
            SimpleDateFormat sdfTemp = new SimpleDateFormat("MM/dd");
            return sdfTemp.format(date);
        }else {
            SimpleDateFormat sdfTemp = new SimpleDateFormat("yyyy/MM/dd");
            return sdfTemp.format(date);
        }

    }

    public static String getFormatWeekDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE MM月dd日");
        String dateStr = sdf.format(date);
        return dateStr;
    }

    public static boolean checkString(String check) {
        if (check == null || check.length() == 0 || check.equals("")) {
            return false;
        }
        return true;
    }
    public static boolean checkInteger(Integer check) {
        if (check == null || check == 0 || check.equals("")) {
            return false;
        }
        return true;
    }

    public static boolean isStringEmpty(String check) {
        if (check == null || check.length() == 0 || check.trim().equals("")) {
            return true;
        }
        return false;
    }

    public static boolean isEmpty(Object checkObj) {
        if (checkObj == null ) {
            return true;
        }
        String check = checkObj.toString();
        if (check == null || check.length() == 0 || check.trim().equals("")) {
            return true;
        }
        return false;
    }

    public static boolean checkString(String str, int minLength) {
        if (str != null && str.length() >= minLength) {
            return true;
        }
        return false;
    }

    public static void setStatusColor(Activity activity, int color, boolean isDark) {
        if (color == Color.TRANSPARENT) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 5.0 以上全透明状态栏
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏 加下面几句可以去除透明状态栏的灰色阴影,实现纯透明
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setStatusBarColor 来设置状态栏颜色
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

            //6.0 以上可以设置状态栏的字体为黑色.使用下面注释的这行打开亮色状态栏模式,实现黑色字体,白底的需求用这句setStatusBarColor(Color.WHITE);
            //            window.getDecorView().setSystemUiVisibility(
            //                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            activity.getWindow().setStatusBarColor(color);

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        if (isDark) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                View decorView = activity.getWindow().getDecorView();
                //重点：SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                decorView.setSystemUiVisibility(option);
//                activity.getWindow().setStatusBarColor(Color.WHITE);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                View decorView = activity.getWindow().getDecorView();
                //重点：SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
//                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }

    }

    public static void setStatusTextColorDark(Activity activity, boolean isDark) {
        if (isDark) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                View decorView = activity.getWindow().getDecorView();
                //重点：SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                decorView.setSystemUiVisibility(option);
//                activity.getWindow().setStatusBarColor(Color.WHITE);
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                View decorView = activity.getWindow().getDecorView();
                //重点：SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
                decorView.setSystemUiVisibility(option);
//                activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
        }
    }

    public static String getFormatString(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        if (str.length() > 2) {
            str = str.substring(0, 2) + "...";

        }
        return str;
    }

    //获取昨天今天明天的方法
    public static String getTimestampString(Date paramDate) {
        String str = null;
        long l = paramDate.getTime();
        Calendar localCalendar = GregorianCalendar.getInstance();
        localCalendar.setTime(paramDate);
        int year = localCalendar.get(Calendar.YEAR);
        if (!isSameYear(year)) { //去年，直接返回
            String paramDate2str = new SimpleDateFormat("yyyy MM dd", Locale.CHINA).format(paramDate);
            return paramDate2str;
        }


        if (isSameDay(l)) {
            int i = localCalendar.get(Calendar.HOUR_OF_DAY);
            if (i > 17) {
                str = "晚上 HH:mm";//HH表示24小时,hh表示12小时进制，
            } else if ((i >= 0) && (i <= 6)) {
                str = "凌晨 HH:mm";
            } else if ((i > 11) && (i <= 17)) {
                str = "下午 HH:mm";
            } else {
                str = "上午 HH:mm";
            }
        } else if (isYesterday(l)) {
            str = "昨天 HH:mm";
        } else if (isBeforeYesterday(l)) {
            str = "前天 HH:mm";
        } else {
            str = "M月d日 HH:mm";
        }
        String paramDate2str = new SimpleDateFormat(str, Locale.CHINA).format(paramDate);
        return paramDate2str;
    }

    private static boolean isSameDay(long paramLong) {
        TimeInfo localTimeInfo = getTodayStartAndEndTime();
        return (paramLong > localTimeInfo.getStartTime()) && (paramLong < localTimeInfo.getEndTime());
    }

    private static boolean isYesterday(long paramLong) {
        TimeInfo localTimeInfo = getYesterdayStartAndEndTime();
        return (paramLong > localTimeInfo.getStartTime()) && (paramLong < localTimeInfo.getEndTime());
    }

    private static boolean isBeforeYesterday(long paramLong) {
        TimeInfo localTimeInfo = getBeforeYesterdayStartAndEndTime();
        return (paramLong > localTimeInfo.getStartTime()) && (paramLong < localTimeInfo.getEndTime());
    }

    public static boolean isSameYear(int year) {
        Calendar cal = Calendar.getInstance();
        int CurYear = cal.get(Calendar.YEAR);
//    Log.e("","CurYear="+CurYear);//2015
        return CurYear == year;
    }


    //  获取 今天开始结束 时间
    public static TimeInfo getTodayStartAndEndTime() {

        Calendar localCalendar1 = Calendar.getInstance();
        localCalendar1.set(Calendar.HOUR_OF_DAY, 0);
        localCalendar1.set(Calendar.MINUTE, 0);
        localCalendar1.set(Calendar.SECOND, 0);
        localCalendar1.set(Calendar.MILLISECOND, 0);
        Date localDate1 = localCalendar1.getTime();
        long l1 = localDate1.getTime();

        Calendar localCalendar2 = Calendar.getInstance();
        localCalendar2.set(Calendar.HOUR_OF_DAY, 23);
        localCalendar2.set(Calendar.MINUTE, 59);
        localCalendar2.set(Calendar.SECOND, 59);
        localCalendar2.set(Calendar.MILLISECOND, 999);
        Date localDate2 = localCalendar2.getTime();
        long l2 = localDate2.getTime();

        TimeInfo localTimeInfo = new TimeInfo();
        localTimeInfo.setStartTime(l1);
        localTimeInfo.setEndTime(l2);
        return localTimeInfo;

    }


//  获取 昨天开始结束 时间


    public static TimeInfo getYesterdayStartAndEndTime() {
        Calendar localCalendar1 = Calendar.getInstance();
        localCalendar1.add(Calendar.DAY_OF_MONTH, -1);//5
        localCalendar1.set(Calendar.HOUR_OF_DAY, 0);//11
        localCalendar1.set(Calendar.MINUTE, 0);//12
        localCalendar1.set(Calendar.SECOND, 0);//13
        localCalendar1.set(Calendar.MILLISECOND, 0);//Calendar.MILLISECOND
        Date localDate1 = localCalendar1.getTime();
        long l1 = localDate1.getTime();


        Calendar localCalendar2 = Calendar.getInstance();
        localCalendar2.add(Calendar.DAY_OF_MONTH, -1);//5
        localCalendar2.set(Calendar.HOUR_OF_DAY, 23);//11
        localCalendar2.set(Calendar.MINUTE, 59);//12
        localCalendar2.set(Calendar.SECOND, 59);//13
        localCalendar2.set(Calendar.MILLISECOND, 999);//Calendar.MILLISECOND
        Date localDate2 = localCalendar2.getTime();
        long l2 = localDate2.getTime();


        TimeInfo localTimeInfo = new TimeInfo();
        localTimeInfo.setStartTime(l1);
        localTimeInfo.setEndTime(l2);
        return localTimeInfo;
    }


//  获取 前天开始结束 时间

    public static TimeInfo getBeforeYesterdayStartAndEndTime() {
        Calendar localCalendar1 = Calendar.getInstance();
        localCalendar1.add(Calendar.DAY_OF_MONTH, -2);
        localCalendar1.set(Calendar.HOUR_OF_DAY, 0);
        localCalendar1.set(Calendar.MINUTE, 0);
        localCalendar1.set(Calendar.SECOND, 0);
        localCalendar1.set(Calendar.MILLISECOND, 0);
        Date localDate1 = localCalendar1.getTime();
        long l1 = localDate1.getTime();


        Calendar localCalendar2 = Calendar.getInstance();
        localCalendar2.add(Calendar.DAY_OF_MONTH, -2);
        localCalendar2.set(Calendar.HOUR_OF_DAY, 23);
        localCalendar2.set(Calendar.MINUTE, 59);
        localCalendar2.set(Calendar.SECOND, 59);
        localCalendar2.set(Calendar.MILLISECOND, 999);
        Date localDate2 = localCalendar2.getTime();
        long l2 = localDate2.getTime();
        TimeInfo localTimeInfo = new TimeInfo();
        localTimeInfo.setStartTime(l1);
        localTimeInfo.setEndTime(l2);
        return localTimeInfo;
    }

    //间隔 length
    public String addLineBreak(int length, String stringIn) {
        StringBuffer stringBufferOut = new StringBuffer();
        if (stringIn.length() < length) {
            return stringIn;
        } else {

            String stringRight = stringIn.substring(length, stringIn.length());

        }
        return stringBufferOut.toString();
    }


    //字符串换行
    public static String addLineBreak(String str, int length) {
        int strBeginPos = 0;  //字符串的初始位置
        String resultStr = ""; //返回的字符串
        if (str.length() <= length) {
            return str;
        }

        List<String> strVec = new ArrayList<>(); //

        while (true) {
            if (strBeginPos + length <= str.length()) {
                strVec.add(str.substring(strBeginPos, strBeginPos + length));
                strBeginPos += length;
            } else {
                strVec.add(str.substring(strBeginPos, str.length()));
                break;
            }
        }

        for (int i = 0; i < strVec.size(); ++i) {
            if (i != strVec.size() - 1) {
                resultStr = resultStr + strVec.get(i) + "\n";
            } else {
                resultStr = resultStr + strVec.get(i);
            }
        }

        return resultStr;
    }

    /**
     * 设置状态栏图标为深色和魅族特定的文字风格，Flyme4.0以上
     * 可以用来判断是否为Flyme用户
     *
     * @param window 需要设置的窗口
     * @param dark   是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
        boolean result = false;
        if (window != null) {
            try {
                WindowManager.LayoutParams lp = window.getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class
                        .getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class
                        .getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (dark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                window.setAttributes(lp);
                result = true;
            } catch (Exception e) {

            }
        }
        return result;
    }

    /**
     * 需要MIUIV6以上
     *
     * @param activity
     * @param dark     是否把状态栏字体及图标颜色设置为深色
     * @return boolean 成功执行返回true
     */
    public static boolean MIUISetStatusBarLightMode(Activity activity, boolean dark) {
        boolean result = false;
        Window window = activity.getWindow();
        if (window != null) {
            Class clazz = window.getClass();
            try {
                int darkModeFlag = 0;
                Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                if (dark) {
                    extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
                } else {
                    extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
                }
                result = true;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //开发版 7.7.13 及以后版本采用了系统API，旧方法无效但不会报错，所以两个方式都要加上
                    if (dark) {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                    } else {
                        activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    }
                }
            } catch (Exception e) {

            }
        }
        return result;
    }

    public static void printDeviceHardwareInfo(Context context) {
        Log.d("LocalDeviceInfo", "---------------Build------------");
        Log.d("LocalDeviceInfo", "Build.BOARD: " + Build.BOARD);
        Log.d("LocalDeviceInfo", "Build.BOOTLOADER: " + Build.BOOTLOADER);
        Log.d("LocalDeviceInfo", "Build.BRAND: " + Build.BRAND);//设备牌子
        Log.d("LocalDeviceInfo", "Build.DEVICE: " + Build.DEVICE);//设备名
        Log.d("LocalDeviceInfo", "Build.DISPLAY: " + Build.DISPLAY);//显示设备号
        Log.d("LocalDeviceInfo", "Build.FINGERPRINT: " + Build.FINGERPRINT);//设备指纹
        Log.d("LocalDeviceInfo", "Build.HARDWARE: " + Build.HARDWARE);
        Log.d("LocalDeviceInfo", "Build.HOST: " + Build.HOST);
        Log.d("LocalDeviceInfo", "Build.ID: " + Build.ID);//设备硬件id
        Log.d("LocalDeviceInfo", "Build.MANUFACTURER: " + Build.MANUFACTURER);//厂商
        Log.d("LocalDeviceInfo", "Build.MODEL: " + Build.MODEL);//设备型号
        Log.d("LocalDeviceInfo", "Build.PRODUCT: " + Build.PRODUCT);//产品名，和DEVICE一样
        Log.d("LocalDeviceInfo", "Build.SERIAL: " + Build.SERIAL);//设备序列号
        Log.d("LocalDeviceInfo", "Build.TAGS: " + Build.TAGS);
        Log.d("LocalDeviceInfo", "Build.TYPE: " + Build.TYPE);
        Log.d("LocalDeviceInfo", "Build.UNKNOWN: " + Build.UNKNOWN);
        Log.d("LocalDeviceInfo", "Build.USER: " + Build.USER);
        Log.d("LocalDeviceInfo", "Build.CPU_ABI: " + Build.CPU_ABI);
        Log.d("LocalDeviceInfo", "Build.CPU_ABI2: " + Build.CPU_ABI2);
        Log.d("LocalDeviceInfo", "Build.RADIO: " + Build.RADIO);
        Log.d("LocalDeviceInfo", "Build.TIME: " + Build.TIME);//出厂时间
        Log.d("LocalDeviceInfo", "Build.VERSION.CODENAME: " + Build.VERSION.CODENAME);
        Log.d("LocalDeviceInfo", "Build.VERSION.INCREMENTAL: " + Build.VERSION.INCREMENTAL);//不详，重要
        Log.d("LocalDeviceInfo", "Build.VERSION.RELEASE: " + Build.VERSION.RELEASE);//系统版本号
        Log.d("LocalDeviceInfo", "Build.VERSION.SDK: " + Build.VERSION.SDK);//api级数
        Log.d("LocalDeviceInfo", "Build.VERSION.SDK_INT: " + Build.VERSION.SDK_INT);//api级数，int型返回
    }

    //注意此方法会重置其他的padding
    public static void setFlagHorizontalLine(Activity context, int... ids) {
        if (context == null) {
            return;
        }
        for (int i = 0; i < ids.length; i++) {
            TextView textView = context.findViewById(ids[i]);
            textView.setText("--");
        }

    }

    //注意此方法会重置其他的padding
    public static boolean dismissPopWindow(PopupWindow... windows) {
        boolean isDismiss = false;
        if (windows == null) {
            return isDismiss;
        }
        for (int i = 0; i < windows.length; i++) {
            PopupWindow popupWindow = windows[i];
            if (popupWindow != null &&
                    popupWindow.isShowing()
                    ) {
                popupWindow.dismiss();
                isDismiss = true;
            }
        }
        return isDismiss;
    }


    void installApk() {

    }

    /**
     * 插入到系统相册, 并刷新系统相册
     *
     * @param imageUrl
     */
    public static void insertSystemAlbumAndRefresh(final String imageUrl, final Application application) {
        Flowable.create(new FlowableOnSubscribe<Object>() {
            @Override
            public void subscribe(FlowableEmitter<Object> e) throws Exception {
                File file = FileUtils.getFileByPath(imageUrl);
                String imageUri = MediaStore.Images.Media.insertImage(application.getContentResolver(), file.getAbsolutePath(), file.getName(), "图片: " + file.getName());
                Log.d("_stone_", "insertSystemAlbumAndRefresh-subscribe: imageUri=" + imageUri);
                syncAlbum(imageUrl, application);
            }
        }, BackpressureStrategy.BUFFER)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    /**
     * 同步刷新系统相册
     *
     * @param imageUrl
     */
    public static void syncAlbum(String imageUrl, Application application) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            final Intent scanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            final Uri contentUri = Uri.fromFile(FileUtils.getFileByPath(imageUrl).getAbsoluteFile());
            scanIntent.setData(contentUri);
            application.sendBroadcast(scanIntent);
        } else {
            //4.4开始不允许发送"Intent.ACTION_MEDIA_MOUNTED"广播, 否则会出现: Permission Denial: not allowed to send broadcast android.intent.action.MEDIA_MOUNTED from pid=15410, uid=10135
            final Intent intent = new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory()));
            application.sendBroadcast(intent);
        }
    }

    /**
     * 获取全局context的方法
     */
    public static Context getAppContext() {
        return appContext;
    }

    /**
     * 日期格式转换，将毫秒时间戳转换为 “ 年-月-日  时:分:秒 ” 格式
     */
    public static String getHumanTime(String millisecond) {
        Date d = new Date(Long.parseLong(millisecond));
        return new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss").format(d);
    }

    public static long getCallLogState(Context context,String number) {
        ContentResolver cr = context.getContentResolver();
        PermissionChecker.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG);
        final Cursor cursor = cr.query(CallLog.Calls.CONTENT_URI,
                new String[]{CallLog.Calls.NUMBER,CallLog.Calls.TYPE,CallLog.Calls.DURATION},
                CallLog.Calls.NUMBER +"=?",
                new String[]{number},
                CallLog.Calls.DATE + " desc");
        int i = 0;
        while(cursor.moveToNext()){

            if (i == 0) {//第一个记录 也就是当前这个电话的记录
                int durationIndex = cursor.getColumnIndex(CallLog.Calls.DURATION);
                long durationTime = cursor.getLong(durationIndex);
//                Log.d("test", "getCallLogState: -----------------duration= " + durationTime);
                return durationTime;
            }
            i++;
        }
        return 0;
    }

    public static String getPingYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        char[] input = inputString.trim().toCharArray();
        String output = "";

        try {
            for (char curchar : input) {
                if (java.lang.Character.toString(curchar).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(curchar, format);
                    output += temp[0];
                } else{
                    output += java.lang.Character.toString(curchar);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return output;
    }


    /**
     * 获取当前应用版本号
     *
     * @return 版本号
     */
    public static String getAppVersionName() {
        String versionName = "";
        int versioncode = 0;
        try {
            // ---get the package info---
            PackageManager pm = appContext.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(appContext.getPackageName(), 0);
            versionName = pi.versionName;
            versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 显示 Toast 提示
     *
     * @param message 提示信息
     */
    public static void showMessage(String message) {
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show();
    }


    /**
     * 使用了头条适配方案的 activity 设置顶部 padding 为状态栏高度 的方法
     *
     * @param viewGroup 要设置 topPadding 的控件
     */
    public static void setAdaptTopPadding(ViewGroup viewGroup) {
        viewGroup.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0);
    }

    public static boolean   isContainsKey(String key,String ...keyStrs){
        if (key == null||keyStrs ==null || keyStrs.length == 0){
            return false;
        }
        for (int i =0;i < keyStrs.length;i++){
            if (keyStrs[i]!=null && key.equals(keyStrs[i]))
            {
                return true;
            }
        }
        return false;
    }
}
