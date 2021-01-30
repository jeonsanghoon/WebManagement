<template>
  <div v-if="info != undefined">
    <!-- 이미지 -->
    <div v-if="info.thumbImageUrl != undefined" class="img-wrap">
      <v-img class="banner-img" :src="`/files/${info.thumbImageUrl}`" />
    </div>
    <div v-else class="empty-img-wrap">
      <v-img
        class="empty-img"
        :src="require(`@/assets/images/icons/image_s6.svg`)"
      />
    </div>
    <!-- 타이틀 -->
    <p class="card-title ondaji_font_16_bold_grey_100 text-truncate">
      {{ info.title }}
    </p>
    <!-- 지역명 -->
    <div class="card-content-wrap d-flex ondaji_font_10_medium_green_100">
      <span
        v-for="(address, index) in info.address"
        class="card-content text-truncate"
        :key="index"
      >
        {{ address }}
        <span v-if="index != info.address.length - 1">·</span>
      </span>
    </div>
    <!-- 부제목 -->
    <p class="card-sub-title ondaji_font_11_grey_70">
      {{ info.subTitle }}
    </p>
    <!-- 서비스 날짜 -->
    <p class="ondaji_font_10_medium_grey_60">{{ info.serviceDate }}</p>
  </div>
</template>

<script>
export default {
  props: {
    info: {
      type: Object,
      required: false,
    },
  },
}
</script>

<style lang="scss" scoped>
p {
  margin: 0;
  padding: 0;
}

.img-wrap .banner-img {
  width: 174px;
  height: 174px;
}

.empty-img-wrap .empty-img {
  width: 150px;
  height: 150px;
}

.empty-img-wrap {
  width: 174px;
  height: 174px;
  background: #d1c8c6;
  padding: 12px 0;
}

.v-image {
  display: block;
  margin: 0 auto;
}

.card-title {
  line-height: 16px;
  margin: 8px 0;
}

.card-content-wrap {
  margin-bottom: 8px;
}

.card-content {
  line-height: 10px;
  max-width: 55px;
}

.card-sub-title {
  margin-bottom: 14px;
  max-height: 42px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
}

@media screen and (max-width: $grid-breakpoints_sm) {
  .empty-img-wrap {
    width: 146px;
    height: 146px;
  }

  .img-wrap .banner-img {
    width: 146px;
    height: 146px;
  }

  .empty-img-wrap .empty-img {
    width: 122px;
    height: 122px;
  }

  .card-content {
    max-width: 43px;
  }
}
</style>
