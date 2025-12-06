<script setup lang="ts">
const emit = defineEmits<{
  (e: "toggle-products-modal", event: Event): void;
}>();

const toggleProductsModal = (event: Event) => {
  emit("toggle-products-modal", event);
};
</script>

<template>
  <section class="buy-hours-wrapper wrapper">
    <div class="buy-hours-cnt content-width">
      <button @click="toggleProductsModal" class="buy-hours-btn">
        <div class="md-font md-fw buy-hours-btn-bg">
          <span> ΑΓΟΡΑ ΩΡΩΝ </span>
        </div>
      </button>
    </div>
  </section>
</template>

<style scoped>
.buy-hours-cnt {
  display: flex;
  justify-content: flex-end;
  padding: 3rem 3rem 0rem 3rem;
}

.buy-hours-btn {
  max-width: fit-content;
  display: block;
  background-color: transparent;
  border: none;
}

.buy-hours-btn-bg {
  width: clamp(140px, 15vw, 170px);
  height: clamp(90px, 11vw, 120px);
  display: flex;
  justify-content: center;
  padding-top: 0.7rem;
  background-image: url("/svg/buy_hours.svg");
  background-repeat: no-repeat;
  font-family: "Geologica-Medium";
  color: var(--orange);
  cursor: pointer;
}

@media screen and (max-width: 1800px) {
  .buy-hours-cnt {
    padding: 2rem 2rem 0 2rem;
  }
}

@media screen and (max-width: 1300px) {
  .buy-hours-cnt {
    padding: 1.5rem 1.5rem 0 1.5rem;
  }
}

@media screen and (max-width: 1000px) {
  .buy-hours-cnt {
    padding: 1.5rem 0 0 1.5rem;
  }
  .buy-hours-btn-bg {
    padding-top: 0.6rem;
  }
}
</style>
