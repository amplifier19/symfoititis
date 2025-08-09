<script setup lang="ts">
import { Masterhead, Page, Toasts, SearchHeader } from '@symfoititis-frontend-monorepo/ui';
</script>

<template>
  <Toasts />
  <Page>
    <template v-slot:header>
      <Masterhead :selected="3" />
    </template>
    <template v-slot:main>
      <SearchHeader title="Προσφορές" :display-search="false" />
    </template>
  </Page>
</template>

<style scoped>

</style>