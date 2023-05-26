import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class EmailScheduler {

  public static void main(String[] args) {
    // Set the time for the email to be sent (in this case, 2 minutes from now)
    Date timeToSend = new Date(System.currentTimeMillis() + 1 * 60 * 1000);
    
    // Schedule the email to be sent at the given time
    Timer timer = new Timer();
    timer.schedule(new SendEmailTask(), timeToSend);
  }
}

class SendEmailTask extends TimerTask {

  @Override
  public void run() {
    // Recipient's email ID needs to be mentioned.
    String to = "habti915@gmail.com";
    
    // Sender's email ID needs to be mentioned
    String from = "wassil_habti@live.fr";
    
    // Assuming you are sending email from localhost
    String host = "localhost";
    
    // Get system properties
    Properties properties = System.getProperties();
    
    // Setup mail server
    properties.setProperty("mail.smtp.host", host);
    
    // Get the default Session object
    Session session = Session.getDefaultInstance(properties);
    
    try {
      // Create a default MimeMessage object
      MimeMessage message = new MimeMessage(session);
      
      // Set From: header field of the header.
      message.setFrom(new InternetAddress(from));
      
      // Set To: header field of the header.
      message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
      
      // Set Subject: header field
      message.setSubject("This is the Subject Line!");
      
      // Now set the actual message
      message.setText("wa     feeeeen a zbi");
      
      // Send message
      Transport.send(message);
      System.out.println("Sent message successfully....");
    } catch (AddressException mex) {
      mex.printStackTrace();
    } catch (MessagingException mex) {
      mex.printStackTrace();
    }
  }
}
