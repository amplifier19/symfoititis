import { createRouter, createWebHistory } from "vue-router";
import { useUserDataService } from "@symfoititis-frontend-monorepo/core/services";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "courses",
      component: () =>
        import("@symfoititis-frontend-monorepo/ui").then((m) => m.CoursesView),
    },
    {
      path: "/notes/:c_id",
      name: "notes",
      component: () =>
        import("@symfoititis-frontend-monorepo/ui").then((m) => m.NotesView),
    },
    {
      path: "/tutoring",
      name: "tutoring",
      component: () => import("../views/TutoringView.vue"),
    },
    {
      path: "/buy/hours",
      name: "buy-hours",
      component: () => import("../views/BuyHoursView.vue"),
    },
    {
      path: "/teachers/:c_id",
      name: "availability",
      component: () => import("../views/TeachersView.vue"),
    },
    {
      path: "/bookings",
      name: "bookings",
      component: () => import("../views/BookingsView.vue"),
    },
    {
      path: "/booking/:c_id/:b_id",
      name: "booking",
      component: () => import("../views/ChatView.vue"),
    },
  ],
  scrollBehavior(to, from, savedPosition) {
    if (to.name == "booking") {
      return false;
    }
    return { top: 0 };
  },
});

router.beforeEach(async (to, from, next) => {
  const { initAuthAdapter, loadUser } = useUserDataService();
  await initAuthAdapter();
  loadUser();
  next();
});

export default router;
