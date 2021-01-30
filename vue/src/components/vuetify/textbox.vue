<template>
  <div class="textbox" ref="textbox">
    <v-row>
      <v-col
        v-if="label.trim() !== ''"
        class="label text-left ma-0 ondaji_font_20_medium_brown_80 pa-0"
        v-text="label"
        cols="12"
        sm="4"
      >
      </v-col
      ><v-col
        v-if="label2.trim() !== ''"
        class="label text-left ma-0 ondaji_font_12_thin_brown_80 pa-0"
        v-text="label2"
        cols="12"
        sm="4"
      >
      </v-col
      ><v-col
        cols="12"
        :sm="label.trim() === '' ? 12 : 8"
        class="pa-0 pt-1 pa-sm-0 ondaji_font_16_medium_brown_100"
      >
        <div class="d-flex">
          <input
            :placeholder="placeholder2"
            :readonly="readonly"
            :type="type"
            :class="errorMessage2 != '' ? 'error-input' : ''"
            v-model="text"
          />
          <Buttons-default
            v-if="isBtn2"
            btnType="primary"
            :btnText="btnText"
            :size="btnSize"
            @click="btnClick"
            :small="
              $vuetify.breakpoint.name === 'sm' ||
              $vuetify.breakpoint.name === 'xs'
            "
            class="ml-2"
          />
        </div>
        <div
          class="error--text"
          v-if="errorMessage2 || '' !== ''"
          v-html="errorMessage2"
        ></div>
      </v-col>
    </v-row>
  </div>
</template>
<style lang="scss">
.textbox input:read-only {
  background-color: #e0e0e0;
  color: #aaaaaa !important;
}
</style>
<style lang="scss" scoped>
.textbox .label {
  max-width: inherit;
  min-width: inherit;
  vertical-align: middle;
}
.textbox input[type="text"],
.textbox input[type="password"] {
  width: 100%; /* 원하는 너비 설정 */
  height: 36px; /* 높이값 초기화 */
  line-height: normal; /* line-height 초기화 */
  padding: 0.8em 0.5em; /* 원하는 여백 설정, 상하단 여백으로 높이를 조절 */
  font-family: inherit; /* 폰트 상속 */
  border: 1px solid #c2b7b3;
  border-radius: $border-radius-root; /* iSO 둥근모서리 제거 */
  outline-style: none; /* 포커스시 발생하는 효과 제거를 원한다면 */
  -webkit-appearance: none; /* 브라우저별 기본 스타일링 제거 */
  -moz-appearance: none;

  appearance: none;
  &:focus {
    border: 1px solid #64b5f6;
  }
  &:disabled {
    border: 1px solid #ccc;
    background: #eaeaea;
  }
  &::placeholder {
    color: #aaaaaa;
    font-size: 16px;
  }
  &.error-input {
    border: 1px solid red;
    color: red;
  }
}
.textbox .label {
  max-width: inherit;
  min-width: inherit;
  vertical-align: middle;
}
.textbox input[type="text"],
.textbox input[type="password"] {
  width: 100%; /* 원하는 너비 설정 */
  height: 36px; /* 높이값 초기화 */
  line-height: normal; /* line-height 초기화 */
  padding: 0.8em 0.5em; /* 원하는 여백 설정, 상하단 여백으로 높이를 조절 */
  font-family: inherit; /* 폰트 상속 */
  border: 1px solid #c2b7b3;
  border-radius: $border-radius-root; /* iSO 둥근모서리 제거 */
  outline-style: none; /* 포커스시 발생하는 효과 제거를 원한다면 */
  -webkit-appearance: none; /* 브라우저별 기본 스타일링 제거 */
  -moz-appearance: none;

  appearance: none;
  &:focus {
    border: 1px solid #64b5f6;
  }
  &:disabled {
    border: 1px solid #ccc;
    background: #eaeaea;
  }
  &::placeholder {
    color: #aaaaaa;
    font-size: 14px;
  }
  &.error-input {
    border: 1px solid red;
    color: red;
  }
}

.error--text {
  font-size: 12px;
  height: 12px;
}
@media screen and (max-width: $grid-breakpoints_sm) {
  .textbox .label.col {
    flex-basis: 50px;
  }
  .textbox .label.ondaji_font_20_medium_brown_80 {
    font-size: 16px !important;
  }
  .textbox input {
    height: 30px !important;
    font-size: 16px !important;
  }
}
</style>
<script>
export default {
  props: {
    value: {
      type: String,
    },
    label: {
      type: String,
      default: "",
    },
    label2: {
      type: String,
      default: "",
    },
    placeholder: {
      type: String,
      default: "",
    },
    type: {
      type: String,
      default: "text",
    },
    readonly: {
      type: Boolean,
      default: false,
    },
    isBtn: {
      type: Boolean,
      default: false,
    },
    btnText: {
      type: String,
      default: "버튼",
    },
    errorMessage: {
      type: String,
      default: "",
    },
  },
  watch: {
    value(val) {
      this.text = val

      if (val !== "") this.errorMessage2 = ""
      else this.errorMessage2 = this.errorMessage
      this.$emit("input", val)
    },
    text(val) {
      if (val !== "") this.errorMessage2 = ""
      else this.errorMessage2 = this.errorMessage
      this.$emit("input", val)
    },
    errorMessage(val) {
      console.log(val)
      this.errorMessage2 = this.errorMessage
    },
  },
  created() {
    this.placeholder2 = this.placeholder
    this.isBtn2 = this.isBtn
    this.errorMessage2 = this.errorMessage
    this.text = this.value
  },
  mounted() {
    if (this.$vuetify.breakpoint.xs) {
      this.btnSize = 30
    }
  },
  data() {
    return {
      text: "",
      isMobile: false,
      placeholder2: "",
      isBtn2: false,
      btnSize: 36,
      errorMessage2: "",
    }
  },
  methods: {
    btnClick() {
      this.$emit("btnClick")
    },
  },
}
</script>
