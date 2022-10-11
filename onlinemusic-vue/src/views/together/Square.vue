<template>
  <div>
    <van-nav-bar left-text="返回" title="活动" left-arrow @click-left="onClickLeft">
      <template #right>
        <van-icon @click="toPlaying" style="margin-right:10px" name="https://s1.ax1x.com/2022/06/29/jnHn9f.png" size="18" />
      </template>
    </van-nav-bar>

    <!-- 活动 -->
    <van-card v-for="item in activityList" :key="item.id" :desc="item.content" :title="item.name" :thumb="item.pictureUrl" @click="toActivity(item.id)">
      <div slot="footer">
        <van-icon name="clock-o" />
        <a class="activityTime">{{ item.startTime }}-{{ item.deadLine }}</a>
      </div>
    </van-card>

    <div style="height:60px"></div>
  </div>
</template>

<script>
import { post } from "../../http/axios.js";
export default {
  data() {
    return {
      activityList: [],
    };
  },
  created() {
    this.getActivity();
  },
  methods: {
    onClickLeft() {
      //Toast('返回');
      this.$router.go(-1);
    },
    // 前往活动详情
    toActivity(id) {
      this.$router.push({
        path: "/content/activity",
        query: { activityId:id },
      });
    },

    // 请求活动
    async getActivity() {
      let param = {
        page: 2,
        pageSize: 15,
      };
      let res = await post("/activity/findByPage", param);
      this.activityList = res.data.data.dataList;
      // console.log(this.activityList, "活动");
    },
    toPlaying() {
      this.$router.push("/function/play");
    },
  },
};
</script>

<style scoped>
.activityTime {
  color: #1989fae2;
}
</style>