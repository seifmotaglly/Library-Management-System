package com.library.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import com.library.model.EmailTemplate;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailService {

    
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine springTemplateEngine;

    @Async
    public void sendEmail(String to, String firstName, EmailTemplate emailTemplate, String confirmationUrl,
            String activationCode, String subject) throws MessagingException {
        String template;
        if (emailTemplate == null) {
            template = "confirmation";
        } else {
            template = emailTemplate.getName();
        }
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
        Map<String, Object> properties = new HashMap<>();
        properties.put("firstName", firstName);
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("activationCode", activationCode);

        Context context = new Context();
        //TODO: Gmail mail 
        context.setVariables(properties);
        mimeMessageHelper.setFrom("Library Management System <librarian@library.management.com>");
        mimeMessageHelper.setTo(to);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(springTemplateEngine.process(template, context), true);

        javaMailSender.send(mimeMessage);
    }
}
