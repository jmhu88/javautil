import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private static final String DEFAULT_ENCODEING = "UTF-8";
    private static final int DEFAULT_TIMEOUT = 1000 * 30;
    private static HttpClient httpClient;

    /**
     * 获得HttpClient实例
     * 
     * @return
     */
    public static HttpClient getHttpClientInstance() {
        return getHttpClientInstance(DEFAULT_TIMEOUT);
    }

    /**
     * 获得HttpClient实例
     * 
     * @param timeout
     *            超时时间（单位秒）
     * @return
     */
    @SuppressWarnings("deprecation")
    public static HttpClient getHttpClientInstance(int timeout) {
        if (httpClient == null) {
            httpClient = new DefaultHttpClient(new ThreadSafeClientConnManager());
            HttpParams httpParams = httpClient.getParams();
            HttpProtocolParams.setVersion(httpParams, HttpVersion.HTTP_1_1);
            HttpProtocolParams.setContentCharset(httpParams, DEFAULT_ENCODEING);
            if (timeout <= 0) {
                HttpConnectionParams.setConnectionTimeout(httpParams, DEFAULT_TIMEOUT);
                HttpConnectionParams.setSoTimeout(httpParams, DEFAULT_TIMEOUT);
            } else {
                HttpConnectionParams.setConnectionTimeout(httpParams, 1000 * timeout);
                HttpConnectionParams.setSoTimeout(httpParams, 1000 * timeout);
            }
            httpParams.setParameter("http.protocol.content-charset", DEFAULT_ENCODEING);
            httpParams.setParameter(HTTP.CONTENT_ENCODING, DEFAULT_ENCODEING);
            httpParams.setParameter(HTTP.CHARSET_PARAM, DEFAULT_ENCODEING);
            httpParams.setParameter(HTTP.DEFAULT_PROTOCOL_CHARSET, DEFAULT_ENCODEING);
        }
        return httpClient;
    }

    /**
     * Get方式请求URL，获得响应的结果
     * 
     * @param url
     * @param params
     * @return
     */
    public static String doGet(String url, Map<String, Object> params, String encode) {

        logger.debug("doGet->url：{}", url);
        logger.debug("doGet->params：{}", params);

        if (StringUtils.isEmpty(url)) {
            logger.error("请确定url地址为空，httpclient请求服务失败");
            return null;
        }
        if(StringUtils.isEmpty(encode)) {
            encode = DEFAULT_ENCODEING;
        }
        try {
            HttpClient httpClient = getHttpClientInstance();

            // 创建HttpGet对象，设置HttpPost对象的请求参数
            HttpGet httpGet = null;
            List<NameValuePair> paramList = getNameValuePairs(params);
            if (url.trim().endsWith("?")) {
                httpGet = new HttpGet(url + URLEncodedUtils.format(paramList, encode));
            } else {
                httpGet = new HttpGet(url + "?" + URLEncodedUtils.format(paramList, encode));
            }
            logger.debug("请求地址->{}", httpGet.getRequestLine());

            // 通过HttpClient对象，执行Get请求返回HttpResponse对象
            HttpResponse httpResponse = httpClient.execute(httpGet);

            // 通过HttpResponse对象，获得HttpEntity对象
            HttpEntity httpEntity = getHttpEntity(httpResponse);

            // 通过HttpEntity对象，获得服务器返回的数据（JSON/XML/HTML）
            return getResponseBody(httpEntity, httpResponse);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

    public static byte[] doGetWithDownloadFile(String url, Map<String, Object> params) {

        logger.debug("doGetWithDownloadFile->url：{}", url);

        byte[] fileBuffer = null;
        if (StringUtils.isEmpty(url)) {
            logger.error("请确定url地址为空，httpclient请求服务失败");
            return fileBuffer;
        }
        try {
            HttpClient httpClient = getHttpClientInstance();

            // 创建HttpGet对象，设置HttpPost对象的请求参数
            HttpGet httpGet = null;
            List<NameValuePair> paramList = getNameValuePairs(params);
            if (url.trim().endsWith("?")) {
                httpGet = new HttpGet(url + URLEncodedUtils.format(paramList, DEFAULT_ENCODEING));
            } else {
                httpGet = new HttpGet(url + "?" + URLEncodedUtils.format(paramList, DEFAULT_ENCODEING));
            }
            logger.debug("请求地址->{}", httpGet.getRequestLine());

            // 通过HttpClient对象，执行Get请求返回HttpResponse对象
            HttpResponse httpResponse = httpClient.execute(httpGet);

            // 通过HttpResponse对象，获得HttpEntity对象
            HttpEntity httpEntity = getHttpEntity(httpResponse);

            // 根据文件输出流，生成文件（包括图片等）
            fileBuffer = EntityUtils.toByteArray(httpEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileBuffer;
    }

    /**
     * 蒋Map参数组装成List<NameValuePair>对象
     * 
     * @param params
     * @return
     */
    private static List<NameValuePair> getNameValuePairs(Map<String, Object> params) {
        List<NameValuePair> paramList = new ArrayList<NameValuePair>();
        if (params != null) {
            Set<Entry<String, Object>> set = params.entrySet();
            String key = null;
            Object value = null;
            for (Entry<String, Object> entry : set) {
                key = entry.getKey();
                value = params.get(key);
                paramList.add(new BasicNameValuePair(key, (value == null) ? null : (String.valueOf(value))));
            }
        }
        return paramList;
    }

    /**
     * 通过HttpResponse对象，获得HttpEntity对象
     * 
     * @param httpResponse
     * @return
     */
    private static HttpEntity getHttpEntity(HttpResponse httpResponse) {
        /*
         * logger.debug("请求状态->{}", httpResponse.getStatusLine()); Header
         * headers[] = httpResponse.getAllHeaders(); for (int i = 0; i <
         * headers.length; i++) { logger.debug("{}->{}", headers[i].getName(),
         * httpResponse.getStatusLine()); }
         */
        HttpEntity httpEntity = httpResponse.getEntity();
        /*
         * logger.debug("ContentType->"+ httpEntity.getContentType());
         * logger.debug("ContentEncoding->"+ httpEntity.getContentEncoding());
         * logger.debug("ContentLength->"+ httpEntity.getContentLength());
         */
        return httpEntity;
    }

    /**
     * 通过HttpEntity对象，获得服务器返回的数据（JSON或HTML等）
     * 
     * @param httpEntity
     * @param httpResponse
     * @return
     */
    @SuppressWarnings("deprecation")
    private static String getResponseBody(HttpEntity httpEntity, HttpResponse httpResponse) {
        String body = null;
        if (httpEntity != null && httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            try {
                body = EntityUtils.toString(httpResponse.getEntity(), DEFAULT_ENCODEING);
                httpEntity.consumeContent();
                logger.debug("请求响应的内容：\n{}", body);
            } catch (ParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return body;
    }
}