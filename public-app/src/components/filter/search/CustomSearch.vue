<script>
import BaseItem from '../BaseItem.vue';
import { debounce } from 'lodash';
export default{
  components: { BaseItem },
  props: {
    item: Object
  },
  created(){
    this.inputHandler = debounce(event => {
      this.$emit('inputChanged', event.target.value);
    }, 30);
  },
  beforeUnmount() {
    this.inputHandler.cancel();
  }
}
</script>

<template>
  <base-item>
    <input class="item-wrapper" type="text" @input="inputHandler" :placeholder="item.value" maxlength="25">
  </base-item>
</template>

<style scoped lang="scss">
.filter-base-item{
  input.item-wrapper{
    cursor: inherit;
    line-height: 30px;
    background-color: #fff;
    border-radius: 0px;
    box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.1);
    width: 100%;
    color:$dark-secondary;
    margin-bottom: 0px;
  }
}
</style>