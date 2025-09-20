<script setup lang="ts">

const props = defineProps<{ 
  title: string; 
  transitionGroupKey?: string 
}>()
</script>

<template>
  <span class="subheader lg-font md-fw" :key="props.transitionGroupKey || 1">{{ props.title }}</span>
</template>

<style scoped>
.subheader {
  display: block;
  height: clamp(70px, 7vw, 125px); 
  line-height: clamp(50px, 5vw, 125px); 
}
</style>
