export default {
	getTrainerTrainings(state, getters, rootState, rootGetters) {
		return state.trainings.filter(training => training.trainerUsername === rootGetters["auth/username"]);
	},
	getAll(state) {
		return state.trainings;
	},
	getUserTrainings(state) {
		return state.userTrainings;
	},
	getFacilityTrainings(state){
		return state.facilityTrainings;
	}
}