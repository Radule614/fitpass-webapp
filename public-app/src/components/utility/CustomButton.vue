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
  border-radius: 20px;
  outline: 0px;
  
  span{
    position: relative;
  }
  &:hover{ 
    span{
      animation: active-text-animation 0.35s ease-in-out forwards;
    }
    &::before{
      content: "";
      position:absolute;
      width: 100%;
      height: 100%;
      top:0px;
      left:0px;
      animation: active-animation 0.35s ease-out forwards;
      box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.3);
      border-radius: 20px;
      background-color: $active-primary;
    }
  }
  &.inverse{
    background-color: $dark-primary;
    color:$active-primary;
    &:hover{
      span{
        animation: active-text-inverse-animation 0.35s ease-in-out forwards;
      }
      &::before{
        background-color: $active-primary;
        color:$dark-primary;
      }
    }
  }
}

@keyframes active-animation{
  0%{
    transform: scaleX(0.0);
  }
  100%{
    transform: scaleX(1.0);
  }
}

@keyframes active-text-animation{
  0%{
    color:$dark-primary;
  }
  30%{
    color:$dark-primary;
  }
  100%{
    color:$light-primary;
  }
}

@keyframes active-text-inverse-animation{
  0%{
    color:$active-primary;
  }
  30%{
    color:$active-primary;
  }
  100%{
    color:$light-primary;
  }
}
</style>