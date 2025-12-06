package gr.symfoititis.tutoring.rowMappers;

import gr.symfoititis.tutoring.records.PurchaseProduct;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PurchaseProductsRowMapper implements RowMapper<PurchaseProduct> {
    @Override
    public PurchaseProduct mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new PurchaseProduct(
                rs.getInt("id"),
                rs.getInt("price"),
                rs.getInt("anchor_price"),
                rs.getString("message"),
                rs.getInt("hours"),
                rs.getInt("weight"),
                rs.getBoolean("increment_balance_weight")
        );
    }
}
