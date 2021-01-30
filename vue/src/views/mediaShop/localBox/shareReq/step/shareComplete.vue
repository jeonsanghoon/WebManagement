<template>
  <v-container class="pa-0">
    <div class="main-wrap">
      <p class="title-text ondaji_font_14_bold_brown_100">
        광고 요청이 완료되었습니다!
      </p>
      <div class="btn-wrap">
        <v-btn
          class="share-btn"
          color="#F5EEE8"
          height="32"
          rounded
          depressed
          @click="close"
        >
          <span>공유현황 보기</span>
        </v-btn>
      </div>
    </div>
  </v-container>
</template>

<script>
export default {
  methods: {
    close() {
      this.$emit('close')
    },
  },
}
</script>

<style lang="scss" scoped>
p {
  margin: 0;
  padding: 0;
}

.main-wrap {
  text-align: center;
  padding: 18px 24px;
}

.title-text {
  margin-top: 100px;
  margin-bottom: 94px;
}

.btn-wrap {
  display: flex;
  justify-content: flex-end;

  .share-btn {
    padding: 0 32px !important;

    span {
      font: {
        size: 14px;
        weight: 600;
        color: #3e2723;
      }
    }
  }
}
</style>
