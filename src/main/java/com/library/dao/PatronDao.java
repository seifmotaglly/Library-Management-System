package com.library.dao;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.library.entity.Patron;

import jakarta.transaction.Transactional;

@Repository
public interface PatronDao extends JpaRepository<Patron, Integer> {

    Optional<Patron> findByPatronUsername(String patronUsername);

    Optional<Patron> findByPatronEmail(String patronEmail);

    @Modifying
    @Transactional
    @Query(value = """
            DELETE FROM patron, book, borrowing USING patron
            INNER JOIN book INNER JOIN borrowing
            WHERE patron.patron_id = :patronId AND book.patron_id =  patron.patron_id
        """, nativeQuery = true)

    @Override
    void deleteById(@SuppressWarnings("null") Integer patronId);

}
