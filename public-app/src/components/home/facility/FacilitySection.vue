<script>
  import FacilityItem from './FacilityItem.vue'
  export default {
    components: {
      FacilityItem
    },
    data(){
      return {
        facilities: [
          {name: "Ime", ftype: "Teretana", status: "radi", address: "adresa", grade: 4.5, workHours: "7-22"},
          {name: "Ime2", ftype: "bazen", status: "radi", address: "adresa", grade: 4.2, workHours: "9-20"},
          {name: "Ime3", ftype: "Teretana", status: "radi", address: "adresa", grade: 4.5, workHours: "7-22"},
          {name: "Ime4", ftype: "bazen", status: "radi", address: "adresa", grade: 4.2, workHours: "9-20"},
          {name: "Ime5", ftype: "Teretana", status: "radi", address: "adresa", grade: 4.5, workHours: "7-22"},
          {name: "Ime6", ftype: "bazen", status: "radi", address: "adresa", grade: 4.2, workHours: "9-20"},
          {name: "Ime7", ftype: "Teretana", status: "radi", address: "adresa", grade: 4.5, workHours: "7-22"},
          {name: "Ime8", ftype: "bazen", status: "radi", address: "adresa", grade: 4.2, workHours: "9-20"}
        ]
      }
    }
  }
</script>

<template>
  <div class="facilities row">
    <facility-item class="col-6" v-for="(facility, index) in facilities" v-bind:key="index" :facility="facility"></facility-item>
  </div>
</template>

<style scoped lang="scss">
  .facilities{
    display: flex;
    flex-direction: row;
    flex-wrap: wrap;
  }
</style>