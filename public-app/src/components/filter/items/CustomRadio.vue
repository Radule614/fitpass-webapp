<script>
import BaseItem from './BaseItem.vue';
export default{
  components: { BaseItem },
  props: {
    item: Object,
    selected: Boolean
  }
}
</script>

<template>
  <base-item>
    <div class="item-wrapper nohighlight" @click="$emit('selection')" :class="{'selected': selected}">
      <div class="item-box"></div>
      <label>{{item.value}}</label>
    </div>
  </base-item>
</template>

<style scoped lang="scss">
.filter-base-item .item-wrapper .item-box{
  border-radius: 50%;
}
</style>