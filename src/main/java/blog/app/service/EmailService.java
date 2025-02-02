package blog.app.service;


import blog.app.model.dto.EmailDto;

public interface EmailService {

    //Send email to single person
    void sendEmail(EmailDto emailDto);

//    // Send email to multiple persons
//    void sendEmail(String []to,String subject,String message);
//
//    //Send email with html
//    void sendEmailWithHtml(String to , String subject,String htmlContent) throws MessagingException;
//
//    //Send email with file
//    void sendEmailWithFile(String to , String subject , String message , File file);

}
