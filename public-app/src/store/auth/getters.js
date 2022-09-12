export default {
  user(state){
    return state.user;
  },
  token(state){
    return state.token;
  },
  isLogged(state){
    return !!state.token;
  },
  userType(state){
    return state.user ? state.user.userType : null; 
  },
  managerFacility(state){
    return state.user && state.user.facility ? state.user.facility : null;
  },
	username(state) {
		return state.user.username;
	},
	customerTrainingHistory(state) {
		return state.user.trainingHistory ? state.user.trainingHistory : [];
	}
}