<template>
  <v-container class="pa-0 mt-n9" fluid>
    <div class="pr-sm-3">
      <v-row class="top-box" no-gutters>
        <v-col
          class="top-box-pos d-sm-flex align-center"
          cols="12"
          sm="8"
          offset-sm="2"
          lg="6"
          offset-lg="3"
        >
          <div class="profile-icon-wrap">
            <div v-if="profileImgUrl != null">
              <v-avatar size="65">
                <v-img :src="`/files/${profileImgUrl}`" />
              </v-avatar>
            </div>
            <div v-else>
              <v-icon class="profile-icon">far fa-user-circle</v-icon>
            </div>
          </div>

          <div class="name-wrap text-truncate ondaji_font_14_bold_brown_100">
            {{ name }}
          </div>

          <div class="address-wrap text-truncate ondaji_font_12_medium_grey_70">
            {{ reduceAddress }}
          </div>

          <div class="profile-modify-wrap ml-sm-auto">
            <v-btn class="modify-btn" rounded depressed @click="profileModify">
              <span class="ondaji_font_12_bold_brown_70">
                프로필 수정
                <v-icon class="ml-1" size="8">fas fa-pen</v-icon>
              </span>
            </v-btn>
          </div>
        </v-col>
      </v-row>

      <v-row class="desc-wrap" no-gutters>
        <v-col class="" cols="12" sm="8" offset-sm="2" lg="7" offset-lg="3">
          <div class="desc-text text-truncate ondaji_font_12_medium_grey_60">
            {{ desc }}
          </div>
        </v-col>
      </v-row>
    </div>
  </v-container>
</template>

<script>
import { commonMediaShop } from "@/views/mediaShop/mixins/commonMixins"

export default {
  mixins: [commonMediaShop],
  data() {
    return {
      profileImgUrl: null,
      address: ``,
      desc: ``,
    }
  },
  created() {
    this.initLocalBox()
  },
  computed: {
    reduceAddress() {
      const places = ["구", "동", "읍", "면", "리"]

      if (this.checkNull(this.address) === false) {
        return this.address
          .split(" ")
          .filter((addr) => {
            const endStr = addr.slice(-1)

            if (places.includes(endStr) === true) {
              return addr
            }
          })
          .join(" ")
      }

      return ``
    },
    name() {
      const member = this.$BaseCommon.memberInfo.getMember().member
      const nickname = member.nickname

      if (this.checkNull(nickname) === true) {
        return `${member.companyName} ${member.branchName}`
      }

      return nickname
    },
  },
  methods: {
    initLocalBox() {
      let formData = {}

      formData.memberCode = this.$BaseCommon.memberInfo.getMember().member.memberCode

      this.$BaseCommon.restful
        .call2("/mediashop/localbox/getlocalboxsimple", formData)
        .then((res) => {
          if (this.checkNull(res) === false) {
            this.profileImgUrl = res.profileUrl
            this.address = res.address
            this.desc = res.desc
          }
        })
        .catch((res) => {
          console.log("/mediashop/localbox/getlocalboxsimple >> ", res)
        })
    },
    profileModify() {
      let formData = {}

      formData.memberCode = this.$BaseCommon.memberInfo.getMember().member.memberCode

      this.$BaseCommon.restful
        .call2("/mediashop/localbox/getlocalbox", formData)
        .then((res) => {
          if (this.checkNull(res) === false) {
            let info = {
              profileInfo: {},
              rangeInfo: {},
              companyInfo: {},
            }

            info.profileInfo.profileImgUrl = res.localBox.profileUrl
            info.profileInfo.desc = res.localBox.desc
            info.profileInfo.keywords = res.keywords.map((retKeyword) => {
              let keyword = {}
              keyword.keywordName = retKeyword

              return keyword
            })

            info.rangeInfo = res.place

            const company = this.$BaseCommon.memberInfo.getMember().member
              .companyName

            if (this.checkNull(company) === false) {
              info.companyInfo.openTime = res.localBox.businessTime
              info.companyInfo.notice = res.localBox.announce
              info.companyInfo.phoneNumber = res.localBox.phone

              info.companyInfo.address = res.place.address
              info.companyInfo.subAddress = res.place.addressDetail
              info.companyInfo.zoneCode = res.place.zipCode

              info.companyInfo.rangeInfo = res.place
            }

            this.$router.push({
              name: "localbox",
              params: { localBoxCode: res.localBox.localboxCode, info: info },
            })
          }
        })
        .catch((res) => {
          console.log("/mediashop/localbox/getlocalbox >> ", res)
        })
    },
  },
}
</script>

<style lang="scss" scoped>
.top-box,
.desc-wrap {
  text-align: start;
}

.top-box {
  background-color: #f2f2f2;
  padding-top: 22px;
  padding-bottom: 7px;
}

.top-box-pos {
  position: relative;
}

.name-wrap {
  margin-left: 80px;
  display: inline-block;
}

.address-wrap {
  margin-left: 10px;
  display: inline-block;
}

.profile-icon-wrap {
  position: absolute;
  width: 69px;
  height: 69px;
  top: -7px;
  border-radius: 50%;
  border: 2px solid white;
}

.profile-icon {
  background: #aaaaaa;
  font-size: 65px;
  border-radius: 50%;
  border: 2px solid white;
}

.modify-btn {
  background: #e0e0e0 !important;
  padding: 0 10px !important;
  height: 20px !important;
}

.desc-wrap {
  background: #fafafa;
  padding-top: 7px;
  padding-bottom: 22px;
  border-bottom: 1px solid #e0e0e0;
}

.desc-text {
  margin-left: 80px;
}

.profile-modify-wrap {
  padding: 0;
}

.profile-modify-wrap .modify-btn {
  display: table;
  margin-left: auto;
}

.address-box {
  margin-left: 10px;
}

@media only screen and (max-width: $grid-breakpoints_sm) {
  .top-box {
    padding: 6px 0;
  }
  .profile-icon-wrap {
    top: 12px;
    left: 5px;
  }
  .name-wrap {
    max-width: 50px;
  }
  .address-wrap {
    max-width: 140px;
  }
  .desc-wrap {
    padding-top: 6px;
    padding-bottom: 29px;
  }
  .desc-text {
    width: 50%;
  }
  .profile-modify-wrap {
    padding-right: 5px;
  }
}
</style>
