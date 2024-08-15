package com.library.entity;

import java.security.Principal;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "patron")
@EntityListeners(AuditingEntityListener.class)
public class Patron implements UserDetails, Principal {

    @Id
    @GeneratedValue
    @Column(insertable = false)
    private Integer patronId;
    @Column(unique = true)
    private String patronUsername;
    private String patronFirstName;
    private String patronLastName;
    @Column(unique = true)
    private String patronEmail;
    private String patronPassword;
    private boolean enabled;
    
    @OneToMany(mappedBy = "bookPatron")
    private List<Book> books;

    @OneToMany(mappedBy = "patron")
    private List<Borrowing> borrowingRecords;

    @Override
    public String getName() {
        return patronEmail;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<GrantedAuthority>();
    }

    @Override
    public String getPassword() {
        return patronPassword;
    }

    @Override
    public String getUsername() {
        return patronUsername;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getFullName() {
        return patronFirstName + " " + patronLastName;
    }

}
