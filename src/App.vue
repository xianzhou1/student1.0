<template>
    <router-view></router-view>

</template>

<script>

export default{
  data(){
    return{

    }
  }

}

</script>


<style>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  list-style:none;
  text-decoration: none;
}

#header {
  display:flex;
  width: 100%;
  height: 60px;
  font-size:22px;
  line-height: 60px;
  text-align: center;
  color:#fff;
  background-color:#1AA094;
}

.test {
  position: absolute;
  top: 0px;
  left: 50%;
  width: 300px;
  height: 80px;
  display: block;
  text-align: center;
  font-size: 22px;
  font-weight: 700;
  line-height: 80px;

  transform: translateX(-50%);
  opacity: 0;
  transition: all 2s;
}

.test .iconfont {
  display: inline-block;
  margin-left: 5px;
  font-size: 22px;
  font-weight: 700;
  text-align: center;
  line-height: 80px;
  transition: all 1s;
}

</style>

