<template>

  <div id="header">
    <span>后台管理系统</span>
    <div class="re-log-in" @click="backLog">
      登录/注册
    </div>
  </div>
  <div id="root">
    <div id="contro">
      <!-- 用户 -->
      <div class="person" @click="userMenu">
          <div>
            <i class="iconfont icon-guanlihoutai1"></i>
          </div>
          用户:{{ mes.userName }}
        </div>
      <!-- 导航栏 -->
      <div class="check-work">
        <!-- 数据查看 -->
        <div class="item" @click="databaseMenu">
            <div class="icon">
              <i class="iconfont icon-houtai-4"></i>
            </div>
            <span>数据后台</span>
        </div>
        <!-- 查询 -->
         <div class="item" @click="selectMenu">
            <div class="icon">
              <i class="iconfont icon-houtai-"></i>
            </div>
            <span>查询学生</span>
        </div>
        <!-- 添加学生 -->
         <div class="item" @click="insertStuMenu" v-if="insertBtn">
            <div class="icon">
              <i class="iconfont icon-a-1_xinxi3fuben17"></i>
            </div>
            <span>添加学生</span>
        </div>
        <!-- 用户中心 -->
         <div class="item" @click="HomeMenu" v-if="HomeBtn">
            <div class="icon">
              <i class="iconfont icon-zhanghaoguanli"></i>
            </div>
            <span>用户中心</span>
        </div>
        <!-- 注销 -->
      
      </div>
      <div class="exit" @click="LogMenu">
        <div class="icon">
          <i class="iconfont icon-guanbihoutai"></i>
        </div>
          <span>注销</span>
      </div>
    </div>
    
    <div id="service" ref="service">
      <component :is="pointer" :mes="mes" @router="modifCom"></component>
    </div>
  </div>

</template>

<script>
import ALLSHOW from '../components/allShow.vue'
import APPLICATION from '../components/applicationData.vue'
import INSERT from '../components/insertStu.vue'
import MANAGE from '../components/managerCom.vue'
import PERSON from '../components/personHome.vue'

export default{
  data(){
    return{
      mes: {},//登录人员信息
      insertBtn: false,//是否有权限进行添加
      HomeBtn: false,//是否有权限进行后台管理
      pointer: "PERSON",

    }
  },
  methods: {
    modifCom(val) {
      this.pointer = val
    },
    //转去登录页面
    backLog() {
      this.$router.push({
        path: '/login'
      })
    },
    // 个人中心
    userMenu() {
      this.pointer = "PERSON"
    },
    // 后台数据
    databaseMenu() {
      this.pointer = "APPLICATION"
    },
    // 查询学生
    selectMenu() {
      this.pointer = "ALLSHOW"
    },
    // 添加学生
    insertStuMenu() {
      this.pointer = "INSERT"
    },
    // 后台管理
    HomeMenu() {
      this.pointer = "MANAGE"
    },
    // 注销
    LogMenu() {
      this.$router.push({
        path:'/login'
      })
    }

  },
  created() {
    this.mes = this.$route.params
    if (this.mes.id == "超级管理员" || this.mes.id == "管理员") {
      this.insertBtn = true
    }
    if (this.mes.id == "超级管理员") {
      this.HomeBtn = true
    }
    
  },
  components: {
    PERSON,
    ALLSHOW,
    APPLICATION,
    INSERT,
    MANAGE,

  },
  
}

</script>


<style scoped>


#root {
  display: flex;
  width: 100%;
  height: 525px;
  background-color: #fff;
}

/* 头部 */
#header span {
  margin-left:50%;
  transform: translate(-50%);
  font-size:24px;
  font-weight: 700;
  flex:1;
}
#header .re-log-in {
  height: 60px;
  width: 100px;
  text-align: center;
  line-height: 60px;
  font-size:18px;
  cursor: pointer;
  transition: all 0.5s;
}
#header .re-log-in:hover {
  color:rgb(7, 45, 33);
  font-size:18px;
}

#contro {
  position:absolute;
  display:flex;
  flex-direction: column;
  margin-top: -70px;
  width: 60px;
  height: 595px;
  background-color:#2F4056;
  opacity: 0.95;
  transition: all 0.9s;
  overflow: hidden;
  z-index: 2;
}

#contro:hover {
  width: 150px;
}

#contro .person {
  margin-left: 13px;
  margin-top:25px;
  margin-bottom:15px;
   display:flex;
  width: 30px;
  height: 30px;
  font-size:20px;
  vertical-align: bottom;
  overflow: hidden;
  transition: all 0.3s;
  cursor: pointer;
}

#contro .person .iconfont {
  font-size:30px;
  margin-right:10px;
}

#contro .person:hover {
  color:aliceblue;
}

#contro:hover .person {
  width: 150px;
}

#contro .person .iconfont {
  color:white;
}

/* 导航栏整体布局 */
#contro .check-work {
  margin-left:13px;
  height: 470px;
  display:flex;
  flex-direction:column;

}
/* 单个选项 */
.check-work .item {
  margin-top: 20px;
  display:flex;
  width: 30px;
  height: 30px;
  font-size:20px;
  vertical-align: bottom;
  overflow: hidden;
  transition: all 0.3s;
  cursor: pointer;
}
/* 导航栏默认的字体样式大小 */
.check-work .iconfont{
  font-size:30px;
  padding-right:10px;
}
/* 单个导航 */
#contro:hover .item{
  width: 190px;
}
/* 单个导航:hover */
#contro .item:hover {
  color: aliceblue;
}

#contro .exit {
  margin-left: 13px;
  margin-bottom:10px;
  display:flex;
  width: 30px;
  height: 30px;
  font-size:20px;
  vertical-align: bottom;
  overflow: hidden;
  transition: all 0.3s;
  cursor: pointer;
}

#contro .exit .iconfont {
  margin-right:20px;
  font-size:30px;
}
#contro .exit:hover {
  color: aliceblue;
}

#contro:hover .exit {
  width:150px;
}


</style>