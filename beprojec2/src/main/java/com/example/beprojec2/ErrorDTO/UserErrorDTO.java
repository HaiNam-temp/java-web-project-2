package com.example.beprojec2.ErrorDTO;

public class UserErrorDTO {
    String errorResonse;
    String detail;
    public UserErrorDTO() {}
    public String getErrorResonse() {
        return errorResonse;
    }

    public void setErrorResonse(String errorResonse) {
        this.errorResonse = errorResonse;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }
}
