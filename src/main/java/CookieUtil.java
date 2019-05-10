import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CookieUtil {

    private static final Log LOG = LogFactory.getLog(CookieUtil.class);
    private static final String CNAME_BACK_LINKS = "backlinksCode";

    /** 增加内存cookie */
    private static void addMemoryCookie(HttpServletResponse response, String cname, String value, String domain) {
        memoryCookie(response, cname, value, domain, -1);
    }

    /** 删除内存cookie */
    public static void removeCookie(HttpServletResponse response, String cname, String domain) {
        memoryCookie(response, cname, null, domain, 0);
    }

    /** 建立内存cookie统一方法 */
    public static void memoryCookie(HttpServletResponse response, String cname, String value, String domain, int maxAge) {
        Cookie cookie = new Cookie(cname, value);
        cookie.setMaxAge(maxAge); // 0：立即删除； -1：浏览器关闭即消失
        cookie.setPath("/");
        cookie.setDomain(domain);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
    }

    /** 将cookie封装到Map里面 */
    private static Map<String, Cookie> ReadCookieMap(HttpServletRequest request) {
        Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }

    /** 根据cookie名获取值 */
    public static Cookie getCookieByName(HttpServletRequest request, String name) {
        Map<String, Cookie> cookieMap = ReadCookieMap(request);
        if (cookieMap.containsKey(name)) {
            Cookie cookie = (Cookie) cookieMap.get(name);
            return cookie;
        } else {
            return null;
        }
    }

    /**
     * 增加推广外链cookie
     * 
     * @param request
     * @param response
     */
    public static void addBackLinksCookie(HttpServletRequest request, HttpServletResponse response) {
        String code = StringUtils.ckStr(request.getParameter(CNAME_BACK_LINKS));
        if (code != null) {
            addMemoryCookie(response, CNAME_BACK_LINKS, code, CustomPropertyConfigurer.getMessage("back_links_domain"));
        }
    }
    
    
    /**
     * 获取推广外链cookie
     * 
     * @param request
     * @param response
     */
    public static Cookie getBackLinksCookie(HttpServletRequest request) {
        
         return CookieUtil.getCookieByName(request,  CNAME_BACK_LINKS);
        
    }
    
    // TODO 获取

    /** 新建会话(含加密) */
    // public static boolean newSession(HttpServletResponse response, String
    // mId, String loginName) {
    // try {
    // String val = mId + SPLIT_SIGN + loginName + SPLIT_SIGN +
    // System.currentTimeMillis();
    // val = DesUtil.encrypt(val);
    // val = URLEncoder.encode(val, ENCODE);
    // addMemoryCookie(response, AuthConstant.SESSION_NAME, val);
    // return true;
    // } catch (Exception e) {
    // LOG.error("新建会话异常：", e);
    // return false;
    // }
    // }

    /** 解析cookie */
    // private static String[] parseCookie(String val) {
    // try {
    // String valDecoder = URLDecoder.decode(val, ENCODE);
    // valDecoder = DesUtil.decrypt(valDecoder);
    // String[] arr = valDecoder.split(SPLIT_SIGN);
    // System.out.println("memberId [" + arr[0] + "] loginName [" + arr[1] +
    // "] createTime ["
    // + new Timestamp(AppUtil.ckLong(arr[2])) + "]");
    // return arr;
    // } catch (Exception e) {
    // LOG.error("检查登陆异常：", e);
    // return null;
    // }
    // }

    /** 检查是否登陆 */
    // public static boolean ckCookieLogin(HttpServletRequest request) {
    // Cookie c = getCookieByName(request, AuthConstant.SESSION_NAME);
    // if (c != null) {
    // String[] arr = parseCookie(c.getValue());
    // if (arr.length >= 1) {
    // request.setAttribute(CURRENT_LOGIN, arr);
    // return true;
    // }
    // }
    // return false;
    // }

    /** 获取当前会员ID */
    // public static String[] getCurrentMemId(HttpServletRequest request) {
    // return (String[]) request.getAttribute(CURRENT_LOGIN);
    // }

    /** 删除会话 */
    // public static void invalidate(HttpServletResponse response) {
    // removeCookie(response, AuthConstant.SESSION_NAME);
    // }
}
