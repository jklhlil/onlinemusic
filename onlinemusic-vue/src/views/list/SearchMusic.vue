<template>
  <div>
    <!-- 顶部导航栏 -->
    <div class="navbar">
      <van-nav-bar left-text="返回" @click-left="onClickLeft">
        <template #right>
         <!-- style="width:300px" -->
          <van-search shape="round" style="width:300px"
          v-model="searchContent" show-action placeholder="请输入搜索关键词" @search="search" input-align="center">
            <template #action>
              <div @click="search">搜索</div>
            </template>
          </van-search>
        </template>
      </van-nav-bar>
    </div>
    <!-- 歌曲 -->
    <div v-if="musicList1.length > 0">
      <music :musicList="musicList1" @getMore="getMore1"></music>
    </div>
    <!-- 歌手 -->
    <div v-if="musicList2.length > 0">
      <music :musicList="musicList2" @getMore="getMore2"></music>
    </div>
    <!-- 无数据 -->
    <div v-if="musicList1.length === 0 && musicList2.length === 0">
      <music :musicList="[]"></music>
    </div>
  </div>
</template>

<script>
import { post } from "../../http/axios.js";
import music from "../../components/Music.vue";
import { Toast } from "vant";
export default {
  components: {
    music: music,
  },
  data() {
    return {
      musicList: [],
      musicList1: [],
      musicList2: [],
      searchContent: this.$route.query.searchContent,

      musicPage1: 1,
      musicPage2: 1,
    };
  },
  created() {
    this.searchContent = this.$route.query.searchContent;
    this.search();
  },
  methods: {
    onClickLeft() {
      //Toast('返回');
      this.$router.push("/manage/home");
    },
    search() {
      this.search1();
      this.search2();
    },

    // 模糊搜索歌名
    async search1() {
      let param = {
        page: this.musicPage1,
        pageSize: 9,
        data: this.searchContent,
      };
      let res1 = await post("/music/findByName", param);
      // console.log(res1, "歌名音乐");
      if (res1.data.data != null) {
        this.musicList1 = res1.data.data.dataList;
        this.musicList1.describe = "单曲 " + this.searchContent;
      } else {
        this.musicList1 = [];
      }
      return Promise.resolve();
    },
    // 模糊搜索歌手
    async search2() {
      let param = {
        page: this.musicPage2,
        pageSize: 9,
        data: this.searchContent,
      };
      let res2 = await post("/music/findBySinger", param);
      // console.log(res2, "歌手音乐");
      if (res2.data.data != null) {
        this.musicList2 = res2.data.data.dataList;
        this.musicList2.describe = "歌手 " + this.searchContent;
      } else {
        this.musicList2 = [];
      }
      return Promise.resolve();
    },
    // 获取更多歌名
    async getMore1(sonMsg) {
      this.musicPage1++;
      let mList = this.musicList1;
      await this.search1();
      if (this.musicList1.length === 0) {
        Toast("已加载全部" + "单曲 " + this.searchContent + "音乐");
      } 
        this.musicList1 = mList.concat(this.musicList1);
        this.musicList1.describe = "单曲 " + this.searchContent;
    },
    // 获取更多歌手
    async getMore2(sonMsg) {
      this.musicPage2++;
      let mList = this.musicList2;
      await this.search2();
      // console.log(this.musicList2); 
      if (this.musicList2.length === 0) {
        Toast("已加载全部" + "歌手 " + this.searchContent + "音乐");
      } 
        this.musicList2 = mList.concat(this.musicList2);
        this.musicList2.describe = "歌手 " + this.searchContent;
    },
  },
};
</script>

<style scoped>
</style>