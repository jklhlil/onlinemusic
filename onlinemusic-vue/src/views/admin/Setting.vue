<template>
  <div>
    <!-- action="http://localhost:8089/upload"    method="post" enctype="multipart/form-data"-->
    <form ref="form" @click="onSubmit" target="posthere">
      <input ref="file" type="file" name="file" />
      <input type="submit" value="文件上传" />
    </form>
    <div>1</div>
    <iframe name="posthere" frameborder=0 width=0 height=0></iframe>
    <!-- <el-upload class="upload-demo" drag action="http://localhost:8089/upload" >
      <i class="el-icon-upload"></i>
      <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
      <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过500kb</div>
    </el-upload> -->
    <div :span="24" style=""></div>
  </div>
</template>


<script>
export default {
  data() {
    return {};
  },
  methods: {
    onSubmit() {
      // let formData=new FormData(this.$refs.form);
      // console.log(formData.get('file'),'有');

      // let formData2=new FormData();
      //   formData2.append('file',this.$refs.file)
      // console.log(formData2.get('file'));

      console.log(this.$refs.file.files, "直接");

      // formData.append('name',41)
      // console.log(formData.get('name'));
    },
  },
};

// export default {
//   data() {
//     return {
//       imageUrl: "",
//     };
//   },
//   methods: {
//     handleAvatarSuccess(res, file) {
//       this.imageUrl = URL.createObjectURL(file.raw);
//     },
//     beforeAvatarUpload(file) {
//       const isJPG = file.type === "image/jpeg";
//       const isLt2M = file.size / 1024 / 1024 < 2;

//       if (!isJPG) {
//         this.$message.error("上传头像图片只能是 JPG 格式!");
//       }
//       if (!isLt2M) {
//         this.$message.error("上传头像图片大小不能超过 2MB!");
//       }
//       return isJPG && isLt2M;
//     },
//   },
// };
</script>


