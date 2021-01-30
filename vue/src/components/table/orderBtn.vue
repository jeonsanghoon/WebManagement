<template lang="pug">
  v-container(class="pa-0")
    div(class="d-flex justify-space-between")
      div(v-for="(icon, index) in icons" :key="index" class="btn-wrap")
        v-btn(class="order-btn" @click="clickOrderBtn(index)" text depressed)
          v-img(contain width="22" :src="require(`@/assets/images/icons/${icon}`)")
</template>

<script>
import { commonMediaShop } from "@/views/mediaShop/mixins/commonMixins"

export default {
  mixins: [commonMediaShop],
  props: {
    orderList: {
      type: Array,
      required: true,
    },
  },
  data() {
    return {
      ORDER_TYPE: {
        TOP: 0,
        UP: 1,
        BOTTOM: 2,
        DOWN: 3,
      },

      type: null,

      icons: [],
    }
  },
  created() {
    this.icons.push("top.svg")
    this.icons.push("onespace_top.svg")
    this.icons.push("down.svg")
    this.icons.push("onespace_down.svg")
  },
  methods: {
    clickOrderBtn(type) {
      if (this.checkNull(this.orderList) === true) {
        return
      }

      this.orderProcess(type)
    },
    orderProcess(type) {
      let retList = this.orderList.slice()

      if (retList.length === 0) {
        return
      }

      if (type === this.ORDER_TYPE.DOWN || type === this.ORDER_TYPE.BOTTOM) {
        if (retList[retList.length - 1].check === true) {
          return
        }
      }

      const checkIndex = retList.findIndex((banner) => banner.check === true)

      if (type === this.ORDER_TYPE.TOP || type === this.ORDER_TYPE.UP) {
        if (checkIndex === 0) {
          return
        }
      }

      const checkBanners = retList.filter((banner) => {
        return banner.check === true
      })

      const noneBanners = retList.filter((banner) => {
        return banner.check === false
      })

      const bannerSeq = retList.map((banner) => {
        return banner.seq
      })

      retList = this.getOrderList(
        type,
        checkIndex,
        checkBanners,
        noneBanners,
        bannerSeq
      )

      this.$emit("orderComplete", retList)
    },
    getOrderList(type, checkIndex, checkBanners, noneBanners, bannerSeq) {
      let tempBanners = []

      switch (type) {
        case this.ORDER_TYPE.TOP:
          tempBanners = this.orderTopBottom(
            checkBanners,
            noneBanners,
            bannerSeq
          )
          break
        case this.ORDER_TYPE.UP:
          tempBanners = this.orderUp(
            checkIndex - 1,
            checkBanners,
            noneBanners,
            bannerSeq
          )
          break
        case this.ORDER_TYPE.DOWN:
          tempBanners = this.orderDown(
            checkIndex,
            checkBanners,
            noneBanners,
            bannerSeq
          )
          break
        case this.ORDER_TYPE.BOTTOM:
          tempBanners = this.orderTopBottom(
            noneBanners,
            checkBanners,
            bannerSeq
          )
          break
      }

      return tempBanners
    },
    orderTopBottom(temp1, temp2, bannerSeq) {
      let tempOrderList = []

      tempOrderList = [...temp1, ...temp2]

      tempOrderList.forEach((banner, i) => {
        banner.index = i
        banner.seq = bannerSeq[i]
      })

      return tempOrderList
    },
    orderUp(checkIndex, checkBanners, noneBanners, bannerSeq) {
      let tempOrderList = []

      let totalIndex = 0

      for (let outIndex = 0; outIndex < noneBanners.length; outIndex++) {
        if (outIndex === checkIndex) {
          for (let inIndex = 0; inIndex < checkBanners.length; inIndex++) {
            checkBanners[inIndex].index = totalIndex
            checkBanners[inIndex].seq = bannerSeq[totalIndex]

            tempOrderList.push(checkBanners[inIndex])

            totalIndex++
          }
        }
        noneBanners[outIndex].index = totalIndex
        noneBanners[outIndex].seq = bannerSeq[totalIndex]

        tempOrderList.push(noneBanners[outIndex])

        totalIndex++
      }

      return tempOrderList
    },
    orderDown(checkIndex, checkBanners, noneBanners, bannerSeq) {
      let tempOrderList = []

      let totalIndex = 0

      for (let outIndex = 0; outIndex < noneBanners.length; outIndex++) {
        noneBanners[outIndex].index = totalIndex
        noneBanners[outIndex].seq = bannerSeq[totalIndex]

        tempOrderList.push(noneBanners[outIndex])

        totalIndex++

        if (outIndex === checkIndex) {
          for (let inIndex = 0; inIndex < checkBanners.length; inIndex++) {
            checkBanners[inIndex].index = totalIndex
            checkBanners[inIndex].seq = bannerSeq[totalIndex]

            tempOrderList.push(checkBanners[inIndex])

            totalIndex++
          }
        }
      }

      return tempOrderList
    },
  },
}
</script>

<style lang="scss" scoped>
.btn-wrap {
  padding: 0 2px;
}

.order-btn {
  padding: 4px !important;
  min-width: 22px !important;
  height: 22px !important;
}

.order-btn:hover {
  background: white;
  overflow: hidden;
  height: 100%;
  z-index: 1;
  border-radius: 3px;
  box-shadow: 0 1px #aaaaaa !important;
}
</style>
