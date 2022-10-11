<template>
  <div class="large">
    <div v-if="musicList.length > 0" class="musicList">
      <div class="describe">
        <div class="text">{{musicList.describe}}</div>
        <div class="play">播放</div>
      </div>
      <div class="music" v-for="(item, index) in musicList" :key="index" @click="play(item,index)">
        <!-- 左 -->
        <div class="left">
          <div class="number">{{ index + 1 }}</div>

          <div class="picture">
            <img :src="item.pictureUrl" alt="" />
          </div>

          <div class="text">
            <div class="name">{{ item.name }}</div>
            <div class="singer">
              {{ item.singer + "-" + item.name }}
            </div>
          </div>
        </div>
        <!-- 右 -->
        <div class="icon">
          <van-icon class="icon1" name="service-o" size="20px" />
          <van-icon name="ellipsis" size="20px" />
        </div>
        <!--  横线。给div加边框，调整div长度位置 -->
        <div class="last"></div>
      </div>
      <div class="more">
        <div class="text" @click="getMore">
          <span>查看更多</span>
          <span style="color:#1d8bfa">{{musicList.describe}}</span>
          <span>音乐</span>
          <van-icon name="arrow" />
        </div>
      </div>
    </div>

    <div v-else class="noData">
      <van-empty description="暂无数据" />
    </div>
  </div>
</template>

<script>
export default {
  props: ["musicList"],
  data() {
    return {};
  },

  methods: {
    play(item, index) {
      let musicList = [];
      this.musicList.forEach((element) => {
        musicList.push(element.id);
      });
      // console.log(item);

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
    getMore() {
      this.$emit("getMore", this.musicList.describe);
    },
  },
};
</script>



<style scoped>
.musicList {
  border-radius: 13px;
  overflow: hidden;
  margin-top: 10px;
  width: 96%;
  margin-left: 2%;
  background-color: #ffffff;
  box-shadow: 4px 4px 4px 1px rgba(119, 119, 119, 0.1);
}
/* 描述 */
.describe {
  width: 100%;
  height: 45px;
  position: relative;
  /* border: #0e0e0e; */
  border-bottom: 1px solid #e6e6e6;
}
.describe .text {
  position: absolute;
  left: 4%;
  top: 50%;
  transform: translate(0, -50%);
  font-size: 17px;
  font-weight: bold;
}
.describe .play {
  position: absolute;
  right: 6%;
  top: 50%;
  transform: translate(0, -50%);
  font-size: 12px;
  border: #e6e6e6 1px solid;
  border-radius: 100px;
  padding: 1px 6px;
}

/* 一行音乐 */
.music {
  width: 100%;
  height: 70px;
  position: relative;
}

.number {
  position: absolute;
  top: 50%;
  left: 3%;
  transform: translate(0, -50%);

  font-size: 18px;

  color: #999999;
}
.picture {
  position: absolute;
  top: 50%;
  left: 10%;
  transform: translate(0, -50%);

  width: 42px;
  height: 42px;
  overflow: hidden;
  border-radius: 10px;

  object-fit: cover;
}
.picture img {
  width: 100%;
  height: 100%;
}
/* 一行 中间文本 */
.music .text {
  position: absolute;
  top: 50%;
  left: 24%;
  transform: translate(0, -50%);

  height: 42px;
  width: 80%;
  /* background-color: rgb(183, 172, 172); */
}
.music .text .name {
  font-size: 15px;
  color: #0e0e0e;
}
.music .text .singer {
  position: absolute;
  bottom: 0%;
  margin-bottom: 0px;

  font-size: 12px;
  color: #919191;
  width: 100px;
  /* 省略号 */
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}
/* 右边图标 */
.icon {
  position: absolute;
  top: 50%;
  right: 5%;
  transform: translate(0, -50%);
}
.icon .icon1 {
  margin-right: 24px;
}

/* 横线。给div加边框，调整div长度位置 */
.last {
  position: absolute;
  height: 0px;
  width: 90%;
  /* 先左后上 */
  left: 50%;
  bottom: 0%;
  transform: translate(-50%, 0);

  border-bottom: 1px solid #e6e6e6;
}

/* 更多 */
.more {
  height: 35px;

  text-align: center;
  line-height: 35px;
  font-size: 14px;
  color: #878787;
}
/* .more .text {
  position: absolute;
  right:6%;
  top: 50%;
  transform: translate(0, -50%);
  
  font-size: 17px;
  font-weight: bold;
} */
</style>