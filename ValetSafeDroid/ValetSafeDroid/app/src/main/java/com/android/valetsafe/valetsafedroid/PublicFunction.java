package com.android.valetsafe.valetsafedroid;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ryan on 2016/7/6.
 */
public class PublicFunction {
    public boolean ValidateUserName(String sName)
    {
        return !sName.trim().isEmpty();
    }

    public boolean ValidateCellphone(String sCellphone){
        if (sCellphone.length() != 8)
            return false;
        for (int i = 0; i < sCellphone.length(); i++)
        {
            if (i == 0)
            {
                if ((sCellphone.charAt(i) != '9') && (sCellphone.charAt(i) != '8'))
                    return false;
            }
            else
            {
                if (!Character.isDigit(sCellphone.charAt(i)))
                    return false;
            }
        }
        return true;
    }

    public boolean ValidateEmail(String sEmail)
    {
        String str="^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(sEmail);
        return m.matches();
    }
}
