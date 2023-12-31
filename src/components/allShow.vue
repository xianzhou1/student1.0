<template>
  <div class="select">
    <div class="title">
      查询界面
    </div>
    <div class="select-body"> 
      <div class="item">
        <span>学生姓名:</span>
        <input type="text" placeholder="请输入姓名" v-model.lazy.trim="stu.name">
      </div>
      <div class="item">
        <span>学生学号:</span>
        <input type="text" placeholder="请输入学号" v-model.lazy.trim="stu.id">
      </div>
      <div class="item">
        <span>所在学院:</span>
        <input type="text" :disabled="disabled" placeholder="请输入学生学院" v-model.lazy.trim="stu.acd">
      </div>
      <div class="item">
        <span>所在班级:</span>
        <input type="text" :disabled="disabled" placeholder="请输入学生班级" v-model.lazy.trim="stu.cls">
      </div>
    </div>
    <div class="table">
      <table>
        <thead>
          <td>学生姓名</td>
          <td>学生学号</td>
          <td>所在学院</td>
          <td>所在班级</td>
          <td>年龄</td>
          <td>学生班级</td>
        </thead>
        <tr v-for="(item, index) of stuList" :key="index">
          <td>{{ item.stuName }}</td>
          <td>{{ item.stuId }}</td>
          <td>{{ item.stuAcd }}</td>
          <td>{{ item.stuClass }}</td>
          <td>{{ item.age }}</td>
          <td>{{ item.nomClass }}</td>
        </tr>
      </table>
    </div>
    <div class="select-button">
      <button @click="selectSome">查询</button>
      <button @click="selectAll">查询全部</button>
    </div>
  </div>

</template>

<script>

export default {

  data(){
    return {
      localMes: {},
      studentDb: [],
      stuList:[],
      acd: "",
      class: "",
      disabled: false,
      stu: {
        name: "",
        id: "",
        acd: "",
        cls:"",
      }
      
    }
  },
  methods: {
    select() {
      if (this.localMes.id == "普通") {
        this.disabled = true
        this.$axios({
          method: 'POST',
          url: '/selectAll',
          data: {
          name: "",
           id: "",
           acd: "",
           sClass:this.localMes.userName
          }
        }).then(res => {
          this.studentDb = res.data.data
        })
      
      } else {
        this.$axios({
          method: 'GET',
          url:'/selectShow'
        }).then(res => {
          this.studentDb = res.data.data
        })
      }
    },
    selectAll() {
      this.select()
      this.stuList = this.studentDb
    },
    selectSome() {
      this.select()
      if (this.localMes.id == "普通") {
        this.acd = this.studentDb[0].stuAcd
        this.class = this.studentDb[0].stuClass
        this.$axios({
          method: 'POST',
          url: '/selectAll',
          data: {
            name: this.stu.name,
            id: this.stu.id,
            acd: this.acd,
            sClass: this.localMes.userName
          }
        }).then(res => {
          this.stuList = res.data.data
        })
      } else {
        this.$axios({
          method: 'POST',
          url: '/selectAll',
          data: {
            name: this.stu.name,
            id: this.stu.id,
            acd: this.stu.acd,
            sClass: this.stu.cls
          }
        }).then(res => {
          this.stuList = res.data.data
        })
      }
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
  mounted() {
    this.localMes = this.mes
    this.select()
    console.log(this.studentDb)
    console.log(this.class+this.acd);
  },

  
}

</script>

<style scoped>

.select {
  position:absolute;
  left:170px;
   
}

.title {
  margin-left:-80px;
  margin-top: 10px;
  font-size:24px;
  margin-bottom: 10px;
  text-align: center;
}

.select-body {
  display:flex;
  justify-content: space-around;
  height: 80px;
  font-size:22px;
}

.select-body .item {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  
}

.select-body .item input {
  font-size:22px;
  width: 200px;
  padding-left: 10px;
  height: 35px;
}

.select-button {
  margin-left:540px;
  margin-top:20px;
  display:flex;
  width: 500px;
  justify-content: space-around;
  font-size:22px;

}

.select-button button {
  width: 140px;
  height: 40px;
  background-color: #f6f6f6;
  transition: all 0.4;
  text-align: center;
  line-height: 40px;
  cursor: pointer;
}
.select-button button:hover {
  margin-top:-1px;
  box-shadow: #000000 0px 0px 10px ;
}


table {
  margin-top:30px;
  position: absolute;
  top:180px;
  width: 1090px;
  height: 315px;
  border-collapse: collapse;
  overflow: auto;
  display: flex;
  flex-direction: column;
  z-index:1;
}

.table tr {
  transition: all 0.7s;
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
  width: 120px;
}

.table input {
  width: 20px;
  height: 20px;
}

.table td:nth-child(2) {
  width: 150px;
}

.table td:nth-child(3) {
  width:250px;
}
.table td:nth-child(4) {
  width:250px;
}
.table td:nth-child(5) {
  width: 60px;
}

.table td:nth-child(6) {
  width: 210px;
}


</style>