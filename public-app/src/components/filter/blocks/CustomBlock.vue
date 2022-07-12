<script>
import CustomCheckbox from '../items/CustomCheckbox.vue'
import CustomRadio from '../items/CustomRadio.vue';
import BaseBlock from './BaseBlock.vue';
export default{
  components: { CustomCheckbox, CustomRadio, BaseBlock },
  props:{
    radioItems: Array,
    radioKey: String,
    checkboxKey: String,
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
    for(let item of this.checkboxItemsProxy) this.checkboxItemStatus[item.key] = false;
    this.$emit('selection', { radio: this.radioItemStatus, checkbox: this.getCheckboxOutput() });
  },
  methods:{
    checkboxItemHandler(event){
      for(let item in event) this.checkboxItemStatus[item] = event[item];
      this.$emit('selection', { radio: this.radioItemStatus, checkbox: this.getCheckboxOutput() });
    },
    radioItemHandler(index){
      if(this.selectedRadioIndex == index) return;
      this.selectedRadioIndex = index;
      this.radioItemStatus[this.radioKey] = this.radioItems[index].key;
      this.$emit('selection', { radio: this.radioItemStatus, checkbox: this.getCheckboxOutput() });
    },
    getCheckboxOutput(){
      if(this.checkboxKey){
        let output = {};
        output[this.checkboxKey] = null;
        let i = 0;
        for(let item in this.checkboxItemStatus){
          if(this.checkboxItemStatus[item]){
            if(i==0) output[this.checkboxKey] = "";
            if(i!=0) output[this.checkboxKey] += ",";
            output[this.checkboxKey] += item;
            i += 1;
          }
        }
        return output;
      }else{
        return this.checkboxItemStatus;
      }
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