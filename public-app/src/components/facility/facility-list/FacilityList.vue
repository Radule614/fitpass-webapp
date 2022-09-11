<script>
import FacilityBlock from './FacilityBlock.vue';
import { mapActions, mapMutations, mapGetters } from 'vuex';
import ConfirmModal from '../../utility/ConfirmModal.vue';
import FacilitiesSort from './FacilitiesSort.vue';
import CustomButton from '../../utility/CustomButton.vue';
export default {
  components:{
    FacilityBlock,
    ConfirmModal,
    FacilitiesSort,
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
      selectedAvgGrade: "ALL",
      confirmModalActive: false,
      facilityToRemove: null,
			selectedStatus: "ALL",
			showSort: false,
			firstTime: true
    }
  },
  computed: {
    facilities() {
      let facilities = this.$store.getters['facility/facilities'];
      if(!facilities) return facilities;
      facilities.sort((a, b) => a.available && !b.available ? -1 : 0);
      return this.shallowShowcase && facilities.length > 6 ? facilities.splice(0, 6) : facilities;
    },
    filteredFacilities() {
      let facilities = this.getFilteredFacilities();
			if(this.selectedStatus !== "ALL") {
				const status = (this.selectedStatus === 'true');
				facilities = facilities.filter(facility => {
					return facility.available === status;
				});
			}
      return facilities ? facilities.sort((a, b) => a.available && !b.available ? -1 : 0) : facilities;
    },
    loggedUserType(){
      return this.$store.getters['auth/userType'];
    },
    currentRouteName(){
      return this.$router.currentRoute.value.name;
    }
  },
  methods:{
    ...mapActions("facility", ["searchFacilities"]),
    ...mapMutations("facility", ["setFilteredFacilities"]),
    ...mapGetters("facility", ["getFilteredFacilities"]),
    search(e) {
      let safeSearchText = this.escapeInput(this.searchText);
      let searchText = safeSearchText + "&" + this.selectedType + "&" + this.selectedAvgGrade;
      console.log(safeSearchText);
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
        this.lists.homePageFacilities = this.facilities;
        return this.lists.homePageFacilities;
      } else {
        this.lists.facilityPageFacilities = this.filteredFacilities;
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
      let safeSearchText = this.escapeInput(this.searchText);
      let searchText = safeSearchText + "&" + this.selectedType + "&" + this.selectedAvgGrade;
      console.log(safeSearchText);
      if(searchText === "&ALL&ALL") {
        this.searchFacilities("");
        return;
      }
      this.searchFacilities(searchText);
    },
    escapeInput(unsafe) {
     return unsafe
         .replace(/&/g, "")
         .replace(/</g, "")
         .replace(/>/g, "")
         .replace(/"/g, "")
        .replace(/\//g, "")
         .replace(/'/g, "");
    },
    scrollToTop(){
        window.scrollTo(0, 0);
    },
    async removeFacility(){
      try{
        await this.$store.dispatch('facility/removeFacility', {name: this.facilityToRemove});
        this.clearRemoval();
      }catch(error){
        console.error(error);
        this.clearRemoval();
      }
    },
    clearRemoval(){
      this.confirmModalActive = false;
      this.facilityToRemove = null
    },
		sortByName(asc) {
			if(this.lists.facilityPageFacilities) {
				asc ? 
					this.lists.facilityPageFacilities.sort((a, b) => a.name.localeCompare(b.name)) : 
					this.lists.facilityPageFacilities.sort((a, b) => b.name.localeCompare(a.name));
			}
		},
		sortByLocation(asc) {
			if(this.lists.facilityPageFacilities) {
				asc ? 
					this.lists.facilityPageFacilities.sort((a, b) => {
						const cityA = a.location.city ? a.location.city : '';
						const cityB = b.location.city ? b.location.city : '';
						return cityA.localeCompare(cityB);
					}) : 
					this.lists.facilityPageFacilities.sort((a, b) => {
						const cityA = a.location.city ? a.location.city : '';
						const cityB = b.location.city ? b.location.city : '';
						cityA.localeCompare(cityB);
					});
			}
		},
		sortByAvgGrade(asc) {
			if(this.lists.facilityPageFacilities) {
				asc ? 
					this.lists.facilityPageFacilities.sort((a, b) => a.grade - b.grade) : 
					this.lists.facilityPageFacilities.sort((a, b) => b.grade - a.grade);
			}
		}
  },
  unmounted() {
    this.setFilteredFacilities({filteredFacilities : this.$store.getters["facility/facilities"]});
  }
}
</script>

<template>
<div class="facility-list">
  <div v-if="!shallowShowcase" class="control-block">
    <div class="search-block" v-if="currentRouteName == 'facility'">
      <div class="search-wrapper">
        <input id="search" placeholder="Search facility..." v-model="this.searchText" @keyup="search"/>
        <fa-icon class="search-icon" :icon="['fas','magnifying-glass']"></fa-icon>
        <fa-icon class="x-icon" :icon="['fas', 'circle-xmark']" @click="xPressed"></fa-icon>
      </div>
			<div class="filters">
				<div class="select-type-wrapper filter">
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
				<div class="select-avg-grade-wrapper filter">
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
				<div class="select-status-wrapper filter">
					Select status:
					<select class="select-status" v-model="selectedStatus">
						<option value="ALL">ALL</option>
						<option value="true">Available</option>
						<option value="false">Not Available</option>
					</select>
				</div>
			</div>
    </div>
		<div class="sort-wrapper">
			<CustomButton @click="showSort = !showSort">Sort</CustomButton>
			<transition name="facilities-sort">
				<FacilitiesSort v-if="showSort" @sortName="sortByName" @sortLocation="sortByLocation" @sortAvgGrade="sortByAvgGrade"/>
			</transition>
		</div>
    <div class="button-group">
      <custom-link v-if="loggedUserType == 'ADMIN' && currentRouteName == 'facility'" class="inverse" to="/facility/add" @click="scrollToTop">Create Facility</custom-link>
    </div>
  </div>
  <div ref="facilities">
    <facility-block v-for="(facility, index) in this.items()" 
                    v-bind:key="index" 
                    :facility="facility" 
                    :selected="selectedIndex==index"
                    :shallowShowcase="shallowShowcase"
                    @selectedEvent="selectedIndex=index"
                    @remove="confirmModalActive = true; facilityToRemove = $event">
    </facility-block>
  </div>

  <confirm-modal :show="confirmModalActive" @close="clearRemoval" @confirm="removeFacility"></confirm-modal>
</div>
</template>

<style scoped lang="scss">
.facility-list{
  .control-block{
    box-shadow:  0 3px 3px -3px rgba(0,0,0,0.3); 
    margin-bottom: 30px;
    padding-bottom: 10px;
    .button-group{
      display: flex;
      justify-content: right;
      & > *{
        margin-left: 15px;
      }
    }
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

  .select-type, .select-avg-grade, .select-status {
    box-shadow: 0 4px 10px rgba(0,0,0,0.3);
    padding: 1rem 1.5rem;
    border-radius: 15px;
    background: $dark-primary;
    color: $active-primary;
    margin-top: 15px;
    margin-left: 15px;
    border: 0;
  }

  .select-type-wrapper, .select-avg-grade-wrapper, .select-status-wrapper {
    font-size: 18px;
  }

	.filters {
		display: flex;
		align-items: center;
		justify-content: flex-start;
		margin-top: 15px;
		flex-wrap: wrap;
		.filter select {
			margin-right: 20px;
		}
	}

	.sort-wrapper {
		margin-top: 30px;
	}
	.facilities-sort-enter-from {
		transform: translateX(-400px);
		opacity: 0;
	}
	.facilities-sort-enter-active {
		transition: all 0.5s ease;
	}
}
</style>