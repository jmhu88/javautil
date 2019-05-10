/*
 * Copyright (C), 2002-2014, 365金融研发中心
 * Project: finance-framework
 * PackName: com.house365.finance.framework.util
 * FileName: CodeGeneratedUtils.java
 * Author: 邱刘军
 * Date: 2014年10月17日上午8:59:41
 * History： 
 */

import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * 各种编码生成工具类
 * 
 * @author 邱刘军
 * @date 2014年10月17日
 * @version 1.0
 */
public class CodeGeneratedUtils {

    public static SimpleDateFormat simple_sdf = new SimpleDateFormat("yyMMdd");
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
    
    /** 产品CODE枚举，e.g. XJD-新居贷  FKD-房开贷 */
    private static ProductCodeEnum productCodeEnum;
    public static ProductCodeEnum getProductCodeEnum() {
        return productCodeEnum;
    }
    public static void setProductCodeEnum(ProductCodeEnum productCodeEnum) {
        CodeGeneratedUtils.productCodeEnum = productCodeEnum;
    }

    /**
     * 生成产品编号，e.g. XJD141027AA0011
     * 
     * @return
     */
    public static String createProductCode(String productCode) {
        return productCode + simple_sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    
    /**
     * 生成订单编号，e.g. XJD141027155001AA0011
     * 
     * @return
     */
    public static String createOrderCode(String productCode) {
        return productCode + sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    
    /**
     * 生成借款合同编号，e.g. XJDJK141027AA0011
     * 
     * @return
     */
    public static String create_JK_ContractCode(String productCode){
        return productCode + "JK" + simple_sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }

    /**
     * 生成债权转让合同
     * 
     * @param productCode
     * @return
     * @author 时林涛
     * @date 2015年7月16日
     */
    public static String create_ZQZR_ContractCode(String productCode) {
        return productCode + "ZQZR" + simple_sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    
    /**
     * 生成投资合同
     * 
     * @param productCode
     * @return
     * @author 时林涛
     * @date 2015年7月16日
     */
    public static String create_TZ_ContractCode(String productCode) {
        return productCode + "TZ" + simple_sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }

    /**
     * 生成众筹借款合同编号，e.g. XJDJK141027AA0011
     *
     * @return
     */
    public static String create_ZC_JK_ContractCode(String productCode){
        return productCode + "JK" + simple_sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    
    /**
     * 生成担保合同编号，e.g. XJDBD141027AA0011
     * 
     * @return
     */
    public static String create_BD_ContractCode(String productCode){
        return productCode + "BD" + simple_sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    
    /**
     * 生成借款人还款订单编号，e.g. XJDHK141027AA0011
     * 
     * @return
     */
    public static String createRepaymentCode(String productCode) {
        return productCode + "HK" + simple_sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    
    /**
     * 生成投资人收益订单编号，e.g. XJDSY141017AA001AA001
     * 
     * @return
     */
    public static String createInvestCode(String productCode) {
        return productCode + "SY" + simple_sdf.format(new Date()) + StringUtils.getRandomChars(10);
    }
    
    /**
     * 生成退款订单编号，e.g. XJDTK141027155001AA0011
     * 
     * @return
     */
    public static String createRefoundCode(String productCode) {
        return productCode + "TK" + sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    
    /**
     * 生成邀请编号，e.g. YQ141224AA0000AA
     * 
     * @return
     */
    public static String createInviteCode() {
        return "YQ" + simple_sdf.format(new Date()) + StringUtils.getRandomChars(8);
    }
    
    /**
     * 生成产品VIP编号，e.g. YQ141224AA0000AA
     * 
     * @return
     */
    public static String createProductVIPNo() {
        return "VIP" + simple_sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    
    /**
     * 生成部门编号，e.g. DEP141224AA0000AA
     * 
     * @return
     */
    public static String createDepNo() {
        return "DEP" + simple_sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    
    /**
     * 生成支付流水号，e.g. DEP141224AA0000AA
     * 
     * @return
     */
    public static String createPayNo(String productCode){
        return productCode + "ZF" + sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    
    /**
     * 生成还款流水号，e.g. XJDHK141224AA0000AA
     * 
     * @return
     */
    public static String createRepayNo(String productCode){
        return productCode + "HK" + sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    
    /**
     * 生成充值流水号
     * @return
     * @author 时林涛
     * @date 2015年4月28日
     */
    public static String createRechargeNo(){
        return "RCH" + sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    
    /**
     * 生成注册流水号
     * @return
     */
    public static String createRegisterNo(){
        return "REG" + sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    
    /**
     * 生成绑卡流水号
     * @return
     */
    public static String createBindCardNo(){
        return "BK" + sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }

    /**
     * 生成提现流水号
     * @return
     */
    public static String createDepositNo(){
        return "DEPOSIT" + sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    
    /**
     * 生成平台充值流水号，e.g. PTCZ141224AA0000AA
     * 
     * @return
     */
    public static String createPlatRechargeNo(String ptchCode){
        return ptchCode + sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }

    /**
     * 生成聚财产品编号，e.g. XJD141027AA0011
     *
     * @return
     */
    public static String createPackageProductCode(String productCode,String index) {
        return productCode + new SimpleDateFormat("yyMMddHHmm").format(new Date()) + index;
    }
    
    /**
     * 体验金code
     * @return
     */
    public static String createActivityCode(){
        return "ACTIVITY" + sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    
    /**
     * 分佣code
     * @return
     */
    public static String createFenyongCode(){
        return "FENYONG" + sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    
    /**
     * 推荐放款表
     * @return
     */
    public static String createRecommendCode(){
        return "RECOMMEND" + sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    
    /**
     * 直接转账（无条件）
     * @return
     */
    public static String createDirectTransNoReasonCode(){
        return "NOREASON" + sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }

    /**
     * 直接转账（无条件）<br/>
     * 聚宝盆2W
     * @return
     */
    public static String createDirectTransNoReasonCodeJBP(String phone){
        return "NOREASON" + phone;
    }
    
    /**
     * 单独分佣code
     * @return
     */
    public static String createAloneFenyongCode(){
        return "ALONEFENYONG" + sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    
    /**
     * 开启授权流水code
     * @return
     */
    public static String createOpenAutoCode(){
        return "OPENAUTO" + sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    /**
     * 关闭授权流水code
     * @return
     */
    public static String createCloseAutoCode(){
        return "CLOSEAUTO" + sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    
    /**
     * 生成预约订单编号
     * 
     * @return
     */
    public static String createReserveOrderCode() {
        return "RESERVE" + sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    
    /**
     * 生成复投订单编号
     * 
     * @return
     */
    public static String createFutouOrderCode() {
        return "FUTOU" + sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    
    /**
     * 生成报名竞价订单编号
     * 
     * @return
     */
    public static String createRegistBidCode() {
        return "REGISTBID" + sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    /**
     * 生成报名竞价订单支付流水编号
     * 
     * @return
     */
    public static String createRegistBidPayNoOrderCode() {
        return "REGISTBID" + "ZF" + sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    
    /**
     * 生成支付竞价订单编号
     * 
     * @return
     */
    public static String createBidOrderCode() {
        return "BIDDING" + sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    /**
     * 生成支付竞价订单支付流水编号
     * 
     * @return
     */
    public static String createBidPayNoOrderCode() {
        return "BIDDING" + "ZF" + sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    
    /**
     * 生成提前还款订单编号，e.g. TQXJDHK141027AA0011
     * 
     * @return
     */
    public static String createTQRepaymentCode(String productCode) {
        return "TQ" + productCode + "HK" + simple_sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    

    /**
     * 生成提前还款流水号，e.g. TQXJDHK141224AA0000AA
     * 
     * @return
     */
    public static String createAdvanceRepayNo(String productCode){
        return "TQ" + productCode + "HK" + sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
    
    /**
     * 生成修改手机号流水号
     * @return
     */
    public static String createResetMobileNo(){
        return "RESETMOBILE" + sdf.format(new Date()) + StringUtils.getRandomChars(6);
    }
}
