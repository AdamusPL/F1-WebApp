package com.typerf1.typerf1.tools;

public class URL {
    public String getUrl() {
        return url;
    }

    public String getGpName(){
        return gpName;
    }

    private String url;
    private String gpName;

    public URL(String gpName, String url) {
        this.url = url;
        this.gpName = gpName;
    }
}
