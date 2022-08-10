<script>
import { routes } from '../../router.js';
export default {
  data(){
    return {
      localRoutes: []
    }
  },
  created() {
    this.localRoutes = routes.filter(item => item.name == 'manager')[0].children.filter(item => item.meta && item.meta.nav);
  }
}
</script>

<template>
  <div class="manager-panel">
    <div class="manager-menu-wrapper">
      <div class="manager-menu">
        <div class="manager-nav">
          <router-link v-for="(route, index) in localRoutes" :key="index" :to="'/manager/' + route.path">{{route.meta.nav}}</router-link>
        </div>
      </div>
    </div>
    <div class="manager-content">
      <router-view></router-view>
    </div>
  </div>
</template>

<style scoped lang="scss">
.manager-panel{
  position: relative;
  min-height: 1400px;
  .manager-menu-wrapper{
    position: absolute;
    height: 100%;
    top:0px;
    left:0px;
    box-shadow: 1px 0px 4px rgba(0, 0, 0, 0.3);
    width: 300px;
    background-color: #fff;
    .manager-menu{
      position: sticky;
      top:0px;
      left:0px;
      height: 300px;
      font-size: 18px;
      .manager-nav{
        & > * {
          display: block;
          padding:20px;
          border-bottom: 1px solid rgba(0, 0, 0, 0.1);
          transition: background-color 0.08s ease-in-out, color 0.08s ease-in-out;
          background-color: #fff;
          &.router-link-exact-active{
            background-color: rgba($active-primary, $alpha: 1)!important;
            color: $light-primary;
          }
          &:hover{
            background-color: rgba($active-primary, $alpha: 0.4);
          }
        }
      }
    }
  }
  
  .manager-content{
    margin-left: 300px;
    padding: 30px;
  }
}
</style>