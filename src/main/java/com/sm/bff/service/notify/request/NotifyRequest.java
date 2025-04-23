package com.sm.bff.service.notify.request;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class NotifyRequest {
    public Long id;
    public long userId;
    public int type;
    public String title;
    public String content;
    public String cta;
    public boolean isRead = false;
    public Timestamp notifyDate;
}
