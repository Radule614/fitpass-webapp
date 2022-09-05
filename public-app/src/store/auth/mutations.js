export default {
  setUser(state, payload) {
    state.token = payload.token;
    state.user = payload.user;
  },
  setToken(state, payload){
    state.token = payload.token;
  },
  setUserData(state, payload){
    state.user = payload.user;
  },
	addCustomerVisitedFacilities(state, visitedFacility) {
		if(state.user.visitedFacilities){
			state.user.visitedFacilities.forEach(item => {
				if(item === visitedFacility) return;
			})
		}
		state.user.visitedFacilities ? state.user.visitedFacilities.push(visitedFacility) : state.user.visitedFacilities = [ visitedFacility ];
	},
	addtrainingToCustomerHistory(state, training) {
		state.user.trainingHistory ? state.user.trainingHistory.push(training) : state.user.trainingHistory = [ training ];
	},
	decrementCustomerAppointmentNumber(state) {
		if(state.user.membership && state.user.membership.appointmentNumber != 0) state.user.membership.appointmentNumber--;
	}
}