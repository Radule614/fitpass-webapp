<script>
import UsersGridItem from './UsersGridItem.vue';
import FilterComponent from '../filter/UsersFilterComponent.vue';
import CustomLink from '../utility/CustomLink.vue';
export default{
  components: { UsersGridItem, FilterComponent, CustomLink },
  props: {
    selectable: Boolean,
    compact: Boolean,
    userType: String,
    noDispatch: Boolean,
    noPadding: Boolean
  },
  emits: ['userSelected'],
  data() {
    return {
      selectedUser: null,
      filterVisible: false
    }
  },
  created() {
    if(!this.noDispatch){
      if(this.userType){
        this.$store.dispatch('users/fetchUsers', { userFilter: { type: this.userType } });
      }else {
        this.$store.dispatch('users/fetchUsers');
      }
    }
  },
  computed: {
    users() {
      let users = this.$store.getters['users/users'];
      if (this.userType && this.userType === 'MANAGER' && users) users.sort((a, b) => b.facility && !a.facility ? -1 : 0);
      return users;
    },
    currentRouteName() {
      return this.$router.currentRoute.value.name;
    }
  },
  methods:{
    async parametersHandler(event) {
      try {
        await this.$store.dispatch('users/fetchUsers', event);
      } catch(error){
        console.error(error);
      }
    },
    toggleFilters(event) {
      if (!this.filterVisible) this.$router.push('/users/filter');
      else this.$router.push('/users');
      this.filterVisible = !this.filterVisible;
    },
    selectedHandler(user){
      this.selectedUser = user.username;
      this.$emit('userSelected', user);
    }
  }
}
</script>

<template>
  <div class="grid-wrapper" :class="{'compact': compact, 'no-padding': noPadding}">
    <div v-if="!compact" class="parameter-block">
      <router-view v-slot="{ Component }">
        <transition name="slide-bottom">
          <component :is="Component" class="child" :selectedUser="selectedUser" @parametersChanged="parametersHandler($event)"/>
        </transition>
      </router-view>
    </div>
    
    <div class="grid">
      <users-grid-item  v-for="(user, index) in users" 
                        :key="index" 
                        :user="user" 
                        :compact="compact"
                        :selectable="selectable"
                        :userType="userType"
                        :selected="selectable && selectedUser==user.username" 
                        @selectedEvent="selectedHandler(user)">
      </users-grid-item>
      <div v-if="!users || users.length == 0" class="message">No users found</div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.grid-wrapper{
  background-color: #fff;
  position: relative;
  .parameter-block{
    background-color: #fff;
    z-index: 30;
    position:sticky;
    top:-2px;
    box-shadow: 0px 2px 3px rgba(0, 0, 0, 0.2);
    .child{
      transition: max-height 0.4s ease-in-out, padding 0.4s ease-in-out, opacity 0.4s ease-in-out;
      overflow: hidden;
      max-height: 420px;
    }
    .slide-bottom-enter-from {
      max-height: 0px;
      padding-top:0px;
      padding-bottom:0px;
      opacity:0;
    }
    .slide-bottom-leave-active{
      &.control-block{
        transition: max-height 0.16s ease-in, padding 0.16s ease-in, opacity 0.16s ease-in;
      }
    }
    .slide-bottom-leave-to {
      max-height: 0px;
      padding-top:0px;
      padding-bottom:0px;
      opacity: 0;
    }
  }
  &.compact .grid{
    padding-top:30px;
  }
  &.no-padding .grid{
    padding:0px;
  }
  .grid{
    padding:30px;
    padding-top: 60px;
    padding-bottom: 100px;
    display: grid;
    grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
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
</style>