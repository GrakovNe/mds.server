package org.grakovne.mds.server.endpoints.support;

public class ApiResponse<T> {
    private String message;
    private T body;

    public ApiResponse(String message) {
        this.message = message;
    }

    public ApiResponse(T body) {
        this.body = body;
    }

    public ApiResponse(String message, T body) {
        this.message = message;
        this.body = body;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "message='" + message + '\'' +
                ", body=" + body +
                '}';
    }
}
