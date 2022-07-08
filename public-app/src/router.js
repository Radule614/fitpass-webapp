import { createRouter, createWebHistory} from 'vue-router';

import store from './store';

// meta.nav present      => route will appear in navigation
// meta.userTypes present => only mentioned user types will be able to access it
export const routes = [
  { path: '/home',      name:     'home',       component: () => import('./pages/HomePage.vue'),      meta: { nav: 'Home'                            } },
  { path: '/facility',  name:     'facility',   component: () => import('./pages/FacilityPage.vue'),  meta: { nav: 'Facilities'                      }, children: 
    [
      { path: 'add', name: 'facilityAdd', component: () => import('./components/facility/FacilityAdd.vue'), meta: { userTypes: ['ADMIN']} },
      { path: ':all(.*)', redirect: '/facility'}
    ]
  },
  { path: '/users',     name:     'users',      component: () => import('./pages/UsersPage.vue'),     meta: { nav: 'Users',     userTypes: ['ADMIN'] } },
  { path: '/about',     name:     'about',      component: () => import('./pages/AboutPage.vue'),     meta: { nav: 'About',                          } },
  { path: '/contact',   name:     'contact',    component: () => import('./pages/ContactPage.vue'),   meta: { nav: 'Contact',                        } },
  { path: '/profile',   name:     'profile',    component: () => import('./pages/ProfilePage.vue'),   meta: { nav: 'Profile',    userTypes: ['ANY']  } },
  { path: '/:all(.*)',  redirect: '/home'    }
]

export const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
});

router.beforeEach((to, from, next) => {
  let userType = store.getters['auth/userType'];
  let userTypes = to.meta.userTypes;

  if(userTypes && userType && !userTypes.includes('ANY') && !userTypes.includes(userType)) next(from.path);
  else if(userTypes && !userType){
    store.dispatch('auth/checkAuthentication').then(() => {
      if(store.getters['auth/isLogged'] && (userTypes.includes('ANY') || userTypes.includes(store.getters['auth/userType']))) next();
      else next(from.path);
    }).catch(e => {
      next(from.path);
    });
  }
  else next();
});

export default router