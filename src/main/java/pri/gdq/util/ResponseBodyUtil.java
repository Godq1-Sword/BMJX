package pri.gdq.util;

import pri.gdq.entity.ResponseBody;

import java.sql.Timestamp;

/**
 * program : taxoa-backend
 * description: 响应数据工具类
 *
 * @author : gdq
 * data : 2019-11-21 17:49
 **/
public class ResponseBodyUtil {

    private static ResponseBody generateResponse(Object data, String error, String message, int status) {
        ResponseBody responseBody = new ResponseBody();
        responseBody.setData(data);
        responseBody.setError(error);
        responseBody.setMessage(message);
        responseBody.setStatus(status);
        responseBody.setTimestamp(new Timestamp(System.currentTimeMillis()));
        return responseBody;
    }

    public static ResponseBody generateSucResp(Object data, String message) {
        return generateResponse(data, null, message, 200);
    }

    public static ResponseBody generateErrResp(Object data) {
        return generateResponse(data, "服务器错误", "服务器错误", 500);
    }

    public static ResponseBody generateErrResp(Object data, String message) {
        return generateResponse(data, "服务器错误", message, 500);
    }

    public static ResponseBody generateNotLogResp(Object data) {
        return generateResponse(data, "未登录", "未登录,请登录", 401);
    }

    public static ResponseBody generateLogExpResp(Object data) {
        return generateResponse(data, "登陆过期", "登录过期,请重新登录", 403);
    }

    public static ResponseBody generatePsEResp(Object data) {
        return generateResponse(data, "用户名密码错误", "用户名密码错误", 500);
    }
}
