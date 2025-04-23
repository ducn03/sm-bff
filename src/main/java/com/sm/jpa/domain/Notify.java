package com.sm.jpa.domain;

import com.sm.jpa.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Table(name = "U_NOTIFY")
@Entity
@Getter
@Setter
public class Notify extends PanacheEntity {

    @Column(name = "user_id", nullable = false)
    public long userId;

    @Column(name = "type")
    public int type;

    @Column(name = "title", length = 512)
    public String title;

    @Column(name = "content", length = 1000)
    public String content;

    @Column(name = "cta", length = 1000)
    public String cta;

    @Column(name = "is_read")
    public boolean isRead = false;

    @Column(name = "notify_date")
    public Timestamp notifyDate;
}
