package com.sm.jpa.repository;

import com.sm.jpa.domain.Notify;
import com.sm.jpa.domain.User;
import io.quarkus.hibernate.reactive.panache.PanacheQuery;
import io.quarkus.hibernate.reactive.panache.PanacheRepositoryBase;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<User, Long> {
    public Uni<User> findByUsername(String username) {
        return find("username = ?1", username).firstResult();
    }
}
