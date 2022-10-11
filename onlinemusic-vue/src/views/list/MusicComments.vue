<template>
  <div class="body">

    <!-- <van-nav-bar left-text="返回" left-arrow @click-left="onClickLeft">
      <template #left>
        <span style="margin-left:10px;  font-size: 20px;">评论</span>
      </template>
      <template #right>
        <van-icon @click="toPlaying" style="margin-right:10px" name="https://s1.ax1x.com/2022/06/29/jnHn9f.png" size="18" />
      </template>
    </van-nav-bar> -->
    <!-- 导航 -->
    <div class="top">
      <van-icon style="margin-left:8px" name="arrow-left" @click="onClickLeft" />
      <span style="margin-left:10px">评论</span>
      <van-icon class="share" name="share-o" @click="showShare = true" />
    </div>
    <van-share-sheet v-model="showShare" title="立即分享给好友" :options="options" @select="onSelect" />

    <!-- 音乐 -->
    <div class="music">
      <img class="img" :src="music.pictureUrl" @click="onClickLeft">
      <span class="text">
        <span style="font-size:18px">{{music.name}}</span>
        <span style="font-size:13px;color:#b8b7b7"> - {{music.singer}}</span>
        <span style="font-size:14px;color:#b8b7b7;font-weight:550"> ꔷ </span>
        <span style="font-size:14px;color:#ef3845;font-weight:550" @click="subscribe" v-if="isSubscribe===0">关注</span>
        <span style="font-size:14px;color:#ef3845;font-weight:550" @click="subscribe" v-else>已关注</span>
      </span>
    </div>
    <van-field v-model="commentContent" rows="1" autosize label="评论" type="textarea" placeholder="请输入评论">
      <template #button>
        <span @click="comment">发送</span>
      </template>
    </van-field>

    <div class="comments">
      <div class="title">
        <span style="margin-left: 8px;">评论区</span>
      </div>
      <!-- 评论 -->
      <div class="comment" v-for="(item,index) in commentList" :key="index">
        <div class="user">
          <img class="img" :src="item.consumer.pictureUrl" alt="">
          <div class="text">
            <span style="font-size:16px;font-weight:550">{{item.consumer.name}}</span>
            <span class="time" style="font-size:11px">{{item.time}}</span>
          </div>
        </div>
        <div class="content">
          <span>
            {{item.content}}
          </span>
          <div style="font-size:13px;color:#527099;">
            回复
            <van-icon name="arrow" />
          </div>
        </div>
        <!--  横线。给div加边框，调整div长度位置 -->
        <div class="last"></div>
      </div>
    </div>

  </div>
</template>

<script>
import { post } from "../../http/axios.js";
import { Toast } from "vant";
export default {
  data() {
    return {
      consumerId: 1,
      musicId: 1,
      isSubscribe: 0,

      commentList: [],
      music: {
        pictureUrl: "https://s1.ax1x.com/2022/06/22/jpC5xs.jpg",
        name: "歌名",
        singer: "歌手",
      },
      commentContent: "",

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
    this.musicId = Number(this.$route.query.musicId);
    // console.log(this.musicId);

    this.getComment();
    this.consumerId = Number(localStorage.getItem("userId"));
    this.isSubscribe = Number(this.$route.query.isSubscribe);
    this.getMusic();
    // 刷新，防止div乱放
    if (Number(sessionStorage.getItem("commentRefresh")) === 0) {
      Toast("正在加载");
      sessionStorage.setItem("commentRefresh", 1);
      this.$router.go(0);
    }
  },
  methods: {
    onClickLeft() {
      //Toast('返回');
      // 修改音乐界面返回的跳转数
      localStorage.setItem(
        "goNum",
        Number(localStorage.getItem("goNum")) + 0.5
      );
      this.$router.replace("/function/play");
    },

    // 请求评论
    async getComment() {
      let param = {
        page: 1,
        pageSize: 25,
        data: this.musicId,
      };
      let res = await post("/comment/findConsumerById", param);
      this.commentList = res.data.data.dataList;
      // console.log(this.commentList, "音乐评论");
    },
    // 请求音乐
    async getMusic() {
      let param = {
        page: 1,
        pageSize: 1,
        data: {
          id: this.musicId,
        },
      };

      let res = await post("/music/findByPage", param);
      // console.log(res);

      this.music = res.data.data.dataList[0];
      // console.log(this.music, "音乐");
    },
    // 分享
    onSelect(option) {
      // console.log(option);
      if (option.name === "QQ") {
        location.href =
          "https://qm.qq.com/cgi-bin/qm/qr?k=w4jNYoeOgOpkULLnPKL_pC_Dx29ERglN&noverify=0#";
        Toast("前往" + option.name);
      } else {
        Toast(option.name);
      }
      this.showShare = false;
    },
    // 评论
    async comment() {
      if (this.commentContent === "") {
        Toast("请输入评论内容");
      } else {
        let param = {
          consumerId: this.consumerId,
          musicId: this.musicId,
          content: this.commentContent,
        };
        let res = await post("/comment/comment", param);
        if (res.data.data != null) {
          this.commentContent = "";
          Toast("评论成功，请等待管理员审核");
        }
      }
    },
    // 关注
    async subscribe() {
      if (this.isSubscribe === 1) {
        this.isSubscribe = 0;
        Toast({ message: "取消关注成功", position: "top" });
      } else {
        let resUser = await post(
          "/consumer/findById",
          localStorage.getItem("userId")
        );
        let score = resUser.data.data.score - this.music.score;
        if (score < 0) {
          this.isSubscribe = 0;
          Toast({
            message: "积分不足，关注失败。积分余额为" + resUser.data.data.score,
            position: "bottom",
          });
        } else {
          this.isSubscribe = 1;
          Toast({ message: "关注成功,所剩积分" + score, position: "top" });
        }
      }
      // 修改订阅
      let param = {
        consumerId: this.consumerId,
        musicId: this.musicId,
        isSubscribe: this.isSubscribe,
      };
      post("/subCol/update", param);
    },
  },
};
</script>

<style scoped>
.body {
  background-color: #f7f7f7;
}
/* 顶部导航 */
.top {
  position: relative;
  width: 100%;
  height: 64px;
  background: #fff;
  font-size: 20px;
  line-height: 62px;
}
.top .share {
  position: absolute;
  right: 20px;
  top: 26%;
  font-size: 28px;
  /* transform: translate(0, -50%); */
}
/* 音乐 */
.music {
  width: 100%;
  height: 64px;
  background: #fff;
  overflow: hidden;
  line-height: 70px;
  position: relative;
}
.music .img {
  height: 50px;
  width: 50px;
  margin-left: 8px;
  border-radius: 100%;
  position: absolute;
  top: 50%;
  transform: translate(0, -50%);
  object-fit: cover;
}
.music .text {
  margin-left: 70px;
}

.comments {
  margin-top: 10px;
  width: 100%;
  background: #fff;
  border-radius: 10px 10px 0 0;
}
/* 标题 */
.comments .title {
  height: 40px;
  font-size: 17px;
  line-height: 39px;
}

/* 评论 */
.comments .comment {
  font-size: 17px;
  line-height: 30px;
  position: relative;
}
/* 用户 */
.comments .comment .user {
  height: 50px;
  width: 100%;
  position: relative;
  margin-top: 10px;

  line-height: 50px;
}
.user .img {
  height: 45px;
  width: 45px;
  margin-left: 8px;
  border-radius: 100%;
  position: absolute;
  top: 50%;
  transform: translate(0, -50%);
  object-fit: cover;
}
.user .text {
  margin-left: 62px;
}
.user .text .time {
  display: inline-block;
  position: absolute;
  right: 8%;
}

/* 评论内容 */
.comment .content {
  margin-left: 62px;
  width: 80%;
}
/* 横线。给div加边框，调整div长度位置 */
.last {
  position: absolute;
  height: 1px;
  width: 85%;
  /* 先左后上 */
  right: 0%;
  bottom: 0%;

  border-bottom: 1px solid #e1e0e0;
}
</style>