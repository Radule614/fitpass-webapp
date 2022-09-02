import Settings from "@/settings"

export default {
	async fetchTrainings(context, payload) {
		const res = await fetch(`${Settings.serverUrl}/api/trainings/get/requiredContentTypes`, {
			method: 'POST',
			body: payload ? JSON.stringify(payload) : JSON.stringify(null)
		});
		if(!res.ok) throw new Error("Failed to fetch trainings");

		const trainings = await res.json();
		// trainings.reverse();
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
	}
}