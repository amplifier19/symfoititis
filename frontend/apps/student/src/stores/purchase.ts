import { defineStore } from "pinia";
import { ref } from "vue";

import { PurchaseProduct, StudentBalance } from "@symfoititis-frontend-monorepo/interfaces";
import { Stripe } from "@stripe/stripe-js";

export const usePurchaseStore = defineStore("purchaseStore", () => {
  const currentProduct = ref<PurchaseProduct | null>(null);
  const purchaseProducts = ref<PurchaseProduct[]>([]);
  const stripe = ref<Stripe | null>(null);
  const clientSecrets = ref<Map<number, string>>(new Map<number, string>());

  const studentBalance = ref<StudentBalance>({
    id: undefined,
    student_id: "",
    hours: 0,
    weight: 0,
  });

  return { stripe, clientSecrets, currentProduct, purchaseProducts, studentBalance };
});