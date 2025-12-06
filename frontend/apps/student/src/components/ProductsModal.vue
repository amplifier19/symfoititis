<script setup lang="ts">
import { storeToRefs } from 'pinia';
import { usePurchaseStore } from "../stores/purchase";
import Product from "./Product.vue";

const emit = defineEmits<{
    (e: "close-modal", event: Event): void;
}>();

const purchaseStore = usePurchaseStore();
const { currentProduct, studentBalance, purchaseProducts } = storeToRefs(purchaseStore);

const selectProduct = (index: number) => {
    currentProduct.value = purchaseProducts.value[index];
    console.log(currentProduct.value);
};

const handleCloseModal = (event: Event) => {
    currentProduct.value = null;
    emit("close-modal", event);
};

</script>

<template>
    <section class="products-wrapper">
        <div class="hours-balance-cnt">
            <div class="hours-balance">
                <div class="hours-img-cnt">
                    <img class="hours-img" src="../components/icon.svg" alt="">
                </div>
                <span class="balance-value lg-font sb-fw">{{ studentBalance.hours }}</span>
            </div>
        </div>
        <button class="modal-close" type="button" aria-label="Close products modal" @click="handleCloseModal">
            <img src="./close_orange.svg" alt="close" />
        </button>

        <div v-if="currentProduct == null" class="purchase-cta-cnt">
            <p class="purchase-cta xl-font lt-fw">ΑΓΟΡΑΣΕ ΩΡΕΣ &#8594; ΚΛΕΙΣΕ ΙΔΙΑΙΤΕΡΑ</p>
            <p class="purchase-cta lg-font lt-fw">χωρίς συνδρομές ό,τι πάρεις θα κάνεις</p>
        </div>
        <div v-else class="purchase-cta-cnt">
            <p class="purchase-cta xl-font lt-fw">{{ currentProduct.message || "" }}</p>
        </div>


        <div class="products-grid-cnt">
            <div v-if="purchaseProducts.length > 0" ref="productsGrid" class="products-grid">
                <Product @click="selectProduct(index)" v-for="(product, index) in purchaseProducts" :key="product.id"
                    :product="product" :data-index="index" />
            </div>
        </div>

        <section class="purchase-btn-cnt">
            <RouterLink :to="{ name: 'checkout', params: { p_id: currentProduct?.id } }" v-if="currentProduct != null"
                class="purchase-btn" id="proceed-to-purchase-btn">
                <span>ΑΓΟΡΑ</span>
            </RouterLink>
            <div v-else class="purchase-btn" id="choose-product-btn">
                <span>ΕΠΕΛΕΞΕ ΠΑΚΕΤΟ</span>
            </div>
        </section>
    </section>
</template>

<style scoped>
.products-wrapper {
    display: flex;
    flex-direction: column;
    width: min(1100px, 92%);
    position: fixed;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    z-index: 1200;
    background-color: var(--white);
    border: var(--main-border);
    border-radius: 14px;
    padding: 36px 44px 32px;
    box-shadow: 0 18px 48px rgba(0, 0, 0, 0.18);
    max-height: min(90vh, 880px);
    overflow-y: auto;
}

.modal-close {
    position: absolute;
    right: 18px;
    top: 18px;
    width: 42px;
    height: 42px;
    background: var(--white);
    border: 2px solid var(--orange);
    border-radius: 999px;
    padding: 6px;
    display: inline-flex;
    align-items: center;
    justify-content: center;
    cursor: pointer;
    box-shadow: 0 8px 18px rgba(0, 0, 0, 0.10);
    transition: transform 160ms cubic-bezier(.2, .9, .2, 1), box-shadow 160ms ease;
    z-index: 1250;
}

.modal-close img {
    width: 18px;
    height: 18px;
    display: block;
}

.modal-close:hover {
    transform: translateY(-3px);
    box-shadow: 0 12px 24px rgba(0, 0, 0, 0.14);
}

.modal-close:focus-visible {
    outline: 3px solid rgba(255, 156, 0, 0.14);
    outline-offset: 3px;
}

.purchase-cta-cnt {
    text-align: center;
    margin: 12px 0 28px;
    color: var(--orange);
    min-height: 72px;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
}

.products-grid-cnt {
    display: flex;
    justify-content: center;
    align-items: center;
}

.products-grid {
    width: 100%;
    display: grid;
    gap: 24px;
    grid-template-columns: repeat(3, 1fr) !important;
    align-items: start;
}

.products-grid>* {
    will-change: transform, opacity;
}

.products-wrapper::-webkit-scrollbar {
    width: 12px;
}

.products-wrapper::-webkit-scrollbar-thumb {
    background: rgba(0, 0, 0, 0.12);
    border-radius: 8px;
}

.hours-balance-cnt {
    margin: 10px 0 6px;
    padding: 0 1.5rem;
}

.hours-balance {
    display: flex;
    flex-direction: row;
    align-items: center;
}

.hours-img-cnt {
    width: 32px;
    height: 32px;
    margin-right: 8px;
}

.balance-value {
    color: var(--orange)
}

.purchase-btn-cnt {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 46px 0 12px 0;
}

.purchase-btn {
    display: flex;
    align-items: center;
    justify-content: center;
    background-repeat: no-repeat;
    width: 220px;
    height: 60px;
    color: var(--orange);
}

#proceed-to-purchase-btn {
    background-image: url("/svg/booking-cta-btn.svg");
    cursor: pointer;
}

#proceed-to-purchase-btn>span {
    margin-bottom: 12px;
    font-family: "Geologica-Medium";
}

#choose-product-btn {
    background-image: url("/svg/booking-dashed-cta-btn.svg");
}

@media screen and (max-width: 1800px) {
    .hours-balance-cnt {
        padding: 0 2rem;
    }
}

@media screen and (max-width: 1300px) {
    .hours-balance-cnt {
        padding: 0 1rem;
    }
}

@media screen and (max-width: 1000px) {
    .hours-img-cnt {
        width: 20px;
        height: 20px;
    }

}

@media screen and (max-width: 900px) {
    .products-wrapper {
        padding: 20px;
    }

    .products-grid {
        gap: 14px;
    }

    .purchase-cta {
        font-size: 18px;
    }

    .purchase-cta-cnt {
        min-height: 64px;
    }
}

@media screen and (max-width: 520px) {
    .products-wrapper {
        padding: 12px;
    }

    .products-grid {
        gap: 10px;
    }
}
</style>