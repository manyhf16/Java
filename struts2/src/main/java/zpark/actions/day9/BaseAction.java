package zpark.actions.day9;

import zpark.ext.jdbc.JdbcUtil;
import zpark.service.UserService;
import zpark.service.UserServiceImpl;

import com.google.gson.annotations.Expose;

public class BaseAction {

    private static UserService service = JdbcUtil.createProxy(new UserServiceImpl(), UserService.class);

    // 包含错误信息, 声明需要json转换
    @Expose
    private String error;

    // 操作成功的提示信息, 声明需要json转换
    @Expose
    private String message;

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserService getService() {
        return service;
    }

}
