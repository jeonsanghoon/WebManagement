<template lang="pug">
  v-container(class="pa-0")
    div(class="d-flex justify-space-between")
        div(v-for="(icon, index) in icons" :key="index" class="btn-wrap")
            v-btn(class="order-btn" @click="clickOrderBtn(index)" text depressed)
                v-img(containwidth="22" :src="require(`@/assets/images/icons/${icon}`)")
</template>

<script>
export default {
  props: {
    value: {
      type: Array,
      required: false,
    },
    checkName: {
      type: String,
      default: "bChk",
    },

    seqName: {
      type: String,
      default: "seq",
    },
  },
  data() {
    return {
      orderType: {
        Top: 0,
        Up: 1,
        Bottom: 2,
        Down: 3,
      },
      type: null,
      icons: ["top.svg", "onespace_top.svg", "down.svg", "onespace_down.svg"],
    }
  },
  created() {},
  methods: {
    clickOrderBtn(type) {
      if ((this.value || null) === null || (this.value.length || null) === null)
        return

      this.orderProcess(type)
    },
    orderProcess(type) {
      let rtnList = [...this.value]
      let checkList = rtnList.filter((w) => w[this.checkName] === true)
      checkList.forEach((element) => {
        element[this.checkName] = true
      })

      let checkIndex = rtnList.findIndex((w) => w[this.checkName] === true)

      if (!this.checkProcess(type, rtnList, checkList, checkIndex)) return
      let rtn = []
      switch (type) {
        case this.orderType.Top:
          rtn = [...this.orderTop(rtnList)]
          break
        case this.orderType.Bottom:
          rtn = [...this.orderBottom(rtnList)]
          break
        case this.orderType.Up:
          rtn = this.orderUp(rtnList)
          break
        case this.orderType.Down:
          rtn = [...this.orderDown(rtnList)]
          break
      }

      for (let i = 0; i < rtn.length; i++) rtn[i][this.seqName] = i + 1

      console.log("onprocress", rtn)
      //this.$emit("input", rtn)
      this.$emit("change", rtn)
    },
    checkProcess(type, rtnList, checkList, checkIndex) {
      if (
        checkList.length === 1 &&
        (type === this.orderType.Down || type === this.orderType.Bottom)
      ) {
        if (rtnList[rtnList.length - 1][this.checkName] === true) return false
      }

      if (
        checkList.length === 1 &&
        (type === this.orderType.Top || type === this.orderType.Up)
      ) {
        if (checkIndex === 0) {
          return false
        }
      }
      return true
    },
    orderTop(list) {
      return [
        ...list.filter((w) => w[this.checkName] === true),
        ...list.filter((w) => (w[this.checkName] || false) === false),
      ]
    },
    orderBottom(list) {
      return [
        ...list.filter((w) => (w[this.checkName] || false) === false),
        ...list.filter((w) => w[this.checkName] === true),
      ]
    },
    orderUp(list) {
      let checkList = []
      for (let i = list.length - 1; i >= 0; i--) {
        if (list[i][this.checkName] === true) {
          checkList.push(list[i])
        } else if (checkList.length > 0) {
          // eslint-disable-next-line no-debugger
          debugger
          list = [
            ...list.slice(0, i),
            ...checkList,
            ...list.slice(i, i + 1),
            ...list.slice(i + 1 + checkList.length),
          ]
          checkList = []
        }
      }

      return list
    },
    orderDown(list) {
      let checkList = []
      for (let i = 0; i < list.length; i++) {
        if (list[i][this.checkName] === true) {
          checkList.push(list[i])
        } else if (checkList.length > 0) {
          list = [
            ...list.slice(0, i - 1 * checkList.length),
            ...list.slice(i, i + 1),
            ...checkList,
            ...list.slice(i + 1),
          ]
          checkList = []
        }
      }
      return list
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
