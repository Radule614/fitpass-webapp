<script>
import UserDetails from './UserDetails.vue';
export default{
  components: { UserDetails },
  props: {
    user: Object,
    selectable: Boolean,
    selected: Boolean
  },
  emits: ["selectedEvent"]
}
</script>

<template>
  <div class="grid-item-wrap" @click="$emit('selectedEvent')" :class="{'selectable': selectable,'selected': selected}">
    <div class="background"></div>
    <div class="grid-item">
      <user-details :user="user"></user-details>
    </div>
  </div>
</template>

<style scoped lang="scss">
.grid-item-wrap{
  box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.3);
  height: 420px;
  background-color: $light-primary;
  position: relative;
  transition: box-shadow 0.2s;
  &.selectable{
    cursor: pointer;
  }
  &.selected{
    box-shadow: 5px -5px 25px 0px rgba(0, 0, 0, 0.3);
    .background{
      &::before,
      &::after{
        width: 16.97px;
        height: 16.97px;
      }
      &::before{
        transform: rotate(-45deg) translateX(8.5px) translateY(-3.5px);
      }
      &::after{
        transform: rotate(-45deg) translateX(8.8px) translateY(3.4px);
      }
    }
    .grid-item{
      transform:translateX(12px) translateY(-12px);
    }
  }
  .background{
    background-color: $active-primary;
    position: absolute;
    width:100%;
    height: 100%;
    top:0px;
    left:0px;
    &::before,
    &::after{
      content:"";
      width: 0px;
      transition: all 0.2s;
      position:absolute;
      width: 0px;
      height: 0px;
      background-color: $active-primary;
      transform: rotate(-45deg) translateX(0px) translateY(0px);
    }
    &::before{
      top:0px;
      left: 0px;
    }
    &::after{
      bottom:0px;
      right: 0px;
    }
  }
  .grid-item{
    background-color: #fff;
    transition: transform 0.2s;
    position: absolute;
    width: 100%;
    height: 100%;
  }
}
</style>