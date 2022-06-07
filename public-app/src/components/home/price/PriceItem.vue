<script>
  export default {
    props: {
      price : {
        title: String,
        color: String,
        amount: Number,
        text: String,
        bold: Boolean
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
  <div class="item nohighlight" v-bind:class="{ 'bold-item': price.bold }">
    <div class="header" v-bind:style="{ backgroundColor: price.color }">{{price.title}}</div>
    <div class="text">{{price.text}}</div>
    <div class="price">{{price.amount}} <span class="unit">RSD</span></div>
    <div class="timespan">/monthly</div>
    <button v-bind:class="{ 'inverse': price.bold }">Learn More</button>
  </div>
</template>

<style scoped lang="scss">
  .item{
    position: relative;
    width: 250px;
    min-height: 250px;
    max-width: 500px;
    background-color:$light-primary;
    box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.3);
    border-radius: 12px;
    margin-left: 50px;
    transition: box-shadow 0.1s;
    text-align: center;
    opacity: 0;
    &.appear{
      animation: magnify 0.4s ease-out forwards;
      &:nth-child(2){
        animation-delay: 0.1s;
      }
      &:nth-child(3){
      animation-delay: 0.2s;
      }
    }
    &:first-child {
        margin-left: 0px;
    }
    &:hover{
      box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.3);
    }
    .header{
      border-radius: 12px 12px 0px 0px;
      line-height: 45px;
      text-transform: uppercase;
      color: $dark-primary;
      font-size: 22px;
    }
    .text{
      font-size: 19px;
      padding: 16px 0px 5px 0px;
    }
    .price{
      font-size: 34px;
      font-weight: bold;
      .unit{
        font-size: 22px;
      }
    }
    .timespan{
      line-height: 40px;
      font-size: 23px;
      font-style: italic;
      text-transform: lowercase;
      opacity: 0.7;
    }
    button{
      margin: 60px auto 30px auto;
      font-size: 18px;
    }
  }
</style>