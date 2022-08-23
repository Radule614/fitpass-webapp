import Settings from '../../settings.js';
import store from "../../store/index.js";

export default{
  async fetchUsers(context, payload){
    const response = await fetch(`${Settings.serverUrl}/api/users/get/filtered`, {
      method: 'POST',
      headers: {
        'Authorization': 'Bearer ' + context.rootState.auth.token
      },
      body: payload ? JSON.stringify(payload) : JSON.stringify(null)
    });
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.messages || responseData.message || 'Failed to fetch filtered users.');
    //console.log(responseData);
    context.commit('setUsers', responseData);
  },
  async createUser(context, payload){
    const response = await fetch(`${Settings.serverUrl}/api/users/create`, {
      method: 'POST',
      headers: {
        'Authorization': 'Bearer ' + context.rootState.auth.token
      },
      body: payload
    });
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.messages || responseData.message || 'Failed to fetch filtered users.');
    
    context.commit('addUser', responseData);
  },
  async deleteUser(context, payload){
    if(payload.username == context.rootState.auth.user.username) return;
    const response = await fetch(`${Settings.serverUrl}/api/users/delete`, {
      method: 'POST',
      headers: {
        'Authorization': 'Bearer ' + context.rootState.auth.token
      },
      body: JSON.stringify(payload)
    });
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.messages || responseData.message || 'Failed to delete user.');

    store.dispatch('facility/fetchFacilities');
    store.dispatch('facility/searchFacilities', "");
    context.commit('removeUser', payload.username);
  },
	async updateUser(context, payload) {
		const res = await fetch(`${Settings.serverUrl}/api/users/update`, {
			method: 'PUT',
			headers: {'Content-Type': 'application/json', 
								'Data-Type': 'application/json', 
								'authorization': 'bearer ' + localStorage.getItem('token')},
			body: JSON.stringify(payload)
		});
		const data = await res.json();
		context.commit('updateUser', data);
		return data;
	}
}