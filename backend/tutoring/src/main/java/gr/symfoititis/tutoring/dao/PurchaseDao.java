package gr.symfoititis.tutoring.dao;

import gr.symfoititis.tutoring.records.PurchaseProduct;
import gr.symfoititis.tutoring.entities.StudentBalance;

import java.util.List;
import java.util.Optional;

public interface PurchaseDao {
    Optional<Long> getPriceByProdId(int prodId);
    List<PurchaseProduct> getProducts();
    Optional<PurchaseProduct> getProduct(int prodId);
    void addProduct(PurchaseProduct product);
    void updateProduct(PurchaseProduct product);
    void deleteProduct(int prodId);

    Optional<StudentBalance> getStudentBalance(String student_id);
    void addStudentBalance(StudentBalance studentBalance);
    void updateStudentBalance(StudentBalance studentBalance);
}
