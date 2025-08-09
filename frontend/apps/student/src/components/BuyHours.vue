<script setup lang="ts">

</script>

<template>
    <section class="wrapper">
        <RouterLink :to="{name: 'buy-hours'}" class="buy-hours-container content-width">
            <div class="buy-hours-button">
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
    padding-right: clamp(1rem, 3vw, 3rem);
}

.buy-hours-button {
    width: clamp(120px, 11vw, 150px);
    height: clamp(70px, 7.5vw, 100px);
    display: flex;
    justify-content: center;
    padding-top: 0.6rem;
    background-image: url('/svg/buy_hours.svg');
    background-repeat: no-repeat;
    font-family: 'Geologica-Medium';
    font-size: clamp(0.8rem, 1.16vw, 1rem);
    color: var(--orange);
    cursor: pointer;
}
</style>