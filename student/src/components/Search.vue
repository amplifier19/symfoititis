<script setup lang="ts"></script>

<template>
  <div class="search-container">
    <span>Σημειώσεις</span>
    <div class="search-group">
      <div class="search-main">
        <div @click="$emit('clear-search')" class="close-icon" aria-label="Clear input">
          <img id="close-icon" src="../assets/svg/close-icon.svg" alt="" />
        </div>
        <slot></slot>
        <div class="search-icon" aria-label="Clear input">
          <i class="fa fa-fw fa-search"></i>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.search-container {
  padding: 0.5rem 2rem;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  border-bottom: 1.5px solid var(--orange);
  align-items: center;
}

.search-container > span {
  color: var(--orange);
  font-size: 1.15rem;
  font-weight: 800;
}

.search-group {
  width: 40%;
  display: flex;
  align-items: center;
}

.search-main {
  width: 100%;
  display: flex;
  flex-direction: row;
  border: 1.5px solid var(--orange);
}

.search-icon,
.close-icon {
  padding: 0 0.2rem;
  display: flex;
  align-items: center;
  justify-content: center;
  background: white;
  color: var(--orange);
}

.close-icon {
  cursor: pointer;
  padding: 0.3rem 0.5rem;
  border-right: 1.5px solid var(--orange);
  font-size: 1rem;
}

#close-icon {
  width: 1.4rem;
}

.search-icon {
  font-size: 1.3rem;
}

@media screen and (max-width: 1024px) {
  .search-container {
    padding: 0.5rem 1.5rem;
  }

  .search-container > span {
    font-size: 1.1rem;
  }

  .search-group {
    width: 55%;
  }

  .close-icon {
    padding: 0rem 0.2rem;
  }

  #close-icon {
    width: 1.2rem;
  }

  .search-icon {
    font-size: 1.1rem;
  }
}

@media screen and (max-width: 590px) {
  .search-container {
    padding: 0.3rem 0.5rem;
  }

  .search-container > span {
    font-size: 1rem;
  }

  .search-group {
    width: 65%;
  }

  #close-icon {
    width: 1rem;
  }

  .search-icon {
    font-size: 1rem;
  }
}
</style>
