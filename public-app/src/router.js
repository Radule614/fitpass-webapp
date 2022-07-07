import { createRouter, createWebHistory} from 'vue-router';

import store from './store';

// meta.nav present      => route will appear in navigation
// meta.userType present => only mentioned user type will be able to access it
export const routes = [
  { path: '/home',      name:     'home',       component: () => import('./pages/HomePage.vue'),      meta: { nav: 'Home'                         } },
  { path: '/facility',  name:     'facility',   component: () => import('./pages/FacilityPage.vue'),  meta: { nav: 'Facilities'                   }, children: 
    [
      { path: 'add', name: 'facilityAdd', component: () => import('./components/facility/FacilityAdd.vue'), meta: {userType: 'ADMIN'} },
      { path: ':all(.*)', redirect: '/facility'}
    ]
  },
  { path: '/staff',     name:     'staff',      component: () => import('./pages/StaffPage.vue'),     meta: { nav: 'Staff',     userType: 'ADMIN' } },
  { path: '/about',     name:     'about',      component: () => import('./pages/AboutPage.vue'),     meta: { nav: 'About'                        } },
  { path: '/contact',   name:     'contact',    component: () => import('./pages/ContactPage.vue'),   meta: { nav: 'Contact'                      } },
  { path: '/:all(.*)',  redirect: '/home'    }
]

export const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

router.beforeEach((to, from, next) => {
  let userType = store.getters['auth/userType'];

  if(to.meta.userType && userType && to.meta.userType != userType){
    next(from.path);
  }
  else if(to.meta.userType && !userType){
    store.dispatch('auth/checkAuthentication').then(() => {
      if(store.getters['auth/isLogged'] && to.meta.userType == store.getters['auth/userType']){
        next();
      }else{
        next(from.path);
      }
    }).catch(e => {
      next(from.path);
    });
  }
  else{
    next();
  }
});

export default router