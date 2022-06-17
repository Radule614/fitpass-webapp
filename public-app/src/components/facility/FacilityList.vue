<script>
import FacilityBlock from './FacilityBlock.vue';
export default {
  components:{
    FacilityBlock
  },
  data(){
    return {
      selectedIndex: -1
    }
  },
  computed: {
    facilities(){
      return this.$store.getters['facility/facilities'];
    }
  },
  methods:{
    facilitySelected(index){
      this.selectedIndex = index;
      for(let facility of this.$refs.facilities.children){ 
        if(index != facility.getAttribute('data-id')){
          facility.classList.remove('selected');
        }
      }
    },
    determineSelected(index){
      return this.selectedIndex==index
    }
  }
}
</script>

<template>
  <div class="container" ref="facilities">
    <facility-block v-for="(facility, index) in facilities" 
                    :data-id="index" 
                    v-bind:key="index" 
                    :facility="facility" 
                    :selected="determineSelected(index)" 
                    @selectedEvent="facilitySelected(index)">
    </facility-block>
  </div>
</template>

<style scoped lang="scss">

</style>