<script>
import CustomCheckbox from '../items/CustomCheckbox.vue'
import CustomRadio from '../items/CustomRadio.vue';
import BaseBlock from './BaseBlock.vue';
export default{
  components: { CustomCheckbox, CustomRadio, BaseBlock },
  props:{
    radioItems: Array,
    radioKey: String,
    checkboxItems: Array,
    caption: String
  },
  data() {
    return {
      selectedRadioIndex: -1,
      radioItemStatus: {},
      checkboxItemStatus: {}
    };
  },
  computed:{
    radioItemsProxy(){
      return this.radioItems ? this.radioItems : [];
    },
    checkboxItemsProxy(){
      return this.checkboxItems ? this.checkboxItems : [];
    }
  },
  created(){
    if(this.radioKey) this.radioItemStatus[this.radioKey] = null;
    
    for(let item of this.checkboxItemsProxy){
      this.checkboxItemStatus[item.key] = false;
    }
    this.$emit('selection', { radio: this.radioItemStatus, checkbox: this.checkboxItemStatus });
  },
  methods:{
    checkboxItemHandler(event){
      for(let item in event){
        this.checkboxItemStatus[item] = event[item];
      }
      this.$emit('selection', { radio: this.radioItemStatus, checkbox: this.checkboxItemStatus });
    },
    radioItemHandler(index){
      if(this.selectedRadioIndex == index) return;
      this.selectedRadioIndex = index;
      this.radioItemStatus[this.radioKey] = this.radioItems[index].key;
      this.$emit('selection', { radio: this.radioItemStatus, checkbox: this.checkboxItemStatus });
    }
  }
}
</script>

<template>
  <base-block :caption="caption">
    <custom-radio v-for="(item, index) in radioItemsProxy" :key="index" :item="item" :selected="index==selectedRadioIndex" @selection="radioItemHandler(index)"></custom-radio>
    <custom-checkbox v-for="(item, index) in checkboxItemsProxy" :key="index" :item="item" @selection="checkboxItemHandler($event)"></custom-checkbox>
  </base-block>
</template>

<style scoped lang="scss">

</style>