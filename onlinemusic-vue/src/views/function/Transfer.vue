<template>
  <div>

  </div>
</template>

<script>
export default {
  created() {

    // console.log(this.$route.query.my === '1',1);
    // if (this.$route.query.my === '1') {
    //   this.$router.replace({
    //     path: "/list/mycomments/",
    //     query: {
    //       userId: localStorage.getItem('userId'),
    //     },
    //   });
    //   return 0;
    // }
    this.toPlay();
  },

  methods: {
    toPlay() {
      this.$router.push({ path: "/function/play", query: this.$route.query });
    },
  },
};
</script>

<style scoped>
</style>