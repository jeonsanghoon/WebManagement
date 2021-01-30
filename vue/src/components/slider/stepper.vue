<template>
  <div
    flat
    ref="ondajiStepper"
    :class="`ondajiStepper  ${model.disabled ? 'disabled' : ''}`"
    :style="`height:${height}`"
  >
    <ul>
      <li
        class="item"
        v-for="(item, index) in list"
        :key="index"
        @click="click(item.value)"
      >
        <div
          class="line active"
          v-if="index > 0 && model.value === item.value - 1"
        ></div>
        <div class="line unactive" v-else-if="index > 0"></div>

        <div class="circle active" v-if="model.value === item.value">
          {{ item.value }}
        </div>
        <div class="circle next" v-else-if="model.value === item.value - 1">
          {{ item.value }}
        </div>
        <div class="circle unactive" v-else>
          {{ item.value }}
        </div>
      </li>
    </ul>
  </div>
</template>

<style lang="scss" scoped>
.ondajiStepper {
  &.disabled {
    pointer-events: none;
    opacity: 0.4;
  }
  ul {
    list-style-type: none;
    padding: 0px;
    height: 100%;
    .circle {
      z-index: 10;
    }
    li {
      height: 36px;
    }
  }
  .item:first-child {
    height: 36px;
  }

  max-width: 36px;
  min-width: 36px;

  .circle {
    border-radius: 50%;
    cursor: pointer;
    min-width: 36px;
    min-height: 36px;
    width: 36px;
    height: 36px;
    font-size: 14px;
    font-weight: 700;
    display: table-cell;
    vertical-align: middle;
    &.active {
      background-color: $default-color !important;
      color: #ffffff;
    }
    &.next {
      border: 3px solid $default-color;
      color: $default-color;
    }
    &.unactive {
      border: 3px solid $disable-color;
      pointer-events: none;
      background-color: #fafafa;
    }
  }
  .line {
    width: 3px;

    margin-right: auto;
    margin-left: auto;
    &.active {
      background-color: $default-color !important;
    }
    &.unactive {
      background-color: $disable-color !important;
    }
  }
}
</style>
<script>
export default {
  props: {
    value: {
      type: Object,
    },
    height: {
      type: String,
      default: "100%",
    },
    isClick: {
      type: Boolean,
      default: true,
    },
  },
  created() {
    this.setList(this.value)
  },
  watch: {
    value(val) {
      this.setList(val)
    },
  },
  mounted() {
    if (!this.isClick) {
      this.$refs.ondajiStepper.querySelectorAll(".circle").forEach((e) => {
        e.style.cursor = "default"
      })
    }
    if (this.model.lineHeight === undefined) this.model.lineHeight = 30
    let line = this.$refs.ondajiStepper.querySelectorAll(".line")

    let tmpHeight = 0

    this.$refs.ondajiStepper.querySelectorAll("li").forEach((el, idx) => {
      if (idx === 0) el.style.height = "36px"
      else
        el.style.height =
          String(
            this.$refs.ondajiStepper.offsetHeight / line.length -
              36 / line.length
          ) + "px"

      if (el.querySelector(".line") != null)
        el.querySelector(".line").style.height =
          String(
            this.$refs.ondajiStepper.offsetHeight / line.length -
              36 -
              36 / line.length
          ) + "px"
    })

    console.log(line, tmpHeight)
  },
  data() {
    return {
      model: {
        total: 4,
        value: 0,
        lineHeight: 30,
        disabled: false,
      },
      list: [],
    }
  },
  methods: {
    setList(val) {
      this.model = Object.assign({}, val)

      this.list = []

      for (let i = 1; i <= this.model.total; i++) {
        let obj = new Object()
        if (i === this.model.value) {
          obj.state = "active"
        } else {
          let nTmp = i - this.model.value
          if (nTmp === 1) obj.state = "next"
          else obj.state = "unactive"
        }
        obj.value = i
        this.list.push(obj)
      }
    },
    click(val) {
      if (!this.isClick) return
      let status = this.list[val - 1].state
      if (status !== "unactive") {
        console.log(val)
        this.model.value = val

        this.$emit("input", this.model)
      }
    },
  },
}
</script>
