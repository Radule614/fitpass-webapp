<script>
import ConfirmModal from '@/components/utility/ConfirmModal.vue';
export default {
  components: { ConfirmModal },
  data(){
    return {
      loading: false,
      messages: [],
      modalActive: false,
      contentForDeletion: null,
    }
  },
  computed: {
    coupons(){
      return this.$store.getters['user/coupons'];
    },
  },
  methods: {
    async formSubmit(event){
      this.loading = true;
      event.preventDefault();
      const data = new FormData(this.$refs.submitForm);
      try{
        await this.$store.dispatch('users/addCoupon', { formData: data });
        this.messages = [];
        this.$refs.nameInput.value = "";
        this.loading = false;
      }catch(error){
        console.log(error);
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
        this.$store.dispatch('facility/deleteContent', { content: this.contentForDeletion });
        this.modalActive = false;
        this.loading = false;
      }catch(error){
        console.error(error);
        this.modalActive = false;
        this.loading = false;
      }
    },
  }
}
</script>

<template>
  <div class="container">
    <h3>Admin Page</h3>
    <hr>
    <h5>Coupons</h5>
    <div class="coupon-list">
      <div class="coupon-item" v-for="(item, index) in coupons" :key="index">
        <div class="coupon-heading">
          {{item.name}}
        </div>
        <hr>
        <div class="details">
          <div class="row">
            <div class="col">Coupon Size</div>
            <div class="col">{{item.size}}</div>
          </div>
        </div>
        <div class="button-group">
          <custom-button type="button" class="btn-delete block inverse" @click="btnDeleteClick(item)">Remove</custom-button>
        </div>
      </div>
    </div>
    <hr>
    <h5>Add Coupon</h5>
    <form @submit="formSubmit($event)" class="add-form" ref="submitForm">
      <table>
        <tr>
          <td><label for="name">name:</label></td>
          <td><input type="text" id="name" name="name" ref="nameInput"></td>
        </tr>
        <tr>
          <td><label for="size">size:</label></td>
          <td><input type="number" id="size" name="size"></td>
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
              <custom-button type="submit" class="block inverse">Add Coupon</custom-button>
            </div>
          </td>
        </tr>
      </table>
    </form>
    <confirm-modal :show="modalActive" @close="cancelDelete" @confirm="deleteHandler"></confirm-modal>
  </div>
</template>

<style scoped lang="scss">
.container{
  padding-top: 30px;
}
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