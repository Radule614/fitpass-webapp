import Settings from '../../settings.js';

export default {
  async getAllFacilityComments(context, facility_id){
    const response = await fetch(`${Settings.serverUrl}/api/comments/all/${facility_id}`, {
      method: 'GET'
    });
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.messages || responseData.message || 'Failed to fetch facility comments.');
    context.commit('setComments', responseData);
  },
  async commentApproval(context, payload){
    const response = await fetch(`${Settings.serverUrl}/api/comments/approval`, {
      method: 'POST',
      body: JSON.stringify({id: payload.comment.id, approval: payload.approval})
    });
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.messages || responseData.message || 'Failed to fetch facility comments.');
    if(payload.approval){
      payload.comment.approved = true;
    }else{
      context.commit('deleteComment', payload.comment);
    }
  },
	async addComment(context, payload) {
		const res = await fetch(`${Settings.serverUrl}/api/comments/add`, {
			method: 'POST',
			headers: { 'Content-Type': 'application/json', 'Data-Type': 'application/json', 'Authorization': 'Bearer ' + context.rootState.auth.token},
			body: JSON.stringify(payload)
		});
		if(!res.ok) throw new Error(res.message || 'Failed to add comment.'); 
		const newComment = await res.json();
		context.commit('addComment', newComment);
	}
}