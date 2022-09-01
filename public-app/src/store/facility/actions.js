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
    const response = await fetch(`${Settings.serverUrl}/api/content/create`, {
      method: 'POST',
      headers: {
        'Authorization': 'Bearer ' + context.rootState.auth.token
      },
      body: payload.formData
    });
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.messages || responseData.message || 'Failed to add content.');
    let data = { id: responseData.id };
    for (const pair of payload.formData.entries()) {
      data[pair[0]] = pair[1];
    }
    payload.facility.content.push(data);
  },
  async deleteContent(context, payload){
    const response = await fetch(`${Settings.serverUrl}/api/content/delete`, {
      method: 'POST',
      headers: {
        'Authorization': 'Bearer ' + context.rootState.auth.token
      },
      body: JSON.stringify({ id: payload.content.id })
    });
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.messages || responseData.message || 'Failed to add content.');

    const content = payload.facility.content;
    for(let key in content){
      if(content[key].id === payload.content.id){
        content.splice(key, 1);
        break;
      }
    }
  },
  async setTrainer(context, payload){
    const response = await fetch(`${Settings.serverUrl}/api/content/trainer/add`, {
      method: 'POST',
      headers: {
        'Authorization': 'Bearer ' + context.rootState.auth.token
      },
      body: JSON.stringify({ trainer_id: payload.trainerUsername, content_id: payload.content.id })
    });
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.messages || responseData.message || 'Failed to set trainer.');
    payload.content.trainer = responseData;
  },
  async removeTrainer(context, payload){
    const response = await fetch(`${Settings.serverUrl}/api/content/trainer/clear`, {
      method: 'POST',
      headers: {
        'Authorization': 'Bearer ' + context.rootState.auth.token
      },
      body: JSON.stringify({ content_id: payload.content.id })
    });
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.messages || responseData.message || 'Failed to clear trainer.');
    payload.content.trainer = null;
  }
}