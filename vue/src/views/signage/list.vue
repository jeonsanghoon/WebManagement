<template lang="pug">
  v-container(fill-height fluid)
    v-row(align="start" justify="start" fill-height fluid)
      v-col(cols="12" class="pb-0")
        Vuetify-menutitle
      
      v-col(cols="12" class="mt-0 pt-2")
        v-card(outlined class="mx-4 mt-5 " )
          v-container(class="pt-0 pb-0" fluid)
            v-row
              v-col(cols="12" class="mt-0 pa-0")
                v-data-table(:headers="headers" class="listTable elevation-1" :items="datalist" :search="search" :loading="isLoading" 
                             :loading-text="$msg.getMsg('L016')" single-expand :expanded.sync="expanded"
                             item-key="signCode" show-expand 
                             :footer-props="{ 'items-per-page-text': '행수', 'page-text':'$vuetify.dataFooter.pageText'.replace('of','de') }")
                  template(v-slot:top)
                    v-toolbar(flat color="white")
                      v-text-field(v-model="search" append-icon="search" max-width="300px" label="검색어를 입력하세요" single-line hide-details)
                      v-spacer
                      Buttons-default(btnType="primary" :size="40"  btnText="추가" button rounded @click="btnNew")
                      v-dialog(v-model="dialog" max-width="600px")
                        v-card(class="mx-0 py-0 px-4")
                          v-card-title(class="py-0 px-2")
                            Vuetify-menutitle(:title="'사이니지정보'" :icon="'icon-install'" )
                          v-card-text(class="px-2 pb-0")
                            v-container(class="py-0")
                              v-form(ref="form" v-model="valid" lazy-validation)
                                v-row
                                  v-col(class="pt-2 pb-0 justify-start" cols="12")
                                    Vuetify-textbox(v-model="editedItem.signName"  placeholder="사이니지명" :rules="checkRule.signNameRules" )
                                  v-col(class="px-0 py-2 justify-start" cols="12" sm="6")
                                    Buttons-radio(v-model="editedItem.frameType" :isRadioImage="false" :rounded="false" 
                                                  trueValue=1 falseValue=6
                                                  trueText="1FRAME" falseText="6FRAME" label="유형선택" small)
                                  v-col(class="px-0 py-2 justify-start" cols="12" sm="6")
                                    Buttons-radio(v-model="editedItem.signType" :trueValue=1 :falseValue=2 :isRadioImage="false" :rounded="false" trueText="내부 광고용" falseText="외부 광고용" label='서비스' small)
                                  v-col(class="px-0 py-2 justify-start" cols="12" sm="6")
                                    Buttons-radio(v-model="editedItem.isVertical" :trueValue="true" :falseValue="false" :isRadioImage="false" :rounded="false" trueText="세로" falseText="가로" label="방향" small)
                                  v-col(class="pl-0 py-2 justify-start"  cols="12" sm="6")
                                    Buttons-radio(v-model="editedItem.isInside"  :trueValue="true" :falseValue="false" :isRadioImage="false" :rounded="false" trueText="실내" falseText="실외" label="기기위치" small)
                                  v-col(class="py-2 justify-start"  cols="12" sm="6")
                                    Vuetify-textbox(v-model="placeList.zipCode" label2="설치 주소" placeholder="우편번호" :isBtn="true" btnText="주소검색" readonly @btnClick="addresPopup")
                                    Kakaomap-PostCode( @kakaoPostCodeCallback="kakaoPostCodeCallback" :dialog="addressDialog")
                                  v-col(class="py-0 pr-sm-6 justify-start"  cols="12" sm="7" style="height:36px")
                                    Vuetify-textbox(v-model="placeList.address" readonly placeholder="주소")
                                  v-col(class="py-0 justify-start"  cols="12" sm="5" style="height:36px")
                                    Vuetify-textbox(v-model="placeList.address2" placeholder="상세주소" :rules="checkRule.address2Rules" )
                          v-card-actions(class="pt-0")
                            v-spacer
                            v-btn(color="blue darken-1" text @click="close") 취소
                            v-btn(color="blue darken-1" text @click="save") 저장
                  template(v-slot:expanded-item="{ headers, item }" class="elevation-0")
                    td(:colspan="headers.length" class="text-left pa-2 grey lighten-4")
                      span 설치위치 : {{ item.isInsideName }}
                      br 
                      span 설치방향 : {{ item.isVerticalName }}
                  template(v-slot:[`item.bannerCnt`]="{ item }") {{item.bannerCnt}}
                    v-icon(small class="ml-2 mr-2" @click="bannerMove(item)") mdi-pencil 
                  template(v-slot:[`item.hideName`]="{ item }")
                      Buttons-switch(v-model="item.hide" :item="item" revert @change="changeHide")
                  template(v-slot:[`item.actions`]="{ item }")
                    v-icon(small class="mr-2" @click="editItem(item)") mdi-pencil
                    v-icon(small @click="deleteItem(item)") mdi-delete
                  template(v-slot:footer.page-text="items") {{ items.pageStart }} - {{ items.pageStop }} / {{ items.itemsLength }} 
            
</template>

<script>
export default {
  props: {},
  created() {
    this.doQuery()
  },
  data() {
    return {
      dialog: false,
      valid: true,
      signagePages: 1,
      editedIndex: -1,
      addressDialog: false,
      expanded: [],
      selVal: { value: "signName", text: "사이니지" },
      selList: [
        { value: "signName", text: "사이니지" },
        { value: "memberName", text: "로컬러" },
      ],
      search: "",
      isLoading: false,
      headers: [
        { text: "사이니지", value: "signName" },
        { text: "유형", value: "signTypeName" },
        { text: "프레임", value: "frameType", align: "center" },

        { text: "운영", value: "hideName" },
        { text: "배너수", value: "bannerCnt", align: "center" },
        { text: "저장", value: "actions", sortable: false },
        { text: "", value: "data-table-expand" },
      ],
      datalist: [],
      editedItem: {
        signName: "",
        isVertical: 1,
        frameType: 1,
        isInside: 1,
      },
      placeList: { addrss: "", address2: "", zipCode: "" },
      checkRule: {
        signNameRules: [(v) => !!v || "사이니지명을 입력하세요"],
        addressRules: [(v) => !!v || "주소검색을 클릭하여 주소를 등록하세요"],
        address2Rules: [(v) => !!v || "상세주소를 등록하세요"],
      },
    }
  },
  methods: {
    doQuery() {
      this.isLoading = true
      let cond = {}
      cond[this.selVal.value] = this.searchText
      this.$BaseCommon.restful
        .call2("/signage/getSignageList", cond)
        .then((res) => {
          this.datalist = res
          this.isLoading = false
        })
    },
    btnNew() {
      this.$router.push("/signage/setting")
    },
    editItem(item) {
      this.editedIndex = this.datalist.indexOf(item)
      this.editedItem = Object.assign({}, item)

      this.getSignagePlace(this.editedItem.signCode)
    },
    getSignagePlace(signCode) {
      this.$BaseCommon.restful
        .call2("/signage/getSignagePlaceList", { signCode: signCode })
        .then((res) => {
          if (res.length > 0) {
            this.placeList = res[0]
          } else {
            this.placeList = {
              placeCode: -1,
              zipCode: "",
              address: "",
              address2: "",
            }
          }
          this.dialog = true
        })
    },
    deleteItem(item) {
      this.editedIndex = this.dataList.indexOf(item)
      this.$alert("정말로 삭제하시겠습니까? ", {
        alertType: "del",
      }).then((res) => {
        if (res) {
          this.$BaseCommon.restful
            .call("/signage/signageDelete", this.dataList[this.editedIndex])
            .then((res) => {
              if (this.afterMessage(res)) {
                self.dataList.splice(this.editedIndex, 1)
                this.editedIndex = -1
              }
            })
        } else {
          console.log("콜백처리 False", res)
        }
      })
    },

    changeHide(item, isTrue) {
      this.$BaseCommon.restful
        .call("/signage/changeStatus", {
          signCode: item.signCode,
          hide: !isTrue,
        })
        .then((res) => {
          console.log(res)
        })
    },
    close() {
      this.dialog = false
      this.$nextTick(() => {
        this.editedItem = Object.assign({}, this.defaultItem)
        this.editedIndex = -1
      })
    },

    save() {
      if (!this.$refs.form.validate()) {
        return
      }

      this.editedItem.placeList = []
      this.editedItem.placeList.push(this.placeList)
      this.editedItem.hide = !this.editedItem.hide
      this.$BaseCommon.restful
        .call("/signage/save", this.editedItem)
        .then((res) => {
          if (res.resultType === "Error") {
            this.$alert(res.message)
          } else {
            this.doQuery()
          }
        })
        .catch((err) => {
          this.$alert(err)
        })
      if (this.editedIndex > -1) {
        Object.assign(this.datalist[this.editedIndex], this.editedItem)
      } else {
        this.datalist.push(this.editedItem)
      }
      this.close()
    },
    addresPopup() {
      this.addressDialog = true
    },
    kakaoPostCodeCallback(res, bConfirm) {
      console.log(res)
      this.addressDialog = false
      if (!bConfirm) {
        return
      }

      this.placeList = {
        zipCode: res.zonecode,
        address: res.jibunAddress,
        roadAddress: res.roadAddress,
      }

      res.region = `${res.sido || res.region_1depth_name} ${
        res.sigungu || res.region_2depth_name || ""
      } ${res.bname || res.region_3depth_name}`

      this.addressDialog = false
    },
    bannerMove(item) {
      console.log(item)
      this.$router.push({
        name: "signageBanner",
        params: item,
      })
    },
  },
}
</script>
<style lang="scss">
.listTable {
  .v-data-table > .v-data-table__wrapper > table > tbody > tr > td,
  .v-data-table > .v-data-table__wrapper > table > thead > tr > td,
  .v-data-table > .v-data-table__wrapper > table > tfoot > tr > td {
    font-size: 0.875rem;
    height: 100px !important;
  }
}
</style>
