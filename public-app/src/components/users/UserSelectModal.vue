<script>
import ModalComponent from "../ModalComponent.vue";
import UsersGrid from "../users/UsersGrid.vue";
export default {
  components: { ModalComponent, UsersGrid },
  props: {
    show: Boolean,
    userType: String
  },
  emits: ['close', 'confirm'],
  data(){
    return {
      selectedUser: null
    }
  },
  watch:{
    show(){
      this.selectedUser = null;
    }
  },
  methods:{
    confirmHandler(){
      if(this.selectedUser != null)
        this.$emit('confirm', this.selectedUser);
    }
  }
}
</script>

<template>
  <teleport to="body">
    <modal-component :responsive="true" :show="show" buttonText="confirm" @close="$emit('close')" @confirm="confirmHandler">
      <template #header>
        Select manager
      </template>
      <template #body>
        <div class="manager-modal-body">
          <users-grid class="grid" :compact="true" :userType="userType" :selectable="true" @userSelected="selectedUser=$event"></users-grid>
        </div>
      </template>
    </modal-component>
  </teleport>
</template>

<style scoped lang="scss">
.manager-modal-body{
  max-height: 560px;
  overflow-y: scroll;
  padding:0px;
}
</style>

