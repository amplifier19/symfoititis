package gr.symfoititis.tutoring.records;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record PurchaseProduct(
        @Positive
        Integer id,
        @NotNull
        @Positive
        Integer price,
        @Positive
        Integer anchor_price,
        String message,
        @NotNull
        @Positive
        Integer hours,
        @PositiveOrZero
        Integer weight,
        Boolean increment_balance_weight
) {}
