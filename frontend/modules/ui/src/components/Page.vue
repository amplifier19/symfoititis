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

.search-input {
  width: 100%;
  box-sizing: unset;
  border: none;
  outline: none;
  background-color: white;
  margin: 0px;
}

.gallery-wrapper {
  display: flex;
  flex-direction: column;
}

.gallery-container {
  margin-bottom: 2rem;
}

.gallery {
  display: grid;
    grid-template-columns: repeat(5, 1fr);
  padding: 0 auto;
  justify-items: center;
}

.card-container {
  width: 100%;
  height: auto;
}

@media screen and (max-width: 1600px) {
  .gallery {
    grid-template-columns: repeat(5, 1fr);
  }
}

@media screen and (max-width: 1300px) {
  .gallery {
    grid-template-columns: repeat(4, 1fr);
  }
}

@media screen and (max-width: 1000px) {
  .gallery {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media screen and (max-width: 700px) {
  .gallery {
    grid-template-columns: repeat(2, 1fr);
  }
}

.sm-font {
  font-size: 15px;
}

.md-font {
  font-size: 18px;
}

.lg-font {
  font-size: 20px;
}

.xl-font {
  font-size: 40px;
}

.xxl-font {
  font-size: 40px;
}

.lt-fw {
  font-family: "Geologica-Light";
}

.rg-fw {
  font-family: "Geologica-Regular";
}

.md-fw {
  font-family: "Geologica-Medium";
}

.sb-fw {
  font-family: "Geologica-SemiBold";
}

@media screen and (max-width: 1000px) {
.sm-font {
  font-size: 12px;
}

.md-font {
  font-size: 16px;
}

.lg-font {
  font-size: 18px;
}

.xl-font {
  font-size: 22px;
}

.xxl-font {
  font-size: 30px;
}
}

</style>
