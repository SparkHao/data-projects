package com.depinx.data.constants;

/**
 * 异常编码
 *
 * @author
 */
public enum ExceptionCode {

    //系统相关 start
    SUCCESS(0, "success"),
    SYSTEM_BUSY(-1, "System is busy,Please try again later~"),
    CAPTCHA_ERROR(-12, "Verification code verification failed"),
    CAPTCHA_REPEAT(-13,"SMS has been sent, please do not send it again"),
    BAD_REQUEST(400, "bad Request"),
    METHOD_NOT_SUPPORT(402,"wrong request method"),
    NO_ACCESS(403,"No Access"),
    /**
     * {@code 404 Not Found}.
     *
     */
    NOT_FOUND(404, "No resource found"),
    INTERNAL_SERVER_ERROR(500, "Internal service error"),
    //系统相关 end

    //jwt token 相关 start
    JWT_BASIC_INVALID(40000, "Invalid Basic Authentication Token"),
    JWT_TOKEN_EXPIRED(40001, "Session timed out, please log in again"),
    JWT_ILLEGAL_ARGUMENT(40003, "missing token parameter"),
    JWT_USER_ENABLED(40007, "User has been disabled"),
    USER_NAME_EXISTS(40008, "this user name already exists"),
    //jwt token 相关 end

    //业务异常
    DID_NOT_FOUND(100000," did not found"),
    CREATE_DID_EXCEPTION(100001,"Create did exception"),
    NEED_ADD_INFORMATION_EXCEPTION(100002,"no additional information"),
    NEED_ADD_INFORMATION_REPEAT(100003,"repeat add information"),
    TEMPLATE_NOT_FOUND(100004,"template not found"),
    CLAIM_IS_NULL(100005,"missing claims"),
    HOLDER_NOT_FOUND(100006,"holder not found"),
    EXPIRE_DATE_IS_NULL(100007,"expire date cannot be empty"),
    CREDENTIAL_NOT_FOUND(100008,"credential not found"),
    CREDENTIAL_ID_IS_NULL(100009,"credential id date cannot be empty"),
    DOWNLOAD_CREDENTIAL_ERROR(100010,"download credential error"),
    ISSUE_DATE_IS_NULL(100011,"issue date cannot be empty"),
    DOWNLOAD_CREDENTIAL_SAVE_FILE_ERROR(100012,"download credential save file error"),
    VERIFY_CREDENTIAL_SAVE_FILE_ERROR(100013,"verify credential file error"),
    VERIFY_CREDENTIAL_FAIL(100014,"verify credential fail"),
    CREATE_FILE_FAIL(100015,"create file fail"),
    VIEW_CREDENTIAL_IMAGE_FAIL(100016,"view credential image fail"),
    BATCH_DOWN_CREDENTIAL_IMAGE_FAIL(100017,"batch down credential fail"),
    EXCEL_PARSE_FAIL(100018,"credential excel file parse fail"),
    TEMPLATE_IS_NOT_FOUND(100019,"template id is not found"),
    PARSE_MULTIPART_FILE_FAIL(100020,"parse MultipartFile is fail"),
    EMAIL_IS_ERROR(100021,"email address is error"),
    DID_DOCUMENT_IS_NULL(100022," did document is null"),
    REPEAT_BIND(100023," repeat bind "),
    EXPIRE_DATE_ERROR(100024,"Expiration date must be greater than the issue date"),
    REPEAT_CREDENTIAL_ERROR(100025,"credential id is error"),
    NOT_LOAD_PUBLIC_KEY(100026, "Could not load public key"),
    VERIFY_DOCUMENT_ERROR(100027, "verify document error"),
    PARAMS_VALIDATE_FAIL(100028, "params is validate fail"),
    ADDRESS_IS_NULL(100029, "address cannot be empty"),
    VALIDATE_MESSAGE_VALIDATE(100030, "validate fail"),
    DES_CRYPTO_FAIL(100031, "des crypto fail"),
    D_MASTER_ACCOUNT_ERROR(100032, "get dmaster account fail"),
    BIND_REPEAT(100033, " bind repeat"),
    SIGN_FAIL(100034, " sign fail"),
    NOT_SUPPORT_RECOVERY(100035, "Unregistered does not support recovery"),
    REPEAT_JOIN(100036," repeat join "),
    NO_JOIN(100037," no join "),
    VERIFY_NETWORK_ERROR(100038," verify network error "),
    VERIFY_FAIL(100039,"airdrop  verify fail "),
    AIRDROP_NOT_FOUND(100040,"airdrop not found"),
    TWITTER_VERIFY_NETWORK_ERROR(100041,"twitter verify network error "),
    VERIFYING_NETWORK(100042,"Network verification in progress, wait a moment"),
    TWITTER_NO_AUTH(100043,"twitter no auth "),
    SIGN_IS_NULL(100044, " sign cannot be empty"),

    COIN_IS_NOT_SUPPORT(100045, "不支持的币种"),

    NEED_LOGIN(100046, "need login"),

    ORDER_NOT_MATCH(100047, "order is not match"),

    DUPLICATE_KEY_EXCEPTION(100048, "当前批次已经发放"),

    WALLET_NOT_CONFIG(100049, "wallet address not set"),

    WALLET_NOT_SUPPORT(100050, "wallet not support"),

    AVAILABLE_NOT_ENOUGH(100051, "Below the minimum withdrawal"),
    REVIEW_WITHDRAWING(100051, "A withdrawal request is already under review"),

    ORDER_BALANCE_BELOW_MIN_PLEDGE(100052, "Minimum Staking Amount: "),
    ORDER_BALANCE_BELOW_MAX_PLEDGE(100052, "Maximum Staking Amount: "),
    AMOUNT_IS_ZERO(100053, "Staking Amount is Zero "),
    STAKING_LIMIT_REACHED(100054, "Staking Limit Reached"),
    STORE_BELOW_SOLD(100054, "Store must be greater than sold"),
    DATA_ALREADY_EXISTS(100055, "Data already exists"),
    PARAMS_INVALID(100056, "Params invalid"),
    TASK_EXECUTOR_ERROR(-18, "多线程异常！"),

    INVITE_USER_NOT_EXITS(100057, "邀请人不存在！"),
    INVITE_USER_ILLEGAL(100058, "邀请人不合法！"),
    ORDER_OPER_TYPE_ILLEGAL(100059, "订单操作类型必须一致！"),
    ORDER_OPER_TYPE_ILLEGAL2(100060, "操作类型必须是Swap或Mint！"),
    ORDER_COIN_ID_ILLEGAL(100061, "操作类型不一致！"),
    ORDER_ALREADY_STAKE(100062, "order [%d] 已经 %s！"),
    ORDER_PLEDGE_PERIOD_ILLEGAL(100063, "质押周期不一致！"),
    ORDER_STATE_ILLEGAL(100064, "未支付订单不允许操作！"),
    ORDER_TYPE_ILLEGAL(100065, "不支持联合挖矿订单！"),
    WITHDRAW_FAILED(100066, "withdraw失败！"),
    THREAD_PROCESS_RUNNING(100067, "有Mint|Stake操作正在进行，请稍候再试！"),

    MINT_WITH_TOKEN_NOT_PERMITTED(100068, "Not whitelisted. Please apply for whitelist."),
    MINT_WITH_TOKEN_LIMIT_REACHED(100069, "Payment amount exceeds the whitelist limit: "),
    MINT_WITH_TOKEN_EXPIRED(100070, "Whitelist eligibility expired"),
    EXIST_UNRESOLVED_REQUEST(100071, "Existing an unresolved request, please wait for handling."),
    INVALID_ORDER_BY_FIELD(100072, "Invalid orderBy field: %s"),
    NON_CONFIG_REWARD_RULE(100073, "未配置释放规则"),

    LIQ_ILLEGAL_APPROVAL_STATE(100074, "当前状态禁止审批"),
    LIQ_APPROVAL_PARAM_EMPTY(100075, "审批相关参数缺失"),
    LIQ_ILLEGAL_APPROVAL_VAL(100076, "非法的审批建议值"),
    LIQ_NON_SAME_APPROVE_USER(100077, "只能由原审批人继续处理"),
    LIQ_APPROVAL_EXPIRED(100078, "已过期不能审批"),
    LIQ_ILLEGAL_CONFIRMATION_STATE(100079, "当前状态禁止确认"),
    LIQ_CONFIRMATION_PARAM_EMPTY(100080, "确认相关参数缺失"),
    LIQ_ILLEGAL_CONFIRMATION_VAL(100081, "非法的确认值"),
    LIQ_ILLEGAL_BURN_STATE(100082, "当前状态禁止执行代币销毁"),
    LIQ_INVOKE_BURN_FAILED(100083, "发起burn交易失败"),
    NON_CONFIG_WITHDRAW_USER(100085, "未配置操作用户"),
    NON_CONFIG_CONFIRM_USER(100086, "未配置确认用户"),

    INVALID_CHANNEL_ID(10087, "Wrong product channel id: "),
    MISSING_ASSET_CONFIG(10089, "Asset configuration is missing. Please contact the administrator to configure it."),
    INVALID_PRODUCT_COIN(10090, "The product's coin is not platform coin. Please contact the administrator to configure it."),
    TOO_MANY_MINT_COIN(10091, "Million nodes power product can only set one coin for minting"),
    WALLET_MINT_LIMIT_REACHED(100092, "Total payment amount exceeds the limit."),
    DAILY_OUTPUT_DUPLICATE(100093, "当前批次号已经配置了产出，请修改配置"),

    MISSING_CLAIM_CONFIG(100094, "Claim configuration %s is missing. Please contact the administrator to configure it."),

    BLACKLIST_STAKE_PROHIBITED(100095, "Staking unavailable due to account restrictions. Please contact support."),
    BLACKLIST_WITHDRAW_PROHIBITED(100096, "Withdrawal unavailable due to account restrictions. Please contact support."),
    TRANSFER_INTERVAL_TIME(100087, "请稍后再试"),
    ONLY_SWAP_ORDER_OPERATOR(100088, "Only swap orders can be operated"),
    MN_NODE_PAYING_NODE(100089, "Node paying, please wait"),
    ;

    private final Integer code;
    private String msg;

    ExceptionCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ExceptionCode fromCode(long code) {
        ExceptionCode[] ecs = values();
        for (ExceptionCode ec : ecs) {
            if (ec.getCode() == code) {
                return ec;
            }
        }
        return SUCCESS;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }


    public ExceptionCode build(String msg, Object... param) {
        this.msg = String.format(msg, param);
        return this;
    }

    public ExceptionCode param(Object... param) {
        msg = String.format(msg, param);
        return this;
    }

    public ExceptionCode commonBiz(Object... param) {
        msg = String.format(msg, param);
        return this;
    }
}
