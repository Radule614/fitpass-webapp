<script>
  export default{
    props: {
      options: [],
      selectedIndex: null
    },
    model: {
      prop: 'selectedIndex',
      event: 'selection'
    },
    methods: {
      selectionChanged(event){
        for(let child of this.$refs.radioBlock.children){
          child.classList.remove('active');
        }
        event.target.classList.add('active');
        this.$emit('selection', +event.target.getAttribute('data-val'));
      }
    }
  }
</script>

<template>
  <div class="radio-block" ref="radioBlock">
    <div class="radio" @click="selectionChanged" data-val="0">
      text
    </div>
    <div class="radio" @click="selectionChanged" data-val="1">
      text2
    </div>
    <div class="radio" @click="selectionChanged" data-val="2">
      text3
    </div>
  </div>
</template>

<style scoped lang="scss">
  .radio-block{
    box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.3);
    background-color: $light-primary;
    display: flex;
    flex-direction: row;
    position: relative;
    border-radius: 10px;
    .radio{
      width: 30px;
      height: 30px;
      font-size: 14px;
      flex: 1;
      background-color: $light-primary;
      cursor: pointer;
      &:first-child{
        border-radius: 10px 0px 0px 10px;
      }
      &:last-child{
        border-radius: 0px 10px 10px 0px;
      }
      &:hover{
        background-color: $active-primary;
        color: $light-primary;
      }
      &.active{
        background-color: $active-primary;
        color: $light-primary;
      }
    }
  }
  
</style>