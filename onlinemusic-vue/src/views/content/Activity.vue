<template>
  <div class="body">
    <div class="navBar">
      <van-sticky>
        <van-nav-bar @click-left="onClickLeft" :title="activity.name" left-text="返回" left-arrow />

      </van-sticky>
    </div>
    <!-- 活动详情 -->
    <div class="activity">
      <!-- 活动的图片 -->
      <div class="picture">
        <img class="img1" :src="activity.pictureUrl" />
      </div>

      <!-- 活动开始截至时间 -->
      <div class="time">
        <span class="timecomtent"> 时间&nbsp;&nbsp;&nbsp; {{activity.startTime}}——{{activity.deadLine}}</span>
      </div>

      <!-- 活动详细介绍 -->
      <div>
        <div>
          <span class="comtent">活动详情</span>
        </div>
        <span><span style="margin-left:20px"></span> {{activity.content}}</span>
      </div>
    </div>
    <div class="time">
      <span class="timecomtent">参加者</span>
    </div>
    <!-- 参加者 -->
    <div class="users" v-for="(item, index) in userList" :key="index">
      <div class="user">
        <img class="img" :src="item.consumer.pictureUrl" />
        <span class="name">{{ item.consumer.name }}</span>
      </div>
    </div>
    <van-tabbar class="bottom">
      <div class="join" @click="join">
        <van-button class="join" round block type="info" v-if="isJoin===0">立即参加</van-button>
        <van-button class="join" round block type="danger" v-else>取消参加</van-button>
      </div>
    </van-tabbar>
  </div>
</template>


<script>
import { Toast } from "vant";
import { post } from "../../http/axios.js";
import axios from "axios";
export default {
  data() {
    return {
      activityId: Number(this.$route.query.activityId),
      userId: Number(localStorage.getItem("userId")),
      activityUserId: null,
      activity: {},
      userList: [],
      isJoin: 0,
    };
  },
  created() {
    this.getActivity();
    // console.log(this.activity, "活动");
    this.getUser();
  },
  methods: {
    onClickLeft() {
      this.$router.go(-1);
    },
    // 请求活动
    async getActivity() {
      let res = await post("/activity/findById", this.activityId);
      this.activity = res.data.data;
      // console.log(this.activity, "活动");
    },
    // 获取参加此活动的人
    async getUser() {
      let param = {
        page: 1,
        pageSize: 18,
        data: this.activityId,
      };

      let res = await post("/actiCons/findConsumerById", param);
      this.userList = res.data.data.dataList;
      this.userList.forEach((item, index) => {
        if (item.consumerId === this.userId) {
          this.isJoin = 1;
          this.activityUserId = item.id;
        }
      });
      // console.log(this.userList, "参加活动的人");
    },
    join() {
      if (this.isJoin === 0) {
        this.isJoin = 1;
        let param = {
          activityId: this.activity.id,
          consumerId: this.userId,
        };
        post("/actiCons/join", param).then(() => {
          this.getUser();
          Toast("已参加");
        });
      } else {
        this.isJoin = 0;
        axios.delete("/actiCons/cancelById/" + this.activityUserId).then(() => {
          this.getUser();
          Toast("已取消参加");
        });
      }
    },
  },
};
</script>

<style scoped>
.body {
  background-color: rgb(255, 255, 255);
  padding: 0px;
  margin: 0px;
}

/* 活动 */
.img1 {
  width: 100%;
}
.picture {
  width: 100%;
}

/* 时间 */
.time {
  height: 40px;
  width: 100%;
  background-color: white;
  color: rgb(161, 161, 161);
}
.time .timecomtent {
  line-height: 40px;
  margin-left: 16px;

  font-size: 15px;
}
.comtent {
  font-weight: 550;
}

/* 用户 */
.users {
  height: 50px;
  margin-top: 5px;
  width: 100%;
}

/* 图片*/
.user {
  margin-top: 5px;
  height: 50px;
  font-size: 17px;
  line-height: 39px;
}
.user .img {
  width: 48px;
  height: 48px;
  border-radius: 100%;
  vertical-align: middle;
  object-fit: cover;
}
.name {
  line-height: 20px;
  font-size: 16px;
  color:  rgb(92, 92, 92)
}

/* 按钮 */
.bottom .join {
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  width: 80%;
  height: 88%;
}
</style>