package com.sm.jpa.repository;

import com.sm.bff.constant.RestConstant;
import com.sm.jpa.domain.Notify;
import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;

import java.util.List;

public class NotifyRepository implements PanacheRepositoryBase<Notify, Long> {
    public PanacheQuery<Notify> findQueryNotifyByUserId(long userId, int pageIndex, int pageSize) {
        return find("userId = ?1 ORDER BY notifyDate DESC", userId).page(pageIndex, pageSize);
    }

    public Uni<List<Notify>> findByUserId(long userId) {
        return find("userId = ?1 ORDER BY notifyDate DESC", userId).list();
    }

    public Uni<Long> countUnread(long userId) {
        return find("userId = ?1", userId).count();
    }

    public Uni<List<Notify>> findByUserIdAndIsRead(long userId, boolean isRead) {
        return find("userId = ?1 and isRead = ?2", userId, isRead).list();
    }

    public Uni<List<Notify>> findByIds(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return Uni.createFrom().item(List.of());
        }
        return find("id IN (?1)", ids)
                .list();
    }
}
