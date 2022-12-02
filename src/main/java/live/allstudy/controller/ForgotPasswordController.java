package live.allstudy.controller;

import live.allstudy.dto.UserEmailDTO;
import live.allstudy.entity.AuthorizedEntity;
import live.allstudy.entity.UserEntity;
import live.allstudy.repository.UserRepository;
import live.allstudy.service.UserService;
import live.allstudy.util.ResponseObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.Random;

/**
 * @author JIAHAO_CHAI
 */

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
public class ForgotPasswordController {
    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepo;

    @PostMapping("/reset_password")
    public ResponseEntity<ResponseObj> resetPassword(@RequestBody UserEntity input) {
        return new ResponseEntity<ResponseObj>(userService.resetPassword(input), HttpStatus.OK);
    }

    @PostMapping("/forgot_password")
    public ResponseEntity<ResponseObj> forgotPassword(@RequestBody UserEmailDTO inputEmail, HttpServletRequest request) {
        return new ResponseEntity<ResponseObj>(userService.sendResetEmail(inputEmail, request), HttpStatus.OK);
    }

    public void sendEmail(String recipientEmail, String link)
            throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("contact@shopme.com", "Shopme Support");
        helper.setTo(recipientEmail);

        String subject = "Here's the link to reset your password";

        String content = "<p>Hello,</p>"
                + "<p>You have requested to reset your password.</p>"
                + "<p>Click the link below to change your password:</p>"
                + "<p><a href=\"" + link + "\">Change my password</a></p>"
                + "<br>"
                + "<p>Ignore this email if you do remember your password, "
                + "or you have not made the request.</p>";

        helper.setSubject(subject);

        helper.setText(content, true);

        mailSender.send(message);
    }
}
