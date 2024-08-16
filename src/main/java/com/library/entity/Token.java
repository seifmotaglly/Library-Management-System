package com.library.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.ForeignKey;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Token {
    
    @Id
    @GeneratedValue
    @Column(insertable = false)
    private Integer tokenId;
    @Column(unique = true)
    private String token;
    private LocalDateTime issuedAt;
    private LocalDateTime expiresAt;
    private LocalDateTime validatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patronId", nullable = false, referencedColumnName = "patronId", foreignKey = @ForeignKey(name = "fk_token_patron", foreignKeyDefinition = "foreign key (patronId) references patron(patronId) on delete cascade"))
    private Patron patron;
}
