export default {
  user(state){
    return state.user;
  },
  isLogged(state){
    return !!state.token;
  },
  userType(state){
    return state.user ? state.user.userType : null; 
  }
}