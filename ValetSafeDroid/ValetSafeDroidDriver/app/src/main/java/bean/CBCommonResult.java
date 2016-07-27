package bean;

import java.io.Serializable;

/**
 * Created by zhenya huang on 2016/7/13.
 */
public class CBCommonResult<T> implements Serializable{
    private int code = CBCommonResultCode.SUCCESS.value(); //执行码
    private T data; //数据
    private String description; //描述

    public CBCommonResult(T data) {
        this.data = data;
    }

    public CBCommonResult(CBCommonResultCode code, String description) {
        this.code = code.value();
        this.description = description;
    }

    public CBCommonResult(CBCommonResultCode code, T data, String description) {
        this.code = code.value();
        this.description = description;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
