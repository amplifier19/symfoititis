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
    public Optional<Integer> getPriceByProdId(int prodId, String student_id) {
         String sql = """
            SELECT pp.price FROM purchase_products pp, student_balance sb WHERE pp.id = ? AND (pp.weight = 0 OR (sb.student_id = ? AND pp.weight <= sb.weight))
        """;
        return jdbcTemplate.query(sql, new PurchaseProductPriceRowMapper(), prodId, student_id).stream().findFirst();
    }

    @Override
    public List<PurchaseProduct> getProducts(String student_id) {
        String sql = """
            SELECT pp.* FROM purchase_products pp, student_balance sb WHERE pp.weight = 0 OR (sb.student_id = ? AND pp.weight <= sb.weight) ORDER BY pp.hours
        """;
        return jdbcTemplate.query(sql, new PurchaseProductsRowMapper(), student_id);
    }

    @Override
    public Optional<PurchaseProduct> getProduct(int prodId, String student_id) {
        String sql = """
            SELECT pp.* FROM purchase_products pp, student_balance sb WHERE pp.id = ? AND (pp.weight = 0 OR (sb.student_id = ? AND pp.weight <= sb.weight))
        """;
        return jdbcTemplate.query(sql, new PurchaseProductsRowMapper(), prodId, student_id).stream().findFirst();
    }

    @Override
    public void addProduct(PurchaseProduct product) {
        String sql = """
            INSERT INTO purchase_products (price, anchor_price, hours, weight, increment_balance_weight) VALUES (?, ?, ?, ?, ?)
        """;
        jdbcTemplate.update(sql, product.price(), product.anchor_price(), product.hours(), product.weight(), product.increment_balance_weight());
    }

    @Override
    public void updateProduct(PurchaseProduct product) {
        String sql = """
            UPDATE purchase_products SET price = ?, anchor_price = ? hours = ?, weight = ?, increment_balance_weight = ? WHERE id = ?
        """;
        jdbcTemplate.update(sql, product.price(), product.anchor_price(), product.hours(), product.weight(), product.increment_balance_weight(), product.id());
    }

    @Override
    public void deleteProduct(int prodId) {
        String sql = """
            DELETE purchase_products WHERE id = ?
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
            INSERT INTO student_balance (student_id, hours, weight) VALUES (?, ?, ?)
        """;
        jdbcTemplate.update(sql, studentBalance.getStudentId(), studentBalance.getHours(), studentBalance.getWeight());
    }

    @Override
    public void updateStudentBalance(StudentBalance studentBalance) {
        String sql = """
            UPDATE student_balance SET hours = ?, weight = ? WHERE student_id = ?
        """;
        jdbcTemplate.update(sql, studentBalance.getHours(), studentBalance.getWeight(), studentBalance.getStudentId());
    }
}
