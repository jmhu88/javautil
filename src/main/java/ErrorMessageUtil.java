/**
 *
 */

import com.house365.finance.framework.persistence.support.Result;

/**
 * @author Administrator
 *
 */
public class ErrorMessageUtil {
    public static Result error(String errorCode, String msg) {
        Result result = new Result();
        result.setMsg(msg);
        result.setSuccess(false);
        result.setRetCode(errorCode);
        return result;
    }
}
