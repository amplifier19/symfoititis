<script setup lang="ts">
import { ref, watch } from 'vue'

const props = defineProps<{
  title: string
  displaySearch: boolean
  search?: string
}>()

const emit = defineEmits<{
  (e: 'clear-search'): void
}>()

const search = ref<string>("")

watch(props, (newProps, oldProps) => {
  search.value = props.search || ""
})
</script>

<template>
  <div class="search-container">
    <span>{{ props.title }}</span>
    <div v-if="props.displaySearch" class="search-group">
      <div class="search-main">
        <slot></slot>
        <div v-if="!search" class="icon-container" id="search-icon-container" aria-label="Clear input">
          <img class="icon" id="search-icon" src="/svg/search_icon.svg" alt="search" />
        </div>
        <div v-else @click="emit('clear-search')" id="close-icon-container" class="icon-container"
          aria-label="Clear input">
          <img class="icon" src="/svg/close_orange.svg" alt="X" />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
#search-icon-container {
  pointer-events: none;
}

.search-container {
  background-color: var(--orange);
  border: 1.5px solid var(--orange);
  padding: 0.3rem 3rem;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  border-bottom: 1.5px solid var(--orange);
  align-items: center;
}

.search-container>span {
  color: var(--white);
  font-family: 'Geologica-SemiBold';
  font-size: 1.15rem;
}

.search-group {
  width: 40%;
  display: flex;
  align-items: center;
}

.search-main {
  background-color: white;
  width: 100%;
  display: flex;
  flex-direction: row;
  position: relative;
}

.icon-container {
  position: relative;
  background-color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  padding: 0.3rem;
  font-size: 1rem;
}

.icon {
  width: 1.2rem;
  height: 1.2rem;
  background-color: white;
}

@media screen and (max-width: 1800px) {

  .search-container {
    padding: 0.2rem 2rem;
  }

  .search-container>span {
    font-size: 1.1rem;
  }

  .search-group {
    width: 55%;
  }

  .icon {
    height: 1rem;
    width: 1rem;
  }
}

@media screen and (max-width: 1300px) {
  .search-container {
    padding: 0.2rem 1rem;
  }

  .search-container>span {
    font-size: 1rem;
  }

  .search-group {
    width: 65%;
  }
}
</style>
