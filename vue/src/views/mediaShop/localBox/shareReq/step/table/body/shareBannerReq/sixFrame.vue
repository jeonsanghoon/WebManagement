<template>
  <v-container class="wrap">
    <div class="content-wrap">
      <div class="touch-wrap">
        <p :class="touchClass[0]" @click="touch = 0">
          터치 전
        </p>
        <p :class="touchClass[1]" @click="touch = 1">
          터치 후
        </p>
        <div class="touch-select" :style="touchStyle[touch]" />
      </div>

      <!-- 터치 전 -->
      <div class="touch-before-wrap" v-if="touch === 0">
        <div class="img-wrap">
          <v-img
            v-if="!checkNull(bannerInfo.img)"
            :src="`/files/${bannerInfo.img}`"
            width="260"
            height="260"
          />
        </div>

        <p class="title text-truncate ondaji_font_20_bold_grey_100">
          {{ bannerInfo.title }}
        </p>
        <p class="sub-title ondaji_font_16_medium_grey_80">
          {{ bannerInfo.subTitle }}
        </p>
        <div class="profile-wrap">
          <v-img
            v-if="!checkNull(bannerInfo.profileImg)"
            class="profile-icon"
            :src="`/files/${bannerInfo.profileImg}`"
            :width="20"
            :height="20"
          />
          <v-icon v-else class="profile-icon">far fa-user-circle</v-icon>
          <p class="localer-text text-truncate ondaji_font_14_medium_grey_60">
            {{ bannerInfo.name }}
          </p>
        </div>
      </div>

      <!-- 터치 후 -->
      <div class="touch-after-wrap" v-else>
        <div class="img-wrap">
          <v-img :src="`/files/${bannerInfo.img}`" width="210" height="210" />
        </div>
        <v-textarea
          class="after-content ondaji_font_16_medium_grey_90"
          v-model="bannerInfo.content"
          dense
          solo
          no-resize
          rows="7"
          hide-details
          flat
          readonly
        />
      </div>
    </div>
  </v-container>
</template>

<script>
import { commonMediaShop } from '@/views/mediaShop/mixins/commonMixins'

export default {
  mixins: [commonMediaShop],
  props: {
    bannerInfo: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      touchClass: [
        'ondaji_font_12_bold_white',
        'ondaji_font_12_medium_grey_50',
      ],
      touchStyle: ['left: 0;', 'right: 0;'],
      touch: 0,
    }
  },
  watch: {
    touch() {
      this.touchClass = [
        'ondaji_font_12_bold_white',
        'ondaji_font_12_medium_grey_50',
      ]

      if (this.touch === 1) {
        this.touchClass = [
          'ondaji_font_12_medium_grey_50',
          'ondaji_font_12_bold_white',
        ]
      }
    },
  },
}
</script>

<style lang="scss" scoped>
p {
  padding: 0;
  margin: 0;
}

.wrap {
  padding: 0;
}

.content-wrap {
  padding: 14px 26px 18px;
}

.touch-wrap {
  position: relative;
  display: flex;
  justify-content: space-around;
  align-items: center;
  background-color: #e0e0e0;
  height: 30px;
  width: 260px;
  border-radius: 30px;

  p {
    cursor: pointer;
    z-index: 1;
  }
}

.touch-select {
  position: absolute;
  background-color: #3e2723;
  width: 128px;
  height: 24px;
  margin: 4px 4px;
  border-radius: 50px;
}

.touch-before-wrap {
  .img-wrap {
    margin-top: 18px;
    background-color: #e7e7e7;
    width: 260px;
    height: 260px;
    border-radius: 3px;
    .v-image {
      border-radius: inherit;
    }
  }

  p {
    width: 260px;
  }
}

.touch-after-wrap {
  padding: 10px 24px 0;
  .img-wrap {
    margin: 14px auto 0;
    background-color: #e7e7e7;
    width: 210px;
    height: 210px;
    border-radius: 3px;
    .v-image {
      border-radius: inherit;
    }
  }
}

.title {
  margin-top: 10px;
}

.sub-title {
  white-space: normal;
  line-height: 1.35;
  height: 84px;
  text-align: justify;
  word-wrap: break-word;
  display: -webkit-box;
  -webkit-line-clamp: 4;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.profile-wrap {
  display: flex;

  .v-image {
    margin-right: 7px;
  }
  .profile-icon {
    display: flex;
    background: #3e2723;
    color: #aaaaaa;
    border-radius: 50%;
    border: 1px solid #3e2723;
    font-size: 18px;
  }
}

@media screen and (max-width: $grid-breakpoints_sm) {
  .wrap {
    padding: 0;
    margin: 0;
    width: 276px;
  }

  .content-wrap {
    padding: 14px 7px 18px;
  }

  .touch-wrap {
    width: 250px;
  }

  .touch-before-wrap {
    .img-wrap {
      width: 250px;
      height: 250px;
    }

    p {
      width: 250px;
    }
  }
}

::v-deep {
  .after-content {
    margin-top: 12px;
    &.v-input {
      padding-left: 0;
      border: none !important;
      &:hover {
        border: none !important;
      }
      textarea {
        padding-right: 0 !important;
        line-height: 24px !important;
      }
    }
  }

  .v-textarea textarea {
    &::-webkit-scrollbar-thumb {
      background-color: #3e2723;
    }
    &::-webkit-scrollbar-track {
      background-color: #e2d9d2;
    }
  }
}
</style>
