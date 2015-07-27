package zpark.actions.day10;

import zpark.ext.jdbc.JdbcUtil;
import zpark.ext.struts.Flash;
import zpark.service.UserService;
import zpark.service.UserServiceImpl;

public class BaseAction {

    private static UserService service = JdbcUtil.createProxy(new UserServiceImpl(), UserService.class);

    private String error;

    private String message;

    // 声明需要放入flash作用域
    @Flash
    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    // 声明需要放入flash作用域
    @Flash
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
