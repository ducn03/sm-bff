package com.sm.jpa.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "U_SM")
public class SM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "template_id")
    private String templateId;

    @Column(name = "master_id")
    private long masterId;

    @Column(name = "status")
    private String status;

    @Column(name = "action_status")
    private String actionStatus;

    @Column(name = "updated_at")
    private Timestamp updatedAt;
}

