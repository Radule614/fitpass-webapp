<script>
  import { ref } from 'vue';
import PriceItem from './PriceItem.vue'
import { useStore } from 'vuex';
import { computed } from '@vue/reactivity';
  export default{
    components:{
      PriceItem
    },
		setup() {
			const store = useStore();
			const prices = computed(() => store.getters['memberships/getPrices']);


			return { prices }
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