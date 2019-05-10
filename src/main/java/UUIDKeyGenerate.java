/*
 * Copyright (C), 2002-2014, 三六五网络金融
 * FileName: UUIDKeyGenerate.java
 * Author:   258737400@qq.com
 * Date:     2013-5-20 上午11:39:25
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */

import java.util.UUID;

/**
 * 主键UUID生成工具类
 *
 * @author 258737400@qq.com
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class UUIDKeyGenerate {
    public static String getUUIDKey(){
        return UUID.randomUUID().toString().replace("-","");
    }
    
    /**
     * 
     * 功能描述: <br>
     * 判断字符串是否为数字
     * 
     * @author 12060945@cnsuning.com
     * @param params 
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static boolean isNum(String msg){
    	if(Character.isDigit(msg.charAt(0))){
    		return true;
    	}
    	return false;
    }
    
}