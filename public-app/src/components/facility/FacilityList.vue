<script>
import FacilityBlock from './FacilityBlock.vue';
import CustomButton from "../utility/CustomButton.vue"
import { mapActions, mapMutations, mapGetters } from 'vuex';
export default {
  components:{
    FacilityBlock,
    CustomButton
},
  props:{
    shallowShowcase: Boolean
  },
  data(){
    return {
      selectedIndex: -1,
      searchText: '',
      lists: {
        homePageFacilities: [],
        facilityPageFacilities: []
      },
      selectedType: "ALL",
      selectedAvgGrade: "ALL"
    }
  },
  computed: {
    facilities() {
      let facilities = this.$store.getters['facility/facilities'];
      if(!facilities) return facilities;
      facilities.sort((a, b) => a.available ? -1 : 0);      
      return this.shallowShowcase && facilities.length > 6 ? facilities.splice(0, 6) : facilities;
    },
    filteredFacilities() {
      return this.getFilteredFacilities;
    }
  },
  methods:{
    ...mapActions("facility", ["searchFacilities"]),
    ...mapMutations("facility", ["setFilteredFacilities"]),
    ...mapGetters("facility", ["getFilteredFacilities"]),
    facilitySelected(index){
      this.selectedIndex = index;
      for(let facility of this.$refs.facilities.children){ 
        if(index != facility.getAttribute('data-id')){
          facility.classList.remove('selected');
        }
      }
    },
    determineSelected(index){
      return this.selectedIndex==index;
    },
    search(e) {
      let searchText = this.searchText + "&" + this.selectedType + "&" + this.selectedAvgGrade;
      console.log(searchText);
      console.log(e);
      if(this.searchText === "" && e.key === "Backspace") {
        this.searchFacilities(searchText);
        return;
      }      
      if(e.key === "Enter") {
        if(this.searchText == "") {
          return;
        }
        this.searchFacilities(searchText);
      }
    },
    items() {
      if(this.shallowShowcase) {
        this.searchFacilities(""); // Da se vrate svi objekti na facilityPage-u kad se predje na homePage
        this.lists.homePageFacilities = this.facilities;
        return this.lists.homePageFacilities;
      } else {
        this.lists.facilityPageFacilities = this.filteredFacilities();
        return this.lists.facilityPageFacilities;
      }
    },
    xPressed(e) {
      if(this.searchText === "") {
        return;
      }
      this.searchText = "";
      this.searchFacilities(this.searchText + "&" + this.selectedType + "&" + this.selectedAvgGrade);
    },
    searchByTypeOrAvgGrade(e) {
      let searchText = this.searchText + "&" + this.selectedType + "&" + this.selectedAvgGrade;
      console.log(searchText);
      if(searchText === "&ALL&ALL") {
        this.searchFacilities("");
        return;
      }
      this.searchFacilities(searchText);
    }
  }
}
</script>

<template>
<div class="facility-list">
  <div v-if="!shallowShowcase" class="search-block">
    <div class="search-wrapper">
      <input id="search" placeholder="Search facility..." v-model="this.searchText" @keyup="search"/>
      <fa-icon class="search-icon" :icon="['fas','magnifying-glass']"></fa-icon>
      <fa-icon class="x-icon" :icon="['fas', 'circle-xmark']" @click="xPressed"></fa-icon>
    </div>
    <div class="select-type-wrapper">
      Select type: 
      <select class="select-type" @change="searchByTypeOrAvgGrade" v-model="selectedType">
        <option value="ALL">ALL</option>
        <option value="GYM">GYM</option>
        <option value="POOL">POOL</option>
        <option value="SPORTS CENTER">SPORTS CENTER</option>
        <option value="DANCE STUDIO">DANCE STUDIO</option>
        <option value="OTHER">OTHER</option>
      </select>
    </div>
    <div class="select-avg-grade-wrapper">
      Select average grade: 
      <select class="select-avg-grade" @change="searchByTypeOrAvgGrade" v-model="selectedAvgGrade">
        <option value="ALL">ALL</option>
        <option value="0.0 - 1.0">0.0 - 1.0</option>
        <option value="1.0 - 2.0">1.0 - 2.0</option>
        <option value="2.0 - 3.0">2.0 - 3.0</option>
        <option value="3.0 - 4.0">3.0 - 4.0</option>
        <option value="4.0 - 5.0">4.0 - 5.0</option>
      </select>
    </div>
  </div>
  <div ref="facilities">
    <facility-block v-for="(facility, index) in this.items()" 
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

  .search-wrapper {
    text-align: center;
    position: relative;
  }

  .search-wrapper input {
    width: 100%;
    padding: 20px 0px 20px 50px;
    border-radius: 24px;
    margin-right: 10px;
    &:focus{
      outline: 4px solid $active-primary;
    }
  }

  .search-icon {
    position: absolute;
    left: 15px;
    top: 22px;
    font-size: 22px;
  }

  .x-icon {
    position: absolute;
    right: 50px;
    top: 18px;
    font-size: 32px;
    &:hover {
      color: $active-primary;
      cursor: pointer;
    }
  }

  .select-type, .select-avg-grade {
    box-shadow: 0 4px 10px rgba(0,0,0,0.3);
    padding: 1rem 1.5rem;
    border-radius: 15px;
    background: $dark-primary;
    color: $active-primary;
    margin-top: 15px;
    margin-left: 25px;
    border: 0;
  }

  .select-type-wrapper, .select-avg-grade-wrapper {
    font-size: 18px;
  }

}
</style>