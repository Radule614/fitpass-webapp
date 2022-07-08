<script>
import UsersGrid from '@/components/users/UsersGrid.vue'
export default {
  components:{
    UsersGrid
  }
}
</script>

<template>
  <div>
    <div class="page-header">
      <h1>Users</h1>
    </div>
    <users-grid :selectable="true"></users-grid>
  </div>
</template>

<style scoped lang="scss">
  .page-header{
    h1{
      margin: 0px;
      padding:50px 0px 70px 0px;
      text-align: center;
      color: $dark-primary;
    }
  }
</style>