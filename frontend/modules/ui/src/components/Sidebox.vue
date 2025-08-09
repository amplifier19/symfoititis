<script setup lang="ts">
  const props = defineProps<{
    content: number
    id: string
  }>()
</script>

<template>
  <li v-if="props.content>0" class="sidebox" :id="props.id">
    {{ content }}
  </li>
  <li v-else class="sidebox hidden" :id="props.id">
    {{ content }}
  </li>
</template>

<style scoped>
.sidebox {
  display: flex;
  align-items: center;
  justify-content: center;
  width: clamp(25px, 3vw, 40px);
  height: clamp(25px, 3vw, 40px);
  border-top: 1px solid var(--white);
  color: var(--white);
}

.hidden {
  visibility: hidden;
} 

#left-sidebox {
  border-left: 1px solid var(--white);
  border-top-left-radius: 14px;
}

#right-sidebox {
  border-right: 1px solid var(--white);
  border-top-right-radius: 14px;
}

@media screen and (max-width: 590px) {
  #right-sidebox {
    display: none;
  }
  #left-sidebox {
    visibility: hidden;
  }
}
</style>
