<script>
import BaseBlock from './BaseBlock.vue';
import CustomSearch from '../items/CustomSearch.vue';
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
      this.itemStatus[item.key] = "";
    }
    this.$emit('search', this.itemStatus);
  },
  methods:{
    inputHandler(text, key){
      this.itemStatus[key] = text;
      this.$emit('search', this.itemStatus);
    }
  }
}
</script>

<template>
  <base-block :caption="caption">
    <custom-search v-for="(item, index) in items" :key="index" :item="item" @inputChanged="inputHandler($event, item.key)"></custom-search>
  </base-block>
</template>

<style scoped lang="scss">
</style>