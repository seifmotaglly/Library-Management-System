package com.library.entity;

import java.util.List;
import com.library.model.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Book extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(insertable = false)
    private Integer bookId;
    private String bookTitle;
    private String bookAuthor;
    private String bookPublicationDate;
    private int bookIsbn;
    @Column(insertable = false, columnDefinition = "boolean default true")
    private boolean borrowable;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patronId")
    private Patron bookPatron;

    @OneToMany(mappedBy = "book")
    private List<Borrowing> borrowingRecords;

}
