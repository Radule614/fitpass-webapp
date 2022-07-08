<script>
import CustomRadio from './CustomRadio.vue'
import BaseBlock from '../BaseBlock.vue';
export default{
  components: { CustomRadio, BaseBlock },
  props:{
    items: Array,
    caption: String
  },
  data() {
    return {
      selectedIndex: -1,
      itemStatus: {}
    };
  },
  created(){
    for(let item of this.items){
      this.itemStatus[item.name] = false;
    }
    this.$emit('selection', this.itemStatus);
  },
  methods:{
    itemSelected(index){
      if(this.selectedIndex == index) return;
      this.selectedIndex = index;
      for(let item of this.items){
        this.itemStatus[item.name] = false;
      }
      this.itemStatus[this.items[this.selectedIndex].name] = true;
      this.$emit('selection', this.itemStatus);
    }
  }
}
</script>

<template>
  <base-block :caption="caption">
    <custom-radio v-for="(item, index) in items" :key="index" :item="item" :selected="index==selectedIndex" @selection="itemSelected(index)"></custom-radio>
  </base-block>
</template>

<style scoped lang="scss">
</style>