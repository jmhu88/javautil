/*
 * Copyright (C), 2002-2014, 365金融研发中心
 * Project: finance-framework
 * PackName: com.house365.finance.framework.util
 * FileName: SmsTemple.java
 * Author: 时林涛
 * Date: 2014年11月14日
 * History:
 */

import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.PropertyPlaceholderHelper.PlaceholderResolver;

/**
 * <一句话描述功能>
 * 
 * @author 时林涛
 * @date 2014年11月14日
 * @version 1.0
 */
public class SmsTempleUtil {

    private static String FINNANCE_PLACEHOLDER_PREFIX = "${";
    private static String FINNANCE_PLACEHOLDER_SUFFIX = "}";
    private static String FINNANCE_DEFAULT = ":";

    private static final Logger logger = LoggerFactory.getLogger(SmsTempleUtil.class);

    public static void main(String[] args) {
        String smsTempleKey = "system.default.imageserver";// "${name},你是人吗${age}";
        HashMap<String, String> mapsKey = new HashMap<String, String>();
        mapsKey.put("name", "时林涛");
        mapsKey.put("age", "时林涛");
        System.out.println(getPlaceHoderInfo(smsTempleKey, mapsKey));
    }

    /**
     * 
     * 根据指定的模板和参数，获取短信内容
     * 
     * @param smsTempleKey
     *            ,短信模板key
     * @param params
     * @return
     * @author 时林涛
     */
    public static String getPlaceHoderInfo(String smsTempleKey, HashMap<String, String> params) {
        // 判断传入参数是否为空和空字符串
        if (StringUtils.isEmpty(smsTempleKey)) {
            logger.error("没有指定短信模板");
            return "";
        }
        // 短信模板
        String sms = "";
        // 根据短信模板key，获取内容
        sms = CustomPropertyConfigurer.getMessage(smsTempleKey);
        // 判断是否获取到模板
        if (StringUtils.isEmpty(sms)) {
            logger.error("指定短信模板无法获取");
            return "";
        }
        // 如果不传参数，直接返回短信内容
        if (null == params || params.isEmpty()) {
            logger.info("获取的短信内容为：" + sms);
            return sms;
        }
        PropertyPlaceholderHelper strictHelper = new PropertyPlaceholderHelper(FINNANCE_PLACEHOLDER_PREFIX,
                FINNANCE_PLACEHOLDER_SUFFIX, FINNANCE_DEFAULT, true);
        sms = strictHelper.replacePlaceholders(sms, (PlaceholderResolver) new RecordPlaceholderResolver(params));
        logger.info("获取的短信内容为：" + sms);
        return sms;
    }
}
