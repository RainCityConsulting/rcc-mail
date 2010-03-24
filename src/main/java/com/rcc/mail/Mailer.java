package com.rcc.mail;

import org.springframework.mail.MailException;
import org.springframework.core.io.InputStreamSource;

import java.util.List;

public interface Mailer {

    public static class Attachment {
        private String name;
        private InputStreamSource source;
        private String contentType;

        public Attachment(String name, InputStreamSource source, String contentType) {
            this.name = name;
            this.source = source; 
            this.contentType = contentType;
        }

        public Attachment(String name, InputStreamSource source) {
            this.name = name;
            this.source = source; 
        }

        public void setName(String name) { this.name = name; }
        public String getName() { return this.name; }

        public void setSource(InputStreamSource source) { this.source = source; }
        public InputStreamSource getSource() { return this.source; }

        public void setContentType(String contentType) { this.contentType = contentType; }
        public String getContentType() { return this.contentType; }
    }

    public void send(String from, String to, List<String> cc, List<String> bcc,
            String subject, String textBody)
        throws MailException;

    public void send(String from, List<String> to, List<String> cc, List<String> bcc,
            String subject, String textBody)
        throws MailException;

    public void send(String from, String to, List<String> cc, List<String> bcc,
            String subject, String htmlBody, String textBody, List<Attachment> attachments)
        throws MailException;

    public void send(String from, List<String> to, List<String> cc, List<String> bcc,
            String subject, String htmlBody, String textBody, List<Attachment> attachments)
        throws MailException;

    public void send(String from, String replyTo, List<String> to, List<String> cc, List<String> bcc,
            String subject, String htmlBody, String textBody, List<Attachment> attachments)
        throws MailException;

    public void send(String from, String to, List<String> cc, List<String> bcc,
            String subject, String htmlBody, String textBody)
        throws MailException;

    public void send(String from, List<String> to, List<String> cc, List<String> bcc,
            String subject, String htmlBody, String textBody)
        throws MailException;
}
