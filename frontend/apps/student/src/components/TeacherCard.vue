<script setup lang="ts">
import { ref, defineAsyncComponent } from 'vue'
import { type Teacher } from '@symfoititis-frontend-monorepo/interfaces'


const rootUrl = import.meta.env.VITE_HOST
const props = defineProps<{
  teacher: Teacher
  selectedTeacherId: string
}>()

const emit = defineEmits<{
  (e: 'select-teacher', teacher: Teacher, event: Event):void
}>()

const expandableDiv = ref<HTMLElement>()

const DateTimePicker = defineAsyncComponent(() => import('./DateTimePicker.vue'))

const handleSelectTeacher = (event: Event) => {
  emit('select-teacher', props.teacher, event)
}
</script>

<template>
  <div ref="expandableDiv" class="pf-v5-c-expandable-section pf-m-display-lg pf-m-limit-width">
    <section @click="handleSelectTeacher" class="pf-v5-c-expandable-section__toggle">
      <span class="regular-text pf-v5-c-expandable-section__toggle-text toogle-item">
        {{ props.teacher.firstname }} {{ props.teacher.lastname }}
      </span>
      <span class="pf-v5-c-expandable-section__toggle-icon toogle-item">
        <!-- <i class="fa fa-angle-right" aria-hidden="true"></i> -->
         <img class="angle-icon" src="./angle-icon-orange.svg" alt="angle-icon">
      </span>
    </section>

    <div v-if="props.selectedTeacherId === props.teacher.t_id" class="pf-v5-c-expandable-section__content calendar-container">
      <DateTimePicker :teacher="props.teacher" />
    </div>
  </div>
</template>

<style scoped>
.pf-v5-c-expandable-section {
  background-image: none;
  box-shadow: none;
  margin-bottom: 1rem;
}

.pf-v5-c-expandable-section__content {
  padding-inline-start: 0; 
  padding-inline-end: 0; 
}

.pf-v5-c-expandable-section__toggle {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  border: var(--main-border);
  border-radius: 16px 16px 0 0;
  padding: 8px 10px;
  width: 100%;
  color: var(--gray);
  cursor: pointer;
}
.pf-v5-c-expandable-section__toggle-icon > img {
  user-select: none;
}

.pf-v5-c-expandable-section > .pf-v5-c-expandable-section__toggle > .pf-v5-c-expandable-section__toggle-icon > img {
  rotate: 180deg;
}

.pf-m-expanded > .pf-v5-c-expandable-section__toggle > .pf-v5-c-expandable-section__toggle-icon > img {
  rotate: -90deg;
}

.pf-m-expanded::after {
  background-color: transparent;
}
.pf-v5-c-expandable-section__toggle:hover {
  background-color: var(--orange);
  color: white;
}
.toogle-item {
  pointer-events: none;
  margin: 0 20px;
}
.pf-v5-c-expandable-section__toggle-text {
  text-transform: uppercase;
  display: flex;
  user-select: none;
  align-items: center;
  justify-items: center;
}
.pf-m-expanded > section,
.pf-m-expanded > section:hover {
  background-color: var(--white);
  border-radius: 16px 16px 0 0;
  border-bottom: transparent;
  color: var(--black);
}
.pf-v5-c-expandable-section__toggle:hover > .pf-v5-c-expandable-section__toggle-icon > img {
  /* color: white; */
  content: url('./angle-icon-white.svg');
}
.pf-v5-c-expandable-section__toggle > .pf-v5-c-expandable-section__toggle-icon > img {
  /* color: var(--orange); */
  content: url('./angle-icon-orange.svg');
}
.pf-m-expanded > .pf-v5-c-expandable-section__toggle:hover > .pf-v5-c-expandable-section__toggle-icon > img {
  /* color: var(--orange); */
  content: url('./angle-icon-orange.svg');
}

.angle-icon {
  width: 23px;
  height: 13px;
}
.calendar-container {
  max-width: 100%;
  border: var(--main-border);
  border-top: transparent;
}
@media screen and (max-width: 1800px) {
  .toogle-item {
    margin: 0 10px;
  }
  /* .fa-angle-right {
    font-size: 30px;
  } */
}
</style>
