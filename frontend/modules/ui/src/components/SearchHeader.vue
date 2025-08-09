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
  <section class="search-wrapper">
    <div class="search-container content-width">

      <span class="search-name">{{ props.title }}</span>

      <div v-if="props.displaySearch" class="searchbar">
          <slot></slot>

          <div v-if="!search" class="icon-container" id="search-icon-container">
            <img class="icon" id="search-icon" src="/svg/search_icon.svg" alt="search" />
          </div>

          <div v-else @click="emit('clear-search')" class="icon-container" id="close-icon-container">
            <img class="icon" src="/svg/close_orange.svg" alt="X" />
          </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
.search-wrapper {
  display: flex;
  align-items: center;
  justify-content: center;
  min-height: clamp(80px, 8vw, 220px);
}

.search-container {
  display: flex;
  flex-direction: row;
  background-color: var(--orange);
  justify-content: space-between;
  padding: 0.3rem 3rem;
}

.search-name {
  font-size: 1.15rem;
  font-family: 'Geologica-SemiBold';
  color: var(--white);
}

.searchbar {
  display: flex;
  flex-direction: row;
  width: clamp(200px, 40%, 550px);
}

.icon-container {
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 1rem;
  padding: 0.3rem;
  cursor: pointer;
  background-color: white;
}

#search-icon-container {
  pointer-events: none;
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

  .search-name {
    font-size: 1.1rem;
  }

  .icon {
    width: 1rem;
    height: 1rem;
  }
}

@media screen and (max-width: 1300px) {
  .search-container {
    padding: 0.2rem 1rem;
  }

  .search-name {
    font-size: 1rem;
  }

  .searchbar>span {
    font-size: 1rem;
  }
}
</style>
