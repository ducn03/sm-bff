package com.sm.jpa.repository;

import com.sm.jpa.domain.SM;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;

@ApplicationScoped
public class SMRepository implements PanacheRepositoryBase<SM, Long> {
    public Uni<SM> findByTemplateIdAndMasterIdOrderByIdDesc(String templateId, long masterId) {
        return find("templateId = ?1 and masterId = ?2 order by id desc", templateId, masterId).firstResult();
    }
}
