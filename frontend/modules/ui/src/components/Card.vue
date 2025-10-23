<script setup lang="ts">
import { computed } from "vue";
import { Course } from "@symfoititis-frontend-monorepo/interfaces";

const props = defineProps<{
  course: Course;
  link: string;
  bookingId?: number;
  context?: string;
}>();

const classList =
  !!props.context && props.context == "booking" ? "booking-ctx" : "card-xtx";

const getCourseThumbnail = computed(() => {
  return props.course.c_display_name
    .split(" ")
    .map((word: string) => {
      const firstLetter = word[0];
      return firstLetter === firstLetter.toUpperCase() ? firstLetter : "";
    })
    .join("");
});
</script>

<template>
  <RouterLink
    class="card-wrapper"
    :to="{
      name: props.link,
      params: { c_id: course.c_id, b_id: props.bookingId },
    }"
  >
    <div :class="`${classList} card-cnt`">
      <slot name="card-header"></slot>
      <span class="course-thumb xxl-font rg-fw">
        {{ getCourseThumbnail }}
      </span>
      <slot name="card-footer"></slot>
    </div>
  </RouterLink>
  <span class="course-name sm-font rg-fw">
    {{ props.course.c_display_name }}
  </span>
</template>

<style scoped>
.card-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
}

.card-cnt {
  height: clamp(110px, 9vw, 140px);
  width: clamp(110px, 9vw, 140px);
  display: flex;
  justify-content: center;
  align-items: center;
  position: relative;
  background-color: var(--white);
  background-repeat: no-repeat;
  color: var(--orange);
}

.card-cnt:hover {
  background-image: url("/svg/hover_course_card.svg");
}

.card-cnt {
  background-image: url("/svg/course-icon.svg");
}

.booking-ctx:hover {
  background-image: url("/svg/hover_flipped_card.svg");
}

.booking-ctx {
  background-image: url("/svg/flipped_card.svg");
}

.course-thumb {
  color: var(--orange);
  flex-grow: 0;
}

.course-name {
  width: 75%;
  display: flex;
  justify-content: center;
  text-align: center;
  margin: 0 auto;
  margin-top: 8px;
  margin-bottom: 30px;
}

@media screen and (max-width: 1600px) {
  .card-cnt {
    height: clamp(110px, 9vw, 500px);
    width: clamp(110px, 9vw, 500px);
  }
}

@media screen and (max-width: 1300px) {
  .card-cnt {
    height: clamp(110px, 10vw, 500px);
    width: clamp(110px, 10vw, 500px);
  }
}

@media screen and (max-width: 1000px) {
  .card-cnt {
    height: clamp(100px, 12.5vw, 500px);
    width: clamp(100px, 12.5vw, 500px);
  }
}

@media screen and (max-width: 700px) {
  .card-cnt {
    height: clamp(100px, 16vw, 500px);
    width: clamp(100px, 16vw, 500px);
  }
}
</style>
