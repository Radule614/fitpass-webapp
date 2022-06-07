import { createRouter, createWebHistory} from 'vue-router'

import HomePage from './pages/HomePage.vue'
import FacilityPage from './pages/FacilityPage.vue'
import StaffPage from './pages/StaffPage.vue'
import AboutPage from './pages/AboutPage.vue'
import ContactPage from './pages/ContactPage.vue'


const routes = [
  { path: '/', redirect: '/home'},
  { path: '/home', name: "home", component: HomePage},
  { path: '/facility', name: "facility", component: FacilityPage},
  { path: '/staff', name: "staff", component: StaffPage},
  { path: '/about', name: "about", component: AboutPage},
  { path: '/contact', name: "contact", component: ContactPage},
  { path: '/:all(.*)', redirect: '/home'}
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router