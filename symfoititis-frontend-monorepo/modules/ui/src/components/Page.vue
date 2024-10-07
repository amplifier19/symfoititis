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
  background-color: var(--white);
  flex-grow: 0;
  min-height: fit-content;
  width: 1490px;
}

@media screen and (max-width: 1800px) {
  .pf-v5-c-page__main-section {
    width: 1210px;
  }
}

@media screen and (max-width: 1300px) {
  .pf-v5-c-page__main-section {
    width: 800px;
  }
}

@media screen and (max-width: 800px) {
  .pf-v5-c-page__main-section {
    width: 95%;
  }
}
</style>
