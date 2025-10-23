<script setup lang="ts">
import { ref } from "vue";

import { useErrorStore } from "@symfoititis-frontend-monorepo/stores";

import type { Note } from "@symfoititis-frontend-monorepo/interfaces";

import { useFetch } from "../composables/fetchService";

const props = defineProps<{ note: Note }>();

const errorStore = useErrorStore();

const { generatePresignedUrl } = useFetch();

const attachmentUrl = ref<string>("");
const linkRef = ref<HTMLAnchorElement>();
const urlExpiresAt = ref<Date>(new Date());

const openAttachment = async () => {
  const urlExpired = Date.now() >= urlExpiresAt.value.getTime();
  if (attachmentUrl.value === "" || urlExpired) {
    attachmentUrl.value = await generatePresignedUrl(
      props.note.c_id,
      props.note.note_filename,
    );
    urlExpiresAt.value = new Date(Date.now() + 1000 * 1000);
  }
  if (linkRef.value) {
    linkRef.value.href = attachmentUrl.value;
    linkRef.value.click();
  }
};
</script>

<template>
  <tr class="pf-v5-c-table__tr">
    <td class="pf-v5-c-table__td" role="cell" data-label="Note display name">
      {{ props.note.note_id }}
    </td>
    <td class="pf-v5-c-table__td" role="cell" data-label="Note display name">
      {{ props.note.note_display_name }}
    </td>
    <td
      v-if="props.note.note_filename"
      class="pf-v5-c-table__td"
      role="cell"
      data-label="Note filename"
    >
      {{ props.note.note_filename }}
    </td>
    <td v-else></td>
    <td class="pf-v5-c-table__td" role="cell" data-label="Note Type">
      {{ props.note.type }}
    </td>
    <td class="pf-v5-c-table__td" role="cell" data-label="Note Type">
      <a
        ref="linkRef"
        :href="attachmentUrl"
        target="_blank"
        rel="noopener noreferrer"
        style="display: none"
      >
      </a>
      <button @click="openAttachment" class="pf-v5-c-button pf-m-link">
        Open Note
      </button>
    </td>
    <td class="pf-v5-c-table__td pf-v5-c-table__action" role="cell">
      <div class="pf-v5-c-overflow-menu__group pf-m-button-group">
        <div class="pf-v5-c-overflow-menu__item">
          <button
            @click="$emit('activate-remove-modal', props.note)"
            class="pf-v5-c-button pf-m-primary"
            type="button"
          >
            <i class="fa fa-trash"></i>
          </button>
        </div>
        <div class="pf-v5-c-overflow-menu__item">
          <button
            class="pf-v5-c-button pf-m-secondary"
            @click="$emit('activate-edit-modal', props.note)"
            type="button"
          >
            Edit
          </button>
        </div>
      </div>
    </td>
  </tr>
</template>

<style scoped>
.pf-v5-c-table__tr {
  width: 100%;
}

.pf-v5-c-table__td {
  padding: 1rem;
}

.fa-trash {
  color: var(--pf-v5-global--danger-color--100);
}

.pf-v5-c-button {
  margin-right: 1rem;
}

.pf-m-primary {
  background-color: white;
}
</style>
