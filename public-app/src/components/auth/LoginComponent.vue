<script>
import ModalComponent from '../ModalComponent.vue'
export default {
  props: {
    show: Boolean
  },
  emits:['close', 'confirm'],
  components: {
    ModalComponent
  },
  data() {
    return {
      loading: false,
      error: false,
      errorMessage: "",
      formData: {
        username: "",
        password: ""
      }
    }
  },
  methods: {
    resetInputData() {
      this.formData.username = "";
      this.formData.password = "";
    },
    resetValidation(){
      this.errorMessage = "";
      this.error = false
    },
    async loginSubmit(event) {

      this.loading = true
      this.resetValidation();
      try{
        await this.$store.dispatch('auth/login', this.formData);
      }catch(e){
        this.errorMessage = e;
        this.loading = false
        this.error = true
      }

      if(!this.error){
        this.resetValidation();
        this.resetInputData();
        this.loading = false
        this.$emit('close')
      }
    }
  }
}
</script>

<template>
  <form @submit.prevent="loginSubmit">
    <modal-component :show="show" :disabled="loading" buttonText="login" @close="$emit('close')">
      <template #header>
        Login
      </template>

      <template #body>
        <input type="text" class="input-primary" name="username" placeholder="username" v-model="formData.username">
        <input type="password" class="input-primary" name="password" placeholder="password" v-model="formData.password">
        <div class="info-block">
          <div class="loader" v-if="loading"></div>
          <div class="error" v-if="error">{{errorMessage}}</div>
        </div>
      </template>
    </modal-component>
  </form>
</template>

<style scoped lang="scss">
  input {
    &.input-primary {
      width: 235px;
    }
  }
  .info-block{
    .error{
      font-size: 14px;
      color:$active-primary;
    }
  }
</style>