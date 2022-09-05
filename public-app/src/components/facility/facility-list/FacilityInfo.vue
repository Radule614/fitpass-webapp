<script>
import LeafletMap from '../../utility/LeafletMap.vue';
import UserSelectModal from '../../users/UserSelectModal.vue';
import ConfirmModal from '../../utility/ConfirmModal.vue';
export default{
  components: { LeafletMap, UserSelectModal, ConfirmModal },
  props: {
    facility: {
      available: Boolean,
      content: Array,
      facilityType: String,
      name: String,
      location: Object,
      grade: Number,
      logoUrl: String,
      manager: Object,
      workingHours: {
        startHour: Number,
        startMinute: Number,
        endHour: Number,
        endMinute: Number
      }
    },
    onlyAvailableManagers: Boolean
  },
  data() {
    return {
      managerModalActive: false,
      clearModalActive: false,
      loading: false
    }
  },
  computed: {
    loggedUserType(){
      return this.$store.getters['auth/userType'];
    },
    workHourDisplay() {
      let temp = this.facility.workingHours;
      return `${temp.startHour}:${temp.startMinute} - ${temp.endHour}:${temp.endMinute}`
    },
    address() {
      return this.facility.location || {}
    },
    latLng() {
      return { lat: this.address.lat || null, lng: this.address.lng || null}
    },
    managerName() {
      if (this.facility.manager)
        return this.facility.manager.firstname + " " + this.facility.manager.lastname;
      return "";
    }
  },
  methods: {
    selectManagerHandler(manager){
      this.loading = true;
      try{
        this.$store.dispatch('facility/setManager', { managerUsername: manager.username, facilityName: this.facility.name });
        this.managerModalActive = false;
        this.loading = false;
      }catch(error){
        console.error(error);
        this.managerModalActive = false;
        this.loading = false;
      }
    },
    clearManagerHandler(){
      this.loading = true;
      try{
        this.$store.dispatch('facility/clearManager', { facilityName: this.facility.name });
        this.clearModalActive = false;
        this.loading = false;
      }catch(error){
        console.error(error);
        this.clearModalActive = false;
        this.loading = false;
      }
    },
    setAvailability(available){
      this.loading = true;
      try{
        this.$store.dispatch('facility/setAvailability', { facility: this.facility, available: available });
        this.clearModalActive = false;
        this.loading = false;
      }catch(error){
        console.error(error);
        this.clearModalActive = false;
        this.loading = false;
      }
    },
    scrollToTop(){
      window.scrollTo(0, 0);
    }
  }
}
</script>

<template>
  <div class="info">
    <div class="left">
      <div class="row">
        <div class="col">average grade:</div>
        <div class="col">{{facility.grade}}</div>
      </div>
      <div class="row">
        <div class="col">working hours:</div>
        <div class="col">{{workHourDisplay}}</div>
      </div>
      <div class="row">
        <div class="col">country:</div>
        <div class="col">{{address.country}}</div>
      </div>
      <div class="row">
        <div class="col">city:</div>
        <div class="col">{{address.city || address.town}}</div>
      </div>
      <div class="row">
        <div class="col">street:</div>
        <div class="col">{{address.road}} {{address.number}}</div>
      </div>
      <div class="row">
        <div class="col">manager:</div>
        <div class="col">{{managerName}}</div>
      </div>
      <div class="button-group">
        <custom-button v-if="facility.available && loggedUserType == 'ADMIN'" class="block" @click="setAvailability(false)">Make unavailable</custom-button>
        <custom-button v-if="!facility.available && loggedUserType == 'ADMIN'" class="block" @click="setAvailability(true)">Make available</custom-button>
        <custom-button v-if="loggedUserType == 'ADMIN'" class="block" @click="clearModalActive = true">Clear Manager</custom-button>
        <custom-button v-if="loggedUserType == 'ADMIN'" class="block" @click="managerModalActive = true">Set Manager</custom-button>
        <custom-link :to="`/facility/${facility.name}`" @click="scrollToTop">Details</custom-link>
      </div>
    </div>
    <div class="right">
      <div class="location">
        <leaflet-map class="map" :showOnly="true" :locationProp="latLng" :markerTooltip="facility.name"></leaflet-map>
      </div>
    </div>

    <user-select-modal :show="managerModalActive" @close="managerModalActive = false" @confirm="selectManagerHandler($event)" userType="MANAGER"></user-select-modal>
    <confirm-modal :show="clearModalActive" @close="clearModalActive = false" @confirm="clearManagerHandler"></confirm-modal>
  </div>
</template>

<style scoped lang="scss">
.info{
  display: flex;
  position: relative;
  .left{
    flex: 1;
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
    .button-group{
      margin-right: 30px;
      margin-top: 10px;
      display: flex;
      justify-content: right;
      flex-wrap: wrap;
      & > button {
        margin-left:10px;
        background-color: #fff;
        box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.2);
        margin-bottom: 10px;
        &:first-child{
          margin-left: 0px;
        }
      }
    }
  }
  .right{
    position: relative;
    .location{
      width: 600px;
      height: 355px;
      display: flex;
      justify-content: center;
      align-items: center;
      border:1px solid gray;
      position: relative;
      .map{
        width: 100%!important;
        height: 100%!important;
        position: absolute;
        top:0px;
        left:0px;
      }
    }
  }
}
</style>