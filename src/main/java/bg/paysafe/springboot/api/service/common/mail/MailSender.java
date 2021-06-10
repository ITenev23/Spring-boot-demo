package bg.paysafe.springboot.api.service.common.mail;

import bg.paysafe.springboot.api.payload.SuccessfulRequestViewModel;
import bg.paysafe.springboot.api.payload.mail.ContactFormRequestModel;
import freemarker.template.TemplateException;

import javax.mail.MessagingException;
import java.io.IOException;

public interface MailSender {

    void send(String recipient, String receiver, String subject, String content) throws MessagingException;

    SuccessfulRequestViewModel contact(ContactFormRequestModel request) throws IOException, TemplateException, MessagingException;

    SuccessfulRequestViewModel verifyUser(String recipientEmail) throws IOException, TemplateException, MessagingException;
}
