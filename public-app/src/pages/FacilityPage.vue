<script>
import CommentList from '@/components/facility/CommentList.vue';
import FacilityDetails from '@/components/facility/FacilityDetails.vue';
import AddComment from '@/components/facility/AddComment.vue';
import RateFacility from '../components/facility/RateFacility.vue';

export default {
  components: { CommentList, FacilityDetails, AddComment, RateFacility },
  computed: {
    facility(){
      let facilities = this.$store.getters['facility/facilities'];
      if(facilities){
        facilities = facilities.filter(f => f.name == this.facility_id);
        if(facilities.length > 0) {
          const f = facilities[0];
          this.$store.dispatch('comments/getAllFacilityComments', f.name);
          return f;
        }
      }
      return null;
    },
    facility_id(){
      return this.$route.params['id'];
    },
    loggedUserType(){
      return this.$store.getters['auth/userType'];
    },
		loggedUser() {
			return this.$store.getters['auth/user'];
		}
  }
}
</script>

<template>
  <div class="container facility">
    <div v-if="facility">
      <facility-details :facility="facility"></facility-details>
			<rate-facility v-if="loggedUser"/>
      <div class="comments">
        <comment-list :forPublic="true" :admin="loggedUserType == 'ADMIN'"></comment-list>
      </div>
			<add-comment :facility_id="facility_id" v-if="loggedUser"/>
    </div>
    <div v-else>
      error 404: facility doesn't exist
    </div>
  </div>
</template>

<style scoped lang="scss">
.facility{
  padding-bottom: 200px;
  padding-top: 40px;
  
  .comments{
    margin-top: 30px;
  }
}
</style>