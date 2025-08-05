package com.depinx.data.constants;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class CommonConstants {
    public static String METAMASK_SIGN_PRE = "METAMASK_DATA_SIGN_PRE_";
    public static String[] SUPPORT_WALLET = {
            "0x032331D1DC39239c24ca98E5487a8Ed994aF1b92",
            "0xCB257126bf725105D9f194113FF8B08c76E9d4E6"};

    public static final String TOKEN = "token";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String LOGIN_SUCCESS = "Success";

    public static final String LOGIN_FAIL = "Error";

    public static final String LOGIN_USER_KEY = "login_user_key";

    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    public static final String HTTP = "http://";
    public static final String HTTPS = "https://";

    public static final Charset CHARSET_UTF_8 = StandardCharsets.UTF_8;

    public static final String[] JSON_WHITELIST_STR = { "org.springframework", "com.depinx" };

    public static final String LARK_APP_TOKEN = "TJalb3CU6ahKLNst11Vl7kd1gnf";
    public static final String LARK_APP_SECRET = "8rJIevTR4XzWaHgOFuB6kgKfOQDdL1bB";
    public static final String LARK_TABLE_ID = "tbliVafama4qSxxq";
    public static final String LARK_APP_ID = "cli_a8844d69edb8d010";
    public static final String SOURCE_TABLE_ID = "tblVfg19CpKGdKkD";
    public static final String SOURCE_VIEW_ID = "vew3WSdiC0";

    public static final String LARK_TABLE_CACHE_KEY = "data_lark_table_cache";

    public static final String OKX_BASE_URL = "https://www.okx.com";
    public static final String BINANCE_BASE_URL = "https://api.binance.com";
    public static final Map<String, Integer> DAY_INTERVALS = Map.of("3d", 3, "5d", 5, "7d", 7);

}
