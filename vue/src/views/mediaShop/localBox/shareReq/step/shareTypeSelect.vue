<template>
  <v-container class="wrap">
    <!-- 타이틀 -->
    <div class="title-wrap">
      <p class="ondaji_font_14_bold_brown_80">
        원하시는 광고 서비스를 선택해 주세요.
        <span class="ondaji_font_14_medium_brown_80">
          (다중 선택 가능)
        </span>
      </p>
    </div>

    <!-- 공유 정보 -->
    <v-row class="device-wrap" no-gutters>
      <div class="signage-wrap">
        <p class="device-text ondaji_font_14_bold_brown_70">
          사이니지
        </p>

        <v-row class="guide-wrap" no-gutters>
          <div v-for="(guide, i) in guides" class="guide-content-wrap" :key="i">
            <div v-if="i !== GUIDE_TYPE.MOBILE">
              <div class="img-wrap" @click="clickType(i)" :style="guide.style">
                <v-img
                  contain
                  width="140"
                  height="140"
                  :src="require(`@/assets/images/icons/${guide.img}.svg`)"
                />
              </div>
              <div class="text-wrap">
                <p class="guide-title ondaji_font_14_bold_brown_80">
                  {{ guide.title }}
                </p>
                <p class="guide-content ondaji_font_12_medium_grey_60">
                  {{ guide.content }}
                </p>
                <p
                  v-if="i === GUIDE_TYPE.SHARE"
                  class="guide-attention ondaji_font_10_medium_brown_70"
                >
                  {{ guides[GUIDE_TYPE.SHARE].attention }}
                </p>
              </div>

              <div
                v-if="
                  guide.select && i === GUIDE_TYPE.LOCAL && !guide.expiration
                "
                class="set-date-wrap"
              >
                <span
                  class="ondaji_font_14_bold_blue_70"
                  @click="clickDate(guide, i)"
                >
                  <span v-if="guide.viewDate === ''">
                    광고기간 설정
                  </span>
                  <span v-else>{{ guide.viewDate }}</span>
                </span>

                <div
                  v-if="guide.dateViewClick"
                  class="date-picker-wrap d-inline-flex"
                >
                  <datePicker ref="refLocalDatePicker" />
                  <v-btn
                    v-if="guide.dateViewClick"
                    class="date-click-btn ondaji_font_14_medium_white"
                    depressed
                    color="#2979ff"
                    rounded
                    height="30"
                    @click="clickDateBtn(GUIDE_TYPE.LOCAL)"
                  >
                    설정
                  </v-btn>
                </div>
              </div>
              <div v-else-if="guide.expiration" class="mt-5">
                <span class="ondaji_font_14_bold_grey_60">
                  <span>{{ guide.viewDate }}</span>
                </span>
              </div>
            </div>
          </div>
        </v-row>
      </div>

      <div class="mobile-wrap">
        <p class="device-text ondaji_font_14_bold_brown_70">
          모바일
        </p>

        <v-row class="guide-wrap" no-gutters>
          <div class="guide-content-wrap">
            <div
              class="img-wrap"
              @click="clickType(GUIDE_TYPE.MOBILE)"
              :style="guides[GUIDE_TYPE.MOBILE].style"
            >
              <v-img
                contain
                width="140"
                height="140"
                :src="
                  require(`@/assets/images/icons/${
                    guides[GUIDE_TYPE.MOBILE].img
                  }.svg`)
                "
              />
            </div>
            <div class="text-wrap">
              <p class="guide-title ondaji_font_14_bold_brown_80">
                {{ guides[GUIDE_TYPE.MOBILE].title }}
              </p>
              <p class="guide-content ondaji_font_12_medium_grey_60">
                {{ guides[GUIDE_TYPE.MOBILE].content }}
              </p>
            </div>

            <div
              v-if="
                guides[GUIDE_TYPE.MOBILE].select &&
                  !guides[GUIDE_TYPE.MOBILE].expiration
              "
              class="set-date-wrap"
            >
              <span
                class="ondaji_font_14_bold_blue_70"
                @click="clickDate(guides[GUIDE_TYPE.MOBILE], GUIDE_TYPE.MOBILE)"
              >
                <span v-if="guides[GUIDE_TYPE.MOBILE].viewDate === ''">
                  광고기간 설정
                </span>
                <span v-else>{{ guides[GUIDE_TYPE.MOBILE].viewDate }}</span>
              </span>

              <div
                v-if="guides[GUIDE_TYPE.MOBILE].dateViewClick"
                class="date-picker-wrap d-inline-flex"
              >
                <datePicker ref="refMobileDatePicker" />
                <v-btn
                  v-if="guides[GUIDE_TYPE.MOBILE].dateViewClick"
                  class="date-click-btn align-center ondaji_font_14_medium_white"
                  depressed
                  color="#2979ff"
                  rounded
                  height="30"
                  @click="clickDateBtn(GUIDE_TYPE.MOBILE)"
                >
                  설정
                </v-btn>
              </div>
            </div>
            <div v-else-if="guides[GUIDE_TYPE.MOBILE].expiration" class="mt-5">
              <span class="ondaji_font_14_bold_grey_60">
                <span>{{ guides[GUIDE_TYPE.MOBILE].viewDate }}</span>
              </span>
            </div>
          </div>
        </v-row>
      </div>
    </v-row>

    <!-- 하단 버튼 -->
    <div class="btn-wrap">
      <v-btn
        class="next-btn"
        :disabled="nextBtnDisabled"
        rounded
        depressed
        @click="moveConfirm"
      >
        <span>
          다음
          <v-icon>fas fa-chevron-right</v-icon>
        </span>
      </v-btn>
    </div>
  </v-container>
</template>

<script>
import datePicker from "../components/datePicker"

export default {
  components: {
    datePicker,
  },
  props: {
    info: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      guides: [
        {
          select: false,
          dateViewClick: false,
          expiration: false,
          title: "지역광고",
          content: "배너의 광고 영역에 설치되어 있는 사이니지에 광고",
          img: "il_loc",
          style: "",
          startDate: "",
          endDate: "",
          viewDate: "",
        },
        {
          select: false,
          expiration: false,
          title: "배너공유",
          content: "원하는 로컬 사업자를 직접 선택하여 사이니지 배너 공유 요청",
          attention:
            "*비영리기관의 배너 또는 영리기관의 정보배너만 가능합니다.",
          img: "il_send",
          style: "",
        },
        {
          select: false,
          dateViewClick: false,
          expiration: false,
          title: "모바일 광고",
          content: "온닫이 사이트의 메인 화면광고",
          img: "il_paid",
          style: "",
          startDate: "",
          endDate: "",
          viewDate: "",
        },
      ],

      GUIDE_TYPE: {
        LOCAL: 0,
        SHARE: 1,
        MOBILE: 2,
      },

      localDate: "",
      mobileDate: "",

      viewLocalDate: "",
      viewMobileDate: "",

      nextBtnDisabled: true,

      dateError: false,
      initGuides: [],
    }
  },
  created() {
    if (this.info.length > 0) {
      this.init()
      this.activeNextBtn()
    }
  },
  watch: {
    guides: {
      deep: true,
      handler() {
        this.activeNextBtn()
      },
    },
  },
  methods: {
    async clickDate(guide, type) {
      if (guide.expiration) return

      guide.dateViewClick = !guide.dateViewClick
      if (guide.startDate !== "") {
        await this.$nextTick()

        const date = {
          start: new Date(guide.startDate),
          end: new Date(guide.endDate),
        }

        let datePicker =
          type === this.GUIDE_TYPE.LOCAL
            ? this.$refs.refLocalDatePicker[0]
            : this.$refs.refMobileDatePicker

        if (datePicker !== undefined) {
          datePicker.date = { ...date }
        }
      }
    },
    init() {
      for (let [i, guide] of this.guides.entries()) {
        guide.select = this.info[i].select
        guide.expiration = this.info[i].expiration
        guide.img = this.info[i].img
        guide.startDate = this.info[i].startDate
        guide.endDate = this.info[i].endDate
        guide.viewDate = this.info[i].viewDate

        if (guide.select) {
          guide.style =
            "border: 2px solid #2979ff; box-shadow: 0 0.05rem 0.5rem 0 #00000050;"
        }
      }
    },
    clickType(type) {
      let guide = this.guides[type]

      if (guide.expiration) return

      if (guide.select) {
        guide.img = guide.img.slice(0, -1)
        guide.style = ""
        guide.dateViewClick = false
      } else {
        guide.img = `${guide.img}2`
        guide.style =
          "border: 2px solid #2979ff; box-shadow: 0 0.05rem 0.5rem 0 #00000050;"
      }

      guide.select = !guide.select

      this.activeNextBtn()
    },
    activeNextBtn() {
      for (const guide of this.guides) {
        if (guide.select) {
          this.nextBtnDisabled = false
          return
        }

        this.nextBtnDisabled = true
      }
    },
    clickDateBtn(type) {
      const date =
        type === 0
          ? this.$refs.refLocalDatePicker[0]
          : this.$refs.refMobileDatePicker

      const input = date.$el.querySelector("input").value.split(" ")

      this.guides[type].startDate = input[0].replaceAll("-", ".")
      this.guides[type].endDate = input[2].replaceAll("-", ".")

      this.guides[type].viewDate = input
        .map((date) => {
          if (date.length > 2) {
            date = date.slice(2)
          }
          return date
        })
        .join("")
        .replace("-", "~")

      this.guides[type].dateViewClick = false
    },
    moveConfirm() {
      for (const [i, guide] of this.guides.entries()) {
        if (i !== this.GUIDE_TYPE.SHARE) {
          if (guide.select && guide.viewDate === "") {
            this.$alert("광고기간 설정이 필요합니다.")
            return
          }
        }
      }

      this.$emit("moveConfirm", this.guides)
    },
  },
}
</script>

<style lang="scss" scoped>
$lg-size: 186px;
$sm-size: 144px;
$xs-size: 134px;

p {
  padding: 0;
  margin: 0;
}

.wrap {
  padding: 0 40px;
}

.title-wrap {
  margin-top: 46px;
  text-align: start;
}

.device-wrap {
  margin-top: 36px;

  .device-text {
    text-align: start;
    padding-left: 10px;
    padding-bottom: 3px;
    border-bottom: 2px solid #d1c8c6;
  }
}

.signage-wrap {
  margin-right: 40px;
}

.mobile-wrap {
  .device-text {
    width: 220px;
  }
}

.guide-wrap {
  margin-top: 24px;
}

.guide-content-wrap {
  position: relative;
  text-align: start;
  padding: 0;

  &:first-child {
    margin-right: 10px;
  }
}

.img-wrap {
  width: $lg-size;
  height: $lg-size;
  border: 2px solid #eaeaea;
  border-radius: 10px;

  display: flex;
  justify-content: center;
  align-items: flex-start;

  cursor: pointer;

  .v-image {
    margin-top: 10px;
  }
}

.set-date-wrap {
  position: relative;
  display: inline-block;
  margin-top: 20px;
  cursor: pointer;
  border: none;

  &:hover {
    border-bottom: 1px solid #2979ff;
  }

  .date-picker-wrap {
    width: 300px;
    height: 57px;
    padding: 7px;

    background-color: white;
    border: 1px solid #fafafa;
    box-shadow: 0 0.05rem 0.5rem 0 #00000050;
    position: absolute;
    top: 25px;
    left: 0;
    margin-left: -30px;
    z-index: 1;

    .date-click-btn {
      position: absolute;
      right: 8px;
      top: 12px;
    }
  }
}

.text-wrap {
  width: $lg-size;

  .guide-title {
    position: absolute;
    top: 153px;
    left: 17px;
  }

  .guide-content {
    margin-top: 8px;
    min-height: 36px;
  }

  .guide-attention {
    margin-top: 14px;
    padding: 5px;
    background-color: #f5eee8;
    text-align: justify;
  }
}
.btn-wrap {
  display: flex;
  justify-content: flex-end;
  margin-top: 30px;
  margin-bottom: 22px;

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
        margin-bottom: 3px;
        font-size: 0.875rem;
      }
    }
  }
}

@media (min-width: $grid-breakpoints_sm) and (max-width: $grid-breakpoints_lg) {
  .wrap {
    padding: 0 30px;
  }

  .mobile-wrap {
    .device-text {
      width: 186px;
    }
  }

  .img-wrap {
    width: $sm-size;
    height: $sm-size;

    .v-image {
      margin-top: -3px;
      width: 120px !important;
      height: 120px !important;
    }
  }

  .text-wrap {
    width: $sm-size;

    .guide-title {
      top: 117px;
      left: 13px;
    }
  }
  .set-date-wrap {
    .date-picker-wrap {
      width: 225px;
      height: 87px;
      padding: 7px;

      .date-click-btn {
        top: 50px;
        right: 8px;
      }
    }
  }
}

@media screen and (max-width: $grid-breakpoints_sm) {
  .wrap {
    padding: 0 10px;
  }

  .mobile-wrap {
    .device-text {
      width: 146px;
    }
  }
  .signage-wrap {
    margin-right: 0;
  }

  .mobile-wrap {
    margin-top: 20px;
  }

  .img-wrap {
    width: $xs-size;
    height: $xs-size;

    .v-image {
      margin-top: -5px;
      height: 112px !important;
      width: 112px !important;
    }
  }

  .text-wrap {
    width: $xs-size;

    .guide-title {
      top: 108px;
      left: 12px;
    }

    .guide-content {
      min-height: 54px;
    }
  }

  .set-date-wrap {
    .date-picker-wrap {
      width: 250px;
      height: 87px;
      padding: 7px;

      .date-click-btn {
        top: 50px;
        right: 8px;
      }
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
