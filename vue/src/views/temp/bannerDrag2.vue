<template>
  <v-container class="grey lighten-5">
    <v-row justify="center" no-gutters>
      <v-col sm="3" class="justify-center">
        <grid-Signage
          :options="this.gridSignageOption"
          :addDataList="addDataList"
          @callbackData="callbackData_gridSignage"
          @callBackDel="callBackDel_gridSignage"
          class="ml-10"
        ></grid-Signage>
        <Vuetify-button
          class="ma-2"
          :name="'btnNew'"
          :btntype="this.$Config.vuetify.btn.type.new"
          :text="'추가'"
          @btnNew_Click="btnBannerAdd"
        ></Vuetify-button>
        <Vuetify-button
          class="ma-2"
          :name="'btnConfirm'"
          :btntype="this.$Config.vuetify.btn.type.default"
          :text="'결과값'"
          @btnConfirm_Click="btnConfirm_Click"
        ></Vuetify-button>
        <v-textarea
          label="결과값"
          auto-grow
          outlined
          rows="5"
          row-height="50"
          style="width:500px"
          shaped
          :value="rtnData"
        ></v-textarea>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped lang="scss">
.vue-grid-layout {
  width: 320px;
  min-height: 500px;
  background-color: #e6e6e6;
}
.vue-grid-item {
  &:not(.vue-grid-placeholder) {
    background: #ccc;
    border: 1px solid black;
    height: 100%;
  }
  .bannerItem {
    height: 100% !important;
  }
}
</style>
<script>
export default {
  data() {
    return {
      gridSignageOption: {
        frametype: 6,
        dataLoadList: [
          {
            bannerCode: 1,
            title: "01번배너정보",
            subTitle: "01번서브정보",
            bannerImageUrl: "/file/download/202003/01.jpg",
            bInfo: true,
          },
          {
            bannerCode: 2,
            title: "02번배너정보",
            subTitle: "02번서브정보",
            bannerImageUrl: "/file/download/202003/02.jpg",
            bInfo: false,
          },
        ],
      },
      dataList: [
        {
          bannerCode: 3,
          title: "3번배너정보",
          subTitle: "03번서브정보",
          bannerImageUrl: "/file/download/202003/03.jpg",
          bInfo: true,
        },
        {
          bannerCode: 4,
          title: "4번배너정보",
          subTitle: "04번서브정보",
          bannerImageUrl: "/file/download/202003/04.jpg",
          bInfo: false,
        },
        {
          bannerCode: 5,
          title: "5번배너정보",
          subTitle: "05번서브정보",
          bannerImageUrl: "/file/download/202003/05.jpg",
          bInfo: true,
        },
        {
          bannerCode: 6,
          title: "6번배너정보",
          subTitle: "06번서브정보",
          bannerImageUrl: "/file/download/202003/06.jpg",
          bInfo: false,
        },
      ],
      addDataList: [],
      rtnData: "",
    }
  },
  created() {
    window.itemdel = this.itemdel
    console.log("데이터 로드 >>>> ", this.dataLoadList)
  },
  methods: {
    callbackData_gridSignage(res) {
      console.log("callbackData_gridSignage", res)
      this.rtnData = JSON.stringify(res)
    },
    callBackDel_gridSignage(res) {
      this.dataList.push(res[0])
    },
    btnBannerAdd() {
      if (this.dataList.length === 0) return

      let contentData = this.dataList.splice(0, 1)
      this.addDataList = contentData
    },
    btnConfirm_Click() {},
  },
}
</script>
