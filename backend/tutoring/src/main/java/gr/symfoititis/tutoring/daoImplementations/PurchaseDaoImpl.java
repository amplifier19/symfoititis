package gr.symfoititis.tutoring.daoImplementations;

import gr.symfoititis.tutoring.dao.PurchaseDao;
import gr.symfoititis.tutoring.records.PurchaseProduct;
import gr.symfoititis.tutoring.entities.StudentBalance;
import gr.symfoititis.tutoring.rowMappers.PurchaseProductPriceRowMapper;
import gr.symfoititis.tutoring.rowMappers.PurchaseProductsRowMapper;
import gr.symfoititis.tutoring.rowMappers.StudentBalanceRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PurchaseDaoImpl implements PurchaseDao {
    private final JdbcTemplate jdbcTemplate;

    public PurchaseDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Long> getPriceByProdId(int prodId) {
        String sql = """
            SELECT price FROM products WHERE id = ?
        """;
        return jdbcTemplate.query(sql, new PurchaseProductPriceRowMapper(), prodId).stream().findFirst();
    }

    @Override
    public List<PurchaseProduct> getProducts() {
        String sql = """
            SELECT * FROM products
        """;
        return jdbcTemplate.query(sql, new PurchaseProductsRowMapper());
    }

    @Override
    public Optional<PurchaseProduct> getProduct(int prodId) {
        String sql = """
            SELECT * FROM products where id = ?
        """;
        return jdbcTemplate.query(sql, new PurchaseProductsRowMapper(), prodId).stream().findFirst();
    }

    @Override
    public void addProduct(PurchaseProduct product) {
        String sql = """
            INSERT INTO products (price, hours) VALUES (?, ?)
        """;
        jdbcTemplate.update(sql, product.price(), product.hours());
    }

    @Override
    public void updateProduct(PurchaseProduct product) {
        String sql = """
            UPDATE products SET price = ?, hours = ? WHERE id = ?
        """;
        jdbcTemplate.update(sql, product.price(), product.hours(), product.id());
    }

    @Override
    public void deleteProduct(int prodId) {
        String sql = """
            DELETE products WHERE id = ?
        """;
        jdbcTemplate.update(sql, prodId);
    }

    @Override
    public Optional<StudentBalance> getStudentBalance(String student_id) {
        String sql = """
            SELECT * FROM student_balance where student_id = ?
        """;
        return jdbcTemplate.query(sql, new StudentBalanceRowMapper(), student_id).stream().findFirst();
    }

    @Override
    public void addStudentBalance(StudentBalance studentBalance) {
        String sql = """
            INSERT INTO student_balance (student_id, hours, is_premium) VALUES (?, ?, ?)
        """;
        jdbcTemplate.update(sql, studentBalance.getStudentId(), studentBalance.getHours(), studentBalance.getIsPremium());
    }

    @Override
    public void updateStudentBalance(StudentBalance studentBalance) {
        String sql = """
            UPDATE student_balance SET hours = ?, is_premium = ? WHERE student_id = ?
        """;
        jdbcTemplate.update(sql, studentBalance.getStudentId(), studentBalance.getHours(), studentBalance.getIsPremium());
    }
}
