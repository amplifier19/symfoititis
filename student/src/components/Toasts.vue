<script setup lang="ts">
import { useErrorStore } from '../stores/errors'
import { useResponseStore } from '../stores/responses'

const errorStore = useErrorStore()
const responseStore = useResponseStore()
</script>

<template>
  <ul v-if="errorStore.errors.length > 0" class="pf-v5-c-alert-group pf-m-toast" role="list">
    <li v-for="(error, index) in errorStore.errors" class="pf-v5-c-alert-group__item">
      <div class="pf-v5-c-alert pf-m-danger" aria-label="Danger toast alert">
        <div class="pf-v5-c-alert__icon">
          <i class="fa fa-fw fa-exclamation-circle" aria-hidden="true"></i>
        </div>
        <p class="pf-v5-c-alert__title" id="alert_two_title">
          <span class="pf-v5-screen-reader">Error alert:</span>
          {{ error }}
        </p>
        <div class="pf-v5-c-alert__action">
          <button
            @click="errorStore.deleteError(index)"
            class="pf-v5-c-button pf-m-plain"
            type="button"
            aria-label="Close success alert: Success alert title"
          >
            <i class="fa fa-times" aria-hidden="true"></i>
          </button>
        </div>
      </div>
    </li>
  </ul>

  <ul v-if="responseStore.responses.length > 0" class="pf-v5-c-alert-group pf-m-toast" role="list">
    <li v-for="(reponse, index) in responseStore.responses" class="pf-v5-c-alert-group__item">
      <div class="pf-v5-c-alert pf-m-success" aria-label="Success toast alert">
        <div class="pf-v5-c-alert__icon">
          <i class="fa fa-fw fa-check-circle" aria-hidden="true"></i>
        </div>
        <p class="pf-v5-c-alert__title" id="alert_one_title">
          <span class="pf-v5-screen-reader">Success alert:</span>
          {{ reponse }}
        </p>
        <div class="pf-v5-c-alert__action">
          <button
            @click="responseStore.deleteResponse(index)"
            class="pf-v5-c-button pf-m-plain"
            type="button"
            aria-label="Close success alert: Success alert title"
          >
            <i class="fa fa-times" aria-hidden="true"></i>
          </button>
        </div>
      </div>
    </li>
  </ul>
</template>

<style scoped></style>
