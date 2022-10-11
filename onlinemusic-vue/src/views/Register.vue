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
        <van-field v-model="username" type="email" name="邮箱" label="邮箱" placeholder="邮箱" />
        <!-- <van-field v-model="password" type="text" name="验证码" label="验证码" placeholder="验证码" :rules="[{ required: true, message: '请填写邮箱验证码' }]" /> -->

        <van-field v-model="verify2"  type="tel" name="验证码" label="验证码" placeholder="验证码" :rules="[{ required: true, message: '请填写邮箱验证码' }]">
        </van-field>

        <van-field v-model="password" type="password" name="密码" label="密码" placeholder="密码" :rules="[{ required: true, message: '请填写密码' }]" />

        <div style="margin: 16px">
          <van-button round block type="info" native-type="submit">注册</van-button>
        </div>
        <!-- 发送验证码 -->
        <van-button native-type="button" class="verify" type="info" size="small" @click="sendVerify" ref="verifyDiv">发送验证码</van-button>
      </van-form>
    </div>

    <!-- 返回登录 -->
    <div class="register" @click="login()">
      <a>返回登录</a>
    </div>
  </div>
</template>

<script>
import { Dialog, Toast } from "vant";
import { post } from "../http/axios.js";
import axios from "axios";
export default {
  data() {
    return {
      username: "",
      verify2: "",
      password: "",
      sendTime: 60,

      verify: "",
      url: "https://s1.ax1x.com/2022/06/14/XINAzj.png",
      loop:null,
    };
  },
  deactivated() { 
    clearInterval(this.loop) 
  },

  methods: {
    // 注册
    async onSubmit() {
      if (this.verify != this.verify2) {
        Toast("验证码错误");
        return 0;
      }

      let param = {
        mail: this.username,
        password: this.password,
      };
      // 请求注册
      let res = await post("/consumer/register", param);
      // console.log(res);
      if (res.data.code === 8012) {
        Dialog({ message: "已成功注册，请登录" });
      } else {
        Dialog({ message: res.data.data });
      }
    },
    // 发送验证码
    sendVerify() {
      if (this.username === "") {
        Toast("请填写注册邮箱");
        return 0;
      }
      if (this.sendTime != 60) {
        Toast("请勿重复发送验证码");
        return 0;
      }

      // 发送验证码请求
      this.verify = this.produceVerify(6);
      console.log(this.verify);
      let param = {
        sendTo: this.username,
        text: this.verify,
      };
      axios.post("/sendMail/verifyCode", param).then((res) => {
        console.log('发送邮箱,花了',60-this.sendTime,'秒');
        
        // this.$refs.verifyDiv.innerHTML = "发送验证码";
        // clearInterval(this.loop);
        // this.sendTime = 60;
      });

      Toast("发送成功");
      // 显示发送时间
      this.$refs.verifyDiv.innerHTML = " " + this.sendTime + " S";
      this.loop = setInterval(() => {
        this.sendTime--;
        this.$refs.verifyDiv.innerHTML = " " + this.sendTime + " S";

        // this.sendTime为0，允许再发送验证码
        if (this.sendTime === 0) {
          this.$refs.verifyDiv.innerHTML = "发送验证码";
          clearInterval(this.loop);
          this.sendTime = 60;
        }
      }, 1000);
    },
    // 获取num位随机数
    produceVerify(num) {
      let verify = "";
      for (let i = 0; i < num; i++) {
        let a = Math.floor(Math.random() * 10);
        verify += a;
      }
      return verify;
    },
    login() {
      this.$router.push("/login");
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
  background-image: linear-gradient(#667db6, #0082c8, #0082c8, #667db6);
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
  position: relative;
  width: 90%;
  /* 通过外边距的形式 */
  margin: 220px 10px auto;
  /* 卡片效果 */
  box-shadow: 0 4px 8px 0 rgba(255, 255, 255, 0.2);
  background-color: white;
  height: 240px;
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

/* 验证码 */
/* .verify{
  position: absolute;
  right:2%;
  top: 35%;

  width: 90px;
  height: 30px;
  border-radius: 100px;
  background-color: #1989fa;

  color: #fff;
  font-size: 14px;
  text-align: center;
  line-height: 18px;
} */
.verify {
  position: absolute;
  top: 23%;
  right: 2%;
}
</style>
