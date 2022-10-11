<template>
  <div>
    <!-- 顶部 -->
    <div class="top">
      <i class="el-icon-s-unfold " style="  margin-left: 10px;">
        <span class="text">首页/音乐管理</span>
      </i>
    </div>

    <el-card>

      <!-- 查询 -->
      <el-row>
        <el-form ref="param" :model="param" label-width="80px">
          <el-col :span="12">
            <el-form-item prop="title">
              <el-input v-model="param.data" style="width: 100%;" placeholder="请输入音乐名或歌手名">{{param.data}}</el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-button type="primary" @click="handleQuery()" style="margin-left:10px">查询</el-button>
            <el-button @click="handleReset('query')">重置</el-button>
          </el-col>
        </el-form>
      </el-row>

      <!-- 表单 -->
      <!-- strip行之间斑马纹 -->
      <el-table ref="multipleTable" :data="tableData" stripe height="515px" :default-sort="{prop: 'id', order: 'descending'}" style="width: 100%" @selection-change="handleSelectionChange">
        <el-table-column fixed type="selection" width="45">
        </el-table-column>

        <el-table-column sortable label="歌名" width="140">
          <template slot-scope="scope">
            <!-- 编辑 -->
            <el-input size="mini" v-model="scope.row.name" v-if="scope.row.id===editId"></el-input>
            <span v-else>{{scope.row.name}}</span>
          </template>
        </el-table-column>

        <el-table-column label="歌手" width="130">
          <template slot-scope="scope">
            <el-input size="mini" v-model="scope.row.singer" v-if="scope.row.id===editId"></el-input>
            <span v-else>{{scope.row.singer}}</span>
          </template>
        </el-table-column>

        <el-table-column sortable label="歌曲链接" width="250" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-input size="mini" v-model="scope.row.url" v-if="scope.row.id===editId"></el-input>
            <span v-else>
              <a :href="scope.row.url"  target="_blank">
                <el-button size="small" type="primary" plain round>收听歌曲</el-button>
              </a>
            </span>
          </template>
        </el-table-column>

        <el-table-column label="歌曲图片链接" width="250" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-input size="mini" v-model="scope.row.pictureUrl" v-if="scope.row.id===editId"></el-input>
            <span v-else>
              <a :href="scope.row.pictureUrl" target="_blank">
                <el-button size="small" type="primary" plain round>查看图片</el-button>
              </a>
            </span>
          </template>
        </el-table-column>

        <el-table-column label="所需积分" width="130">
          <template slot-scope="scope">
            <el-input size="mini" v-model="scope.row.score" v-if="scope.row.id===editId"></el-input>
            <span v-else>{{scope.row.score}}</span>
          </template>
        </el-table-column>

        <el-table-column label="是否通过审核" width="110">
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
          <el-button size="medium" type="danger" @click="batchDelete()">批量删除</el-button>
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
          adminId: 1,
          id: 1,
          name: "歌名",
          singer: "歌手",
          url: "http/",
          pictureUrl: "http/",
          isCheck: 1,
        },
      ],
      page: {
        current: 1,
        size: 5,
        total: 0,
      },
      // 请求的param
      param: {
        data: "",
        page: 1,
        pageSize: 5,
      },

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
      this.param.data = "";
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
      let res = await axios.put("/music/edit", row);
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
      let res = await axios.delete("/music/deleteById/" + row.id);
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
    //批量删除
    async batchDelete() {
      if (this.multipleSelection.length === 0) {
        Toast("未选中数据");
      } else {
        Dialog.confirm({ message: "是否批量删除！" })
          .then(() => {
            let idList = [];
            this.multipleSelection.forEach((item) => {
              idList.push(item.id);
            });

            // delete方法有坑，参数必须要有括号包裹 {data:idList}
            axios.delete("/music/batchDelete", { data: idList }).then((res) => {
              if (res.data.code === 8011) {
                Toast("批量删除成功");
                this.getData();
              } else {
                Toast("批量删除失败");
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

    //从后端读取数据
    getData() {
      this.page.current = this.param.page;
      this.page.size = this.param.pageSize;

      post("/music/findByAdmin", this.param).then((res) => {
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
