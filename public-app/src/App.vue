<script>
import HeaderComponent from "./components/HeaderComponent.vue";
import FooterComponent from "./components/FooterComponent.vue";
export default {
  components: {
      HeaderComponent,
      FooterComponent
  },
  data(){
    return {
      btnUpEnabled: false
    }
  },
  computed:{
    loading(){
      return !this.$store.getters['facility/facilities'];
    }
  },
  created(){
    window.addEventListener('scroll', this.handleScroll);
    this.$store.dispatch('auth/checkAuthentication');
    this.$store.dispatch('facility/fetchFacilities');
  },
  unmounted () {
    window.removeEventListener('scroll', this.handleScroll);
  },	
  methods: {
    btnUpClick(event){
      window.scrollTo(0, 0);
    },
    handleScroll(event){
      this.btnUpEnabled = window.scrollY >= 400
    }
  },
}
</script>

<template>
  <header-component></header-component>
  <div class="content-wrapper">
    <router-view></router-view>
  </div>
  <footer-component></footer-component>
  <button class="btn-up" v-if="btnUpEnabled" @click="btnUpClick">
    <fa-icon :icon="['fas', 'arrow-up']"></fa-icon>
  </button>
  <div class="loading-screen" v-if="loading">
    <div class="background"></div>
    <div class="loader"></div>
  </div>
</template>

<style lang="scss">
#app {
  //font-family: Avenir, Helvetica, Arial, sans-serif;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
  font-family: 'Roboto';
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  color: $dark-primary;
  .content-wrapper{
    padding-top: 80px;
    min-height: 900px;
  }
  .btn-up{
    background-color: $dark-primary;
    color: $light-primary;
    position:fixed;
    bottom:10px;
    left:10px;
    border-radius: 50%;
    width: 50px;
    height: 50px;
    padding:0px;
    margin:0px;
    border:2px solid $light-primary;
    box-shadow: none;
    z-index: 2;
  }
  .loading-screen{
    position: fixed;
    width:100%;
    height: 100%;
    top:0px;
    left:0px;
    z-index: 100;
    .background{
      background-color: $light-primary;
      position: absolute;
      width:100%;
      height: 100%;
      top:0px;
      left:0px;
      opacity: 0.7;
    }
    .loader{
      position: absolute;
      top:0px;
      left:0px;
      right: 0px;
      bottom:0px;
      margin:auto;
    }
  }

}

</style>
