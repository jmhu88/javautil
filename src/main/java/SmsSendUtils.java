/*
 * Copyright (C), 2002-2014, 三六五网络金融
 * FileName: SmsSendUtils.java
 * Author:   12100131
 * Date:     2013-6-9 下午5:13:23
 * Description: 短信发送的工具类     
 */

import com.house365.finance.framework.sms.SimpleMsg;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 短信发送的工具类<br>
 * 
 * @author 12100131
 */
public final class SmsSendUtils {
    /**
     * 多次发短信时，最大的号码数量
     */
    public final static int MAX_SEND_SMS = 100;

    /**
     * 批量发送短信失败
     */
    public final static int BATCH_SEND_ERROR = -1;
    
    /**
     * 逗号
     */
    public final static String COMMA = ",";

    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(SmsSendUtils.class);

    /**
     * 私有的构造函数
     */
    private SmsSendUtils() {
    }

    /**
     * 单个号码发短信<br>
     * 
     * @author 12100131
     * @param phoneNumber 电话号码
     * @param content 短信内容
     * @return 是否发送成功 true:发送成功 false:发送失败
     */
    public static boolean singleSend(String phoneNumber, String content) {
        return singleSendWithPriority(phoneNumber, content, 0);
    }

    /**
     * 带有优先级的单个号码短信发送<br>
     * 【注意】该参数要和短信平台沟通，不能随便指定
     * 
     * @author 12100131
     * @param phoneNumber 电话号码
     * @param content 短信内容
     * @param priority 短信发送的优先级
     * @return 是否发送成功 true:发送成功 false:发送失败
     */
    public static boolean singleSendWithPriority(String phoneNumber, String content, int priority) {
        if (StringUtils.isBlank(phoneNumber) || StringUtils.isBlank(content)) {
            logger.warn("Method:[singleSendWithPriority], phoneNumber or content is empty when sending single sms.");
            return false;
        }

        return SimpleMsg.send(phoneNumber, content, priority);
    }

    /**
     * 对多个号码发送同样的内容<br>
     * 号码与号码之间用“,”号间隔，内容是一样的
     * 
     * @author 12100131
     * @param phoneList 电话号码list
     * @param content 短信的内容
     * @return 是否发送成功 true:发送成功 false:发送失败
     */
    public static boolean multiSend(List<String> phoneList, String content) {
        return multiSendWithPriority(phoneList, content, 0);
    }

    /**
     * 对多个号码发送同样的内容，有优先级<br>
     * 号码与号码之间用“,”号间隔，内容是一样的<br>
     * 【注意】该参数要和短信平台沟通，不能随便指定
     * 
     * @author 12100131
     * @param phoneList 电话号码list
     * @param content 短信的内容
     * @return 是否发送成功 true:发送成功 false:发送失败
     */
    public static boolean multiSendWithPriority(List<String> phoneList, String content, int priority) {
        if (StringUtils.isBlank(content)) {
            logger.warn("Method:[multiSend], content is empty when multi sending sms.");
            return false;
        }

        if (null == phoneList || phoneList.isEmpty()) {
            logger.warn("Method:[multiSend], phoneList is empty or null when multi sending sms.");
            return false;
        }

        // 每次多次发送的次数不超过100个
        if (MAX_SEND_SMS < phoneList.size()) {
            logger.warn("Method:[multiSend], the number of phone is bigger than 100 when multi sending sms.");
            return false;
        }

        // 拼接多次发短信的号码
        StringBuffer phoneBuff = new StringBuffer(100);
        for (Iterator<String> iter = phoneList.iterator(); iter.hasNext();) {
            phoneBuff.append(iter.next());

            // 最后一个号码不需要拼接逗号
            if (iter.hasNext()) {
                phoneBuff.append(COMMA);
            }
        }

        // 调用多次发送短信，号码与号码之间用“,”号间隔
        return SimpleMsg.sendMulti(phoneBuff.toString(), content, priority);
    }

    /**
     * 批量发送短信<br>
     * 号码和短信内容必须一一对应
     * 
     * @author 12100131
     * @param phoneList 电话号码list
     * @param contentList 短信内容的list
     * @return 短信发送成功的数量<br>
     *         如果电话号码和短信内容为空则发送失败返回-1,；如果两个List长度不一致，返回-1；如果发送的数量超过100，发送失败
     */
    public static int batchSend(List<String> phoneList, List<String> contentList) {
        return batchSendWithPriority(phoneList, contentList, 0);
    }

    /**
     * 带有优先级的批量发送短信 <br>
     * 号码和短信内容必须一一对应<br>
     * 【注意】该参数要和短信平台沟通，不能随便指定
     * 
     * @author 12100131
     * @param phoneList 电话号码list
     * @param contentList 短信内容的list
     * @param priority 短信发送的优先级
     * @return 短信发送成功的数量<br>
     *         如果电话号码和短信内容为空则发送失败返回-1,；如果两个List长度不一致，返回-1；如果发送的数量超过100，返回-1
     */
    public static int batchSendWithPriority(List<String> phoneList, List<String> contentList, int priority) {
        if (null == phoneList || phoneList.isEmpty()) {
            logger.warn("Method:[batchSend], phoneList is empty or null when batch sending sms.");
            return BATCH_SEND_ERROR;
        }

        if (null == contentList || contentList.isEmpty()) {
            logger.warn("Method:[batchSend], contentList is empty or null when batch sending sms.");
            return BATCH_SEND_ERROR;
        }

        // 批量发送短信的号码和短信内容不一致
        if (phoneList.size() != contentList.size()) {
            logger.warn("Method:[batchSend], the size of contentList and size of phoneList are not consistent when batch sending sms.");
            return BATCH_SEND_ERROR;
        }
        
        // 每次多次发送的次数不超过100个
        if (MAX_SEND_SMS < phoneList.size()) {
            logger.warn("Method:[batchSend], the number of phone is bigger than 100 when batch sending sms.");
            return BATCH_SEND_ERROR;
        }

        int size = phoneList.size();
        int result = 0;

        // 调取单次循环发送短信
        for (int i = 0; i < size; i++) {
            if (singleSendWithPriority(phoneList.get(i), contentList.get(i), priority)) {
                result++;
            }
        }

        return result;
    }
}
