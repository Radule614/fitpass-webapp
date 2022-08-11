<script>
import LeafletMap from '../utility/LeafletMap.vue';
import FacilityDetails from '../facility/FacilityDetails.vue';
export default {
  components: { LeafletMap, FacilityDetails },
  computed: {
    facility(){
      return this.$store.getters['auth/managerFacility'];
    }
  }
}
</script>

<template>
  <div v-if="facility">
    <facility-details :facility="facility"></facility-details>
  </div>
  <div v-else>
    no facility
  </div>
</template>

<style scoped lang="scss">
.location{
  width: 100%;
  height: 400px;
  position: relative;
  border:1px solid gray;
  .map{
    width: 100%!important;
    height: 100%!important;
    position: absolute;
    top:0px;
    left:0px;
  }
}
.info{
  max-width: 700px;
  padding-top: 30px;
  .row {
    box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.2);
    margin: 5px 0px 0px 0px;
    margin-right: 30px;
    &:first-child{
      margin-top: 0px;
    }
    .col{
      color: $dark-primary;
      font-style: normal;
      font-size: 15px;
      line-height: 36px;
      &:nth-child(2){
        font-style: italic;
        background-color: rgba($active-primary, $alpha: 0.4);
      }
    }
  }
}
</style>