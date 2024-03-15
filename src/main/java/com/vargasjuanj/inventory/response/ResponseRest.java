package com.vargasjuanj.inventory.response;

import java.util.HashMap;

public class ResponseRest {
    private HashMap<String, String> metadata =  new HashMap<>();

    public HashMap<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(String type, String code, String date) {
        metadata.put("type",type);
        metadata.put("code",code);
        metadata.put("date",date);
    }
}
