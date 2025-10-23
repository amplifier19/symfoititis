<script setup lang="ts">
import gsap from "gsap";
import type { Course } from "@symfoititis-frontend-monorepo/interfaces";
import { Subheader } from "@symfoititis-frontend-monorepo/ui";
import Card from "./Card.vue";

const props = defineProps<{
  uniqueSemesters: number[];
  filteredCourses: Course[];
  link: string;
}>();

const alphabet = "ΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡΣΤΥΦΧΨΩ";

const semCourses = (semester: number) => {
  return props.filteredCourses.filter(
    (course: Course) => course.semester == semester,
  );
};

const beforeEnter = (element: Element) => {
  const el = element as HTMLElement;
  el.style.opacity = "0";
  el.style.transform = "translateY(60px)";
};

const enter = (element: Element, done: gsap.Callback) => {
  const el = element as HTMLElement;
  gsap.to(el, {
    opacity: 1,
    y: 0,
    duration: 0.3,
    delay: 0.2 * parseInt(el.dataset.index as string),
    onComplete: done,
  });
};
</script>

<template>
  <section class="gallery-wrapper wrapper">
    <div
      v-for="semester in uniqueSemesters"
      :key="semester"
      class="gallery-container content-width"
    >
      <Subheader :title="`${alphabet[semester - 1]}' Εξάμηνο`" />
      <transition-group
        tag="div"
        name="gallery"
        class="gallery outline"
        @before-enter="beforeEnter"
        @enter="enter"
        appear
      >
        <div
          v-for="(course, index) in semCourses(semester)"
          :key="course.c_id"
          class="card-container"
          :data-index="index"
        >
          <Card
            v-if="course.semester === semester"
            :course="course"
            :link="props.link"
          />
        </div>
      </transition-group>
    </div>
  </section>
</template>

<style scoped></style>
