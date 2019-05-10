/*
 * Copyright (C), 2002-2014, 365金融研发中心
 * Project: finance-backend
 * PackName: com.house365.finance.framework.util
 * FileName: CustomPropertyConfigurer.java
 * Author: 邱刘军
 * Date: 2014年10月2日 上午9:38:57
 */

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * 自定义属性配置文件加载器
 * 
 * @author 邱刘军
 * @date 2014-09-24
 * @version 1.0
 */
public class CustomPropertyConfigurer extends PropertyPlaceholderConfigurer {

    public static Map<String, String> propertyMap;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
            throws BeansException {
        super.processProperties(beanFactory, props);
        propertyMap = new HashMap<String, String>();
        for (Object key : props.keySet()) {
            String keyStr = key.toString();
            String value = props.getProperty(keyStr);
            propertyMap.put(keyStr, value);
        }
    }

    public static String getMessage(String code) {
        return propertyMap.get(code);
    }

    public static Set<String> getSetMessage(String code, String splitSign) {
        Set<String> set = new HashSet<String>();
        String val = propertyMap.get(code);
        if (StringUtils.isNotBlank(val)) {
            String[] arr = val.split(splitSign);
            for (String s : arr) {
                if (StringUtils.isNotBlank(s)) {
                    set.add(s);
                }
            }
        }
        return set;
    }

    public static String getMessage(String code, String defaultMessage) {
        String value = propertyMap.get(code);
        if (value == null) {
            return defaultMessage;
        }
        return value;
    }

    public static String getMessage(String code, Object[] args) {
        String value = propertyMap.get(code);
        if (value == null) {
            return value;
        }
        String result = value.replaceAll("\\{\\d\\}", "%s");
        return String.format(result, args);
    }

    public static String getMessage(String code, Object[] args, String defaultMessage) {
        String value = propertyMap.get(code);
        if (value == null) {
            return defaultMessage;
        }
        String result = value.replaceAll("\\{\\d\\}", "%s");
        return String.format(result, args);
    }
}
