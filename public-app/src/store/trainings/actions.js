import Settings from "@/settings"

export default {
	async fetchTrainings(context, payload) {
		const res = await fetch(`${Settings.serverUrl}/api/trainings/get/requiredContentTypes`, {
			method: 'POST',
			body: payload ? JSON.stringify(payload) : JSON.stringify(null)
		});
		if(!res.ok) throw new Error("Failed to fetch trainings");

		const trainings = await res.json();
		context.commit('setTrainings', { trainings });
	},
	async addTraining(context, payload) {
		const res = await fetch(`${Settings.serverUrl}/api/trainings/add`, {
			method: 'POST',
			headers: { 'Authorization': 'Bearer ' + context.rootState.auth.token },
			body: payload.formData
		});
		const training = await res.json();
		context.commit('addTraining', { training });
		return training;
	},
	async fetchFacilityTrainings(context, payload){
		const response = await fetch(`${Settings.serverUrl}/api/trainings/facility/${payload.facility_id}`, {
      method: 'GET'
    })
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.messages || responseData.message || 'Failed to fetch user data.');
		context.commit('setFacilityTrainings', { trainings: responseData })
	},
	async fetchUserTrainings(context, payload) {
		const response = await fetch(`${Settings.serverUrl}/api/trainings/get/filtered`, {
			method: 'POST',
			headers: {'Contenty-Type': 'applicaiton/json', 'Data-Type': 'application/json' },
			body: JSON.stringify(payload)
		});
		const data = await response.json();
		if(!response.ok) throw new Error("Failed to fetch user trainings");
		await context.commit('setUserTrainings', { trainings: data });
		await context.commit('setTrainings', { trainings: data });
	}
}