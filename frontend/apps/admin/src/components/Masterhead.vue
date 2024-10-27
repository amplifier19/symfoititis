<script setup lang="ts">
import { useAuthStore } from '@symfoititis-frontend-monorepo/stores'

const rootUrl = import.meta.env.VITE_HOST
const authStore = useAuthStore()
</script>

<template>
  <header class="pf-v5-c-masthead">
    <span class="pf-v5-c-masthead__toggle">
      <button
        @click="$emit('toogle-sidebar')"
        class="pf-v5-c-button pf-m-plain"
        type="button"
        aria-label="Global navigation"
      >
        <i class="fa fa-bars" aria-hidden="true"></i>
      </button>
    </span>
    <div class="pf-v5-c-masthead__main">
      <RouterLink class="pf-v5-c-masthead__brand" :to="{ name: 'home' }">
        <img class="pf-v5-c-brand" :src="`${rootUrl}/svg/logo-hor-white.svg`" alt="white-logo" />
      </RouterLink>
    </div>
    <div class="pf-v5-c-masthead__content">
      <button @click="authStore.logout()" class="pf-v5-c-button pf-m-tertiary" type="button">
        Logout
      </button>
    </div>
  </header>
</template>

<style scoped>
.pf-v5-c-brand {
  width: 170px;
  height: auto;
}
</style>
