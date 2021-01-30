<template>
  <v-btn
    type="button"
    ref="ondajibutton"
    :width="width"
    :height="size"
    :rounded="rounded2"
    :tile="tile"
    :text="text2"
    :outlined="outlined2"
    :disabled="disabled"
    :depressed="depressed2"
    :class="`ondajiButton size${size} ${btnType} text-${textAlign2}`"
    @click="click"
    onsubmit="return false;"
  >
    <v-icon :class="`buttonicon preappend`" v-if="preAppendIcon !== null">{{
      preAppendIcon
    }}</v-icon>
    <span style="width: 100%">{{ btnText }}</span>
    <v-icon class="append" :size="size" v-if="appendIcon !== null">{{
      appendIcon
    }}</v-icon>
  </v-btn>
</template>

<style lang="scss" scoped>
.v-btn--outlined {
  border-width: 3px !important;
}
.ondajiButton {
  padding-right: 0px;
  &:hover {
    cursor: pointer;
  }
}

$defaultColor: #3e2723;
.v-application .ondajiButton.primary {
  background-color: $defaultColor !important;
}

.v-application .ondajiButton.secondary {
  background-color: #fff !important;
  color: $defaultColor !important;
  &:hover,
  &.active {
    background-color: #e2d9d2 !important;
  }
}

.v-application .ondajiButton.text {
  background-color: transparent;
  color: $defaultColor !important;
  &:hover {
    background-color: #eaeaea !important;
  }
}
.v-application .ondajiButton.request {
  background-color: #2979ff !important;
  color: #fff;
}

.size40 {
  $fontSize: 16px;
  $iconpadding: 12px;
  font-size: $fontSize !important;
  font-weight: 700 !important;

  & .v-icon {
    $iconsize: ($fontSize)-2px;
    font-size: $iconsize !important;
    padding-top: 2px;
    &.preappend {
      padding-right: $iconpadding !important;
    }
    &.append {
      padding-left: $iconpadding !important;
    }
  }
}

.size {
  $fontSize: 14px;
  $iconpadding: 10px;
  &36,
  &30 {
    font-size: $fontSize !important;
    font-weight: 700 !important;

    & .v-icon {
      $iconsize: ($fontSize)-2px;
      font-size: $iconsize !important;

      padding-top: 1px;

      &.preappend {
        padding-right: $iconpadding !important;
        width: 0px;
      }
      &.append {
        padding-left: $iconpadding !important;
        width: 0px;
      }
    }
  }
}
.v-btn {
  padding-left: 16px !important;
  padding-right: 16px !important;
}

.size {
  $fontSize: 13px;
  $iconpadding: 8px;
  &28,
  &26 {
    padding-left: 12px !important;
    padding-right: 12px !important;
    font-size: $fontSize !important;
    font-weight: 500 !important;

    & .v-icon {
      $iconsize: ($fontSize)-2px;
      font-size: $iconsize !important;

      padding-top: 1.5px;

      &.preappend {
        padding-right: $iconpadding !important;
        width: 0px;
      }
      &.append {
        padding-left: $iconpadding !important;
        width: 0px;
      }
    }
  }
}

.size {
  &24 {
    $fontSize: 12px;
    $iconpadding: 4px;
    padding-left: 10px !important;
    padding-right: 10px !important;
    font-size: $fontSize !important;
    padding: 0px 10px 0px 9px !important;
    & .v-icon {
      $iconsize: ($fontSize)-2px;
      font-size: $iconsize !important;
      padding-top: 2px;
      &.preappend {
        padding-right: $iconpadding !important;
      }
      &.append {
        padding-left: $iconpadding !important;
      }
    }
  }
}
</style>
<script>
export default {
  props: {
    btnType: {
      type: String,
      default: "",
    },
    rounded: {
      type: Boolean,
      default: null,
    },
    tile: {
      type: Boolean,
      default: false,
    },
    text: {
      type: Boolean,
      default: false,
    },
    depressed: {
      type: Boolean,
      default: false,
    },
    outlined: {
      type: Boolean,
      default: false,
    },
    disabled: {
      type: Boolean,
      defalut: false,
    },
    size: {
      type: Number,
      default: 36,
    },
    textAlign: {
      type: String,
      default: null,
    },
    appendIcon: {
      type: String,
      default: null,
    },
    preAppendIcon: {
      type: String,
      default: null,
    },
    btnText: {
      type: String,
      default: "버튼이다",
    },
    width: {
      type: String,
      default: "auto",
    },
    isActive: {
      type: Boolean,
      default: false,
    },
  },
  created() {
    this.depressed2 = this.depressed
    this.outlined2 = this.outlined
    this.rounded2 = this.rounded
    this.text2 = this.text

    if (this.btnType !== "") {
      this.depressed2 = true
      switch (this.btnType) {
        case "primary":
          break
        case "secondary":
          this.outlined2 = true
          break
        case "text":
          this.text2 = true
          if (this.rounded === null) this.rounded2 = true
          break
        case "request":
          if (this.rounded === null) this.rounded2 = true
          break
      }
    }
  },
  watch: {
    isActive(val) {
      if (val) {
        this.$refs.ondajibutton.$el.classList.add("active")
      } else {
        this.$refs.ondajibutton.$el.classList.remove("active")
      }
    },
  },
  mounted() {
    try {
      console.log("size", this.size)
      if (this.textAlign === undefined) this.textAlign2 = null
      else this.textAlign2 = this.textAlign
      if (this.appendIcon !== null) {
        if (this.textAlign2 === null) this.textAlign2 = "left"
        this.$refs.ondajibutton.$el.style.paddingLeft = "18px"
        this.$refs.ondajibutton.$el.style.paddingRight = "15px"
      } else if (this.preAppendIcon !== null) {
        if (this.textAlign2 === null) this.textAlign2 = "right"
        this.$refs.ondajibutton.$el.style.paddingLeft = "15px"
        this.$refs.ondajibutton.$el.style.paddingRight = "15px"
      } else {
        if (this.textAlign2 === null) this.textAlign2 = "center"
      }
    } catch (e) {
      console.log(e)
    }

    //this.$refs.ondajibutton.$el .v-btn:not(.v-btn--round).v-size--default
  },
  data() {
    return {
      color: "red",
      textAlign2: null,
      depressed2: false,
      outlined2: false,
      rounded2: false,
      text2: false,
    }
  },
  methods: {
    click() {
      this.$emit("click")
    },
  },
}
</script>
