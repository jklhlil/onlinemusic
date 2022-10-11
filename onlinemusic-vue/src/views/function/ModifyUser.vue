<template>
  <div class="body">
    <van-nav-bar left-text="返回" left-arrow @click-left="onClickLeft" />
    <div class="userface">
      <!-- 更改头像 -->
      <div class="uploader">
        <van-uploader v-model="fileList" :after-read="afterRead" :max-count="1" />
      </div>
    </div>

    <!-- 输入任意文本 -->
    <van-field v-model="user.name" label="用户名" />

    <van-field v-model="user.mail" readonly label="邮箱" />
    <van-field v-model="user.password" type="password" label="密码" />
    <van-field v-model="password1" type="password" label="确认密码" />
    <!-- 输入手机号，调起手机号键盘 -->
    <van-field v-model="user.phone" label="电话号码" />
    <van-button @click="change()" round type="info">修改用户信息</van-button>

  </div>
</template>

<script>
import { Toast, Dialog } from "vant";
import axios, { post } from "../../http/axios.js";
export default {
  data() {
    return {
      user: {
        id: "",
        name: "",
        password: "",
        phone: "",
        mail: "",
        pictureUrl: "",
      },
      password1: "",
      fileList: [],
      pictureFile: null,
    };
  },
  created() {
    this.user.id = this.$route.query.userId;
    this.getUserInfoByToken();
  },
  methods: {
    onClickLeft() {
      this.$router.go(-1);
    },
    // 获取用户
    async getUserInfoByToken() {
      let res = await post(
        "/consumer/findById",
        localStorage.getItem("userId")
      );
      this.user = res.data.data;
      // this.password1=this.user.password;
      this.user.password = null;
      this.password1 = null;
    },
    //修改用户信息
    async change() {
      if (this.user.password != this.password1) {
        console.log(this.password1, this.user.password);
        Toast("两次密码不匹配，无法修改");
        return 0;
      }
      console.log(this.user);
      
      Dialog.confirm({
        message: "是否修改",
      })
        .then(() => {
          let param = {
            id: this.user.id,
            name: this.user.name,
            mail: this.user.mail,
            password: this.user.password,
            phone: this.user.phone,
          };
          axios.put("/consumer/edit", param);
          if (this.pictureFile != null) {
            this.updatePicture(this.pictureFile);
          }
          Toast("修改成功");
          setTimeout(() => {
            this.$router.go(-1);
          }, 200);
        })
        .catch(() => {
          // on cancel
        });
    },
    // 动画
    afterRead(file) {
      this.pictureFile = file;
      file.status = "uploading";
      file.message = "上传中...";
      setTimeout(() => {
        file.status = "success";
      }, 500);
    },
    // 修改头像
    updatePicture(file) {
      // 构建formData，请求体数据为formData，请求体类型为 "multipart/form-data"。
      let formData = new FormData();
      formData.append("userId", localStorage.getItem("userId"));
      formData.append("file", file.file);
      return axios({
        url: "/consumer/updatePicture",
        method: "PUT",
        headers: {
          "Content-Type": "multipart/form-data",
        },
        data: formData,
      }).then((res) => {
        if (res.data.code === 8013) {
        } else {
        }
        // console.log(res.data, "照片更新情况");
      });
    },
  },
};
</script>

<style scoped>
.body {
  height: 100%;
  background-color: #fff;
}
.userface {
  position: relative;
  padding: 10px 16px;
  color: #646566;
  word-wrap: break-word;
  font-size: 14px;
  height: 140px;
}
/*  更改头像  */
.uploader {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  overflow: hidden;
  border-radius: 12px;
}
img {
  width: 100%;
  height: 100%;
}
button {
  position: absolute;
  left: 50%;
  top: 80%;
  transform: translate(-50%, 0);
  width: 50%;
}
</style>
