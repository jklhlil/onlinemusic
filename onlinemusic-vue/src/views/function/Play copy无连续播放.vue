<template>
  <div class="body" ref="body">
    <audio ref="audioMusic" style="display: none" :src="musicData.music.url"></audio>
    <!-- 顶部 -->
    <div class="top">
      <van-icon name="arrow-down" class="left" size="20px" color="#f6fef8" @click="back" />
      <span style="font-size: 17px;color:#f5ffff">{{musicData.music.name}}</span>
      <br />
      <span style="font-size: 13px;color:#87bbc4">{{musicData.music.singer}}/{{musicData.music.name}}</span>
      <van-icon class="right" name="ellipsis" size="20px" color="#f6fef8" />
    </div>
    <!-- 唱片 -->
    <div id="songstatus_pic">
      <!--指针-->
      <img src="https://s1.ax1x.com/2022/06/17/XqX8Dx.png" id="discpointer_pic" />

      <div id="status_pic" ref="img" @click="play">
        <img id="ddd" :src="musicData.music.pictureUrl" />
        <img id="ccc" src="https://s1.ax1x.com/2022/06/17/XqREBn.png" />

      </div>

      <!--黑胶唱片-->
      <!-- <img @click="play" ref="img" src="https://s1.ax1x.com/2022/06/17/XqREBn.png" id="status_pic" /> -->
    </div>
    <div class="bottom">
      <!-- 上面图标 -->
      <div class="icons1">
        <!-- 收藏 -->
        <van-icon class="unite collection" name="like" color="red" @click="collection" v-if="musicData.isCollect === 1" />
        <van-icon class="unite collection" name="like-o" color="#d1d6d1" @click="collection" v-else />

        <!-- 订阅 -->
        <van-icon class="unite subscribe" name="http://localhost:8089/files/%E8%AE%A2%E9%98%852.png" size="26px" @click="subscribe" :badge="musicData.music.score" v-if="musicData.isSubscribe === 1" />
        <van-icon class="unite subscribe" name="http://localhost:8089/files/%E8%AE%A2%E9%98%851.png" size="26px" @click="subscribe" :badge="musicData.music.score" v-else />

        <van-icon class="unite download" name="http://localhost:8089/files/%E4%B8%8B%E8%BD%BD.png" @click="download" ref="down" />

        <van-icon class="unite comment" name="comment-o" color="#d1d6d1" @click="comment" :badge="commentNum" />
      </div>
      <!-- 进度 -->
      <div class="progress">
        <span class="lin current">{{ currentTimeString }}</span>
        <div class="lin line1" ref="line1"></div>
        <div class="lin mark"></div>
        <div class="lin line2" ref="line2"></div>
        <span class="totalTime lin">{{ totalTimeString }}</span>
      </div>

      <!-- 下面图标 -->
      <div class="icons2">
        <van-icon class="unite replay" name="revoke" color="#d1d6d1" @click="replay" />
        <van-icon class="unite previous" name="arrow-left" color="#d1d6d1" @click="previous" />

        <!-- 播放 -->
        <van-icon class="unite" id="iconPlay" name="play-circle-o" color="#f6fef8" @click="play" v-if="intervals.length === 0" />
        <van-icon class="unite" id="iconPlay" name="pause-circle-o" color="#f6fef8" @click="play" v-else />

        <van-icon class="unite next" name="arrow" color="#d1d6d1" @click="next" />

        <van-icon class="unite loop" name="http://localhost:8089/files/%E5%BE%AA%E7%8E%AF1.png" @click="loop(1)" v-if="isSingle===0" />
        <van-icon class="unite loop" name="http://localhost:8089/files/%E5%BE%AA%E7%8E%AF2.png" @click="loop(0)" v-else />

      </div>
    </div>
  </div>
</template>

<script>
import { Toast } from "vant";
import { post } from "../../http/axios.js";
export default {
  data() {
    return {
      musicList: [],
      index: 0,

      musicData: {
        isCollect: 0,
        isSubscribe: 0,
        music: {
          name: "画",
          singer: "邓紫棋",
          url: "http://localhost:8089/files/%E9%82%93%E7%B4%AB%E6%A3%8B%20-%20%E7%94%BB.mp3",
        },
      },
      commentNum: 0,

      audioMusic: {},
      currentTimeString: "00:00",
      totalTimeString: "05:19",
      isSingle: 0,

      intervals: [],
      rotateVal: 0,

    };
  },
  created() {

      this.init();

  },
  destroyed() {

    // 销毁定时操作
    this.clearIntervals();
  },
  methods: {
    init() {
      this.musicList = this.$route.query.musicList;
      // TMD 路径的东西一定要转换格式
      this.index = Number(this.$route.query.index);

      this.getMusic();
      this.getCommentNum();
      setTimeout(() => {
        this.getBackground();
      }, 10);

      setTimeout(() => {
        // 设置audio
        this.audioMusic = this.$refs.audioMusic;
        this.getTotalTimeString();

        // this.play();
        this.loop(0);
      }, 100);
    },
    // 请求音乐，有收藏订阅信息
    async getMusic() {
      let param = {
        consumerId: this.$route.query.userId,
        musicId: this.musicList[this.index],
      };
      let res = await post("/music/findSubColMusicById", param);
      this.musicData = res.data.data;
      console.log(this.musicData, "音乐，含收藏订阅信息");
    },
    async getCommentNum() {
      let res = await post(
        "/comment/findConsumerNumById",
        this.musicList[this.index]
      );
      this.commentNum = res.data.data;
    },
    back() {
      //Toast('返回');
      this.$router.go(-2 * Number(localStorage.getItem("goNum")) + 1);
      localStorage.setItem("goNum", 1);
    },

    // 上面图标的方法
    // 收藏
    async collection() {
      if (this.musicData.isCollect === 1) {
        this.musicData.isCollect = 0;
        Toast({ message: "取消收藏成功", position: "bottom" });
      } else {
        this.musicData.isCollect = 1;
        Toast({ message: "收藏成功", position: "bottom" });
      }
      let param = {
        consumerId: this.musicData.consumerId,
        musicId: this.musicData.musicId,
        isCollect: this.musicData.isCollect,
      };
      post("/subCol/update", param);
    },
    // 订阅
    async subscribe() {
      if (this.musicData.isSubscribe === 1) {
        this.musicData.isSubscribe = 0;
        Toast({ message: "取消订阅成功", position: "bottom" });
      } else {
        let resUser = await post(
          "/consumer/findById",
          localStorage.getItem("userId")
        );
        let score = resUser.data.data.score - this.musicData.music.score;
        if (score < 0) {
          this.musicData.isSubscribe = 0;
          Toast({
            message: "积分不足，订阅失败。积分余额为" + resUser.data.data.score,
            position: "bottom",
          });
        } else {
          this.musicData.isSubscribe = 1;
          Toast({ message: "订阅成功,所剩积分" + score, position: "bottom" });
        }
      }
      // 修改订阅
      let param = {
        consumerId: this.musicData.consumerId,
        musicId: this.musicData.musicId,
        isSubscribe: this.musicData.isSubscribe,
      };
      post("/subCol/update", param);
    },
    // 下载
    download() {
      // window.location.href = this.musicData.music.url;
      // 这么多下载方法，只有原生xml有用
      let ajax = new XMLHttpRequest();
      ajax.open("GET", this.musicData.music.url, true);
      ajax.responseType = "blob";
      //ajx.withCredentials = true;//如果跨域
      ajax.onload = function (oEvent) {
        let content = ajax.response;
        let a = document.createElement("a");
        a.download = "歌曲.mp3";
        a.style.display = "none";
        let blob = new Blob([content]);
        a.href = URL.createObjectURL(blob);
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
      };
      ajax.send();
    },
    // 评论
    comment() {
      this.$router.push({
        path: "/list/musicComments",
        query: {
          musicId: this.musicData.musicId,
          userId: this.musicData.consumerId,
        },
      });
    },

    // 下面图标的方法
    // 播放按钮
    play() {
      this.audioMusic = this.$refs.audioMusic;
      if (this.intervals.length === 0) {
        // 播放
        this.audioMusic.play();
        this.executeIntervals();
      } else {
        // 暂停
        this.audioMusic.pause();
        this.clearIntervals();
        this.intervals = [];
      }
    },
    // 上一首
    previous() {
      let index = this.index - 1;
      if (index < 0) {
        Toast("已经是此栏目第一首");
        index = 0;
      } else {
        // 修改返回的跳转数
        localStorage.setItem(
          "goNum",
          Number(localStorage.getItem("goNum")) + 1
        );
        this.$router.push({
          path: "/function/transfer",
          query: {
            musicList: this.musicList,
            index: index,
            userId: this.musicData.consumerId,
            state: 1,
          },
        });
      }
    },
    // 下一首
    next() {
      let index = this.index + 1;
      if (index > this.musicList.length - 1) {
        Toast("已经是此栏目最后一首");
        index = this.musicList.length - 1;
      } else {
        // 修改返回的跳转数
        localStorage.setItem(
          "goNum",
          Number(localStorage.getItem("goNum")) + 1
        );
        this.$router.push({
          path: "/function/transfer",
          query: {
            musicList: this.musicList,
            index: index,
            userId: this.musicData.consumerId,
            state: 1,
          },
        });
      }
    },
    // 重播
    replay() {
      this.audioMusic.currentTime = 0;
      if (this.intervals.length === 0) {
        this.play();
      }
    },
    // 单曲循环
    loop(val) {
      if (val === 0) {
        this.isSingle = 0;
        this.loopSet = () => {
          this.next();
        };
      } else {
        this.isSingle = 1;
        this.loopSet = () => {
          this.replay();
        };
      }
    },
    loopSet() {},

    // 下面是工具方法

    // 执行各种定时操作
    executeIntervals() {
      let id = setInterval(() => {
        // 旋转
        let img = this.$refs.img;
        if (img != null && img != undefined) {
          this.rotateVal += 7;
          img.style.transform =
            "translate( -50%,0)rotate(" + this.rotateVal + "deg)";
          img.style.transition = "0.1s linear";
        }
      }, 100);
      this.intervals.push(id);

      id = setInterval(() => {
        //更新进度条
        this.updateProgress();
        // 当前时间
        this.getCurrentTimeString();
        // 播放完
        if (this.audioMusic.currentTime >= this.audioMusic.duration) {
          this.play();
          this.loopSet();
        }
      }, 900);
      this.intervals.push(id);
    },
    // 取消定时操作
    clearIntervals() {
      this.intervals.forEach((item) => {
        clearInterval(item);
      });
    },
    // 获取总时长
    getTotalTimeString() {
      // url不存在时
      if (this.audioMusic.duration != this.audioMusic.duration) {
        return 0;
      }

      let time = this.audioMusic.duration;
      // 分钟;
      let minute = time / 60;
      let minutes = parseInt(minute);
      if (minutes < 10) {
        minutes = "0" + minutes;
      }
      //秒
      let second = time % 60;
      let seconds = Math.round(second);
      if (seconds < 10) {
        seconds = "0" + seconds;
      }

      this.totalTimeString = minutes + ":" + seconds;
    },
    // 获取当前时长
    getCurrentTimeString() {
      let time = this.audioMusic.currentTime;
      let minutes = parseInt(time / 60);
      if (minutes < 10) {
        minutes = "0" + minutes;
      }
      let seconds = Math.round(time % 60);
      if (seconds < 10) {
        seconds = "0" + seconds;
      }
      // 总共时长的秒数
      this.currentTimeString = minutes + ":" + seconds;
    },
    // 更改进度条
    updateProgress() {
      let totalTime = this.audioMusic.duration;
      let currentTime = this.audioMusic.currentTime;
      let proportion = currentTime / totalTime;
      this.$refs.line1.style.width = `${proportion * 70}%`;
      this.$refs.line2.style.width = `${(1 - proportion) * 70}%`;
    },
    // 背景颜色
    getBackground() {
      let bgList = [
        "linear-gradient(#bdc3c7,#2c3e50)",
        "linear-gradient(#334d50,#cbcaa5)",
        "linear-gradient(#2193b0,#1488CC)",
        "linear-gradient(#1488CC,#FBD786,#536976)",
        "linear-gradient(#0F2027,#203A43,#2c5364)",
        "linear-gradient(#12c2e9,#26a0da,#314755)",
        "linear-gradient(#373B44,#4286f4)",
        "linear-gradient(#4568DC,#B06AB3)",
        "linear-gradient(#6DD5FA,#FFFFFF,#2980B9)",
        "linear-gradient(#BE93C5,#7BC6CC)",
        "linear-gradient(#536976,#292E49)",
        "linear-gradient(#007991,#292E49)",
        "linear-gradient(#E4E5E6,#00416A)",
        "linear-gradient(#7F7FD5,#86A8E7,#076585)",
        "linear-gradient(#00B4DB,#0083B0)",
      ];
      let num = Math.floor(Math.random() * bgList.length);
      this.$refs.body.style.background = bgList[num];
      // 3
      console.log(num, bgList[num], "背景");
    },
  },
};
</script>

<style scoped>
.body {
  height: 100%;
}
/* 顶部 */
.top {
  text-align: center;
  position: relative;
  /* background-color: rgb(195, 160, 160); */
  width: 100%;
  height: 45px;
}
.top .left {
  position: absolute;
  left: 3%;
  top: 50%;
  transform: translate(0, -50%);
}
.top .right {
  position: absolute;
  right: 3%;
  top: 50%;
  transform: translate(0, -50%);
}

/* 指针 */
#discpointer_pic {
  position: absolute;
  right: 40%;
  top: 45px;

  width: 156px;
  height: 125px;
  display: block;
  transition: 0.5s;
  z-index: 10;
}
/* 唱片 */
#status_pic {
  position: absolute;
  left: 50%;
  top: 15%;
  transform: translate(-50%, 0);

  box-shadow: 0px 0px 2px 5px rgba(255, 255, 255, 0.2333);

  width: 250px;
  height: 250px;
  overflow: hidden;
  border-radius: 100%;
  transition: 0.5s;
}
#ddd {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  width: 150px;
  height: 150px;
  display: block;
  border-radius: 100%;
  transition: 0.5s;
  z-index: 2;
}
#ccc {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  background-color: rgb(0, 0, 0);

  width: 250px;
  height: 250px;
  display: block;
  border-radius: 100%;
  transition: 0.5s;
  z-index: 1;
}
/* #status_pic {
  margin: 10% auto;

  width: 250px;
  height: 250px;
  display: block;
  border-radius: 100%;
  transition: 0.5s;
  z-index: 0;
} */

/* 底部图标 */
.bottom {
  height: 140px;
  width: 100%;
  position: absolute;
  bottom: 0%;
}
/* 上一堆 */
.bottom .icons1 .unite {
  font-size: 25px;
  margin-left: 15%;
}
#iconPlay {
  /* 播放垂直居中 */
  position: relative;
  top: 9px;
  font-size: 50px;
}
/* 下一堆 */
.bottom .icons2 .unite {
  font-size: 25px;
  margin-left: 11%;
}

/* 进度 */
.bottom .progress {
  position: relative;
  margin: 10px 0;
}
.bottom .progress .lin {
  margin-left: 4%;
  font-size: 12px;
  color: rgb(199, 199, 199);
  /* color: #d1d6d1; */
}

/* 横线 垂直居中*/
.bottom .progress .line1 {
  display: inline-block;
  margin-bottom: 4px;
  width: 0%;
  height: 1px;
  border-bottom: 1px solid #ffffff;
}
.bottom .progress .mark {
  display: inline-block;
  margin: 0 0 1px 0;

  width: 5px;
  height: 6px;
  background-color: #ffffff;
  border-radius: 100px;
}
.bottom .progress .line2 {
  display: inline-block;
  margin: 0 0 4px 0;
  width: 70%;
  height: 1px;
  border-bottom: 1px solid rgba(207, 207, 207, 0.553);
}
</style>