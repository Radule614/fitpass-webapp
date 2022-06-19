export default {
  facilities(state){
    return JSON.parse(JSON.stringify(state.facilities)); 
  },
  getFilteredFacilities(state) {
    return state.filteredFacilities;
  }
}