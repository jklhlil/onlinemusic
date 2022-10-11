<template>
  <div>
    <van-nav-bar left-text="返回" left-arrow @click-left="onClickLeft" />

    歌手页面
    
  </div>
</template>

<script>
export default {
  data() {
    return {
      
    };
  },
  methods: {
    onClickLeft() {
      //Toast('返回');
      this.$router.go(-1);
    },
  },
};
</script>

<style scoped>
</style>