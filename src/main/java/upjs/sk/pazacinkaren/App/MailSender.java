package upjs.sk.pazacinkaren.App;

//zdroj_dependecy: https://mvnrepository.com/artifact/com.sun.mail/javax.mail/1.6.2
//https://www.youtube.com/watch?v=A7HAB5whD6I

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

	public static void send() {
		Properties properties = new Properties();
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");

		final String login = "pazacinkaren@gmail.com";
		final String password = "pazacinkaren123";

		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(login, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("pazacinkaren@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(SignInPageController.signedInUser.getEmail()));
			message.setSubject("Order - PAZacinkaren");
			message.setText("Dear " + SignInPageController.signedInUser.getName() + " "
					+ SignInPageController.signedInUser.getSurname() + ", \n"
					+ "thank you for your order! You've ordered following palacinkas: \n"
					+ OrderPageController.mailMessage() + "\n\n"
					+ "We hope you'll enjoy your meal! \n\n Sincerely, \n Yours PAZacinkaren team.");
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

}
