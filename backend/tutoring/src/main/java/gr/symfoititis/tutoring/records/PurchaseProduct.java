package gr.symfoititis.tutoring.records;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PurchaseProduct(
        @Positive
        Integer id,
        @NotNull
        @Positive
        Long price,
        @NotNull
        @Positive
        Integer hours
) {}
