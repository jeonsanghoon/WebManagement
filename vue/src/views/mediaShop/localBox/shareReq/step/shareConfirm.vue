<template>
  <v-container class="wrap">
    <v-row class="content-wrap" no-gutters>
      <div>
        <!-- 타이틀 -->
        <div class="title-wrap">
          <p class="ondaji_font_14_medium_brown_100">
            아래와 같이 광고 요청을 하셨습니다.
          </p>
          <p class="ondaji_font_14_medium_brown_100">
            내용을 확인하신 후 승인하여 주시기 바랍니다.
          </p>
        </div>

        <!-- 공유 정보 -->
        <ul class="guide-wrap">
          <li v-for="(guide, i) in guides" :key="i">
            <div v-if="guide.select" class="guide-content-wrap">
              <div class="img-wrap">
                <v-img
                  contain
                  :src="require(`@/assets/images/icons/${guide.img}`)"
                />
              </div>
              <p class="guide-title ondaji_font_14_bold_brown_80">
                {{ guide.title }}
              </p>
              <p class="date-title ondaji_font_13_medium_brown_70">
                {{ guide.date }}
              </p>
            </div>
          </li>
        </ul>

        <!-- 요청서 작성 -->
        <div v-if="guides[GUIDE_TYPE.SHARE].select">
          <div class="share-wrap">
            <p class="ondaji_font_14_medium_brown_70">
              배너 공유는 별도의 요청서 작성이 필요합니다.
            </p>
            <div class="req-btn-wrap">
              <v-btn
                class="req-btn"
                rounded
                depressed
                @click="clickReqBtn = !clickReqBtn"
              >
                <span class="ondaji_font_12_bold_white">
                  요청서 작성하기
                </span>
              </v-btn>
            </div>
          </div>
        </div>
      </div>
    </v-row>

    <!-- 배너공유 요청서 -->
    <shareBannerReq
      v-if="clickReqBtn"
      ref="refShareBannerReq"
      :info="shareBanner"
    />
    <!-- 하단 버튼 -->
    <v-row class="footer-wrap" no-gutters>
      <div class="back-btn-wrap">
        <v-btn @click="moveSelctType" depressed text height="30">
          <span class="ondaji_font_14_bold_brown_80">뒤로가기</span>
        </v-btn>
      </div>
      <div class="complete-btn-wrap">
        <v-btn
          class="next-btn"
          @click="moveComplete"
          :disabled="nextBtnDisabled"
          depressed
        >
          <span :class="nextBtnTextClass">승인하기</span>
        </v-btn>
      </div>
    </v-row>
    <inputCheckModal :info="inputCheckInfo" :dialog.sync="inputCheckDialog" />
  </v-container>
</template>

<script>
import { commonMediaShop } from '@/views/mediaShop/mixins/commonMixins'

import shareBannerReq from './table/body/shareBannerReq/shareBannerReqMain'
import inputCheckModal from '@/components/vuetify/inputCheckModal'

export default {
  mixins: [commonMediaShop],
  components: {
    shareBannerReq,
    inputCheckModal,
  },
  props: {
    info: {
      type: Array,
      required: true,
    },
    shareBanner: {
      type: Object,
      required: true,
    },
  },
  data() {
    return {
      guides: [
        {
          select: false,
          title: '지역광고',
          img: 'il_loc2.svg',
          date: '',
        },
        {
          select: false,
          title: '배너공유',
          img: 'il_send2.svg',
        },
        {
          select: false,
          title: '모바일 광고',
          img: 'il_paid2.svg',
          date: '',
        },
      ],

      GUIDE_TYPE: {
        LOCAL: 0,
        SHARE: 1,
        MOBILE: 2,
      },

      clickReqBtn: false,
      nextBtnDisabled: false,
      nextBtnTextClass: 'ondaji_font_14_bold_grey_20',

      inputCheckInfo: [],
      inputCheckDialog: false,
    }
  },
  created() {
    if (this.info.length > 0) {
      this.init()
    }
    if (this.guides[this.GUIDE_TYPE.SHARE].select) {
      this.nextBtnDisabled = true
    }
  },
  watch: {
    clickReqBtn() {
      this.nextBtnDisabled = this.clickReqBtn === true ? false : true

      this.nextBtnTextClass =
        this.clickReqBtn === true
          ? 'ondaji_font_14_bold_white'
          : 'ondaji_font_14_bold_grey_20'
    },
  },
  methods: {
    init() {
      for (let [i, guide] of this.guides.entries()) {
        guide.select = this.info[i].select
        guide.date = this.info[i].viewDate

        if (i === this.GUIDE_TYPE.SHARE) {
          this.shareBanner.desc = this.info[i].desc
          this.shareBanner.startDate = this.info[i].stateDate
          this.shareBanner.endDate = this.info[i].endDate
        }
      }

      this.nextBtnDisabled = this.guides[this.GUIDE_TYPE.SHARE].check
        ? true
        : false
    },
    moveSelctType() {
      this.$emit('moveSelctType')
    },
    moveComplete() {
      let shareInfo = null

      if (this.guides[this.GUIDE_TYPE.SHARE].select) {
        this.inputCheckInfo.length = 0

        shareInfo = this.$refs.refShareBannerReq.getShareInfo()

        this.checkInput(shareInfo)

        if (this.inputCheckInfo.length > 0) {
          this.inputCheckDialog = true
          return
        }
      }
      this.$emit('moveComplete', shareInfo)
    },
    checkInput(shareInfo) {
      if (shareInfo.selectLocalersInfo.length <= 0) {
        this.pushCheckInfo('로컬러')
      }

      if (this.checkNull(shareInfo.reqReason)) {
        this.pushCheckInfo('요청 사유')
      }

      if (this.checkNull(shareInfo.startDate)) {
        this.pushCheckInfo('게시 기간')
      }
    },
    pushCheckInfo(text) {
      let info = new Object()

      info.text = text

      this.inputCheckInfo.push(info)
    },
  },
}
</script>

<style lang="scss" scoped>
$lg-size: 136px;
$sm-size: 120px;
$xs-size: 90px;

ul {
  padding: 0;
  list-style: none;
}

p {
  padding: 0;
  margin: 0;
}

.wrap {
  padding: 0;
}

.content-wrap {
  display: flex;
  justify-content: center;
}

.title-wrap {
  margin-top: 46px;
  text-align: start;
}

.guide-wrap {
  display: flex;
  margin-top: 24px;

  .guide-content-wrap {
    position: relative;
    padding: 0;
    margin-right: 14px;
    text-align: start;
  }
}

.img-wrap {
  width: $lg-size;
  height: $lg-size;
  border: 2px solid #2979ff;
  border-radius: 10px;

  display: flex;
  justify-content: center;
  align-items: flex-start;

  .v-image {
    max-width: 80%;
  }
}

.guide-title {
  position: absolute;
  width: $lg-size;
  top: 105px;
  left: 15px;
}

.date-title {
  margin-top: 6px;
}

.share-wrap {
  margin-top: 28px;
  text-align: start;
  display: flex;
  align-items: center;

  .req-btn-wrap {
    margin-left: 15px;

    .req-btn {
      height: 24px !important;
      background: #3e2723 !important;
    }
  }
}

.footer-wrap {
  margin-top: 32px;
  background-image: linear-gradient(to right, #e2d9d2 33%, #ffffff00 0%);
  background-position: top;
  background-size: 35px 2px;
  background-repeat: repeat-x;

  .back-btn-wrap {
    margin: 14px 0 14px 12px;

    .v-btn {
      min-width: 0 !important;
    }
  }

  .complete-btn-wrap {
    margin: 14px 28px 14px auto;

    .next-btn {
      width: 88px !important;
      height: 30px !important;
      background: #3e2723 !important;

      span {
        font: {
          weight: 600;
          size: 0.875rem;
        }
        color: white;

        .v-icon {
          margin-left: 28px;
          font-size: 0.875rem;
        }
      }
    }
  }
}

@media (min-width: $grid-breakpoints_sm) and (max-width: $grid-breakpoints_lg) {
  .content-wrap {
    padding: 0 30px;
  }

  .img-wrap {
    width: $sm-size;
    height: $sm-size;
  }

  .guide-title {
    width: $sm-size;
    top: 93px;
    left: 13px;
  }
}

@media screen and (max-width: $grid-breakpoints_sm) {
  .content-wrap {
    padding: 0 10px;
  }

  .guide-content-wrap {
    margin-right: 6px;
  }

  .img-wrap {
    width: $xs-size;
    height: $xs-size;
  }

  .guide-title {
    width: $xs-size;
    top: 67px;
    left: 8px;
  }

  .share-wrap {
    display: block;

    .req-btn-wrap {
      display: flex;
      justify-content: flex-end;
      margin-top: 10px;
      margin-left: 0;
    }
  }
}
::v-deep {
  .theme--light.v-btn.v-btn--disabled:not(.v-btn--flat):not(.v-btn--text):not(.v-btn-outlined) {
    background: #aaaaaa !important;
    span {
      font: {
        weight: 600;
        size: 0.875rem;
      }
      color: #e0e0e0;
    }
    .v-icon {
      color: #e0e0e0 !important;
    }
  }
}
</style>
