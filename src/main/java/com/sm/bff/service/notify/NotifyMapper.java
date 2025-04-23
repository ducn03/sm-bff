package com.sm.bff.service.notify;

import com.sm.bff.service.notify.dto.NotifyDTO;
import com.sm.bff.service.notify.request.NotifyRequest;
import com.sm.jpa.domain.Notify;
import jakarta.inject.Singleton;

@Singleton
public class NotifyMapper {
    public NotifyDTO toDTO(Notify input){
        NotifyDTO output = new NotifyDTO();

        output.setId(input.getId());
        // output.setUserId();
        output.setType(input.getType());
        output.setTitle(input.getTitle());
        output.setContent(input.getContent());
        output.setCta(input.getCta());
        output.setRead(input.isRead);
        output.setNotifyDate(input.getNotifyDate());

        return output;
    }

    public Notify toEntity(NotifyDTO input){
        Notify output = new Notify();

        output.setId(input.getId());
        // output.setUserId();
        output.setType(input.getType());
        output.setTitle(input.getTitle());
        output.setContent(input.getContent());
        output.setCta(input.getCta());
        output.setRead(input.isRead);
        output.setNotifyDate(input.getNotifyDate());

        return output;
    }

    public Notify toEntity(NotifyRequest input){
        Notify output = new Notify();

        output.setId(input.getId());
        output.setUserId(input.getUserId());
        output.setType(input.getType());
        output.setTitle(input.getTitle());
        output.setContent(input.getContent());
        output.setCta(input.getCta());
        output.setRead(input.isRead);
        output.setNotifyDate(input.getNotifyDate());

        return output;
    }
}
