<template>
 
  <div class="manager">
     <div class="test"
       ref="loginMes">
    {{ logmes }}<span :class="logIconfont"></span>
  </div>
    <div id="title">
      <i class="iconfont icon-houtaizonglan"></i>
      <span>用户管理界面</span>
    </div>
    <div id="manager-header">
      <i class="iconfont icon-houtai-2"></i>
      <div>
        <span>尊敬的超级管理员,你好!</span>
      </div>
    </div>
    <div id="select">
      <div id="select-title">
        查询信息
      </div>
        <div id="select-item">
          <span>请输入查询的用户名:</span>
          <input type="text" v-model.lazy.trim="selectName" placeholder="请输入查询的用户名">
        </div>
         <div id="select-item">
          <span>请输入查询的身份:</span>
          <input type="text" v-model.lazy.trim="selectIdentity" placeholder="请输入查询的身份">
        </div>
        <div id="select-buttom">
          <button @click="selectOne">查询</button>
          <button @click="selectAll">查询全部</button>
        </div>
    </div>
    <div id="insert">
      <div id="insert-title">
        添加用户
      </div>
          <div id="item-insert">
            <span>请输入添加的用户名:</span>
            <input type="text" v-model.lazy.trim="user.userName" placeholder="用户名">
          </div>
              <div id="item-insert">
            <span>请输入添加的密码:</span>
            <input type="text" v-model.lazy.trim="user.password" placeholder="密码">
          </div>
              <div id="item-insert">
            <span>请输入添加的身份:</span>
            <input type="text" v-model.lazy.trim="user.id" placeholder="权限">
          </div>
          <button @click="insertUser">添加</button>
    </div>
    <div id="manager-table">
       <span>如下为目前用户的全部信息:</span>
      <table>
        <tr>
          <td>用户名</td>
          <td>密码</td>
          <td>身份权限</td>
          <td>操作</td>
        </tr>
        <tr v-for="(item,index) of managerList" :key="index">
          <td>{{ item.userName }}</td>
          <td>{{ item.password }}</td>
          <td>{{ item.id }}</td>
          <td v-show="item.id == '超级管理员' ? false : true">
            <span @click="deleteUser(item.userName)">删除</span>
          </td>
        </tr>
      </table>
    </div>
  </div>

</template>

<script>
import axios from 'axios'


export default {
  data() {
    return {
      localMes: {},//当前用户信息
      managerList: [],//用户信息表
      selectName: "",//查询的名字
      selectIdentity: "",//查询的身份
      user: {
        userName: "",
        password: "",
        id:""
      },//添加的信息
      delflag: true,
      logmes: "",
      logIconfont:"",

    }
  },
  props: {
    mes: {
      type: Object,
      default() {
        return {userName: "普通", id: "普通"}
      }
    }
  },
  created() {
    this.localMes = this.mes
  },
  mounted() {
    this.getManList()
  },
  methods: {
    // 获取用户信息
    getManList() {
      this.$axios({
       method: 'GET',
        url: '/showAllUser'
      }).then(res => {
       this.managerList = res.data.data
      })
    },
    selectOne() {
      if (this.selectName != "" && this.selectIdentity == "") {
        this.$axios({
          method: 'POST',
          url: '/selectUserName',
          data: {
            userName: this.selectName
          }
        }).then(res => {
          this.managerList = res.data.data
        })
      } else if (this.selectName == "" && this.selectIdentity != "") {
        this.$axios({
          method: 'POST',
          url: '/selectUserId',
          data: {
            id: this.selectIdentity
          }
        }).then(res => {
          this.managerList = res.data.data
        })
      } else if (this.selectName != "" && this.selectIdentity != "") {
        this.$axios({
          method: 'POST',
          url: '/selectManager',
          data: {
            name:this.selectName,
            id: this.selectIdentity
          }
        }).then(res => {
          this.managerList = res.data.data
        })
      }
    },
    selectAll() {
      this.getManList()
    },
    insertUser() {
      const tmp = this.$refs.loginMes.style
      console.log(this.user.id);
      if (this.user.userName == "" || this.user.id == "" || this.user.password == "") {
        this.logShow(tmp, "不得为空!", "iconfont icon-delete-filling")
      } else if (this.user.id != "超级管理员" && this.user.id != "管理员" && this.user.id != "普通"){
        this.logShow(tmp, "权限给予不合法!", "iconfont icon-delete-filling")
      } else if (this.exUserName(this.user.userName)) {
        this.logShow(tmp, this.user.userName+"用户已存在!", "iconfont icon-delete-filling")
      } else if(this.user.password.length < 5){
        this.logShow(tmp, "密码小于五位!", "iconfont icon-delete-filling")
      } else {
        this.$axios({
          method: 'POST',
          url: '/insertUser',
          data: {
            userName: this.user.userName,
            password: this.user.password,
            id: this.user.id
          }
        }).then(res => {
          console.log(res.data)
          this.logShow(tmp, "添加成功", "iconfont icon-success-filling")
        }).catch(err => {
          console.log(err)
        })
        for (let i = 0; i < 10; i++){
          this.getManList()
        }
      }
      
    },
    deleteUser(username) {
      const tmp = this.$refs.loginMes.style
      this.$axios({
        method: 'POST',
        url: '/deleteUserName',
        data: {
          name:username
        }
      }).then(res => {
        console.log(res.data.data);
        for (let i = 0; i < 5; i++){
          this.getManList()
        }
      })
    },
    // 提示信息
    logShow(tmp,mes,iconf){
      tmp.opacity = '1'
      this.logIconfont = iconf
      this.logmes = mes
      let timeout = 0
      let threadout = setInterval(() => {
        if(timeout >= 1){
          tmp.opacity = '0'
          clearInterval(threadout)
        }
        timeout += 1
      }, 1000)
    },
    exUserName(name) {
      for (let i = 0; i < this.managerList.length; i++){
        if (this.managerList[i].userName == name) {
          return true
        }
      }
      return false
    }


  }


}

</script>

<style scoped>
.manager {
  margin-top: -10px;
  width: 1280px;
  height: 526px;
  padding-left:190px;
  background-color: #3E4566;
  color:aliceblue;
  
}

.manager #title {
  margin-top:10px;
  margin-left:-20px;
  padding-top:10px;
  width: 930px;
  font-size:22px;
  text-align: center;
}
.manager #title .iconfont{
  font-size:30px;
  margin-right:10px;
}

#manager-header {
  margin-top:10px;
  margin-left:335px;
  font-size:20px;

}

#manager-header .iconfont {
  display:block;
  margin-top:10px;
  margin-left:90px;
  font-size:45px;
  margin-bottom:20px;
}

#select {
  position: absolute;
  top:150px;
  left:170px;
  width: 230px;
  height: 400px;
  border: 2px solid #000000;
  padding-left:20px;
  padding-top:20px;
  display:flex;
  flex-direction: column;
    animation: btn 3s alternate forwards;
}

#select #select-title {
  font-size:22px;
  margin-bottom:20px;
  text-align: center;
}

#select-item {
  margin-bottom:30px;
  font-size:20px;
  width: 190px;
  display:flex;
  flex-direction: column;
  
}

input {
  margin-top:20px;
  padding-left:30px;
  width:99%;
  height:40px;
  border-style: none;
  font-size:16px;
  background-color: #f6f6f6;
  border-radius: 10px;
  transition: all 0.3s;
}

input:focus {
box-shadow:#000000 0px 0px 10px;
}


#select-buttom {
  width: 180px;
  height: 80px;
  display:flex;
  flex-direction: column;
  justify-content: space-between;

}

#select-buttom button {
  border-radius: 10px;
  width: 99%;
  height: 35px;
  border: 3px solid #000;
  transition: all 0.3s;
}

#select-buttom button:hover {
  margin-top:-2px;
  background-color: rgb(137, 214, 245);
  box-shadow:#fffefe 0px 0px 10px;
}

#insert {
  position: absolute;
  top:150px;
  right:30px;
  width: 350px;
  height: 400px;
  display:flex;
  flex-direction: column;
  border: 2px solid #000;
  text-align: center;
  font-size:18px;
  animation: btn 3s alternate forwards;
}

#insert #insert-title {
  margin-top:20px;
  font-size:20px;
  margin-bottom: 10px;
}

#insert input{
  width: 70%;
  margin-bottom: 10px;
}

#insert button {
  margin-top:10px;
  width: 50%;
  height: 40px;
  margin-left:25%;
 border-radius: 20px;
 border: 2px solid #000;
 transition: all 0.3s;
}

#insert button:hover {
  margin-top:7px;
  box-shadow: #898585 0px 0px 10px ;
  
}

.test {
  top:80px;
  left:1060px;
  color:rgb(0, 0, 0);
  font-size:22px;
}

#manager-table {
  position: absolute;
  top:280px;
  left:50%;
  transform: translate(-50%);
  width: 440px;
  text-align: center;
}

#manager-table span {
  display:inline-block;
  margin-bottom:10px;
  font-size:18px;
}

#manager-table table {
  display:block;
  width: 100%;
  height: 204px;
  overflow: auto;
  border-collapse: collapse;
}

#manager-table table td {
  border: 1px solid #ffffff;
  height: 50px;
  font-size: 18px;
}

#manager-table table td:nth-child(4) span {
  margin-top:10px;
  text-align: center;
  line-height: 30px;
  display:inline-block;
  width: 60px;
  height: 30px;
  background-color: red;
  color:black;
  cursor: pointer;
}

#manager-table table td:nth-child(1) {
  width: 60px;
}

#manager-table table td:nth-child(2) {
  width: 140px;
}

#manager-table table td:nth-child(3) {
  width: 130px;
}

#manager-table table td:nth-child(4) {
  width: 100px;
}

@keyframes btn {
  0%{
    transform: translateY(600px);
  }
  100% {
    transform: translateY(0px);
  }
}


</style>

