package com.example.bulkemailsender.service;

import com.example.bulkemailsender.config.EmailProviderConfig;
import com.example.bulkemailsender.data.BulkEmailMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailMessageService {
    private final EmailProviderConfig emailProviderConfig;

    public void sendEmail(BulkEmailMessageDto bulkEmailMessageDto) {
        try{
            Properties prop=new Properties();
            prop.putAll(emailProviderConfig.getProperties());

            String from=bulkEmailMessageDto.getFrom();
            String to=bulkEmailMessageDto.getTo();
            String subject= bulkEmailMessageDto.getSubject();
            String text= bulkEmailMessageDto.getBody();
            String username=emailProviderConfig.getAuth().getPassword();
            String password=emailProviderConfig.getAuth().getPassword();

            Session session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(text, "text/html; charset=utf-8");

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            message.setContent(multipart);

            Transport.send(message);
        }catch (MessagingException e){
            log.error(e.getMessage(),e);
        }
    }
}
