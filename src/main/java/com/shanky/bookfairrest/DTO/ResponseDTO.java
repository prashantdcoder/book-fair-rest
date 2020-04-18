package com.shanky.bookfairrest.DTO;

public class ResponseDTO<T> {

    private boolean status;
    private T data;
    private String message;

    public void setSuccessResponse(T data, String message) {
        this.status = true;
        setDataAndMessage(data, message);
    }

    public void setFailureResponse(T data, String message) {
        this.status = false;
        setDataAndMessage(data, message);
    }

    private void setDataAndMessage(T data, String message) {
        this.data = data;
        this.message = message;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
