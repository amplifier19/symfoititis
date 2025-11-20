package gr.symfoititis.tutoring.controllers;

import gr.symfoititis.common.records.Response;
import gr.symfoititis.tutoring.records.PurchaseProduct;
import gr.symfoititis.tutoring.entities.StudentBalance;
import gr.symfoititis.tutoring.services.PurchaseService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gr.symfoititis.common.utils.RoleValidation.*;

@Validated
@RestController
public class PurchaseController {
    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping("/products")
    ResponseEntity<Response> getPriceByProdId(
            @NotBlank
            @RequestHeader("X-Role")
            String role,
            @NotNull
            @NotBlank
            @RequestHeader("X-User-Id")
            String user_id
    ) {
        isStudentOrAdmin(role);
        List<PurchaseProduct> products = purchaseService.getProducts(user_id);
        return ResponseEntity.ok(new Response(200, products));
    }

    @GetMapping("/products/{id}")
    ResponseEntity<Response> getPriceByProdId(
        @Positive
        @PathVariable(value="id", required = true)
        int id,
        @NotBlank
        @RequestHeader("X-Role")
        String role,
        @NotNull
        @NotBlank
        @RequestHeader("X-User-Id")
        String user_id
    ) {
        isStudent(role);
        PurchaseProduct product = purchaseService.getProduct(id, user_id);
        return ResponseEntity.ok(new Response(200, product));
    }

    @PostMapping("/product")
    ResponseEntity<Response> addProduct(
            @Valid
            @RequestBody
            PurchaseProduct product,
            @NotBlank
            @RequestHeader("X-Role")
            String role
    ) {
        isAdmin(role);
        purchaseService.addProduct(product);
        return ResponseEntity.ok(new Response(200, "Successfully added one product"));
    }

    @PutMapping("/product")
    ResponseEntity<Response> updateProduct(
            @Valid
            @RequestBody
            PurchaseProduct product,
            @NotBlank
            @RequestHeader("X-Role")
            String role
    ) {
        isAdmin(role);
        purchaseService.updateProduct(product);
        return ResponseEntity.ok(new Response(200, "Successfully updated one product"));
    }

    @DeleteMapping("/products/{id}")
    ResponseEntity<Response> deleteProduct(
            @Positive
            @PathVariable(value="id", required = true)
            int id,
            @NotBlank
            @RequestHeader("X-Role")
            String role
    ) {
        isAdmin(role);
        purchaseService.deleteProduct(id);
        return ResponseEntity.ok(new Response(200, "Successfully deleted one product"));
    }

    @GetMapping("/balance")
    ResponseEntity<Response> getStudentBalance(
            @NotBlank
            @RequestHeader("X-User-Id")
            String student_id,
            @NotBlank
            @RequestHeader("X-Role")
            String role
    ) {
        isStudent(role);
        StudentBalance studentBalance = purchaseService.getStudentBalance(student_id);
        return ResponseEntity.ok(new Response(200, studentBalance));
    }

    // stripe webhook
    @PostMapping("/webhook/updateBalance")
    ResponseEntity<Response> updateStudentBalance(
            @NotBlank
            @RequestHeader("X-User-Id")
            String student_id,
            @Valid
            @RequestBody
            PurchaseProduct product
    ) {
        StudentBalance balance = purchaseService.getStudentBalance(student_id);
        int weight;
        if (balance == null) {
            weight = product.increment_balance_weight() ? 1 : 0;
            balance = new StudentBalance(student_id, product.hours(), weight);
            purchaseService.addStudentBalance(balance);
        } else {
            weight = balance.getWeight();
            if (product.increment_balance_weight()) balance.setWeight(weight+1);
            purchaseService.updateStudentBalance(balance);
        }
        return ResponseEntity.ok(new Response(200, "Successfully updated balance"));
    }
}
