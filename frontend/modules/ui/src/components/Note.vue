<script setup lang="ts">
import { ref } from 'vue'
import { type Note } from '@symfoititis-frontend-monorepo/interfaces'
import { useNotesDataService } from '@symfoititis-frontend-monorepo/core/services'

const { generatePresignedUrl } = useNotesDataService()

const props = defineProps<{
  note: Note  
}>()

const attachmentUrl = ref<string>('')
const linkRef = ref<HTMLAnchorElement>()
const urlExpiresAt = ref<Date>(new Date())

const openAttachment = async () => {
    const urlExpired = Date.now() >= urlExpiresAt.value.getTime()
    if (attachmentUrl.value === '' || urlExpired) {
        attachmentUrl.value = await generatePresignedUrl(props.note.c_id, props.note.note_filename)
        urlExpiresAt.value = new Date(Date.now() + 1000 * 1000)
    }
    if (linkRef.value) {
        linkRef.value.href = attachmentUrl.value
        linkRef.value.click()
    }
}


</script>

<template>
  <li class="notes">
    <a ref="linkRef" :href="attachmentUrl" 
      target="_blank" 
      style="display: none;" 
      rel="noopener noreferrer"
    ></a>
    <div @click="openAttachment" class="note-link" > 
      {{ props.note.note_display_name }}
    </div>
  </li>
</template>

<style scoped>
  
.notes {
  cursor: pointer;
  font-size: 1rem;
  width: 60%;
  border-bottom: var(--main-border);
  margin-bottom: 1rem;
}

.note-link {
  display: block;
  padding: 0rem 1rem 0.5rem 1rem;
}
@media screen and (max-width: 1800px) {
  .notes {
    font-size: 0.9rem;
    margin-bottom: 0.8rem;
  }

  .note-link {
    padding: 0rem 0.8rem 0.5rem 0.8rem;
  }
}

@media screen and (max-width: 1300px) {
  .notes {
    font-size: 0.9rem;
    margin-bottom: 0.5rem;
  }

  .note-link {
    padding: 0rem 0.5rem 0.5rem 0.5rem;
  }
}
</style>
