package com.android.valetsafe.valetsafedroid;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ryan on 2016/7/6.
 */
public class PublicFunction {
    private boolean hasSD = false;
    private String SDPATH;
    private String FILESPATH;
    public PublicFunction(Context context){
        hasSD = Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
        SDPATH = Environment.getExternalStorageDirectory().getPath();
        FILESPATH = context.getFilesDir().getPath();
    }
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

    //判断文件是否存在
    public boolean fileIsExists(String strFile)
    {
        try
        {
            File f=new File(strFile);
            if(!f.exists())
            {
                return false;
            }

        }
        catch (Exception e)
        {
            return false;
        }

        return true;
    }

    public void DeleteFile(String strFile)
    {
        try
        {
            File f=new File(strFile);
            if(!f.exists())
            {
                return;
            }
            else
            {
                f.delete();
            }
        }
        catch (Exception e)
        {
        }

        return;
    }

    public void writeTxtToFile(String strcontent, String fileName) {
        // 每次写入时，都换行写
        String strContent = strcontent + "\r\n";
        try {
            File file = new File(SDPATH + "//" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rwd");
            raf.seek(file.length());
            raf.write(strContent.getBytes());
            raf.close();
        } catch (Exception e) {
            Log.e("TestFile", "Error on write File:" + e);
        }
    }

    // 生成文件
    public File makeFilePath(String filePath, String fileName) {
        File file = null;
        makeRootDirectory(filePath);
        try {
            file = new File(filePath + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }

    // 生成文件夹
    public static void makeRootDirectory(String filePath) {
        File file = null;
        try {
            file = new File(filePath);
            if (!file.exists()) {
                file.mkdir();
            }
        } catch (Exception e) {
            Log.i("error:", e + "");
        }
    }
}
