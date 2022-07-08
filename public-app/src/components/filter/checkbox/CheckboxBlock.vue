<script>
import CustomCheckbox from './CustomCheckbox.vue'
import BaseBlock from '../BaseBlock.vue';
export default{
  components: { CustomCheckbox, BaseBlock },
  props:{
    items: Array,
    caption: String
  },
  data() {
    return {
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
    itemSelected(event){
      for(let item in event){
        this.itemStatus[item] = event[item];
      }
      this.$emit('selection', this.itemStatus);
    }
  }
}
</script>

<template>
  <base-block :caption="caption">
    <custom-checkbox v-for="(item, index) in items" :key="index" :item="item" @selection="itemSelected($event)"></custom-checkbox>
  </base-block>
</template>

<style scoped lang="scss">

</style>