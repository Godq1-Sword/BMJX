package pri.gdq.entity;

import java.sql.Timestamp;

/**
 * program : taxoa-backend
 * description: 响应数据实体类
 *
 * @author : gdq
 * data : 2019-11-21 17:47
 **/
public class ResponseBody {
    // 错误信息
    private String error;
    // 状态码
    private int status;
    // 其他信息
    private String message;
    // 数据
    private Object data;
    // 时间
    private Timestamp timestamp;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ResponseBody{" +
                "error='" + error + '\'' +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", timestamp=" + timestamp +
                '}';
    }
}
