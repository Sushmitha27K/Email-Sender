package com.example.email.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import com.example.email.entity.Email;
import com.example.email.repository.EmailRepository;

@Service
public class EmailService {
	
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TaskScheduler taskScheduler;
    @Autowired
    private EmailRepository emailRepository;
     
    public void scheduleEmail(String dateTime, String subject, String body) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime scheduledTime = LocalDateTime.parse(dateTime, formatter);

        taskScheduler.schedule(() -> sendEmails(subject, stripHtmlTags(body)),
                scheduledTime.atZone(ZoneId.systemDefault()).toInstant());
    }
private void sendEmails(String subject, String body) {
        List<Email> recipients = emailRepository.findAll();
        for (Email recipient : recipients) {
            sendEmail(recipient.getEmail(), subject, body);
        }
    }
public void sendEmail(String to, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(subject);
    message.setText(text);
    mailSender.send(message);
}
private String stripHtmlTags(String html) {
    return HtmlUtils.htmlEscape(html.replaceAll("\\<.*?\\>", ""));
}
}