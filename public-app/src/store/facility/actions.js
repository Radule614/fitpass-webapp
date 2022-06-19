import Settings from '../../settings.js';
import axios from "axios";

export default {
  async fetchFacilities(context, payload){
    const response = await fetch(`${Settings.serverUrl}/api/facilities/all`, {
      method: 'GET'
    })
    const responseData = await response.json();
    if (!response.ok) {
      const error = new Error(responseData.message || 'Failed to fetch user data.');
      throw error;
    }
    console.log(responseData);
    
    context.commit('setFacilities', {
      facilities: responseData
    });

    context.commit('setFilteredFacilities', {
      filteredFacilities: responseData
    });

  },
  async searchFacilities({ commit }, searchText) {
    const response = await axios.get(`${Settings.serverUrl}/api/facilities/search/${searchText}`);
    
    commit('setFilteredFacilities', {
      filteredFacilities: response.data
    });
  }
}