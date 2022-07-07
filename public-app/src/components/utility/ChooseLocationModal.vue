<script>
import ModalComponent from "../ModalComponent.vue";
import LeafletMap from "./LeafletMap.vue";
export default{
  props: {
    locationProp: {
      lat:Number,
      lng:Number
    },
    show: Boolean
  },
  emits:['close', 'confirm', 'locationChanged'],
  components:{
    ModalComponent,
    LeafletMap
  },
  data(){
    return {
      location:{
        lat:0,
        lan:0
      },
      loading: false
    }
  },
  beforeMount(){
    if(this.locationProp) this.location = this.locationProp;
  },
  methods:{
    async emitLocationData(){
      this.loading = true;
      const response = await fetch(`https://nominatim.openstreetmap.org/reverse?format=jsonv2&lat=${this.location.lat}&lon=${this.location.lng}`, {
        method: 'GET'
      });
      const locationData = await response.json();
      const address = locationData.address || {};
      
      const data = {
        lat: this.location.lat,
        lng: this.location.lng,
        country: address.country,
        state: address.state,
        city: address.city,
        town: address.town,
        road: address.road,
        number: address.house_number,
        postcode: address.postcode
      }
      this.loading = false;
      this.$emit('confirm', data);
    },
  }
}
</script>

<template>
  <teleport to="body">
    <modal-component :responsive="true" :show="show" buttonText="confirm" :simple="true" @close="$emit('close')" @confirm="emitLocationData" :disabled="loading">
      <template #body>
        <div class="map-wrap">
          <leaflet-map class="map" :locationProp="locationProp" @locationChanged="location=$event"></leaflet-map>
        </div>
      </template>
    </modal-component>
  </teleport>
</template>

<style scoped lang="scss">
.map-wrap{
  height:400px;
  .map{
    position: absolute;
    width:100%!important;
    height: 100%!important;
    top:0px;
    left:0px;
  }
}
</style>