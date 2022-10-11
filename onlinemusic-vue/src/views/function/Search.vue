<template>
  <div>
    <!-- 顶部导航栏 -->
    <div class="navbar">
      <van-nav-bar left-text="返回" @click-left="onClickLeft">
        <template #right>
          <!-- style="width:300px" -->
          <van-search shape="round" style="width:300px" v-model="searchContent" show-action autofocus="true" placeholder="请输入搜索关键词" input-align="center" @search="onSearch">
            <template #action>
              <div @click="onSearch">搜索</div>
            </template>
          </van-search>
        </template>
      </van-nav-bar>
    </div>
    <div style="margin: 20px">数据较少，可搜索 “许” “南” “明” “子” “宋” 等</div>
  </div>
</template>

<script>
import { Dialog } from "vant";
export default {
  data() {
    return {
      searchContent: "",
    };
  },
  methods: {
    onClickLeft() {
      //Toast('返回');
      this.$router.push("/manage/home");
    },
    onSearch() {
      if (this.searchContent === "") {
        Dialog({ message: "请输入搜索内容" });
      } else {
        this.$router.push({
          path: "/list/searchmusic",
          query: { searchContent: this.searchContent },
        });
      }
    },
  },
};
</script>

<style scoped>
.serchDiv {
  width: 80%;
}
</style>