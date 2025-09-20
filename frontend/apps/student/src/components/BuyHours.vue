<script setup lang="ts">

</script>

<template>
    <section class="wrapper">
        <RouterLink :to="{name: 'buy-hours'}" class="buy-hours-container content-width">
            <div class="md-font md-fw buy-hours-button">
            <span> 
                    ΑΓΟΡΑ ΩΡΩΝ        
            </span> 
            </div>
        </RouterLink>
    </section>
</template>

<style scoped>
.buy-hours-container {
    display: flex;
    justify-content: end;
    align-items: center;
    /* padding-right: clamp(1rem, 3vw, 3rem); */
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


@media screen and (max-width: 1000px) {
  .buy-hours-button {
    padding-top: 0.6rem;
  }
}
</style>