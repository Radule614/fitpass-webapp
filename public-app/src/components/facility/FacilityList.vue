<script>
import FacilityBlock from './FacilityBlock.vue';
export default {
  components:{
    FacilityBlock
  },
  props:{
    shallowShowcase: Boolean
  },
  data(){
    return {
      selectedIndex: -1
    }
  },
  computed: {
    facilities(){
      let facilities = this.$store.getters['facility/facilities'];
      if(!facilities) return facilities;
      facilities.sort((a, b) => a.available ? -1 : 0);      
      return this.shallowShowcase && facilities.length > 6 ? facilities.splice(0, 6) : facilities;
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
<div class="facility-list">
  <div v-if="!shallowShowcase" class="search-block">

    search

  </div>
  <div ref="facilities">
    <facility-block v-for="(facility, index) in facilities" 
                    :data-id="index" 
                    v-bind:key="index" 
                    :facility="facility" 
                    :selected="determineSelected(index)"
                    :shallowShowcase="shallowShowcase"
                    @selectedEvent="facilitySelected(index)">
    </facility-block>
  </div>
</div>
</template>

<style scoped lang="scss">
.facility-list{
  .search-block{
    box-shadow:  0 3px 3px -3px rgba(0,0,0,0.3); 
    margin-bottom: 30px;
    padding-bottom: 10px;
  }
}
</style>