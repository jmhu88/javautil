import java.awt.Color;

/**
 * 十六进制颜色与java.awt.Color转换工具类
 * 
 * @author pengpeng
 * @date 2013-11-9 上午11:21:51
 * @version 1.0
 */
public class ColorUtils {

	public static final String HEX_COLOR_PREFIX = "#";
	
	/**
	 * java.awt.Color颜色转16进制表示的.
	 * @param color
	 * @return
	 */
	public static String toHexString(Color color) {
		String R, G, B;
		StringBuffer sb = new StringBuffer();
		R = Integer.toHexString(color.getRed());
		G = Integer.toHexString(color.getGreen());
		B = Integer.toHexString(color.getBlue());
		R = R.length() == 1 ? "0" + R : R;
		G = G.length() == 1 ? "0" + G : G;
		B = B.length() == 1 ? "0" + B : B;
		sb.append(HEX_COLOR_PREFIX);
		sb.append(R);
		sb.append(G);
		sb.append(B);
		return sb.toString();

	}
	
	/**
	 * 16进制表示的颜色转java.awt.Color.
	 * @param hexColor		- e.g. '#2195DE'
	 * @return
	 */
	public static Color parseColor(String hexColor){
		if(hexColor == null){
			throw new IllegalArgumentException("Arguments 'hexColor' can not be null!");
		}
		if(!hexColor.toUpperCase().matches("#?[0-9A-F]{6}")){
			throw new IllegalArgumentException(String.format("Arguments 'hexColor'[%s] is an invalid hex color string!", hexColor));
		}
		Color targetColor = null;
		hexColor = hexColor.toUpperCase();
		targetColor = new Color(Integer.parseInt(hexColor.startsWith(HEX_COLOR_PREFIX) ? hexColor.substring(1) : hexColor, 16));
		return targetColor;
	}
	
	public static void main(String[] args) {
		String hexColor = "#2195DE";
		Color color = parseColor(hexColor);
		System.out.println(color);
		System.out.println(toHexString(color));
	}

}
