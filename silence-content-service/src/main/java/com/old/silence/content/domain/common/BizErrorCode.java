package com.old.silence.content.domain.common;

import org.apache.http.HttpStatus;

public enum BizErrorCode {

    INVALID_REWARD_ITEM_ID(HttpStatus.SC_BAD_REQUEST, 10001, "无效的奖品ID"),
    INVALID_REWARD_ITEM_TYPE(HttpStatus.SC_BAD_REQUEST, 10101, "无效的奖品类型"),
    INVALID_REWARD_ITEM_STOCK(HttpStatus.SC_BAD_REQUEST, 10102, "很遗憾，已抢完"),
    CANNOT_DELETE_REWARD_ITEM(HttpStatus.SC_BAD_REQUEST, 10201, "正在生效的活动中,不能删除奖品"),
    INVALID_EVENT(HttpStatus.SC_BAD_REQUEST, 10301, "无效的活动"),
    INVALID_EVENT_GAME(HttpStatus.SC_BAD_REQUEST, 10302, "无效的玩法"),
    INVALID_EVENT_GAME_REWARD_ITEM(HttpStatus.SC_BAD_REQUEST, 10303, "无有效的任务奖品"),
    INVALID_PARAMS(HttpStatus.SC_BAD_REQUEST, 10401, "无效的参数"),
    INVALID_EVENT_GAME_TASK_TYPE(HttpStatus.SC_BAD_REQUEST, 10402, "无效的任务类型"),
    REWARD_ITEM_STOCK_UPDATE_INVALID_PARAMS(HttpStatus.SC_BAD_REQUEST, 10501, "减少的数量不能大于剩余库存量。"),

    INVOKE_THIRDPARTY_PORT_ERROR(HttpStatus.SC_BAD_REQUEST, 10402, "调用第三方接口失败"),

    DECRYPT_UPLOADSTEPS_ERROR(HttpStatus.SC_BAD_REQUEST, 10403, "解密上传步数失败"),

    INVALID_REWARD_STATUS(HttpStatus.SC_BAD_REQUEST, 10101, "无效的奖品状态"),
    DUPLICATE_EVENT_CODE(HttpStatus.SC_BAD_REQUEST, 10111, "活动识别码已存在"),
    DUPLICATE_GROUP_CODE(HttpStatus.SC_BAD_REQUEST, 10211, "组合编码已存在"),
    EVENT_GAME_REPEATED_USE(HttpStatus.SC_BAD_REQUEST, 10212, "包含已在其他组合中的玩法,请检查确认"),
    INVALID_MARKETING_PROMOTION(HttpStatus.SC_BAD_REQUEST, 10501, "优惠信息无效"),
    INVALID_MARKETING_PROMOTION_ACCOUNT_ERROR(HttpStatus.SC_BAD_REQUEST, 10502, "账户信息不可为空"),

    INVALID_VERIFY_CODE(HttpStatus.SC_BAD_REQUEST, 10301, "无效的验证码"),
    INVALID_PHONE(HttpStatus.SC_BAD_REQUEST, 10302, "无效的手机号"),
    CANNOT_OPERATE_FREQUENTLY(HttpStatus.SC_BAD_REQUEST, 10303, "请勿频繁操作"),
    CANNOT_REQUEST_FREQUENTLY(HttpStatus.SC_BAD_REQUEST, 10401, "请勿频繁请求"),
    FREQUENCY_EXCEEDS_LIMIT(HttpStatus.SC_BAD_REQUEST, 20401, "频次超过限制"),
    CANNOT_REPEAT_ASSIST(HttpStatus.SC_BAD_REQUEST, 10501, "请勿重复助力"),
    CANNOT_SELF_ASSIST(HttpStatus.SC_BAD_REQUEST, 10504, "不能给本人助力"),
    INVENTORY_DEDUCTION_FAILED(HttpStatus.SC_BAD_REQUEST, 10402, "库存扣减失败"),
    ASSISTANT_FREQUENCY_LIMIT(HttpStatus.SC_BAD_REQUEST, 10502, "助力者频次已达上限"),
    SHARER_FREQUENCY_LIMIT(HttpStatus.SC_BAD_REQUEST, 10503, "分享者频次已达上限"),

    DATA_COUNT_TOO_LARGE(HttpStatus.SC_BAD_REQUEST, 20001, "数据量太大,请调整请求参数"),
    CANNOT_FIND_PARTICIPANT(HttpStatus.SC_BAD_REQUEST, 20002, "无法找到用户信息,请确认手机号是否正确"),

    EVENT_ERROR(HttpStatus.SC_BAD_REQUEST, 10601, "活动太火爆了,请刷新后重试!"),

    REQUEST_NOT_UP_TO_STANDARD(HttpStatus.SC_BAD_REQUEST, 10602, "请求参数不达标!"),

    CHANNEL_CONFIG_STATUS_99(HttpStatus.SC_BAD_REQUEST, 10603, "渠道配置状态为已停售,不允许新增买赠配置!"),
    CHANNEL_CONFIG_STATUS_OTHER(HttpStatus.SC_BAD_REQUEST, 10604, "未知渠道配置状态,不允许新增买赠配置!"),

    JMP_PRIVILEGE_ERROR(HttpStatus.SC_BAD_REQUEST, 10700, "权益激活失败！"),
    JMP_TASK_ERROR(HttpStatus.SC_BAD_REQUEST, 10701, "激活医健周任务失败！"),
    JMP_ADD_STEP_ERROR(HttpStatus.SC_BAD_REQUEST, 10702, "同步步数失败！"),
    JMP_QUERY_ERROR(HttpStatus.SC_BAD_REQUEST, 10703, "查询任务数据失败！"),
    CONFIG_CHECK_ERROR(HttpStatus.SC_BAD_REQUEST, 10702, "规则起效终止时间内存在有效规则"),
    CONFIG_TIME_CHECK_ERROR(HttpStatus.SC_BAD_REQUEST, 10705, "请检查时间设置"),
    CANNOT_FIND_WALK_TASK(HttpStatus.SC_BAD_REQUEST, 10704, "未找到有效的医健步数玩法"),
    JMP_QUERY_WEEK_TASK_INFO_ERROR(HttpStatus.SC_BAD_REQUEST, 10706, "查询医健周信息失败！"),

    CANNOT_REPEAT_SIGN_UP(HttpStatus.SC_BAD_REQUEST, 10801, "请勿重复报名"),
    CANNOT_FIND_SIGN_UP(HttpStatus.SC_BAD_REQUEST, 10802, "未找到有效的报名玩法"),
    SIGN_UP_CHECK_FALSE(HttpStatus.SC_BAD_REQUEST, 10803, "不符合报名资格"),

    CANNOT_CANCEL_SIGNUP(HttpStatus.SC_BAD_REQUEST, 10900, "已签到无法取消报名"),
    CANNOT_SIGNIN_IF_NOT_SIGNUP(HttpStatus.SC_BAD_REQUEST, 10901, "未报名无法签到"),

    HOLDER_OVER_FREQUENCY_LIMIT(HttpStatus.SC_BAD_REQUEST, 10910, "投保人超过次数限制"),
    INSURED_OVER_FREQUENCY_LIMIT(HttpStatus.SC_BAD_REQUEST, 10911, "被保人超过次数限制"),
    HOLDER_INFO_NULL(HttpStatus.SC_BAD_REQUEST, 10912, "投保人信息为空"),
    INSURED_INFO_NULL(HttpStatus.SC_BAD_REQUEST, 10913, "被保人信息为空"),
    SOURCE_POLICY_OVER_FREQUENCY_LIMIT(HttpStatus.SC_BAD_REQUEST, 10914, "保单超过次数限制"),
    SOURCE_POLICY_INFO_NULL(HttpStatus.SC_BAD_REQUEST, 10915, "来源保单信息为空"),

    ORG_NOT_AUTHORIZE(HttpStatus.SC_BAD_REQUEST, 10920, "您没有权限执行此操作，请联系管理员获取相应权限。"),

    INVENTORY_INCREMENT_ACCUMULATED_FAILED(HttpStatus.SC_BAD_REQUEST, 10404, "更新活动已使用库存失败"),
    UPDATE_COUNT_NOT_MATCH(HttpStatus.SC_BAD_REQUEST, 10405, "更新数据数量和原始数据数量不匹配"),

    // 账户产品配置相关 ------
    ACCOUNT_CONFIG_TYPE_ERROR(HttpStatus.SC_BAD_REQUEST, 11001, "账户产品配置不正确"),
    ACCOUNT_CONFIG_SOURCE_IDENTIFIER_NOT_BLANK(HttpStatus.SC_BAD_REQUEST, 11002,
            "账户产品(险种)sourceIdentifier不可为空"),
    ACCOUNT_CONFIG_END_DATE_CANNOT_EARLIER_START_DATE(HttpStatus.SC_BAD_REQUEST, 11003, "结束日期不可早于开始日期"),
    ACCOUNT_CONFIG_AM_CANNOT_BLANK(HttpStatus.SC_BAD_REQUEST, 11004, "可购买的AM不可为空"),
    ACCOUNT_CONFIG_ATTRIBUTES_CANNOT_BLANK(HttpStatus.SC_BAD_REQUEST, 11005, "attributes不可为空"),
    ACCOUNT_CONFIG_DESCRIPTION_CANNOT_BLANK(HttpStatus.SC_BAD_REQUEST, 11006, "描述信息description不可为空"),
    ACCOUNT_CONFIG_CHANNEL_CODES_CANNOT_BLANK(HttpStatus.SC_BAD_REQUEST, 11007, "投保来源channelCodes不可为空"),
    ACCOUNT_BALANCE_RANGE_OUT_OF_RANGE(HttpStatus.SC_BAD_REQUEST, 11008, "账户余额区间超出范围"),
    ACCOUNT_CONFIG_BACK_INTERVAL_CANNOT_LESS_FRONT_VALUE(HttpStatus.SC_BAD_REQUEST, 11009, "后区间不可小于前值"),
    ACCOUNT_CONFIG_ENTER_DECIMAL_BETWEEN(HttpStatus.SC_BAD_REQUEST, 11010, "请输入(0,1]间的小数"),
    ACCOUNT_CONFIG_ONLY_SUPPORT_TWO_DECIMAL(HttpStatus.SC_BAD_REQUEST, 11011, "仅支持两位小数"),
    ACCOUNT_CONFIG_DISCOUNT_CANNOT_LESS_MAXIMUM_DISCOUNT(HttpStatus.SC_BAD_REQUEST, 11012, "折扣不可小于最高折扣"),
    ACCOUNT_CONFIG_BALANCE_INTERVAL_NEEDS_CONTINUOUS(HttpStatus.SC_BAD_REQUEST, 11013, "账户余额区间需连续"),
    ACCOUNT_CONFIG_TIME_RANGE_OVERLAP(HttpStatus.SC_BAD_REQUEST, 11014,
            "该账户产品（险种）下存在相同时间的已生效规则，请检查"),
    ACCOUNT_CONFIG_AM_COUNT_ERROR(HttpStatus.SC_BAD_REQUEST, 11015, "可购买的AM数量超过限制"),

    CLAIM_REWARD_FAIL(HttpStatus.SC_BAD_REQUEST, 10406, "领取奖励失败，请稍后重试"),

    INVALID_COMPETITION_ID(HttpStatus.SC_BAD_REQUEST, 20001, "无效的赛事ID"),
    INVALID_ROUND_ID(HttpStatus.SC_BAD_REQUEST, 20002, "无效的轮次ID"),
    INVALID_MATCH_ID(HttpStatus.SC_BAD_REQUEST, 20003, "无效的比赛场次"),
    INVALID_GUESS(HttpStatus.SC_BAD_REQUEST, 20004, "比赛已开始或结束，无法竞猜"),
    INVALID_GUESS_TIME(HttpStatus.SC_BAD_REQUEST, 20005, "竞猜时间无效：请在比赛开始前5分钟内提交"),
    DUPLICATE_GUESS(HttpStatus.SC_BAD_REQUEST, 20006, "您已竞猜过，请勿重复竞猜"),
    GUESS_LOCK(HttpStatus.SC_BAD_REQUEST, 20007, "竞猜提交过于频繁，请稍后再试"),
    GUESS_UPDATE_LOCK(HttpStatus.SC_BAD_REQUEST, 20008, "竞猜结果已经开始更新，请稍后再试"),
    DUPLICATE_MATCH(HttpStatus.SC_BAD_REQUEST, 20009, "您已提交游戏数据，请勿重复提交"),
    GUESS_UPDATE_LOCK_TIMEOUT(HttpStatus.SC_BAD_REQUEST, 20010, "竞猜获取锁超时失败"),

    TOURNAMENT_REGISTRATION_LIMIT(HttpStatus.SC_BAD_REQUEST, 30001, "报名人数已满，请关注后续活动"),
    TOURNAMENT_REGISTRATION_REPEAT(HttpStatus.SC_BAD_REQUEST, 30002, "请勿重复报名"),
    TOURNAMENT_REGISTRATION_OPERATE_FREQUENTLY(HttpStatus.SC_BAD_REQUEST, 30003, "请勿频繁操作"),
    TOURNAMENT_NOT_EXIST(HttpStatus.SC_BAD_REQUEST, 30004, "赛事不存在"),

    TOURNAMENT_REGISTRATION_NOT_FOUND(HttpStatus.SC_BAD_REQUEST, 30005, "请进行活动报名"),
    TOURNAMENT_CONFIG_NOT_FOUND(HttpStatus.SC_BAD_REQUEST, 30006, "未找到活动配置信息"),
    TOURNAMENT_MARKETING_EVENT_NOT_PUBLISHED(HttpStatus.SC_BAD_REQUEST, 30007, "活动未发布"),
    TOURNAMENT_MARKETING_EVENT_NOT_READY(HttpStatus.SC_BAD_REQUEST, 30008, "活动未开始"),
    TOURNAMENT_MARKETING_EVENT_END(HttpStatus.SC_BAD_REQUEST, 30009, "活动已结束"),
    TOURNAMENT_REGISTRATION_NOT_READY(HttpStatus.SC_BAD_REQUEST, 30010, "赛事报名未开始"),
    TOURNAMENT_REGISTRATION_END(HttpStatus.SC_BAD_REQUEST, 30011, "赛事报名已结束"),
    TOURNAMENT_REGISTRATION_WITHOUT_VITALITY_GO_BENEFICIARY(HttpStatus.SC_BAD_REQUEST, 30011, "没有活力go权益"),

    // 302
    TOURNAMENT_CACHE_PARSE_ERROR(HttpStatus.SC_BAD_REQUEST, 30207, "挑战缓存解析失败"),
    TOURNAMENT_CHALLENGE_NOT_FOUND(HttpStatus.SC_BAD_REQUEST, 30208, "未找到进行中的挑战记录"),
    TOURNAMENT_TASK_ITEMS_INSUFFICIENT(HttpStatus.SC_BAD_REQUEST, 30209, "完成任务项数量不足"),
    TOURNAMENT_CHALLENGE_EXPIRED(HttpStatus.SC_BAD_REQUEST, 302010, "挑战已过期，无法提交"),
    TOURNAMENT_INVALID_TIME_RANGE(HttpStatus.SC_BAD_REQUEST, 30211, "挑战时间不在有效范围内"),
    TOURNAMENT_EXERCISE_ITEMS_EMPTY(HttpStatus.SC_BAD_REQUEST, 30212, "挑战任务项为空"),
    TOURNAMENT_EXERCISE_ITEM_NOT_ALLOWED(HttpStatus.SC_BAD_REQUEST, 30213, "挑战任务项不在指定任务项中"),
    TOURNAMENT_RANKING_DATA_NOT_FOUND(HttpStatus.SC_BAD_REQUEST, 30214, "未查询到排行榜数据"),

    ;

    private final Integer httpStatusCode;

    private final Integer code;

    private final String message;

    BizErrorCode(Integer httpStatusCode, Integer code, String message) {
        this.httpStatusCode = httpStatusCode;
        this.code = code;
        this.message = message;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
