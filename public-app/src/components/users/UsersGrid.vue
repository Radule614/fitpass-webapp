<script>
import UsersGridItem from './UsersGridItem.vue';
import FilterComponent from '../filter/UsersFilterComponent.vue';
import CustomLink from '../utility/CustomLink.vue';
export default{
  components:{
    UsersGridItem,
    FilterComponent,
    CustomLink
  },
  props:{
    selectable: Boolean
  },
  data(){
    return{
      selectedUser: -1
    }
  },
  computed:{
    users(){
      return this.$store.getters['users/users'];
    },
    currentRouteName(){
      return this.$router.currentRoute.value.name;
    }
  },
  methods:{
    async parametersHandler(event){
      try{
        await this.$store.dispatch('users/fetchUsers', event);
      }catch(error){
        console.error(error);
      }
    },
    scrollToTop(){
      window.scrollTo(0, 0);
    }
  }
}
</script>

<template>
  <div class="grid-wrapper">
    <div class="parameter-block">
      <router-view></router-view>
      <div v-if="currentRouteName == 'users'">
        <filter-component @parametersChanged="parametersHandler($event)"></filter-component>
        <div class="control-block">
          <custom-link v-if="currentRouteName == 'users'" class="inverse" to="/users/add" @click="scrollToTop">Create Account</custom-link>
        </div>
      </div>
    </div>
    <div class="grid">
      <users-grid-item  v-for="(user, index) in users" 
                        :key="index" 
                        :user="user" 
                        :selectable="selectable" 
                        :selected="selectable && selectedUser==index" 
                        @selectedEvent="selectedUser=index">
      </users-grid-item>
      <div v-if="!users || users.length == 0" class="message">No users found</div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.grid-wrapper{
  .parameter-block{
    padding:30px;
    box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.1);
    .control-block{
      padding-top: 20px;
      display: flex;
      justify-content: right;
    }
  }
  .grid{
    padding:30px;
    margin-top: 30px;
    display: grid;
    grid-template-columns: repeat(5, 1fr);
    grid-gap: 30px;
  }
  .message{
    padding-left: 30px;
    font-style: italic;
    color: $active-primary;
    font-size:18px;
  }
}

@media screen and (max-width: 1700px) {
  .grid-wrapper .grid{
    grid-template-columns: repeat(4, 1fr);
  }
}
@media screen and (max-width: 1350px) {
  .grid-wrapper .grid{
    grid-template-columns: repeat(3, 1fr);
  }
}
@media screen and (max-width: 900px) {
  .grid-wrapper .grid{
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>