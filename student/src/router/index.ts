import { createRouter, createWebHistory } from 'vue-router'

import { useAuthStore } from '../stores/auth'
import { useErrorStore } from '../stores/errors'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'courses',
      component: () => import('../views/CoursesView.vue')
    },
    {
      path: '/notes/:c_id',
      name: 'course',
      component: () => import('../views/NotesView.vue')
    }
  ],
  scrollBehavior() {
    return { top: 0 }
  }
})

router.beforeEach(async (to, from, next) => {
  const errorStore = useErrorStore()
  try {
    const authStore = useAuthStore()
    if (Object.keys(authStore.keycloak).length === 0) {
      authStore.keycloakCreate()
      await authStore.keycloakInit()
      await authStore.updateToken(0)
    } else {
      await authStore.updateToken(420)
    }
  } catch (error: any) {
    errorStore.addError(error)
  }
  next()
})
export default router
