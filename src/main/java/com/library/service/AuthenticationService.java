package com.library.service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.library.dao.PatronDao;
import com.library.dao.TokenDao;
import com.library.entity.Patron;
import com.library.entity.Token;
import com.library.model.AuthenticateRequest;
import com.library.model.AuthenticateResponse;
import com.library.model.EmailTemplate;
import com.library.model.RegisterRequest;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final PatronDao patronDao;
    private final TokenDao tokenDao;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Transactional
    public void register(RegisterRequest registerRequest) throws MessagingException {
        Patron patron = Patron.builder()
                .patronUsername(registerRequest.getPatronUsername())
                .patronEmail(registerRequest.getPatronEmail())
                .patronFirstName(registerRequest.getPatronFirstName())
                .patronLastName(registerRequest.getPatronLastName())
                .patronPassword(passwordEncoder.encode(registerRequest.getPatronPassword()))
                .enabled(false)
                .build();
        patronDao.save(patron);
        sendValidationEmail(patron);
    }

    private void sendValidationEmail(Patron patron) throws MessagingException {
        String token = generateAndSaveActivationToken(patron);

        emailService.sendEmail(patron.getPatronEmail(), patron.getPatronFirstName(), EmailTemplate.ACTIVATION,
                "http://localhost:4200/activate", token, "Activate your account");// TODO: change url

    }

    @Transactional
    private String generateAndSaveActivationToken(Patron patron) {

        String generatedToken = GenerateActivationCode(8);
        Token token = Token.builder()
                .token(generatedToken)
                .issuedAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(10))
                .patron(patron)
                .build();

        tokenDao.save(token);
        return generatedToken;

    }

    @Transactional
    private String GenerateActivationCode(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder code = new StringBuilder(length);
        SecureRandom secireRandom = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int index = secireRandom.nextInt(chars.length());
            code.append(chars.charAt(index));
        }
        return code.toString();
    }

    public AuthenticateResponse authenticate(@Valid AuthenticateRequest authenticateRequest) {
        Authentication auth = new UsernamePasswordAuthenticationToken(authenticateRequest.getPatronEmail(),
        authenticateRequest.getPatronPassword());
        Authentication authenticate = authenticationManager.authenticate(auth);
        Map<String, Object> claims = new HashMap<String, Object>();
        Patron patron = (Patron) authenticate.getPrincipal();
        claims.put("fullName", patron.getFullName());
        String token = jwtService.generateToken(claims, patron);

        return AuthenticateResponse.builder().token(token).build();
    }

    @Transactional
    public void activateAccount(String token) throws MessagingException {
        Token savedToken = tokenDao.findByToken(token).orElseThrow(() -> new RuntimeException("Token not found")); // TODO:
                                                                                                                   // handle
                                                                                                                   // exception

        if (savedToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            sendValidationEmail(savedToken.getPatron());
            throw new RuntimeException("Token expired, new token sent to your email");
        }
        Patron patron = tokenDao.findById(savedToken.getPatron().getPatronId()).get().getPatron();
        patron.setEnabled(true);
        patronDao.save(patron);
        savedToken.setValidatedAt(LocalDateTime.now());
        tokenDao.save(savedToken);
    }

}
