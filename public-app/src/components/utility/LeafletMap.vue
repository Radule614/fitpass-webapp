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
      location: {lat:0, lng:0}
    };
  },
  beforeMount(){
    if(this.locationProp){
      this.location = this.locationProp;
    }
  },
  methods: {
    setNewLocation(event){
      this.location = event.target._latlng;
      this.$emit('locationChanged', this.location)
    }
  },
};
</script>

<template>
  <div style="height: 300px; width: 300px;">
    <l-map
      v-model="zoom"
      v-model:zoom="zoom"
      :center="[44, 20]"
    >
      <l-tile-layer
        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
      ></l-tile-layer>

      <l-marker v-if="!showOnly" :lat-lng="this.location" draggable @moveend="setNewLocation($event)">
        <l-tooltip>
          Choose location
        </l-tooltip>
      </l-marker>

      <l-marker v-if="showOnly" :lat-lng="this.location">
        <l-tooltip>
          {{markerTooltip}}
        </l-tooltip>
      </l-marker>

    </l-map>
  </div>
</template>