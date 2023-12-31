<template>
  
  <div id="insert">
    <div class="test"
       ref="loginMes">
    {{ logmes }}<span :class="logIconfont"></span>
    </div>
    <div id="insert-body">
    <div class="title">
      <i class="iconfont icon-houtai-1"></i>
      <span>添加学生信息</span>
    </div>
    <div class="modifyMes">
      <div class="item">
        请输入学生姓名: <input type="text" placeholder="请输入学生姓名" v-model.lazy.trim="student.stuName">
      </div>
      <div class="item">
        请输入学生ID: <input type="text" placeholder="请输入学生学号" v-model.lazy.trim="student.stuId">
      </div>
      <div class="item">
        请输入学生学院: <input type="text" placeholder="请输入所在学院" v-model.lazy.trim="student.stuAcd">
      </div>
      <div class="item">
        请输入所在班级: <input type="text" placeholder="请输入所在班级" v-model.lazy.trim="student.stuClass">
      </div>
      <div class="item">
        请输入学生年龄: <input type="text" placeholder="请输入学生年龄" v-model.lazy.trim="student.age">
      </div>
      <div class="item">
        请输入学生班级: <input type="text" placeholder="请输入学生班级" v-model.lazy.trim="student.nomClass">
      </div>
    </div>
      <button @click="submitInsert">提交修改</button>
    </div>
  </div>

</template>

<script>

export default {
  data() {
    return {
      localMes: {},
      currentStudb: [],
      student: {
        stuName: "",
        stuId: "",
        stuAcd: "",
        stuClass: "",
        age: 18,
        nomClass:""
      },
      logIconfont: "",
      logmes:"",
      
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
    this.$axios({
      method: 'GET',
      url: '/selectShow'
    }).then(res => {
      this.currentStudb = res.data.data
    })
  },
  methods: {
    logShow(tmp, mes, iconf) {
      tmp.opacity = '1'
      this.logIconfont = iconf
      this.logmes = mes
      let timeout = 0
      let threadout = setInterval(() => {
        if (timeout >= 1) {
          tmp.opacity = '0'
          clearInterval(threadout)
        }
        timeout += 1
      }, 1000)
    },
    submitInsert() {
      const tmp = this.$refs.loginMes.style
      console.log(tmp);
      if (this.student.stuName == "" || this.student.stuId == "" || this.student.stuAcd == "" || this.student.stuClass == "" || this.student.nomClass == "") {
        this.logShow(tmp, "不得为空!", "iconfont icon-delete-filling")
      } else if (this.Numbers(this.student.stuId)) {
        this.logShow(tmp, "学号为纯数字!", "iconfont icon-delete-filling")
      } else if (this.stuId(this.student.stuId)) {
        this.logShow(tmp, "学号不能重复!", "iconfont icon-delete-filling")
      } else {
        this.$axios({
          method: 'POST',
          url: '/insertNew',
          data: {
            stuName: this.student.stuName,
            stuId: this.student.stuId,
            stuAcd: this.student.stuAcd,
            stuClass: this.student.stuClass,
            age: this.student.age,
            nomClass: this.student.nomClass
          }
        }).then(res => {
          console.log(res)
        })
        this.logShow(tmp, "添加成功,请返回查看", "iconfont icon-success-filling")
      }
    },
    Numbers(id) {
      const str = parseInt(id)
      if (id == str) {
        return false
      } else {
        return true
      }
    },
    stuId(id) {
      for (let i = 0; i < this.currentStudb; i++) {
        if (id == this.currentStudb[i].stuId) {
          return true
        }
      }
      return false
    }
  },


}

</script>

<style scoped>


.test {
  position: absolute;
  top: 180px;
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


#insert {
  margin-top:30px;
  margin-left: 275px;
}

#modify {
  margin-left:35px;
  
}

.title {
  margin-left:-35px;
  margin-top:10px;
  text-align: center;
  font-size:24px;
  margin-bottom:30px;
  font-weight: 700;
   vertical-align: middle;
}
.title .iconfont {
  margin-right: 10px;
  font-size:30px;
 
}

.modifyMes {
  margin-left:20px;
  width: 750px;
  height: 300px;
  display:flex;
  flex-direction: column;
  justify-content:space-between;
  
}

.modifyMes .item {
  margin-left:70px;
  display:flex;
  width: 600px;
  justify-content: space-between;
  font-size:26px;

}

#insert-body button {
  margin-left:250px;
  margin-top:60px;
  width: 220px;
  height: 50px;
  font-size:24px
}
.modifyMes input {
  padding-left:20px;
  font-size:20px;
}

</style>