import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 有关字符串的工具类
 *
 * @author pengpeng
 * @date 2013-8-4 上午12:23:11
 * @version 1.0
 */
public class StringUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtils.class);

    public static final String DEFAULT_EMPTY_STRING = "";

    public static final String DEFAULT_NULL_STRING = "null";

    /**
     * 判断字符串是否为空值(null, "", " ", "null")
     * 
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return str == null || DEFAULT_EMPTY_STRING.equals(str.trim()) || DEFAULT_NULL_STRING.equals(str.trim());
    }

    /**
     * 判断字符串是否不为空值
     * 
     * @param str
     * @return
     */
    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    /**
     * 如果字符串为空值(null, "", " ", "null"),则将其转换为空串""
     * 
     * @param str
     * @return
     */
    public static String notEmpty(String str) {
        if (isEmpty(str)) {
            return DEFAULT_EMPTY_STRING;
        }
        return str.trim();
    }

    /**
     * 如果字符串为空值(null, "", " ", "null"),则将其转换为默认值defaults
     * 
     * @param str
     * @param defaults
     * @return
     */
    public static String defaultEmpty(String str, String defaults) {
        if (isEmpty(str)) {
            return defaults;
        } else {
            return str;
        }
    }

    /**
     * 判断输入字符是否为阿拉伯字母
     * 
     * @param cs
     * @return
     */
    public static boolean isAlpha(CharSequence cs) {
        if (cs == null || cs.length() == 0) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isLetter(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符是否为数字[0-9](注：不包含小数点.)
     * 
     * @param cs
     * @return
     */
    public static boolean isNumeric(CharSequence cs) {
        if (cs == null || cs.length() == 0) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断字符是否为数字(注：包含小数点.)
     * 
     * @param cs
     * @return
     */
    public static boolean isDigit(String str) {
        Pattern p = Pattern.compile("\\d+\\.?\\d*");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    /**
     * 判断字符是否为字母或者数字[a-zA-Z0-9]
     * 
     * @param cs
     * @return
     */
    public static boolean isAlphanumeric(CharSequence cs) {
        if (cs == null || cs.length() == 0) {
            return false;
        }
        int sz = cs.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isLetterOrDigit(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * 在目标字符串targetStr左边补充字符appendChar,使得目标字符串的总长度达到length
     * (注：targetStr为null时返回null)
     * 
     * @param targetStr
     * @param appendChar
     * @param length
     * @return
     */
    public static String lPad(String targetStr, char appendChar, int length) {
        if (targetStr == null) {
            return null;
        }
        int len = targetStr.length();
        while (len++ < length) {
            targetStr = appendChar + targetStr;
        }
        return targetStr;
    }

    /**
     * 在目标字符串targetStr右边补充字符appendChar,使得目标字符串的总长度达到length
     * (注：targetStr为null时返回null)
     * 
     * @param targetStr
     * @param appendChar
     * @param length
     * @return
     */
    public static String rPad(String targetStr, char appendChar, int length) {
        if (targetStr == null) {
            return null;
        }
        int len = targetStr.length();
        while (len++ < length) {
            targetStr += appendChar;
        }
        return targetStr;
    }

    /**
     * 分别从目标字符串中的两端剔除需要剔除的字符串stripChars
     * 
     * <pre>
     * StringUtils.strip(null, *)          = null
     * StringUtils.strip("", *)            = ""
     * StringUtils.strip("abc", null)      = "abc"
     * StringUtils.strip("  abc", null)    = "abc"
     * StringUtils.strip("abc  ", null)    = "abc"
     * StringUtils.strip(" abc ", null)    = "abc"
     * StringUtils.strip("  abcyx", "xyz") = "  abc"
     * </pre>
     * 
     * @param str
     * @param stripChars
     * @return
     */
    public static String strip(String str, String stripChars) {
        if (isEmpty(str)) {
            return str;
        }
        str = stripStart(str, stripChars);
        return stripEnd(str, stripChars);
    }

    /**
     * 从目标字符串中的起始端开始剔除需要剔除的字符串stripChars
     * 
     * <pre>
     * StringUtils.stripStart(null, *)          = null
     * StringUtils.stripStart("", *)            = ""
     * StringUtils.stripStart("abc", "")        = "abc"
     * StringUtils.stripStart("abc", null)      = "abc"
     * StringUtils.stripStart("  abc", null)    = "abc"
     * StringUtils.stripStart("abc  ", null)    = "abc  "
     * StringUtils.stripStart(" abc ", null)    = "abc "
     * StringUtils.stripStart("yxabc  ", "xyz") = "abc  "
     * </pre>
     * 
     * @param str
     * @param stripChars
     * @return
     */
    public static String stripStart(String str, String stripChars) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return str;
        }
        int start = 0;
        if (stripChars == null) {
            while (start != strLen && Character.isWhitespace(str.charAt(start))) {
                start++;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while (start != strLen && stripChars.indexOf(str.charAt(start)) != -1) {
                start++;
            }
        }
        return str.substring(start);
    }

    /**
     * 生成随机数字
     * 
     * @param length
     * @return
     */
    public static String getRandomNumber(int length) {
        String str = "0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(9);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 从目标字符串中的末端开始剔除需要剔除的字符串stripChars
     * 
     * <pre>
     * StringUtils.stripEnd(null, *)          = null
     * StringUtils.stripEnd("", *)            = ""
     * StringUtils.stripEnd("abc", "")        = "abc"
     * StringUtils.stripEnd("abc", null)      = "abc"
     * StringUtils.stripEnd("  abc", null)    = "  abc"
     * StringUtils.stripEnd("abc  ", null)    = "abc"
     * StringUtils.stripEnd(" abc ", null)    = " abc"
     * StringUtils.stripEnd("  abcyx", "xyz") = "  abc"
     * StringUtils.stripEnd("120.00", ".0")   = "12"
     * </pre>
     * 
     * @param str
     * @param stripChars
     * @return
     */
    public static String stripEnd(String str, String stripChars) {
        int end;
        if (str == null || (end = str.length()) == 0) {
            return str;
        }

        if (stripChars == null) {
            while (end != 0 && Character.isWhitespace(str.charAt(end - 1))) {
                end--;
            }
        } else if (stripChars.length() == 0) {
            return str;
        } else {
            while (end != 0 && stripChars.indexOf(str.charAt(end - 1)) != -1) {
                end--;
            }
        }
        return str.substring(0, end);
    }

    public static String trimAllWhitespace(String str) {
        if (str == null) {
            return null;
        }
        return str.replaceAll(" ", DEFAULT_EMPTY_STRING);
    }

    /**
     * 随机生成字符
     * 
     * @param length
     * @return
     * @author 邱刘军
     */
    public static String getRandomChars(int length) {
        String str = "0123456789ABCDEFGHIJKLMNPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int number = random.nextInt(35);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 
     * 判断字符串是否为空[null,""," "]
     * 
     * @param str
     * @return
     * @author hzy
     */
    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 
     * 判断字符串是否非空[null,""," "]
     * 
     * @param str
     * @return
     * @author hzy
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 处理String参数，如果str是空，则返回null
     * 
     * @param str
     * @return
     * @author hzy
     */
    public static String ckStr(String str) {
        if (isNotBlank(str)) {
            return str.trim();
        }
        return null;
    }

    /**
     * 
     * 处理Integer参数，如果str是空，则返回null
     * 
     * @param str
     * @return
     * @author hzy
     */
    public static Integer ckInt(String str) {
        if (isBlank(str)) {
            return null;
        }
        try {
            return Integer.parseInt(str.trim());
        } catch (NumberFormatException e) {
            LOGGER.error("String > Integer error, source[" + str + "]", e);
            return null;
        }
    }

    /**
     * 
     * 处理Long参数，如果str是空，则返回null
     * 
     * @param str
     * @return
     * @author hzy
     */
    public static Long ckLong(String str) {
        if (isBlank(str)) {
            return null;
        }
        try {
            return Long.parseLong(str.trim());
        } catch (NumberFormatException e) {
            LOGGER.error("String > Long error, source[" + str + "]", e);
            return null;
        }
    }

    /**
     * 
     * 处理BigDecimal参数，如果str是空，则返回null
     * 
     * @param str
     * @return
     * @author hzy
     */
    public static BigDecimal ckDecimal(String str) {
        if (isBlank(str)) {
            return null;
        }
        try {
            return new BigDecimal(str.trim());
        } catch (Exception e) {
            LOGGER.error("String > BigDecimal error, source[" + str + "]", e);
            return null;
        }
    }

    /**
     * 处理成Boolean型
     * 
     * @param str
     * @return
     */
    public static Boolean ckBoolean(String str) {
        if (isBlank(str)) {
            return null;
        }
        if ("1".equals(str)) {
            return true;
        }
        if ("0".equals(str)) {
            return false;
        }
        try {
            return Boolean.valueOf(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 处理Timestamp参数，如果str是空，则返回null
     * 
     * @param str
     * @return
     * @author hzy
     */
    public static Timestamp ckTime(String str) {
        if (isBlank(str) || str.indexOf("null") != -1 || str.startsWith(" ")) {
            return null;
        }
        try {
            return Timestamp.valueOf(str);
        } catch (IllegalArgumentException e) {
            LOGGER.error("String > Timestamp error, source[" + str + "]", e);
            return null;
        }
    }

    /**
     * MD5加密
     * 
     * @param input
     * @return
     */
    public static String md5Encrypt(String input) {
        return DigestUtils.md5Hex("@com.house365$anjd_" + input);
    }

    /**
     * 
     * 给名称加匿名 *
     * 
     * @param name
     * @return
     */
    public static String fixName(String name) {
        String newName = "";
        if (!StringUtils.isEmpty(name)) {
            newName = name.substring(0, 1) + "**";

        }
        return newName;
    }
    
    /**
     * 
     * 给名称加匿名 *
     * 
     * @param name
     * @return 例如    "a"     "a**"
     * 			    "ab"    "a**b"
     * 			    "abc"   "a**c"
     */
    public static String fixNameForInvestDetail(String name){
    	if (name != null && name.length() > 0) {
        	String nickNameEnd = "";
        	// 根据产品要求 更新投资记录 投资人隐位规则
        	if(name.length()>=2){
        		nickNameEnd  = name.substring(name.length()-1, name.length());
        	}
            return(name.substring(0, 1) + "**"+nickNameEnd);
        }
    	return name;
    }

    /**
     * Java正则 HTML 标签 得到 纯文本
     *
     * @param inputString
     * @return
     */
    public static String Html2Text(String inputString) {
        /**
         * String htmlStr = inputString; //含html标签的字符串 String textStr ="";
         * java.util.regex.Pattern p_script; java.util.regex.Matcher m_script;
         * java.util.regex.Pattern p_style; java.util.regex.Matcher m_style;
         * java.util.regex.Pattern p_img; java.util.regex.Matcher m_img;
         * java.util.regex.Pattern p_html; java.util.regex.Matcher m_html; try{
         * String regEx_script =
         * "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
         * //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> } String
         * regEx_style =
         * "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
         * //定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style> } String
         * regEx_html = "<[^>]+>"; //定义HTML标签的正则表达式 String regEx_img =
         * "<\\s*img\\s+([^>]+)\\s*>";
         * //定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script> } p_script =
         * Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); m_script =
         * p_script.matcher(htmlStr); htmlStr = m_script.replaceAll("");
         * //过滤script标签
         * 
         * p_style = Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE);
         * m_style = p_style.matcher(htmlStr); htmlStr = m_style.replaceAll("");
         * //过滤style标签
         * 
         * p_img = Pattern.compile(regEx_img,Pattern.CASE_INSENSITIVE); m_img =
         * p_img.matcher(htmlStr); htmlStr = m_img.replaceAll(""); //过滤style标签
         * 
         * p_html = Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); m_html
         * = p_html.matcher(htmlStr); htmlStr = m_html.replaceAll("");
         * //过滤html标签
         * 
         * textStr = htmlStr; }catch(Exception e){ } return textStr;//返回文本字符串
         **/

        String textStr = "";
        if (inputString == null) {
            return "";
        }
        try {
            String regEx_html = "(<[^>]+>)";
            Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            Matcher matcher_html = p_html.matcher(inputString);
            textStr = matcher_html.replaceAll("").trim();
        } catch (Exception e) {
            LOGGER.error("Java正则 HTML 标签 得到 纯文本错误", e);
            return "";
            // TODO Auto-generated catch block
        }
        return textStr;
    }

    /**
     * 数组转字符串
     * @param array
     * @return
     */
    public static String join(Object array[]) {
        return join(array, ",");
    }

    public static String join(Object array[], String separator) {
        if (array == null)
            return null;
        else
            return join(array, separator, 0, array.length);
    }

    public static String join(Object array[], String separator, int startIndex, int endIndex) {
        if (array == null)
            return null;
        if (separator == null)
            separator = "";
        int bufSize = endIndex - startIndex;
        if (bufSize <= 0)
            return "";
        bufSize *= (array[startIndex] != null ? array[startIndex].toString().length() : 16) + separator.length();
        StringBuilder buf = new StringBuilder(bufSize);
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex)
                buf.append(separator);
            if (array[i] != null)
                buf.append(array[i]);
        }

        return buf.toString();
    }
    
    /**
     * 随机生成字母数字组合  length至少大于2
     * 
     * @param length
     * @return
     * @author chenl
     */
    public static String getRandomCharsAndNum(int length) {
        if(length <2){
            return "";
        }
        
        String str1 = getRandomChars(length-2);
        str1=str1+getRandomNumber(1);
        Random random = new Random();
        String str2 = "ABCDEFGHIJKLMNPQRSTUVWXYZ";
        str1=str1+String.valueOf(str2.charAt(random.nextInt(24)));
     
        return str1;
    }
    
    /**
     * 
     * 统计某个字符  在字符串中出现的次数
     * @param in
     * @param s
     * @return
     */
    public static int countNoRepeat(String in, String s){
        if(null==in ||s.length() > in.length()){
        return 0;
        }
        if(in.startsWith(s)){
        return 1 + countNoRepeat(in.substring(s.length()), s);
        }else{
        return countNoRepeat(in.substring(1), s);
        }
        }
    


}
