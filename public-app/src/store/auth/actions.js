import Settings from '../../settings.js';
import { router } from '../../router.js';
import axios from 'axios';
import store from "../../store/index.js";

let timer;
let membershipInterval;

export default {
  async login(context, payload) {
    const response = await fetch(`${Settings.serverUrl}/api/auth/login`, {
      method: 'POST',
      body: JSON.stringify({
        username: payload.username,
        password: payload.password
      })
    })
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.message || 'Failed to authenticate.');

    const expiresIn = +responseData.expirationTime - new Date().getTime();

    localStorage.setItem('token', responseData.token);
    localStorage.setItem('tokenExpiration', responseData.expirationTime);

    timer = setTimeout(() => { context.dispatch('logout') }, expiresIn)

    context.commit('setToken', { token: responseData.token });
    context.dispatch('getUserData', {token: responseData.token});

  },
  async signup(context, payload) {
    const response = await axios.post(`${Settings.serverUrl}/api/auth/register`, payload);

    console.log(response);
    if(response.status !== 200) {
      throw new Error(response.message);
    }

    console.log(response.data);

    context.commit('setUserData', {
      user: response.data
    });
    await context.dispatch('login', response.data);
  },
  async getUserData(context, payload){
    const response = await fetch(`${Settings.serverUrl}/api/users/get`, {
      method: 'GET',
      headers: {
        'Authorization': 'Bearer ' + payload.token
      }
    })
    const responseData = await response.json();
    if (!response.ok) throw new Error(responseData.message || 'Failed to fetch user data.');
    console.log(responseData);
    context.commit('setUserData', { user: responseData });
  },
  logout(context, payload){
    localStorage.removeItem('token');
    localStorage.removeItem('tokenExpiration');
    
    clearTimeout(timer);
		clearInterval(membershipInterval);

    context.commit('setUser', { token: null, user: null });
    store.commit('comments/setComments', []);
    if(router) router.push('/home');
  },
  async checkAuthentication(context, payload){
    const token = localStorage.getItem('token');
    const tokenExpiration = localStorage.getItem('tokenExpiration');
    const expiresIn = +tokenExpiration - new Date().getTime();

    if(expiresIn < 0) return

    timer = setTimeout(() => { context.dispatch('logout') }, expiresIn);

    if(token){
      context.commit('setToken', { token: token });
      await context.dispatch('getUserData', {token: token});
    }
  }
}