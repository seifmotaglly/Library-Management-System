package com.library.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.library.entity.Borrowing;

public interface BorrowingDao extends JpaRepository<Borrowing, Integer> {

    @Query("""
                SELECT
                (COUNT (*) > 0) AS isBorrowed
                FROM Borrowing borrowingRecords
                WHERE borrowingRecords.patron.patronId = :patronId
                AND borrowingRecords.book.bookId = :bookId
                And borrowingRecords.borrowed = true
            """)

    boolean isAlreadyBorrowedByUser(Integer bookId, Integer patronId);

    @Query("""
                SELECT borrowingRecord 
                FROM Borrowing borrowingRecord
                WHERE borrowingRecord.book.bookId = :bookId
                AND borrowingRecord.patron.patronId = :patronId
                AND borrowingRecord.borrowed = true
            """)
    Optional<Borrowing> findByBookIdAndPatronId(Integer bookId, Integer patronId);

}
