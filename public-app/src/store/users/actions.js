import Settings from '../../settings.js';

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
    
    console.log(responseData);
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
    
    console.log(responseData);
    context.commit('addUser', responseData);
  }
}