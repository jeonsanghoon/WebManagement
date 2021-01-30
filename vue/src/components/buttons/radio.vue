<template lang="pug">
  div(ref="radioButton")
    div(class="v-label" v-if="label !== ''") {{ label }}
    v-radio-group(:class="`buttonRadio`" v-model="this.bData" row :small="small" :hide-details="true")
      v-btn(:small="small" :class="`ondajiButton trueButton secondary ${bData === trueValue ? 'active' : ''}`" :rounded="rounded"  :outlined="outlined"  
            @click="change(true, trueValue)"  v-ripple="false")
        div(v-if="isRadioImage" class="dotcircle")
          div(class="dot" v-if="value")
        
        span(class="ondaji_font_16_medium_grey_80") {{ trueText }}
      v-btn(:class="`ml-4 ondajiButton falseButton ${btnType} ${bData === falseValue ? 'active' : ''}`" 
            :rounded="rounded" :outlined="outlined" @click="change(false, falseValue)"  :small="small"
            )
        div(v-if="isRadioImage" class="dotcircle")
          div(class="dot" v-if="!value")
        span(class="ondaji_font_16_medium_grey_80") {{ falseText }}
        
</template>
<style lang="scss">
.buttonRadio .v-icon {
  font-size: 18px !important;
}
</style>

<style lang="scss" scoped>
$defaultColor: #3e2723;
.v-application .ondajiButton.primary {
  background-color: $defaultColor !important;
}

.v-label {
  font-size: 0.75rem;
  margin-bottom: 0.5rem;
}
.v-input--selection-controls {
  margin: 0px;
  padding: 0px;
}
$defaultColor: #3e2723;
.v-application .ondajiButton.secondary {
  background-color: #fff !important;
  color: $defaultColor !important;

  &.active {
    background-color: #e2d9d2 !important;
  }
}

.dotcircle {
  border-radius: 50%;
  border: 1px solid $defaultColor;
  width: 16px;
  height: 16px;
  padding: 2.5px;
  margin-right: 8px;

  .dot {
    border-radius: 50%;
    width: 100%;
    height: 100%;
    background-color: $defaultColor;
  }
}

.v-btn--outlined {
  border: 2px solid $defaultColor;
  border-width: 3px !important;
}

.v-input--radio-group.v-input--radio-group--row .v-radio {
  margin-right: 0px;
}
.v-size--small {
  &.v-btn--outlined {
    border-width: 2px !important;
  }
  .ondaji_font_16_medium_grey_80 {
    font-size: 0.875rem !important;
    font-weight: 400 !important;
  }
}
</style>
<script>
export default {
  props: {
    value: {},
    trueValue: { default: true },
    falseValue: { default: false },
    btnType: {
      btnType: String,
      default: "secondary",
    },
    isRadioImage: {
      type: Boolean,
      default: true,
    },
    rounded: {
      type: Boolean,
      default: true,
    },
    outlined: {
      type: Boolean,
      default: true,
    },
    trueText: {
      type: String,
      default: "실내",
    },
    falseText: {
      type: String,
      default: "실외",
    },
    small: {
      type: Boolean,
      default: false,
    },
    label: {
      type: String,
      default: "",
    },
  },
  created() {},
  mounted() {
    this.init(this.value)
  },
  data() {
    return {
      bData: null,
    }
  },
  watch: {
    bData(val) {
      this.init(val)
    },
  },
  methods: {
    change(isCheck, val) {
      this.bData = val

      this.$emit("input", val)
      //this.setActiveColor(isCheck)
    },
    init(val) {
      this.bData = val
      // eslint-disable-next-line no-debugger
      debugger
      this.setActiveColor(String(this.bData) === String(this.trueValue))
      console.log("setActiveColor", this.bData, this.trueValue)

      if (this.btnType !== "") {
        switch (this.btnType) {
          case "primary":
            break
          case "secondary":
            this.outlined = true
            break
        }
      }
    },
    setActiveColor(isCheck) {
      if (this.$refs.radioButton === undefined) return

      this.$refs.radioButton.querySelectorAll(".ondajiButton").forEach((el) => {
        el.classList.remove("active")
      })
      if (isCheck)
        this.$refs.radioButton
          .querySelector(".ondajiButton.trueButton")
          .classList.add("active")
      else
        this.$refs.radioButton
          .querySelector(".ondajiButton.falseButton")
          .classList.add("active")
    },
  },
}
</script>
