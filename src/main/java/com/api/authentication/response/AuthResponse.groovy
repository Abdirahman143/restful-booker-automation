package com.api.authentication.response

import com.fasterxml.jackson.annotation.JsonProperty



class AuthResponse {

    @JsonProperty("token")
    private String token;
    @JsonProperty("reason")
    private String reason

    String getReason() {
        return reason
    }

    void setReason(String reason) {
        this.reason = reason
    };

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;

    }
}
