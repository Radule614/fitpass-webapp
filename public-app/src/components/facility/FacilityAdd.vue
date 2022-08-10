<script>
import CustomLink from '../utility/CustomLink.vue';
import CustomFileInput from '../utility/CustomFileInput.vue';
import ChooseLocationModal from '../utility/ChooseLocationModal.vue';
export default{
  components:{
    CustomLink,
    CustomFileInput,
    ChooseLocationModal
  },
  data(){
    return {
      messages: [],
      locationModalActive: false,
      location: { lat: 0, lng: 0 },
      address: null,
      loading: false
    }
  },
  computed:{
    locationFeedback(){
      if(!this.address) return "No location selected"
      else return `${this.address.country||""}, ${this.address.city||this.address.town||""}, ${this.address.road||""} ${this.address.number||""}`
    }
  },
  methods:{
    scrollToTop(){
      window.scrollTo(0, 0);
    },
    newLocationSelected(event){
      this.location.lat = event.lat;
      this.location.lng = event.lng;
      this.address = event;
      this.locationModalActive = false;
    },
    async formSubmit(event){
      this.loading = true;
      event.preventDefault();
      const data = new FormData(this.$refs.submitForm);
      data.append('location', JSON.stringify(this.address));

      try{
        await this.$store.dispatch('facility/addFacility', data);
        await this.$store.dispatch('facility/searchFacilities', "");
        this.loading = false;
        this.scrollToTop();
        this.$router.replace('facility');
      }catch(error){
        this.messages = error.message.split(",");
        this.loading = false;
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
            <div class="info">{{locationFeedback}}</div>
          </div>
        </td>
      </tr>
    </table>
    <div class="error-block">
      <div v-for="(message, index) in messages" :key="index">{{message}}</div>
    </div>
    <div class="button-group">
      <custom-link to="/facility" @click="scrollToTop">Cancel</custom-link>
      <custom-button type="submit" class="inverse block" @click="formSubmit($event)" :disabled="loading">Create</custom-button>
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