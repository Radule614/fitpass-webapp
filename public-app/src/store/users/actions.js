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
    context.commit('setUsers', responseData);
  },
  async fetchFacilityTrainers(context, payload){
    const response = await fetch(`${Settings.serverUrl}/api/facilities/manager/trainers/${payload.facility_id}`, {
      method: 'GET',
      headers: {
        'Authorization': 'Bearer ' + context.rootState.auth.token
      },
    });
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.messages || responseData.message || 'Failed to fetch filtered users.');
    context.commit('setUsers', responseData);
  },
  async fetchFacilityVisitors(context, payload){
    const response = await fetch(`${Settings.serverUrl}/api/facilities/manager/customers/${payload.facility_id}`, {
      method: 'GET',
      headers: {
        'Authorization': 'Bearer ' + context.rootState.auth.token
      },
    });
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.messages || responseData.message || 'Failed to fetch filtered users.');
    context.commit('setUsers', responseData);
  },
  usersClear(context, _){
    context.commit('setUsers', []);
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
			method: 'PATCH',
			headers: {'Content-Type': 'application/json', 
								'Data-Type': 'application/json', 
								'authorization': 'bearer ' + context.rootState.auth.token },
			body: JSON.stringify(payload)
		});
		const data = await res.json();
		context.commit('updateUser', data);
		return data;
	},
	async changeUserPassword(context, payload) {
		const res = await fetch(`${Settings.serverUrl}/api/users/updatePassword`, {
			method: 'PATCH',
			headers: {'Content-Type': 'application/json',
								'Data-Type': 'application/json',
								'Authorization': 'Bearer ' + context.rootState.auth.token },
			body: JSON.stringify(payload)
		});
		const data = await res.json();
		if(!res.ok) throw new Error(data || "Failed to change password");
	},
  async fetchCoupons(context, _){
    const response = await fetch(`${Settings.serverUrl}/api/coupon/all`);
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.messages || responseData.message || 'Failed to fetch coupons.');
    context.commit('setCoupons', responseData);
  },
  async addCoupon(context, payload){
    const response = await fetch(`${Settings.serverUrl}/api/coupon/create`, {
      method: 'POST',
      headers: {
        'Authorization': 'Bearer ' + context.rootState.auth.token
      },
      body: payload.formData
    });
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.messages || responseData.message || 'Failed to add coupon.');
    let data = { id: responseData.id };
    for (const pair of payload.formData.entries()) {
      data[pair[0]] = pair[1];
    }
    context.commit('addCoupon', data);
  },
  async deleteCoupon(context, payload){
    const response = await fetch(`${Settings.serverUrl}/api/coupon/delete`, {
      method: 'POST',
      headers: {
        'Authorization': 'Bearer ' + context.rootState.auth.token
      },
      body: JSON.stringify({ id: payload.content.id })
    });
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.messages || responseData.message || 'Failed to delete coupon.');
    context.commit('removeCoupon', { id: payload.content.id });
  },
}