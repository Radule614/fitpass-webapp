<script>
import TrainingList from '@/components/training/TrainingList.vue';
export default {
  components: { TrainingList },
  beforeMount () {
    if(this.facility && this.facility.name){
      this.$store.dispatch('trainings/fetchFacilityTrainings', { facility_id: this.facility.name });
    }
  },
  computed:{
    facility(){
      return this.$store.getters['auth/managerFacility'];
    },
    trainings(){
      const temp = this.$store.getters['trainings/getFacilityTrainings'];
      return temp ? temp : [];
    }
  }
}
</script>

<template>
  <div>
    <training-list :trainings="trainings"></training-list>
  </div>
</template>

<style scoped lang="scss">

</style>