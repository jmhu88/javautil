/*
 * Copyright (C), 2002-2014, 365金融研发中心
 * Project: finance-dmo
 * PackName: com.house365.finance.dmo.audit.domain
 * FileName: ProductTypeEnum.java
 * Author: xuxuegang
 * Date: 2014年11月28日下午7:41:20
 * History： 
 */

import java.util.HashMap;
import java.util.Map;


/** 
 * 担保方式
 * 
 * @author 许学刚
 * @date 2014年11月28日
 * @version 1.0
 */
public enum ProductWarrantyEnum {

    PRODUCT_CODE_FXS("FKS", "房开商担保"),
    PRODUCT_CODE_THRID("THIRD", "第三方担保机构担保"),
    PRODUCT_CODE_THRID_WARRENTY("THIRDWARRANTY", "第三方担保"),
    PRODUCT_CODE_BENXI("BENXI", "本息保障计划"),
    PRODUCT_CODE_QUANYI("QUANYI","权益保障计划"),
    PRODUCT_CODE_QUANYI_A("QUANYIA","权益保障A计划"),
    PRODUCT_CODE_QUANYI_B("QUANYIB","权益保障B计划"),
    PRODUCT_CODE_FINANCE("FINANCE", "金融机构担保");
    
    /** 枚举的CODE */
    private String code;
    
    /** 枚举的NAME */
    private String name;
    
    private ProductWarrantyEnum(String code, String name) {
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
        for(ProductWarrantyEnum em : values()){
            if(em.getCode().equals(code)){
                return em.getName();
            }
        }
        return null;
    }
    
    public static Map<String, String> getMap() {
        Map<String, String> map = new HashMap<String, String>();
        for (ProductWarrantyEnum em : values()) {
            map.put(em.code, em.name);
        }
        return map;
    }
}
