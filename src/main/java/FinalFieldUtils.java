import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 修改final字段的值工具类
 *  
 * @author pengpeng
 * @date 2013-10-30 下午5:10:28
 * @version 1.0
 */
public class FinalFieldUtils {

	/**
	 * 修改final字段的值
	 * @param targetClass
	 * @param fieldName
	 * @param newValue
	 * @throws Exception
	 */
	public static void setFinalValue(Class<?> targetClass, String fieldName, Object newValue) throws Exception {
		Assert.notNull(targetClass);
		Assert.notNull(fieldName);
		setFinalValue(targetClass.getField(fieldName), newValue);
	}
	
	/**
	 * 修改final字段的值
	 * @param field
	 * @param newValue
	 * @throws Exception
	 */
	public static void setFinalValue(Field field, Object newValue) throws Exception {
		Assert.notNull(field);
		field.setAccessible(true);
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
		field.set(null, newValue);
	}
	
}
