<template>
  <modal-component :show="show" @close="$emit('close')" buttonText="register" :width="600" :disabled="loading">
    <template #header>
      Register
    </template>
    <template #body>
      <form @submit.prevent="register()">

      </form>
    </template>
  </modal-component>
</template>

<script>
import ModalComponent from "../ModalComponent.vue"

export default {
    name: "RegisterComponent",
    props: {
      show: Boolean,

    },
    data() {
      return {
        loading: false,
      }
    },
    methods: {
      register() {
        
      }
    },
    components: {
      ModalComponent
    }
}

</script>

<style scoped>

</style>