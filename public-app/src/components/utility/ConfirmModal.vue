<script>
import ModalComponent from "../ModalComponent.vue";
export default{
  props: {
    show: Boolean
  },
  emits:['close', 'confirm'],
  components:{
    ModalComponent
  }
}
</script>

<template>
  <teleport to="#app">
    <modal-component :show="show" buttonText="confirm" :width="320" :simple="true" @close="$emit('close')" @confirm="$emit('confirm')">
      <template #body>Are you sure?</template>
    </modal-component>
  </teleport>
</template>