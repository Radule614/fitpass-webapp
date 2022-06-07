import authMutations from './mutations.js';
import authActions from './actions.js';
import authGetters from './getters';

export default {
  namespaced: true,
  state() {
    return {
      usedId: null,
      token: null
    }
  },
  mutations: authMutations,
  actions: authActions,
  getters: authGetters
}