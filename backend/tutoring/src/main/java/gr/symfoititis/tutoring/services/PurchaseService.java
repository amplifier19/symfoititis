package gr.symfoititis.tutoring.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import gr.symfoititis.common.exceptions.NotFoundException;
import gr.symfoititis.common.exceptions.PaymentException;
import gr.symfoititis.tutoring.dao.PurchaseDao;
import gr.symfoititis.tutoring.records.PurchaseProduct;
import gr.symfoititis.tutoring.entities.StudentBalance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseService {
    @Value("{stripe.apikey}")
    private String stripeApiKey;

    private final PurchaseDao purchaseDao;

    public PurchaseService(PurchaseDao purchaseDao) {
        this.purchaseDao = purchaseDao;
    }

    public String createPaymentIntent(int prodId, String student_id) throws PaymentException {
        try {
            Stripe.apiKey = this.stripeApiKey;

            Integer price = getPriceByProdId(prodId, student_id);

            PaymentIntentCreateParams params =
                    PaymentIntentCreateParams.builder()
                            .setAmount(price.longValue())
                            .setCurrency("eur")
                            .setAutomaticPaymentMethods(
                                    PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                            .setEnabled(true)
                                            .build()
                            )
                            .build();
            PaymentIntent paymentIntent = PaymentIntent.create(params);
            return paymentIntent.getClientSecret();
        } catch (StripeException ex) {
            throw new PaymentException(ex.getMessage());
        }
    }

    public Integer getPriceByProdId(int prodId, String student_id) {
        return purchaseDao.getPriceByProdId(prodId, student_id).orElseThrow(() -> new NotFoundException(String.format("Price for product %d not found", prodId)));
    }

    public List<PurchaseProduct> getProducts(String student_id) {
        return purchaseDao.getProducts(student_id);
    }

    public PurchaseProduct getProduct(int prodId, String student_id) {
        return purchaseDao.getProduct(prodId, student_id).orElseThrow(() -> new NotFoundException(String.format("Product %d not found", prodId)));
    }

    public void addProduct(PurchaseProduct product) {
        purchaseDao.addProduct(product);
    }

    public void updateProduct(PurchaseProduct product) {
        purchaseDao.updateProduct(product);
    }

    public void deleteProduct(int prodId) {
        purchaseDao.deleteProduct(prodId);
    }

    public StudentBalance getStudentBalance(String student_id) {
        return purchaseDao.getStudentBalance(student_id).orElse(null);
    }

    public void addStudentBalance(StudentBalance studentBalance) {
        purchaseDao.addStudentBalance(studentBalance);
    }

    public void updateStudentBalance(StudentBalance studentBalance) {
        purchaseDao.updateStudentBalance(studentBalance);
    }
}
