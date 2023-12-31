<template>
  <div class="test"
       ref="loginMes">
    {{ logmes }}<span :class="logIconfont"></span>
  </div>

</template>

<script>

export default {
  
  data() {
    return {
      lmes: "",
      lIcon: "",

    }
  },
  props: {
    logmes,
    logIconfont,
  },
  created() {
    this.lmes = this.logmes
    this.lIcon = this.logIconfont
  }
  
}

</script>

<style>
.test {
  position: absolute;
  top: 0px;
  left: 50%;
  width: 200px;
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