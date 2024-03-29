import { createRouter, createWebHistory } from "vue-router";

import store from "./store";

// meta.nav present       => route will appear in navigation
// meta.userTypes present => only mentioned user types will be able to access it
export const routes = [
  {
    path: "/home",
    name: "home",
    component: () => import("./pages/HomePage.vue"),
    meta: { nav: "Home" },
  },
  {
    path: "/facility",
    name: "facility",
    component: () => import("./pages/FacilitiesPage.vue"),
    meta: { nav: "Facilities" },
    children: [
      {
        path: "add",
        name: "facilityAdd",
        component: () => import("./components/facility/facility-list/FacilityAdd.vue"),
        meta: { userTypes: ["ADMIN"] },
      },
      { path: ":all(.*)", redirect: "/facility" },
    ],
  },
  {
    path: "/facility/:id",
    name: "facilityPage",
    component: () => import("./pages/FacilityPage.vue"),
  },
  {
    path: "/users",
    name: "users",
    component: () => import("./pages/UsersPage.vue"),
    meta: { nav: "Users", userTypes: ["ADMIN"] },
    children: [
      {
        path: "",
        name: "usersDefault",
        component: () => import("./components/users/ControlBlock.vue"),
      },
      {
        path: "filter",
        name: "usersFilter",
        component: () => import("./components/filter/UsersFilterComponent.vue"),
      },
      {
        path: "add",
        name: "usersAdd",
        component: () => import("./components/users/UsersAdd.vue"),
      },
      { path: ":all(.*)", redirect: "/users" },
    ],
  },
  {
    path: "/admin",
    name: "adminOverview",
    component: () => import("./pages/AdminPage.vue"),
    meta: { nav: "Admin", userTypes: ["ADMIN"] }
  },
  {
    path: "/manager",
    name: "manager",
    component: () => import("./pages/ManagerPage.vue"),
    meta: { nav: "Manager", userTypes: ["MANAGER"] },
    children: [
      {
        path: "overview",
        name: "managerOverview",
        component: () => import("./pages/manager/OverviewPanel.vue"),
        meta: { nav: "Overview" },
      },
      {
        path: "content",
        name: "managerContent",
        component: () => import("./pages/manager/ContentPanel.vue"),
        meta: { nav: "Content" },
      },
      {
        path: "feedback",
        name: "managerFeedback",
        component: () => import("./pages/manager/FeedbackPanel.vue"),
        meta: { nav: "Feedback" },
      },
      {
        path: "trainer",
        name: "menagerTrainer",
        component: () => import("./pages/manager/TrainerPanel.vue"),
        meta: { nav: "Trainers" },
      },
      {
        path: "visitors",
        name: "managerVisitors",
        component: () => import("./pages/manager/CustomerPanel.vue"),
        meta: { nav: "Visitors" },
      },
      {
        path: "training",
        name: "managerTraining",
        component: () => import("./pages/manager/TrainingPanel.vue"),
        meta: { nav: "Trainings" },
      },
      { path: ":all(.*)", redirect: "/manager/overview" },
    ],
  },
  {
    path: "/trainings",
    name: "trainings",
    component: () => import("./pages/TrainingsPage.vue"),
    meta: { nav: "Trainings", userTypes: ["CUSTOMER", "TRAINER"] },
  },
  {
    path: "/profile",
    name: "profile",
    component: () => import("./pages/ProfilePage.vue"),
    meta: { nav: "Profile", userTypes: ["ANY"] },
  },
  { path: "/:all(.*)", redirect: "/home" },
];

export const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
});

router.beforeEach((to, from, next) => {
  let userType = store.getters["auth/userType"];
  let userTypes = to.meta.userTypes;

  if (userTypes && userType && !userTypes.includes("ANY") && !userTypes.includes(userType)){
    next(from.path);
  }else if (userTypes && !userType) {
    store.dispatch("auth/checkAuthentication").then(() => {
        if (store.getters["auth/isLogged"] && (userTypes.includes("ANY") || userTypes.includes(store.getters["auth/userType"]))){
          next();
        } else {
          next(from.path);
        }
      }).catch((e) => {
        next(from.path);
      });
  } else next();
});

export default router;
