<script>
import ConfirmModal from '../utility/ConfirmModal.vue';
export default {
  components: { ConfirmModal },
  data(){
    return {
      loading: false,
      messages: [],
      modalActive: false,
      contentForDeletion: null
    }
  },
  computed: {
    facility(){
      return this.$store.getters['auth/managerFacility'];
    },
    contentList(){
      return this.facility ? this.facility.content : [];
    }
  },
  methods:{
    async formSubmit(event){
      this.loading = true;
      event.preventDefault();
      const data = new FormData(this.$refs.submitForm);
      try{
        await this.$store.dispatch('facility/addContent', { formData: data, facility: this.facility });
        this.messages = [];
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
        this.$store.dispatch('facility/deleteContent', { content: this.contentForDeletion, facility: this.facility } );
        this.modalActive = false;
        this.loading = false;
      }catch(error){
        console.error(error);
        this.modalActive = false;
        this.loading = false;
      }
    }
  }
}
</script>

<template>
  <div>
    <div class="content-list">
      <div class="content-item" v-for="(item, index) in contentList" :key="index">
        <div class="name">
          {{item.name}} | {{item.type}}
        </div>
        <custom-button class="btn-delete" @click="btnDeleteClick(item)">
          <fa-icon :icon="['fas', 'xmark']"></fa-icon>
        </custom-button>
      </div>
    </div>
    <div v-if="contentList.length == 0">No content</div>
    <hr>
    <form class="add-form" ref="submitForm" v-if="facility">
      <table>
        <tr>
          <td><label for="name">name:</label></td>
          <td><input type="text" id="name" name="name"></td>
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
              <custom-button class="block inverse" @click="formSubmit($event)">Add Content</custom-button>
            </div>
          </td>
        </tr>
      </table>
    </form>

    <confirm-modal :show="modalActive" @close="cancelDelete" @confirm="deleteHandler"></confirm-modal>
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
    & > * {
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
  .content-item{
    padding-left: 10px;
    border-radius: 50px;
    border:1px solid gray;
    margin-left: 5px;
    margin-bottom: 5px;
    display: flex;
    align-items: center;
    &:first-child{
      margin-left: 0px;
    }
    .btn-delete{
      padding:0px 14px;
      margin-left: 20px;
    }
  }
}

</style>