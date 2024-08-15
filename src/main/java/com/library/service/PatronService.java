package com.library.service;

import java.util.List;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.library.dao.PatronDao;
import com.library.entity.Patron;
import com.library.model.PatronRequest;
import com.library.model.PatronResponse;
import com.library.util.PatronMapper;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class PatronService implements UserDetailsService{

    private final PatronDao patronDao;
    private final PatronMapper patronMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String patronUsername) throws UsernameNotFoundException {
        return patronDao.findByPatronUsername(patronUsername).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }

    @Transactional
    public UserDetails loadUserByEmail(String patronEmail) throws UsernameNotFoundException {
        return patronDao.findByPatronEmail(patronEmail).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }

    public List<PatronResponse> findAllPatrons() {
        return patronDao.findAll().stream().map(patronMapper::toPatronResponse).toList();
    }

    public PatronResponse findPatronById(Integer patronId) {
        return patronDao.findById(patronId).map(patronMapper::toPatronResponse).orElseThrow(() -> new RuntimeException("Patron not found"));

    }

    public PatronResponse updatePatron(@Valid PatronRequest patronRequest) {
        Patron authenticatedPatron = (Patron) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        patronMapper.updatePatron(authenticatedPatron, patronRequest);
        return patronMapper.toPatronResponse(authenticatedPatron);

    }

    public Integer deletePatron() {
        Integer authenticatedPatronId =  ((Patron) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getPatronId();
        patronDao.deleteById(authenticatedPatronId);
        return authenticatedPatronId;

    }


}
