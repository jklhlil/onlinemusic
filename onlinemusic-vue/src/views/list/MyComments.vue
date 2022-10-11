<template>
  <div class="body">
    <div class="top">
      <van-icon class="left" name="arrow-left" @click="onClickLeft" />
      <span class="text">我的评论</span>
      <!-- <van-icon class="share" name="share-o" /> -->
    </div>
    <!-- 用户 -->
    <div class="user">
      <img class="img" :src="user.pictureUrl" @click="onClickLeft">
      <span class="text">
        <span class="name">{{user.name}}</span>
        <span class="mail"> - {{user.mail}}</span>
      </span>
    </div>

    <div class="comments">
      <div class="title">
        <span style="margin-left: 8px;">我的评论</span>
      </div>
      <!-- 评论 -->
      <div class="comment" v-for="(item,index) in commentList" :key="index">
        <div class="music">
          <img class="img" :src="item.music.pictureUrl" @click="toPlay(item,index)">
          <div class="text">
            <span style="font-size:16px;font-weight:550">{{item.music.name}}</span>
            <span style="font-size:13px;color:#b8b7b7"> / {{item.music.singer}}</span>
            <span class="time">{{item.time}}</span>
          </div>
        </div>
        <div class="content">
          <span>
            {{item.content}}
          </span>
          <div>
            <!-- <span style="font-size:13px;color:#527099;"> 修改 </span> -->
            <span style="font-size:13px;color:#ff0000;" @click="deleteComment(item.id)"> 删除 </span>
            <!-- <van-icon name="arrow" /> -->
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
import axios from "axios";
import { Dialog, Toast } from "vant";
export default {
  data() {
    return {
      userId: 1,
      user: {
        pictureUrl: "https://s1.ax1x.com/2022/06/13/XWZgeK.jpg",
        name: "用户名",
        mail: "1@1",
      },
      commentList: [],
    };
  },
  created() {
    this.getComment();
    this.userId = this.$route.query.userId;
    this.getUser();

    // // 刷新，防止div乱放
    if (Number(sessionStorage.getItem("commentRefresh")) === 0) {
      Toast("正在加载");
      sessionStorage.setItem("commentRefresh", 1);
      this.$router.go(0);
    }
  },
  methods: {
    onClickLeft() {
      //Toast('返回');
      this.$router.go(-1);
    },
    // 请求用户
    async getUser() {
      let res = await post("/consumer/findById", this.userId);
      this.user = res.data.data;
      // console.log(this.user, "用户");
    },
    // 请求评论
    async getComment() {
      let param = {
        page: 1,
        pageSize: 25,
        data: localStorage.getItem("userId"),
      };
      let res = await post("/comment/findMusicById", param);

      if (res.data.data != null) {
        this.commentList = res.data.data.dataList;
      } else {
        this.commentLis = [];
      }
      // console.log(this.commentList, "我的评论");
    },

    // 删除评论
    async deleteComment(commentId) {
      Dialog.confirm({
        message: "是否确删除评论?",
        theme: "round-button",
      })
        .then(() => {
          axios.delete("/comment/deleteById/" + commentId).then(() => {
            this.getComment();
            // this.$router.go(0);
          });
        })
        .catch(() => {
          // on cancel
        });
    },
    // 播放音乐
    toPlay(item, index) {
      let musicList = [];
      this.commentList.forEach((element) => {
        musicList.push(element.musicId);
      });

      this.$router.push({
        path: "/function/play",
        query: {
          musicList: musicList,
          index: index,
          userId: localStorage.getItem("userId"),
          state: 1,
        },
      });
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
.top .left {
  margin-left: 8px;
}
.top .text {
  margin-left: 10px;
}
/* 下面这个css坑 */
/* .top .share {
  position: absolute;
  right: 20px;
  top: 50%;
  font-size: 28px;
  transform: translate(0, -50%);
} */

/* 用户 */
.user {
  width: 100%;
  height: 64px;
  background: #fff;
  overflow: hidden;
  line-height: 70px;
  position: relative;
}
.user .img {
  height: 50px;
  width: 50px;
  margin-left: 8px;
  border-radius: 100%;
  position: absolute;
  top: 50%;
  transform: translate(0, -50%);
  object-fit: cover;
}
/* 用户文本 */
.user .text {
  margin-left: 70px;
}
.user .text .name {
  font-size: 18px;
}
.user .text .mail {
  font-size: 13px;
  color: #b8b7b7;
}

/* 标题 */
.comments {
  margin-top: 10px;
  width: 100%;
  background: #fff;
  border-radius: 10px 10px 0 0;
}

/* 标题文本 */
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
/* 音乐 */
.comments .comment .music {
  height: 50px;
  width: 100%;
  position: relative;
  margin-top: 10px;

  line-height: 50px;
}
.music .img {
  height: 45px;
  width: 45px;
  margin-left: 8px;
  border-radius: 100%;
  position: absolute;
  top: 50%;
  transform: translate(0, -50%);
  object-fit: cover;
}
.music .text {
  margin-left: 62px;
}
/* 评论时间 */
.music .text .time {
  display: inline-block;
  position: absolute;
  right: 8%;
  font-size: 11px;
}
/* 内容 */
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