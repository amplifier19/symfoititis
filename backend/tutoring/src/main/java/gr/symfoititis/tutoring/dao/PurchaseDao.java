package gr.symfoititis.tutoring.dao;

import gr.symfoititis.tutoring.records.PurchaseProduct;
import gr.symfoititis.tutoring.entities.StudentBalance;

import java.util.List;
import java.util.Optional;

public interface PurchaseDao {
    Optional<Integer> getPriceByProdId(int prodId, String student_id);
    List<PurchaseProduct> getProducts(String student_id);
    Optional<PurchaseProduct> getProduct(int prodId, String student_id);
    void addProduct(PurchaseProduct product);
    void updateProduct(PurchaseProduct product);
    void deleteProduct(int prodId);

    Optional<StudentBalance> getStudentBalance(String student_id);
    void addStudentBalance(StudentBalance studentBalance);
    void updateStudentBalance(StudentBalance studentBalance);
}
