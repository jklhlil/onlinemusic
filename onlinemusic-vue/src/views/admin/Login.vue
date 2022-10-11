<template>
  <div class="login">
    <!-- 顶部标图片+标题 -->
    <div class="header">
      <van-image round width="120px" height="60px" :src="url" />
      <div class="header_line">
        <h1>在线音频后台</h1>
      </div>
    </div>
    <!-- 登入部分 -->
    <div class="loginArea">
      <van-form @submit="onSubmit">
        <van-field v-model="username" name="管理员名" label="管理员名" placeholder="管理员名" :rules="[{ required: true, message: '请填写管理员名' }]" />
        <van-field v-model="password" type="password" name="密码" label="密码" placeholder="密码" :rules="[{ required: true, message: '请填写密码' }]" />
        <div style="margin: 16px">
          <van-button round block type="info" native-type="submit">登录</van-button>
        </div>
      </van-form>
    </div>
    <!-- 注册 -->
    <div class="userLogin" @click="userLogin()">
      <a>返回用户登录</a>
    </div>
  </div>
</template>

<script>
import { Dialog } from "vant";
import { post } from "../../http/axios.js";
export default {
  data() {
    return {
      username: "",
      password: "",
      url: "https://s1.ax1x.com/2022/06/14/XINAzj.png",
    };
  },

  methods: {
    async onSubmit() {
      let param = {
        mail: this.username,
        password: this.password,
      };
      // 请求登录
      let res = await post("/admin/login", param);
      if (res.data.code === 2000) {
        this.$router.push({
          path: "/admin/music",
        });
        // console.log( res.data.data,'管理员');

        // 将管理员传到记下来
        localStorage.setItem("adminId", res.data.data.id);
        localStorage.setItem("adminId", res.data.data.id);

      } else {
        Dialog({ message: "账号不存在或密码有误" });
      }
    },
    userLogin() {
      this.$router.push("/login");
    },
  },
};
</script>

<style>
.userLogin {
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
  background-image: linear-gradient(#a70000, #fffde4);
  /* background-image: linear-gradient(#667db6, #0082c8,#0082c8,#667db6); */
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
</style>
