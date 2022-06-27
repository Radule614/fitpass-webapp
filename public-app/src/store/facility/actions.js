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
  },
  async addFacility(context, payload){
    for (var x of payload) console.log(x);
    console.log(payload.get('file'));
    const response = await fetch(`${Settings.serverUrl}/api/facilities/add`, {
      method: 'POST',
      headers: {
        'Authorization': 'Bearer ' + context.rootState.auth.token
      },
      body: payload
    });
    console.log(response);
    const responseData = await response.json();
    if (!response.ok) {
      const error = new Error(responseData.message || 'Failed to add new facility.');
      throw error;
    }
    console.log(responseData);
  }
}