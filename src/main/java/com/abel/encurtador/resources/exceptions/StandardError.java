package com.abel.encurtador.resources.exceptions;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StandardError {
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private Integer code;
    private String msg;
    private String date;

    public StandardError(Integer code, String msg, Date date) {
        this.code = code;
        this.msg = msg;
        this.date = sdf.format(date);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = sdf.format(date);;
    }
}
