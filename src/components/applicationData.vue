<template>
  <div v-if="modifyCom" id="modifyModel">
    <component is="MODIFY" :id="localMes.id" :stu="modifyStudent"></component>
  </div>

  <div class="application">
    <div class="explain">
      当前权限为:&nbsp;&nbsp;{{ this.localMes.id }}
    </div>
    <div id="del" v-if="deleteBtnShow">
      <div class="deleteBtn" @click="deleteSome">
        <span class="iconfont icon-ashbin"></span>
          批量删除
      </div>
    </div>
    <div class="table">
       <thead>
          <td><input type="checkbox" ref="disabledAll" @click="allChecked"></td>
          <td>学生姓名</td>
          <td>学生学号</td>
          <td>所在学院</td>
          <td>所在班级</td>
          <td>年龄</td>
          <td>学生班级</td>
          <td>操作</td>
        </thead>
      <table>
        <tr v-for="(item, index) of stuList" :key="index">
          <td><input type="checkbox" v-show="powerDelete" ref="disabledSingly" :checked="crotroCheck" @click="singlyCheck(index)"></td>
          <td>{{ item.stuName }}</td>
          <td>{{ item.stuId }}</td>
          <td>{{ item.stuAcd }}</td>
          <td>{{ item.stuClass }}</td>
          <td>{{ item.age }}</td>
          <td>{{ item.nomClass }}</td>
          <td>
            <span @click="modifyStu(item)">修改</span>
            <span @click="deleteStudent(item)">删除</span>
          </td>
        </tr>
      </table>
    </div>
  </div>

</template>

<script>
import MODIFY from './modifyCom.vue'

export default {
  data() {
    return {
      localMes: {},//接收信息
      stuList: [],//渲染的数据
      crotroCheck: false,//是否全选
      delArray: [],//批量删除的数组
      deleteBtnShow: false,//批量删除的按钮
      powerDelete: false,//删除权限限制
      modifyCom: false,//修改学生信息
      modifyStudent: {},//要修改的学生信息
      refObj: {},//refs对象挂载时获取
      sureDelete: false,//确认删除

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
    if (this.localMes.id == "普通") {
      this.$axios({
        method: 'POST',
        url: '/selectAll',
        data: {
          name: "",
          id: "",
          acd: "",
          sClass: this.localMes.userName
        }
      }).then(res => {
        this.stuList = res.data.data
      })
    } else {
      this.getStudentData()
    }
  },
  mounted() {
    this.refObj = this.$refs
    if (this.localMes.id == "超级管理员") {
      this.deleteBtnShow = true
      this.refObj.disabledAll.disabled = false
      this.powerDelete = true
    }
  },
  methods: {
    //组件位于创建生命周期时用于获取数据
    getStudentData() {
      if (this.localMes.id != "普通") {
        this.$axios({
          method: 'GET',
          url: '/selectShow'
        }).then(res => {
          this.stuList = res.data.data
        })
      } else {
        this.$axios({
          method: 'POST',
          url: '/selectAll',
          data: {
            name: "",
            id: "",
            acd: "",
            sClass: this.localMes.userName
          }
        }).then(res => {
          this.stuList = res.data.data
        })
      }
    },
    //是否全选事件
    allChecked() {
      this.crotroCheck = this.crotroCheck ? false : true
      if (this.crotroCheck) {
        for (let i = 0; i < this.stuList.length; i++){
          if (this.delArray.indexOf(this.stuList[i].stuId) == -1) {
            this.delArray.push(this.stuList[i].stuId)
          }
        }
      } else {
        while (this.delArray.length > 0) {
          this.delArray.pop()
        }
      }
    },
    //单项选择
    singlyCheck(i) {
      let tmp = this.delArray.indexOf(this.stuList[i].stuId)
      if (tmp == -1) {
        this.delArray.push(this.stuList[i].stuId)
      } else {
        this.delArray.splice(tmp,1)
      }
    },
    //修改学生
    modifyStu(item) {
      this.modifyStudent = item
      this.modifyCom = this.modifyCom ? false : true
    },
    //删除单个学生
    deleteStudent(item) {
      if (this.localMes.id == "超级管理员") {
        this.sureDelete = true
        this.Realdel([item.stuId])
      }
    },
    //批量删除
    deleteSome() {
      if (this.localMes.id == "超级管理员" && this.powerDelete) {
        this.sureDelete = true
        this.Realdel(this.delArray)
      }
    },
    Realdel(itemArray) {
      for (let i = 0; i < itemArray.length; i++){
        this.$axios({
          method: 'POST',
          url: '/deleteAny',
          data: {
            ids: itemArray[i]
          }
        }).then(res => {
          console.log(res);
        })
        this.re()
        let t = this.delArray.indexOf(itemArray[i])
        if (t != -1) {
          this.delArray.splice(t,1)
        }
      }
    },
    re() {
      for (let i = 0; i < 6; i++) {
        this.getStudentData()
      }
    }
    

  },
  components: {
    MODIFY,

  }

}

</script>

<style scoped>

#modifyModel {
  position: absolute;
  top:80px;
  left:50%;
  transform: translate(-50%);
  width: 570px;
  height: 490px;
  background-color: rgb(99, 152, 148);
  border-radius: 25px;
  z-index:2;
  
}

.application {
  position: absolute;
  left:170px;
}

.application .explain {
  width: 300px;
  position:absolute;
  top:20px;
  left:360px;
  font-size:24px;
}
.application #del {
  position: absolute;
  top:60px;
  left:40px;
  width: 170px;
  height: 50px;

  overflow: hidden;
  margin-bottom: 10px;
  
}

.application .deleteBtn {
  width: 170px;
  height: 50px;
  border-radius: 30px;
  text-align: center;
  line-height: 50px;
  font-size:20px;
  cursor: pointer;
  background-color: #dcbcbc;
  animation: delbtn 2s alternate forwards;
  z-index:0.5;
  transition: all 0.3s;
}

.application .deleteBtn:hover {
  font-weight: 700;
  font-size:22px;
  color: rgb(17, 33, 73);
}

.application .deleteBtn .iconfont {
  font-size:24px;
  margin-right:5px;
  color:red;
}

table {
  position: absolute;
  top:170px;
  width: 1090px;
  height: 265px;
  border-collapse: collapse;
  overflow: auto;
  display: flex;
  flex-direction: column;
  z-index:1;
}

.table tr {

  transition: all 0.7s;
}
.table thead {
  position:fixed;
  top:180px;
}
.table thead td:nth-child(2) {
    width: 97px;
}
.table thead td:nth-child(3){
    width: 119px;
}
.table thead td:nth-child(4){
    width: 193px;
}
.table thead td:nth-child(5){
    width: 195px;
}
.table thead td:nth-child(6){
    width: 59px;
}
.table thead td:nth-child(7){
    width: 175px;
}





.table tr:hover {
  background-color: #d2cfcf;
}

.table td {
  font-size:18px;
  font-weight: 700;
  height: 50px;
  text-align: center;
  border: 1px solid #000;
 
}

.table td:nth-child(1) {
  padding-top:7px;
  width: 40px;
}

.table input {
  width: 20px;
  height: 20px;
}

.table td:nth-child(2) {
  width: 100px;
}

.table td:nth-child(3) {
  width:120px;
}
.table td:nth-child(4) {
  width:200px;
}

.table td:nth-child(5) {
  width: 200px;
}

.table td:nth-child(6) {
  width: 60px;
}

.table td:nth-child(7) {
  width: 180px;
}

.table td:nth-child(8) {
  width: 200px;
}

.table td:nth-child(8) span {
  display:inline-block;
  width: 60px;
  height: 30px;
  text-align: center;
  line-height: 30px;
  cursor: pointer;
}

.table td:nth-child(8) span:nth-child(1) {
  margin-left:-8px;
  margin-right:16px;
  background-color: #ead3ae;
  transition: all 0.3s;
}

.table td:nth-child(8) span:hover {
   box-shadow: 0px 0px 10px 0px rgb(30, 52, 106);
}

.table td:nth-child(8) span:nth-child(2) {
  margin-right: -15px;
  background-color: #cc8c8c;
}



@keyframes tableTr {
  0% {
    transform: translate(400px);
  }
  100% {
    transform: translate(0px);
  }
}

@keyframes delbtn {
  0%{
    transform: translateY(200px);
    z-index:0.5;
  }
  100% {
    transform: translateY(0px);
    z-index: 0.5;
  }
}



</style>