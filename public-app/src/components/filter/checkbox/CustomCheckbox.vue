<script>
import BaseItem from '../BaseItem.vue';
export default{
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
        ret[this.item.name] = val;
        this.$emit("selection", ret);
      }
    }
  },
  components: { BaseItem }
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