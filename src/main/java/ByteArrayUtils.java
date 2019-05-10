import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * 基本常用类型与字节数组的相互转换工具
 * 
 * @author pengpeng
 * @date 2013-8-4 上午12:48:26
 * @version 1.0
 */
public class ByteArrayUtils {
	
	public static final DateFormat DATE_FORMART = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * byte型1个字节
	 */
	public static final int PRIMITIVE_TYPE_BYTE_LENGTH_BYTE = 1;
	
	/**
	 * short型2个字节
	 */
	public static final int PRIMITIVE_TYPE_BYTE_LENGTH_SHORT = 2;
	
	/**
	 * int型4个字节
	 */
	public static final int PRIMITIVE_TYPE_BYTE_LENGTH_INT = 4;
	
	/**
	 * long型8个字节
	 */
	public static final int PRIMITIVE_TYPE_BYTE_LENGTH_LONG = 8;
	
	/**
	 * float型4个字节
	 */
	public static final int PRIMITIVE_TYPE_BYTE_LENGTH_FLOAT = 4;
	
	/**
	 * byte型数据转byte[]
	 * @param number
	 * @return
	 */
	public static byte[] byteToByteArray(byte number){
		return new byte[]{number};
	}
	
	/**
	 * byte[]型数据转byte
	 * @param bytes
	 * @return
	 */
	public static Byte byteArrayToByte(byte[] bytes){
		return bytes[0];
	}
	
	/**
	 * short型数据转byte[]
	 * @param number
	 * @return
	 */
	public static byte[] shortToByteArray(short number){
		int byteLength = PRIMITIVE_TYPE_BYTE_LENGTH_SHORT;
		byte[] bytes = new byte[byteLength];
		for(int i = 0; i < byteLength; i++){
			bytes[byteLength - i - 1] = (byte)((number >> (8 * i)) & 0xff);
		}
		return bytes;
	}
	
	/**
	 * byte[]型数据转short
	 * @param bytes
	 * @return
	 */
	public static Short byteArrayToShort(byte[] bytes){
		short number = 0;
		int byteLength = bytes.length;
		for(int i = 0; i < byteLength; i++){
			number |= ((short)(bytes[byteLength - i - 1] & 0xff) << (8 * i));
		}
		return number;
	}
	
	/**
	 * int型数据转byte[]
	 * @param number
	 * @return
	 */
	public static byte[] intToByteArray(int number){
		int byteLength = PRIMITIVE_TYPE_BYTE_LENGTH_INT;
		byte[] bytes = new byte[byteLength];
		for(int i = 0; i < byteLength; i++){
			bytes[byteLength - i - 1] = (byte)((number >> (8 * i)) & 0xff);
		}
		return bytes;
	}
	
	/**
	 * byte[]型数据转int
	 * @param bytes
	 * @return
	 */
	public static Integer byteArrayToInt(byte[] bytes){
		int number = 0;
		int byteLength = bytes.length;
		for(int i = 0; i < byteLength; i++){
			number |= ((int)(bytes[byteLength - i - 1] & 0xff) << (8 * i));
		}
		return number;
	}
	
	/**
	 * long型数据转byte[]
	 * @param number
	 * @return
	 */
	public static byte[] longToByteArray(long number){
		int byteLength = PRIMITIVE_TYPE_BYTE_LENGTH_LONG;
		byte[] bytes = new byte[byteLength];
		for(int i = 0; i < byteLength; i++){
			bytes[byteLength - i - 1] = (byte)((number >> (8 * i)) & 0xff);
		}
		return bytes;
	}
	
	/**
	 * byte[]型数据转long
	 * @param bytes
	 * @return
	 */
	public static Long byteArrayToLong(byte[] bytes){
		long number = 0l;
		int byteLength = bytes.length;
		for(int i = 0; i < byteLength; i++){
			number |= ((long)(bytes[byteLength - i - 1] & 0xff) << (8 * i));
		}
		return number;
	}
	
	/**
	 * float型数据转byte[]
	 * @param number
	 * @return
	 */
	public static byte[] floatToByteArray(float number){
		int bits = Float.floatToIntBits(number);
		return intToByteArray(bits);
	}
	
	/**
	 * byte[]型数据转float
	 * @param bytes
	 * @return
	 */
	public static Float byteArrayToFloat(byte[] bytes){
		int bits = byteArrayToInt(bytes);
		return Float.intBitsToFloat(bits);
	}
	
	/**
	 * double型数据转byte[]
	 * @param number
	 * @return
	 */
	public static byte[] doubleToByteArray(double number){
		long bits = Double.doubleToLongBits(number);
		return longToByteArray(bits);
	}
	
	/**
	 * byte[]型数据转double
	 * @param bytes
	 * @return
	 */
	public static Double byteArrayToDouble(byte[] bytes){
		long bits = byteArrayToLong(bytes);
		return Double.longBitsToDouble(bits);
	}
	
	/**
	 * char型数据转byte[]
	 * @param number
	 * @return
	 */
	public static byte[] charToByteArray(char chr){
		return shortToByteArray((short)chr);
	}
	
	/**
	 * byte[]型数据转char
	 * @param bytes
	 * @return
	 */
	public static Character byteArrayToChar(byte[] bytes){
		short s = byteArrayToShort(bytes);
		return (char)s;
	}
	
	/**
	 * String型数据转byte[]
	 * @param number
	 * @return
	 */
	public static byte[] stringToByteArray(String str){
		return str.getBytes();
	}
	
	/**
	 * byte[]型数据转String
	 * @param bytes
	 * @return
	 */
	public static String byteArrayToString(byte[] bytes){
		return new String(bytes);
	}
	
	/**
	 * String型数据转byte[]
	 * @param number
	 * @return
	 */
	public static byte[] stringToByteArray(String str, String charset){
		try {
			return str.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	/**
	 * byte[]型数据转String
	 * @param bytes
	 * @return
	 */
	public static String byteArrayToString(byte[] bytes, String charset){
		try {
			return new String(bytes, charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	public static byte[] dateToByteArray(Date date){
		return null;
	}
	
	public static void main(String[] args) {
		short s = 12321;
		byte[] bytes = shortToByteArray(s);
		System.out.println(Arrays.toString(bytes));
		System.out.println(s + " , " + byteArrayToShort(bytes));
		System.out.println("-----------------------");
		int i = 819392132;
		bytes = intToByteArray(i);
		System.out.println(Arrays.toString(bytes));
		System.out.println(i + " , " + byteArrayToInt(bytes));
		System.out.println("-----------------------");
		long l = 19392010302132132l;
		bytes = longToByteArray(l);
		System.out.println(Arrays.toString(bytes));
		System.out.println(l + " , " + byteArrayToLong(bytes));
		System.out.println("-----------------------");
		
		double d = 4889.4001;
		bytes = doubleToByteArray(d);
		System.out.println(Arrays.toString(bytes));
		System.out.println(d + " , " + byteArrayToDouble(bytes));
		System.out.println("-----------------------");
		
		char c = '中';
		bytes = charToByteArray(c);
		System.out.println(Arrays.toString(bytes));
		System.out.println(c + " , " + byteArrayToChar(bytes));
		System.out.println("-----------------------");
		
		ByteBuffer buffer = ByteBuffer.allocate(2);
		buffer.putChar('中');
		System.out.println(Arrays.toString(buffer.array()));
	}

}
