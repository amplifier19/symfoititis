package gr.symfoititis.email.services;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import gr.symfoititis.common.entities.Booking;
import gr.symfoititis.common.exceptions.InternalServerErrorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {
    @Value("${email.sendgrid.key}")
    String sendgridKey;

    public void sendBookingEmailNotificationToTeacher(Booking booking, String courseName) {
        Email from = new Email("noreply@symfoititis.gr");
        String subject = "\uD83D\uDCC5 Νέο μάθημα προγραμματίστηκε!";
        Email to = new Email(booking.getTeacher_email());
        String textContent = String.format( """
        Γεια σου %s %s,
        Ένα νέο μάθημα προγραμματίστηκε και είσαι ο υπεύθυνος καθηγητής! \uD83D\uDCC5
        Δες παρακάτω τις λεπτομέρειες:
        
        Μάθημα: %s
        Μαθητής: %s
        Ημερομηνία και Ώρα: %s στις %02d:00
        Τρόπος Διεξαγωγής: Online μέσω της πλατφόρμας Συμφοιτητής
        Video Call Link: http://meet.symfoititis.gr/%s
        
        Πώς να προετοιμαστείς:
            • Φρόντισε να έχεις πρόσβαση σε όλο το απαραίτητο υλικό πριν το μάθημα.
            • Έλεγξε τη σύνδεσή σου στο internet για μια ομαλή διδασκαλία.
        
        Αν για οποιονδήποτε λόγο δεν μπορείς να παραδώσεις το μάθημα, ενημέρωσε την ομάδα μας το συντομότερο δυνατό στο [email υποστήριξης].
        Αν έχεις οποιαδήποτε ερώτηση ή χρειάζεσαι βοήθεια, μπορείς να επικοινωνήσεις μαζί μας στο support@symfoititis.gr
        Τόσα χρόνια μαθητής, είχες σίγουρα φανταστεί πως θα ήταν να δίδασκες εσύ. Ώρα να βγάλεις τα απωθημένα σου (όχι ντάξει σε πειράζω).
        Φέρε τον καλύτερο σου εαυτό - η βοήθεια σου σημαίνει πολλά σε αυτά τα παιδιά.
        
        Καλό μάθημα, το χεις!!!
        
        Η ομάδα του Συμφοιτητής
        """, booking.getTeacher_firstname(), booking.getTeacher_lastname(), courseName, booking.getStudent_name(), booking.getDate(),booking.getStart_time(), booking.getRoom());
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
            throw new InternalServerErrorException("Email error");
        }
    }

    public void sendBookingEmailNotificationToStudent(Booking booking, String courseName) {
        Email from = new Email("noreply@symfoititis.gr");
        String subject = "\uD83C\uDF93 Το μάθημά σου είναι έτοιμο!";
        Email to = new Email(booking.getStudent_email());
        String textContent = String.format( """
        Το μάθημά σου επιβεβαιώθηκε! \uD83C\uDF89
        Ακολουθούν όλες οι λεπτομέρειες που χρειάζεσαι:
        
        Μάθημα: %s
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
        """, courseName, booking.getTeacher_firstname(), booking.getTeacher_lastname(), booking.getDate().toString(), booking.getStart_time(), booking.getRoom());
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
            throw new InternalServerErrorException("Email error");
        }
    }
}
