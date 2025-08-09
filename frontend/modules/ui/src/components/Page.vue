<script setup lang="ts">
function setRealVh() {
const vh = window.innerHeight * 0.01;
  document.documentElement.style.setProperty('--real-vh', `${vh}px`);
}

window.addEventListener('resize', setRealVh);
window.addEventListener('orientationchange', setRealVh);
setRealVh();
</script>

<template>
  <slot name="header"></slot>

  <section id="page-wrapper">
    <slot name="subheader"></slot>
    <slot name="main"></slot>
  </section>
</template>

<style>
* {
  -webkit-tap-highlight-color: transparent;
}

html, body {
  height: 100%;
  margin: 0;
  padding: 0;
  background-color: var(--white, white);
  overflow: hidden;
}

#root {
  display: flex;
  flex-direction: column;
  height: 100%;
  background-color: var(--white);
}

#page-wrapper {
  flex: 1;
  overflow-x: hidden;
  overflow-y: auto;
  display: flex;
  background-color: var(--white);
  flex-direction: column;
}

.wrapper {
  display: flex; 
  justify-content: center;
  align-items: center;
}

.content-width {
  width: clamp(350px, 70vw, 1500px);
}
</style>
