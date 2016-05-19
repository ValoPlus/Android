package com.valoplus.android;

/**
 * Created by tom on 12.02.16.
 */
public class ErrorDTO {
    private String errorText;

    public ErrorDTO(String errorText) {
        this.errorText = errorText;
    }

    public ErrorDTO() {
    }

    public String getErrorText() {
        return errorText;
    }

    @Override
    public String toString() {
        return errorText;
    }
}
