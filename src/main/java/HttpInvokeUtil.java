/*
 * Copyright (C), 2002-2014, 三六五网络金融
 * FileName: HttpInvokeUtil.java
 * Author:   258737400@qq.com
 * Date:     2013-5-20 上午8:44:08
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */

import java.io.IOException;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Http调用工具类
 * 
 * @author 258737400@qq.com
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public abstract class HttpInvokeUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpInvokeUtil.class);

    private static final int TIMEOUT = 20000;

    /**
     * 功能描述: HTTP Post请求
     * 
     * @author 258737400@qq.com
     * @param url 请求URL
     * @param xmlStr 消息体
     * @param headerParamMap 消息头参数
     * @return 响应信息
     * @throws ClientProtocolException
     * @throws IOException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String sendPostRequest(final String url, String xmlStr, Map<String, String> headerParamMap)
        throws IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        // 设置连接时间为20秒
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, TIMEOUT);
        // 设置数据传输时间为20秒
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, TIMEOUT);

        StringEntity reqEntity = new StringEntity(xmlStr, "UTF-8");
        
        // 建立HttpPost对象
        HttpPost httpPost = new HttpPost(url);
        // 设置Header参数
        if (!headerParamMap.containsKey("Content-Type")) {
            httpPost.setHeader("Content-Type", "text/xml; charset=utf-8");
        }
        for (Map.Entry<String, String> entry : headerParamMap.entrySet()) {
            httpPost.setHeader(entry.getKey(), entry.getValue());
        }
        // 请求体
        httpPost.setEntity(reqEntity);
        LOGGER.info("req start:" + System.currentTimeMillis());
        HttpResponse response = httpClient.execute(httpPost);
        LOGGER.info("req end:" + System.currentTimeMillis());
        // 如果状态码为200,就是正常返回
        String respStr = String.valueOf(response.getStatusLine().getStatusCode());
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            respStr = EntityUtils.toString(response.getEntity());
        }
        return respStr;
    }
}
