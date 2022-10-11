<template>
  <div class="body">
    <el-col :span="3" class="nav">
      <el-menu default-active="0"  class="el-menu-vertical-demo" @select="select" @open="handleOpen" @close="handleClose" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
        <el-menu-item index="0">
          <i class="el-icon-location"></i>
          <span slot="title">音乐管理</span>
        </el-menu-item>
        <el-menu-item index="1">
          <i class="el-icon-menu"></i>
          <span slot="title">评论管理</span>
        </el-menu-item>
        <el-menu-item index="2">
          <i class="el-icon-document"></i>
          <span slot="title">用户管理</span>
        </el-menu-item>
        <el-menu-item index="3">
          <!-- <i class="el-icon-setting"></i>
          <span slot="title">系统设置</span> -->
        </el-menu-item>
        <el-menu-item index="4"  style="height:420px">
        </el-menu-item>
        <el-menu-item index="5" @click="loginOut">
          <i class="el-icon-back"></i>
          <span slot="title">退出</span>
        </el-menu-item>
      </el-menu>
    </el-col>
    <div class="right">    <router-view></router-view></div>

  </div>
</template>

<script>
import { Dialog } from "vant";
export default {
  data() {
    return {
      paths: ["/admin/music", "/admin/comment","/admin/user","/admin/setting",,],
    };
  },
  methods: {
    select(key, keyPath) {
      this.$router.push(this.paths[key]);
    },
    loginOut(){
       Dialog.confirm({
        message: "是否退出登录",
        theme: 'round-button',
      })
        .then(() => {
          this.$router.push("/login");
        })
        .catch(() => {
          // on cancel
        });
    },
    handleOpen(key, keyPath) {
      // console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      // console.log(key, keyPath);
    },
  },
};
</script>

<style scoped>
.body{
  margin: 0px;
  padding:0px;
}
.nav{
  height: 1000px;
}
.right{
  overflow: hidden;
}
</style>