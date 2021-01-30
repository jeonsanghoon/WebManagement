<template>
  <v-container class="align-start justify-start" fluid>
    <v-row class="px-2 px-lg-0" no-gutters>
      <v-col cols="12">
        <v-col class="pt-2" cols="12">
          <Vuetify-menutitle />
        </v-col>

        <div class="content-box">
          <headerMain :page="page" />
          <profileReg
            v-if="page === TYPE.PROFILE"
            :info="info.profileInfo"
            @nextPage="nextPage"
          />
          <rangeReg
            v-else-if="page === TYPE.RANGE"
            :info="info.rangeInfo"
            :call="info.call"
            @prevPage="prevPage"
            @complete="complete"
          />
          <companyReg
            v-else
            :info="info.companyInfo"
            :call="info.call"
            @prevPage="prevPage"
            @complete="complete"
          />
        </div>
      </v-col>
    </v-row>

    <inputCheckModal :info="inputCheckInfo" :dialog.sync="inputCheckDialog" />
  </v-container>
</template>

<script>
import { commonMediaShop } from "@/views/mediaShop/mixins/commonMixins"

import headerMain from "./header/mediaShopHeaderMain"
import profileReg from "./body/mediaShopProfileReg"
import rangeReg from "./body/mediaShopRangeReg"
import companyReg from "./body/mediaShopCompanyReg"
import inputCheckModal from "@/components/vuetify/inputCheckModal"

export default {
  mixins: [commonMediaShop],
  components: {
    headerMain,
    profileReg,
    rangeReg,
    companyReg,
    inputCheckModal,
  },
  data() {
    return {
      TYPE: {
        PROFILE: 0,
        RANGE: 1,
        COMPANY: 2,
      },

      MEMBER_TYPE: {
        PERSONAL: 0,
        COMPANY: 1,
      },

      info: {
        profileInfo: {},
        rangeInfo: {},
        companyInfo: {},
        call: false,
      },

      memberType: -1,
      // 요청 응답 변수
      reqSaveCheck: false,

      inputCheckInfo: [],
      inputCheckDialog: false,

      page: 0,
      localBoxCode: null,
    }
  },
  created() {
    const member = this.$BaseCommon.memberInfo.getMember().member

    this.checkParam(member)

    if (this.checkNull(member.companyName) === false) {
      this.memberType = this.MEMBER_TYPE.COMPANY
    } else {
      this.memberType = this.MEMBER_TYPE.PERSONAL
    }
  },
  methods: {
    checkParam(member) {
      const params = this.$route.params

      if (this.checkObjectEmpty(params) === true) {
        if (this.checkNull(params.info) === false) {
          this.localBoxCode = params.localBoxCode
          this.info.profileInfo = params.info.profileInfo
          this.info.rangeInfo = params.info.rangeInfo

          if (this.checkObjectEmpty(params.info.companyInfo) === true) {
            this.info.companyInfo = params.info.companyInfo
            this.info.companyInfo.rangeInfo = params.info.companyInfo.rangeInfo
          }
        }
        this.info.call = true
      } else if (this.checkNull(member.localboxCode) === false) {
        this.$router.push({ name: "localboxbanner" })
      } else {
        this.reqLocalBoxCode()
      }
    },
    prevPage(info) {
      if (this.memberType === this.MEMBER_TYPE.PERSONAL) {
        this.info.rangeInfo = info
      } else {
        this.info.companyInfo = info
      }

      this.page = this.TYPE.PROFILE
    },
    nextPage(info) {
      this.info.profileInfo = info

      if (this.checkInput(this.TYPE.PROFILE) === false) {
        return
      }

      if (this.memberType === this.MEMBER_TYPE.PERSONAL) {
        this.page = this.TYPE.RANGE
      } else {
        this.page = this.TYPE.COMPANY
      }
    },
    complete(info) {
      let type = null
      if (this.memberType === this.MEMBER_TYPE.PERSONAL) {
        this.info.rangeInfo = info
        type = this.TYPE.RANGE
      } else {
        this.info.companyInfo = info
        type = this.TYPE.COMPANY
      }

      if (this.checkInput(type) === false || this.reqSaveCheck === true) {
        return
      }

      this.reqSave()
    },
    reqSave() {
      if (this.reqSaveCheck === true) {
        return
      }

      const formData = this.getFormData()

      this.reqSaveCheck = true

      this.$BaseCommon.restful
        .call2("/mediashop/localbox/save", formData)
        .then((res) => {
          if (this.checkNull(res) === false) {
            if (res.data != null) {
              this.localBoxCode = Number(res.data)

              this.$router.push({
                name: "localboxbanner",
                params: { localBoxCode: this.localBoxCode },
              })
            }
            this.reqSaveCheck = false
          }
        })
        .catch((res) => {
          console.log("/mediashop/localbox/save >> ", res)
        })
    },
    getFormData() {
      let localbox = {
        desc: this.info.profileInfo.desc,
        profileUrl: this.info.profileInfo.profileImgUrl,
      }

      if (this.localBoxCode != 0) {
        localbox.localboxCode = this.localBoxCode
      }

      const keywords = this.info.profileInfo.keywords
      let place = {}

      if (this.checkNull(this.info.companyInfo.rangeInfo) === false) {
        localbox.businessTime = this.info.companyInfo.openTime
        localbox.announce = this.info.companyInfo.notice
        localbox.phone = this.info.companyInfo.phoneNumber

        const range = this.info.companyInfo.rangeInfo

        place.address = range.address
        place.addressDetail = range.subAddress
        place.roadAddress = range.roadAddress
        place.zipCode = range.zonecode
        place.latitude = range.latitude
        place.longitude = range.longitude
        place.radius = range.radius
      } else {
        place = this.info.rangeInfo
      }

      const formData = {
        localbox: localbox,
        keywords: keywords,
        place: place,
      }

      return formData
    },
    checkInput(type) {
      this.inputCheckInfo.length = 0
      switch (type) {
        case this.TYPE.PROFILE:
          this.checkProfileInput()
          break
        case this.TYPE.RANGE:
          this.checkRangeInput()
          break
        case this.TYPE.COMPANY:
          this.checkCompanyInput()
          break
      }

      if (this.inputCheckInfo.length > 0) {
        this.inputCheckDialog = true
        return false
      }

      return true
    },
    checkProfileInput() {
      const profileReg = this.info.profileInfo

      if (this.checkNull(profileReg.profileImgUrl) === true) {
        this.pushCheckInfo(`프로필 사진`)
      }

      if (this.checkNull(profileReg.desc) === true) {
        this.pushCheckInfo(`소개말`)
      }

      if (profileReg.keywords.length <= 0) {
        this.pushCheckInfo(`키워드`)
      }
    },
    checkRangeInput() {
      const rangeInfo = this.info.rangeInfo

      if (this.checkNull(rangeInfo) === true) {
        this.pushCheckInfo(`광고영역`)
      }
    },
    checkCompanyInput() {
      const companyInfo = this.info.companyInfo

      if (this.checkNull(companyInfo.address) === true) {
        this.pushCheckInfo(`주소`)
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
.content-box {
  background: #f2f2f2;
}
</style>
