import { createStore } from "vuex";

import authModule from './auth/index.js';
import facilityModule from './facility/index.js';
import usersModule from './users/index.js';

const store = createStore({
  modules:{
    auth: authModule,
    facility: facilityModule,
    users: usersModule
  }
})

export default store