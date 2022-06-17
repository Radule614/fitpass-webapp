<script>
import Settings from '../../settings.js';

export default {
  props: {
    selected:Boolean,
    facility: {
      available: Boolean,
      content: String,
      facilityType: String,
      name: String,
      grade: Number,
      logoUrl: String,
      workingHours: {
        startHour: Number,
        startMinute: Number,
        endHour: Number,
        endMinute: Number
      }
    }
  },
  data(){
    return {
      detailsActive: false,
      imagePath: ""
    }
  },
  created(){
    this.imagePath = `${Settings.serverUrl}/${this.facility.logoUrl}`;
  },
  computed:{
    workHourDisplay(){
      let temp = this.facility.workingHours;
      return `${temp.startHour}:${temp.startMinute} - ${temp.endHour}:${temp.endMinute}`
    }
  },
  methods: {
    selectedHandler(){
      if(this.facility.available){
        this.$emit('selectedEvent');
      }
    }
  }
}
</script>

<template>
  <div class="facility" :class="{'details-active': detailsActive, 'selected': selected, 'facility-available': facility.available}">
    <div class="header">
      <div class="left" @click="selectedHandler">
        <div class="text-group">
          <div class="text">{{facility.name}}</div>
          <div class="text">{{facility.facilityType}}</div>
          <div class="text available-text-wrap">
            <span v-if="facility.available">Available</span>
            <span v-else style="color:red;">Not available</span>
          </div>
        </div>
        <div class="image" ref="imageContainer" :style="{'background-image':`url(${this.imagePath})`}"></div>
      </div>
      <div class="right btn-wrap">
        <custom-button class="btn-details block no-shadow" @click="detailsActive=!detailsActive">
          <fa-icon class="button-text" :icon="['fas', 'angle-left']"></fa-icon>
        </custom-button>
      </div>
    </div>
      <div class="details">
        <div class="details-inner">
          <div>{{facility.name}}</div>
          <div>{{workHourDisplay}}</div>
          <div>{{workHourDisplay}}</div><div>{{facility.name}}</div>
          <div>{{workHourDisplay}}</div><div>{{facility.name}}</div>
          <div>{{workHourDisplay}}</div><div>{{facility.name}}</div>
          <div>{{workHourDisplay}}</div><div>{{facility.name}}</div>
          <div>{{workHourDisplay}}</div><div>{{facility.name}}</div>
        </div>
      </div>
  </div>
</template>

<style scoped lang="scss">
.facility{
  width: 100%;
  margin-top: 15px;
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
    transition: max-height 0.4s ease-out;
    //transition: height 0.4s cubic-bezier(0.6, -0.28, 0.735, 0.045);
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
          transition: border 0.02s ease-in-out 0.33s;
        }
      &:hover{
        .image::after{
          border-color: transparent transparent transparent $active-primary;
          transition: none;
        }
      }
      .text-group::before{
        content: "";
        position:absolute;
        width: 100%;
        height: 100%;
        top:0px;
        left:0px;
        background-color: $active-primary;
        transform: scaleX(0);
        transform-origin: right;
        transition: transform 0.35s ease-in-out;
      }
    }    
  }
  &.selected{
    .header {
      .left{
        color: $light-primary;
        transition: color 0.20s ease-in-out 0.15s;
        .text-group{
          .available-text-wrap{
            color: $light-primary;
            transition: color 0.20s ease-in-out 0.15s;
          }
        }
        .image::after{
          border-color: transparent transparent transparent $active-primary;
          transition: none;
        }
        .text-group::before{
          transform: scaleX(1);
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
      transition: max-height 0.3s ease-in;
    }
  }
}
</style>