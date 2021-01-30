<template>
  <v-dialog
    class="check-input-dialog ma-0"
    v-model="dialog"
    max-width="340"
    persistent
  >
    <v-card class="py-5 px-2 px-sm-7">
      <v-row class="mb-7" no-gutters>
        <v-col cols="12">
          <span class="check-title">필수 입력 항목을 확인해 주세요.</span>
        </v-col>
      </v-row>

      <v-row no-gutters>
        <v-col class="content-box pa-5" cols="12">
          <v-row v-if="info.checkTitle === false" class="mb-2" no-gutters>
            <v-col cols="12">
              <span class="check-content"
                ><v-icon small>done</v-icon>배너명</span
              >
            </v-col>
          </v-row>

          <v-row v-if="info.checkRange === false" class="mb-2" no-gutters>
            <v-col cols="12">
              <span class="check-content"
                ><v-icon small>done</v-icon>광고영역 설정</span
              >
            </v-col>
          </v-row>

          <v-row v-if="info.checkFile === false" no-gutters>
            <v-col cols="12">
              <span class="check-content">
                <v-icon small>done</v-icon>이미지 또는 영상 1개 이상
              </span>
            </v-col>
          </v-row>
        </v-col>
      </v-row>

      <v-row class="text-end mt-7" no-gutters>
        <v-col cols="12">
          <span class="check-back" @click="close">돌아가기</span>
        </v-col>
      </v-row>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  props: {
    info: {
      type: Object,
      required: false,
      default: null,
    },
    dialog: {
      type: Boolean,
      required: true,
      default: false,
    },
  },
  data() {
    return {
      mainBannerInfo: null,
      subBannerInfo: null,
    }
  },
  created() {},
  methods: {
    close() {
      this.$emit('update:dialog', false)
    },
  },
}
</script>

<style lang="scss" scoped>
.check-title {
  font-weight : normal;
  color: #5c4c47;
}

.content-box {
  border: 2px solid #e0e0e0;
}

.check-content {
  font-weight: normal;
  color: #a9a09e;
}

.check-back {
  font-weight: bold;
  color: #9f9693;
  cursor: pointer;
}

::v-deep {
  .v-icon {
    margin-right: 1rem;
  }
}
</style>
