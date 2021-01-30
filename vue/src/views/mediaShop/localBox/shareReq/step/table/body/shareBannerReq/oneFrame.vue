<template>
  <v-container class="wrap">
    <div class="content-wrap">
      <div>
        <v-img
          :class="classInfo.imgClass"
          :src="`/files/${bannerInfo.img}`"
          :width="classInfo.imgWidth"
          :height="classInfo.imgHeight"
        >
          <div
            v-if="bannerInfo.video && !playVideo"
            class="content-video-btn"
            @click="playVideo = true"
          >
            <v-img
              contain
              width="44"
              height="44"
              :src="require('@/assets/images/icons/movie_sm.svg')"
            />
          </div>

          <div v-if="playVideo">
            <video
              muted
              autoplay
              loop
              :width="classInfo.imgWidth"
              :height="classInfo.imgHeight"
            >
              <source :src="`/files/${bannerInfo.video}`" type="video/mp4" />
            </video>
          </div>

          <div class="profile-wrap">
            <v-img
              class="profile-icon"
              :src="`/files/${bannerInfo.profileImg}`"
              :width="classInfo.profileImgSize"
              :height="classInfo.profileImgSize"
            />
            <p>{{ bannerInfo.name }}</p>
          </div>
          <div class="expand-wrap" @click="dialog = true">
            <v-icon small color="#3E2723">
              fas fa-expand
            </v-icon>
          </div>
        </v-img>
      </div>
    </div>

    <v-dialog v-model="dialog" fullscreen>
      <v-img
        :class="classInfo.modalImgClass"
        :src="`/files/${bannerInfo.img}`"
        :min-width="classInfo.modalImgWidth"
        :min-height="classInfo.modalImgHeight"
        :max-width="classInfo.modalImgWidth"
        :max-height="classInfo.modalImgHeight"
      >
        <div
          v-if="bannerInfo.video && !modalPlayVideo"
          class="content-video-btn"
          @click="modalPlayVideo = true"
        >
          <v-img
            contain
            width="44"
            height="44"
            :src="require('@/assets/images/icons/movie_sm.svg')"
          />
        </div>

        <div v-if="modalPlayVideo">
          <video
            muted
            autoplay
            loop
            :width="classInfo.modalImgWidth"
            :height="classInfo.modalImgHeight"
          >
            <source :src="`/files/${bannerInfo.video}`" type="video/mp4" />
          </video>
        </div>

        <div class="profile-wrap">
          <v-img
            class="profile-icon"
            :src="`/files/${bannerInfo.profileImg}`"
            :width="classInfo.modalProfileImgSize"
            :height="classInfo.modalProfileImgSize"
          />
          <p>{{ bannerInfo.name }}</p>
        </div>
      </v-img>
      <span class="clear-icon">
        <v-icon color="white" size="44" @click="dialog = false">
          clear
        </v-icon>
      </span>
    </v-dialog>
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
      classInfo: {},
      playVideo: false,
      dialog: false,
      modalPlayVideo: false,
    }
  },
  watch: {
    bannerInfo() {
      if (this.bannerInfo.vertical) {
        this.classInfo = {
          imgClass: 'vertical-img',
          imgWidth: '260',
          imgHeight: '460',
          profileImgSize: '20',
          modalImgClass: 'modal-vertical-img',
          modalImgWidth: '416',
          modalImgHeight: '740',
        }
      } else {
        this.classInfo = {
          imgClass: 'horizontal-img',
          imgWidth: '260',
          imgHeight: '148',
          profileImgSize: '12',
          modalImgClass: 'modal-horizontal-img',
          modalImgWidth: '740',
          modalImgHeight: '416',
        }
      }

      this.classInfo.modalProfileImgSize = '28'

      if (this.getScreenSize() === 'xs') {
        this.classInfo.imgWidth = '250'

        this.classInfo.modalImgWidth = '310'

        if (!this.bannerInfo.vertical) {
          this.classInfo.modalImgHeight = '160'
        }
      }
    },
  },
}
</script>

<style lang="scss" scoped>
@mixin profile-common {
  position: absolute;
  display: flex;
  font-weight: 500;
  .v-image {
    margin-right: 7px;
  }
}

@mixin profile-icon {
  display: flex;
  background: #3e2723;
  color: #aaaaaa;
  border-radius: 50%;
  border: 2px solid #3e2723;
}

p {
  padding: 0;
  margin: 0;
}

.wrap {
  padding: 0;
}

.content-wrap {
  padding: 12px 26px;
  height: 485px;
  display: flex;
  align-items: center;
}

.v-image {
  border-radius: 3px;
}

.horizontal-img {
  position: relative;

  .profile-wrap {
    @include profile-common;
    color: #757575;
    font-size: 0.5rem;
    bottom: 5px;
    left: 10px;

    .profile-icon {
      @include profile-icon;
      font-size: 18px;
    }
  }
}

.vertical-img {
  position: relative;

  .profile-wrap {
    @include profile-common;
    color: #757575;
    font-size: 0.875rem;
    bottom: 10px;
    left: 12px;

    .profile-icon {
      @include profile-icon;
      font-size: 18px;
    }
  }
}

.content-video-btn {
  position: absolute;
  top: 56px;
  left: 114px;
  cursor: pointer;
}

.v-icon {
  padding-top: 1px;
  padding-left: 5px;
}

.expand-wrap {
  position: absolute;
  bottom: 10px;
  right: 6px;
  background-color: white;
  border: 1px solid #aaaaaa;
  border-radius: 2px;
  width: 25px;
  height: 25px;
  cursor: pointer;
}

.clear-icon {
  position: absolute;
  top: 30px;
  right: 30px;
  width: 44px;
}

.modal-vertical-img,
.modal-horizontal-img {
  position: relative;

  .content-video-btn {
    position: absolute;
    top: 190px;
    left: 360px;
    cursor: pointer;
  }

  .profile-wrap {
    @include profile-common;
    color: white;
    font-size: 1.125rem;
    bottom: 10px;
    left: 12px;

    .profile-icon {
      @include profile-icon;
      font-size: 28px;
    }
  }
}

video {
  object-fit: cover;
}

@media screen and (max-width: $grid-breakpoints_sm) {
  .wrap {
    padding: 0;
  }

  .content-wrap {
    padding: 12px 6px;
  }

  .modal-vertical-img,
  .modal-horizontal-img {
    .content-video-btn {
      top: 60px;
      left: 140px;
    }
  }
}

::v-deep {
  .v-dialog {
    position: relative;
    display: flex;
    justify-content: center;
    align-items: center;
    background: #000000e6;
  }
}
</style>
