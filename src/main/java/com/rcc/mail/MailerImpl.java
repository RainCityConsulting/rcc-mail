package com.rcc.mail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.mail.MailException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

import java.util.List;

public class MailerImpl implements Mailer {
    private static final Log log = LogFactory.getLog(MailerImpl.class);

    private JavaMailSenderImpl mailSender;

    public void setMailSender(JavaMailSenderImpl mailSender) {
        this.mailSender = mailSender;
    }

    public void send(String from, List<String> to, List<String> cc, List<String> bcc,
            String subject, String textBody)
        throws MailException
    {
        this.send(from, to, cc, bcc, subject, null, textBody);
    }

    public void send(String from, List<String> to, List<String> cc, List<String> bcc,
            String subject, String htmlBody, String textBody)
        throws MailException
    {
        try {
            MimeMessage message = this.mailSender.createMimeMessage();

            MimeMessageHelper helper = null;
            if (textBody != null && htmlBody != null) {
                helper = new MimeMessageHelper(message, true);
            } else {
                helper = new MimeMessageHelper(message, false);
            }

            helper.setTo(to.toArray(new String[0]));
            if (cc != null) { helper.setCc(cc.toArray(new String[0])); }
            if (bcc != null) { helper.setBcc(bcc.toArray(new String[0])); }
            helper.setFrom(from);
            helper.setSubject(subject);

            if (textBody != null && htmlBody != null) {
                helper.setText(textBody, htmlBody);
            } else if (htmlBody != null) {
                helper.setText(htmlBody, true);
            } else {
                helper.setText(textBody, false);
            }

            this.mailSender.send(message);
        } catch (javax.mail.MessagingException e) {
            throw new MailPreparationException(e);
        }
    }
}
