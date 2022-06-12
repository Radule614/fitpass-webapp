export default {
  facilities(state){
    return JSON.parse(JSON.stringify(state.facilities)); 
  }
}