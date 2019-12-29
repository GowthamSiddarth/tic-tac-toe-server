package com.gowtham.tictactoe.helper.responsewrapper;

public class MessageResponse {

    private boolean success;
    private String message;

    public MessageResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
