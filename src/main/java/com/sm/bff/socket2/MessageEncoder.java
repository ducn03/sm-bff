package com.sm.bff.socket2;

import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<String> {


    @Override
    public String encode(String message) throws EncodeException {
        return message;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {
        // Close resources (if any used)
    }
}
