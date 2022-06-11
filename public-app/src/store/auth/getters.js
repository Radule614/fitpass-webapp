export default {
  user(state){
    return state.user;
  },
  isLogged(state){
    return !!state.token
  }
}