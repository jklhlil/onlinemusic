<template>
  <div class="login">
    <!-- 顶部标图片+标题 -->
    <div class="header">
      <van-image round width="120px" height="60px" :src="url" />
      <div class="header_line">
        <h1>在线音频APP</h1>
      </div>
    </div>
    <!-- 登入部分 -->
    <div class="loginArea">
      <van-form @submit="onSubmit">
        <van-field v-model="username" name="用户名" label="用户名" placeholder="用户名" :rules="[{ required: true, message: '请填写用户名' }]" />
        <van-field v-model="password" type="password" name="密码" label="密码" placeholder="密码" :rules="[{ required: true, message: '请填写密码' }]" />
        <div style="margin: 16px">
          <van-button round block type="info" native-type="submit">登录</van-button>
        </div>
      </van-form>
    </div>
    <!-- 注册 -->
    <div class="register">
      <a @click="register()">没有账号点我注册</a>
      <a class="adminlogin" @click="adminLogin()">管理员登录</a>
    </div>
  </div>
</template>

<script>
import { Dialog, Toast } from "vant";
import { post } from "../http/axios.js";
export default {
  data() {
    return {
      username: "",
      password: "",
      url: "https://s1.ax1x.com/2022/06/14/XINAzj.png",
    };
  },

  methods: {
    // 请求登录
    async onSubmit() {
      let param = {
        mail: this.username,
        password: this.password,
      };
      let res = await post("/consumer/login", param);
      if (res.data.code === 2000) {
        Toast(res.data.data.mail + " 欢迎你");
        this.$router.push({
          path: "/manage/home",
        });
        // console.log( res.data.data,'用户');

        // 将用户传到记下来
        localStorage.setItem("userId", res.data.data.id);
        localStorage.setItem("userScore", res.data.data.score);
      } else {
        Dialog({ message: "账号不存在或密码有误" });
      }
    },
    register() {
      this.$router.push("/register");
    },
    adminLogin() {
      this.$router.push("/admin/login");
    },
  },
};
</script>

<style>
.register {
  margin-left: 10px;
}
a {
  font-size: 16px;
  font-weight: 500;
  color: #fff;
}
.login {
  width: 100%;
  /* vh视窗高度 */
  height: 100vh;
  /* 设置渐变色 */
  /* background-image: linear-gradient(#a70000, #fffde4); */

    background-image: linear-gradient(#667db6, #0082c8,#0082c8,#667db6);
  overflow: hidden;
}
.header {
  width: 100%;
  text-align: center;
  /* 定位布局 */
  /* 声明为绝对定位布局 */
  position: absolute;
  /* 距离顶部120px */
  top: 100px;
}
.loginArea {
  width: 90%;
  /* 通过外边距的形式 */
  margin: 220px 10px auto;
  /* 卡片效果 */
  box-shadow: 0 4px 8px 0 rgba(255, 255, 255, 0.2);
  background-color: white;
  height: 200px;
  /* 设置圆角 */
  border-radius: 15px;
  /* 设置内边距 */
  padding: 10px;
}
.van-image {
  display: inline-block;
  vertical-align: middle;
}
.header_line {
  display: inline-block;
}

/* 管理员登录 */
.adminlogin {
  position: absolute;
  right: 4%;
}
</style>
