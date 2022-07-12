<script>
export default{
  props: {
    user: Object
  }
}
</script>

<template>
  <div class="user-details">
    <div class="header">
      <div class="image-wrapper">
        <img class="image" src="../../assets/user-image.png">
      </div>
    </div>
    <div class="info">
      <div class="main">
        <div class="fullname">{{user.firstname}} {{user.lastname}}</div>
        <div class="username">{{user.username}}</div>
      </div>
      <div class="details">
        <div class="row">
          <div class="col">birthday</div>
          <div class="col">{{user.dateOfBirth}}</div>
        </div>
        <div class="row">
          <div class="col">gender</div>
          <div class="col">{{user.gender}}</div>
        </div>
        <div class="row">
          <div class="col">user type</div>
          <div class="col">{{user.userType}}</div>
        </div>
        <div class="row" v-if="user.points || user.points == 0">
          <div class="col">points</div>
          <div class="col">{{user.points}}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped lang="scss">
.user-details{
  .header{
    background-color: rgba($active-primary, $alpha: 0.7);
    height: 80px;
    position: relative;
    .image-wrapper{
      position: absolute;
      bottom:-40px;
      left:0px;
      right:0px;
      margin: auto;
      width: 80px;
      height: 80px;
      border-radius: 50%;
      border:4px solid #fff;
      background-repeat: no-repeat;
      background-color: lightskyblue;
      background-color: rgba($active-primary, $alpha: 0.7);
      img{
        width: 30px;
        position: absolute;
        top:0px;
        bottom:0px;
        left:0px;
        right:0px;
        margin: auto;
      }
    }
  }
  .info{
    padding:15px;
    padding-top: 45px;
    .main{
      text-align: center;
      .fullname{
        font-size: 18px;
      }
      .username{
        font-style: italic;
        color: $active-primary;
      }
    }
    .details{
      .row {
        box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.1);
        margin: 10px 0px 0px 0px;
        &:first-child{
          margin-top: 20px;
        }
        .col{
          text-transform: lowercase;
          color: $dark-primary;
          font-style: normal;
          line-height: 42px;
          &:nth-child(2){
            font-style: italic;
            background-color: rgba($active-primary, $alpha: 0.3);
          }
        }
      }
    }
  }
}
</style>
