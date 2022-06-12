import facilityMutations from './mutations.js';
import facilityActions from './actions.js';
import facilityGetters from './getters.js';

export default {
  namespaced: true,
  state(){
    return {
      facilities: null
    }
  },
  mutations: facilityMutations,
  actions: facilityActions,
  getters: facilityGetters
}