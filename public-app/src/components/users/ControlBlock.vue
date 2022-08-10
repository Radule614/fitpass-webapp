<script>
import ConfirmModal from '../utility/ConfirmModal.vue';
export default{
  components: { ConfirmModal },
  props: {
    selectedUser: String
  },
  data(){
    return {
      confirmModalActive: false,
      loading: false
    }
  },
  computed:{
    loggedUser(){
      return this.$store.getters['auth/user'];
    }
  },
  methods:{
    async deleteUser(){
      this.loading = true;
      try{
        await this.$store.dispatch('users/deleteUser', { username: this.selectedUser });
        this.loading = false;
        this.confirmModalActive = false;
      }catch(error){
        console.error(error);
        this.loading = false;
        this.confirmModalActive = false;
      }
    }
  }
}
</script>

<template>
  <div class="control-block">
    <div class="left">
      <custom-button v-if="selectedUser&&loggedUser&&selectedUser!=loggedUser.username" class="inverse block" @click="confirmModalActive = true">Delete Account</custom-button>
    </div>
    <div class="right">
      <custom-link to="/users/filter">Open Filters</custom-link>
      <custom-link class="inverse" to="/users/add">Create Account</custom-link>
    </div>
    <confirm-modal :show="confirmModalActive" @close="confirmModalActive = false;" @confirm="deleteUser"></confirm-modal>
  </div>
</template>

<style scoped lang="scss">
.control-block{
  padding:12px 30px 10px 30px;
  position: relative;
  max-height: 100px;
  display: flex;
  .left{
    display: flex;
    flex:1;
    & > * {
      margin-right: 15px;
    }
  }
  .right{
    display: flex;
    justify-content: right;
    & > * {
      margin-left: 15px;
    }
  }
}
</style>