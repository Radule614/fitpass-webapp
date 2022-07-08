<script>
import BaseBlock from '../BaseBlock.vue';
import CustomSearch from './CustomSearch.vue';
export default{
  components: { BaseBlock, CustomSearch },
  props: {
    items: Array,
    caption: String
  },
  data(){
    return {
      itemStatus: {}
    }
  },
  created(){
    for(let item of this.items){
      this.itemStatus[item.name] = "";
    }
    this.$emit('search', this.itemStatus);
  },
  methods:{
    inputHandler(text, itemName){
      this.itemStatus[itemName] = text;
      this.$emit('search', this.itemStatus);
    }
  }
}
</script>

<template>
  <base-block :caption="caption">
    <custom-search v-for="(item, index) in items" :key="index" :item="item" @inputChanged="inputHandler($event, item.name)"></custom-search>
  </base-block>
</template>

<style scoped lang="scss">
</style>