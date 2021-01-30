<template>
  <v-container class="pa-0">
    <v-col class="d-flex">
      <div class="order-btn-wrap ondaji_color_grey_20 d-flex align-center">
        <orderBtn :orderList="bannerList" @orderComplete="orderComplete" />
      </div>

      <div
        class="preview-btn-wrap ondaji_color_grey_20 d-flex align-center ml-auto"
      >
        <v-btn
          class="preview-btn"
          text
          depressed
          :dialog.sync="previewLocalBoxDialog"
          @click="previewLocalBoxDialog = true"
        >
          <v-img
            contain
            width="23"
            src="@/assets/images/icons/preview.svg"
          ></v-img>
        </v-btn>
      </div>

      <div
        class="upload-btn-wrap ondaji_color_grey_20 d-flex align-center ml-2"
      >
        <v-btn class="upload-btn" text depressed @click="upload">
          업로드
        </v-btn>
      </div>
    </v-col>

    <previewLocalBoxModal :dialog.sync="previewLocalBoxDialog" />
  </v-container>
</template>

<script>
import { commonMediaShop } from '@/views/mediaShop/mixins/commonMixins'

import orderBtn from '@/components/table/orderBtn'
import previewLocalBoxModal from '../previewLocalBoxModal'

export default {
  mixins: [commonMediaShop],
  components: {
    orderBtn,
    previewLocalBoxModal,
  },
  props: {
    bannerList: {
      type: Array,
      required: false,
    },
  },
  data() {
    return {
      headerInfo: [],
      previewLocalBoxDialog: false,
    }
  },
  methods: {
    orderComplete(retList) {
      this.$emit('orderComplete', retList)
    },
    upload() {
      this.$emit('upload')
    }
  },
}
</script>

<style lang="scss" scoped>
.order-btn-wrap {
  width: 138px;
  height: 26px;
  border-radius: 3px;
}

.preview-btn-wrap {
  border-radius: 3px;
}

.preview-btn {
  padding: 3px !important;
  min-width: 26px !important;
  height: 26px !important;
}

.upload-btn-wrap {
  background: #3e2723;
}

.upload-btn {
  background: #3e2723;
  height: 26px !important;
  font-size: 12px;
  font-weight: bold;
  color: white;
}
</style>
