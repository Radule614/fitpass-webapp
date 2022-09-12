<script>
import ConfirmModal from '@/components/utility/ConfirmModal.vue';
import CustomButton from '@/components/utility/CustomButton.vue';
import UserSelectModal from '@/components/users/UserSelectModal.vue';
export default {
  components: { ConfirmModal, CustomButton, UserSelectModal },
  data(){
    return {
      loading: false,
      messages: [],
      modalActive: false,
      contentForDeletion: null,
      trainerModalActive: false,
      clearModalActive: false,
      selectedContent: null
    }
  },
  computed: {
    facility(){
      return this.$store.getters['auth/managerFacility'];
    },
    contentList(){
      return this.facility && this.facility.content ? this.facility.content : [];
    }
  },
  methods:{
    async formSubmit(event){
      this.loading = true;
      event.preventDefault();
      const data = new FormData(this.$refs.submitForm);
      data.append('facility_id', this.facility.name);
      try{
        await this.$store.dispatch('facility/addContent', { formData: data, facility: this.facility });
        this.messages = [];
        this.$refs.nameInput.value = "";
        this.loading = false;
      }catch(error){
        this.messages = error.message.split(",");
        this.loading = false;
      }
    },
    btnDeleteClick(content){
      this.contentForDeletion = content;
      this.modalActive = true;
    },
    cancelDelete(){
      this.contentForDeletion = null;
      this.modalActive = false;
    },
    async deleteHandler(){
      this.loading = true;
      try{
        this.$store.dispatch('facility/deleteContent', { content: this.contentForDeletion, facility: this.facility });
        this.modalActive = false;
        this.loading = false;
      }catch(error){
        console.error(error);
        this.modalActive = false;
        this.loading = false;
      }
    },
    selectTrainerHandler(trainer){
      this.loading = true;
      try{
        this.$store.dispatch('facility/setTrainer', { trainerUsername: trainer.username, content: this.selectedContent });
        this.trainerModalActive = false;
        this.selectedContent = null;
        this.loading = false;
      }catch(error){
        console.error(error);
        this.trainerModalActive = false;
        this.selectedContent = null;
        this.loading = false;
      }
    },
    clearTrainerHandler(){
      this.loading = true;
      try{
        this.$store.dispatch('facility/removeTrainer', { content: this.selectedContent });
        this.clearModalActive = false;
        this.selectedContent = null;
        this.loading = false;
      }catch(error){
        console.error(error);
        this.clearModalActive = false;
        this.selectedContent = null;
        this.loading = false;
      }
    },
    trainerName(content){
      return content && content.trainer ? `${content.trainer.firstname} ${content.trainer.lastname}` : "";
    }
  }
}
</script>

<template>
  <div>
    <div class="content-list">
      <div class="content-item" v-for="(item, index) in contentList" :key="index">
        <div class="content-heading">
          {{item.name}}
        </div>
        <hr>
        <div class="details">
          <div class="row">
            <div class="col">Content Type</div>
            <div class="col">{{item.type}}</div>
          </div>
          <div class="row">
            <div class="col">Trainer</div>
            <div class="col">{{trainerName(item)}}</div>
          </div>
        </div>
        <div class="button-group left">
          <custom-button type="button" class="block" @click="clearModalActive=true;selectedContent=item">Clear Trainer</custom-button>
          <custom-button type="button" class="block" @click="trainerModalActive=true;selectedContent=item">Set Trainer</custom-button>
        </div>
        <div class="button-group">
          <custom-button type="button" class="btn-delete block inverse" @click="btnDeleteClick(item)">Remove</custom-button>
        </div>
      </div>
    </div>
    <div v-if="contentList.length == 0">No content</div>
    <hr>
    <form @submit="formSubmit($event)" class="add-form" ref="submitForm" v-if="facility">
      <table>
        <tr>
          <td><label for="name">name:</label></td>
          <td><input type="text" id="name" name="name" ref="nameInput"></td>
        </tr>
        <tr>
          <td><label for="type">type:</label></td>
          <td>
            <select class="select-primary" name="type" id="type">
              <option value="GROUP">GROUP</option>
              <option value="PERSONAL">PERSONAL</option>
            </select>
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <div class="error-block">
              <div v-for="(message, index) in messages" :key="index">{{message}}</div>
            </div>
          </td>
        </tr>
        <tr>
          <td colspan="2">
            <div class="button-group">
              <custom-button type="submit" class="block inverse">Add Content</custom-button>
            </div>
          </td>
        </tr>
      </table>
    </form>
    <confirm-modal :show="modalActive" @close="cancelDelete" @confirm="deleteHandler"></confirm-modal>
    <user-select-modal :show="trainerModalActive" @close="trainerModalActive=false" @confirm="selectTrainerHandler($event)" userType="TRAINER"></user-select-modal>
    <confirm-modal :show="clearModalActive" @close="clearModalActive = false" @confirm="clearTrainerHandler"></confirm-modal>
  </div>
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
    margin-top: 40px;
    & > button {
      margin-left: 15px;
    }
  }

  label{
    padding-right: 50px;
  }

  td{
    padding-bottom: 12px;
    input,
    select{
      margin-bottom: 0px;
      border-radius: 0px;
      width: 280px;
    }
  }
}
.content-list{
  display: flex;
  flex-wrap: wrap;
  .content-item {
    position: relative;
    padding: 15px;
    margin-right: 15px;
    margin-bottom: 15px;
    min-width: 200px;
    box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.3);
    .content-heading {
      margin-bottom: 5px;
      align-items: center;
    }
    hr{
      margin-top: 0px;
    }
    .details{
      font-size: 14px;
      .row {
        box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.2);
        margin: 5px 0px 0px 0px;
        &:first-child{
          margin-top: 0px;
        }
        .col{
          color: $dark-primary;
          font-style: normal;
          font-size: 15px;
          display: flex;
          align-items: center;
          padding-top:5px;
          padding-bottom: 5px;
          &:nth-child(2){
            font-style: italic;
            background-color: rgba($active-primary, $alpha: 0.4);
          }
        }
      }
    }
    .button-group{
      &.left{
        justify-content: left;
      }
      display: flex;
      & > button {
        margin-left: 15px;
        &:first-child{
          margin-left: 0px;
        }
      }
      justify-content: right;
      margin-top: 20px;
    }
  }
}


</style>