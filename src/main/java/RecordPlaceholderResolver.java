/*
 * Copyright (C), 2002-2014, 365金融研发中心
 * Project: finance-framework
 * PackName: com.house365.finance.framework.util
 * FileName: RecordPlaceholderResolver.java
 * Author: 时林涛
 * Date: 2014年10月27日
 * History:
 */

import java.util.HashMap;
import org.springframework.util.PropertyPlaceholderHelper.PlaceholderResolver;

/**
 * 功能描述： 占位符的替换功能，参数类型Map
 * 
 * @author 时林涛 2014-10-27
 */

public class RecordPlaceholderResolver implements PlaceholderResolver {

    private HashMap<String, String> map;

    public RecordPlaceholderResolver(HashMap<String, String> maps) {
        this.map = maps;
    }

    public String resolvePlaceholder(String placeholderName) {
        try {
            String propVal = map.get(placeholderName).toString();
            return propVal;
        } catch (Throwable ex) {
            System.err.println("Could not resolve placeholder '" + placeholderName + "' in ["
                    + "] as system property: " + ex);
        }
        return null;
    }
}
