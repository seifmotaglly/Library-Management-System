package com.library.util;

import java.lang.reflect.Field;
import org.springframework.stereotype.Component;
import com.library.entity.Patron;
import com.library.model.PatronRequest;
import com.library.model.PatronResponse;

@Component
public class PatronMapper {

    public Patron toPatron(PatronRequest patronRequest) {
        return Patron.builder()
                .patronId(patronRequest.patronId())
                .patronEmail(patronRequest.patronEmail())
                .patronUsername(patronRequest.patronUsername())
                .patronFirstName(patronRequest.patronFirstName())
                .patronLastName(patronRequest.patronLastName())
                .patronPassword(patronRequest.patronPassword())
                .build();
    }

    public PatronResponse toPatronResponse(Patron patron) {
        return PatronResponse.builder()
                .patronId(patron.getPatronId())
                .patronUsername(patron.getPatronUsername())
                .patronEmail(patron.getPatronEmail())
                .patronFirstName(patron.getPatronFirstName())
                .patronLastName(patron.getPatronLastName())
                .patronPassword(patron.getPatronPassword())
                .build();
            }
            //handel exception

    public void updatePatron(Patron patron, PatronRequest patronRequest) {
        try {
            Patron updatedPatron = this.toPatron(patronRequest);
            Class<?> patronClass = Patron.class;
            Field[] patronFields = patronClass.getDeclaredFields();
            for (Field field : patronFields) {
                field.setAccessible(true);
                Object value = field.get(updatedPatron);
                if (value != null) {
                    field.set(patron, value);
                }
                field.setAccessible(false);
            }
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new RuntimeException("Failed to update paton", e);
        }
    }

}
