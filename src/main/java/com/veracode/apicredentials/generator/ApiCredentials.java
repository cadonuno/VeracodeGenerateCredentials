package com.veracode.apicredentials.generator;

public class ApiCredentials {
    private String apiID;
    private String apiKey;


    public ApiCredentials(String apiID, String apiKey) {
        this.apiID = apiID;
        this.apiKey = apiKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiID() {
        return apiID;
    }
}
