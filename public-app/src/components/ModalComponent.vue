<script>
export default {
  props: {
    show: Boolean,
    buttonText: String,
    width: Number,
    disabled: Boolean,
    simple: Boolean,
    responsive: Boolean
  },
  emits:['close', 'confirm'],
  computed: {
    isDisabled(){
      return this.disabled
    }
  },
  watch: {
    show(newShow, oldShow){
      if(newShow == true){
        document.body.style.overflowY = 'hidden';
      }else{
        document.body.style.overflowY = 'scroll';
      }
    }
  }
}
</script>

<template>
  <transition name="modal">
    <div v-if="show" class="modal-mask">
      <div class="modal-wrapper" :class="{'simple': simple}">
        <div class="modal-container" :class="{'container': responsive}" v-bind:style="{ 'width': width + 'px'}">
          <div v-if="!simple" class="modal-header">
            <slot name="header">
              default heading
            </slot>
          </div>
          <div class="modal-content">
            <div class="modal-body">
              <slot name="body">
                default body
              </slot>
            </div>
            <div class="modal-footer">
              <slot name="footer">
                <custom-button type="button" :disabled="isDisabled" @click="$emit('close')">cancel</custom-button>
                <custom-button type="submit" :disabled="isDisabled" class="inverse" @click="$emit('confirm')">{{ buttonText }}</custom-button>
              </slot>
            </div>
          </div>
        </div>
      </div>
    </div>
  </transition>
</template>

<style scoped lang="scss">
.modal-mask {
  position: fixed;
  z-index: 9998;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.62);
  display: table;
  transition: opacity 0.3s ease;
}

.modal-wrapper {
  display: table-cell;
  vertical-align: middle;
}

.modal-container {
  margin: 0px auto;
  background-color: $light-primary;
  border-radius: 2px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.3s ease;
  padding: 0px;
  &:not(.container){
    width: 300px;
  }
}

.modal-content{
  padding: 15px;
  border-radius: 0px 0px 2px 2px;
  background-color: $light-primary;
}

.modal-header{
  font-size: 22px;
  color:$active-primary;
  background-color: $dark-primary;
  border-radius: 2px 2px 0px 0px;
  border-bottom: 0px;
}

.simple .modal-body{
  text-align: center;
  font-size: 17px;
  padding-bottom: 30px;
}

.modal-footer{
  padding-bottom: 0px;
}


.modal-enter-from {
  opacity: 0;
}

.modal-leave-to {
  opacity: 0;
}

.modal-enter-from .modal-container,
.modal-leave-to .modal-container {
  -webkit-transform: scale(1.1);
  transform: scale(1.1);
}
</style>