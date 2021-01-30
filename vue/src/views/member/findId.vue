<template>
  <v-container fill-height class="ondijiBackground fullContent">
    <v-row
      justify="center"
      align="center"
      style="margin-left:0px;margin-right:0px;"
    >
      <v-col cols="12" justify="center" align="center" class="findID pa-0">
        <carousel
          ref="carousel"
          :per-page="1"
          :indicators="false"
          :slideOnSwipe="false"
          :scrollPerPage="false"
          :touchDrag="false"
          :mouseDrag="false"
          paginationPosition="top"
        >
          <slide>
            <v-card outlined class="cardForm">
              <v-form ref="form" v-model="valid">
                <v-card-title class="card-title">
                  <v-spacer></v-spacer>
                  <v-btn small icon @click="close">
                    <v-icon>fas fa-times</v-icon>
                  </v-btn>
                </v-card-title>
                <v-card-text class="card-text">
                  <v-container>
                    <v-row>
                      <v-col cols="12" class="titleOndaji font-weight-bold">
                        아이디 찾기
                      </v-col>
                    </v-row>
                    <v-row>
                      <v-col cols="12" class="cardDiscription"
                        >아이디를 잊으셨나요? 아래의 정보를 입력해
                        주세요.</v-col
                      >
                    </v-row>
                    <v-row>
                      <v-col cols="12" class="card-input">
                        <v-text-field
                          label="이름"
                          v-model="memberName"
                          v-mask="'XXXXXXX'"
                          width="50px"
                          placeholder="이온닫"
                          color="brown darken-4"
                          class="text-left"
                          :rules="checkRule.nameRules"
                          v-on:keyup.enter="next(1)"
                        >
                        </v-text-field>
                      </v-col>
                    </v-row>
                    <v-row>
                      <v-col cols="12" class="card-input">
                        <v-text-field
                          label="생년월일"
                          v-model="birthDate"
                          v-mask="'######'"
                          placeholder="900101"
                          color="brown darken-4"
                          class="text-left body-2"
                          :rules="checkRule.birthRules"
                          v-on:keyup.enter="next(1)"
                        >
                        </v-text-field>
                      </v-col>
                    </v-row>
                    <v-row>
                      <v-col class="pt-4 text-right">
                        <span
                          class="red--text caption "
                          v-if="errorMessage != ''"
                          >{{ errorMessage }}</span
                        >
                      </v-col>
                    </v-row>
                  </v-container>
                </v-card-text>
                <v-card-actions class="card-actions">
                  <Vuetify-button
                    rounded
                    class="ma-2"
                    width="5rem"
                    :btntype="this.$Config.vuetify.btn.type.confirm"
                    :text="'다음'"
                    @click="next(1)"
                  ></Vuetify-button>
                </v-card-actions>
              </v-form>
            </v-card>
          </slide>
          <slide>
            <v-card outlined class="cardForm">
              <v-form ref="form1" v-model="valid">
                <v-card-title class="card-title">
                  <v-spacer></v-spacer>
                  <v-btn small icon @click="close">
                    <v-icon>fas fa-times</v-icon>
                  </v-btn>
                </v-card-title>
                <v-card-text class="card-text">
                  <v-container>
                    <v-row>
                      <v-col cols="12" class="titleOndaji font-weight-bold">
                        아이디 찾기
                      </v-col>
                    </v-row>
                    <v-row>
                      <v-col cols="12" class="cardDiscription"
                        >입력하신 정보로 {{ memberList.length }}개의 아이디를
                        찾았습니다.<br />
                        동명이인의 아이디가 함께 검색될 수 있습니다.
                      </v-col>
                    </v-row>
                    <v-divider width="100%"></v-divider>

                    <v-row class="listarea">
                      <v-col cols="12" class="caption  pt-0 pb-0 ">
                        <v-list>
                          <v-list-item
                            v-for="item in memberList"
                            :key="item.memberId"
                            class="pa-0 listdata"
                          >
                            <v-list-item-content>
                              {{ item.memberId }}
                            </v-list-item-content>
                          </v-list-item>
                        </v-list>
                        <span class="caption pt-4">
                          *개인정보 보호를 위해 일부 내용은 *로 표시됩니다.
                        </span>
                      </v-col>
                    </v-row>
                    <v-row>
                      <v-col cols="12" class="caption"> </v-col>
                    </v-row>
                  </v-container>
                </v-card-text>
                <v-card-actions class="card-actions">
                  <Vuetify-button
                    rounded
                    class="ma-2 mr-0"
                    :btntype="this.$Config.vuetify.btn.type.list"
                    text="비밀번호 찾기"
                    @click="moveFindPassword"
                  ></Vuetify-button>
                  <Vuetify-button
                    rounded
                    class="ma-2"
                    :btntype="this.$Config.vuetify.btn.type.confirm"
                    text="로그인"
                    @click="close"
                  ></Vuetify-button>
                </v-card-actions>
              </v-form>
            </v-card>
          </slide>
        </carousel>
      </v-col>
    </v-row>
  </v-container>
</template>

<style lang="scss" scoped>
.cardForm form {
  height: 100%;
}
.VueCarousel-slide {
  padding-right: 0.5em;
  padding-left: 0.5em;
}
.card form {
  height: 100%;
}
.cardForm {
  min-width: 20rem;
  max-width: 25rem;
  border-radius: 0.625rem !important;
  height: 35rem;
  padding-left: 0rem;
  padding-right: 0rem;
  .card {
    &-title {
      padding-top: 0.5rem;
      padding-right: 0.5rem;
    }

    &-text {
      padding-left: 1.5rem;
      padding-right: 1.5rem;
      text-align: left;
    }
    &-input {
      font-size: 0.875rem;
      padding-top: 0px;
      padding-bottom: 0.5rem;
      height: 4.5rem;
    }
    &-action {
      padding-left: 1.5rem;
      padding-right: 1.5rem;
    }
  }

  & .cardDiscription {
    height: 5rem;
    opacity: 0.8;
    color: #887d7a;
  }

  .card-actions {
    position: absolute;
    bottom: 0px;
    right: 0.5rem;
  }
  .card-actions_left {
    position: absolute;
    bottom: 0px;
    left: 0.5rem;
  }
}

.link {
  text-decoration: underline;
  cursor: pointer;
}
.listarea {
  min-height: 208px;
  .listdata {
    color: #887d7a;
  }
  .caption {
    color: rgba($color: #887d7a, $alpha: 0.4);
  }
}
</style>
<style>
.findID .VueCarousel-pagination {
  display: none;
}
.v-input--checkbox.idRemember label {
  font-size: 1em !important;
  left: -0.2em !important;
}
.v-input--checkbox.idRemember .v-icon {
  font-size: 1.2em !important;
}
</style>

<script>
import ax from "axios"
export const axios = ax
import { Carousel, Slide } from "vue-carousel"
export default {
  components: {
    Carousel,
    Slide,
  },
  data() {
    return {
      valid: true,
      birthDate: "",
      memberName: "",
      memberList: [],
      errorMessage: "",
      checkRule: {
        nameRules: [(v) => !!v || "이름을 입력하세요"],
        birthRules: [
          (v) => !!v || "생년월일을 입력하세요",
          /*  (v) =>
                        /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/.test(v) ||
                        '영문자, 숫자, 특수문자 각 1자리 이상을 사용하여 8자리 이상을 입력하세요',*/
          /*  
           v=> /[A-Z]+/.test(v) || '대문자 1자리 이상 숫자를 입력하세요',
           v=> /[0-9]+/.test(v) || '1자리 이상 숫자를 입력하세요',
           */
        ],
      },
    }
  },
  created() {
    const loginId = this.$BaseCommon.memberInfo.getMember().loginId

    this.memberId = loginId != undefined ? loginId : this.memberId
  },
  mounted() {
    this.$BaseCommon.settingInfo.pageInit({
      el: this.$el,
      isBar: false,
      isTop: false,
    })
    if (!this.$BaseCommon.stringInfo.isNullEmpty(localStorage.memberId)) {
      this.isIDRemember = true
      this.memberId = localStorage.memberId
    } else {
      this.isIDRemember = false
    }
  },
  beforeCreate() {},
  methods: {
    close() {
      //this.$router.push("/member/login")
      this.$BaseCommon.memberInfo.moveLogin()
    },
    next(page) {
      if (!this.$refs.form.validate()) {
        return
      }
      let birth = ""
      if (this.birthDate.length === 10)
        birth = this.$moment(this.birthDate).format("YYYYMMDD")
      else birth = this.birthDate
      this.$BaseCommon.restful
        .call("/member/findID", {
          memberName: this.memberName,
          birthDate: birth,
        })
        .then((res) => {
          if (res != null) {
            this.memberList = res
            if (res.length > 0) {
              this.$refs.carousel.currentPage = page
              this.$refs.carousel.goToPage(page)
            } else {
              this.errorMessage = "*입력하신 내용을 다시 한번 확인해 주세요."
            }
          }
        })
    },
    moveFindPassword() {
      this.$router.push("/member/findpw")
    },
  },
}
</script>
