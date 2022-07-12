<script>
export default{
  props:{
    caption: String
  },
}
</script>

<template>
  <div class="base-block">
    <div class="caption">{{caption}}</div>
    <div>
      <slot></slot>
    </div>
  </div>
</template>

<style scoped lang="scss">
.base-block{
  padding:20px;
  box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.3);
  width: 350px;
  .caption{
    padding-bottom: 8px;
    text-transform: uppercase;
    font-size: 15px;
    font-weight: bold;
    color:$active-primary;
  }
}
</style>