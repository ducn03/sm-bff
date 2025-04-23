package com.sm.bff.service.notify;

import com.sm.bff.service.notify.dto.NotifyDTO;
import com.sm.bff.service.notify.request.NotifyRequest;
import com.sm.jpa.domain.Notify;
import com.sm.jpa.repository.NotifyRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class NotifyGenerator {
    private final NotifyRepository notifyRepository;
    private final NotifyMapper notifyMapper;

    public NotifyGenerator(NotifyRepository notifyRepository, NotifyMapper notifyMapper) {
        this.notifyRepository = notifyRepository;
        this.notifyMapper = notifyMapper;
    }

    @Transactional
    public Uni<NotifyDTO> generate(NotifyRequest request){
        Notify notify = notifyMapper.toEntity(request);
        return notifyRepository.persist(notify)
                .map(notifyMapper::toDTO);
    }
}
