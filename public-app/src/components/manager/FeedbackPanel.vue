<script>
import CommentList from '../facility/CommentList.vue';
export default {
  components:{ CommentList },
  beforeMount () {
    if(this.facility && this.facility.name){
      this.$store.dispatch('comments/getAllFacilityComments', this.facility.name);
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
    <comment-list></comment-list>
  </div>
</template>

<style scoped lang="scss">

</style>