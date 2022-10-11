<template>
  <div id="">
    <router-view></router-view>

    <van-tabbar v-model="active">
      <van-tabbar-item to="/manage/home" icon="wap-home-o">首页</van-tabbar-item>
      <van-tabbar-item to="/manage/square" icon="points">活动</van-tabbar-item>
      <van-tabbar-item to="/manage/my" icon="contact">我的</van-tabbar-item>
    </van-tabbar>
  </div>
</template>

<script>
export default {
  data() {
    return {
      active: 0,
    };
  },
  created() {
    // 下面导航正确显示
    if (this.$route.path === "/manage/home") {
      this.active = 0;
    } else if (this.$route.path === "/manage/square") {
      this.active = 1;
    } else {
      this.active = 2;
    }

    // 音乐返回跳转数。评论刷新数。
    localStorage.setItem("goNum", 1);
    sessionStorage.setItem("commentRefresh", 0);
  },
  methods: {},
};
</script>

<style>
</style>
