export default{
  setUsers(state, payload){
    state.users = payload;
  },
  addUser(state, user){
    state.users.push(user);
  }
}