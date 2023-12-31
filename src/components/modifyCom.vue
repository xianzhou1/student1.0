<template>

  <div id="modify">
    <div class="title">
      <i class="iconfont icon-houtai-1"></i>
      <span>修改学生信息</span>
    </div>
    <div class="modifyMes">
      <div class="item">
        请输入学生姓名: <input type="text" placeholder="请输入学生姓名" v-model.lazy.trim="student.stuName">
      </div>
      <div class="item">
        请输入学生ID: <input type="text" disabled placeholder="请输入学生学号" v-model.lazy.trim="student.stuId">
      </div>
      <div class="item">
        请输入学生学院: <input type="text" placeholder="请输入所在学院" v-model.lazy.trim="student.stuAcd">
      </div>
      <div class="item">
        请输入所在班级: <input type="text" :disabled="stuClsShow" placeholder="请输入所在班级" v-model.lazy.trim="student.stuClass">
      </div>
      <div class="item">
        请输入学生年龄: <input type="text"  placeholder="请输入学生年龄" v-model.lazy.trim="student.age">
      </div>
      <div class="item">
        请输入学生班级: <input type="text" :disabled="stuClsShow" placeholder="请输入学生班级" v-model.lazy.trim="student.nomClass">
      </div>
    </div>
    <button @click="submitStudent">提交修改</button>
  </div>
</template>

<script>

export default {
  data() {
    return {
      student: {},//学生信息
      stuClsShow:false,

    }
  },
  props: {
    id:{},
    stu: {
    },
  },
  mounted() {
    this.student = this.stu
    if (this.id == "普通") {
      this.stuClsShow = true
    }
    console.log(this.id+this.stuClsShow)
  },
  method: {
    //提交学生信息
    submitStudent() {
      console.log(this.tmp);
      if (this.student.stuName == "" || this.student.stuAcd == "" || this.student.stuClass == "" || this.student.nomClass == "") {
        this.logShow(this.tmp, "不得为空!", "iconfont icon-delete-filling")
      } else {
           this.$axios({
          method: 'POST',
          url: '/updataNew',
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
      }
    },


  },

}

</script>

<style scoped>

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
  width: 450px;
  height: 300px;
  display:flex;
  flex-direction: column;
  justify-content:space-between;
  
}

.modifyMes .item {
  display:flex;
  justify-content: space-between;
  font-size:26px;

}

#modify button {
  margin-left:210px;
  margin-top:30px;
  width: 120px;
  height: 40px;
  font-size:20px
}
.modifyMes input {
  font-size:20px;
}


</style>