/*
 * Copyright (C), 2002-2014, 365金融研发中心
 * Project: finance-framework
 * PackName: com.house365.finance.framework.util
 * FileName: PageUtils.java
 * Author: 熊林立
 * Date: 2014年11月5日
 * History:
 */

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hsqldb.lib.StringUtil;

/**
 * 页面参数校验
 * 
 * @author 熊林立
 * @date 2014年11月5日
 * @version 1.0
 */
public class PageUtils {

    public static final String DEFAULT_EMPTY_STRING = "";

    public static final String DEFAULT_NULL_STRING = "null";

    /**
     * 数字每4位 添加一个空格
     * 
     * @param str
     * @return
     */
    public static String getFourBlank(String str) {
        if (StringUtils.isEmpty(str)) {
            return DEFAULT_EMPTY_STRING;
        } else {
            str = str.trim();
            int count = 0;
            StringBuilder sb = new StringBuilder(str);
            for (int i = 0; i < str.length(); i++) {
                if (i != 0) {
                    if (i % 4 == 0) {
                        sb.insert(i + count, " ");
                        count++;

                        // 银行卡加*
                        if (str.length() - i > 4) {
                            sb.replace(i + count, i + count + 4, "****");
                        }
                    }
                }
            }
            return sb.toString();
        }

    }

    /**
     * 
     * 根据身份证获取年龄
     * 
     * @param idCardNo
     * @return
     * @author 华庆春
     */
    @SuppressWarnings("deprecation")
    public static int getAgeByIdCardNo(String idCardNo) {
        if (StringUtils.isEmpty(idCardNo)) {
            return 0;
        }
        try {
            String birthStr = null;
            SimpleDateFormat sdf = null;
            if (idCardNo.length() == 15) {
                birthStr = idCardNo.substring(6, 12);
                sdf = new SimpleDateFormat("yyMMdd");
            } else {
                birthStr = idCardNo.substring(6, 14);
                sdf = new SimpleDateFormat("yyyyMMdd");
            }
            Date today = sdf.parse(sdf.format(new Date()));
            Date birth = sdf.parse(birthStr);
            return today.getYear() - birth.getYear();
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 
     * 根据身份证获取性别
     * 
     * @param idCardNo
     * @return
     * @author 华庆春
     */
    public static int getSexByIdCardNo(String idCardNo) {
        if (StringUtils.isEmpty(idCardNo)) {
            return 0;
        }
        String sexStr = null;
        if (idCardNo.length() == 15) {
            sexStr = idCardNo.substring(14, 15);
        } else {
            sexStr = idCardNo.substring(16, 17);
        }
        int sexInt = Integer.valueOf(sexStr);
        return sexInt % 2;
    }

    /**
     * 
     * 处理Str，超过长度length的部分用点号显示
     * 
     * @param name
     *            length
     * @return
     */
    public static String getAsteriskName(String name, int length) {
        if (StringUtil.isEmpty(name))
            return "";
        if (name.length() <= length) {
            return name;
        }
        return name.substring(0, length) + "...";
    }

    /**
     * 
     * 校验用户输入的银行卡号
     * 
     * @param idCardNo
     * @return
     */
    public static boolean checkIdCardNo(String idCardNo) {
        if (StringUtils.isEmpty(idCardNo)) {
            return false;
        }
        Pattern pattern;

        if (idCardNo.length() == 15) {
            pattern = Pattern.compile("^(\\d{6})()?(\\d{2})(\\d{2})(\\d{2})(\\d{2})(\\w)$");
        } else if (idCardNo.length() == 18) {
            pattern = Pattern.compile("^(\\d{6})()?(\\d{4})(\\d{2})(\\d{2})(\\d{3})(\\w)$");
        } else {
            return false;
        }
        Matcher matcher = pattern.matcher(idCardNo);
        if (matcher.matches()) {
            if (idCardNo.length() == 15) {
                String date = "19" + matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5);
                // 判断是否为有效的时间类型
                if (!DateTimeUtils.isDate(date, null)) {
                    return false;
                }
            } else {
                String date = matcher.group(3) + "-" + matcher.group(4) + "-" + matcher.group(5);
                // 判断是否为有效的时间类型
                if (!DateTimeUtils.isDate(date, null)) {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }
}
