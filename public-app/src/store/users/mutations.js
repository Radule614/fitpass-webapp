export default{
  setUsers(state, payload){
    state.users = payload;
  },
  addUser(state, user){
    state.users.push(user);
  },
  removeUser(state, username){
    state.users = state.users.filter(user => user.username != username);
  }
}