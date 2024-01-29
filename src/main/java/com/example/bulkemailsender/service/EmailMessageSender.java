package com.example.bulkemailsender.service;

import com.example.bulkemailsender.data.EmailMessageDto;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class EmailMessageSender {

    private final JavaMailSender mailSender;

    public void sendEmail(EmailMessageDto emailMessageDto) {

        MimeMessage message = mailSender.createMimeMessage();
        try {
            String recipient = emailMessageDto.getTo();
            String subject = emailMessageDto.getSubject();
            String text = emailMessageDto.getBody();

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(recipient);
            helper.setSubject(subject);

            helper.setText(text);

            mailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

//    private final EmailProviderConfig emailProviderConfig;
//
//    public void sendEmail(BulkEmailMessageDto bulkEmailMessageDto) {
//        try{
//            Properties prop=new Properties();
//            prop.putAll(emailProviderConfig.getProperties());
//
//            String from=bulkEmailMessageDto.getFrom();
//            String to=bulkEmailMessageDto.getTo();
//            String subject= bulkEmailMessageDto.getSubject();
//            String text= bulkEmailMessageDto.getBody();
//            String username=emailProviderConfig.getAuth().getPassword();
//            String password=emailProviderConfig.getAuth().getPassword();
//
//            Session session = Session.getInstance(prop, new Authenticator() {
//                @Override
//                protected PasswordAuthentication getPasswordAuthentication() {
//                    return new PasswordAuthentication(username, password);
//                }
//            });
//
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(from));
//            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
//            message.setSubject(subject);
//
//            MimeBodyPart mimeBodyPart = new MimeBodyPart();
//            mimeBodyPart.setContent(text, "text/html; charset=utf-8");
//
//            Multipart multipart = new MimeMultipart();
//            multipart.addBodyPart(mimeBodyPart);
//
//            message.setContent(multipart);
//
//            Transport.send(message);
//        }catch (MessagingException e){
//            log.error(e.getMessage(),e);
//        }
}
