package com.sm.bff.service.notify.dto;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class NotifyDTO {
    public long id;
    // public long userId;
    public int type;
    public String title;
    public String content;
    public String cta;
    public boolean isRead = false;
    public Timestamp notifyDate;
}
