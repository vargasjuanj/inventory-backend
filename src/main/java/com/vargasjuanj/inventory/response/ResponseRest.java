package com.vargasjuanj.inventory.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
