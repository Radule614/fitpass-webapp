<template>
  <modal-component :show="show" @confirm="register()" @close="$emit('close')" buttonText="register" :width="600" :disabled="loading">
    <template #header>
      Register
    </template>
    <template #body>
      <form>
        <div class="grid-container">
          <input type="text" placeholder="Username" v-model="formData.username"/>
          <input type="text" placeholder="First name" v-model="formData.firstName"/>
          <input type="password" placeholder="Password" v-model="formData.password">
          <input type="text" placeholder="Last name" v-model="formData.lastName">
          <input type="password" placeholder="Confirm password" v-model="formData.confirmPassword"/>
          <input placeholder="Date Of Birth" type="text" onfocus="(this.type='date')" onblur="(this.type='text')" 
                 min="1900-01-01" :max="this.today" v-model="formData.dateOfBirth">
          <div class="radio-buttons">
            <label for="">Gender:</label>
            <input type="radio" id="male" value="male" name="gender" v-model="formData.gender"/>
            <label for="male">Male</label>
            <input type="radio" id="female" value="female" name="gender" v-model="formData.gender"/>
            <label for="female">Female</label>
          </div>
          <div class="info">
            <div class="error" v-show="this.error">
              {{this.errorMsg}}
            </div>
            <div class="loader" v-show="this.loading"></div>
          </div>
        </div>
      </form>
    </template>
  </modal-component>
</template>

<script>
import ModalComponent from "../ModalComponent.vue"
import { mapActions } from 'vuex';
import { tupleExpression } from "@babel/types";

export default {
    name: "RegisterComponent",
    props: {
      show: Boolean,
    },
    data() {
      return {
        loading: false,
        error: false,
        errorMsg: "",
        formData: {
          username: "",
          password: "",
          confirmPassword: "",
          firstName: "",
          lastName: "",
          dateOfBirth: "",
          gender: ""
        }
      }
    },
    methods: {
      ...mapActions("auth", ["signup"]),
      async register() {
        // Reset validation data
        this.resetValidation();
        // Da li su uneti svi podaci
        if(!this.isAllDataFilled()) {
          this.error = true;
          this.errorMsg = "Please insert all informations.";
          return;
        } 
        // Da li je sifra validna
        if(!this.isPasswordValid()) {
          this.error = true;
          this.errorMsg = "Password must containt at least 8 characters and 1 of them must be a digit.";
          return;
        }
        // Da li je sifra dobro uneta
        if(this.formData.password !== this.formData.confirmPassword) {
          this.error = true;
          this.errorMsg = "Your password and confirmation password do not match."
          return;
        }

        this.loading = true;
        let requestBody = {
          username: this.formData.username,
          password: this.formData.password,
          firstName: this.formData.firstName,
          lastName: this.formData.lastName,
          dateOfBirth: this.formData.dateOfBirth,
          gender: this.formData.gender
        }

        await this.signup(requestBody)
          .catch(err => {
            this.error = true;
            this.loading = false;
            this.errorMsg = err.response.data;
          });

        if(!this.error) {
          this.resetFormData();
          this.resetValidation();
          this.loading = false;
          this.$emit('close');
          this.$toast.success(`Your registration has been successfully completed.`, {
            position: "top"
          });
          setTimeout(this.$toast.clear, 3000);
        }
      },
      isAllDataFilled() {
        if(this.formData.username === "" ||
           this.formData.password === "" ||
           this.formData.confirmPassword === "" ||
           this.formData.firstName === "" ||
           this.formData.lastName === "" ||
           this.formData.dateOfBirth === "" ||
           this.formData.gender === "") {
            return false;
           }
        return true;
      },
      isPasswordValid() {
        // Da li je sifra ima bar 8 karaktera i barem jednu cifru
        if(this.formData.password.length < 8 || this.formData.password.replace(/[^0-9]/g, '').length < 1) {
          return false;
        }
        return true;
      },
      resetValidation() {
        this.error = false;
        this.errorMsg = "";
      },
      resetFormData() {
        this.formData.username = "",
        this.formData.password = "",
        this.formData.confirmPassword = "",
        this.formData.firstName = "",
        this.formData.lastName = "",
        this.formData.dateOfBirth = "",
        this.formData.gender = ""
      }
    },
    computed: {
      today() {
        return new Date().toISOString().split("T")[0];
      }
    },
    components: {
      ModalComponent
    }
}

</script>

<style scoped lang="scss">
  .grid-container {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 0px;
  }

  .radio-buttons {
    grid-column: span 2;
  }

  .radio-buttons label {
    margin-right: 20px;
  }

  .radio-buttons input[type=radio] {
    margin-right: 5px;
  }

  .info {
    grid-column: span 2;
    justify-self: center;
    margin-top: 15px;
  }

  .error {
    color: $active-primary;
  }

</style>