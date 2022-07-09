<script>
export default{}
</script>

<template>
  <div class="filter-base-item">
    <slot v-bind="{...$props, ...$attrs}"></slot>
  </div>
</template>

<style lang="scss">

//WARNING: NOT SCOPED

.filter-base-item{
  margin-top: 8px;
  &:first-child{
    margin-top: 0px;
  }
  .item-wrapper{
    position: relative;
    display: flex;
    background-color: #fff;
    box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.1);
    padding: 10px 0px;
    cursor: pointer;
    &.selected{
      background-color: rgba($active-primary, $alpha: 0.3);
      .item-box{
        background-color: $active-primary;
      }
    }
    .item-box{
      width: 22px;
      height: 22px;
      outline:2px solid $active-primary;
      border: 2px solid #fff;
      position:relative;
      margin-left: 12px;
      +label{
        cursor: pointer;
        flex:1;
        color: $dark-secondary;
        padding-left: 10px;
        line-height: 22px;
      }
    }
  }
}
</style>