<template>
  <header-component></header-component>
  <div class="content-wrapper">
    <router-view></router-view>
  </div>
  <footer-component></footer-component>
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
    this.$store.dispatch('auth/checkAuthentication');
  }
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
}

</style>
