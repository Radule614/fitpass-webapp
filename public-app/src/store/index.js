import { createStore } from "vuex";

import authModule from './auth/index.js';
import facilityModule from './facility/index.js';

const store = createStore({
  modules:{
    auth: authModule,
    facility: facilityModule
  }
})

export default store