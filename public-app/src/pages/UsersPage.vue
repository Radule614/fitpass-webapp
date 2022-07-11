<script>
import UsersGrid from '@/components/users/UsersGrid.vue'
export default {
  components:{
    UsersGrid
  }
}
</script>

<template>
  <div class="users">
    <users-grid :selectable="true"></users-grid>
  </div>
</template>

<style scoped lang="scss">
  .users{
    padding-top: 10px;
  }
</style>