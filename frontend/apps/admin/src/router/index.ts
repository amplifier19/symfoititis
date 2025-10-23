import { createRouter, createWebHistory } from "vue-router";
import { useUserDataService } from "@symfoititis-frontend-monorepo/core/services";

import HomeView from "../views/HomeView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/universities",
      name: "universities",
      component: () => import("../views/UniversitiesView.vue"),
    },
    {
      path: "/departments",
      name: "departments",
      component: () => import("../views/DepartmentsView.vue"),
    },
    {
      path: "/courses/:dep_id",
      name: "courses",
      component: () => import("../views/CoursesView.vue"),
    },
    {
      path: "/notes/:c_id",
      name: "notes",
      component: () => import("../views/NotesView.vue"),
    },
  ],
});

router.beforeEach(async (to, from, next) => {
  const { initAuthAdapter } = useUserDataService();
  await initAuthAdapter();
  next();
});

export default router;
