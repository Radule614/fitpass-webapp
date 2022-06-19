<script>
export default{
  props:{
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
  computed: {
    workHourDisplay(){
      let temp = this.facility.workingHours;
      return `${temp.startHour}:${temp.startMinute} - ${temp.endHour}:${temp.endMinute}`
    }
  },
}
</script>

<template>
  <div class="info">
    <div class="left">
      <table>
        <tr>
          <td>name: </td>
          <td>{{facility.name}}</td>
        </tr>
        <tr>
          <td>type: </td>
          <td>{{facility.facilityType}}</td>
        </tr>
        <tr>
          <td>average grade: </td>
          <td>{{facility.grade}}</td>
        </tr>
        <tr>
          <td>working hours: </td>
          <td>{{workHourDisplay}}</td>
        </tr>
      </table>
    </div>
    <div class="right">
      <div class="location"><i>location</i></div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.info{
  display: flex;
  .left{
    flex: 1;
    table{
      tr{
        td{
          line-height: 30px;
          &:first-child{
            padding-right: 35px;
            opacity: 0.85;
          }
        }
      }
    }
  }
  .right{
    .location{
      width: 500px;
      height: 295px;

      display: flex;
      justify-content: center;
      align-items: center;
      border:1px solid gray;
    }
  }
}
</style>