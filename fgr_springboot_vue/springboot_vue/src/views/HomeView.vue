<template>
  <div>
    <!--    新增按钮-->
    <div style="margin: 10px 0">
      <el-button type="primary" @click="add">新增</el-button>
      <el-button>其它</el-button>
    </div>
    <!-- 搜索-->
    <div style="margin: 10px 0">
      <el-input v-model="search" placeholder=" 请 输 入 家 居 名 " style="width:
30%"></el-input>
      <el-button style="margin-left: 10px" type="primary" @click="list">查询</el-button>
    </div>
    <!-- 去掉字段的 width, 让其自适应 -->
    <el-table :data="tableData" stripe style="width: 90%">
      <el-table-column
          prop="id"
          label="ID" sortable
      >
      </el-table-column>
      <el-table-column
          prop="name"
          label="家居名" >
      </el-table-column>
      <el-table-column
          prop="maker"
          label="厂家">
      </el-table-column>
      <el-table-column
          prop="price"
          label="价格">
      </el-table-column>
      <el-table-column
          prop="sales"
          label="销量">
      </el-table-column>
      <el-table-column
          prop="stock"
          label="库存">
      </el-table-column>


      <el-table-column fixed="right" label="操作" width="100">
        <template #default="scope">
          <el-button @click="handleEdit(scope.row)" type="text">编辑</el-button>
<!--          引入一个确认框-->
<!--          <el-button type="text">删除</el-button>-->
          <el-popconfirm
            title="确定删除吗？" @confirm="handleDel(scope.row.id)">
            <template #reference>
              <el-button size="mini" type="text">删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

<!--    引入分页组件，可以根据需要进行定制-->
    <div style="margin: 10px 0">
      <el-pagination
          @size-change="handlePageSizeChange"
          @current-change="handleCurrentChange"
          :current-page="currentPage"
          :page-sizes="[5,10,15]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

<!--    说明:-->
<!--    1. el-dialog ：v-model="dialogVisible" 表示对话框, 和 dialogVisible 变量双向-->
<!--    绑定,控制是否显示对话框-->
<!--    2. el-form :model="form" 表示表单数据和 form 数据变量双向绑定-->
<!--    3. el-input v-model="form.name" 表示表单的 input 控件，名字为 name 需要和-->
<!--    后台 Javabean 属性一致-->
    <el-dialog title="提示" v-model="dialogVisible" width="30%">
      <el-form :model="form" :rules="rules" ref="form" label-width="120px">
<!--        prop="name" 表示和rules的哪个规则关联-->
        <el-form-item label="家居名" prop="name">
          <el-input v-model="form.name" style="width: 50%"></el-input>
<!--          显示返回的后端校验信息-->
          {{validMsg.name}}
        </el-form-item>
        <el-form-item label="厂商" prop="maker">
          <el-input v-model="form.maker" style="width: 50%"></el-input>
          {{validMsg.maker}}
        </el-form-item>
        <el-form-item label="价格" prop="price">
          <el-input v-model="form.price" style="width: 50%"></el-input>
          {{validMsg.price}}
        </el-form-item>
        <el-form-item label="销量" prop="sales">
          <el-input v-model="form.sales" style="width: 50%"></el-input>
          {{validMsg.sales}}
        </el-form-item>
        <el-form-item label="库存" prop="stock">
          <el-input v-model="form.stock" style="width: 50%"></el-input>
          {{validMsg.stock}}
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
      <el-button @click="dialogVisible = false">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
// @ is an alias to /src
// 引入request组件/对象
import request from "@/utils/request";

export default {
  name: 'HomeView',
  components: false,
  data() {//数据
    return {
      validMsg:{},//后端校验信息
      currentPage: 1,//当前页
      pageSize: 4, //每页显示记录数
      total: 10,//一共有多少页记录 表中对应的记录
      dialogVisible: false,
      form: {},
      search: '',
      tableData: [],
      rules: {//提交表单的验证规则
        name: [
          {required: true, message: '请输入称家居名', trigger: 'blur'}
        ],maker: [
          {required: true, message: '请输入称制造商', trigger: 'blur'}
        ],price: [
          {required: true, message: '请输入价格', trigger: 'blur'},
          {pattern: /^(([1-9]\d*)|(0))(\.\d+)?$/, message: '请输入数字', trigger: 'blur'}
        ],sales: [
          {required: true, message: '请输入销量', trigger: 'blur'},
          {pattern: /^(([1-9]\d*)|(0))$/, message: '请输入整数', trigger: 'blur'}
        ],stock: [
          {required: true, message: '请输入库存', trigger: 'blur'},
          {pattern: /^(([1-9]\d*)|(0))$/, message: '请输入整数', trigger: 'blur'}
        ]
      }
    }
  },
  created() {//完成表格数据的使用
    this.list()
  },
  methods: {//方法区
    //处理pageSize的变化
    handlePageSizeChange(pageSize){
      this.pageSize = pageSize
      this.list()//刷新列表
    },
    handleCurrentChange(currentPage){
      //处理currentPage的变化
      // alert(currentPage)
      this.currentPage = currentPage
      //刷新列表
      this.list()
    },
    //处理删除
    handleDel(id) {
      //使用request发出请求，删除当前的家居
      request.delete("/api/del/" + id).then(
          res => {
            if (res.code == "200") {//删除成功
              //需要提示成功的消息框
              this.$message({
                type: "success",
                message: "删除成功"
              })
            } else {//删除失败
              //需要提示失败的消息框
              this.$message({
                type: "error",
                message: res.msg  //也可以res.msg来提示
              })
            }
            //刷新家居列表
            this.list()
          }
      )
    },
    list (){//显示家居信息
      // request.post("/api/furns").then(res => {
      //   //返回的数据和tableData绑定
      //   // console.log(res)
      //   this.tableData = res.data
      // })
      //分页查询 带条件
      request.get("/api/furnsBySearchPage2" ,{
        params:{
          pageNum:this.currentPage,
          pageSize:this.pageSize,
          search:this.search
        }
      } ).then(res => {
        console.log("res-",res)
        this.tableData = res.data.records
        //修改total
        this.total = res.data.total
      })
    },
    save() {
      if (this.form.id){//执行修改 null为false
        //request.put/get/post/... 都是ajax异步请求 发出的请求和接下来的代码会分布进行
        request.put("/api/update",this.form).then(
            res => {
              if (res.code == "200") {//修改成功
                //需要提示成功的消息框
                this.$message({
                  type: "success",
                  message: "更新成功"
                })
              } else {//修改失败
                //需要提示失败的消息框
                this.$message({
                  type: "error",
                  message: "更新失败"//也可以res.msg来提示
                })
              }
              //刷新家居列表
              this.list()
              //关闭对话框
              this.dialogVisible = false
            }
        )
      }else{//执行添加
        // request.post("/api/save",this.form).then(
        //     res => {
        //       // console.log("res=",res)
        //       this.dialogVisible = false
        //       this.list() //刷新表格列表
        //     }
        // )
        // 添加时与白蛋验证，如果验证没有通过，就不去提交
        this.$refs['form'].validate(valid => {
          //为了配合后端校验  给前端放行 测试后修改
          // alert(valid)
          // valid = true
          if (valid){//验证通过
            request.post("/api/save",this.form).then(
                res => {
                  if (res.code === "200"){//添加成功
                    // console.log("res=",res)
                    this.dialogVisible = false
                    this.list() //刷新表格列表
                  }else if (res.code === "400"){//后端校验失败
                    //取出返回的校验信息
                    this.validMsg.name = res.data.name
                    this.validMsg.maker = res.data.maker
                    this.validMsg.price = res.data.price
                    this.validMsg.sales = res.data.sales
                    this.validMsg.stock = res.data.stock
                  }
                }
            )
          }else{
            this.$message({
              type: "error",
              message: "验证未通过，不提交"
            })
            return false;
          }
        })

      }
    },
    handleEdit(row) {//先写一个方法
      // //方法一 将数据转换成字符串在转换成json对象 点击的表格当前行数据和弹出框的数据是深拷贝，不会双向绑定
      // console.log("row--",JSON.parse(JSON.stringify(row)))
      // //把得到的数据和form绑定，显示到对话框
      // this.form = JSON.parse(JSON.stringify(row))
      // this.dialogVisible = true
      //方法二 去数据库请求获取实时信息
      request.get("/api/furn/" + row.id).then(
          res => {
            if (res.code === "200"){//查询成功
              this.form = res.data
            }else {
              //需要提示成功的消息框
              this.$message({
                type: "error",
                message: res.msg
              })
            }
          }
      )
      this.dialogVisible = true;//显示对话框
    },
    //add方法 显示添加的对话框
    add() {
      this.dialogVisible = true
      //每次显示对话框时，清空表单
      this.form = {}
      //上次表单验证的信息清空
      this.$refs['form'].resetFields()
      //清空上次后端返回的校验信息
      this.validMsg = {}
    }
  }
}
</script>
