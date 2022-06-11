let timer;

export default {
  async login(context, payload) {
    const response = await fetch('http://localhost:9999/api/auth/login', {
      method: 'POST',
      body: JSON.stringify({
        username: payload.username,
        password: payload.password
      })
    })

    const responseData = await response.json();
    
    if (!response.ok) {
      const error = new Error(responseData.message || 'Failed to authenticate.');
      throw error;
    }

    const expiresIn = +responseData.expirationTime - new Date().getTime();

    localStorage.setItem('token', responseData.token);
    localStorage.setItem('tokenExpiration', responseData.expirationTime);

    timer = setTimeout(() => {
      context.dispatch('logout');
    }, expiresIn)

    context.commit('setToken', {
      token: responseData.token
    });
    context.dispatch('getUserData', {token: responseData.token});
    
  },
  async signup(context, payload) {

    //TODO

  },
  async getUserData(context, payload){
    const response = await fetch('http://localhost:9999/api/users/get', {
      method: 'GET',
      headers: {
        'Authorization': 'Bearer ' + payload.token
      }
    })
    const responseData = await response.json();
    if (!response.ok) {
      const error = new Error(responseData.message || 'Failed to fetch user data.');
      throw error;
    }
    console.log(responseData);

    context.commit('setUserData', {
      user: responseData
    });
  },
  logout(context, payload){
    localStorage.removeItem('token');
    localStorage.removeItem('tokenExpiration');
    
    clearTimeout(timer);
    
    context.commit('setUser', {
      token: null,
      user: null
    });
  },
  checkAuthentication(context, payload){
    const token = localStorage.getItem('token');
    const tokenExpiration = localStorage.getItem('tokenExpiration');

    const expiresIn = +tokenExpiration - new Date().getTime();

    if(expiresIn < 0){
      return
    }

    timer = setTimeout(() => {
      context.dispatch('logout');
    }, expiresIn);

    if(token){
      context.commit('setToken', {
        token: token
      });
      context.dispatch('getUserData', {token: token});
    }
  }
}