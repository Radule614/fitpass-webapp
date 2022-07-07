<script>
  import CustomButton from './CustomButton.vue';
  export default{
    props:{},
    components:{
      CustomButton
    }
  }
</script>

<template>
 <custom-button class="block flat">
    <router-link v-bind="{...$props, ...$attrs}">
      <slot></slot>
    </router-link>
  </custom-button>
</template>

<style scoped lang="scss">
  a{
    padding: 0 32px;
    position: relative;
  }
</style>