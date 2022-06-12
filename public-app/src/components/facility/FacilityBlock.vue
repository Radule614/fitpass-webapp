<script>
export default {
  props: {
    facility: {
      available: Boolean,
      content: String,
      facilityType: String,
      name: String,
      grade: Number,
      workingHours: {
        startHour: Number,
        startMinute: Number,
        endHour: Number,
        endMinute: Number
      }
    }
  },
  computed:{
    workHourDisplay(){
      let temp = this.facility.workingHours;
      return `${temp.startHour}:${temp.startMinute} - ${temp.endHour}:${temp.endMinute}`
    }
  },
}
</script>

<template>
  <div class="facility-wrap">
    <div class="background"></div>
    <div class="facility">
      {{facility.name}}
    </div>
  </div>
</template>

<style scoped lang="scss">
  .facility-wrap{
    height: 400px;
    background-color: $light-primary;
    position: relative;
    .background{
      background-color: $active-primary;
      position: absolute;
      width:100%;
      height: 100%;
      top:0px;
      left:0px;
      &::before,
      &::after{
        content:"";
        width: 0px;
        transition: all 0.2s;
        position:absolute;
        width: 0px;
        height: 0px;
        background-color: $active-primary;
        transform: rotate(-45deg) translateX(0px) translateY(0px);
      }
      &::before{
        top:0px;
        left: 0px;
      }
      &::after{
        bottom:0px;
        right: 0px;
      }
    }
    .facility{
      background-color: $light-primary;
      height: 400px;
      transition: transform 0.2s;
      position: relative;
    }
    &:hover{
      .background{
        &::before,
        &::after{
          width: 16.97px;
          height: 16.97px;
        }
        &::before{
          transform: rotate(-45deg) translateX(8.5px) translateY(-3.5px);
        }
        &::after{
          transform: rotate(-45deg) translateX(8.8px) translateY(3.4px);
        }
      }
      .facility{
        transform:translateX(12px) translateY(-12px);
      }
    }
  }
</style>