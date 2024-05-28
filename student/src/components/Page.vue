<script setup lang="ts"></script>

<template>
  <div class="wrapper">
    <slot name="header"></slot>

    <main>
      <section class="pf-v5-c-page__main-section">
        <slot name="main"></slot>
      </section>
    </main>
  </div>
</template>

<style scoped>
.wrapper {
  position: relative;
  width: 100vw;
  background-color: var(--white);
  display: flex;
  flex-direction: column;
}

main {
  min-height: 100vh;
  background-color: var(--white);
  display: flex;
  justify-content: center;
}

.pf-v5-c-page__main-section {
  margin: 5vh auto;
  padding: 0rem;
  border: 1.5px solid var(--orange);
  background-color: var(--white);
  flex-grow: 0;
  min-height: fit-content;
  width: 70%;
}

@media screen and (max-width: 1024px) {
  .pf-v5-c-page__main-section {
    width: 80%;
  }
}

@media screen and (max-width: 590px) {
  .pf-v5-c-page__main-section {
    width: 95%;
    margin: 5vh auto;
  }
}
</style>
