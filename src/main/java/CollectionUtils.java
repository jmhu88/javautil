import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 集合类型的工具类
 *
 * @author pengpeng
 * @date 2013-8-4 上午12:51:44
 * @version 1.0
 */
public class CollectionUtils {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CollectionUtils.class);

	public static boolean isEmpty(Collection<?> collection){
		return collection == null || collection.isEmpty();
	}
	
	public static boolean isEmpty(Map<?,?> map){
		return map == null || map.isEmpty();
	}
	
	/** 将一个 JavaBean 对象转化为一个 Map */
    @SuppressWarnings("rawtypes")
    public static Map<String, Object> beanToMap(Object bean, boolean filterNull) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        if (bean != null) {
            Class type = bean.getClass();
            try {
                BeanInfo beanInfo = Introspector.getBeanInfo(type);
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
                for (int i = 0; i < propertyDescriptors.length; i++) {
                    PropertyDescriptor descriptor = propertyDescriptors[i];
                    String propertyName = descriptor.getName();
                    if (!propertyName.equals("class")) {
                        Method readMethod = descriptor.getReadMethod();
                        Object result = readMethod.invoke(bean, new Object[0]);
                        if (result != null) {
                            returnMap.put(propertyName, result);
                        } else if (!filterNull) {
                            returnMap.put(propertyName, result);
                        }
                    }
                }
                LOGGER.debug("result Map [" + returnMap + "]");
            } catch (Exception e) {
                LOGGER.error("bean-to->Map error", e);
            }
        }
        return returnMap;
    }

    /** 将一个 JavaBean 对象转化为一个 Map */
    public static Map<String, Object> beanToMap(Object bean) {
        return beanToMap(bean, true);
    }
	
}
