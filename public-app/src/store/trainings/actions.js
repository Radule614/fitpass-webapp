import Settings from "@/settings"

export default {
	async fetchUserTrainings(context, payload) {
		const res = await fetch(`${Settings.serverUrl}/api/trainings/${payload.username}`);
		const data = await res.json();

		context.commit('setUserTrainings', { trainings: data });
	},
	async fetchTrainings(context) {
		const res = await fetch(`${Settings.serverUrl}/api/trainings/all`);
		if(!res.ok) throw new Error("Failed to fetch trainings");

		const trainings = await res.json();
		trainings.reverse();
		context.commit('setTrainings', { trainings });
	},
	async addTraining(context, payload) {
		const res = await fetch(`${Settings.serverUrl}/api/trainings/add`, {
			method: 'POST',
			body: payload.formData
		});
		const training = await res.json();
		context.commit('addTraining', { training });
	}
}