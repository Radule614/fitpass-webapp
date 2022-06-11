<template>
  <header-component></header-component>
  <div class="content-wrapper">
    <router-view></router-view>
  </div>
  <footer-component></footer-component>
  <button class="btn-up" v-if="btnUpEnabled" @click="btnUpClick">
    <fa-icon :icon="['fas', 'arrow-up']"></fa-icon>
  </button>
</template>

<script>
import HeaderComponent from "./components/HeaderComponent.vue";
import FooterComponent from "./components/FooterComponent.vue";
export default {
  components: {
      HeaderComponent,
      FooterComponent
  },
  created(){
    window.addEventListener('scroll', this.handleScroll);
    this.$store.dispatch('auth/checkAuthentication');
  },
  data(){
    return {
      btnUpEnabled: false,
    }
  },
  unmounted () {
    window.removeEventListener('scroll', this.handleScroll);
  },
  methods: {
    btnUpClick(event){
      window.scrollTo(0, 0);
    },
    handleScroll(event){
      this.btnUpEnabled = window.scrollY >= window.innerHeight
    }
  },
}

</script>

<style lang="scss">
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
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
  }

}

</style>
