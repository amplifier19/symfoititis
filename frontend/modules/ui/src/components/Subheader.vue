<script setup lang="ts">

const props = defineProps<{ 
  title: string; 
  transitionGroupKey?: string 
}>()
</script>

<template>
  <h2 :key="props.transitionGroupKey || 1">{{ props.title }}</h2>
</template>

<style scoped>
h2 {
  height: clamp(50px, 5vw, 125px); 
  line-height: clamp(50px, 5vw, 125px); 
  font-size: 1.15rem;
  font-family: 'Geologica-Medium';
}

@media screen and (max-width: 1800px) {
  h2 {
    font-size: 1.1rem;
  }
}

@media screen and (max-width: 1300px) {
  h2 {
    font-size: 1rem;
  }
}
</style>
