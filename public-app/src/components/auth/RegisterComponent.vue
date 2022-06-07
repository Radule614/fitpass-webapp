<script>
  import ModalComponent from '../ModalComponent.vue'
  import RadioBlock from '../utility/RadioBlock.vue';
  export default{
    props: {
      show: Boolean
    },
    data(){
      return {
        loading: false,
        error: false,
        errorMessage: "",
        formData: {
          username: "",
          firstname: "",
          lastname: "",
          password: "",
          passwordConfirm: "",
          gender: null
        }
      }
    },
    components: {
    ModalComponent,
    RadioBlock
},
    methods:{
      resetInputData() {
        this.formData.username = "";
        this.formData.firstname = "";
        this.formData.lastname = "";
        this.formData.password = "";
        this.formData.passwordConfirm = "";
        this.formData.gender = null;
      },
      resetValidation(){
        this.errorMessage = "";
        this.error = false
      },

      async registerSubmit(event){
        console.log(this.formData.gender)
        
        // this.loading = true
        // this.resetValidation();
        // let requestBody = {
        //   username: this.formData.username,
        //   firstname: this.formData.firstname,
        //   lastname: this.formData.lastname,
        //   password: this.formData.password
        // }
        // this.error = false
        // try{
        //   await this.$store.dispatch('auth/signup', requestBody);
        // }catch(e){
        //   this.errorMessage = e;
        //   this.loading = false
        //   this.error = true
        // }

        // if(!this.error){
        //   this.resetValidation();
        //   this.resetInputData();
        //   this.loading = false
        //   this.$emit('close')
        // }
      }
    }
  }
</script>

<template>
  <form @submit.prevent="registerSubmit">
    <modal-component :show="show" :disabled="loading" :width="500" buttonText="register" @close="$emit('close')">
      <template #header>
        Register a new account
      </template>

      <template #body>
        <div class="row">
          <div class="col-6">
            <input type="text" class="input-primary" name="username" placeholder="username" v-model="formData.username">     
            <input type="text" class="input-primary" name="firstname" placeholder="firstname" v-model="formData.firstname">  
            <input type="text" class="input-primary" name="lastname" placeholder="lastname" v-model="formData.lastname">  
          </div>
          <div class="col-6">
            <input type="password" class="input-primary" name="password" placeholder="password" v-model="formData.password">
            <input type="password" class="input-primary" name="password-confirm" placeholder="confirm password" v-model="formData.passwordConfirm">
            <radio-block v-model="formData.gender"></radio-block>
          </div>
          <div class="info-block">
            <div class="loader" v-if="loading"></div>
            <div class="error" v-if="error">{{errorMessage}}</div>
          </div>
        </div>
      </template>
    </modal-component>
  </form>
</template>

<style scoped lang="scss">
  .info-block{
    .error{
      font-size: 14px;
      color:$active-primary;
    }
  }
</style>