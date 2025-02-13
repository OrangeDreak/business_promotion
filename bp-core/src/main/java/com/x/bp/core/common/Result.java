package com.x.bp.core.common;


import java.io.Serializable;

/**
 *
 * @author: zhaofs
 * @date: 2024/9/29
 */
public class Result<T> implements Serializable {
    /** 状态码**/
    int code ;

    /**是否成功**/
    boolean success;

    /**数据**/
    T data;

    /**数量"**/
    int cnt;

    /**消息"**/
    String message;

    /**是否有下一页"**/
    boolean hasNext;


    public static<T> Result<T> buildSuccess(T t, int cnt, boolean hasNext){
        Result<T> result = new Result<>();
        result.code = 200;
        result.success = true;
        result.data = t;
        result.cnt = cnt;
        result.hasNext = hasNext;
        return result;
    }

    public static<T> Result<T> buildSuccess(T t, int cnt){
        return buildSuccess(t, cnt, false);
    }

    public static<T> Result<T> buildSuccess(T t, boolean hasNext){
        return buildSuccess(t, 0, hasNext);
    }

    public static <T> Result<T> buildHasNext() {
        return buildSuccess(null, 0, false);
    }

    public static <T> Result<T> buildHasNext(T t) {
        return buildSuccess(t, 0, true);
    }

    public static <T> Result<T> buildHasNext(T t, int cnt) {
        return buildSuccess(t, cnt, true);
    }

    public static<T> Result<T> buildSuccess(T t){
        return buildSuccess(t, 0);
    }

    public static<T> Result<T> buildSuccess(){
        Result<T> result = new Result<>();
        result.code = 200;
        result.success = true;
        // r.data = new T();
        return result;
    }


    public static <T> Result<T> buildFail(String msg) {
        return buildFail(msg, 500);
    }

    public static <T> Result<T> buildFail(String msg, int code) {
        Result<T> result = new Result<>();
        result.code = code;
        result.success = false;
        // result.data = msg;
        result.message = msg;
        return result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }
}
