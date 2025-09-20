<script setup lang="ts">
import gsap from 'gsap'

import type { Booking as BookingType } from '@symfoititis-frontend-monorepo/interfaces'

import Booking from './Booking.vue' 
import Subheader from './Subheader.vue' 

const props = defineProps<{
    bookings: BookingType[]
    header: string
}>()

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
  <section class="gallery-wrapper wrapper">
    <div class="gallery-container content-width">
      <Subheader :title="props.header" />
      <transition-group tag="div" name="gallery" class="gallery" @before-enter="beforeEnter" @enter="enter" appear>
        <div v-for="(booking, index) in props.bookings" :key="booking.b_id" :data-index="index" class="card-container"> 
          <Booking :key="booking.b_id" :booking="booking" :card="true" />
        </div>
      </transition-group>
    </div>
  </section>
</template>

<style scoped>
.subheader {
  color: var(--orange);
}
</style>
