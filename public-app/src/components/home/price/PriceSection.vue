<script>
  import PriceItem from './PriceItem.vue'
  export default{
    components:{
      PriceItem
    },
    data(){
      return {
        prices: [
          {title: "bronze", color: "#CD7F32", amount:3000, text: "lorem ipsum", bold: false},
          {title: "silver", color: "#C0C0C0", amount:3000, text: "dolor sit amet", bold: false},
          {title: "gold", color: "#FFD700", amount:3000, text: "consectetur adipiscing", bold: true}
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