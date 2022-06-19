export default {
  setFacilities(state, payload){
    state.facilities = payload.facilities;
  },
  setFilteredFacilities(state, payload) {
    state.filteredFacilities = payload.filteredFacilities;
  }
}