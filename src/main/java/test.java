/*
 * Copyright (C), 2002-2014, 365金融研发中心
 * Project: finance-framework
 * PackName: com.house365.finance.framework.util
 * FileName: test.java
 * Author: 时林涛
 * Date: 2014年10月27日
 * History:
 */

import java.util.HashMap;
import org.springframework.util.PropertyPlaceholderHelper;

/**
 * <一句话描述功能>
 * 
 * @author 时林涛
 * @date 2014年10月27日
 * @version 1.0
 */
public class test {
    public static String FINNANCE_PLACEHOLDER_PREFIX = "${";
    public static String FINNANCE_PLACEHOLDER_SUFFIX = "}";
    public static String FINNANCE_DEFAULT = ":";
    
    public static void main(String[] args) {
        String strDate="";
        String PlaceholderResolver="${name},你是人吗${age}";
        HashMap  maps=new HashMap();
        maps.put("name", "时林涛");
        maps.put("age", "时林涛");
    PropertyPlaceholderHelper strictHelper = new PropertyPlaceholderHelper(FINNANCE_PLACEHOLDER_PREFIX, FINNANCE_PLACEHOLDER_SUFFIX, FINNANCE_DEFAULT, true);
    strDate = strictHelper.replacePlaceholders(PlaceholderResolver, (org.springframework.util.PropertyPlaceholderHelper.PlaceholderResolver) new RecordPlaceholderResolver(maps));
    System.out.println(strDate);
    }
}
