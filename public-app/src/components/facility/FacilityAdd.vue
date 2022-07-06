<script>
import CustomLink from '../utility/CustomLink.vue';
import CustomButton from '../utility/CustomButton.vue';
import CustomFileInput from '../utility/CustomFileInput.vue';
import ChooseLocationModal from '../utility/ChooseLocationModal.vue';
export default{
  components:{
    CustomLink,
    CustomFileInput,
    CustomButton,
    ChooseLocationModal
},
  data(){
    return {
      messages: [],
      locationModalActive: false,
      location: { lat: 44, lng: 20 }
    }
  },
  computed:{
    loggedUserType(){
      return this.$store.getters['auth/userType'];
    },
    currentRouteName(){
      return this.$router.currentRoute.value.name;
    }
  },
  methods:{
    scrollToTop(){
      window.scrollTo(0, 0);
    },
    newLocationSelected(event){
      this.location.lat = event.lat;
      this.location.lng = event.lng;
      console.log(this.location);
      this.locationModalActive = false;
    },
    async formSubmit(event){
      event.preventDefault();
      const data = new FormData(this.$refs.submitForm);
      data.append('location', JSON.stringify(this.location));
      console.log(data);
      try{
        await this.$store.dispatch('facility/addFacility', data);
        this.$router.replace('facility');
      }catch(error){
        this.messages = error.message.split(",");
      }
    }
  }
}
</script>

<template>
  <form class="add-form" ref="submitForm">
    <table>
      <tr>
        <td><label for="name">name:</label></td>
        <td><input type="text" id="name" name="name"></td>
        <td><label for="working-hours">working hours:</label></td>
        <td>
          <div class="time-group" id="working-hours">
            <input type="time" name="startTime">
            <span>-</span>
            <input type="time" name="endTime">
          </div>
        </td>
      </tr>
      <tr>
        <td><label for="type">type:</label></td>
        <td>
          <select class="select-primary" name="type" id="type">
            <option value="GYM">GYM</option>
            <option value="POOL">POOL</option>
            <option value="SPORTS_CENTER">SPORTS_CENTER</option>
            <option value="DANCE_STUDIO">DANCE_STUDIO</option>
            <option value="OTHER">OTHER</option>
          </select>
        </td>
        <td><label for="available">available:</label></td>
        <td><input type="checkbox" id="available" name="available"></td>
      </tr>
      <tr>
        <td><label>image:</label></td>
        <td colspan="3"><custom-file-input name="image" class="block"></custom-file-input></td>
      </tr>
      <tr>
        <td><label>location:</label></td>
        <td colspan="3">
          <div class="location-block">
            <custom-button class="block" @click="locationModalActive = true">Location</custom-button>
            <div class="info">No location selected</div>
          </div>
        </td>
      </tr>
      <tr>
        <td style="vertical-align:top;"><label for="content">content summary:</label></td>
        <td colspan="3"><textarea class="textarea-primary" name="content" id="" cols="30" rows="10"></textarea></td>
      </tr>
    </table>
    <div class="error-block">
      <div v-for="(message, index) in messages" :key="index">{{message}}</div>
    </div>
    <div class="button-group">
      <custom-link v-if="currentRouteName == 'facilityAdd'" to="/facility" @click="scrollToTop">Cancel</custom-link>
      <custom-button type="submit" v-if="currentRouteName == 'facilityAdd'" class="inverse block" to="/facility" @click="formSubmit($event)">Add</custom-button>
    </div>
  </form>

  <choose-location-modal :show="locationModalActive" :locationProp="location" @close="locationModalActive=false" @confirm="newLocationSelected($event)"></choose-location-modal>
</template>

<style scoped lang="scss">
.add-form{
  .error-block{
    font-style: italic;
    color: red;
  }
  .button-group{
    display: flex;
    justify-content: right;
    & > * {
      margin-left: 15px;
    }
  }

  td{
    padding-bottom: 12px;
    &:nth-child(3){
      padding-left: 50px;
    }
    label{
      padding-right: 20px;
    }
    input, 
    select{
      margin-bottom: 0px;
      border-radius: 0px;
    }
    .time-group span{
      margin: 0px 10px;
    }
    textarea{
      width:100%;
      margin-bottom: 0px;
      border-radius: 0px;
    }
    .location-block{
      display: flex;
      flex-direction: row;
      & > button{
        padding-right: 10px;
        padding-left: 10px;
      }
      .info{
        line-height: 36px;
        padding-left: 14px;
        font-style: italic;
      }
    }
  }
}
</style>