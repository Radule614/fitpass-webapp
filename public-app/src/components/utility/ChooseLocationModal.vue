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
      }
    }
  },
  beforeMount(){
    if(this.locationProp) this.location = this.locationProp;
  }
}
</script>

<template>
  <teleport to="body">
    <modal-component :responsive="true" :show="show" buttonText="confirm" :simple="true" @close="$emit('close')" @confirm="$emit('confirm', location)">
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