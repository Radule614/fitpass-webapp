<script>
  import PriceItem from './PriceItem.vue'
  export default{
    components:{
      PriceItem
    },
    data(){
      return {
        prices: [
          {title: "Small", color: "#CD7F32", amount:5000, duration: 1, appointmentNumber: 20, text: "20 training sessions per month in some of sport facilities that we offer", bold: false},
          {title: "Medium", color: "#C0C0C0", amount:13500, duration: 3, appointmentNumber: 22, text: "22 training sessions per month in some of sport facilities that we offer", bold: false},
          {title: "Large", color: "#FFD700", amount:53000, duration: 12, appointmentNumber: 24, text: "24 training sessions per month in some of sport facilities that we offer", bold: true}
        ]
      }
    }
  }
</script>

<template>
  <div class="prices">
    <price-item v-for="(price, index) in prices" v-bind:key="index" :price="price"></price-item>
  </div>
</template>

<style>
  .prices{
    display: flex;
    flex-direction: row;
    align-items: center;
    justify-content: center;
    position: relative;
    margin: auto;
  }
</style>