<script>
import BaseItem from './BaseItem.vue';
export default{
  components: { BaseItem },
  props: {
    item: Object
  },
  data() {
    return {
      value: false
    };
  },
  computed: {
    selected: {
      get() {
        return this.value;
      },
      set(val) {
        this.value = val;
        let ret = {};
        ret[this.item.key] = val;
        this.$emit("selection", ret);
      }
    }
  }
}
</script>

<template>
  <base-item>
    <div class="item-wrapper nohighlight" @click="selected=!selected" :class="{'selected': selected}">
      <div class="item-box"></div>
      <label>{{item.value}}</label>
    </div>
  </base-item>
</template>

<style scoped lang="scss">
</style>