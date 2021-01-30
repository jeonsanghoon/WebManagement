<template lang="pug">
  div
    //지도 위에 표시될 마커 카테고리 
    div(class="category")
      v-container(class="ml-0 pa-0")
        v-row
          v-col(cols="12" class="pt-0 pb-0")
            v-card(style="height:28px;" outlined class="selCategory pl-1 pr-1")
              a(href="javascript:void(0)" @click="depthClick(1)") {{ category.depth1.selectedName}}
              v-icon(dark :style="'display:' + (category.depth1.selectedValue === '08' ? 'none' : '')") keyboard_arrow_right
              a(:style="'display:' + (category.depth1.selectedValue === '08' ? 'none' : '')" href="javascript:void(0)" @click="depthClick(2)") {{ category.depth2.selectedName }}
              v-icon(dark) keyboard_arrow_right
              a(href="javascript:void(0)" @click="depthClick(3)") {{ category.depth3.selectedName }}
            
        
        v-row
          v-col(cols="12" class="pt-1")
            div(class="dvDepth1" :style="'display:' + (category.depth1.isShow ? 'block' : 'none')")
              v-list
                v-list-item(:class="'item_' + replaceString(item.code)" v-for="(item, index) in this.category.depth1.list" :key="index" :value="item.code"
                            @click="dvDepthClick(item, 1)") {{ item.name }}
                
            div(class="dvDepth2" :style="'display:' + (category.depth2.isShow ? 'block' : 'none')")
              v-list
                v-list-item(  :class="'item_' + replaceString(item.code)" 
                              v-for="(item, index) in this.category.depth2.list" :key="index" :value="item.code"
                              @click="dvDepthClick(item, 2)") {{ item.name }}
            
            div(class="dvDepth3" :style="'display:' + (category.depth3.isShow ? 'block' : 'none')")
              v-list
                v-list-item(:class="'item_' + replaceString(item.code)"
                            v-for="(item, index) in this.category.depth3.list"  :key="index"  :value="item.code"
                            @click="dvDepthClick(item, 3)") {{ item.name }}
</template>
<style lang="scss" scoped>
.category,
.category * {
  color: #000;
  text-align: left !important;
  font-size: 0.75rem !important;
  font-weight: 500;
}
.category {
  .v-card {
    padding-top: 4px;
    max-width: max-content;
  }
  overflow: hidden;

  font-size: 12px;
  text-align: center;
  a {
    padding: 0.5em;
    text-decoration-line: none;
    cursor: pointer;
  }
  a:hover {
    text-decoration-line: underline;
    cursor: pointer;
  }
}
.dvDepth {
  &1 {
    margin-left: 0rem;
    padding-left: 0px;
  }
  &2 {
    margin-left: 5.5rem;
    padding-left: 0px;
  }
  &3 {
    margin-left: 9rem;
    padding-left: 0px;
  }
}
[class*="dvDepth"] {
  background-color: #fff;
  position: fixed;
  overflow: hidden;
  margin-top: 0px;
  padding-top: 0px;
  z-index: 10;
}
select {
  border: 1px solid #b1b1b1;
}
select::-webkit-scrollbar {
  width: 10px;
}
select::-webkit-scrollbar-thumb {
  background-color: #b1b1b1;
  border-radius: 3px;
  background-clip: padding-box;
  border: 1px solid #b1b1b1;
}
select::-webkit-scrollbar-track {
  background-color: rgb(199, 199, 199);
  border-radius: 3px;
  box-shadow: inset 0px 0px 5px white;
}
.container {
  height: 28px;
}
.v-list {
  height: 180px;
  padding-top: 0px;
  padding-bottom: 0px;
  overflow-y: auto;
  .v-list-item {
    min-height: 36px;
    &.selected {
      background: #eaeaea !important;
    }
  }
}
</style>
<script>
export default {
  props: {
    value: {
      type: String,
      default: "01|02|06",
    },
  },
  watch: {
    value(val) {
      console.log(val)
      this.isChangeEvent = false

      if (val !== this.areaCode) {
        this.areaCode = val
        setTimeout(() => {
          this.init(val)
        }, 50)
      }
    },
  },
  data() {
    return {
      isChangeEvent: false,
      areaCode: "01|01|01",
      category: {
        depth1: {
          isShow: false,

          selectedValue: "01",
          selectedName: "서울특별시",
          moveMapName: "",
          list: [{ code: "01", name: "서울특별시" }],
        },
        depth2: {
          isShow: false,

          selectedValue: "01",
          selectedName: "강남구",
          moveMapName: "",
          list: [{ code: "01", name: "강남구" }],
        },
        depth3: {
          isShow: false,
          selectedValue: "01",
          selectedName: "개포1동",
          moveMapName: "",
          list: [{ code: "01", name: "개포1동" }],
        },
      },
      arrSelCategory: [],
    }
  },
  created() {
    this.init(this.value)
  },
  methods: {
    init(val) {
      if (val === "") return

      this.arrSelCategory = String(val).split("|")
      this.category.depth1.selectedValue = this.arrSelCategory[0]
      this.category.depth2.selectedValue = this.arrSelCategory[1]
      this.category.depth3.selectedValue = this.arrSelCategory[2]

      this.category.depth1.isShow = false
      this.category.depth2.isShow = false
      this.category.depth3.isShow = false

      this.setDepth1()
    },
    depthClick: function(nType) {
      this.category.depth1.isShow = false
      this.category.depth2.isShow = false
      this.category.depth3.isShow = false

      if (nType === 1) {
        this.category.depth1.isShow = true
      }
      if (nType === 2) {
        document.querySelector(".dvDepth2").style.marginLeft =
          String(
            document.querySelectorAll(".selCategory a")[0].offsetWidth +
              document.querySelectorAll(".selCategory .v-icon")[0].offsetWidth
          ) + "px"
        this.category.depth2.isShow = true
        let cw2 = document.querySelectorAll(".selCategory a")[0].offsetWidth
        console.log(cw2)
      }
      if (nType === 3) {
        document.querySelector(".dvDepth3").style.marginLeft =
          String(
            document.querySelectorAll(".selCategory a")[0].offsetWidth +
              document.querySelectorAll(".selCategory a")[1].offsetWidth +
              document.querySelectorAll(".selCategory .v-icon")[0].offsetWidth +
              document.querySelectorAll(".selCategory .v-icon")[1].offsetWidth
          ) + "px"

        this.category.depth3.isShow = true
      }
      setTimeout(() => {
        this.setScrollTop(nType)
      }, 50)
    },
    dvDepthClick: function(item, nType) {
      this.category.depth1.isShow = false
      this.category.depth2.isShow = false
      this.category.depth3.isShow = false

      this.category["depth" + String(nType)].selectedValue = item.code
      this.category["depth" + String(nType)].selectedName = item.name

      this.isChangeEvent = true
      if (nType === 1) {
        this.arrSelCategory[0] = item.code
        this.arrSelCategory[1] = "01"
        this.arrSelCategory[2] = "01"
        this.areaCode = this.getCategory()
        this.setDepth2()
      }
      if (nType === 2) {
        this.arrSelCategory[1] = item.code.split("|")[1]
        this.arrSelCategory[2] = "01"
        this.areaCode = this.getCategory()
        this.setDepth3()
      }

      if (nType === 3) {
        this.arrSelCategory[2] = item.code.split("|")[2]
        this.areaCode = this.getCategory()
        this.$emit("change", this.getCategory(), this.getCategoryNames())

        this.$emit("input", this.areaCode)
        this.isChangeEvent = false
      }
    },
    getCategory() {
      return (
        this.arrSelCategory[0] +
        "|" +
        this.arrSelCategory[1] +
        "|" +
        this.arrSelCategory[2]
      )
    },
    getCategoryNames() {
      return (
        this.category.depth2.selectedName +
        " " +
        this.category.depth3.selectedName
      )
    },
    setDepth1() {
      this.$BaseCommon.restful
        .call2("/common/getAddressAreaList", { level: 1, hide: false })
        .then((res) => {
          this.category.depth1.list = res
          this.category.depth1.selectedValue = this.arrSelCategory[0]

          this.category.depth1.selectedValue =
            this.category.depth1.selectedValue === ""
              ? "01"
              : this.category.depth1.selectedValue
          this.category.depth1.selectedName = res.filter(
            (f) => f.code === this.category.depth1.selectedValue
          )[0].name

          this.setDepth2()
        })
    },
    setDepth2: function() {
      this.$BaseCommon.restful
        .call2("/common/getAddressAreaList", {
          level: 2,
          code: this.category.depth1.selectedValue,
          hide: false,
        })
        .then((res) => {
          console.log("setDepth2 =>", res, this.category.depth2.moveMapName)
          this.category.depth2.list = res

          if (
            this.category.depth1.selectedValue === "08" ||
            this.category.depth2.moveMapName !== ""
          ) {
            try {
              this.category.depth2.selectedValue = this.arrSelCategory[1]
            } catch (e) {
              console.log(e)
            }
          } else {
            this.category.depth2.selectedValue = "" //res[0].code
            this.category.depth2.selectedName = "선택" //res[0].name
            this.category.depth3.list = []
            this.category.depth3.selectedValue = "" //res[0].code
            this.category.depth3.selectedName = "" //res[0].namex
          }
          this.category.depth2.selectedValue =
            this.arrSelCategory[0] + "|" + this.arrSelCategory[1]

          this.category.depth2.selectedName = res.filter(
            (f) => f.code === this.category.depth2.selectedValue
          )[0].name

          this.setDepth3()
        })
    },
    setDepth3: function() {
      this.$BaseCommon.restful
        .call2("/common/getAddressAreaList", {
          level: 3,
          code: this.category.depth2.selectedValue,
          hide: false,
        })
        .then((res) => {
          console.log("setDepth3", res, this.category.depth3.moveMapName)
          this.category.depth3.list = res
          if (
            this.category.depth1.selectedValue === 8 ||
            this.category.depth3.moveMapName !== ""
          ) {
            this.category.depth3.selectedName = this.category.depth3.moveMapName
            try {
              this.category.depth3.selectedValue = this.arrSelCategory[2]
            } catch (e) {
              console.log(e)
            }
            this.category.depth3.moveMapName = ""
          } else {
            this.category.depth3.selectedValue = "" //res[0].code
            this.category.depth3.selectedName = "선택" //res[0].name
          }
          this.category.depth3.selectedValue =
            this.arrSelCategory[0] +
            "|" +
            this.arrSelCategory[1] +
            "|" +
            this.arrSelCategory[2]
          this.category.depth3.selectedName = res.filter(
            (f) => f.code === this.category.depth3.selectedValue
          )[0].name

          if (this.isChangeEvent)
            setTimeout(() => {
              this.areaCode = this.getCategory()
              this.$emit("change", this.getCategory(), this.getCategoryNames())
              this.$emit("input", this.areaCode)
              this.isChangeEvent = false
            }, 50)
        })
    },
    replaceString(str, searchStr = "|", replaceStr = "_") {
      return this.$BaseCommon.stringInfo.replaceAll(str, searchStr, replaceStr)
    },
    setListActiveCss(nType) {
      if (
        document.querySelector(
          `.category .dvDepth${nType} .v-list-item.selected`
        ) !== null &&
        document.querySelector(
          `.category .dvDepth${nType} .v-list-item.selected`
        ).classList !== null
      ) {
        document
          .querySelector(`.category .dvDepth${nType} .v-list-item.selected`)
          .classList.remove("selected")
      }
      let tag = this.$BaseCommon.stringInfo.replaceAll(
        this.category["depth" + String(nType)].selectedValue,
        "|",
        "_"
      )
      if (
        document.querySelector(`.category .dvDepth${nType} .item_` + tag) !==
        null
      )
        document
          .querySelector(`.category .dvDepth${nType} .item_` + tag)
          .classList.add("selected")
    },
    setScrollTop(nType) {
      this.setListActiveCss(nType)
      let idx = this.category["depth" + String(nType)].list.findIndex(
        (f) => f.code === this.category["depth" + String(nType)].selectedValue
      )

      let nTop = idx * 40 - 40 * 1
      if (idx > 4)
        document.querySelector(
          `.category .dvDepth${nType} .v-list`
        ).scrollTop = nTop
      else
        document.querySelector(
          `.category .dvDepth${nType} .v-list`
        ).scrollTop = 0
    },
    depthClose() {
      this.category.depth1.isShow = false
      this.category.depth2.isShow = false
      this.category.depth3.isShow = false
    },
  },
}
</script>
