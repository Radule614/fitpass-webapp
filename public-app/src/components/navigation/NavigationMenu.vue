<script>
import NavigationItem from './NavigationItem.vue'
import { routes } from '../../router.js';
export default{
  components: {
    NavigationItem
  },
  computed:{
    activeRoutes(){
      return routes.filter(route => route.meta && 
                                    route.meta.nav && 
                                      (!route.meta.userTypes ||
                                      this.$store.getters['auth/isLogged'] && 
                                        (route.meta.userTypes.includes('ANY') || 
                                        route.meta.userTypes.includes(this.$store.getters['auth/userType']))));
    }
  }
}
</script>

<template>
  <nav class="menu">
    <navigation-item v-for="route in activeRoutes" v-bind:key="route.id" :path="route.path" :text="route.meta.nav"></navigation-item>
  </nav>
</template>

<style scoped lang="scss">
  .menu{
    display: flex;
    flex-direction:row;
    font-size: 18px;
  }
</style>