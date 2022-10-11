<template>
  <div class="home">
    <!-- 顶部导航栏 -->
    <div class="navbar">
      <van-nav-bar @click-left="onClickLeft">
        <template #left >
          <van-icon @click-left="onClickLeft" size="21" name="https://s1.ax1x.com/2022/06/29/jnHn9f.png" />
        </template>
        <template #right  >
           <!-- style="width:300px" -->
          <van-search shape="round"  style="width:300px" v-model="value" show-action placeholder="请输入搜索关键词" @search="onSearch" @click="onSearch">
            <template #action>
              <div @click="onSearch">搜索</div>
            </template>
          </van-search>
        </template>
      </van-nav-bar>
    </div>
    <!-- 轮播图 -->
    <div class="swiper">
      <van-swipe class="my-swipe" :autoplay="3000" indicator-color="white">
        <van-swipe-item v-for="(item, index) in activityList" :key="index" @click="toActivity(item.id)">
          <img :src="item.pictureUrl" class="img2">
        </van-swipe-item>
      </van-swipe>
    </div>
    <!-- 宫格 -->
    <div class="grid">
      <van-grid column-num="4">
        <van-grid-item icon="service-o" text="每日推荐" @click="toGrid(0)" />
        <van-grid-item icon="bar-chart-o" text="排行榜" @click="toGrid(1)" />
        <van-grid-item icon="fire-o" text="热评" @click="toGrid(2)" />
        <van-grid-item icon="play-circle-o" text="最多订阅" @click="toGrid(3)" />
      </van-grid>
    </div>
    <!-- 歌曲详情 -->
    <music :musicList="musicList" @getMore="getMoreMusic"></music>

    <div style="height:60px"></div>
  </div>
</template>

<script>
import music from "../../components/Music.vue";
import { post } from "../../http/axios.js";
// import Vue from 'vue';
// import { Lazyload } from 'vant';
// Vue.use(Lazyload);
export default {
  components: {
    music: music,
  },
  data() {
    return {
      value: "",
      activityList: [
        { pictureUrl: '"https://s1.ax1x.com/2022/06/13/XWKwvj.jpg"' },
      ],
      musicList: [],

      musicPage: 6,
    };
  },

  created() {
    this.getMusic();
    this.getActivity();
  },
  methods: {
    onClickLeft() {
      this.$router.push("/function/play");
    },
    onClickRight() {},
    // 宫格跳转
    toGrid(index) {
      this.$router.push("/list/hot/" + index);
    },
    toSingers() {
      this.$router.push("/list/singers");
    },
    // 搜索框
    onSearch() {
      this.$router.push("/function/search");
    },
    // 轮播图活动
    toActivity(id) {
      this.$router.push({
        path: "/content/activity",
        query: { activityId:id },
      });
    },
    // 播放
    play() {
      this.$router.push("/function/play/");
    },
    // 请求音乐
    async getMusic() {
      let param = {
        page: this.musicPage,
        pageSize: 10,
      };
      let res = await post("/music/findByPage", param);
      this.musicList = res.data.data.dataList;
      this.musicList.describe = "单曲";
      return Promise.resolve();
    },
    // 请求活动
    async getActivity() {
      let param = {
        page: 1,
        pageSize: 5,
      };
      let res = await post("/activity/findByPage", param);
      this.activityList = res.data.data.dataList;
      // console.log(this.activityList, "活动");
    },
    // 获取更多音乐，监听子组件
    async getMoreMusic(sonMsg) {
      //进行请求音乐
      let mList = this.musicList;
      this.musicPage++;
      await this.getMusic();
      // console.log(this.musicList.length);

      if (this.musicList.length === 0) {
        Toast("已加载全部" + "单曲 音乐");
      }
      this.musicList = mList.concat(this.musicList);
      this.musicList.describe = "单曲";
    },
  },
};
</script>

<style>
.van-search {
  padding: 5px;
}
.my-swipe .van-swipe-item {
  height: 148px;
  /* color: #fff; */
  /* font-size: 20px; */
  /* line-height: 150px; */
  /* text-align: center; */
  background-color: #39a9ed;

  /* overflow: hidden; */
}
.img2 {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

van-grid-item {
  width: 20px;
}
.grid {
  /* width: 96%;
  margin-left: 2%; */

  border-bottom-left-radius: 12px;
  border-bottom-right-radius: 12px;
  overflow: hidden;
  /* box-shadow: 4px 4px 4px 1px rgba(119, 119, 119, 0.5); */
}
</style>
