<template lang="pug">
    v-container(fill-height fluid)
        v-row(fill-height fluid)
          v-col(cols="12" class="pb-0" )
              Vuetify-menutitle(title="미디어 작업실 > 사이니지" icon="icon-mediaedit" :isDevider="false" )
          v-col(cols="12" class="mt-0 pt-2 ondaji_color_grey_10 menutitle") 
            span(class="ondaji_font_18_bold_grey_70") 사이니지 편집 
            span(class="ondaji_font_16_medium_grey_60 pl-2") {{signage.signName}} 
            v-chip(outlined rounded  color="fafafa" small class="ml-2") {{signage.frameTypeName}}
          v-col(cols="12" sm="5")
            div(class=" ondaji_color_brown_10")
              v-container
                v-row
                  v-col(cols="12" class="py-0 conditionPadding")
                    v-divider(class="pb-0")
                  v-col(cols="12" class="py-1 conditionPadding text-left")
                    Vuetify-Selectbox(v-model="selData" class="pt-0" :options="selItems")
                    v-divider(vertical class="py-0 dividerAlign") 
                    Vuetify-Selectbox(v-model="selData2" :options="selItems2")
                  v-col(cols="12" class="py-0 conditionPadding")
                    tableFooter(:info="{ totalNum: bannerTotalPage, PAGE_TOTAL_NUM: 1 }" :viewStyle="1" :perText="'of'" @getPageInfo="changePage1" )
                  v-col(cols="12" class="py-0")
                    v-card( outlined color="transparent" class="dvList" v-if="notDataShow1" fill-height fluid)  
                      div(class="notDataShow")
                        h3 등록된 배너가 없습니다. 
                        br 
                        h3 배너를 등록해 주세요. 
                    v-list( class="dvList ondaji_color_brown_10 py-1")
                      v-list-item( v-for="(item, i) in currentPage1List" :key="i" class="white my-2 ml-0")
                        Vuetify-bannerListCard(v-model="currentPage1List[i]")
            
          v-col(cols="12" sm="2")  
            v-container(fill-height fluid)
              v-row(class="align-center justify-center" fill-height fluid)
                v-col(class="my-auto" cols="12")
                  Buttons-default(btnType="primary" class="mt-10" :size="40" appendIcon="fas fa-chevron-right" btnText="게시" button rounded @click="signageBannerAdd")
          v-col(cols="12" sm="5")
            div(class="ondaji_color_brown_10")
              v-container
                v-row
                  v-col(cols="6" class="pb-0 conditionPadding")
                    div(class="order-btn-wrap ondaji_color_grey_20 d-flex align-center mt-1")
                      Vuetify-listOrder(v-model="currentPage2List"  @change="orderChange")
                  v-col(cols="6" class="text-right pb-0 conditionPadding")
                    //v-btn(fab small depressed color="ondaji_color_brown_10")
                      v-img(contain small max-width="26" max-height="26" src="@/assets/images/icons/preview.svg")
                    v-btn(icon fab small color="#3e2723" @click="bannerDelete") 
                      v-icon fas fa-trash-alt
                    Buttons-default(btnType="primary" class="ml-2" btnText="업로드" :size="24" @click="signageBannerUpdate" )
                  v-col(cols="12" class="py-0 conditionPadding")
                    tableFooter(:info="{ totalNum: signageBannerTotalPage, PAGE_TOTAL_NUM: 1 }" :viewStyle="1" :perText="'of'" @getPageInfo="changePage2" )
                  v-col(cols="12" class="py-0") 
                    v-card( outlined color="transparent" class="dvList" v-if="notDataShow2" fill-height fluid)  
                      div(class="notDataShow")
                        h3 등록된 배너가 없습니다. 
                        br 
                        h3 배너를 등록해 주세요.

                    v-list( class="dvList ondaji_color_brown_10 py-1" v-else)
                      v-list-item( v-for="(item, i) in currentPage2List" :key="item.code" class="white my-2 ml-0")
                        Vuetify-bannerListCard(v-model="currentPage2List[i]" :seqName="'seq'" :index ="i" :isPrivateShow="true" @callback="bannerItemCallback")
                    
</template>
<style lang="scss">
.selectSmall.v-text-field--outlined.v-input--dense.v-text-field--outlined
  > .v-input__control
  > .v-input__slot {
  min-height: 32px;
  .v-input__append-inner {
    margin-top: 4px;
  }
  .v-list-item--dense,
  .v-list--dense .v-list-item {
    min-height: 30px;
  }
}
</style>
<style lang="scss" scoped>
.dividerAlign {
  vertical-align: middle;
}
.order-btn-wrap {
  width: 138px;
  height: 26px;
  border-radius: 3px;
}

.conditionPadding {
  padding-left: 2rem;
  padding-right: 2rem;
}
.dvList {
  height: 560px; /* or any height you want */
  overflow-y: auto;
  padding-right: 1.25rem;
  padding-left: 1.25rem;
  //padding-bottom: 1.25rem;
  .v-list {
    width: 100%;
  }
}
.menutitle {
  border-top: 1px solid rgba(0, 0, 0, 0.12);
  text-align: left;
  padding-left: 2rem;
}
.v-chip {
  border-width: 3px;
}

.thumbnail-zoom {
  width: 3rem;
  height: 3rem;
  transition: transform 0.2s;
  margin: 0 auto;
  &:hover {
    transform: scale(5) !important;
    z-index: 1;
  }
}
.notDataShow {
  margin: auto auto;
}
</style>
<script>
export default {
  props: {
    id: { default: 1 },
  },
  created() {
    this.signage = this.$route.params
    this.memberCode = this.$BaseCommon.memberInfo.getMember().member.memberCode
    this.orderText = this.selItems[0].text
    this.getBannerList()
    this.getSignageBannerList()
  },
  watch: {
    bannerList(res) {
      // eslint-disable-next-line no-debugger
      debugger
      if (res.length > 0) {
        this.bannerTotalPage =
          Math.floor(res.length / this.pageSize) +
          (res.length % this.pageSize > 1 ? 1 : 0)
      } else this.bannerTotalPage = 1

      this.changePage1({ curPage: this.currentPage1 - 1 })
    },
    signageBannerList(res) {
      if (res.length > 0) {
        this.signageBannerTotalPage = res[res.length - 1].page + 1
      } else this.signageBannerTotalPage = 1

      this.changePage2({ curPage: this.currentPage2 - 1 })
    },
    currentPage1List(res) {
      // eslint-disable-next-line no-debugger
      debugger
      console.log("currentPage1List", res)
    },
    currentPage2List(res) {
      //this.currentPage1List = res
      console.log("currentPage2List", res)
      res.forEach(() => {
        setTimeout(() => {
          if (this.currentPage2List.length > 5)
            this.currentPage2List[4].isPrivate = true
        }, 500)
      })
    },
    menu() {},
    selData() {
      this.getBannerList()
    },
    selData2() {
      this.getBannerList()
    },
  },
  data() {
    return {
      menu: false,
      orderText: "",
      pageSize: 6,
      memberCode: -1,
      notDataShow1: false,
      notDataShow2: false,
      signage: { signCode: -1 },
      bannerList: [],
      bannerTotalPage: 1,
      signageBannerList: [],
      signageBannerTotalPage: 1,
      currentPage1: 1,
      currentPage1List: [],
      currentPage2: 1,
      currentPage2List: [],
      selData: { value: "a.update_date desc", text: "최근 수정된 순서" },
      selItems: [
        { value: "a.update_date desc", text: "최근 수정된 순서" },
        { value: "a.insert_date desc", text: "최근 제작일 순" },
        { value: "a.insert_date", text: "오래된 제작일 순" },
        { value: "a.title", text: "배너명(↑)" },
        { value: "a.title desc", text: "배너명(↓)" },
      ],
      selData2: { value: [1, 2], text: "정보/광고" },
      selItems2: [
        { value: [1, 2], text: "정보/광고" },
        { value: [1], text: "정보" },
        { value: [2], text: "광고" },
      ],
    }
  },
  methods: {
    getBannerList() {
      this.$BaseCommon.restful
        .call2("/banner/getbannerstorelist", {
          notSignCode: this.signage.signCode,
          memberCode: this.memberCode,
          orderBy: this.selData.value,
          bannerType: this.selData2.value,
          inStatus: "9,99",
        })
        .then((res) => {
          this.bannerList = []
          setTimeout(() => {
            this.bannerList = [...res]
          }, 100)
        })
        .catch((res) => {
          this.$alert(JSON.stringify(res))
          console.log("/banner/getbannerstorelist >> ", res)
        })
    },
    getBannerOrder(item) {
      this.orderText = item.text
    },
    getSignageBannerList() {
      this.$BaseCommon.restful
        .call2("/signage/getSignageBannerList", {
          signCode: this.signage.signCode,
          //  page: page,
        })
        .then((res) => {
          this.signageBannerList = []

          setTimeout(() => {
            this.signageBannerList = [...res]
          }, 100)
        })
        .catch((res) => {
          this.$alert(res)
          console.log("/banner/getbannerstorelist >> ", res)
        })
    },
    saveListChekeds() {
      let saveList = [...this.currentPage1List.filter((w) => w.bChk === true)]
      return saveList
    },
    deleteListChekeds() {
      // eslint-disable-next-line no-debugger
      debugger
      let saveList = [...this.currentPage2List.filter((w) => w.bChk === true)]
      return saveList
    },
    saveListSet() {
      // eslint-disable-next-line no-debugger
      debugger
      let seq = this.getLastSeq(this.currentPage2List)
      let saveList = [...this.saveListChekeds()]
      saveList.forEach((item, index) => {
        saveList[index].page = this.currentPage2
        saveList[index].seq = seq
        seq++
      })
      return saveList
    },
    getLastSeq(list) {
      let pageList = list.filter((w) => w.page === this.currentPage2)
      // eslint-disable-next-line no-debugger
      debugger
      let rtn =
        pageList.length === 0
          ? 1
          : pageList.sort((a, b) => b.seq - a.seq)[0].seq + 1
      return rtn
    },
    signageBannerAdd() {
      // eslint-disable-next-line no-debugger
      debugger
      let saveList = [...this.saveListSet()]
      if (saveList.length === 0) {
        this.$alert("선택된 데이터가 없습니다.")
        return
      }
      if (
        this.signage.frameType > 1 &&
        saveList.length + this.currentPage2List.length > this.signage.frameType
      ) {
        this.$alert(`최대 등록 갯수는 ${this.signage.frameType}개 입니다.`)
        return
      }
      this.signageBannerListSave(saveList)
    },
    signageBannerUpdate() {
      let saveList = this.currentPage2List.filter(
        (w) => (w.editMode || "") === "U"
      )
      this.signageBannerListSave(saveList)
    },
    signageBannerListSave(saveList) {
      if (saveList.length === 0) {
        this.$alert(`저장할 데이터가 업습니다.`)
        return
      }
      this.$BaseCommon.restful
        .call2("/signage/signageBannerListSave", saveList)
        .then((res) => {
          if (res.resultType == "Error") throw new Error(res.message)

          this.getBannerList()
          this.getSignageBannerList()
        })
        .catch((res) => {
          this.$alert(res.message)
        })
    },
    bannerDelete() {
      let deleteList = [...this.deleteListChekeds()]
      if (deleteList.length === 0) {
        this.$alert("삭제할 데이터가 없습니다.")
        return
      }
      this.$alert("선택한 배너를 정말로 삭제하시겠습니까?", {
        alertType: "del",
      }).then((res) => {
        if (res) {
          this.bannerDeleteExec(deleteList)
        }
      })
    },
    bannerDeleteExec(deleteList) {
      this.$BaseCommon.restful
        .call2("/signage/signageBannerListDelete", deleteList)
        .then((res) => {
          if (res.resultType == "Error") throw new Error(res.message)

          this.getBannerList()
          this.getSignageBannerList()
        })
        .catch((res) => {
          this.$alert(res.message)
        })
    },
    bannerItemCallback(item, index) {
      this.signageBannerList[index] = item
      let tmp = [...this.signageBannerList]
      this.signageBannerList = []
      setTimeout(() => {
        this.signageBannerList = tmp
      }, 200)
    },
    changePage1(page) {
      console.log(page)
      this.currentPage1 = page.curPage + 1
      this.currentPage1List = []
      // eslint-disable-next-line no-debugger
      debugger
      let self = this
      setTimeout(() => {
        self.currentPage1List = self.bannerList.filter(
          (w) =>
            w.rowNum > (self.currentPage1 - 1) * self.pageSize &&
            w.rowNum <= self.currentPage1 * self.pageSize
        )
        self.setNotDataMessage(self.currentPage1List, 1)
      }, 200)

      //this.getSignageBannerList(this.currentPage1)
    },
    changePage2(page) {
      console.log(page)
      this.currentPage2 = page.curPage + 1
      this.currentPage2List = []
      setTimeout(() => {
        this.currentPage2List = this.signageBannerList.filter(
          (w) => w.page === this.currentPage2
        )
        this.setNotDataMessage(this.currentPage2List, 2)
      }, 200)

      //this.getSignageBannerList(this.currentPage1)
    },
    setNotDataMessage(list, nType = 1) {
      if (list.length === 0) this["notDataShow" + String(nType)] = true
      else this["notDataShow" + String(nType)] = false
    },
    selectChange(item) {
      console.log(item)
    },
    orderChange(res) {
      this.currentPage2List = []
      res.forEach((data, index) => {
        res[index].editMode = "U"
      })
      setTimeout(() => {
        console.log(res)
        this.currentPage2List = [...res]
      }, 0)
    },
  },
}
</script>
