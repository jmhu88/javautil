import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 日期时间工具类
 *
 * @author pengpeng
 * @date 2013-8-4 下午3:38:00
 * @version 1.0
 */
public class DateTimeUtils {

    public static final String DEFAULT_DATE_FORMAT_PATTERN_SHORT = "yyyy-MM-dd";

    public static final String DEFAULT_DATE_FORMAT_PATTERN_FULL = "yyyy-MM-dd HH:mm:ss";
    
    public static final String DATE_FORMAT_YYYYMM = "yyyy-MM";

    private static Map<String, SimpleDateFormat> dateFormatCache = new ConcurrentHashMap<String, SimpleDateFormat>();

    /**
     * 以yyyy-MM-dd HH:mm:ss形式返回当前时间的字符串
     *
     * @return
     */
    public static String getCurrentDateTimeString() {
        return getCurrentDateString(DEFAULT_DATE_FORMAT_PATTERN_FULL);
    }

    /**
     * 以yyyy-MM-dd形式返回当前日期的字符串
     *
     * @return
     */
    public static String getCurrentDateString() {
        return getCurrentDateString(DEFAULT_DATE_FORMAT_PATTERN_SHORT);
    }

    /**
     * 返回pattern所指定的当前时间的字符串
     *
     * @param pattern
     * @return
     */
    public static String getCurrentDateString(String pattern) {
        if (pattern == null || "".equals(pattern.trim())) {
            return null;
        }
        SimpleDateFormat sdf = null;
        if (dateFormatCache.containsKey(pattern)) {
            sdf = dateFormatCache.get(pattern);
        } else {
            try {
                sdf = new SimpleDateFormat(pattern);
                dateFormatCache.put(pattern, sdf);
            } catch (Exception e) {
                e.printStackTrace();
                sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT_PATTERN_FULL);
            }
        }
        return sdf.format(new Date());
    }

    /**
     * 返回时间date所指定的日期格式的字符串形式
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String getDateString(Date date, String pattern) {
        if (date == null || pattern == null || "".equals(pattern.trim())) {
            return null;
        }
        SimpleDateFormat sdf = null;
        if (dateFormatCache.containsKey(pattern)) {
            sdf = dateFormatCache.get(pattern);
        } else {
            try {
                sdf = new SimpleDateFormat(pattern);
                dateFormatCache.put(pattern, sdf);
            } catch (Exception e) {
                e.printStackTrace();
                sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT_PATTERN_FULL);
            }
        }
        return sdf.format(date);
    }

    /**
     * 把Date按照yyyy-MM-dd格式，返回
     *
     * @param date
     * @return
     */
    public static String getShortDateString(Date date) {
        return getDateString(date, DEFAULT_DATE_FORMAT_PATTERN_SHORT);
    }

    public static String getFullDateString(Date date) {
        return getDateString(date, DEFAULT_DATE_FORMAT_PATTERN_FULL);
    }

    /**
     * 将dateTimeString按照格式pattern转换成Date
     *
     * @param dateTimeString
     * @param pattern
     * @return
     */
    public static Date getDateByString(String dateTimeString, String pattern) {
        if (dateTimeString == null || "".equals(dateTimeString.trim()) || pattern == null || "".equals(pattern.trim())) {
            return null;
        }
        SimpleDateFormat sdf = null;
        if (dateFormatCache.containsKey(pattern)) {
            sdf = dateFormatCache.get(pattern);
        } else {
            sdf = new SimpleDateFormat(pattern);
            dateFormatCache.put(pattern, sdf);
        }
        try {
            return sdf.parse(dateTimeString);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将dateTimeString按照默认格式yyyy-MM-dd HH:mm:ss转换成Date
     *
     * @param dateTimeString
     * @return
     */
    public static Date getDateByString(String dateTimeString) {
        return getDateByString(dateTimeString, DEFAULT_DATE_FORMAT_PATTERN_FULL);
    }
    
    /**
     * 将dateTimeString按照默认格式yyyy-MM-dd转换成Date
     *
     * @param dateTimeString
     * @return
     */
    public static Date getShortDateByString(String dateTimeString) {
        return getDateByString(dateTimeString, DEFAULT_DATE_FORMAT_PATTERN_SHORT);
    }

    /**
     *
     * 时间加减方法,根据type类型，加减value
     *
     * @param date
     * @param type
     *            操作对象，年/月/日/时/分/秒，eg：Calendar.MONTH 月，Calendar.DAY_OF_MONTH 天
     * @param value
     *            更新值
     * @return
     */
    public static Date getUpdateDateTime(Date date, Integer type, Integer value) {
        if (null == date || null == value || null == type) {
            return null;
        }
        try {
            Calendar calendar = Calendar.getInstance();// 日历对象
            calendar.setTime(date);// 设置当前日期
            calendar.add(type, value);// 时间更改
            return calendar.getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 日期转化为大写
     *
     * @param date
     * @return
     */
    public static String dataToUpper(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH) + 1;
        int day = ca.get(Calendar.DAY_OF_MONTH);
        return numToUpper(year) + "年" + monthToUppder(month) + "月" + dayToUppder(day) + "日";
    }

    /**
     * 日期转化为年月日
     *
     * @param date
     * @return
     */
    public static String dataToFormat(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH) + 1;
        int day = ca.get(Calendar.DAY_OF_MONTH);
        return year + "年" + month + "月" + day + "日";
    }

    /**
     * 日期转化为大写
     *
     * @param date
     * @return
     */
    public static String monthDayToUpper(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        int month = ca.get(Calendar.MONTH) + 1;
        int day = ca.get(Calendar.DAY_OF_MONTH);
        return month + "月" + day + "日";
    }

    /**
     * 将数字转化为大写
     *
     * @param num
     * @return
     */
    public static String numToUpper(int num) {
        // String u[] = {"零","壹","贰","叁","肆","伍","陆","柒","捌","玖"};
        // String u[] = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String u[] = { "〇", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        char[] str = String.valueOf(num).toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            sb.append(u[Integer.parseInt(str[i] + "")]);
        }
        return sb.toString();
    }

    /**
     * 月转化为大写 〈功能详细描述〉
     *
     * @param month
     * @return
     */
    public static String monthToUppder(int month) {
        if (month < 10) {
            return numToUpper(month);
        } else if (month == 10) {
            return "十";
        } else {
            return "十" + numToUpper(month - 10);
        }
    }

    /**
     * 日转化为大写
     *
     * @param day
     * @return
     */
    public static String dayToUppder(int day) {
        if (day < 20) {
            return monthToUppder(day);
        } else {
            char[] str = String.valueOf(day).toCharArray();
            if (str[1] == '0') {
                return numToUpper(Integer.parseInt(str[0] + "")) + "十";
            } else {
                return numToUpper(Integer.parseInt(str[0] + "")) + "十" + numToUpper(Integer.parseInt(str[1] + ""));
            }
        }
    }

    /**
     *
     * 校验指定的字符串是否为正确的时间类型
     *
     * @param date
     * @param format
     *            需要校验的格式,默认为yyyy-MM-dd
     * @return
     */
    public static boolean isDate(String date, String format) {
        if (StringUtils.isEmpty(format)) {
            format = DEFAULT_DATE_FORMAT_PATTERN_SHORT;
        }
        if (StringUtils.isEmpty(date)) {
            return false;
        }
        SimpleDateFormat check = new SimpleDateFormat(format);
        try {
            check.setLenient(false);
            check.parse(date);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }

    /**
     * 拿到默认格式的SimpleDateFormat
     *
     * @return
     */
    public static SimpleDateFormat getSimpleDateFormat() {
        return getSimpleDateFormat(null);
    }

    /**
     * 拿到指定输出格式的SimpleDateFormat
     *
     * @param format
     * @return
     */
    public static SimpleDateFormat getSimpleDateFormat(String format) {
        SimpleDateFormat sdf;
        if (StringUtils.isEmpty(format)) {
            sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT_PATTERN_SHORT);
        } else {
            sdf = new SimpleDateFormat(format);
        }

        return sdf;
    }

    /**
     * 转换默认格式的时间为Date
     *
     * @param dateStr
     * @return
     */
    public static Date formatStr2Date(String dateStr) {
        return formatStr2Date(dateStr, null);
    }

    /**
     * 转换指定格式的时间为Date
     *
     * @param dateStr
     * @param format
     * @return
     */
    public static Date formatStr2Date(String dateStr, String format) {
        if (StringUtils.isEmpty(dateStr)) {
            return null;
        }
        if (StringUtils.isEmpty(format)) {
            format = DEFAULT_DATE_FORMAT_PATTERN_SHORT;
        }
        SimpleDateFormat sdf = getSimpleDateFormat(format);
        return sdf.parse(dateStr, new ParsePosition(0));
    }

    /**
     *
     * 计算两个日期直接差的天数，包括时分秒 2015-1-1 08:40到2015-1-2 08:39则相差0天
     *
     * @param firstDate
     *            较小的日期
     * @param secondDate
     *            较大的日期
     * @return
     */
    public static int caculate2Days(Date firstDate, Date secondDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(firstDate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(secondDate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate
     *            较小的时间
     * @param bdate
     *            较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获得收益时间(获取当前天-1天，周末,节假日不算).
     *
     * @param date
     *            任意日期
     * @return the income date
     * @throws NullPointerException
     *             if null == date
     */
    public static Date getIncomeDate(Date date) throws NullPointerException {
        if (null == date) {
            throw new NullPointerException("the date is null or empty!");
        }

        // 对日期的操作,我们需要使用 Calendar 对象
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);

        // -1天
        calendar.add(Calendar.DAY_OF_MONTH, -1);

        Date incomeDate = calendar.getTime();

        if (isHoliday(calendar)) {
            // 递归
            return getIncomeDate(incomeDate);
        }
        return incomeDate;
    }

    /**
     * 判断一个日历是不是周末.
     *
     * @param calendar
     *            the calendar
     * @return true, if checks if is weekend
     */
    private static boolean isWeekend(Calendar calendar) {
        // 判断是星期几
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);

        if (dayOfWeek == 1 || dayOfWeek == 7) {
            return true;
        }
        return false;
    }

    /**
     * 一个日历是不是节假日.
     *
     * @param calendar
     *            the calendar
     * @return true, if checks if is holiday
     */
    private static boolean isHoliday(Calendar calendar) {
        String pattern = "yyyy-MM-dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String dateString = simpleDateFormat.format(calendar.getTime());

        // 节假日 这个可能不同地区,不同年份 都有可能不一样,所以需要有个地方配置, 可以放数据库, 配置文件,环境变量 等等地方
        // 这里以配置文件 为例子
        ResourceBundle resourceBundle = ResourceBundle.getBundle("holidayConfig");
        String holidays = resourceBundle.getString("holiday");

        String[] holidayArray = holidays.split(",");

        boolean isHoliday = org.apache.commons.lang.ArrayUtils.contains(holidayArray, dateString);
        return isHoliday;
    }

    /**
     * 得到两日期相差几个月
     *
     * @return
     */
    public static long getMonth(Date startDate, Date endDate) {
        long monthday;
        try {
            startDate = getDateByString(getShortDateString(startDate), DEFAULT_DATE_FORMAT_PATTERN_SHORT);
            endDate = getDateByString(getShortDateString(endDate), DEFAULT_DATE_FORMAT_PATTERN_SHORT);

            Calendar starCal = Calendar.getInstance();
            starCal.setTime(startDate);
            int sYear = starCal.get(Calendar.YEAR);
            int sMonth = starCal.get(Calendar.MONTH);

            Calendar endCal = Calendar.getInstance();
            endCal.setTime(endDate);
            int eYear = endCal.get(Calendar.YEAR);
            int eMonth = endCal.get(Calendar.MONTH);
            
            monthday = ((eYear - sYear) * 12 + (eMonth - sMonth)) ;
            if (monthday >0) {
                starCal.add(Calendar.MONTH, (int)monthday);// 时间更改
                if (endCal.before(starCal)){
                    monthday = monthday -1; 
                }
            }
            return monthday;
        } catch (Exception e) {
            monthday = 0;
        }
        return monthday;
    }

    /**
     * 数字转成汉字
     *
     * @param original
     * @return
     */
    public static String cnUpperCaser(String original) {
        // 将数字转化为汉字的数组,因为各个实例都要使用所以设为静态
        final char[] cnNumbers = { '〇', '一', '二', '三', '四', '五', '六', '七', '八', '九' };
        // 供分级转化的数组,因为各个实例都要使用所以设为静态
        final String[] series = { " ", "十", "百", "仟", "万", "十万", "百万", "仟万", "亿" };
        // 整数部分
        String integerPart;
        // 小数部分
        String floatPart = "";
        StringBuffer sb = new StringBuffer();
        if (original.contains(".")) {
            // 如果包含小数点
            int dotIndex = original.indexOf(".");
            integerPart = original.substring(0, dotIndex);
            floatPart = original.substring(dotIndex + 1);
        } else {
            // 不包含小数点
            integerPart = original;
        }
        // 整数部分处理
        for (int i = 0; i < integerPart.length(); i++) {
            int number = getNumber(integerPart.charAt(i));
            if (number == 0 && sb.lastIndexOf("〇") == -1 && i != (integerPart.length() - 1)) {
                sb.append("〇");
            } else if (number != 0) {
                sb.append(cnNumbers[number]);
                sb.append(series[integerPart.length() - 1 - i]);
            }
        }

        // 小数部分处理
        if (floatPart.length() > 0) {
            sb.append("点");
            for (int i = 0; i < floatPart.length(); i++) {
                int number = getNumber(floatPart.charAt(i));
                sb.append(cnNumbers[number]);
            }
        }
        // 返回拼接好的字符串
        return sb.toString().replaceAll(" ", "");
    }

    /**
     * 将字符形式的数字转化为整形数字 因为所有实例都要用到所以用静态修饰
     *
     * @param c
     * @return
     */
    private static int getNumber(char c) {
        String str = String.valueOf(c);
        return Integer.parseInt(str);
    }


}
