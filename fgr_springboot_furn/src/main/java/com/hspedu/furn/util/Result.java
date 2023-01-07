package com.hspedu.furn.util;

/**
 * @author: guorui fu
 * @versiion: 1.0
 * 1.Result<T>自定义泛型
 * 2.Result<T> 对象就是后端返回给前端的数据,以json格式返回
 */
public class Result<T> {
    private String code;//状态码
    private String msg;//对状态说明
    private T data;//返回携带的数据,使用泛型增强扩展性

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    //无参构造器
    public Result(){
    }

    //带参构造器
    public Result(T data){
        this.data = data;
    }

    //编写方法,返回需要的Result对象 表示成功
    public static Result success(){
        Result result = new Result<>();
        result.setCode("200");
        result.setMsg("success");
        return result;
    }

    //返回Result对象 表示成功的Result 同时可以携带数据
    //如果需要再static方法使用泛型,需要再static <T>
    public static <T> Result<T> success(T data){
        Result<T> result = new Result<>(data);
        result.setCode("200");
        result.setMsg("success");
        return result;
    }

    //编写方法,返回需要的Result对象 表示失败
    public static Result error(String code,String msg){
        Result result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    //返回Result对象 表示失败的Result 同时可以携带数据
    public static <T> Result<T> error(String code,String msg,T data){
        Result<T> result = new Result<>(data);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
