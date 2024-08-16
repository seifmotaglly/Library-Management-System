 package com.library.entity;

import com.library.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Borrowing extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(insertable = false)
    private Integer borrowingId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patronId")
    private Patron patron;
    @ManyToOne
    @JoinColumn(name = "bookId")
    private Book book;
    private boolean borrowed;

}
