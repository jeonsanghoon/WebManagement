<template>
  <v-container>
    <v-row align="start" justify="start">
      <v-col cols="12">
        <Vuetify-menutitle @click="click" />
      </v-col>
      <v-col cols="12" class="text-left">
        <div>
          <span class="ondaji_font_18_medium_brown_70 pt-2 pl-2">type </span>
          <span class="ondaji_font_14_thin_brown_70 pt-2 pl-2"
            >- primary, secondary, text, request</span
          >
          <span class="ondaji_font_18_medium_brown_70 pt-2 pl-2"> size </span>
          <span class="ondaji_font_14_thin_brown_70 pt-2 pl-2"
            >- 40, 30, 28, 26, 24</span
          >
        </div>
        <div>
          <span class="ondaji_font_18_medium_brown_70 pt-2 pl-2">
            preAppendIcon
          </span>
          <span class="ondaji_font_14_thin_brown_70 pt-2 pl-2"
            >- font awsome 이미지 사용</span
          >
          <span class="ondaji_font_18_medium_brown_70 pt-2 pl-2">
            appendIcon
          </span>
          <span class="ondaji_font_14_thin_brown_70 pt-2 pl-2"
            >- font awsome 이미지 사용</span
          >
        </div>
        <div>
          <span class="ondaji_font_18_medium_brown_70 pt-2 pl-2"> width </span>
          <span class="ondaji_font_14_thin_brown_70 pt-2 pl-2">- 버튼넓이</span>
        </div>
      </v-col>
      <v-col cols="12">
        <Buttons-default
          btnType="primary"
          :size="40"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="primary"
          :size="36"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="primary"
          :size="30"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="primary"
          :size="28"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="primary"
          :size="26"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="primary"
          :size="24"
          class="ma-2"
          @click="click"
        />
        <div class="ondaji_font_18_medium_brown_70">
          예제) &lt;Buttons-default btnType="primary" :size="40"
          @click="click"/&gt;
        </div>
      </v-col>

      <v-col cols="12">
        <Buttons-default
          btnType="primary"
          disabled
          :size="40"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="primary"
          disabled
          :size="36"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="primary"
          disabled
          :size="30"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="primary"
          disabled
          :size="28"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="primary"
          disabled
          :size="26"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="primary"
          disabled
          :size="24"
          class="ma-2"
          @click="click"
        />
        <div class="ondaji_font_18_medium_brown_70">
          예제) &lt;Buttons-default btnType="primary" disabled :size="40"
          @click="click"/&gt;
        </div>
      </v-col>
      <v-col cols="12">
        <Buttons-default
          btnType="secondary"
          :size="40"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="secondary"
          :size="36"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="secondary"
          :size="30"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="secondary"
          :size="28"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="secondary"
          :size="26"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="secondary"
          :size="24"
          class="ma-2"
          @click="click"
        />
        <div class="ondaji_font_18_medium_brown_70">
          예제) &lt;Buttons-default btnType="secondary" :size="40"
          @click="click"/&gt;
        </div>
      </v-col>
      <v-col cols="12">
        <Buttons-default
          btnType="text"
          :size="40"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="text"
          :size="36"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="text"
          :size="30"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="text"
          :size="28"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="text"
          :size="26"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="text"
          :size="24"
          class="ma-2"
          appendIcon="fas fa-chevron-right"
          @click="click"
        />
        <div class="ondaji_font_18_medium_brown_70">
          예제) &lt;Buttons-default btnType="text" :size="40"
          @click="click"/&gt;
        </div>
      </v-col>
      <v-col cols="12">
        <Buttons-default
          btnType="request"
          :size="40"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="request"
          :size="36"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="request"
          :size="30"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="request"
          :size="28"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="request"
          :size="26"
          class="ma-2"
          @click="click"
        />
        <Buttons-default
          btnType="request"
          :size="24"
          class="ma-2"
          @click="click"
        />
        <div class="ondaji_font_18_medium_brown_70">
          예제) &lt;Buttons-default btnType="request" :size="40"
          @click="click"/&gt;
        </div>
      </v-col>
      <v-col cols="12">
        <Buttons-default
          btnType="primary"
          :size="40"
          class="ma-2"
          appendIcon="fas fa-chevron-right"
          @click="click"
        />
        <Buttons-default
          btnType="primary"
          :size="36"
          class="ma-2"
          appendIcon="fas fa-chevron-right"
          width="200px"
          @click="click"
        />
        <Buttons-default
          btnType="primary"
          :size="30"
          class="ma-2"
          preAppendIcon="fas fa-chevron-left"
          width="200px"
          @click="click"
        />
        <Buttons-default
          btnType="primary"
          :size="28"
          class="ma-2"
          appendIcon="fas fa-chevron-right"
          @click="click"
        />
        <Buttons-default
          btnType="primary"
          :size="26"
          class="ma-2"
          appendIcon="fas fa-chevron-right"
          @click="click"
        />
        <Buttons-default
          btnType="primary"
          :size="24"
          class="ma-2"
          appendIcon="fas fa-chevron-right"
          @click="click"
        />
        <div class="ondaji_font_18_medium_brown_70">
          예제) &lt;Buttons-default btnType="primary" :size="40" appendIcon="fas
          fa-chevron-right" @click="click"/&gt;
        </div>
      </v-col>
      <v-col cols="12">
        <Buttons-default
          btnType="secondary"
          :size="40"
          class="ma-2"
          preAppendIcon="fas fa-chevron-left"
          @click="click"
        />
        <Buttons-default
          btnType="secondary"
          :size="36"
          class="ma-2"
          appendIcon="fas fa-chevron-right"
          @click="click"
        />
        <Buttons-default
          btnType="secondary"
          :size="30"
          class="ma-2"
          appendIcon="fas fa-chevron-right"
          @click="click"
        />
        <Buttons-default
          btnType="secondary"
          :size="28"
          class="ma-2"
          appendIcon="fas fa-chevron-right"
          width="200px"
          @click="click"
        />
        <Buttons-default
          btnType="secondary"
          :size="26"
          class="ma-2"
          appendIcon="fas fa-chevron-right"
          width="120px"
          @click="click"
        />
        <Buttons-default
          btnType="secondary"
          :size="24"
          class="ma-2"
          appendIcon="fas fa-chevron-right"
          @click="click"
        />

        <div class="ondaji_font_18_medium_brown_70">
          예제) &lt;Buttons-default btnType="primary" :size="40"
          preAppendIcon="fas fa-chevron-left" @click="click"/&gt;
        </div>
      </v-col>
      <v-col cols="12">
        <Buttons-default
          btnType="text"
          :size="40"
          class="ma-2"
          appendIcon="fas fa-chevron-right"
          @click="click"
        />
        <Buttons-default
          btnType="text"
          :size="36"
          class="ma-2"
          preAppendIcon="fas fa-chevron-left"
          @click="click"
        />
        <Buttons-default
          btnType="text"
          :size="30"
          class="ma-2"
          appendIcon="fas fa-chevron-right"
          @click="click"
        />
        <Buttons-default
          btnType="text"
          :size="28"
          class="ma-2"
          appendIcon="fas fa-chevron-right"
          @click="click"
        />
        <Buttons-default
          btnType="text"
          :size="26"
          class="ma-2"
          appendIcon="fas fa-chevron-right"
          @click="click"
        />
        <Buttons-default
          btnType="text"
          :size="24"
          class="ma-2"
          appendIcon="fas fa-chevron-right"
          @click="click"
        />
        <div class="ondaji_font_18_medium_brown_70">
          예제) &lt;Buttons-default btnType="text" :size="40" appendIcon="fas
          fa-chevron-right" @click="click"/&gt;
        </div>
      </v-col>
      <v-col cols="12">
        <Buttons-default
          btnType="request"
          :size="40"
          class="ma-2"
          :textAlign="'left'"
          appendIcon="fas fa-chevron-right"
          width="300px"
          btnText="설치신청"
          @click="click"
        />
        <Buttons-default
          btnType="request"
          :size="40"
          class="ma-2"
          appendIcon="fas fa-chevron-right"
          @click="click"
        />
        <Buttons-default
          btnType="request"
          :size="36"
          class="ma-2"
          appendIcon="fas fa-chevron-right"
          @click="click"
        />
        <Buttons-default
          btnType="request"
          :size="30"
          class="ma-2"
          appendIcon="fas fa-chevron-right"
          @click="click"
        />
        <Buttons-default
          btnType="request"
          :size="28"
          class="ma-2"
          appendIcon="fas fa-chevron-right"
          @click="click"
        />
        <Buttons-default
          btnType="request"
          :size="26"
          class="ma-2"
          appendIcon="fas fa-chevron-right"
          @click="click"
        />
        <Buttons-default
          btnType="request"
          :size="24"
          class="ma-2"
          appendIcon="fas fa-chevron-right"
          @click="click"
        />
        <div class="ondaji_font_18_medium_brown_70">
          예제) &lt;Buttons-default btnType="request" :size="40"
          :textAlign="'left'" appendIcon="fas fa-chevron-right" width="300px"
          btnText="설치신청" @click="click"/&gt;
        </div>
      </v-col>
      <v-col cols="12">
        <Buttons-default
          btnType="request"
          :size="40"
          class="ma-2"
          preAppendIcon="fas fa-chevron-left"
          btnText="설치신청"
          @click="click"
        />
        <Buttons-default
          btnType="request"
          :size="36"
          class="ma-2"
          preAppendIcon="fas fa-chevron-left"
          @click="click"
        />
        <Buttons-default
          btnType="request"
          :size="30"
          class="ma-2"
          preAppendIcon="fas fa-chevron-left"
          width="100px"
          @click="click"
        />
        <Buttons-default
          btnType="request"
          :size="28"
          class="ma-2"
          preAppendIcon="fas fa-chevron-left"
          @click="click"
        />
        <Buttons-default
          btnType="request"
          :size="26"
          class="ma-2"
          preAppendIcon="fas fa-chevron-left"
          @click="click"
        />
        <Buttons-default
          btnType="request"
          :size="24"
          class="ma-2"
          preAppendIcon="fas fa-chevron-left"
          btnText="설치신청"
          @click="click"
        />
        <div class="ondaji_font_18_medium_brown_70">
          예제) &lt;Buttons-default btnType="request" :size="40"
          preAppendIcon="fas fa-chevron-left" btnText="설치신청"
          @click="click"/&gt;
        </div>
      </v-col>
      <v-col cols="12" class="text-left">
        <Buttons-radio v-model="bRadio" trueText="실내" falseText="실외" />
        <div class="ondaji_font_18_medium_brown_70">
          예제) &lt;Buttons-radio v-model="bRadio" /&gt;
        </div>
      </v-col>
      <v-col cols="12" class="align-left">
        <v-container fluid>
          <v-row>
            <v-col cols="3" lg="1">
              <Slider-stepper v-model="stepper" @change="stepperChange" />
            </v-col>
            <v-col class="text-left" cols="9" lg="11">
              <div class="title pt-2">ui정의</div>
              &lt;Vuetify-stepper v-model="stepper"&gt;<br />

              <div class="title pt-2">v-model</div>
              stepper:{

              <div class="pl-5">
                total:4, <span class="pl-12" />/* step 총갯수 */
              </div>
              <div class="pl-5">
                value:1, <span class="pl-12" />/* 현재 step 값 */
              </div>
              <div class="pl-5">
                lineHeight:30,<span class="pl-3" /> /* 라인 높이 px */
              </div>
              <div class="pl-5">
                disabled:false, <span class="pl-3" />/* disable 여부 */
              </div>
              <div class="pl-2">}</div>
            </v-col>
          </v-row>
        </v-container>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  created() {},
  watch: {
    bRadio(val) {
      this.$alert(String(val))
    },
  },
  data() {
    return {
      bRadio: false,
      stepper: {
        total: 4,
        value: 1,
        lineHeight: 30,
        disabled: false,
      },
    }
  },
  methods: {
    click() {
      this.$alert("버튼클릭")
    },
    stepperChange(val) {
      this.$alert("stepper 변경", val)
    },
  },
}
</script>
