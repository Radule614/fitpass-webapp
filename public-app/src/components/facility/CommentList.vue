<script>
import ConfirmModal from '../utility/ConfirmModal.vue';
export default {
  components: { ConfirmModal },
  props: {
    forPublic: Boolean,
    admin: Boolean,
    manager: Boolean
  },
  data(){
    return {
      deleteModalActive: false,
      approveModalActive: false,
      commentForApproval: null
    }
  },
  computed: {
    comments(){
      const comments = this.$store.getters['comments/comments'];
      return this.forPublic && !this.admin ? comments.filter(c => c.approved) : comments;
    }
  },
  methods: {
    getFullname(comment){
      return comment.firstname + " " + comment.lastname;
    },
    async approvalHandler(approval){
      try{
        await this.$store.dispatch('comments/commentApproval', { comment: this.commentForApproval, approval: approval });
        this.clearApproval();
      }catch(error){
        console.error(error);
        this.clearApproval();
      }
    },
    clearApproval(){
      this.deleteModalActive = false,
      this.approveModalActive = false,
      this.commentForApproval = null
    }
  }
}
</script>

<template>
  <div :class="{ 'manager': manager }">
    <h5 class="banner my-5">COMMENTS</h5>
    <div class="comment-list" v-if="comments.length">
      <div class="comment" v-for="(comment, index) in comments" :key="index" :class="{ 'approved': comment.approved, 'admin': admin }">
        <div class="comment-header">
          <span class="username">{{comment.username}}</span>
          <span class="fullname">&nbsp;({{getFullname(comment)}})</span>
          <span>&nbsp;posted on</span>
          <span class="date">&nbsp;{{comment.postedOn}}</span>
        </div>
        <div class="comment-body">
          {{comment.content}}
        </div>
        <div class="footer" v-if="admin || !forPublic">
          <div class="approved">
            <div v-if="comment.approved">approved</div>
            <div v-else>not approved</div>
          </div>
          <div v-if="admin" class="button-group">
            <custom-button @click="commentForApproval=comment; deleteModalActive=true">Delete</custom-button>
            <custom-button v-if="!comment.approved" @click="commentForApproval=comment; approveModalActive=true" class="inverse">Approve</custom-button>
          </div>
        </div>
        <hr>
      </div>
    </div>
		<div v-else>
			There are no comments yet.
		</div>
    <confirm-modal :show="deleteModalActive"  @close="clearApproval" @confirm="approvalHandler(false)"></confirm-modal>
    <confirm-modal :show="approveModalActive" @close="clearApproval" @confirm="approvalHandler(true)"></confirm-modal>
  </div>
</template>

<style scoped lang="scss">
.comment-list {
  .comment{
    .footer{
      color:red;
      font-style: italic;
      font-size: 14px;
    }
    &.approved .footer{
      color:green;
    }
    &:not(.approved):not(.admin){
      opacity: 0.8;
    }
  }
  .comment-header{
    font-size: 15px;
  }
  .username{
    color: $active-primary;
    font-size: 16px;
  }
  .date{
    color: $active-primary;
  }
  .fullname{
    font-size: 14px;
    font-style: italic;
  }
  .comment-body { 
    padding:5px;
    font-size: 15px;
  }
  hr {
    margin-top: 0.4rem;
  }
  .footer{
    display: flex;
    .approved{
      flex:1;
    }
    .button-group{
      display: flex;
      justify-content: right;
      & > button {
        margin-left: 15px;
      }
    }
  }
}
.banner {
	background: $active-primary;
	color: $light-primary;
	font-size: 72px;
	padding: 30px 60px;
	width: 100vw;
	transform: translateX(-120px);
}
.manager .banner{
  width: 100%;
  transform: none;
  font-size: 52px;
  padding-left: 30px;
  margin-top: 0px!important;
}
</style>