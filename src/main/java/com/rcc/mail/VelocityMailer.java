package com.rcc.mail;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.apache.velocity.app.VelocityEngine;

import org.springframework.mail.MailException;

import java.io.StringWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;

public class VelocityMailer {
    private Mailer mailer;
    private VelocityEngine velocity;

    public void setMailer(Mailer mailer) { this.mailer = mailer; }
    public void setVelocity(VelocityEngine velocity) { this.velocity = velocity; }

    public Mailer getMailer() { return this.mailer; }

    public void mergeAndSend(String from, List<String> to, List<String> cc, List<String> bcc,
            String subjectTemplate, String htmlTemplate, String textTemplate,
            Map<String, Object> context)
        throws Exception 
    {
        this.mergeAndSend(from, to, cc, bcc, subjectTemplate, htmlTemplate, textTemplate, context,
                null);
    }

    public void mergeAndSend(String from, List<String> to, List<String> cc, List<String> bcc,
            String subjectTemplateName, String htmlTemplateName, String textTemplateName,
            Map<String, Object> context, List<Mailer.Attachment> attachments)
        throws Exception 
    {
        Context ctx = new VelocityContext(context);

        Writer writer = new StringWriter();
        this.velocity.mergeTemplate(subjectTemplateName, ctx, writer);
        writer.close();
        String subject = writer.toString();

        String textBody = null;
        if (textTemplateName != null) {
            writer = new StringWriter();
            this.velocity.mergeTemplate(textTemplateName, ctx, writer);
            writer.close();
            textBody = writer.toString();
        }

        String htmlBody = null;
        if (htmlTemplateName != null) {
            writer = new StringWriter();
            this.velocity.mergeTemplate(htmlTemplateName, ctx, writer);
            writer.close();
            htmlBody = writer.toString();
        }

        this.mailer.send(from, to, cc, bcc, subject, htmlBody, textBody, attachments);
    }       

    public void evaluateAndSend(String from, List<String> to, List<String> cc, List<String> bcc,
            String subjectTemplate, String htmlTemplate, String textTemplate,
            Map<String, Object> context, List<Mailer.Attachment> attachments)
        throws Exception 
    {
        Context ctx = new VelocityContext(context);
        this.evaluateAndSend(from, to, cc, bcc, subjectTemplate, htmlTemplate, textTemplate,
                ctx, attachments);
    }

    public void evaluateAndSend(String from, List<String> to, List<String> cc, List<String> bcc,
            String subjectTemplate, String htmlTemplate, String textTemplate,
            Context ctx, List<Mailer.Attachment> attachments)
        throws Exception 
    {
        Writer writer = new StringWriter();
        this.velocity.evaluate(ctx, writer, this.getClass().getName(), subjectTemplate);
        writer.close();
        String subject = writer.toString();

        String textBody = null;
        if (textTemplate != null) {
            writer = new StringWriter();
            this.velocity.evaluate(ctx, writer, this.getClass().getName(), textTemplate);
            writer.close();
            textBody = writer.toString();
        }

        String htmlBody = null;
        if (htmlTemplate != null) {
            writer = new StringWriter();
            this.velocity.evaluate(ctx, writer, this.getClass().getName(), htmlTemplate);
            writer.close();
            htmlBody = writer.toString();
        }

        this.mailer.send(from, to, cc, bcc, subject, htmlBody, textBody, attachments);
    }       
}
