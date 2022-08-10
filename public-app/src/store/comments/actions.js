import Settings from '../../settings.js';

export default {
  async getAllFacilityComments(context, facility_id){
    const response = await fetch(`${Settings.serverUrl}/api/comments/all/${facility_id}`, {
      method: 'GET'
    });
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.messages || responseData.message || 'Failed to fetch facility comments.');
    context.commit('setComments', responseData);
  }
}