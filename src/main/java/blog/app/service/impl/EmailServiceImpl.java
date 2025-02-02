package blog.app.service.impl;

import blog.app.model.dto.EmailDto;
import blog.app.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender javaMailSender;

//    @Override
//    @Retryable(
//            value = { MessagingException.class },
//            maxAttempts = 3,
//            backoff = @Backoff(delay = 2000)
//    )
    @Async
    public void sendEmail(EmailDto emailDto) {
        try {
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setTo(emailDto.getTo());
            simpleMailMessage.setSubject(emailDto.getSubject());
            simpleMailMessage.setText(emailDto.getMessage());
            javaMailSender.send(simpleMailMessage);
            logger.info("Email has been sent to {}", emailDto.getTo());
        } catch (Exception e) {
            logger.error("Failed to send email to {}: {}", emailDto.getTo(), e.getMessage());
        }
    }

//    @Override
//    @Async
//    public void sendEmail(String[] to, String subject, String message) {
//        try {
//            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
//            simpleMailMessage.setTo(to);
//            simpleMailMessage.setSubject(subject);
//            simpleMailMessage.setText(message);
//            simpleMailMessage.setFrom("bhurgrishahjahan28@gmail.com");
//            javaMailSender.send(simpleMailMessage);
//            logger.info("Email has been sent to multiple recipients.");
//        } catch (Exception e) {
//            logger.error("Failed to send email to multiple recipients: {}", e.getMessage());
//        }
//    }

//    @Override
//    @Async
//    public void sendEmailWithHtml(String to, String subject, String htmlContent) {
//        try {
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(htmlContent, true);
//            mimeMessage.setFrom("bhurgrishahjahan28@gmail.com");
//            javaMailSender.send(mimeMessage);
//            logger.info("HTML email has been sent to {}", to);
//        } catch (MessagingException e) {
//            logger.error("Failed to send HTML email to {}: {}", to, e.getMessage());
//        }
//    }

//    @Override
//    @Async
//    public void sendEmailWithFile(String to, String subject, String message, File file) {
//        try {
//            if (file == null || !file.exists() || !file.canRead()) {
//                logger.error("The file does not exist or cannot be read: {}", file != null ? file.getAbsolutePath() : "null");
//                return;
//            }
//
//            String fileName = file.getName().toLowerCase();
//            if (!isValidFileType(fileName)) {
//                logger.error("File type not allowed: {}", fileName);
//                return;
//            }
//
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(message, false);
//            helper.setFrom("bhurgrishahjahan28@gmail.com");
//
//            FileSystemResource fileResource = new FileSystemResource(file);
//            helper.addAttachment(file.getName(), fileResource);
//
//            javaMailSender.send(mimeMessage);
//            logger.info("Email with attachment has been sent to {}", to);
//
//        } catch (MessagingException e) {
//            logger.error("Failed to send email with attachment to {}: {}", to, e.getMessage());
//        } catch (Exception e) {
//            logger.error("Unexpected error while sending email to {}: {}", to, e.getMessage());
//        }
//    }

//    // Helper method to validate file types
//    private boolean isValidFileType(String fileName) {
//        return fileName.endsWith(".png") || fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") ||
//                fileName.endsWith(".gif") || fileName.endsWith(".bmp") ||
//                fileName.endsWith(".txt") || fileName.endsWith(".pdf") ||
//                fileName.endsWith(".jar") || fileName.endsWith(".war") ||
//                fileName.endsWith(".zip");
//    }


//    @Override
//    @Async
//    public void sendEmailWithFile(String to, String subject, String message, File file) {
//        try {
//            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
//            helper.setTo(to);
//            helper.setSubject(subject);
//            helper.setText(message, false);
//            helper.setFrom("bhurgrishahjahan28@gmail.com");
//            FileSystemResource fileResource = new FileSystemResource(file);
//            helper.addAttachment(file.getName(), fileResource);
//            javaMailSender.send(mimeMessage);
//            logger.info("Email with attachment has been sent to {}", to);
//        } catch (MessagingException e) {
//            logger.error("Failed to send email with attachment to {}: {}", to, e.getMessage());
//        }
//    }
}
