<script>
export default {
  beforeMount () {
    this.$store.dispatch('comments/getAllFacilityComments', this.facility.name);
  },
  computed:{
    facility(){
      return this.$store.getters['auth/managerFacility'];
    },
    comments(){
      return this.$store.getters['comments/comments'];
    }
  },
  methods:{
    getFullname(comment){
      return comment.firstname + " " + comment.lastname;
    }
  }
}
</script>

<template>
<div>
  <h5>comments</h5>
  <hr>
  <div class="comment-list">
    <div class="comment" v-for="(comment, index) in comments" :key="index" :class="{ 'approved': comment.approved }">
      <div class="comment-header">
        <span class="username">{{comment.username}}</span>
        <span class="fullname">&nbsp;({{getFullname(comment)}})</span>
        <span>&nbsp;posted on</span>
        <span class="date">&nbsp;-datum-</span>
      </div>
      <div class="comment-body">
        {{comment.content}}
      </div>
      <div class="footer">
        <div v-if="comment.approved">approved</div>
        <div v-else>not approved</div>
      </div>
      <hr>
    </div>
  </div>
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
    &:not(.approved){
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
}
</style>