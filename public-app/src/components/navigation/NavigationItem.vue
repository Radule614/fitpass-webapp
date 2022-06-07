<script>
  export default {
    props: {
      routeName: String,
      text: String
    }
  }
</script>

<template>
  <div class="menu-item">
    <router-link :to="routeName">{{text}}</router-link>
  </div>
</template>

<style scoped lang="scss">
  .menu-item a{
    display: block;
    padding: 0px 35px;
    position: relative;
    text-decoration: none;
    box-shadow: 0px 0px 1px rgba(0, 0, 0, 0.5);
    &:hover{
      color: $active-primary;
      &::after{
        content: "";
        width: 0;
        height: 0;
        border-style: solid;
        border-width: 0 7px 7px 7px;
        border-color: transparent transparent $active-primary transparent;
        display: block;
        position: absolute;
        bottom: 0px;
        left: 0px;
        right: 0px;
        margin: auto;
        transition: border 0.25s;
      }
    }
    &.router-link-active{
      color: $active-primary;
      &:hover{
        color: $active-primary;
      }
      &::before{
        content: "";
        position: absolute;
        height: 10px;
        width: 100%;
        background-color: $active-primary;
        bottom: -10px;
        left: 0px;
        animation: nav-active-animation 0.25s ease-out forwards;
      }
      &::after{
        content: "";
        width: 0;
        height: 0;
        border-style: solid;
        border-width: 0 12px 12px 12px;
        border-color: transparent transparent $active-primary transparent;
        display: block;
        position: absolute;
        bottom: 0px;
        left: 0px;
        right: 0px;
        margin: auto;
        transition: border 0.25s;
      }
    }
  }

  @keyframes nav-active-animation{
  0%{
    transform: scaleX(0.2);
  }
  100%{
    transform: scaleX(1);
  }
}
</style>