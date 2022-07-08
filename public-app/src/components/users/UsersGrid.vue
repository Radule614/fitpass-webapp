<script>
import UsersGridItem from './UsersGridItem.vue'
import FilterComponent from '../filter/FilterComponent.vue'
export default{
  components:{
    UsersGridItem,
    FilterComponent
},
  props:{
    selectable: Boolean
  },
  data(){
    return{
      users: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10],
      selectedUser: -1
    }
  }
}
</script>

<template>
  <div class="grid-wrapper">
    <div class="parameter-block">
      <filter-component></filter-component>
    </div>
    <div class="grid">
      <users-grid-item  v-for="(user, index) in users" 
                        :key="index" 
                        :user="user" 
                        :selectable="selectable" 
                        :selected="selectable && selectedUser==index" 
                        @selectedEvent="selectedUser=index">
      </users-grid-item>
    </div>
  </div>
</template>

<style scoped lang="scss">
.grid-wrapper{
  .parameter-block{
    padding:30px;
    box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.1);
  }
  .grid{
    padding:30px;
    margin-top: 30px;
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    grid-gap: 30px;
  }
}

@media screen and (max-width: 1500px) {
  .grid-wrapper .grid{
    grid-template-columns: repeat(3, 1fr);
  }
}
@media screen and (max-width: 1100px) {
  .grid-wrapper .grid{
    grid-template-columns: repeat(2, 1fr);
  }
}
</style>