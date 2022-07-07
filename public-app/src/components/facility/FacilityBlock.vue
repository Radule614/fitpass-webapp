<script>
import Settings from '../../settings.js';
import FacilityInfo from './FacilityInfo.vue';

export default {
  components:{
    FacilityInfo
  },
  props: {
    selected:Boolean,
    facility: {
      available: Boolean,
      content: String,
      facilityType: String,
      name: String,
      location: Object,
      grade: Number,
      logoUrl: String,
      workingHours: {
        startHour: Number,
        startMinute: Number,
        endHour: Number,
        endMinute: Number
      }
    },
    shallowShowcase: Boolean
  },
  data(){
    return {
      facilityAppeared: false,
      detailsActive: false
    }
  },
  emits:['selectedEvent', 'remove'],
  created(){
    window.addEventListener('scroll', this.handleScroll);
  },
  mounted(){
    this.checkAnimations();
  },
  unmounted () {
    window.removeEventListener('scroll', this.handleScroll);
  },
  computed:{
    imagePath(){
      return `${Settings.serverUrl}/${this.facility.logoUrl}`;
    },
    loggedUserType(){
      return this.$store.getters['auth/userType'];
    }
  },
  methods: {
    handleScroll(event){
      this.checkAnimations();
    },
    checkAnimations(){
      const element = this.$el;
      let windowHeight = window.innerHeight;
      let distanceFromTop = element.getBoundingClientRect().top;
      if(distanceFromTop - windowHeight <= 0){
        this.facilityAppeared = true;
      }
    },
    selectedHandler(event){
      if(this.facility.available && !event.target.closest('.btn-delete')){
        this.$emit('selectedEvent');
      }
    }
  }
}
</script>

<template>
  <div class="facility" :class="{'appear': facilityAppeared,'details-active': detailsActive && !shallowShowcase, 'selected': selected && !shallowShowcase && facility.available, 'facility-available': facility.available, 'shallow-showcase': shallowShowcase}">
    <div class="header">
      <div class="left" @click="selectedHandler($event)">
        <div class="text-group">
          <div class="text">{{facility.name}}</div>
          <div class="text">{{facility.facilityType}}</div>
          <div class="text available-text-wrap">
            <span v-if="facility.available">Available</span>
            <span v-else style="color:red;">Not available</span>
          </div>
        </div>
        <div class="image" ref="imageContainer" :style="{'background-image':`url(${this.imagePath})`}"></div>
        <button v-if="loggedUserType == 'ADMIN'" type="button" class="btn-delete" @click="$emit('remove', facility.name)">
          <span>
            <fa-icon :icon="['fas', 'xmark']"></fa-icon>
          </span>
        </button>
      </div>
      <div v-if="!shallowShowcase" class="right btn-wrap">
        <custom-button class="btn-details block no-shadow" @click="detailsActive=!detailsActive">
          <fa-icon class="button-text" :icon="['fas', 'angle-left']"></fa-icon>
        </custom-button>
      </div>
    </div>
    <div v-if="!shallowShowcase" class="details">
      <div class="details-inner">
        <facility-info :facility="facility"></facility-info>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.facility{
  width: 100%;
  margin-top: 15px;
  opacity: 0;
  &:first-child{
    margin-top: 0px;
  }
  
  .header{
    height: 100px;
    display: flex;
    flex-direction: row;
    position: relative;
    background-color: #fff;
    .left{
      box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.3);
      display: flex;
      flex-direction: row;
      position: relative;
      flex: 1;
      background-color: $light-primary;
      z-index: 1;
      font-size: 18px;
      .text-group{
        display: flex;
        position: relative;
        .text{
          width: 150px;
          position: relative;
          text-align: center;
          display: flex;
          align-items: center;
          padding-left: 30px;
          .available-text-wrap{
            color: red;
          }
        }
      }
      .image{
        height: 100px;
        background-size: cover;
        background-position-y: center;
        background-repeat: no-repeat;
        flex: 1;
        position: relative;
        &::after{
          content: "";
          width: 0;
          height: 0;
          border-style: solid;
          border-width: 100px 0 0 20px;
          border-color: transparent transparent transparent $light-primary;
          position: absolute;
          left:0px;
          bottom:0px;
        }
      }
      .btn-delete{
        position: absolute;
        right:0px;
        top:0px;
        border: none;
        outline: none;
        background: transparent;
        display: block;
        width: 0;
        height: 0;
        border-style: solid;
        border-width: 50px 60px 50px 0;
        border-color: transparent $light-primary transparent transparent;
        padding:0px;
        opacity: 0.4;
        color: $dark-primary;
        transition: opacity 0.3s ease-in-out, color 0.3s ease-in-out;
        span{
          position: absolute;
          right: -50px;
          top: -17px;
          font-size: 24px;
        }
        &::before{
          content: "";
          display: block;
          width: 0;
          height: 0;
          border-style: solid;
          right: 0px;
          top: 0px;
          position: absolute;
          transition:border 0.3s ease-in-out, transform 0.3s ease-in-out, border-radius 0.6s ease-in-out;
          border-width: 0px;
          border-radius: 50%;
          transform: translateX(60px) translateY(0px);
          border-color: transparent $active-primary transparent transparent;
        }
        &:hover{
          opacity: 1;
          color: $light-primary;
          &::before{
            border-width: 50px 60px 50px 0;
            border-radius: 0px;
            transform: translateX(60px) translateY(-50px);
          }
        }
      }
    }
    .right{
      position: relative;
      box-shadow: 0px 0px 4px rgba(0, 0, 0, 0.3);
      margin-left: 0px;
      background-color: #fff;
      z-index: 2;
      &.btn-wrap{
        width: 100px;
        .btn-details{
          outline:none;
          border:none;
          background-color: #fff;
          text-align: center;
          margin:auto;
          display: block;
          width: 100%;
          height: 100%;
          position: absolute;
          top:0px;
          right:0px;
          bottom:0px;
          left:0px;
          font-size: 24px;
          .button-text{
            transition: transform 0.3s;
            transform: rotate(0deg);
          }
        } 
      }
    }
  }

  .details{
    max-height: 0;
    box-shadow: 0px 0px 2px rgba(0, 0, 0, 0.3);
    position: relative;
    background-color: $light-primary;
    transition: max-height 0.4s ease-in-out;
    overflow: hidden;
    transform: translateY(-3px);
    .details-inner{
      padding:30px;
    }
  }

  

  &.facility-available{
    .header .left {
      cursor: pointer;
      background-color: #fff;
      position: relative;
      .text-group{
        .available-text-wrap{
          color: green;
        }
      }
      .image::after{
          border-color: transparent transparent transparent #fff;
          transition: border 0.25s ease-in-out;
        }
      &:hover{
        .image::after{
          border-color: transparent transparent transparent $active-primary;
        }
      }
      &::before{
        content: "";
        position:absolute;
        width: 100%;
        height: 100%;
        top:0px;
        left:0px;
        background-color: $active-primary;
        transform: scale(0);
        transition: transform 0.25s ease-in-out, border-radius 0.25s ease-in-out;
        border-radius: 50%;
      }
    }    
  }

  &.appear{
    animation: appearance-from-bottom 0.4s ease-in-out forwards;
  }

  &.shallow-showcase{
    .header .left{
      cursor: default;
      .btn-delete{
        display: none;
      }
      &:hover {
        .image::after{
          border-color: transparent transparent transparent #fff;
        }
        .btn-delete{
          display: none;
        }
      }
    }
    &.appear{
      animation: appearance-from-right 0.4s ease-in-out forwards;
    }
  }
  &.selected{
    .header {
      .left{
        color: $light-primary;
        transition: color 0.15s ease-in-out 0.1s;
        .text-group{
          .available-text-wrap{
            color: $light-primary;
            transition: color 0.15s ease-in-out 0.1s;
          }
        }
        .image::after{
          border-color: transparent transparent transparent $active-primary;
        }
        &::before{
          transform: scale(1);
          border-radius: 0;
          transition: transform 0.25s ease-in-out, border-radius 0.65s ease-in-out;
        }
      }
    }
  }
  &.details-active{
    .header .right.btn-wrap .btn-details {
      .button-text{
        transform: rotate(-90deg);
      }
    }
    .details{
      max-height: 350px;
      transition: max-height 0.3s ease-in-out;
    }
  }
}
</style>