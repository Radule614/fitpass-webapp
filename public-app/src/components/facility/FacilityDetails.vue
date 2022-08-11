<script>
import LeafletMap from '../utility/LeafletMap.vue';
export default {
  components: { LeafletMap },
  props: {
    facility: Object
  },
  computed: {
    latLng() {
      return { lat: this.address.lat || null, lng: this.address.lng || null}
    },
    address() {
      return this.facility.location || {}
    },
    workHourDisplay() {
      let temp = this.facility.workingHours;
      return `${temp.startHour}:${temp.startMinute} - ${temp.endHour}:${temp.endMinute}`
    },
    managerName() {
      if (this.facility.manager)
        return this.facility.manager.firstname + " " + this.facility.manager.lastname;
      return "";
    }
  }
}
</script>

<template>
  <div>
    <div class="location">
      <leaflet-map class="map" :showOnly="true" :locationProp="latLng" :markerTooltip="facility.name"></leaflet-map>
    </div>
    <div class="info">
      <div class="row">
        <div class="col">name:</div>
        <div class="col">{{facility.name}}</div>
      </div>
      <div class="row">
        <div class="col">available:</div>
        <div class="col">{{facility.available}}</div>
      </div>
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
      <div class="row" v-if="managerName!=''">
        <div class="col">manager:</div>
        <div class="col">{{managerName}}</div>
      </div>
    </div>
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