<script>
import TrainingList from '@/components/training/TrainingList.vue';
export default {
  components: { TrainingList },
  beforeMount () {
    this.$store.dispatch('trainings/trainingsClear');
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
    <training-list v-if="trainings.length > 0" :trainings="trainings"></training-list>
    <div v-else>
      No trainings
    </div>
  </div>
</template>

<style scoped lang="scss">

</style>