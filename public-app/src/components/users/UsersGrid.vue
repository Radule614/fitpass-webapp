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
      selectedUser: -1,
      filterVisible: false
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
    }
  }
}
</script>

<template>
  <div class="grid-wrapper" :class="{ 'users-page': currentRouteName == 'users' }">
    <div class="parameter-block">
      <router-view></router-view>
      <div v-if="currentRouteName == 'users'">
        <filter-component class="filter-block" @parametersChanged="parametersHandler($event)" :class="{'filter-visible': filterVisible}"></filter-component>
        <div class="control-block">
          <custom-button class="block" @click="filterVisible=!filterVisible">Toggle Filters</custom-button>
          <custom-link class="inverse" to="/users/add">Create Account</custom-link>
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
  background-color: #fff;
  position: relative;
  &:not(.users-page) .parameter-block{
    padding:30px;
    padding-bottom: 10px;
    box-shadow: 0px 2px 3px rgba(0, 0, 0, 0.2);
  }
  .parameter-block{
    background-color: #fff;
    z-index: 30;
    position:sticky;
    padding: 10px 0px;
    top:-2px;
    box-shadow: 0px 2px 3px rgba(0, 0, 0, 0.2);
    .filter-block{
      padding:30px;
      padding: 5px 30px 10px 30px;
      transition: max-height 0.25s ease-in-out, padding 0.25s ease-in-out;
      max-height: 400px;
      overflow: hidden;
      &:not(.filter-visible){
        max-height: 0px;
        padding-top: 0px;
        padding-bottom: 0px;
      }
    }
    .control-block{
      display: flex;
      justify-content: right;
      padding:0px 30px;
      position: relative;
      & > * {
        margin-left: 15px;
      }
    }
  }
  .grid{
    padding:30px;
    padding-top: 60px;
    display: grid;
    grid-template-columns: repeat(5, 1fr);
    grid-gap: 30px;
    background-color: #fff;
    position: relative;
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