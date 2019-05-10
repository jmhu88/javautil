import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;

/**
 * I/O流的相关工具类
 *
 * @author pengpeng
 * @date 2013-8-4 下午3:10:23
 * @version 1.0
 */
public class IOUtils {

	public static final int BUFFER_SIZE = 4096;


	/**
	 * 将一个输入流转为字节数组
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static byte[] copyToByteArray(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream(BUFFER_SIZE);
		copy(in, out);
		return out.toByteArray();
	}

	/**
	 * 将一个字符流按照特定的编码转为字符串
	 * @param in
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String copyToString(InputStream in, Charset charset) throws IOException {
		Assert.notNull(in, "No InputStream specified");
		StringBuilder out = new StringBuilder();
		InputStreamReader reader = new InputStreamReader(in, charset);
		char[] buffer = new char[BUFFER_SIZE];
		int bytesRead = -1;
		while ((bytesRead = reader.read(buffer)) != -1) {
			out.append(buffer, 0, bytesRead);
		}
		return out.toString();
	}

	/**
	 * 将一个字节数组输出到一个输出流中
	 * @param in
	 * @param out
	 * @throws IOException
	 */
	public static void copy(byte[] in, OutputStream out) throws IOException {
		Assert.notNull(in, "No input byte array specified");
		Assert.notNull(out, "No OutputStream specified");
		out.write(in);
	}

	/**
	 * 将字符串按照特定的编码输出到输出流中
	 * @param in
	 * @param charset
	 * @param out
	 * @throws IOException
	 */
	public static void copy(String in, Charset charset, OutputStream out) throws IOException {
		Assert.notNull(in, "No input String specified");
		Assert.notNull(charset, "No charset specified");
		Assert.notNull(out, "No OutputStream specified");
		Writer writer = new OutputStreamWriter(out, charset);
		writer.write(in);
		writer.flush();
	}

	/**
	 * 输入流in输出到输出流out中去
	 * @param in
	 * @param out
	 * @return
	 * @throws IOException
	 */
	public static int copy(InputStream in, OutputStream out) throws IOException {
		Assert.notNull(in, "No InputStream specified");
		Assert.notNull(out, "No OutputStream specified");
		int byteCount = 0;
		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;
		while ((bytesRead = in.read(buffer)) != -1) {
			out.write(buffer, 0, bytesRead);
			byteCount += bytesRead;
		}
		out.flush();
		return byteCount;
	}

	public static void closeQuietly(Closeable closeable) {
		try {
			if(closeable != null){
				closeable.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Closeable closeable) throws IOException {
		if(closeable != null){
			closeable.close();
		}
	}
	
}
