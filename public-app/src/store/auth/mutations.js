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
  }
}