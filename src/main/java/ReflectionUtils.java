import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 反射工具类
 *
 * @author pengpeng
 * @date 2013-8-4 下午2:38:22
 * @version 1.0
 */
public class ReflectionUtils {
	
	/**
	 * 根据属性字段名称获取Field
	 * @param clazz
	 * @param name
	 * @return
	 */
	public static Field findField(Class<?> clazz, String name) {
		Assert.notNull(clazz, "Class must not be null");
		Assert.isTrue(name != null, "Field name must be specified");
		Class<?> searchType = clazz;
		while (!Object.class.equals(searchType) && searchType != null) {
			Field[] fields = searchType.getDeclaredFields();
			for (Field field : fields) {
				if ((name == null || name.equals(field.getName()))) {
					return field;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}
	
	/**
	 * 在目标对象上设置属性字段的值
	 * @param field
	 * @param target
	 * @param value
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void setFieldValue(Field field, Object target, Object value) {
		field.setAccessible(true);
		try {
			field.set(target, value);
		} catch (Exception e) {
			throw new ReflectionException("setting field's value by reflection with an exception, message : " + e.getMessage());
		}
	}
	
	/**
	 * 在目标对象上获取属性字段的值
	 * @param field
	 * @param target
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getFieldValue(Field field, Object target) {
		try {
			field.setAccessible(true);
			return (T) field.get(target);
		} catch (Exception e) {
			throw new ReflectionException("getting field's value by reflection with an exception, message : " + e.getMessage());
		}
	}
	
	/**
	 * 根据方法名字及入参在类中查找方法
	 * @param clazz
	 * @param name
	 * @param paramTypes
	 * @return
	 */
	public static Method findMethod(Class<?> clazz, String name, Class<?>... paramTypes) {
		Assert.notNull(clazz, "Class must not be null");
		Assert.notNull(name, "Method name must not be null");
		Class<?> searchType = clazz;
		while (searchType != null) {
			Method[] methods = (searchType.isInterface() ? searchType.getMethods() : searchType.getDeclaredMethods());
			for (Method method : methods) {
				if (name.equals(method.getName())
						&& (paramTypes == null || Arrays.equals(paramTypes, method.getParameterTypes()))) {
					return method;
				}
			}
			searchType = searchType.getSuperclass();
		}
		return null;
	}
	
	/**
	 * 根据方法名字在类中查找其无参方法
	 * @param clazz
	 * @param name
	 * @return
	 */
	public static Method findMethod(Class<?> clazz, String name) {
		return findMethod(clazz, name, new Class[0]);
	}
	
	public static Object invokeMethod(Method method, Object target) {
		return invokeMethod(method, target, new Object[0]);
	}

	/**
	 * 调用类的特定方法
	 * @param method	- Method方法对象
	 * @param target	- 方法所属目标对象
	 * @param args		- 方法入参
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public static Object invokeMethod(Method method, Object target, Object... args) {
		try {
			method.setAccessible(true);
			return method.invoke(target, args);
		} catch (Exception e) {
			throw new ReflectionException("invoke method by reflection with an exception, message : " + e.getMessage());
		}
	}
	
	static class ReflectionException extends RuntimeException {

		private static final long serialVersionUID = 1L;

		public ReflectionException(String message, Throwable cause) {
			super(message, cause);
		}

		public ReflectionException(String message) {
			super(message);
		}

		public ReflectionException(Throwable cause) {
			super(cause);
		}
		
	}
	
}