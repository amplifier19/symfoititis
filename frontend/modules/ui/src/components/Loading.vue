<script setup lang="ts"></script>

<template>
  <div class="loading-wrapper">
    <div class="loading-container">
      <svg
        class="pf-v5-c-spinner"
        role="progressbar"
        viewBox="0 0 100 100"
        aria-label="Loading..."
        style="--pf-v5-c-spinner--diameter: 80px"
      >
        <circle
          class="pf-v5-c-spinner__path"
          cx="50"
          cy="50"
          r="45"
          fill="none"
        />
      </svg>
    </div>
  </div>
</template>

<style scoped>
.loading-wrapper {
  display: flex;
  width: 100%;
  height: 100%;
  align-items: center;
  justify-content: center;
}

.loading-container {
  width: clamp(50px, 5vw, 80px);
  height: auto;
  display: flex;
  justify-content: space-evenly;
  align-items: center;
}
</style>
