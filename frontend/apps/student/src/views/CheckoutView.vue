<script setup lang="ts">
import { Toasts, Page, Masterhead } from '@symfoititis-frontend-monorepo/ui';
import Product from '../components/Product.vue';
import { loadStripe } from '@stripe/stripe-js'
import { PurchaseDataService } from '../core/services/purchase/purchase-data.service';
import { useRoute } from 'vue-router';
import { onMounted, onUnmounted, ref } from 'vue';
import { usePurchaseStore } from '../stores/purchase';
import { storeToRefs } from 'pinia';
import { useErrorStore } from '@symfoititis-frontend-monorepo/stores';
import Loading from 'modules/ui/src/components/Loading.vue';

const route = useRoute();
const purchaseStore = usePurchaseStore();
const { stripe, currentProduct } = storeToRefs(purchaseStore);
const errorStore = useErrorStore();
const purchaseDataService = PurchaseDataService.getPurchaseDataFactory();
const loading = ref<boolean>(false);

const initStripe = async () => {
    try {
        if (stripe.value) {
            return;
        }
        stripe.value = await loadStripe('pk_test_51O8N4IF0w3osNqiOpOon6xWtqoMrs7L4OhTNkZLUxgaYLZZudHbpe3X4sdRM5lZsstDXBU7SFDrrOdV3qdE7WuNg006gJFJwYL');
    } catch (error) {
        errorStore.addError('Failed to load payment gateway');
    }
};

const createStripeElement = async (clientSecret: string) => {
    if (!stripe.value) {
        errorStore.addError('Failed to load payment gateway');
        return;
    }
    const appearance = { /* appearance */ };
    const elements = stripe.value.elements({ clientSecret, appearance });

    const paymentElement = elements.create('payment');
    paymentElement.mount('#payment-element');
};

onMounted(async () => {
    // loading.value = true;

    await purchaseDataService.getPurchaseProducts();
    if (currentProduct.value === null && route.params.p_id) {
        const prod = purchaseStore.purchaseProducts.find(p => p.id === Number(route.params.p_id)) || null;
        if (prod) {
            currentProduct.value = prod;
        } else {
            errorStore.addError('Product not found');
            loading.value = false;
            return;
        }
    }

    await initStripe();
    
    if (!stripe.value) {
        errorStore.addError('Failed to load payment gateway');
        loading.value = false;
        return;
    }

    const clientSecret = await purchaseDataService.createPaymentIntent(Number(route.params.p_id))
    await createStripeElement(clientSecret);

    loading.value = false;
})

onUnmounted(() => {
    currentProduct.value = null;
})
</script>

<template>
    <Toasts />
    <Page>
        <template v-slot:header>
            <Masterhead :selected="-1" />
        </template>
        <template v-slot:main>
            <section class="wrapper checkout-wrapper">
                <Loading v-if="loading || !currentProduct" />
                <div v-else class="checkout-card">
                    <div class="product-section">
                        <Product :product="currentProduct" />
                    </div>
                    
                    <div class="separator">
                        <span class="separator-line"></span>
                        <span class="separator-text md-font lt-fw">Payment Details</span>
                        <span class="separator-line"></span>
                    </div>
                    
                    <form class="payment-section" id="payment-form" @submit.prevent="">
                        <div id="payment-element" />
                        <button id="submit" class="pay-button lg-font md-fw">
                            Complete Purchase
                        </button>
                    </form>
                </div>
            </section>
        </template>
    </Page>
</template>

<style scoped>
.checkout-wrapper {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    padding: 1rem;
    min-height: calc(100vh - 80px);
    max-height: calc(100vh - 80px);
    overflow: hidden;
}

.checkout-card {
    width: min(1100px, 95%);
    max-height: 100%;
    background-color: var(--white);
    border: var(--main-border);
    border-radius: 14px;
    padding: 2rem 3rem;
    box-shadow: 0 12px 32px rgba(0, 0, 0, 0.12);
    display: flex;
    flex-direction: column;
    gap: 1.5rem;
    overflow-y: auto;
}

.product-section {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 0.5rem;
}

.section-title {
    color: var(--orange);
    margin: 0;
    text-align: center;
    text-transform: uppercase;
    letter-spacing: 0.5px;
}

.separator {
    display: flex;
    align-items: center;
    gap: 1rem;
    margin: 0.5rem 0;
}

.separator-line {
    flex: 1;
    height: 1px;
    background: linear-gradient(to right, transparent, var(--orange), transparent);
    opacity: 0.3;
}

.separator-text {
    color: var(--gray);
    text-transform: uppercase;
    letter-spacing: 0.8px;
    white-space: nowrap;
}

.payment-section {
    display: flex;
    flex-direction: column;
    gap: 1rem;
}

#payment-element {
    padding: 0.5rem 0;
}

.pay-button {
    width: 100%;
    padding: 14px 28px;
    background-color: var(--orange);
    color: var(--white);
    border: 2px solid var(--orange);
    border-radius: 10px;
    cursor: pointer;
    text-transform: uppercase;
    letter-spacing: 1px;
    transition: all 180ms cubic-bezier(.2, .9, .2, 1);
    box-shadow: 0 4px 12px rgba(245, 145, 32, 0.3);
    margin-top: 0.5rem;
}

.pay-button:hover {
    background-color: var(--orange-shadow);
    border-color: var(--orange-shadow);
    transform: translateY(-2px);
    box-shadow: 0 8px 20px rgba(245, 145, 32, 0.4);
}

.pay-button:active {
    transform: translateY(0);
    box-shadow: 0 2px 8px rgba(245, 145, 32, 0.3);
}

.pay-button:focus-visible {
    outline: 3px solid rgba(255, 156, 0, 0.3);
    outline-offset: 3px;
}

.pay-button:disabled {
    background-color: var(--light-gray);
    border-color: var(--light-gray);
    cursor: not-allowed;
    transform: none;
    box-shadow: none;
}

@media screen and (max-width: 768px) {
    .checkout-wrapper {
        padding: 0.75rem;
    }

    .checkout-card {
        width: 100%;
        padding: 1.5rem 1.75rem;
        gap: 1.25rem;
    }

    .section-title {
        font-size: 18px;
    }

    .separator {
        gap: 0.75rem;
        margin: 0.25rem 0;
    }

    .separator-text {
        font-size: 13px;
    }

    .pay-button {
        padding: 12px 20px;
        font-size: 16px;
    }
}

@media screen and (max-width: 480px) {
    .checkout-wrapper {
        padding: 0.5rem;
    }

    .checkout-card {
        padding: 1.25rem 1.5rem;
        gap: 1rem;
    }

    .separator-text {
        font-size: 12px;
    }
}
</style>