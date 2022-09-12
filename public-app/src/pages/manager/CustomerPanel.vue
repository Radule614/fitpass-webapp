<script>
import UsersGrid from '@/components/users/UsersGrid.vue';
export default {
  components: { UsersGrid },
  beforeMount () {
    this.$store.dispatch('users/usersClear');
    if(this.facility && this.facility.name){
      this.$store.dispatch('users/fetchFacilityVisitors', { facility_id: this.facility.name });
    }
  },
  computed:{
    facility(){
      return this.$store.getters['auth/managerFacility'];
    }
  }
}
</script>

<template>
  <div>
    <users-grid :noDispatch="true" :compact="true" :noPadding="true" class="grid"></users-grid>
  </div>
</template>

<style scoped lang="scss">
.grid{
  padding:0px;
}
</style>