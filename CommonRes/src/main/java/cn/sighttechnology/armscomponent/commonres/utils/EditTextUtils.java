package cn.sighttechnology.armscomponent.commonres.utils;

import android.widget.EditText;

public class EditTextUtils {
    //设置小数点输入位数
    public static void setNumberDot(EditText...    editText){
        if (editText == null ||
                editText.length == 0){
            return;
        }
        for (int i = 0; i < editText.length;i++){
            editText[i].addTextChangedListener(new NumberDotTextWatcher(editText[i]));
        }

    }

    //设置小数点输入位数
    public static void setNumberDot(int dotNum, EditText...    editText ){
        if (editText == null ||
                editText.length == 0){
            return;
        }
        for (int i = 0; i < editText.length;i++){
            editText[i].addTextChangedListener(
                    new NumberDotTextWatcher(editText[i]).setDigits(dotNum));
        }
    }
}
