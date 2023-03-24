package com.dev.utils;

import java.text.SimpleDateFormat;

public class Definitions {
    public static final int MINIMAL_PASSWORD_LENGTH = 6;
    public static final int MINIMAL_USERNAME_LENGTH = 5;
    public static final int SECOND = 1000;
    public static final int MINUTE = 60 * SECOND;

    public static final int USER_INITIAL_CREDIT = 1000;

    public static final String EVENT_PLACE_BID = "PLACE-BID";
    public static final String EVENT_CLOSE_AUCTION = "CLOSE-AUCTION";

    public static final String EVENT_STATS = "STATS";


    public static final int MIN_BIDS_FOR_CLOSE_AUCTION = 3;

    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");


    public static final int BID_COST_FEE = 1;

    public static final int ADD_PRODUCT_FEE = 2;
    public static final double PRODUCT_SELL_PROFIT_PERCENT = 0.95;

    public static final String USER_PARAM = "user";
    public static final String ADMIN_PARAM = "admin";







}
