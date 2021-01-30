<template lang="pug">
  div(class="dateTempleate")
    div(ref="dataPicker" v-if="mode === 'rangeD'")
      vc-date-picker(mode="range" v-model="model" class="flex-grow datepicker" hidePopover show-caps)
        //<!--Custom Input Slot-->
        v-text-field(id="date" slot-scope="{ inputProps, inputEvents }" outlined dense
                    v-bind="inputProps" v-on="inputEvents" hide-details color="white" :show-popover="false"
                    :append-icon="model != null ? clearIcon : ''" @click:append="() => {model = null}")
    div(ref="dataPicker" v-else-if="mode === 'range'")
      vc-date-picker(mode="range" v-model="model" class="flex-grow datepicker" hidePopover show-cap)
        //<!--Custom Input Slot-->
        v-text-field(id="date" align-center slot-scope="{ inputProps, inputEvents }" outlined dense v-bind="inputProps" v-on="inputEvents" hide-details color="white")
    div(ref="dataPicker" v-else)
      vc-date-picker(:mode="mode" v-model="model" hidePopover show-cap align-center :input-props="{ placeholder: '날짜추가', readonly: true }")
</template>

<style lang="scss">
.dateTempleate {
  input {
    text-align: center;
    padding: 9.5px 12px;

    &::placeholder {
      color: #bdbdbd !important;
      font-weight: 400 !important;
    }
  }
  .datepicker {
    .v-text-field--outlined fieldset {
      background-color: white !important;
      border: 1px lightgrey solid !important ;
    }
  }
  input.vc-appearance-none.vc-border-gray-400:focus,
  input.vc-appearance-none.vc-border-gray-400:active,
  .v-text-field--outlined.v-input--is-focused fieldset {
    border-color: #bbdefb !important;
  }
}
.vc-text-base {
  font-size: 14px !important;
  color: #242424 !important;
  font-weight: 500 !important;
}
.vc-title {
  font-size: 16px;
  font-weight: bold;
  color: #231815 !important;
}
.vc-text-gray-900 {
  color: #231815 !important;
}
.vc-text-gray-600 {
  color: #231815 !important;
}
.vc-weekday {
  font-size: 12px;
}
.vc-container {
  --day-content-bg-color-focus: none !important;
}
.vc-day-content {
  font-size: 14px !important;
  font-weight: 500 !important;
  &.vc-border-blue-700 {
    color: #fff !important;
  }
}
.vc-border-blue-700 {
  border-color: #3e2723 !important;
}
.vc-bg-blue-600 {
  background-color: #3e2723 !important;
}
.vc-bg-blue-200 {
  background-color: #f2f2f2 !important;
  color: #fff !important;
}
.vc-text-white {
  color: #fff !important;
}

.focus\:vc-shadow:focus {
  box-shadow: none !important;
}

// 선택했을 때 컬러
.vc-text-blue-900 {
  color: #403532 !important;
}

.vc-bg-gray-800 {
  background-color: #231815 !important;
}

.vc-border-gray-400 {
  border-color: #bdbdbd !important;
}
</style>
<script>
export default {
  props: {
    mode: {
      type: String,
      default: "single",
    },
    value: {},
    clearIcon: {
      type: String,
      default: "cancel",
    },
    date: () => ({}),
  },
  created() {
    this.model = this.value
  },
  watch: {
    model(val) {
      this.$emit("input", val)
    },
    value() {
      this.model = this.value
    },
  },

  mounted() {
    /* document
      .querySelector(".datep-picker v-text-field__slot")
      .classList.add("inputFocus")
      */
  },
  data() {
    return {
      model: null,
      formats: {
        title: "MMMM YYYY",
        weekdays: "W",
        navMonths: "MMM",
        input: ["YYYY-MM-DD", "L"], // Only for v-date-picker
        dayPopover: ["YYYY-MM-DD"], // Only for v-date-picker,
        data: ["YYYY-MM-DD"], // For attribute dates,
      },
    }
  },
}
</script>
