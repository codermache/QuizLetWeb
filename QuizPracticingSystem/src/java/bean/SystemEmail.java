
package bean;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SystemEmail {

    // Mat khai email cua ban
    final String fromEmail = "grouptreeswp391@gmail.com";
    final String password = "Grouptree123@";

    /**
     * Using system email to send email to <String>toEmail</String> address with
     * subject and body content
     *
     * @param toEmail
     * @param subject
     * @param body
     */
    public void sendEmail(String toEmail, String subject, String body) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
            props.put("mail.smtp.port", "587"); //TLS Port
            props.put("mail.smtp.auth", "true"); //enable authentication
            props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
            Authenticator auth = new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, password);
                }
            };
            Session session = Session.getInstance(props, auth);
            MimeMessage msg = new MimeMessage(session);
            //set message headers
            msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
            msg.addHeader("format", "flowed");
            msg.addHeader("Content-Transfer-Encoding", "8bit");
            msg.setFrom(new InternetAddress(fromEmail, "Quiz Practice System"));
            msg.setReplyTo(InternetAddress.parse(fromEmail, false));
            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8");
            msg.setSentDate(new Date());
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
            Transport.send(msg);
        } catch (MessagingException ex) {
            Logger.getLogger(SystemEmail.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(SystemEmail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
