<script setup lang="ts">
import { ref, watch } from "vue";

const props = defineProps<{
  title: string;
  displaySearch: boolean;
  search?: string;
}>();

const emit = defineEmits<{
  (e: "clear-search"): void;
}>();

const search = ref<string>("");

watch(props, (newProps, oldProps) => {
  search.value = props.search || "";
});
</script>

<template>
  <section class="search-wrapper">
    <div class="search-container content-width">
      <span class="search-name lg-font md-fw">{{ props.title }}</span>

      <div v-if="props.displaySearch" class="searchbar">
        <slot></slot>

        <div v-if="!search" class="icon-container" id="search-icon-container">
          <img
            class="icon"
            id="search-icon"
            src="/svg/search_icon.svg"
            alt="search"
          />
        </div>

        <div
          v-else
          @click="emit('clear-search')"
          class="icon-container"
          id="close-icon-container"
        >
          <img class="icon" src="/svg/close_orange.svg" alt="X" />
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped>
.search-wrapper {
  display: flex;
  align-items: flex-end;
  justify-content: center;
  min-height: clamp(65px, 5vw, 200px);
}

.search-container {
  display: flex;
  flex-direction: row;
  background-color: var(--orange);
  justify-content: space-between;
  padding: 4px 3rem;
}

.search-name {
  color: var(--white);
  min-width: 0;
  flex: 1 1 auto;
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
  cursor: pointer;
  background-color: white;
}

#search-icon-container {
  flex: 0 0 auto;
  pointer-events: none;
}

.icon {
  margin: 0 6px;
  flex: 0 0 auto;
  object-fit: fill;
  background-color: white;
  width: 18px;
  height: 18px;
}

@media screen and (max-width: 1800px) {
  .search-container {
    padding: 4px 2rem;
  }
}

@media screen and (max-width: 1300px) {
  .search-container {
    padding: 4px 1rem;
    max-height: 35px;
  }
}
</style>
