package com.abm.constant;

public class EndPoints {
    public static final String VERSION="/v1";
    //profiller:
    public static final String API="/api";
    public static final String DEV="/dev";
    public static final String TEST="/test";

    public static final String ROOT=API+VERSION;


    //entities:
    public static final String AUTH= ROOT+"/auth";



    //methods:
    public static final String SAVE = "/save";
    public static final String REGISTER = "/register";
    public static final String VERIFY_ACCOUNT = "/verify_account";
    public static final String LOGIN = "/login";
    public static final String UPDATE_EMAIL = "/update_email";
    public static final String DELETE = "/delete";
    public static final String FINDALL = "/findall";
    public static final String FINDBYID = "/findbyid";
    public static final String UPDATE_PASSWORD ="update_password";
    public static final String FORGET_PASSWORD = "forget_password";
    ;
}
