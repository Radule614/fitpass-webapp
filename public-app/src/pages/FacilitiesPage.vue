<script>
import FacilityList from '../components/facility/FacilityList.vue';
export default {
  components:{
    FacilityList
  }
}
</script>

<template>
  <div>
    <div class="facilities container">
      <router-view></router-view>
      <facility-list></facility-list>
    </div>
  </div>
</template>

<style scoped lang="scss">
  .facilities{
    padding-bottom: 150px;
    padding-top: 40px;
  }
</style>