export default {
	setTrainings(state, payload) {
		state.trainings = payload.trainings;
	},
	addTraining(state, payload) {
		state.trainings.unshift(payload.training);
	}
}