<template>
  <div class="mine">
    <div class="bg"></div>
    <!-- 用户信息页 -->
    <div class="userinfo">
      <div class="userface">
        <img :src="user.pictureUrl" alt="" />
      </div>
      <div class="username">{{user.name}}</div>
    </div>

    <!-- 收藏 歌单 动态 -->
    <div class="mid">
      <van-grid column-num="3" icon-size="20px">
        <van-grid-item icon="star" text="我的收藏" @click="toGrid(0)" />
        <van-grid-item icon="like" text="我的订阅" @click="toGrid(1)" />
        <van-grid-item icon="comment" text="评论" @click="toMycomments()" />
      </van-grid>
    </div>

    <!-- 操作列表 -->
    <div class="fa">
      <div>
        <van-cell title="用户信息设置" icon="setting-o" @click="loding()">
          <!-- 使用 right-icon 插槽来自定义右侧图标 -->
          <template #right-icon>
            <van-icon name="arrow" class="search-icon" />
          </template>
        </van-cell>
      </div>
      <div class="share">
        <van-cell icon="share-o" title="点击分享给好友" @click="showShare = true">
          <template #right-icon>
            <van-icon name="arrow" class="search-icon" />
          </template>
        </van-cell>
        <van-share-sheet v-model="showShare" title="立即分享给好友" :options="options" @select="onSelect" />

      </div>
      <div class="action">
        <van-cell title="我的积分" icon="shop-o" @click="score()">
          <!-- 使用 right-icon 插槽来自定义右侧图标 -->
          <template #right-icon>
            <van-icon name="arrow" class="search-icon" />
          </template>
        </van-cell>

        <van-cell title="用户注销" icon="close" @click="userCancel">
          <!-- 使用 right-icon 插槽来自定义右侧图标 -->
          <template #right-icon>
            <van-icon name="arrow" class="search-icon" />
          </template>
        </van-cell>
        <van-cell title="联系我们" icon="friends-o" @click="relation">
          <!-- 使用 right-icon 插槽来自定义右侧图标 -->
          <template #right-icon>
            <van-icon name="arrow" class="search-icon" />
          </template>
        </van-cell>
      </div>
      <div class="adivice">
        <van-cell is-link title="意见反馈" icon="user-o" @click="show = true">
          <template #right-icon>
            <van-icon name="arrow" class="search-icon" />
          </template>
        </van-cell>
        <van-action-sheet v-model="show" title="写下你的建议">
          <div class="content">
            <textarea class="textarea" cols="40" rows="10"></textarea>
            <van-button style="margin-top:10px" type="info">确认提交</van-button>
          </div>
        </van-action-sheet>
        <!-- <van-cell title="其他设置" icon="setting-o">
          <template #right-icon>
            <van-icon name="arrow" class="search-icon" />
          </template>
        </van-cell> -->
      </div>
    </div>

    <div class="loginout" @click="loginOut">退出登录</div>

    <div style="height:60px"></div>
  </div>
</template>

<script>
import { Dialog, Toast } from "vant";
import axios, { post } from "../../http/axios.js";
export default {
  data() {
    return {
      user: {
        pictureUrl: "https://s1.ax1x.com/2022/06/22/jpCUUO.jpg",
      },

      userinfo: {},
      show: false,
      actions: [],

      showShare: false,
      options: [
        [
          { name: "微信", icon: "wechat" },
          { name: "朋友圈", icon: "wechat-moments" },
          { name: "微博", icon: "weibo" },
          { name: "QQ", icon: "qq" },
        ],
        [
          { name: "复制链接", icon: "link" },
          { name: "分享海报", icon: "poster" },
          { name: "二维码", icon: "qrcode" },
          { name: "小程序码", icon: "weapp-qrcode" },
        ],
      ],
    };
  },
  created() {
    this.getUserInfoByToken();
  },
  methods: {
    // 获取用户
    async getUserInfoByToken() {
      let res = await post(
        "/consumer/findById",
        localStorage.getItem("userId")
      );
      this.user = res.data.data;
      // console.log(this.user, "用户");
    },
    // 宫格跳转
    toGrid(index) {
      this.$router.push("/list/collectsubscribe/" + index);
    },
    // 动态
    toMycomments() {
      this.$router.push({
        path: "/list/mycomments/",
        query: {
          userId: this.user.id,
        },
      });
    },
    // 设置信息
    loding() {
      this.$router.push({
        path: "/function/modifyuser",
        query: { userId: this.user.userId },
      });
    },
    // 分享
    onSelect(option) {
      // console.log(1);
      if (option.name === "QQ") {
        location.href =
          "https://qm.qq.com/cgi-bin/qm/qr?k=w4jNYoeOgOpkULLnPKL_pC_Dx29ERglN&noverify=0#";
        Toast("前往" + option.name);
      } else {
        Toast(option.name);
      }
      this.showShare = false;
    },
    // 我的积分
    score() {
      Dialog({
        message: this.user.name + "您所剩余积分为" + this.user.score + "分",
        theme: "round-button",
      });
    },
    // 注销
    userCancel() {
      Dialog.confirm({
        message: "注销操作不可逆，是否注销！",
      })
        .then(() => {
          axios.delete("/consumer/deleteById/" + this.user.id);
          this.$router.push("/login");
        })
        .catch(() => {
          // on cancel
        });
    },
    // 联系我们
    relation() {
      Dialog.alert({
        title: "联系我们",
        message: "联系QQ1587022340",
      }).then(() => {
        // on close
      });
    },
    // 反馈
    feedback() {
      // console.log(1);
    },
    // 退出登陆
    loginOut() {
      Dialog.confirm({
        message: "是否退出登录",
        theme: "round-button",
      })
        .then(() => {
          this.$router.push("/login");
        })
        .catch(() => {
          // on cancel
        });
    },
  },
};
</script>

<style>
.midcontain {
  display: inline-block;
  background-color: red;
  margin: 6px;
  height: 80px;
  width: 30%;
}
.mid {
  width: 90%;
  margin-top: 20px;
  margin-left: 5%;
  border-radius: 28px;
  overflow: hidden;
  box-shadow: 4px 4px 4px 1px rgba(119, 119, 119, 0.5);
}

.van-search {
  padding: 5px;
}
.mine .bg {
  background-image: linear-gradient(#00ffff, #f0f0f0);
  filter: blur(3px);
  border-bottom-left-radius: 50%;
  border-bottom-right-radius: 50%;
  height: 180px;
}
.mine .userinfo {
  height: 160px;
  width: 80%;
  position: absolute;
  top: 100px;
  left: 50%;
  margin-left: -40%;
  opacity: 0.9;
  /* filter: blur(8px); */

}
.userinfo .userface {
  width: 100px;
  height: 100px;
  border-radius: 50%;
  margin: -50px auto;
}
.userinfo .userface img {
  width: 100%;
  height: 100%;
  border-radius: 50%;
  object-fit: cover;
}
.userinfo .username {
  text-align: center;
  margin-top: 60px;
  font-size: 20px;
}
.loginout {
  font-size: large;
  width: 50%;
  background-image: linear-gradient(#21a7c9, #6dd5ed);
  text-align: center;
  color: black;
  line-height: 35px;
  border-radius: 20px;
  margin: 0 auto;
  cursor: pointer;
}
.fa {
  margin: 10px auto;
  width: 90%;
}
.share {
  margin-bottom: 10px;
  width: 100%;
  box-shadow: 4px 4px 4px 1px rgba(119, 119, 119, 0.5);
}
.action {
  margin-bottom: 10px;
  width: 100%;
  box-shadow: 4px 4px 4px 1px rgba(119, 119, 119, 0.5);
}
.adivice {
  margin-bottom: 10px;
  width: 100%;
  box-shadow: 4px 4px 4px 1px rgba(119, 119, 119, 0.5);
}

/* 反馈 */
.content {
  padding: 16px 16px 150px;
  text-align: center;
}
.area {
  outline: none;
}

.content .textarea {
  border-color: #1989fa;
}
</style>
