package bg.paysafe.springboot.api.service.common.mail;

import bg.paysafe.springboot.api.payload.SuccessfulRequestViewModel;
import bg.paysafe.springboot.api.payload.mail.ContactFormRequestModel;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.io.StringWriter;

@Service
public class DefaultMailSender implements MailSender {

    private final JavaMailSender mailSender;

    private final Configuration freemarkerConfiguration;

    private final String from;

    public DefaultMailSender(
            JavaMailSender mailSender,
            Configuration freemarkerConfiguration,
            @Value("${spring.mail.username}") String from
    ) {
        this.mailSender = mailSender;
        this.freemarkerConfiguration = freemarkerConfiguration;
        this.from = from;
    }

    @Override
    public void send(String sender, String recipient, String subject, String content) throws MessagingException {
        var mimeMessage = this.mailSender.createMimeMessage();
        var helper = new MimeMessageHelper(
                mimeMessage,
                false,
                "utf-8"
        );

        mimeMessage.setContent(content, "text/html;charset=UTF-8");

        helper.setFrom(sender);
        helper.setTo(recipient);
        helper.setSubject(subject);

        try {
            this.mailSender.send(mimeMessage);

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public SuccessfulRequestViewModel contact(ContactFormRequestModel request) throws IOException, TemplateException, MessagingException {
        var writer = new StringWriter();
        this.freemarkerConfiguration
                .getTemplate("mails/contact-form.ftlh")
                .process(request, writer);

        this.send(
                this.from,
                this.from,
                "New contact request from " + request.getEmail(),
                writer.toString()
        );

        return new SuccessfulRequestViewModel(true);
    }

    @Override
    public SuccessfulRequestViewModel verifyUser(String recipientEmail) throws IOException, TemplateException, MessagingException {
        var writer = new StringWriter();

        this.freemarkerConfiguration
                .getTemplate("mails/verify-user.ftlh")
                .process(null, writer);

        this.send(
                this.from,
                recipientEmail,
                "Your identity has been verified",
                writer.toString()
        );

        return new SuccessfulRequestViewModel(true);
    }

}
