import { createStore } from "vuex";

import authModule from './auth/index.js';

const store = createStore({
  modules:{
    auth: authModule
  }
})

export default store