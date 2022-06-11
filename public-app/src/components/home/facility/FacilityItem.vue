<script>
  export default {
    props: {
      facility: {
        name: String,
        ftype: String,
        status: String,
        address: String,
        grade: Number,
        workHours: String
      }
    },
    created () {
      window.addEventListener('scroll', this.handleScroll);
    },
    mounted(){
      this.checkAnimations();
    },
    unmounted () {
      window.removeEventListener('scroll', this.handleScroll);
    },
    methods: {
      handleScroll(event){
        this.checkAnimations();
      },
      checkAnimations(){
        const element = this.$el;

        let windowHeight = window.innerHeight;
        
        let distanceFromTop = element.getBoundingClientRect().top;
        if(distanceFromTop - windowHeight + element.offsetHeight <= 0){
          element.classList.add('appear');
        }
      }
    }
  }
</script>

<template>
  <div class="item">
    <div class="item-inner">
      <div class="header">{{facility.name}} <span class="type">/ {{facility.ftype}}</span></div>
      <div class="body">
        <table>
          <tr>
            <td>status: </td>
            <td>{{facility.status}}</td>
          </tr>
          <tr>
            <td>address: </td>
            <td>{{facility.address}}</td>
          </tr>
          <tr>
            <td>grade: </td>
            <td>{{facility.grade}}</td>
          </tr>
          <tr>
            <td>work hours: </td>
            <td>{{facility.workHours}}</td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
  .item{
    padding-right: 50px;
    position: relative;
    margin-bottom: 35px;
    opacity: 0;
    &.appear:nth-child(odd){
      animation: appearance-from-left 0.4s ease-out forwards;
    }

    &.appear:nth-child(even){
      animation: appearance-from-right 0.4s ease-out forwards;
      animation-delay: 0.1s;
    }
    .item-inner{
      position: relative;
      background-color:$light-primary;
      box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.3);
      border-radius: 12px;
      .header{
        border-radius: 12px 12px 0px 0px;
        line-height: 45px;
        text-transform: uppercase;
        color: $active-primary;
        font-size: 20px;
        background-color: $dark-primary;
        padding: 0px 15px;
        .type{
          font-size: 18px;
          text-transform: lowercase;
          color: $light-primary;
          font-style: italic;
          padding-left: 2px;
        }
      }
      .body{
        padding:15px;
        table tr td{
          &:first-child{
            padding-right: 40px;
          }
        }
      }
    }
  }
</style>