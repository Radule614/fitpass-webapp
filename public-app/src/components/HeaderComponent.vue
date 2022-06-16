<script>
import NavigationMenu from './navigation/NavigationMenu.vue'
import LoginComponent from './auth/LoginComponent.vue'
import RegisterComponent from './auth/RegisterComponent.vue'
export default{
  components: {
    NavigationMenu,
    LoginComponent,
    RegisterComponent
  },
  data(){
    return{
      showLogin: false,
      showRegister: false
    }
  },
  computed: {
    isLogged(){
      return this.$store.getters['auth/isLogged'];
    },
    userData(){
      return this.$store.getters['auth/user'];
    }
  },
  methods: {
    logout(event){
      this.$store.dispatch('auth/logout');
    }
  }
}
</script>

<template>
  <header>
    <div class="header-inner">
      <div class="logo">
        <img src="../assets/logo.png" alt="nologo">
      </div>
      <navigation-menu></navigation-menu>
      <div class="account-group">
        <custom-button class="btn-register inverse" v-if="!isLogged" @click="showRegister = true">Register</custom-button>
        <custom-button class="btn-login" v-if="!isLogged" @click="showLogin = true">Login</custom-button>
        <custom-button class="btn-logout inverse" v-if="isLogged" @click="logout">Logout</custom-button>
        <div class="profile-mini" v-if="isLogged && userData">
          {{userData.username}}
        </div>
      </div>
    </div>
    <div class="header-line">
      <div class="logo-part"></div>
      <div class="active-part"></div>
    </div>

    <teleport to="body">
      <login-component :show="showLogin" @close="showLogin = false"></login-component>
      <register-component :show="showRegister" @close="showRegister = false"></register-component>
    </teleport>
  </header>
</template>

<style scoped lang="scss">
  header{
    box-shadow: 0px 0px 30px rgba(0, 0, 0, 0.4);
    position: absolute;
    width: 100%;
    background-color: $light-primary;
    z-index: 10;
    .header-inner{
      display: flex;
      flex-direction: row;
      line-height: 70px;
      position: relative;
      .logo{
        width: 200px;
        background-color:$dark-primary;
        position: relative;
        img{
          display: block;
          position: absolute;
          top:0px;
          bottom:0px;
          left:0px;
          right:0px;
          margin:auto;
          height: 50px;
        }
      }
      .account-group{
        flex-grow: 1;
        display: flex;
        flex-direction:row-reverse;
        line-height: 30px;
        position: relative;
        button{
          line-height: 30px;
          height: 40px;
          margin: 16px 16px 0px 0px;
        }
        .profile-mini{
          line-height: 70px;
          font-size: 19px;
          margin-right: 30px;
        }
      }
    }
    .header-line{
      display: flex;
      flex-direction: row;
      height: 10px;
      .logo-part{
        width: 200px;
      }
      .active-part{
        flex-grow: 1;
        background-color: $dark-primary;
      }
    }
  }
  
</style>