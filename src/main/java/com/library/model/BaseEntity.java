package com.library.model;

import java.time.LocalDateTime;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    private LocalDateTime borrowedAt;
    private Integer borrowedBy;
    private LocalDateTime returnedAt;

}
