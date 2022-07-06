export default {
  setFacilities(state, payload){
    state.facilities = payload.facilities;
  },
  setFilteredFacilities(state, payload) {
    state.filteredFacilities = payload.filteredFacilities;
  },
  addFacility(state, facility){
    state.facilities.push(facility);
  },
  removeFacility(state, facilityName){
    state.facilities = state.facilities.filter(item => item.name != facilityName);
    state.filteredFacilities = state.filteredFacilities.filter(item => item.name != facilityName);
  }
}