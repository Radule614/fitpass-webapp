<script>
  import PriceSection from '@/components/home/price/PriceSection.vue'
  import FacilitySection from '@/components/home/facility/FacilitySection.vue'
  export default {
    components:{
      PriceSection,
      FacilitySection
    },
    data () {
      return{
        scrollAnimated: []
      }
    },
    created () {
      window.addEventListener('scroll', this.handleScroll);
    },
    unmounted () {
      window.removeEventListener('scroll', this.handleScroll);
    },
    mounted(){
      this.checkAnimations();
    },
    methods: {
      handleScroll(event){
        this.checkAnimations();
      },
      checkAnimations(){
        const elements = this.scrollAnimated;

        let windowHeight = window.innerHeight;
        
        for(let element of elements){
          let distanceFromTop = element.getBoundingClientRect().top;
          if(distanceFromTop - windowHeight + element.offsetHeight <= 0){
            element.classList.add('appear');
          }
        }
      },
      btnJoinNowClick(){
        window.scrollTo(0, this.$refs.sectionGetStarted.offsetTop);
      },
      btnMoreClick(){
        this.$router.push('/facility');
        window.scrollTo(0, 0);
      }
    }
  }
</script>

<template>
  <div class="page-header">
    <h1>HOME</h1>
    <div class="container">
      <div class="row">
        <div class="info col-6">
          <h2>
            <div class="animated-1">Feel Great.</div> 
            <div class="animated-2">Body and Mind.</div>
          </h2>
          <div class="text animated-3">Choose from hundreds of workouts, healthy recipes, relaxing meditations, and expert articles, for a whole body and mind approach to feeling great.</div>
          <button class="btn-join-now animated-4" @click="btnJoinNowClick">Join Now</button>
        </div>
        <div class="col-6">
          <img src="../assets/home-header-image.png" alt="noimage">
        </div>
      </div>
    </div>
  </div>
  <section class="secondary" ref="sectionGetStarted">
    <div class="container">
      <h2 :ref="(el) => scrollAnimated.push(el)" class="scroll-animated">GET STARTED.</h2>
      <h3 :ref="(el) => scrollAnimated.push(el)" class="scroll-animated">CHOOSE THE PLAN THAT SUITS YOU.</h3>
    </div>
  </section>
  <section>
    <div class="container">
      <price-section></price-section>
    </div>
  </section>
  <section class="primary">
    <div class="container">
      <h2 :ref="(el) => scrollAnimated.push(el)" class="scroll-animated">VAST SELECTION.</h2>
      <h3 :ref="(el) => scrollAnimated.push(el)" class="scroll-animated">FIND THE PLACE THAT WORKS FOR YOU.</h3>
    </div>
  </section>
  <section>
    <div class="container">
      <facility-section></facility-section>
    </div>
    <div class="fade-wrapper">
      <button class="btn-more" @click="btnMoreClick">
        <fa-icon :icon="['fas', 'arrow-right']"></fa-icon>
        <span>see more</span>
      </button>
    </div>
  </section>
</template>

<style scoped lang="scss">
  h1{
    line-height: 250px;
    font-size: 80px;
    margin:0px;
    text-align: center;
  }
  .page-header{
    background-image: url("../assets/home-header-background.jpg");
    height: 1000px;
    background-size: cover;
    background-position-y:top;
    background-repeat: no-repeat;
    position: relative;
    .animated-1,
    .animated-2,
    .animated-3,
    .animated-4{
      opacity: 0;
			animation: appearance-from-right 0.4s ease-out forwards;
			will-change: transform;
    }
    .animated-1 {
			
			animation-delay: 0.5s;
		}
		.animated-2 {
			animation-delay: 0.6s;
		}
		.animated-3 {
			animation-delay: 0.7s;
		}
		.animated-4 {
			animation-delay: 0.8s;
		}
    .container{
      padding:100px;
      margin:auto;
      background-color: $light-primary;
      position: absolute;
      bottom:0px;
      left:0px;
      right:0px;
      box-shadow: 0px 0px 30px rgba(0, 0, 0, 0.3);
      animation: home-page-header-appearance 0.5s ease-out forwards;
      div{
        position: relative;
      }
      .info{
        padding: 40px 40px 40px 0px;
        h2{
          font-size: 55px;
        }
        .text{
          font-size: 20px;
          max-width: 350px;
          margin-top: 20px;
        }
        button{
          line-height: 50px;
          padding: 0 35px;
          font-size: 19px;
          margin-top: 60px;
          border-radius: 25px;
        }
      }
      img{
        width: 100%;
        position:absolute;
        margin:auto;
        top:0px;
        bottom:0px;
        left:0px;
        right:0px;
      }
    }
  }
  section{
    position: relative;
    background-color: $light-primary;
    min-height: 100px;
    padding:60px 0px;
    h2{
      font-size: 40px;
      padding-bottom: 10px;
      margin: 0px;
      opacity: 0;
      &.appear{
        animation: appearance-from-left 0.4s ease-out forwards;
      }
    }
    h3{
      font-size: 32px;
      margin: 0px;
      opacity: 0;
      &.appear{
        animation: appearance-from-right 0.4s ease-out forwards;
      }
    }
    &.primary{
      background-color: $active-primary;
      color: $dark-primary;
      h3{
        color: $light-primary;
      }
    }
    &.secondary{
      background-color: $dark-primary;
      color: $light-primary;
      h3{
        color: $active-primary;
      }
    }
    .fade-wrapper{
      height: 300px;
      width: 100%;
      position: absolute;
      display: block;
      background: linear-gradient(0deg, rgba(255,255,255,1) 25%, rgba(245,245,245,0) 100%);
      bottom: 0px;
      left: 0px;
      button.btn-more{
        position: absolute;
        bottom:20px;
        left: 0px;
        right: 0px;
        width: 150px;
        margin:auto;
        opacity: 0.95;
        background-color: $light-primary;
        color: $dark-primary;
        &:hover{
          background-color: $dark-primary;
          color: $active-primary;
          opacity: 1;
        }
        span{
          padding-left: 16px;
        }
      }
    }
  }

  @keyframes home-page-header-appearance{
  0%{
    transform: translateY(400px);
  }
  100%{
    transform: translateY(0px);
  }
}

</style>