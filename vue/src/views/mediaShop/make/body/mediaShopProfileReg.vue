<template>
  <v-container class="pa-0 text-start" fluid>
    <v-row class="px-2 px-lg-0" no-gutters>
      <v-col cols="12" lg="5" offset-lg="2">
        <!-- 프로필 사진 -->
        <div class="profile-box">
          <input ref="imageInput" type="file" hidden @change="reqImgUpload" />
          <div class="profile-title ondaji_font_16_bold_brown_80">
            프로필 사진
          </div>
          <div class="profile-icon-box" @click="imgSelect">
            <v-avatar v-if="profileImgUrl != null" size="85">
              <v-img :src="`/files/${profileImgUrl}`" />
            </v-avatar>
            <div v-else>
              <v-icon class="profile-icon">far fa-user-circle</v-icon>
              <v-icon class="plus-icon">fas fa-plus</v-icon>
            </div>
          </div>
        </div>

        <!-- 소개말 -->
        <div class="intro-box">
          <div class="intro-title ondaji_font_16_bold_brown_80">소개말</div>
          <v-textarea
            v-model="desc"
            label="소개말을 입력해주세요."
            background-color="white"
            color="grey lighten-1"
            counter="100"
            auto-grow
            rows="1"
            row-height="90"
            single-line
            dense
            maxlength="100"
          ></v-textarea>
        </div>

        <!-- 키워드 -->
        <div class="keyword-box">
          <div class="keyword-title ondaji_font_16_bold_brown_80">
            키워드<span class="keyword-caption ondaji_font_12_medium_brown_70"
              >5개 이하</span
            >
          </div>
          <autoComplete ref="autocomplete" :info="keywordInfo"></autoComplete>
        </div>

        <div class="btn-box">
          <v-btn class="next-btn" rounded depressed @click="nextPage">
            <span class="next-btn-text"
              >다음
              <v-icon class="btn-right-icon">fas fa-chevron-right</v-icon></span
            >
          </v-btn>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { commonMediaShop } from '@/views/mediaShop/mixins/commonMixins'

import autoComplete from '@/components/vuetify/autoComplete'
export default {
  mixins: [commonMediaShop],
  components: {
    autoComplete,
  },
  props: {
    info: {
      type: Object,
      required: false,
    },
  },
  data() {
    return {
      desc: ``,
      profileImgUrl: null,

      keywordInfo: {
        keywords: [],
        rounded: false,
        type: 1,
      },
    }
  },
  created() {
    if ((Object.keys(this.info).length === 0) === false) {
      this.initProfile()
    }
  },
  methods: {
    initProfile() {
      this.desc = this.info.desc
      this.profileImgUrl = this.info.profileImgUrl

      this.info.keywords.forEach((keyword) => {
        this.keywordInfo.keywords.push(keyword.keywordName)
      })
    },
    imgSelect() {
      this.$refs.imageInput.click()
    },
    reqImgUpload(img) {
      const fileImg = img.target.files[0]

      if (this.checkImgType(fileImg) === false) {
        return
      }

      let fileReader = new FileReader()
      fileReader.readAsDataURL(fileImg)

      let self = this

      fileReader.onload = function(e) {
        let src = e.target.result.split(',')

        self.$BaseCommon.restful
          .imageDataUpload(src[1])
          .then((res) => {
            if (self.checkNull(res) === false) {
              self.profileImgUrl = res[0].attachmentUrl
            }
          })
          .catch((res) => {
            console.log('/mediaShop/reqImgUpload >> ', res)
          })
      }
    },
    checkImgType(fileImg) {
      if (
        fileImg.type != `image/jpg` &&
        fileImg.type != `image/jpeg` &&
        fileImg.type != `image/png`
      ) {
        this.$alert('jpg, jpeg, png 형식만 가능합니다.')
        return false
      }

      return true
    },
    nextPage() {
      this.$emit('nextPage', this.getInfo())
    },
    getInfo() {
      let info = {}

      info.profileImgUrl = this.profileImgUrl
      info.desc = this.desc
      info.keywords = this.$refs.autocomplete.getKeywords()

      return info
    },
  },
}
</script>

<style lang="scss" scoped>
.profile-box {
  margin-top: 40px;
}

.profile-icon-box {
  position: relative;
  width: 85px;
  height: 85px;
  cursor: pointer;
}

.profile-icon {
  background: #c4c4c4;
  font-size: 75px;
  border-radius: 50%;
  border: 5px solid white;
}

.plus-icon {
  position: absolute;
  color: white;
  top: 2rem;
  right: 2rem;
}

.profile-title,
.intro-title,
.keyword-title {
  margin-bottom: 12px;
}

.intro-box,
.keyword-box {
  margin-top: 32px;
}

.keyword-box {
  margin-bottom: 40px;
}

.keyword-caption {
  margin-left: 10px;
}

.next-btn {
  width: 88px !important;
  height: 30px !important;
  background: #3e2723 !important;
}

.next-btn-text {
  font: {
    weight: 700;
    size: 0.875rem;
  }
  color: white;
}

.btn-right-icon {
  margin-left: 28px;
  font-size: 0.875rem;
}

.btn-box {
  float: right;
  margin-bottom: 40px;
}

::v-deep {
  .v-avatar {
    border-radius: 50%;
    border: 5px solid white;
  }

  .v-input__slot {
    padding: 4px 12px !important;
    border: 0 !important;
  }

  .v-input__slot::before {
    border: 0 !important;
  }

  .v-label {
    font-weight: 400;
    font-size: 0.85rem;
    color: #aaa19f !important;
  }

  .v-counter {
    margin-top: 4px;
  }
}
</style>
