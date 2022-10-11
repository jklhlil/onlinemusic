<template>
  <div>
    <!-- 顶部 -->
    <div class="top">
      <i class="el-icon-s-unfold " style="  margin-left: 10px;">
        <span class="text">首页/评论管理</span>
      </i>
    </div>

    <el-card>

      <!-- 查询 -->
      <el-row>
        <el-form ref="param"  label-width="80px">
          <el-col :span="12">
            <el-form-item >
              <el-input v-model="param.data.content" style="width: 100%;" placeholder="请输入评论过滤器内容">{{param.data.content}}</el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-button type="primary" @click="handleQuery()" style="margin-left:10px">查询</el-button>
            <el-button @click="handleReset('query')">重置</el-button>
          </el-col>
          <el-button type="primary" @click="queryNoCheck()">一键查找需审核的评论</el-button>

        </el-form>
      </el-row>

      <!-- 表单 -->
      <!-- strip行之间斑马纹 -->
      <el-table ref="multipleTable" :data="tableData" stripe height="515px" :default-sort="{prop: 'id', order: 'descending'}" style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column fixed type="selection" width="50">
        </el-table-column>

        <el-table-column sortable label="评论内容" width="800">
          <template slot-scope="scope">
            <!-- 编辑 -->
            <el-input size="mini" v-model="scope.row.content" v-if="scope.row.id===editId"></el-input>
            <span v-else>{{scope.row.content}}</span>
          </template>
        </el-table-column>

        <el-table-column label="是否通过审核" width="120">
          <template slot-scope="scope">
            <el-input size="mini" v-model="scope.row.isCheck" v-if="scope.row.id===editId"></el-input>
            <span v-else>
              <span v-if="scope.row.isCheck===1">
                已通过
              </span>
              <span v-else>
                未通过
              </span>
            </span>
          </template>
        </el-table-column>

        <el-table-column sortable label="评论时间" width="140">
          <template slot-scope="scope">
            <!-- 编辑 -->
            <el-input size="mini" v-model="scope.row.time" v-if="scope.row.id===editId"></el-input>
            <span v-else>{{scope.row.time}}</span>
          </template>
        </el-table-column>

        <el-table-column sortable label="评论者id" width="100" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-input size="mini" v-model="scope.row.consumerId" v-if="scope.row.id===editId"></el-input>
            <span v-else>{{scope.row.consumerId}}</span>
          </template>
        </el-table-column>

        <el-table-column label="被评论的音乐id" width="200" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-input size="mini" v-model="scope.row.musicId" v-if="scope.row.id===editId"></el-input>
            <span v-else>{{scope.row.musicId}}</span>
          </template>
        </el-table-column>

        <!-- fixed="right"固定列 -->
        <el-table-column label="操作" width="150" fixed="right">
          <template slot-scope="scope">
            <span v-if="scope.row.id===editId">
              <el-button size="mini" type="primary" @click="confirmEdit(scope.row)">确认更改</el-button>
            </span>
            <span v-else>
              <el-button size="mini" @click="handleEdit(scope.row,scope.$index)">编辑</el-button>
              <el-button size="mini" type="danger" @click="handleDelete(scope.row,scope.$index)">删除</el-button>
            </span>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-row>

        <div class="batch">
          <el-button size="medium" type="primary" @click="batchCheck()">批量通过审核</el-button>
          <el-button size="medium" @click="toggleSelection()">取消选择</el-button>
        </div>

        <el-pagination class="paging" background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page.sync="page.current" :page-sizes="[5, 10, 15, 20,50,100,200]" :page-size="page.size" layout="total, sizes, prev, pager, next, jumper" :total="page.total">
        </el-pagination>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import { post } from "../../http/axios.js";
import axios from "axios";
import { Dialog, Toast } from "vant";
export default {
  data() {
    return {
      tableData: [
        {
          id: 1,
          content: "评论内容",
          isCheck: 0,
          consumerId: 11,
          musicId: 12,

          adminId: 1,
        },
      ],
      page: {
        current: 1,
        size: 10,
        total: 0,
      },
      // 请求的param
      param: {
        data: { content: "" },
        page: 1,
        pageSize: 10,
      },
      //  请求的路径
      path: "/comment/findByPage",

      editId: -10,
      multipleSelection: [],
    };
  },
  methods: {
    //查询
    handleQuery() {
      this.getData();
      Toast({ message: "查询完毕", duration: 100 });
    },
    // 重置
    handleReset() {
      this.param.data = { content: "" };
      this.path = "/comment/findByPage";
      this.getData();
      Toast({ message: "重置成功", duration: 100 });
    },
    // 编辑
    handleEdit(row, index) {
      this.editId = row.id;
    },
    // 确认更改
    async confirmEdit(row) {
      this.editId = -10;
      let res = await axios.put("/comment/update", row);
      if (res.data.code === 8013) {
        Toast("更改成功");
                this.getData();
      } else {
        Toast("更改失败");
        this.getData();
      }
    },
    // 删除
    async handleDelete(row) {
      let res = await axios.delete("/comment/deleteById/" + row.id);
      if (res.data.code === 8011) {
        Toast("删除成功");
        this.getData();
      } else {
        Toast("删除失败");
      }
    },
    // 多选，保存多选数据
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    //批量通过审核
    async batchCheck() {
      if (this.multipleSelection.length === 0) {
        Toast("未选中数据");
      } else {
        Dialog.confirm({ message: "是否通过审核！" })
          .then(() => {
            let idList = [];
            this.multipleSelection.forEach((item) => {
              idList.push(item.id);
            });

            // delete方法有坑，参数必须要有括号包裹 {data:idList}
            axios.put("/comment/batchCheck", idList).then((res) => {
              if (res.data.code === 8013) {
                Toast("通过审核成功");
                this.getData();
              } else {
                Toast("通过审核失败");
              }
            });
          })
          .catch(() => {
            Toast("已取消批量删除操作");
          });
      }
    },
    toggleSelection(rows) {
      if (rows) {
        rows.forEach((row) => {
          this.$refs.multipleTable.toggleRowSelection(row);
        });
      } else {
        this.$refs.multipleTable.clearSelection();
      }
    },
    // 请求未检查的评论
    queryNoCheck() {
      this.path = "/comment/findNoCheck";
      this.getData();
    },

    //从后端读取数据
    getData() {
      this.page.current = this.param.page;
      this.page.size = this.param.pageSize;

      post(this.path, this.param).then((res) => {
        if (res.data.code == 8014) {
          this.page.total = res.data.data.total;
          this.tableData = res.data.data.dataList;
        } else {
          this.page.total = 0;
          this.tableData = [];
        }
      });
    },

    //翻页
    handleCurrentChange(val) {
      this.param.page = val;
      this.getData();
    },
    //页面大小改变
    handleSizeChange(val) {
      this.param.pageSize = val;
      this.getData();
    },
  },
  mounted() {
    this.getData();
  },
};
</script>


<style scoped>
.top {
  position: relative;
  font-size: 25px;
  height: 40px;
  line-height: 43px;
  background-color: rgb(225, 225, 225);
  color: rgb(133, 133, 133);
}
.top .text {
  position: absolute;
  top: 50%;
  transform: translate(0, -50%);
  font-size: 17px;
  margin-left: 10px;
}

/* 批量 */
.batch{
  position: relative;
  top: 4px; display:inline-block;
}
/* 分页 */
.paging{
  margin-top: 9px;
  margin-left: 25px;display:inline-block;
}
</style>
