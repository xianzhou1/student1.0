<template>
  

  <div id="solution" v-show="soQustion">
    答疑
  </div>

  <div id="person">
      <div id="person-home">
    <div id="person-head">
        <i :class="personHeader"></i>
      <span>用户:&nbsp;{{ mes.userName }}</span>
    </div>
    <div id="person-intro">
      <span>{{ mes.userName }}</span>
      <span>{{ personIntroMes }}</span>
    </div>
    <div id="person-power">
      <span>你的权限为下 <i class="iconfont icon-houtai_yiwen"></i> </span>
    </div>
    <div id="power-ful">
        <div id="item" @click="allShow">
           <i class="iconfont icon-houtaichakan"></i>
          <span>查询学生</span>         
        </div>
        <div id="item" v-show="delPower" @click="applicationShow">
          <i class="iconfont icon-huishouzhan"></i>
          <span>删除学生</span>
        </div>
        <div id="item" v-show="modifyPower" @click="applicationShow">
          <i class="iconfont icon-user"></i>
          <span>修改信息</span>
        </div>
        <div id="item" v-show="insertPower"  @click="insertShow">
          <i class="iconfont icon-add-circle"></i>
          <span>添加学生</span>
        </div>
        <div id="item" v-show="managePower" @click="manageShow">
          <i class="iconfont icon-houtai-5"></i>
          <span>管理用户</span>
        </div> 
      </div>
      </div>
  </div>
</template>


<script>

export default {
  data() {
    return {
      soQustion: false,//答疑
      localMes: {},//接收父组件传来的参数
      personIntroMes: "",
      //icon-houtaiguanli4,icon-houtaiyonghu
      personHeader: "iconfont icon-houtai-2",
      delPower: true,
      modifyPower: true,
      insertPower: true,
      managePower: true,

    }
  },
  props: {
    mes: {
      type: Object,
      default() {
        return {userName: "普通",id: "普通"}
      }
    },

  },
  created() {
    this.localMes = this.mes
    if (this.localMes.id == "超级管理员") {
      this.personIntroMes = "尊敬的超级管理员,你好!"
    } else if (this.localMes.id == "管理员") {
      this.personIntroMes = "管理员,你好"
      this.managePower = false
      this.delPower = false
      this.personHeader = 'iconfont icon-houtaiguanli4'
    } else {
      this.personIntroMes = "普通用户,你好"
      this.managePower = false
      this.insertPower = false
      this.delPower = false
      this.personHeader = 'iconfont icon-houtaiyonghu'
    }
    
  },
  methods: {
    allShow() {
      this.$emit("router","ALLSHOW")
    },
    applicationShow() {
      this.$emit("router","APPLICATION")
    },
    insertShow() {
      this.$emit("router","INSERT")
    },
    manageShow() {
      this.$emit("router","MANAGE")
    }

    
  }

}

</script>


<style scoped>
#solution {
  position:absolute;
  top:50%;
  bottom:50%;
  transform: translate(-50%,-50%);

}

#person {
  width: 1280px;
  height: 526px;
  background-color: #3E4566;
  opacity: 0.9;
  z-index: 1;
    color:#fff;
}


#person-home {
  position:relative;
  top:3%;
  left:50%;
  transform: translate(-50%);
  width: 500px;
  height: 470px;
  display:flex;
  flex-direction: column;
  justify-content: space-between;
}

#person-head {
  position: absolute;
  bottom:-450px;
  display: flex;
  flex-direction: column;
  width: 100%;
  height: 120px;
  text-align: center;
  overflow: hidden;
  animation: head 3s alternate forwards;
}

#person-head .iconfont {
  margin-left:205px;
  margin-bottom: 20px;
  width: 75px;
  height: 75px;
  border-radius: 100%;
  font-size:55px;
  line-height: 60px;
  
}

#person-head span {
  margin-left:-15px;
  font-size:24px;
}

#person-home #person-intro {
  position:absolute;
  bottom:-590px;
  margin-left:70px;
  display:flex;
  flex-direction: column;
  width: 350px;
  height: 100px;
  border-top: 1px solid #b0a9a9;
  border-bottom: 1px solid #b0a9a9;
  font-size:20px;
  text-align: center;
  padding-top: 20px;
  animation: head 2s 0.5s alternate forwards;
}

#person-home #person-power {
  position:absolute;
  bottom:-640px;
  left:180px;
  text-align: center;
  font-size:18px;
  animation: head 2s 0.5s alternate forwards;
}

#person-home #person-power .iconfont {
  font-size:20px;
  vertical-align: middle;
}

#person-home #person-power .iconfont:hover {
  color:black;
  cursor: pointer;
}

#person-home #power-ful {
  position:absolute;
  /* top:340px; */
  bottom:-760px;
  margin-left:-100px;
  margin-bottom: 50px;
  width: 700px;
  display:flex;
  justify-content: space-evenly ;
  animation: head 2s 1s alternate forwards;
}

#person-home #power-ful #item {
  margin-right:20px;
  width: 120px;
  height: 40px;
  font-size:18px;
  text-align: center;
  line-height: 40px;
  cursor: pointer;
}

#person-home #power-ful #item .iconfont {
  font-size:23px;
  margin-right:10px;
  vertical-align: bottom;
}

#person-home #power-ful #item:hover {
  color:black;
}


@keyframes head {
  0% {
    transform: translateY(0px);
  }
  100% {
    transform: translateY(-800px);
  }
}



</style>