import Settings from '../../settings.js';
import axios from "axios";
import store from "../../store/index.js";

export default {
  async fetchFacilities(context, payload){
    const response = await fetch(`${Settings.serverUrl}/api/facilities/all`, {
      method: 'GET'
    })
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.messages || responseData.message || 'Failed to fetch user data.');

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

    store.dispatch('users/fetchUsers');
    context.commit('removeFacility', payload.name)
  },
  async setManager(context, payload){
    const response = await fetch(`${Settings.serverUrl}/api/facilities/manager/set`, {
      method: 'POST',
      headers: {
        'Authorization': 'Bearer ' + context.rootState.auth.token
      },
      body: JSON.stringify(payload)
    });
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.messages || responseData.message || 'Failed to remove facility.');
    
    context.dispatch('fetchFacilities');
    context.dispatch('searchFacilities', "");
    store.dispatch('users/fetchUsers');
  },
  async clearManager(context, payload){
    const response = await fetch(`${Settings.serverUrl}/api/facilities/manager/clear`, {
      method: 'POST',
      headers: {
        'Authorization': 'Bearer ' + context.rootState.auth.token
      },
      body: JSON.stringify(payload)
    });
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.messages || responseData.message || 'Failed to remove facility.');
    context.dispatch('fetchFacilities');
    context.dispatch('searchFacilities', "");
    store.dispatch('users/fetchUsers');
  },
  async addContent(context, payload){
    const response = await fetch(`${Settings.serverUrl}/api/facilities/manager/content/add`, {
      method: 'POST',
      headers: {
        'Authorization': 'Bearer ' + context.rootState.auth.token
      },
      body: payload.formData
    });
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.messages || responseData.message || 'Failed to add content.');

    let data = {};
    for (const pair of payload.formData.entries()) {
      data[pair[0]] = pair[1];
    }
    payload.facility.content.push(data);
  },
  async deleteContent(context, payload){
    const response = await fetch(`${Settings.serverUrl}/api/facilities/manager/content/delete`, {
      method: 'POST',
      headers: {
        'Authorization': 'Bearer ' + context.rootState.auth.token
      },
      body: JSON.stringify({ name: payload.content.name })
    });
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.messages || responseData.message || 'Failed to add content.');

    const content = payload.facility.content;
    for(let key in content){
      if(content[key].name == payload.content.name){
        content.splice(key, 1);
        break;
      }
    }
  }
}