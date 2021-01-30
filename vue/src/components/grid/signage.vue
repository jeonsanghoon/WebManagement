<template>
  <div>
    <grid-layout
      :layout.sync="layout"
      :col-num="2"
      :row-height="160"
      :is-draggable="true"
      :is-resizable="false"
      :is-mirrored="false"
      :vertical-compact="true"
      :margin="[0, 0]"
      :use-css-transforms="true"
      @layout-created="layoutCreatedEvent"
      @layout-before-mount="layoutBeforeMountEvent"
      @layout-mounted="layoutMountedEvent"
      @layout-ready="layoutReadyEvent"
      @layout-updated="layoutUpdatedEvent"
    >
      <grid-item
        v-for="item in layout"
        :x="item.x"
        :y="item.y"
        :w="item.w"
        :h="item.h"
        :i="item.i"
        :key="item.i"
        @resize="item_resizeEvent"
        @move="item_moveEvent"
        @resized="item_resizedEvent"
        @container-resized="item_containerResizedEvent"
        @moved="item_movedEvent"
        v-html="item.content"
      >
      </grid-item>
    </grid-layout>
  </div>
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
import VueGridLayout from "vue-grid-layout"
export default {
  props: {
    options: {
      type: Object,
    },
    addDataList: {
      type: Array,
    },
  },
  components: {
    GridLayout: VueGridLayout.GridLayout,
    GridItem: VueGridLayout.GridItem,
  },
  watch: {
    addDataList: {
      handler(n, o) {
        console.log("dialog => watch >> ", n, o)
        this.bannerAdd(n)
        this.dataLoadList.push(n[0])
      },
    },
  },

  data() {
    return {
      frametype: 6,
      layout: [],
      dataLoadList: [],
    }
  },
  created() {
    this.frametype = this.options.frametype
    this.dataLoadList = this.options.dataLoadList
    window.itemdel = this.itemdel
    console.log("데이터 로드 >>>> ", this.dataLoadList)

    this.bannerAdd(this.dataLoadList)
    this.$emit("callbackData", this.getBannerList())
  },
  methods: {
    layoutCreatedEvent: function(newLayout) {
      console.log("Created layout: ", newLayout)
    },
    layoutBeforeMountEvent: function(newLayout) {
      console.log("beforeMount layout: ", newLayout)
    },
    layoutMountedEvent: function(newLayout) {
      console.log("Mounted layout: ", newLayout)
    },
    layoutReadyEvent: function(newLayout) {
      console.log("Ready layout: ", newLayout)
    },
    layoutUpdatedEvent: function(newLayout) {
      console.log("Updated layout: ", newLayout)

      let maxY = Math.max(...this.layout.map(({ y }) => y))

      let maxX0Y = Math.max(
        ...this.layout.filter((w) => w.x === 0).map(({ y }) => y)
      )
      let maxX1Y = Math.max(
        ...this.layout.filter((w) => w.x === 1).map(({ y }) => y)
      )

      if (this.frametype / 2 <= maxY) {
        if (maxX0Y > maxX1Y) {
          let from = this.layout.filter((w) => w.x === 0 && w.y === maxX0Y)
          this.layout[from[0].i].x = 1
          this.layout[from[0].i].y = maxX1Y + 1
        } else if (maxX0Y < maxX1Y) {
          let from = this.layout.filter((w) => w.x === 1 && w.y === maxX1Y)
          this.layout[from[0].i].x = 0
          this.layout[from[0].i].y = maxX0Y + 1
        }
      }
      this.$emit("callbackData", this.getBannerList())
    },
    item_resizeEvent: function(i, newH, newW, newHPx, newWPx) {
      console.log(
        "RESIZE i=" +
          i +
          ", H=" +
          newH +
          ", W=" +
          newW +
          ", H(px)=" +
          newHPx +
          ", W(px)=" +
          newWPx
      )
    },
    item_moveEvent: function(i, newX, newY) {
      console.log("MOVE i=" + i + ", X=" + newX + ", Y=" + newY)
    },
    item_movedEvent: function(i, newX, newY) {
      console.log("MOVED i=" + i + ", X=" + newX + ", Y=" + newY)
    },
    item_resizedEvent: function(i, newH, newW, newHPx, newWPx) {
      console.log(
        "RESIZED i=" +
          i +
          ", H=" +
          newH +
          ", W=" +
          newW +
          ", H(px)=" +
          newHPx +
          ", W(px)=" +
          newWPx
      )
    },
    item_containerResizedEvent: function(i, newH, newW, newHPx, newWPx) {
      console.log(
        "CONTAINER RESIZED i=" +
          i +
          ", H=" +
          newH +
          ", W=" +
          newW +
          ", H(px)=" +
          newHPx +
          ", W(px)=" +
          newWPx
      )
    },
    setBannerInfo: function(data) {
      let html = `<div class='bannerItem' style="background-image : url('${
        data.bannerImageUrl
      }');">
                            <i style='color:red;float:right;margin-right:0.1em;cursor:pointer' onclick='itemdel(${
                              data.bannerCode
                            });'  class="fas fa-minus-square"></i>
                             ${!data.bInfo ? '<i class="fas fa-ad"></i>' : ""} 
                            <div class="header-content">
                                <div class='bannertitle'>${data.title}<div>
                                <div class='bannertitlesub'>${
                                  data.sub_title
                                } <i style='float:right;' class="far fa-bookmark"></i><div>
                            </div>
                          </div>
                        `
      return html
    },
    bannerAdd(addlist) {
      if (this.layout.length == this.frametype) return

      /* 추가 데이터 가져오는 부분 아랫줄을 DB 데이터로 변경*/
      addlist.forEach((contentData) => {
        let addData = { w: 1, h: 1 }

        let maxX0Y =
          this.layout.filter((w) => w.x == 0).length == 0
            ? -1
            : Math.max(
                ...this.layout.filter((w) => w.x === 0).map(({ y }) => y)
              )
        let maxX1Y =
          this.layout.filter((w) => w.x == 1).length == 0
            ? -1
            : Math.max(
                ...this.layout.filter((w) => w.x === 1).map(({ y }) => y)
              )

        addData.bannerCode = contentData.bannerCode
        addData.x = maxX0Y <= maxX1Y ? 0 : 1
        addData.y = (maxX0Y <= maxX1Y ? maxX0Y : maxX1Y) + 1
        addData.i = String(this.layout.length)
        addData.content = this.setBannerInfo(contentData)
        this.layout.push(addData)
      })
      console.log("layout >>>> ", JSON.stringify(this.layout))
      this.$emit("callbackData", this.getBannerList())
    },
    getBannerList() {
      let rtn = this.layout.sort(function(a, b) {
        // 오름차순
        return a.x + a.y * 10 < b.x + b.y * 10
          ? -1
          : a.x + a.y * 10 > b.x + b.y * 10
          ? 1
          : 0
      })

      let rtnList = []

      rtn.forEach(function(item) {
        let rtnData = { x: item.x, y: item.y, bannerCode: item.bannerCode }
        rtnList.push(rtnData)
      })
      return rtnList
    },
    itemdel(bannerCode) {
      this.$emit(
        "callBackDel",
        this.dataLoadList.filter((w) => w.bannerCode === bannerCode)
      )

      for (let i = 0; i < this.layout.length; i++) {
        if (this.layout[i].bannerCode === bannerCode) {
          this.layout.splice(i, 1)
        }
      }
      this.$emit("callbackData", this.getBannerList())
    },
  },
}
</script>
