package com.litmus.app.dto;

public class AppsResponse {
    private int status;
    private String message;

    public static final AppsResponse success_save_response = new AppsResponse( 0, "App Model saved successfully");
    public static final AppsResponse failed_save_response = new AppsResponse( -1, "failed to save app Model");
    public static final AppsResponse success_delete_response = new AppsResponse( 0, "App deleted");
    public static final AppsResponse failed_delete_response = new AppsResponse( -1, "failed to delete App");

    public AppsResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public AppsResponse() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
