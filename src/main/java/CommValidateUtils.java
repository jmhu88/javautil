/*
 * Copyright (C), 2002-2014, 三六五网络金融
 * FileName: CommValidateUtils.java
 * Author:   12070681 mashenga@cnsuning.com
 * Date:     2013-7-15 下午7:52:16
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */

import java.util.regex.Pattern;

/**
 * 常用参数校验工具类<br>
 * 
 * @author mashenga@cnsuning.com
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class CommValidateUtils {

    /**
     * 
     * 功能描述: <br>
     * 判断字符串是否由数字和字母组成
     * 
     * @author 12070681 mashenga@cnsuning.com
     * @param str 需要验证的字符串
     * @return 由数字或字母组成返回true
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean isLetterOrNum(String str) {
        String regEx = "^[A-Za-z0-9]+$";
        Pattern pat = Pattern.compile(regEx);
        boolean match = pat.matcher(str).matches();
        return match;
    }

    /**
     * 
     * 功能描述: <br>
     * 判断字符串是否为数字
     * 
     * @author 12060945@cnsuning.com
     * @param msg 需要校验的字符串
     * @return 由数字组成返回true
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean isNum(String msg) {
        String reg = "^\\d+$";
        return Pattern.matches(reg, msg);
    }

    /**
     * 
     * 功能描述: <br>
     * 判断字符串是否超出了指定长度
     * 
     * @author mashenga@cnsuning.com
     * @param limit 限制长度
     * @param str 需要校验的字符串
     * @return 未超过指定长度返回true
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean isOutOfLenLimits(int limit, String str) {
        if (str.length() <= limit) {
            return true;
        }
        return false;
    }

    /**
     * 
     * 功能描述: <br>
     * 校验字符串是否是合法的手机号码
     *
     * @author mashenga@cnsuning.com
     * @param str 需校验的字符串
     * @return 合法手机号码返回true
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean isMobile(String str){
        String reg = "^[1][0-9][0-9]{9}$";
        return Pattern.matches(reg, str);
    }
    
    /**
     * 
     * 功能描述: <br>
     * 检查输入字符串是否符合国内固话或者传真格式 
     *
     * @author 12070681 mashenga@cnsuning.com
     * @param str 需校验的字符串
     * @return 符合格式返回true
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean isTelephone(String str){
        String reg = "^(0\\d{2,3})?(-)?(\\d{7,8})(-(\\d{3,}))?$";
        return Pattern.matches(reg, str);
    }
    
    /**
     * 
     * 功能描述: <br>
     * 校验身份证号是否合法
     *
     * @author 12070681 mashenga@cnsuning.com
     * @param idCardNumber 身份证号
     * @return 合法返回true
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean isIdCard(String idCardNumber){
        int length=idCardNumber.length();
        String reg1 = "^[0-9]{15}$/";
        String reg2 = "^[0-9]{17}$";
        String reg3 = "^[0-9]{1}$";
        
        //身份证长度只能为18位或15位
        if(length!=15 && length!=18){
            return false;
        }
        //如果为15位必须是全数字
        if(length==15){
            if(!Pattern.matches(reg1, idCardNumber)){
                return false;
            }
        }
        else{//18位时末尾可以是X或x
            String idCardNumberFront=idCardNumber.substring(0,17);
            String last=idCardNumber.substring(17);
            if(!Pattern.matches(reg2, idCardNumberFront)){
                return false;
            }
            if(!Pattern.matches(reg3, last) && !"x".equals(last) && !"X".equals(last)){
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * 功能描述: <br>
     * 判断是否是合法的电子邮件地址
     *
     * @author 12070681 mashenga@cnsuning.com
     * @param email 电子邮件
     * @return 合法返回true
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean isEmail(String email) {
        String reg = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        return Pattern.matches(reg, email);
    }

    /**
     * 
     * 功能描述: <br>
     * 检查是不是合法的全国客服电话
     *
     * @author 12070681 mashenga@cnsuning.com
     * @param phone 需要校验的字符串
     * @return 合法返回true
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean isServicePhone(String phone) {
        String reg = "^(400|800)(-)?[0-9]{7}$";
        return Pattern.matches(reg, phone);
    }
    
    /**
     * 判断是否含有特殊字符 <br>
     * 〈功能详细描述〉
     * 
     * @param str
     * @return
     * @author 13072984 叶青
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean isStrSpecial(String str) {
        String reg = "[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！%@#￥……&*（）——|{}【】‘；：”“'。，、？]";
        Pattern pattern = Pattern.compile(reg);
        return pattern.matcher(str).find();
    }
    
}
