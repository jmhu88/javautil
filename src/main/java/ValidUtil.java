/*
 * Copyright (C), 2002-2015, 365金融研发中心
 * FileName: //文件名
 * Author:   365
 * Date:     2015年5月1日 下午4:28:02
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */

import java.util.regex.Pattern;

/**
 * 校验工具类
 * 
 * @author hzy
 */
public class ValidUtil {
    
    public static final String phone_rgx = "^1[3|4|5|7|8][0-9]\\d{8}$";

    // 邮箱验证
    public static final Pattern p_email = Pattern
            .compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$");

    // 验证手机号
    public static final Pattern p_tel = Pattern.compile(phone_rgx);
    
    // 特殊字符校验
    public static final Pattern CHARACTER_RGX = Pattern
            .compile("[^(`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？)]+");

}
