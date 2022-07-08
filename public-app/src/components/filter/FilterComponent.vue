<script>
import CheckboxBlock from './checkbox/CheckboxBlock.vue'
import RadioBlock from './radio/RadioBlock.vue';
import SearchBlock from './search/SearchBlock.vue';
import {debounce} from 'lodash';
export default{
  components:{
    CheckboxBlock,
    RadioBlock,
    SearchBlock
  },
  data(){
    return {
      filterTypes: [
        { name: 'userType',     value: 'user type' },
        { name: 'customerType', value: 'customer type' }
      ],
      sortTypes: [
        { name: 'username',     value: 'username'},
        { name: 'lastname',     value: 'lastname'},
        { name: 'firstname',    value: 'firstname'},
        { name: 'points',       value: 'point number'}
      ],
      searchTypes: [
        { name: 'username',     value: 'username'},
        { name: 'lastname',     value: 'lastname'},
        { name: 'firstname',    value: 'firstname'}
      ],
      parameterStatus: {}
    }
  },
  created(){
    this.parameterHandler = debounce(event => {
      console.log(this.parameterStatus);
    }, 600);
  },
  beforeUnmount() {
    this.parameterHandler.cancel();
  },
  methods:{
    parametersChanged(event){
      this.parameterStatus = {...this.parameterStatus, ...event};
      this.parameterHandler();
    }
  }
}
</script>

<template>
  <div class="filter-block">
    <checkbox-block :items="filterTypes"  caption="filter by" @selection="parametersChanged($event)"></checkbox-block>
    <radio-block    :items="sortTypes"    caption="sort by"   @selection="parametersChanged($event)"></radio-block>
    <search-block   :items="searchTypes"  caption="search by" @search="parametersChanged($event)"></search-block>
  </div>
</template>

<style scoped lang="scss">
.filter-block {
  display: flex;
  > * {
    margin-left:30px;
    &:first-child{
      margin-left: 0px;
    }
  }
}
</style>