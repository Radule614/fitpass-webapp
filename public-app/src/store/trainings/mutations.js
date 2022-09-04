export default {
	setTrainings(state, payload) {
		state.trainings = payload.trainings;
	},
	addTraining(state, payload) {
		state.trainings.unshift(payload.training);
	},
	removeTraining(state, payload) {
		state.trainings = state.trainings.filter(training => training.id != payload.trainingId);
	} 
}