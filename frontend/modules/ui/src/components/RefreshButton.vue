<script setup lang="ts">
import { ref } from 'vue'

const disabled = ref<boolean>(false)
const emit = defineEmits<{
    (e: 'refresh'):void
}>()

const emitRefresh = () => {
    disabled.value = true 
    emit('refresh')
    setTimeout(() => {
        disabled.value = false
    }, 5000)
}
</script>


<template>
    <button v-if="disabled"  class="pf-v5-c-button pf-m-primary pf-m-disabled">Refresh</button> 
    <button v-else @click="emitRefresh" class="pf-v5-c-button pf-m-primary enabled">Refresh</button> 
</template>

<style scoped>
.pf-v5-c-button {
    width: fit-content !important;
    margin-left: 2rem;
}
.enabled {
    background-color: var(--orange);
}
.pf-v5-c-button:hover {
    background-color: var(--orange-shadow);
}
.pf-m-disabled {
    cursor: not-allowed !important;
    pointer-events: none;
}
</style>