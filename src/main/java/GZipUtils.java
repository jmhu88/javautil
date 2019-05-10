import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public class GZipUtils {

	public static byte[] compress(byte[] data) throws IOException {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        GZIPOutputStream gos = new GZIPOutputStream(baos);// 压缩
        gos.write(data, 0, data.length);  
        gos.finish();
        byte[] output = baos.toByteArray();  
        baos.flush();  
        baos.close();
        return output;  
    } 
	
}
