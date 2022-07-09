import Settings from '../../settings.js';
import axios from "axios";

export default {
  async fetchFacilities(context, payload){
    const response = await fetch(`${Settings.serverUrl}/api/facilities/all`, {
      method: 'GET'
    })
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.messages || responseData.message || 'Failed to fetch user data.');

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
    const response = await fetch(`${Settings.serverUrl}/api/facilities/add`, {
      method: 'POST',
      headers: {
        'Authorization': 'Bearer ' + context.rootState.auth.token
      },
      body: payload
    });
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.messages || responseData.message || 'Failed to add new facility.');

    console.log(responseData);
    if(responseData) context.commit('addFacility', responseData);
  },
  async removeFacility(context, payload){
    const response = await fetch(`${Settings.serverUrl}/api/facilities/delete`, {
      method: 'POST',
      headers: {
        'Authorization': 'Bearer ' + context.rootState.auth.token
      },
      body: JSON.stringify(payload)
    });
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.messages || responseData.message || 'Failed to remove facility.');

    context.commit('removeFacility', payload.name)
  }
}