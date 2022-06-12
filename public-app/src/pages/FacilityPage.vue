<script>
import FacilityBlock from '../components/facility/FacilityBlock.vue';
export default {
  components:{
    FacilityBlock
  },
  computed: {
    facilities(){
      return this.$store.getters['facility/facilities'];
    }
  }
}
</script>

<template>
  <div class="page-header">
    <h2>Facilities</h2>
  </div>
  <div class="facilities">
    <facility-block v-for="(facility, index) in facilities" v-bind:key="index" :facility="facility"></facility-block>
  </div>
</template>

<style scoped lang="scss">
  .page-header{
    background-color: $dark-secondary;
    h2{
      margin: 0px;
      padding:50px 0px;
      text-align: center;
      color: $light-secondary;
    }
  }
  .facilities{
    display: grid;
    background-color: $dark-secondary;
    min-height: 1000px;
    grid-template-columns: repeat(4, minmax(0, 1fr));
    grid-row-gap: 40px;
    grid-column-gap: 40px;
    padding: 40px;
  }
  @media screen and (max-width:1600px) {
    .facilities{
      grid-template-columns: repeat(3, minmax(0, 1fr));
    }
  }
  @media screen and (max-width:1200px) {
    .facilities{
      grid-template-columns: repeat(2, minmax(0, 1fr));
    }
  }
</style>