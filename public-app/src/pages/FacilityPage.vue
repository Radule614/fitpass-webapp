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
      <h1>Facilities</h1>
    </div>
    <div class="facilities container">
      <facility-list></facility-list>
    </div>
  </div>
</template>

<style scoped lang="scss">
  .page-header{
    h1{
      margin: 0px;
      padding:50px 0px 70px 0px;
      text-align: center;
      color: $dark-primary;
    }
  }
  .facilities{
    padding-bottom: 150px;
  }
</style>