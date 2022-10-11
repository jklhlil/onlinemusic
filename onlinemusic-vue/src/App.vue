<template>
  <div id="app">
    <!-- 全局的根组件 -->
    <router-view></router-view>
    <audio muted="muted" ref="audioMusic" src="1"></audio>
  </div>
</template>

<script>
import { Toast } from "vant";
import { post } from "./http/axios";
import axios from "axios";

console.log("全局", 111132421244213);
export default {
  data() {
    return {
      // musicStorage: {},
      audioMusic: {},
      loopId: null,
      music: {},

      commentInterval: null,
      user: {},
    };
  },
  watch: {
    $route: {
      handler: function (val, oldVal) {
        // 从播放页面出来，         播放
        // setTimeout才能 获得更新后的sessionStorage
        setTimeout(() => {
          if (
            oldVal.path === "/function/play" &&
            val.path != "/function/transfer" &&
            Number(sessionStorage.getItem("isPlay")) === 1
          ) {
            // 这里一定要用setAttribute，用vue的动态绑定不能及时更新src。
            this.$refs.audioMusic.setAttribute(
              "src",
              sessionStorage.getItem("musicUrl")
            );
            this.$refs.audioMusic.currentTime = Number(
              sessionStorage.getItem("currentTime")
            );
            // console.log(this.$refs.audioMusic, "对象,出");
            this.$refs.audioMusic.play();
            this.$refs.audioMusic.volume = 0.1;

            // 全局这首结束，播放下一首
            this.loopId = setInterval(() => {
              if (this.audioMusic.currentTime >= this.audioMusic.duration) {
                this.editScroe();
                this.playNext();
              }
            }, 1500);
          } else {
          }
        }, 1);

        // 进入播放界面，          暂停
        if (
          val.path === "/function/play" &&
          oldVal.path != "/function/transfer" &&
          Number(sessionStorage.getItem("isPlay")) === 1
        ) {
          sessionStorage.setItem(
            "currentTime",
            this.$refs.audioMusic.currentTime
          );
          // console.log(this.$refs.audioMusic, "对象，进");
          this.$refs.audioMusic.pause();

          clearInterval(this.loopId);
        } else {
        }

        // 前往登录注册页面，    停止
        if (val.path === "/login" || val.path === "/register") {
          this.$refs.audioMusic.currentTime = 0;
          this.$refs.audioMusic.pause();
          clearInterval(this.loopId);
        }

        //!!! 前往评论界面   2.2刷新全局前，保存一次currentTime
        // if (oldVal.path != "/") {
        //   if (this.$refs.audioMusic != null) {
        //     sessionStorage.setItem(
        //       "currentTime",
        //       this.$refs.audioMusic.currentTime
        //     );
        //   }
        // } else {
        // }
      },
      // 深度监听路由
      deep: true,
    },
  },
  created() {
    this.getUser();
    setTimeout(() => {
      this.audioMusic = this.$refs.audioMusic;

      //!!! 解决 评论div 乱放    1.刷新全局时，创建music
      if (Number(sessionStorage.getItem("isPlay")) === 1) {
        // 这里一定要用setAttribute，用vue的动态绑定不能及时更新src。
        this.$refs.audioMusic.setAttribute(
          "src",
          sessionStorage.getItem("musicUrl")
        );
        this.$refs.audioMusic.currentTime = Number(
          sessionStorage.getItem("currentTime")
        );
        this.$refs.audioMusic.play();
        this.$refs.audioMusic.volume = 0.1;
      }
      //!!!  2.1 刷新全局前，不断保存currentTime。
      if (this.commentInterval === null) {
        // console.log("this.commentInterval===null");
        this.commentInterval = setInterval(() => {
          if (this.$refs.audioMusic != null) {
            sessionStorage.setItem(
              "currentTime",
              this.$refs.audioMusic.currentTime
            );
          }
        }, 200);
      }
    }, 200);
  },
  methods: {
    //播放下一首
    async playNext() {
      let musicList = JSON.parse(sessionStorage.getItem("musicList"));
      let musicIndex = Number(sessionStorage.getItem("musicIndex"));

      let musicIndex1 = musicIndex;
      // console.log(musicList.length,musicIndex);

      if (musicList.length - 1 === musicIndex) {
        Toast("已播放至最后一首，将循环播放");
      } else {
        Toast("正在播放下一首");
        musicIndex1 = musicIndex + 1;
        sessionStorage.setItem("musicIndex", musicIndex1);
      }
      // 请求下一首，播放
      let musicId = musicList[musicIndex1];
      let res = await post("/music/findById/" + musicId);

      this.$refs.audioMusic.setAttribute("src", res.data.data.url);
      this.$refs.audioMusic.play();
      this.$refs.audioMusic.volume = 0.1;
    },
    async getUser() {
      let res = await post(
        "/consumer/findById",
        localStorage.getItem("userId")
      );
      this.user = res.data.data;
    },
    // 更新积分
    editScroe() {
      let param = {
        id: localStorage.getItem("userId"),
        score: this.user.score + 1,
      };
      axios.put("/consumer/edit", param).then((q) => {
        console.log(q);
        this.getUser();
      });
    },
  },
  destroyed() {
    clearInterval(this.loopId);
  },
};
</script>

<style>
/* 全局设置样式 */
html,
body,
#app {
  height: 100%;
  background-color: #f0f0f0;
}
</style>
