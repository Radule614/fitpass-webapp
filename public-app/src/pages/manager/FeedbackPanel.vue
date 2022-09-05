<script>
import CommentList from '@/components/facility/CommentList.vue';
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
    <comment-list :manager="true"></comment-list>
  </div>
</template>

<style scoped lang="scss">

</style>