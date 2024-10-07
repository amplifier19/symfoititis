import { createRouter, createWebHistory } from 'vue-router';
import { useAuthStore } from '@symfoititis-frontend-monorepo/stores'
import { useErrorStore } from '@symfoititis-frontend-monorepo/stores'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'courses',
      component: () => import('@symfoititis-frontend-monorepo/ui').then((m) => m.CoursesView),
    },
    {
      path: '/notes/:c_id',
      name: 'notes',
      component: () => import('@symfoititis-frontend-monorepo/ui').then((m) => m.NotesView),
    },
    {
      path: '/tutoring',
      name: 'tutoring',
      component: () => import('../views/TutoringView.vue'),
    },
    {
      path: '/availability/:c_id',
      name: 'availability',
      component: () => import('../views/AvailabilityView.vue'),
    },
  ],
  scrollBehaviour() {
    return { top: 0 }
  }
});

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
  } catch (error: Error | string) {
    if (typeof error === "string") {
      errorStore.addError({status: 500, error: error})
    } else if (typeof error === "object") {
      errorStore.addError(JSON.parse(error.message))
    }
  }
  next()
})

export default router;
