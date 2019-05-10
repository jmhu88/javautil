import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;

/**
 * 有关Object的工具类
 *
 * @author pengpeng
 * @date 2013-8-4 下午2:33:53
 * @version 1.0
 */
public class ObjectUtils {

	/**
	 * 判断对象是否为空
	 * null				-> true
	 * ""				-> true
	 * " "				-> true
	 * "null"			-> true
	 * empty Collection	-> true
	 * empty Array		-> true
	 * others			-> false
	 * @param object
	 * @return
	 */
	public static boolean isEmpty(Object object){
		if(object == null){
			return true;
		}else if(object instanceof String){
			return ((String)object).trim().equals("") || ((String)object).trim().equals("null");
		}else if(object instanceof Collection<?> || object instanceof Map<?,?>){
			return ((Collection<?>)object).isEmpty();
		}else if(object.getClass().isArray()){
			return Array.getLength(object) == 0;
		}else{
			return false;
		}
	}
	
	public static boolean isArray(Object obj) {
		return (obj != null && obj.getClass().isArray());
	}
	
	public static boolean isCheckedException(Throwable ex) {
		return !(ex instanceof RuntimeException || ex instanceof Error);
	}
	
}
