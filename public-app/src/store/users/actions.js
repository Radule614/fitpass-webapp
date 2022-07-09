import Settings from '../../settings.js';

export default{
  async fetchUsers(context, payload){
    const response = await fetch(`${Settings.serverUrl}/api/users/get/filtered`, {
      method: 'POST',
      headers: {
        'Authorization': 'Bearer ' + context.rootState.auth.token
      },
      body: JSON.stringify(payload)
    });
    const responseData = await response.json();
    if (!response.ok) {
      const error = new Error(responseData.messages || responseData.message || 'Failed to fetch filtered users.');
      throw error;
    }
    context.commit('setUsers', responseData);
  }
}