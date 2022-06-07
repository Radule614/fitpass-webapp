let timer;

export default {
  async login(context, payload) {
    await context.dispatch('auth', {
      ...payload,
      mode: 'login'
    });
  },
  async signup(context, payload) {
    await context.dispatch('auth', {
      ...payload,
      mode: 'register'
    });
  },
  logout(context, payload){
    localStorage.removeItem('token');
    localStorage.removeItem('userId');
    localStorage.removeItem('tokenExpiration');
    
    clearTimeout(timer);
    
    context.commit('setUser', {
      token: null,
      userId: null
    });
  },
  checkAuthentication(context, payload){
    const token = localStorage.getItem('token');
    const userId = localStorage.getItem('userId');
    const tokenExpiration = localStorage.getItem('tokenExpiration');

    const expiresIn = +tokenExpiration - new Date().getTime();

    if(expiresIn < 0){
      return
    }

    timer = setTimeout(() => {
      context.dispatch('logout');
    }, expiresIn);

    if(token && userId){
      context.commit('setUser', {
        token: token,
        userId: userId
      });
    }
  },
  async auth(context, payload){
    let url = 'https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=AIzaSyBgc1YZj4sPRHGmHdZ-2uMUWNIlCT6HuHQ';
    if(payload.mode == 'login'){
      url = 'https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=AIzaSyBgc1YZj4sPRHGmHdZ-2uMUWNIlCT6HuHQ';
    }

    const response = await fetch(url, {
      method: 'POST',
      body: JSON.stringify({
        email: payload.username,
        password: payload.password,
        returnSecureToken: true
      })
    })

    const responseData = await response.json();
    if (!response.ok) {
      const error = new Error(responseData.message || 'Failed to authenticate.');
      throw error;
    }

    const expiresIn = +responseData.expiresIn * 1000;
    const expirationDate = new Date().getTime() + expiresIn;    

    localStorage.setItem('token', responseData.idToken);
    localStorage.setItem('userId', responseData.localId);
    localStorage.setItem('tokenExpiration', expirationDate);

    timer = setTimeout(() => {
      context.dispatch('logout');
    }, expiresIn)

    context.commit('setUser', {
      token: responseData.idToken,
      userId: responseData.localId
    });
  }
}