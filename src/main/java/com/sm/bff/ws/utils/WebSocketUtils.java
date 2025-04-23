package com.sm.bff.ws.utils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class WebSocketUtils {
    public static Map<String,String> parseQuery(String query) {
        Map<String,String> m = new HashMap<>();
        if (query == null || query.isBlank()) return m;
        for (String pair : query.split("&")) {
            var idx = pair.indexOf("=");
            if (idx>0) {
                String k = URLDecoder.decode(pair.substring(0, idx), StandardCharsets.UTF_8);
                String v = URLDecoder.decode(pair.substring(idx+1), StandardCharsets.UTF_8);
                m.put(k, v);
            }
        }
        return m;
    }
}
