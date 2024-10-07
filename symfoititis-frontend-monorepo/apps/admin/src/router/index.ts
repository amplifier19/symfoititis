import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'

import { useErrorStore } from '@symfoititis-frontend-monorepo/stores'
import { useAuthStore } from '@symfoititis-frontend-monorepo/stores'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path: '/universities',
      name: 'universities',
      component: () => import('../views/UniversitiesView.vue')
    },
    {
      path: '/departments',
      name: 'departments',
      component: () => import('../views/DepartmentsView.vue')
    },
    {
      path: '/courses/:dep_id',
      name: 'courses',
      component: () => import('../views/CoursesView.vue')
    },
    {
      path: '/notes/:c_id',
      name: 'notes',
      component: () => import('../views/NotesView.vue')
    }
  ]
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
  } catch (error: Error) {
    errorStore.addError(JSON.parse(error.message))
  }
  next()
})

export default router
