<script setup lang="ts">
import gsap from 'gsap'
import type { Course } from '@symfoititis-frontend-monorepo/interfaces'

import { Card } from '@symfoititis-frontend-monorepo/ui'
import { Subheader } from '@symfoititis-frontend-monorepo/ui'

const props = defineProps<{ recentCourses: Course[]; link: string }>()

const beforeEnter = (element: Element) => {
  const el = element as HTMLElement
  el.style.opacity = '0'
  el.style.transform = 'translateY(60px)'
}

const enter = (element: Element, done: gsap.Callback) => {
  const el = element as HTMLElement
  gsap.to(el, {
    opacity: 1,
    y: 0,
    duration: 0.3,
    delay: 0.2 * parseInt(el.dataset.index as string),
    onComplete: done
  })
} 
</script>

<template>
  <div class="gallery-container">
    <Subheader title="Προβλήθηκαν Πρόσφατα" />
    <transition-group tag="div" class="gallery" @beforeEnter="beforeEnter" @enter="enter" appear>
      <div v-for="(course, index) in props.recentCourses" :key="course.c_id" class="card-container"
        :data-index="index">
        <Card :course="course" :link="props.link" />
      </div>
    </transition-group>
  </div>
</template>

<style scoped>
.gallery-container {
  margin-top: 6rem;
}

.gallery-container>h2 {
  color: var(--orange);
}

.gallery {
  display: grid;
  grid-template-columns: repeat(6, 1fr);
  padding: 0 auto;
  justify-items: center;
}

.card-container {
  width: max-content;
  height: auto;
}

@media screen and (max-width: 1800px) {
  .gallery {
    grid-template-columns: repeat(6, 1fr);
  }

  .gallery-container {
    margin-top: 5rem;
  }
}

@media screen and (max-width: 1300px) {
  .gallery-container {
    margin-top: 4rem;
  }

  .gallery {
    grid-template-columns: repeat(5, 1fr);
  }
}

@media screen and (max-width: 800px) {
  .gallery {
    grid-template-columns: repeat(4, 1fr);
  }
}

@media screen and (max-width: 550px) {
  .gallery {
    grid-template-columns: repeat(3, 1fr);
  }
}

@media screen and (max-width: 300px) {
  .gallery {
    grid-template-columns: repeat(2, 1fr);
  }
}

@media screen and (max-width: 250px) {
  .gallery {
    grid-template-columns: repeat(1, 1fr);
  }
}
</style>
