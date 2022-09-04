import userMutations from './mutations.js';
import userActions from './actions';
import userGetters from './getters';

export default {
  namespaced: true,
  state() {
    return {
      users: [],
      coupons: []
    }
  },
  mutations: userMutations,
  actions: userActions,
  getters: userGetters
}