import { createRouter, createWebHistory} from 'vue-router'

import HomePage from './pages/HomePage.vue'
import FacilityPage from './pages/FacilityPage.vue'
import StaffPage from './pages/StaffPage.vue'
import AboutPage from './pages/AboutPage.vue'
import ContactPage from './pages/ContactPage.vue'
import store from './store'

// meta.nav present      => route will appear in navigation
// meta.userType present => only mentioned user type will be able to access it
export const routes = [
  { path: '/home',      name:     'home',       component: HomePage,      meta: { nav: 'Home'                         } },
  { path: '/facility',  name:     'facility',   component: FacilityPage,  meta: { nav: 'Facilities'                   } },
  { path: '/staff',     name:     'staff',      component: StaffPage,     meta: { nav: 'Staff',     userType: 'ADMIN' } },
  { path: '/about',     name:     'about',      component: AboutPage,     meta: { nav: 'About'                        } },
  { path: '/contact',   name:     'contact',    component: ContactPage,   meta: { nav: 'Contact'                      } },
  { path: '/:all(.*)',  redirect: '/home'    }
]

export const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

router.beforeEach((to, from, next) => {
  let userType = store.getters['auth/userType'];
  if(to.meta.userType){
    if(to.meta.userType == userType) next();
    else next('home');
  }else{
    next();
  }
});

export default router