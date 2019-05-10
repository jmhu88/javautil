import com.house365.finance.framework.base64.Base64;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

//令牌，用于参数一个随机唯一的令牌值  
public class TokenProcessor{  
    private TokenProcessor(){}  
    private static final TokenProcessor token = new TokenProcessor();  
    public static TokenProcessor getInstance(){  
        return token;  
    }  
    public String generateToken(){  
        String token = System.currentTimeMillis()+new Random().nextInt()+"";    //随机的值  
        try {  
            MessageDigest md = MessageDigest.getInstance("md5");        //注意下面的处理方式  
            byte[] md5 = md.digest(token.getBytes());  
            return new String(Base64.encode(md5), "ASCII"); //base64编码    
        } catch (NoSuchAlgorithmException e) {  
            throw new RuntimeException(e);  
        } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;  
    }  
}