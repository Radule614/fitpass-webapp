import { createStore } from "vuex";

import authModule from './auth/index.js';
import facilityModule from './facility/index.js';
import usersModule from './users/index.js';
import commentsModule from './comments/index.js';
import userTypesModule from './userTypes/index.js';
import membershipsModule from "./memberships/index.js";
import trainingsModule from './trainings/index.js';

const store = createStore({
  modules:{
    auth: authModule,
    facility: facilityModule,
    users: usersModule,
    comments: commentsModule,
		userTypes: userTypesModule,
		memberships: membershipsModule,
		trainings: trainingsModule
  }
})

export default store