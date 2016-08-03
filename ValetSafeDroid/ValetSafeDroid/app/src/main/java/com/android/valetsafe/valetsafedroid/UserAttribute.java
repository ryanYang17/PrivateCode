package com.android.valetsafe.valetsafedroid;

import android.app.Application;

/**
 * Created by ryan on 2016/7/26.
 */
public class UserAttribute {
    public final static String TYPE_ADVANCED = "0";
    public final static String TYPE_NOW = "1";
    private static long id;
    private static String name;
    private static String cell_phone;
    private static String email;
    private static String password;
    private static String register_time;
    private static String delete_time;

    public static long getId() {
        return id;
    }

    public static void setId(long id) {
        UserAttribute.id = id;
    }

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        UserAttribute.name = name;
    }

    public static String getCell_phone() {
        return cell_phone;
    }

    public static void setCell_phone(String cell_phone) {
        UserAttribute.cell_phone = cell_phone;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UserAttribute.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserAttribute.password = password;
    }

    public static String getRegister_time() {
        return register_time;
    }

    public static void setRegister_time(String register_time) {
        UserAttribute.register_time = register_time;
    }

    public static String getDelete_time() {
        return delete_time;
    }

    public static void setDelete_time(String delete_time) {
        UserAttribute.delete_time = delete_time;
    }
}
