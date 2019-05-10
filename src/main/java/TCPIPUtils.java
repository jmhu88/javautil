import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;

public class TCPIPUtils {

	/**
	 * 获取客户端的ip地址
	 * @param request
	 * @return
	 */
	public static String getRemoteIpAddr(HttpServletRequest request) {
		String ipAddress = null;
		// ipAddress = request.getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0
				|| "unknown".equalsIgnoreCase(ipAddress)) {
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
					ipAddress = inet.getHostAddress();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}

		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.split(".").length > 3) { // "***.***.***.***"
			if (ipAddress.indexOf(",") > 0) {
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
	
	/**
	 * 获取服务器的ip地址:端口号
	 * @param request
	 * @return
	 */
	public static String getLocalIpAddr(HttpServletRequest request) {
		return request.getLocalAddr() + ":" + request.getLocalPort();
	}
	
    /**
     * 获得IP对应的Mac地址
     * @param ip
     * @return
     */
    public static String getMACAddress(String ip) {
        BufferedReader br = null;
        Process p = null;
        String address = "";
        try {
            String os = System.getProperty("os.name");
            if (os.startsWith("Windows")) {
                if(ip.equals("localhost") || ip.equals("127.0.0.1") || ip.equals("0.0.0.0")){
                    p = Runtime.getRuntime().exec("cmd.exe /c ipconfig /all");
                } else {
                    File file = new File("c:/Windows/SysWOW64");
                    if(file.exists()){
                        p = Runtime.getRuntime().exec("c:/Windows/sysnative/nbtstat.exe -a " + ip);
                    } else {
                        p = Runtime.getRuntime().exec("nbtstat -a " + ip);
                    }
                }
                br = new BufferedReader(new InputStreamReader(p.getInputStream(), "gbk"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    if (line.indexOf("Physical Address") > 0 || line.indexOf("物理地址") > 0) {
                        int index = line.indexOf(":");
                        index += 2;
                        address = line.substring(index);
                        break;
                    } else if(line.indexOf("MAC 地址") > 0) {
                        int index = line.indexOf("=");
                        index += 2;
                        address = line.substring(index);
                        break;
                    }
                }
            } else if (os.startsWith("Linux")) {
                p = Runtime.getRuntime().exec("/bin/sh -c ifconfig -a");
                //String[] cmd = { "/bin/sh", "-c", "ping " + ip + " -c 2 && arp -a" };
                br = new BufferedReader(new InputStreamReader(p.getInputStream(), "gbk"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    if (line.indexOf("HWaddr") > 0) {
                        int index = line.indexOf("HWaddr") + "HWaddr".length();
                        address = line.substring(index);
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(br != null){
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return address.trim();
    }
}
