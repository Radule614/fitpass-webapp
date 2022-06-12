import Settings from '../../settings.js';

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
  }
}