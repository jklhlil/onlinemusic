<template>
  <div>
    <van-nav-bar left-text="返回" left-arrow @click-left="onClickLeft">
      <template #right>
        <van-icon @click="toPlaying" style="margin-right:10px" name="https://s1.ax1x.com/2022/06/29/jnHn9f.png" size="18" />
      </template>
    </van-nav-bar>

    <!-- tab选项卡 -->
    <div class="switchTab">
      <van-tabs v-model="activeKey" @click="switchTabHandler">
        <van-tab v-for="(item, index) in statusArr" :key="index" :title="item.name">
          <music :musicList="musicList" @getMore="getMoreMusic"></music>
        </van-tab>
      </van-tabs>
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
      activeKey: 0,
      musicList: [],
      statusArr: [
        {
          name: "我的收藏",
          url: "/subCol/findColMusicById",
          param: {
            data: localStorage.getItem("userId"),
            page: 1,
            pageSize: 10,
          },
        },
        {
          name: "我的订阅",
          url: "/subCol/findSubMusicById",
          param: {
            data: localStorage.getItem("userId"),
            page: 1,
            pageSize: 10,
          },
        },
      ],
    };
  },

  created() {
    // path传值时字符串！！！！
    this.activeKey = Number(this.$route.params.id);
    this.switchTabHandler(this.activeKey);
  },

  methods: {
    onClickLeft() {
      //Toast('返回');
      this.$router.go(-1)
    },

    // 请求音乐
    async switchTabHandler(index) {
      this.activeKey = index;
      location.replace("#/list/collectsubscribe/" + index);

      let res = await post(
        this.statusArr[index].url,
        this.statusArr[index].param
      );
      if (res.data.data != null) {
        this.musicList = res.data.data.dataList;
        this.musicList.describe = this.statusArr[index].name;
      } else {
        this.musicList = [];
        this.musicList.describe = this.statusArr[index].name;
      }
      // console.log(this.musicList, this.statusArr[index].name);
      return Promise.resolve();
    },
    toPlaying() {
      this.$router.push("/function/play");
    },
    // 获取更多音乐，监听子组件
    getMoreMusic(sonMsg) {
      // 根据传过来的信息，进行请求音乐
      this.statusArr.forEach((item, index) => {
        if (item.name === sonMsg) {
          this.getMusic(index);
        }
      });
    },
    // 异步方法，从getMore单独拿出来
    async getMusic(index) {
      let mList = this.musicList;
      this.statusArr[index].param.page++;
      await this.switchTabHandler(index);
      // console.log(this.musicList.length);

      if (this.musicList.length === 0) {
        Toast("已加载全部 " + this.statusArr[index].name + " 音乐");
      }
      this.musicList = mList.concat(this.musicList);
      this.musicList.describe = this.statusArr[index].name;
    },
  },
};
</script>

<style scoped>
</style>