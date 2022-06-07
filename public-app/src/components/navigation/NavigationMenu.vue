

<script>
  import NavigationItem from './NavigationItem.vue'

  export default{
    data() {
      return {
        routes: [
          {name: "home", text: "Home"},
          {name: "facility", text: "Facilities"},
          {name: "staff", text: "Staff"},
          {name: "about", text: "About"},
          {name: "contact", text: "Contact"}
        ]
      }
    },
    components: {
      NavigationItem
    }
  }
</script>

<template>
  <nav class="menu">
    <navigation-item v-for="route in routes" v-bind:key="route.id" :routeName="route.name" :text="route.text"></navigation-item>
  </nav>
</template>

<style scoped lang="scss">
  .menu{
    display: flex;
    flex-direction:row;
  }
</style>