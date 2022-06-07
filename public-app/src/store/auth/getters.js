export default {
  usedId(state){
    return state.usedId;
  },
  isLogged(state){
    return !!state.token
  }
}