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
    <div class="page-header">
      <h2>Facilities</h2>
    </div>
    <div class="facilities">
      <facility-list></facility-list>
    </div>
  </div>
</template>

<style scoped lang="scss">
  .page-header{
    h2{
      margin: 0px;
      padding:50px 0px;
      text-align: center;
      color: $dark-primary;
    }
  }
  .facilities{
    padding-bottom: 150px;
  }
</style>