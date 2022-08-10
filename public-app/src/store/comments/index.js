import commentMutations from './mutations.js';
import commentActions from './actions.js';
import commentGetters from './getters.js';

export default {
  namespaced: true,
  state(){
    return {
      comments: []
    }
  },
  mutations: commentMutations,
  actions: commentActions,
  getters: commentGetters
}