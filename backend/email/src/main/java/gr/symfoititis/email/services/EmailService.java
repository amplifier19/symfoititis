package gr.symfoititis.email.services;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import gr.symfoititis.common.entities.Booking;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {
    @Value("${email.sendgrid.key}")
    String sendgridKey;

    public void sendBookingEmailNotificationToTeacher() {
//        Email from = new Email("noreply@symfoititis.gr");
//        String subject = "";
//        Email to = new Email("test@example.com");
//        Content content = new Content("text/plain", "and easy to do anywhere, even with Java");
//        Mail mail = new Mail(from, subject, to, content);
//
//        SendGrid sg = new SendGrid(System.getenv("SENDGRID_API_KEY"));
//        Request request = new Request();
//        try {
//            request.setMethod(Method.POST);
//            request.setEndpoint("mail/send");
//            request.setBody(mail.build());
//            Response response = sg.api(request);
//            System.out.println(response.getStatusCode());
//            System.out.println(response.getBody());
//            System.out.println(response.getHeaders());
//        } catch (IOException ex) {
//            throw ex;
//        }
    }

    public void sendBookingEmailNotificationToStudent(Booking booking) {
        Email from = new Email("noreply@symfoititis.gr");
        String subject = "\uD83C\uDF93 Το μάθημά σου είναι έτοιμο!";
        Email to = new Email(booking.getStudent_email());
        String textContent = String.format( """
            Το μάθημά σου επιβεβαιώθηκε! \uD83C\uDF89
            Ακολουθούν όλες οι λεπτομέρειες που χρειάζεσαι:
                            
            Μάθημα: [Όνομα μαθήματος]
            Καθηγητής: %s %s
            Ημερομηνία και Ώρα: %s στις %02d:00
            Τρόπος Διεξαγωγής: Online μέσω της πλατφόρμας Συμφοιτητής
            Video Call Link: http://meet.symfoititis.gr/%s
                            
            Σημαντικό!
                            
            Φρόντισε να συνδεθείς 5 λεπτά πριν την ώρα του μαθήματος.
            Έλεγξε τη σύνδεσή σου στο internet και τον εξοπλισμό σου για να μην έχουμε απρόοπτα.
            Πώς να συνδεθείς:
                            
            Άνοιξε την ιστοσελίδα Συμφοιτητής.
            Κάνε κλικ στο κουμπί “Τα μαθήματά μου”.
            Βρες το μάθημα και πάτησε “Σύνδεση”.
            Αν έχεις οποιαδήποτε ερώτηση ή χρειάζεσαι βοήθεια, μπορείς να επικοινωνήσεις μαζί μας στο support@symfoititis.gr
                            
            Τα λέμε Σεπτέμβρη.
            Όχι, όχι πλάκα κάνω \uD83D\uDE1C
                            
            Καλό μάθημα!!!
                            
            Η ομάδα του Συμφοιτητής
        """, booking.getTeacher_firstname(), booking.getTeacher_lastname(), booking.getDate().toString(), booking.getStart_time(), booking.getRoom());
        Content content = new Content("text/plain", textContent);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(sendgridKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
        }
    }
}
