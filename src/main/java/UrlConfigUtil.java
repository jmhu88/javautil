/*
 * Copyright (C), 2002-2014, 365金融研发中心
 * Project: finance-framework
 * PackName: com.house365.finance.framework.component.authFilter
 * FileName: UrlEnum.java
 * Author: 邱刘军
 * Date: 2014年10月22日下午5:42:52
 * History： 
 */

import java.util.ArrayList;
import java.util.List;

/**
 * URL配置工具类
 * 
 * @author 邱刘军
 * @date 2014年10月22日
 * @version 1.0
 */
public class UrlConfigUtil {

    /** 需要进行登录才可以访问的地址统一放到该List对象中 */
    public static List<String> LOGIN_URL_LIST = new ArrayList<String>();
    
    /** 【触屏版】需要进行登录才可以访问的地址统一放到该List对象中 */
    public static List<String> MOBILE_LOGIN_URL_LIST = new ArrayList<String>();
    
    static {
        //-------------------PC版--------------------
        // add by shilintao 会员账户页面
        LOGIN_URL_LIST.add("/account/([a-zA-Z0-9]*/)*[\\s\\S]*");
        
        // 产品认购确认
        LOGIN_URL_LIST.add("/product/productConfirm.htm");
        
        // 产品订单支付
        LOGIN_URL_LIST.add("/product/payOrder.htm");
        
        // 订单重新支付
        LOGIN_URL_LIST.add("/product/orderRePay.htm");
        
        // 订单支付同步回调
        LOGIN_URL_LIST.add("/product/synPayCallBack.htm");
        
        // 商品信息录入
        LOGIN_URL_LIST.add("/loan/productInfo-execute-add.htm");
        
        // 我要借款个人信息录入
        LOGIN_URL_LIST.add("/loan/personalInfo-execute-add.htm");
        
        // 上传资料信息录入
        LOGIN_URL_LIST.add("/loan/uploadInfo-execute-add.htm");
        
        // 还款
        LOGIN_URL_LIST.add("/product/repayment.htm");
        
        // 我的还款，借款历史
        LOGIN_URL_LIST.add("/borrow/([a-zA-Z0-9]*/)*[\\s\\S]*");
        
        // 还款成功页
        LOGIN_URL_LIST.add("/product/repaymentSuccess.htm");
        
        //--------------------触屏版------------------
        //我的账户
        MOBILE_LOGIN_URL_LIST.add("/account/memberDetail.htm");
        // add by shilintao 会员账户页面
        MOBILE_LOGIN_URL_LIST.add("/account/([a-zA-Z0-9]*/)*[\\s\\S]*");
        
        MOBILE_LOGIN_URL_LIST.add("/repayment/([a-zA-Z0-9]*/)*[\\s\\S]*");
        
    }
}
