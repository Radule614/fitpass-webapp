import { createStore } from "vuex";

import authModule from './auth/index.js';
import facilityModule from './facility/index.js';
import usersModule from './users/index.js';
import commentsModule from './comments/index.js';

const store = createStore({
  modules:{
    auth: authModule,
    facility: facilityModule,
    users: usersModule,
    comments: commentsModule
  }
})

export default store