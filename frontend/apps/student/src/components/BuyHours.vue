<script setup lang="ts">

</script>

<template>
    <section class="buy-hours-wrapper wrapper">
        <div class="buy-hours-container content-width">
        <RouterLink :to="{name: 'buy-hours'}" class="buy-hours-link">
            <div class="md-font md-fw buy-hours-button">
            <span> 
                    ΑΓΟΡΑ ΩΡΩΝ        
            </span> 
            </div>
        </RouterLink>
        </div>
    </section>
</template>

<style scoped>
.buy-hours-container {
    display: flex;
    justify-content: flex-end;
    padding: 3rem 3rem 0rem 3rem;
}

.buy-hours-link {
    max-width: fit-content;
    display: block;
}

.buy-hours-button {
    width: clamp(140px, 15vw, 170px);
    height: clamp(90px, 11vw, 120px);
    display: flex;
    justify-content: center;
    padding-top: 0.7rem;
    background-image: url('/svg/buy_hours.svg');
    background-repeat: no-repeat;
    font-family: 'Geologica-Medium';
    color: var(--orange);
    cursor: pointer;
}
@media screen and (max-width: 1800px) {
  .buy-hours-container {
    padding: 2rem 2rem 0 2rem;
  }
}

@media screen and (max-width: 1300px) {
  .buy-hours-container {
    padding: 1.5rem 1.5rem 0 1.5rem;
}
}

@media screen and (max-width: 1000px) {
  .buy-hours-container {
    padding: 1.5rem 0 0 1.5rem;
  }
  .buy-hours-button {
    padding-top: 0.6rem;
  }
}
</style>