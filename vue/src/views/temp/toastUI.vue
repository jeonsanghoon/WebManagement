<template lang="pug">
  v-container
    v-row(class="mx-5" justify="center" no-gutters)
      v-col(lg="2")
        toast-Image(:dialog="this.imageOption.dialog" :imagePath="this.imageOption.imagePath" @imageSaveCallback="imageSaveCallback")
        Vuetify-button(class="ma-2" :name="'btnNew'" :btntype="this.$Config.vuetify.btn.type.new" :text="'이미지편집'" @btnNew_Click="btnClick")
        div(class="title mb-1") 결과
        p
          v-img(:src="this.imageOption.imagePath" aspect-ratio="1.7" style="width:300px;height:auto")
</template>

<script>
export default {
  data() {
    return {
      imageOption: {
        dialog: false /* 필수 */,
        imagePath:
          "/file/download/202003/81a617eb-fc1c-44eb-82f0-612975cd9c47.png" /* 필수 없으면 빈값 */,
      },
    }
  },
  methods: {
    btnClick() {
      this.imageOption.dialog = true
    },
    imageSaveCallback(res) {
      this.imageOption.dialog = false
      if (res !== null) {
        console.log("저장성공", res)
        this.imageOption.imagePath = "/file/download/" + res[0].attachmentUrl
      } else console.log("저장실패")
    },
  },
}
</script>
