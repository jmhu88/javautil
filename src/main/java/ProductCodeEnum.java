/*
 * Copyright (C), 2002-2014, 365金融研发中心
 * Project: finance-dmo
 * PackName: com.house365.finance.dmo.audit.domain
 * FileName: ProductTypeEnum.java
 * Author: 邱刘军
 * Date: 2014年10月13日下午7:41:20
 * History： 
 */

/** 
 * 产品编码枚举：XJD-新居贷  FKD-房开贷
 * 
 * @author 邱刘军
 * @date 2014年10月13日
 * @version 1.0
 */
public enum ProductCodeEnum {

    PRODUCT_CODE_XJD("XJD", "新居贷"),
    PRODUCT_CODE_FKD("FKD", "房开贷"),
    PRODUCT_CODE_ZQZR("ZQZR", "债权转让"),
    PRODUCT_CODE_YFKD("YFD", "优房贷（过桥贷）"),
    PRODUCT_CODE_365JC("365JC", "365聚财"),
    PRODUCT_CODE_ZXD("ZXD", "装修贷"),
    PRODUCT_CODE_JBP("JBP", "聚宝盆"),
    PRODUCT_CODE_OTHER("OTHER", "其他"),
    PRODUCT_CODE_YQT("YQT", "易企通"),
    PRODUCT_CODE_ZHJR("ZHJR", "综合金融"),
    PRODUCT_CODE_SFD("SFD", "优房贷（消费贷）"),
    PRODUCT_CODE_HFD("HFD", "优房贷（换房贷）"),
    PRODUCT_CODE_365ZC("365ZC", "365众筹"),
    PRODUCT_CODE_REGISTBID("REGISTBID", "众筹报名竞价"),
    PRODUCT_CODE_BIDDING("BIDDING", "众筹竞价款"),
    PRODUCT_CODE_OTHER_ZC("OTHER_ZC", "其他和众筹"),
    PRODUCT_CODE_TQXJD("TQXJD", "提前还款新居贷"),
    PRODUCT_CODE_TQZXD("TQZXD", "提前还款装修贷"),
    PRODUCT_CODE_XSB("XSB", "新手标");
    
    /** 枚举的CODE */
    private String code;
    /** 枚举的NAME */
    private String name;
    
    private ProductCodeEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }
    
    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }
    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public static String getName(String code){
        for(ProductCodeEnum em : values()){
            if(em.getCode().equals(code)){
                return em.getName();
            }
        }
        return null;
    }

    /**
     * 根据产品编号，获取对应的Code
     * 
     * @param productNo
     * @return
     * @author 时林涛
     * @date 2016年1月23日
     */
    public static String getCode(String productNo) {
        for (ProductCodeEnum em : values()) {
            if (productNo.indexOf(em.getCode()) == 0) {
                return em.getCode();
            }
        }
        return null;
    }
}
