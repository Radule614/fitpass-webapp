<script>
</script>

<template>
  <button>
    <span>
      <slot></slot>
    </span>
  </button>
</template>
 
<style scoped lang="scss">
button {
  position: relative;
  display: block;
  font-size:16px;
  border: 0px;
  line-height: 40px;
  padding: 0 32px;
  box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.3);
  background-color: $light-primary;
  color:$dark-primary;
  border-radius: 25px;
  outline: 0px;
  &.flat{
    padding: 0px;
  }
  &::before{
    content: "";
    position:absolute;
    width: 100%;
    height: 100%;
    top:0px;
    left:0px;
    border-radius: 50%;
    background-color: $active-primary;
    transform: scale(0);
    transition: transform 0.3s ease-in-out, border-radius 0.3s ease-in-out;
  }
  &.block{
    border-radius: 0px;
  }
  &.no-shadow{
    box-shadow: none;
  }
  span{
    position: relative;
    transition:color 0.3s ease-in-out 0.1s;
  }
  &:hover{ 
    span{
      color:$light-primary;
      transition:color 0.3s ease-in-out;
    }
    &::before{
      border-radius: 25px;
      transform: scale(1);
    }
    &.block::before{
      transition: transform 0.3s ease-in-out, border-radius 0.7s ease-in-out;
      border-radius: 0px;
    }
  }
  &.inverse{
    background-color: $dark-primary;
    color:$active-primary;
    &:hover{
      span{
        color:$light-primary;
      }
      &::before{
        background-color: $active-primary;
        color:$dark-primary;
      }
    }
  }
}

</style>