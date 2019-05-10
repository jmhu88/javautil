/*
 * Copyright (C), 2002-2014, 365金融研发中心
 * Project: finance-framework
 * PackName: com.house365.finance.framework.util
 * FileName: SmsUtil.java
 * Author: 时林涛
 * Date: 2014年10月8日
 * History:
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 短信发送工具类
 * 
 * @author 时林涛
 * @date 2014年10月8日
 * @version 1.0
 */
public class SmsUtil {
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(SmsUtil.class);
    /**
     * 短信发送服务的URL
     */
    private static String smsHost = "system.default.sms.host";
    /**
     * 通知短信发送服务的JID
     */
    private static String smsNotifyJid = "system.default.sms.notify.jid";
    /**
     * 验证码短信发送服务的JID
     */
    private static String smsVerifyJid = "system.default.sms.verify.jid";
    /**
     * 短信发送服务的部门
     */
    private static String smsDepart = "system.default.sms.depart";
    /**
     * 短信发送服务的城市
     */
    private static String smsCity = "system.default.sms.city";

    /**
     * 
     * 通知短信发送
     * 
     * @param phoneList
     * @param msMsg
     * @return
     * @throws Exception
     */
    public static boolean sendNotifySms(String phone, String msMsg) throws Exception {
        Map<String, Object> urlParams = new HashMap<String, Object>();
        urlParams.put("msg", msMsg);
        // 获取发送URL
        String sendUrl = getSendUrl(phone, smsNotifyJid);
        return sendSingleSms(sendUrl, urlParams, phone);
    }

    /**
     * 
     * 验证码短信发送
     * 
     * @param phoneList
     * @param msMsg
     * @param yzmCode
     * @return
     * @throws Exception
     */
    public static boolean sendVerifySms(String phone, String msMsg, String yzmCode) throws Exception {
        Map<String, Object> urlParams = new HashMap<String, Object>();
        urlParams.put("msg", msMsg);
        urlParams.put("yzm", yzmCode);
        // 获取发送URL
        String sendUrl = getSendUrl(phone, smsVerifyJid);
        return sendSingleSms(sendUrl, urlParams, phone);
    }

    /**
     * 获取发送短信URL
     * 
     * @param phoneList
     * @param msJid
     * @return
     * @throws Exception
     */
    private static String getSendUrl(String phone, String msJid) throws Exception {
        List<String> urlParam = null;
        String sendURL = "";
        String param_jid = CustomPropertyConfigurer.getMessage(msJid);
        String param_depart = CustomPropertyConfigurer.getMessage(smsDepart);
        String param_city = CustomPropertyConfigurer.getMessage(smsCity);
        // 从配置文件中获取URL
        urlParam = new ArrayList<String>();
        urlParam.add(param_jid);
        urlParam.add(param_depart);
        urlParam.add(param_city);
        urlParam.add(phone);
        sendURL = CustomPropertyConfigurer.getMessage(smsHost, urlParam.toArray());
        return sendURL;
    }

    /**
     * 
     * 发送消息
     * 
     * @param sendURL
     * @param params
     * @param phone
     * @return true/false
     */
    private static boolean sendSingleSms(String sendURL, Map<String, Object> urlParams, String phone) {
        String result = "";
        try {
            result = HttpClientUtil.doGet(sendURL, urlParams, "gbk");
            JSONObject json = JSONObject.fromObject(result);
            if (json.isEmpty() || json.isNullObject() || null == json.get(phone) || !json.get(phone).equals(1)) {
                logger.error("手机->" + phone + "发送短信失败，返回JSON为->" + result);
                return false;
            }
            // 返回结果格式：1：表示短信运营商接受指令成功
            // {"15861800307":1}
            return true;
        } catch (Exception e) {
            // 处理异常情况，eg:result无法转化为JSON
            logger.error("手机->" + phone + "发送短信失败，返回JSON为->" + result + ",抛出->" + e.getMessage());
            return false;
        }
    }
}
