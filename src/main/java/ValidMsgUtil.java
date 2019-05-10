/*
 * Copyright (C), 2002-2014, 365金融研发中心
 * Project: finance-framework
 * PackName: com.house365.finance.framework.util
 * FileName: ValidMsgUtil.java
 * Author: 邱刘军
 * Date: 2014年10月20日下午2:11:40
 * History： 
 */

import com.house365.finance.framework.persistence.support.Result;
import com.house365.finance.framework.shiro.service.consts.CommonConstants;
import java.util.List;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

/** 
 * 输入校验消息工具类
 * 
 * @author 邱刘军
 * @date 2014年10月20日
 * @version 1.0
 */
public class ValidMsgUtil {

    /**
     * 服务器段Bean的JSR303输入校验
     * 
     * @param errors
     * @return
     */
    public static String getErrorMsg(BindingResult bindingResult) {
        StringBuffer msg = new StringBuffer();
        if(bindingResult.hasFieldErrors()) {
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (ObjectError error : errors) {
                msg.append(error.getDefaultMessage() + "\n");
            }
        }
        return msg.toString();
    }

    /**
     * 服务器段Bean的JSR303输入校验
     * 
     * @param bindingResult
     * @return
     */
    public static Result<String> getErrorResult(BindingResult bindingResult) {
        String errorMsg = getErrorMsg(bindingResult);
        if(StringUtils.isEmpty(errorMsg)) {
            return new Result<String>(true, null, CommonConstants.RESULT_RET_CODE_SUCCESS);
        }
        return new Result<String>(false, errorMsg, CommonConstants.RESULT_RET_CODE_FAILURE);
    }
}
