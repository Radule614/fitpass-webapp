<script>
import CustomLink from '../utility/CustomLink.vue';
import CustomRadio from '../filter/items/CustomRadio.vue';
export default {
  components: { CustomLink, CustomRadio },
  data(){
    return {
      messages: [],
      loading: false,
      genders: [ { value: 'male' }, { value: 'female'} ],
      genderSelectedIndex: 0
    }
  },
  created(){
    this.$store.dispatch('users/fetchUsers');
  },
  methods:{
    async formSubmit(event) {
      this.loading = true;
      event.preventDefault();
      const data = new FormData(this.$refs.submitForm);
      data.append('gender', this.genders[this.genderSelectedIndex].value);
      try{
        await this.$store.dispatch('users/createUser', data);
        this.loading = false;
        this.$router.replace('users');
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
        <td><label for="username">username:</label></td>
        <td><input type="text" id="username" name="username"></td>
        <td><label for="firstname">firstname:</label></td>
        <td><input type="text" id="firstname" name="firstname"></td>
      </tr>
      <tr>
        <td><label for="password">password:</label></td>
        <td><input type="password" id="password" name="password"></td>
        <td><label for="lastname">lastname:</label></td>
        <td><input type="text" id="lastname" name="lastname"></td>
      </tr>
      <tr>
        <td><label for="password-confirm">confirm password:</label></td>
        <td><input type="password" id="password-confirm" name="passwordConfirm"></td>
        <td><label for="birthday">birthday:</label></td>
        <td><input type="date" id="birthday" name="dateOfBirth"></td>
      </tr>
      <tr>
        <td><label>gender:</label></td>
        <td><custom-radio class="thick" :item="genders[0]" @selection="genderSelectedIndex=0" :selected="genderSelectedIndex==0"></custom-radio></td>
        <td><label for="type">user type:</label></td>
        <td>
          <select class="select-primary" name="type" id="type">
            <option value="MANAGER">MANAGER</option>
            <option value="TRAINER">TRAINER</option>
          </select>
        </td>
      </tr>
      <tr>
        <td></td>
        <td><custom-radio class="thick" :item="genders[1]" @selection="genderSelectedIndex=1" :selected="genderSelectedIndex==1"></custom-radio></td>
      </tr>
    </table>
    <div class="error-block">
      <div v-for="(message, index) in messages" :key="index">{{message}}</div>
    </div>
    <div class="button-group">
      <custom-link to="/users">Cancel</custom-link>
      <custom-button type="submit" class="inverse block" @click="formSubmit($event)" :disabled="loading">Create</custom-button>
    </div>
  </form>
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
      background-color: #fff;
    }
  }
}
</style>