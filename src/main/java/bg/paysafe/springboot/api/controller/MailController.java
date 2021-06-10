package bg.paysafe.springboot.api.controller;

import bg.paysafe.springboot.api.payload.SuccessfulRequestViewModel;
import bg.paysafe.springboot.api.payload.mail.ContactFormRequestModel;
import bg.paysafe.springboot.api.service.common.mail.MailSender;
import freemarker.template.TemplateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.IOException;

import static bg.paysafe.springboot.api.constant.URLMappings.MAIL_BASE;

@RestController
public class MailController {

    private final MailSender mailSender;

    public MailController(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @PostMapping(value = MAIL_BASE)
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessfulRequestViewModel> contactUs(
            @RequestBody @Valid ContactFormRequestModel request
    ) throws MessagingException, IOException, TemplateException {
        return new ResponseEntity<>(this.mailSender.contact(request), HttpStatus.CREATED);
    }

}
