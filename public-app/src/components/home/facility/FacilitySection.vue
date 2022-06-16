<script>
import FacilityItem from './FacilityItem.vue'
export default {
  components: {
    FacilityItem
  },
  computed:{
    facilities(){
      return this.$store.getters['facility/facilities'];
    }
  }
}
</script>

<template>
  <div class="facilities row">
    <facility-item class="col-6" v-for="(facility, index) in facilities" v-bind:key="index" :facility="facility"></facility-item>
  </div>
</template>

<style scoped lang="scss">
  .facilities{
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
  }
</style>