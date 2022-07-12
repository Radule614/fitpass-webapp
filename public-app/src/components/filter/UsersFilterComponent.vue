<script>
import SearchBlock from './blocks/SearchBlock.vue';
import CustomBlock from './blocks/CustomBlock.vue';
import { debounce } from 'lodash';
export default{
  components:{
    SearchBlock,
    CustomBlock
  },
  data(){
    return {
      userFilters: [
        { key: 'customer',      value: 'customers' },
        { key: 'trainer',       value: 'trainers' },
        { key: 'admin',         value: 'admins' },
        { key: 'manager',       value: 'managers' }
      ],
      customerFilters: [
        { key: 'bronze',        value: 'bronze' },
        { key: 'silver',        value: 'silver' },
        { key: 'gold',          value: 'gold' }
      ],
      sortTypes:[
        { key: 'username',      value: 'username' },
        { key: 'firstname',     value: 'firstname'},
        { key: 'lastname',      value: 'lastname' },
        { key: 'points',        value: 'point number'},
      ],
      sortReverse: [ 
        { key: 'reverse',       value: 'reverse order' } 
      ],
      searchTypes: [
        { key: 'username',      value: 'username' },
        { key: 'firstname',     value: 'firstname' },
        { key: 'lastname',      value: 'lastname' }
      ],
      parameterStatus: {}
    }
  },
  created(){
    this.parameterHandler = debounce(() => { this.$emit('parametersChanged', this.parameterStatus) }, 500);
  },
  beforeUnmount() {
    this.parameterHandler.cancel();
    this.$store.dispatch('users/fetchUsers');
  },
  methods:{
    paramsHandler(event, objectKey){
      let payload = {};
      if(event.radio || event.checkbox) payload = { ...event.radio, ...event.checkbox };
      else payload = { ...event };
      this.parameterStatus[objectKey] = payload;
      this.parameterHandler();
    }
  }
}
</script>

<template>
<div class="filter-wrapper">
  <div class="filter-block">
    <custom-block caption="filter by user type"     @selection="paramsHandler($event, 'userFilter')"      :checkboxItems="userFilters"      checkboxKey="type"></custom-block>
    <custom-block caption="filter by customer type" @selection="paramsHandler($event, 'customerFilter')"  :checkboxItems="customerFilters"  checkboxKey="type"></custom-block>
    <custom-block caption="sort by"                 @selection="paramsHandler($event, 'sort')"            :radioItems="sortTypes"           radioKey="type" :checkboxItems="sortReverse"></custom-block>
    <search-block caption="search by"               @search="paramsHandler($event,    'search')"          :items="searchTypes"></search-block>
  </div>
  <div class="button-block">
    <custom-link to="/users">Close Filters</custom-link>
  </div>
</div>
</template>

<style scoped lang="scss">
.filter-wrapper{
  padding: 30px;
  padding-bottom: 10px;
  .filter-block {
    display: flex;
    > * {
      margin-left:30px;
      &:first-child{
        margin-left: 0px;
      }
    }
  }
  .button-block{
    margin-top: 12px;
    display: flex;
    justify-content: right;
    position: relative;
    & > * {
      margin-left: 15px;
    }
  }
}
</style>