package com.sm.bff.event;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EventMessage {
    private String type;
    private String data;
}
