package com.antonio.sistema_stock.entities;

import jakarta.persistence.Embeddable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Embeddable
public class Audit {
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @PrePersist
    public void prePersist(){
        this.created_at= LocalDateTime.now(ZoneId.of("GMT-3")).withNano(0);
    }
    @PreUpdate
    public  void preUpdate(){
        this.updated_at= LocalDateTime.now(ZoneId.of("GMT-3")).withNano(0);
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }
}
