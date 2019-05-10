/**
 * 有关数组的工具类
 * 
 * @author pengpeng
 * @date 2013-8-4 上午12:58:09
 * @version 1.0
 */
public class ArrayUtils {

	public static boolean isEmpty(byte[] array){
		return array == null || array.length == 0;
	}
	
	public static boolean isEmpty(short[] array){
		return array == null || array.length == 0;
	}
	
	public static boolean isEmpty(int[] array){
		return array == null || array.length == 0;
	}
	
	public static boolean isEmpty(long[] array){
		return array == null || array.length == 0;
	}
	
	public static boolean isEmpty(float[] array){
		return array == null || array.length == 0;
	}
	
	public static boolean isEmpty(double[] array){
		return array == null || array.length == 0;
	}
	
	public static boolean isEmpty(char[] array){
		return array == null || array.length == 0;
	}
	
	public static boolean isEmpty(boolean[] array){
		return array == null || array.length == 0;
	}
	
	public static boolean isEmpty(Object[] array){
		return array == null || array.length == 0;
	}
	
	public static Byte[] toObjectArray(byte[] array){
		if(isEmpty(array)){
			return null;
		}else{
			Byte[] result = new Byte[array.length];
			for(int i = 0; i < array.length; i++){
				result[i] = array[i];
			}
			return result;
		}
	}
	
	public static Short[] toObjectArray(short[] array){
		if(isEmpty(array)){
			return null;
		}else{
			Short[] result = new Short[array.length];
			for(int i = 0; i < array.length; i++){
				result[i] = array[i];
			}
			return result;
		}
	}
	
	public static Integer[] toObjectArray(int[] array){
		if(isEmpty(array)){
			return null;
		}else{
			Integer[] result = new Integer[array.length];
			for(int i = 0; i < array.length; i++){
				result[i] = array[i];
			}
			return result;
		}
	}
	
	public static Long[] toObjectArray(long[] array){
		if(isEmpty(array)){
			return null;
		}else{
			Long[] result = new Long[array.length];
			for(int i = 0; i < array.length; i++){
				result[i] = array[i];
			}
			return result;
		}
	}
	
	public static Float[] toObjectArray(float[] array){
		if(isEmpty(array)){
			return null;
		}else{
			Float[] result = new Float[array.length];
			for(int i = 0; i < array.length; i++){
				result[i] = array[i];
			}
			return result;
		}
	}
	
	public static Double[] toObjectArray(double[] array){
		if(isEmpty(array)){
			return null;
		}else{
			Double[] result = new Double[array.length];
			for(int i = 0; i < array.length; i++){
				result[i] = array[i];
			}
			return result;
		}
	}
	
	public static Character[] toObjectArray(char[] array){
		if(isEmpty(array)){
			return null;
		}else{
			Character[] result = new Character[array.length];
			for(int i = 0; i < array.length; i++){
				result[i] = array[i];
			}
			return result;
		}
	}
	
	public static Boolean[] toObjectArray(boolean[] array){
		if(isEmpty(array)){
			return null;
		}else{
			Boolean[] result = new Boolean[array.length];
			for(int i = 0; i < array.length; i++){
				result[i] = array[i];
			}
			return result;
		}
	}
	
	public static byte[] toPrimitiveArray(Byte[] array){
		if(isEmpty(array)){
			return null;
		}else{
			byte[] result = new byte[array.length];
			for(int i = 0; i < array.length; i++){
				result[i] = array[i];
			}
			return result;
		}
	}
	
	public static short[] toPrimitiveArray(Short[] array){
		if(isEmpty(array)){
			return null;
		}else{
			short[] result = new short[array.length];
			for(int i = 0; i < array.length; i++){
				result[i] = array[i];
			}
			return result;
		}
	}
	
	public static int[] toPrimitiveArray(Integer[] array){
		if(isEmpty(array)){
			return null;
		}else{
			int[] result = new int[array.length];
			for(int i = 0; i < array.length; i++){
				result[i] = array[i];
			}
			return result;
		}
	}
	
	public static long[] toPrimitiveArray(Long[] array){
		if(isEmpty(array)){
			return null;
		}else{
			long[] result = new long[array.length];
			for(int i = 0; i < array.length; i++){
				result[i] = array[i];
			}
			return result;
		}
	}
	
	public static float[] toPrimitiveArray(Float[] array){
		if(isEmpty(array)){
			return null;
		}else{
			float[] result = new float[array.length];
			for(int i = 0; i < array.length; i++){
				result[i] = array[i];
			}
			return result;
		}
	}
	
	public static double[] toPrimitiveArray(Double[] array){
		if(isEmpty(array)){
			return null;
		}else{
			double[] result = new double[array.length];
			for(int i = 0; i < array.length; i++){
				result[i] = array[i];
			}
			return result;
		}
	}
	
	public static char[] toPrimitiveArray(Character[] array){
		if(isEmpty(array)){
			return null;
		}else{
			char[] result = new char[array.length];
			for(int i = 0; i < array.length; i++){
				result[i] = array[i];
			}
			return result;
		}
	}
	
	public static boolean[] toPrimitiveArray(Boolean[] array){
		if(isEmpty(array)){
			return null;
		}else{
			boolean[] result = new boolean[array.length];
			for(int i = 0; i < array.length; i++){
				result[i] = array[i];
			}
			return result;
		}
	}
	
	/**
	 * 字符串数组转换成字符串输出
	 * 
	 * @param attr
	 * @return
	 */
	public static String toString(String[] attr){
	    StringBuffer sb = new StringBuffer();
	    if(attr == null || attr.length == 0) {
	        return sb.toString();
	    }
	    for (int i = 0; i < attr.length; i++) {
	        sb.append(attr[i]);
	        if(i != attr.length - 1) {
	            sb.append(",");
	        }
        }
	    return sb.toString();
	}
}
