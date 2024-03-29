export default {
	setTrainings(state, payload) {
		state.trainings = payload.trainings;
	},
	addTraining(state, payload) {
		state.trainings.unshift(payload.training);
	},
	removeTraining(state, payload) {
		state.trainings = state.trainings.filter(training => training.id != payload.trainingId);
	},
	setFacilityTrainings(state, payload) {
		state.facilityTrainings = [];
		state.facilityTrainings.push(...payload.trainings);
	},
	setUserTrainings(state, payload) { 
		state.userTrainings = [];
		state.userTrainings.push(...payload.trainings);
	},
	removeUserTraining(state, payload) {
		state.userTrainings = state.userTrainings.filter(training => training.id != payload.trainingId);
	}
}