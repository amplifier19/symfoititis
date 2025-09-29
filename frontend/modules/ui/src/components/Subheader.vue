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
  display: flex;
  height: clamp(70px, 7vw, 125px); 
  margin-bottom: 32px;
  align-items: flex-end;
}

@media screen and (max-width: 1000px) {
  .subheader {
    margin-bottom: 24px;
  }
}
</style>

