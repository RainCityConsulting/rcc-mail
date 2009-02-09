package com.rcc.mail;

import org.springframework.mail.MailException;

import java.util.List;

public interface Mailer {
    public void send(String from, List<String> to, List<String> cc, List<String> bcc,
            String subject, String textBody)
        throws MailException;

    public void send(String from, List<String> to, List<String> cc, List<String> bcc,
            String subject, String htmlBody, String textBody)
        throws MailException;
}
