<script>
export default{
  data(){
    return {
      filename: "No file selected"
    }
  },
  methods:{
    inputChanged(e){
      if(e.target.files && e.target.files[0]){
        this.filename = e.target.files[0].name;
        this.$emit('fileChanged', e.target.files[0]);
      }
    }
  }
}
</script>

<template>
  <div>
    <label class="input-file-label" for="file" v-bind="{...$props, ...$attrs}">
      <span>Select Image</span>
      <input type="file" id="file" name="file" @change="inputChanged($event)">
    </label>
    <span class="filename">{{filename}}</span>
  </div>
</template>

<style scoped lang="scss">
div{
  position: relative;
}
label.input-file-label{
  margin-bottom: 0px;
  &.block{
    border-radius: 0px;
  }
  position: relative;
  font-size:16px;
  box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.3);
  background-color: $light-primary;
  color:$dark-primary;
  border-radius: 25px;
  outline: 0px;
  &::before{
    content: "";
    position:absolute;
    width: 100%;
    height: 100%;
    top:0px;
    left:0px;
    border-radius: 50%;
    background-color: $active-primary;
    transform: scale(0);
    transition: transform 0.3s ease-in-out, border-radius 0.3s ease-in-out;
  }
  &.block{
    border-radius: 0px;
  }
  span{
    position: relative;
    transition:color 0.1s ease-in-out 0.1s;
  }
  &:hover{ 
    span{
      color:$light-primary;
      transition:color 0.4s ease-in-out;
    }
    &::before{
      border-radius: 25px;
      transform: scale(1);
    }
    &.block::before{
      transition: transform 0.3s ease-in-out, border-radius 0.7s ease-in-out;
      border-radius: 0px;
    }
  }
}
.filename{
  padding-left: 14px;
  font-style: italic;
}
</style>