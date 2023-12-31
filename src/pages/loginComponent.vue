<template>
  <div class="img">
    <img src="../images/素材1.jpg" alt="">
  </div>

  <div class="test"
       ref="loginMes">
    {{ logmes }}<span :class="logIconfont"></span>
  </div>

  <div class="body">
    <div id="login-box">
      <h2>{{ title }}</h2>
      <div class="form">
      <!-- 输入用户名 -->
        <div class="item">
          <i class="iconfont icon-user-filling"></i>
          <input type="text"
                 placeholder="用户名"
                 v-model="username">
        </div>
        <!-- 输入密码 -->
        <div class="item">
          <i :class="passicon"></i>
          <input :type='psw'
                 placeholder="请输入密码"
                 v-model="password"
                 @focus="passLevel"><!-- 密码焦点事件-->
          <p :class="eyes" @click="showPassword"></p>
        </div>
        <!-- 注册时再次输入密码 -->
        <div class="item" v-show="logOrSign">
          <i :class="passicon"></i>
          <input :type='pswsign'
                 placeholder="请再次输入密码"
                 v-model="signpassword"
                 @focus="passLevel">
          <p :class="eyessign" @click="showSignPassword"></p>
        </div>
        <!-- 输入验证码 -->
        <div class="item">
          <i class="identi iconfont icon-security"></i>
          <input type="text"
                 placeholder="验证码"
                 v-model="identity" ref="eyes" >
        
          <div class="code" @click="refreshCode">
            <SIdentify :identifyCode="identifyCode"></SIdentify>
          </div>       
        </div>
        <div>
          <button @click="login">{{ title }}</button>
        </div>
        <div class="login-or-signin">
          <span @click="Log_In">登录</span>
          <span @click="Sign_In">注册</span>
        </div>
      </div>
    </div>
  </div>

</template>


<script>
import SIdentify from "../components/sIdentify.vue";

export default{
  data(){
    return {
      title: "LogIn",
      identifyCode: "",
	    // 验证码从identifyCodes中随机选取4位"
	    identifyCodes: "1234567890qwertyuiopasdfghjklzxcvbnm",
      username:"",//用户名
      password:"",//密码
      flag: 0,//是否有此人
      passicon: "",//切换图标
      identity: "",//输入的验证码
      logmes: "",//登录信息提示
      logIconfont: "",//
      psw: "password",//密码不显示
      eyes: "eyes iconfont icon-eye-close",
      logOrSign: false,//注册还是登录
      signpassword: "",//注册再次输入密码
      eyessign: "eyes iconfont icon-eye-close",
      pswsign: "password",//注册是再次输入密码的input属性
      len: 0,//判断注册时的用户名是否存在
      stulen: 0,
      userId: "普通",//身份默认普通

    }
  },
  updated(){
    this.$axios({
        method:"POST",
        url: "/isexiest",
        data: {
          name: this.username,
          password: this.password,
        }
      }).then(res=>{
        this.flag = res.data.data
      })
    this.$axios({
        method: 'POST',
        url: '/selectUserName',
        data: {
          userName: this.username
        }
      }).then(res => {
        this.len = res.data.data.length
        this.userId = res.data.data
      })
    if(this.password.length > 5 && this.passLevel()){
      this.passicon = "iconfont icon-smile-filling"
    }else if(this.password.length >= 5){
      this.passicon = "iconfont icon-meh-filling"
    }else if(this.password.length < 5){
      this.passicon = "iconfont icon-cry-filling"
    }
    this.studentLogIn()
  },
  mounted() {
	// 初始化验证码
    this.refreshCode();
  },
  methods:{
    // 生成随机数
    randomNum(min, max) {
        // max = max + 1
        return Math.floor(Math.random() * (max-min));
    },
    // 更新验证码
    refreshCode() {
        this.identifyCode = "";
        this.makeCode(this.identifyCodes, 4);
        console.log('当前验证码==',this.identifyCode);
    },
    // 随机生成验证码字符串
    makeCode(data,len) {
        for (let i = 0; i < len; i++) {
          this.identifyCode += this.identifyCodes[this.randomNum(0, this.identifyCodes.length)]
        }
    },
    //登录检测
    login(){
      const tmp = this.$refs.loginMes.style
      if (this.title == "LogIn") {
        // 验证密码和账号是否正确
        if ((this.flag > 0 && this.identity == this.identifyCode)) {
          this.$router.push({
            name: 'root',
            params: {
                userName: this.userId[0].userName,
                id: this.userId[0].id
            }
          })
          return true
        }
        if (this.stulen > 0) {
          this.$router.push({
            name: 'root',
            params: {
                userName: this.username,
                id: "普通",
            }
          })
          return true
        } else if (this.identity != this.identifyCode) {
          this.logShow(tmp, "验证码错误", "iconfont icon-delete-filling")
        } else {
          this.logShow(tmp, "该用户不存在", "iconfont icon-delete-filling")
        }
      } else if(this.title == "SignIn"){
        //注册检查
        //先验证俩次密码与一样
        this.signinChecked(tmp) 
      }
      this.refreshCode()

    },
    //注册检测
    signinChecked(tmp) {
       if (this.signpassword.length >= 5 && this.password == this.signpassword && this.identity == this.identifyCode && this.len == 0) {
          this.logOrSign = false
          this.title = "LogIn"
          this.insertUser(this.username,this.password)
          this.logShow(tmp, "注册成功!", "iconfont icon-success-filling")
        } else if (this.password != this.signpassword){
          this.logShow(tmp, "密码不一致!", "iconfont icon-delete-filling")
        } else if (this.identity != this.identifyCode) {
          this.logShow(tmp, "验证码错误!", "iconfont icon-delete-filling")
        } else if (this.len != 0) {
          this.logShow(tmp, "用户名已存在", "iconfont icon-delete-filling")
        } else if(this.signpassword.length < 5){
          this.logShow(tmp, "密码至少五位!", "iconfont icon-delete-filling")
        }
    },
    //是否是学生登录
    studentLogIn() {
      this.$axios({
        method: 'POST',
        url: '/selectStudent',
        data: {
          stuClass: this.username,
          stuId: this.password
        }
      }).then(res => {
        this.stulen = res.data.data.length
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
    //密码等级
    passLevel(){
      let flag1 = false
      let flag2 = false
      for(let i = 0; i < this.password.length; i++){
        let t = this.password[i].charCodeAt()
        if (t>=48 && t <=57) {
          flag1 = true
        }
        if (t>=65 && t <=90){
          flag2 = true 
        }
          if (t>=97 && t <=122) { 
          flag2 = true 
        }
      }
      if(flag1 && flag2){
        return true
      }else{
        return false
      }
    },
    //显示密码
    showPassword(){
      this.psw = this.psw == "password" ? "text" : "password"
      this.eyes = this.eyes == "eyes iconfont icon-eye-close" ? "eyes iconfont icon-browse" : "eyes iconfont icon-eye-close"
    },
    //显示再次输入的密码
    showSignPassword() {
      this.pswsign = this.pswsign == "password" ? "text" : "password"
      this.eyessign = this.eyessign == "eyes iconfont icon-eye-close" ? "eyes iconfont icon-browse" : "eyes iconfont icon-eye-close"
    },
    Log_In() {
      this.title = "LogIn"
      this.logOrSign = false
      this.refreshCode()
    },
    Sign_In() {
      this.title = "SignIn"
      this.logOrSign = true
      this.refreshCode()
    },
    //添加人员
    insertUser(name,passwd) {
      this.$axios({
        method: "POST",
        url: "/insertUser",
        data: {
          userName: name,
          password: passwd,
          id: "普通"
        }
      }).then(res => {
        console.log(res);
      })
    },

  },
  components: {
    SIdentify,
  }
}

</script>

<style scoped>

.login-or-signin {
  position:absolute;
  bottom:-40px;
  left:27%;
  width: 200px;
  height: 20px;
  display:flex;
  justify-content: space-between;
  font-size: 20px;
  font-weight: 700;
  color:chocolate;
}

.login-or-signin span {
  cursor: pointer;

}

.login-or-signin span:hover {
  color:#fff;
}

.item .eyes {
  position:absolute;
  right:-70px;
  top:6px;
  cursor: pointer;

}

img {
  width: 1280px;
  height: 581.2px;
}
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

.body {
  position:absolute;
  top:50%;
  left:50%;
  transform: translate(-50%,-50%);
  margin-top: -50px;

}

.identi {
  display: inline-block;
  font-size:25px;
  margin-right:10px;
}

#login-box {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  width: 450px;
  height: 400px;
  margin: 0 auto;
  margin-top: 15%;
  text-align: center;
  background: #00000060;
  padding: 20px 50px;
}
#login-box h2 {
  margin:0;
  padding:0;
  color: #fff;
  font-size:40px;
}

#login-box .form {
  position:absolute;
  bottom: 50px;
  display:flex;
  justify-content: space-evenly;
  flex-direction: column;
}

#login-box .form .item {
  
  position:relative;
  margin-left: 40px;
  width: 200px;
  height: 40px;
  display: flex;
  justify-content: space-between;
  margin-bottom: 15px;
}

#login-box .form .item .iconfont {
  margin-right: 10px;
  font-size: 30px;
}

#login-box .form .item i {
  margin-top: 8px;
  font-size: 18px;
  color: #fff;
}
#login-box .form .item input {
 
  width: 220px;
  font-size: 18px;
  border: 0;
  border-bottom: 2px solid #fff;
  padding: 5px 10px;
  background: #ffffff00;
  color: #fff;
}
#login-box button {
  margin-top: 20px;
  margin-left:80px;
  width: 190px;
  height: 30px;
  font-size: 20px;
  font-weight: 700;
  color: #fff;
  background-image: linear-gradient(to right, #74ebd5 0%, #9face6 100%);
  border: 0;
  border-radius: 15px;
}
</style>