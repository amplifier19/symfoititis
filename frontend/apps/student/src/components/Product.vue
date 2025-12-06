<script setup lang="ts">
import { PurchaseProduct } from "@symfoititis-frontend-monorepo/interfaces";

const props = defineProps<{
  product: PurchaseProduct
}>();
</script>

<template>
  <div class="product-wrapper" tabindex="0">
    <table class="product-cnt">
      <tbody>
        <tr class="product-section">
          <td class="product-hours"><span class="hours-text xl-font sb-fw">{{ props.product.hours }}</span></td>
        </tr>
        <tr class="product-section">
          <td class="product-prices"><span class="price-text anchor-price lg-font">{{ props.product.anchor_price ==
            0
            ? "" : `${props.product.anchor_price}&#8364;` }}</span><span class="price-text price xl-font sb-fw">{{
                props.product.price }}&#8364;</span></td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>
.product-wrapper {
  /* min-height: 236px;  */
  min-height: fit-content;
  display: flex;
  align-items: center;
  justify-content: center;
  box-sizing: border-box;
  cursor: pointer
}

.product-wrapper:focus {
  outline: 2px dashed var(--orange);
  outline-offset: 6px;
  border-radius: 10px;
}

.product-cnt {
  height: 100%;
  width: 100%;
  display: flex;
  justify-content: center;
  flex-direction: column;
  align-items: center;
}

.product-section {
  height: 50%;
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
}

.product-hours {
  height: clamp(92px, 9vw, 120px);
  width: clamp(92px, 10vw, 120px);
  background-image: url("/svg/logo_icon_filled.svg");
  background-repeat: no-repeat;
  background-position: center center;
  background-size: contain;
  display: flex;
  justify-content: center;
  align-items: center;
  padding-bottom: 12px;
  color: white;
}

.product-prices {
  height: 50%;
  display: flex;
  justify-content: center;
  align-items: flex-end;
  flex-direction: column;
}

.price-text {
  margin: 0 auto;
}

.price-text {
  display: inline-block;
  position: relative;
  line-height: 1.2em;
}

.anchor-price {
  display: block;
  min-height: 20px;
  margin-bottom: 8px;
}

.product-prices .anchor-price:not(:empty)::after {
  content: "";
  position: absolute;
  left: -10%;
  right: -10%;
  top: 50%;
  height: 2px;
  background: var(--orange);
  transform: rotate(-20deg);
  transform-origin: center;
  z-index: 2;
}

.product-prices .price-text>* {
  position: relative;
}

@media screen and (max-width: 700px) {
  .product-hours {
    height: clamp(72px, 18vw, 100px);
    width: clamp(72px, 18vw, 100px);
    padding-bottom: 8px;
  }
}

@media screen and (max-width: 420px) {
  .product-hours {
    height: clamp(60px, 25vw, 84px);
    width: clamp(60px, 25vw, 84px);
    padding-bottom: 6px;
  }
}
</style>