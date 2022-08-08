<script>
import {
  LMap,
  LIcon,
  LTileLayer,
  LMarker,
  LControlLayers,
  LTooltip,
  LPopup,
  LPolyline,
  LPolygon,
  LRectangle,
} from "@vue-leaflet/vue-leaflet";
import "leaflet/dist/leaflet.css";
export default {
  components: {
    LMap,
    LIcon,
    LTileLayer,
    LMarker,
    LControlLayers,
    LTooltip,
    LPopup,
    LPolyline,
    LPolygon,
    LRectangle,
  },
  props:{
    locationProp: {
      lat:Number,
      lng:Number
    },
    showOnly: Boolean,
    markerTooltip: String 
  },
  emits:['locationChanged'],
  data() {
    return {
      zoom: 2,
      center: [0, 0],
      location: {lat:0, lng:0}
    };
  },
  computed:{
    getLocation(){
      return this.showOnly ? this.locationProp : this.location;
    }
  },
  mounted(){
    if(this.showOnly){
      this.zoom = 12;
    }
    if(this.locationProp){
      this.location = this.locationProp;
    }
    if(this.location && this.location.lat && this.location.lng) this.center = [this.location.lat, this.location.lng];
  },
  methods: {
    setNewLocation(event){
      if(!this.showOnly&&event.latlng){
        this.location = event.latlng;
        this.$emit('locationChanged', this.location);
      }
    }
  },
};
</script>

<template>
  <div style="height: 300px; width: 300px;">
    <l-map
      v-model="zoom"
      v-model:zoom="zoom"
      :maxZoom="19"
      :minZoom="1"
      :center="getLocation"
      @click="setNewLocation($event)"
    >
      <l-tile-layer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
      ></l-tile-layer>

      <l-marker :lat-lng="getLocation">
        <l-tooltip>
          <div v-if="showOnly">{{markerTooltip}}</div>
          <div v-else>Choose location</div>
        </l-tooltip>
      </l-marker>
    </l-map>
  </div>
</template>