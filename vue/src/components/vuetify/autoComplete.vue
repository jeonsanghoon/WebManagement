<template>
  <v-container class="pa-0" fluid>
    <v-autocomplete
      ref="autocomplete"
      placeholder="한글,영문,숫자만 입력하세요."
      :search-input.sync="keyword"
      v-model="selectKeywords"
      :items="result"
      :loading="loading"
      :rounded="info.rounded"
      dense
      no-filter
      auto-select-first
      hide-no-data
      hide-selected
      chips
      hide-details
      multiple
      background-color="white"
      color="grey lighten-1"
      maxlength="100"
    >
      <template v-slot:selection="data">
        <v-chip
          :class="chipClass"
          v-bind="data.attrs"
          :input-value="data.selected"
          close
          @click="data.select"
          @click:close="closeChip(data.item)"
          close-icon="mdi-close"
          :style="chipStyle"
        >
          <span
            class="text-truncate"
            ref="chipItem"
            style="max-width: 5.0rem;"
            >{{ data.item }}</span
          >
          <span class="chip-divider ml-2">|</span>
        </v-chip>
      </template>
      <template v-slot:item="data">
        <v-list-item-content v-text="data.item"></v-list-item-content>
      </template>
    </v-autocomplete>
    <div
      v-if="info.type != TYPE.MEDIA_SHOP"
      class="autocomplete-rule text-right"
    >
      5개 이하
    </div>
  </v-container>
</template>

<script>
export default {
  props: {
    info: {
      type: Object,
      required: true,
      default: null,
    },
  },
  data() {
    return {
      // 초성(19자) ㄱ ㄲ ㄴ ㄷ ㄸ ㄹ ㅁ ㅂ ㅃ ㅅ ㅆ ㅇ ㅈ ㅉ ㅊ ㅋ ㅌ ㅍ ㅎ
      initialSound: [
        0x3131,
        0x3132,
        0x3134,
        0x3137,
        0x3138,
        0x3139,
        0x3141,
        0x3142,
        0x3143,
        0x3145,
        0x3146,
        0x3147,
        0x3148,
        0x3149,
        0x314a,
        0x314b,
        0x314c,
        0x314d,
        0x314e,
      ],
      // 중성(21자) ㅏ ㅐ ㅑ ㅒ ㅓ ㅔ ㅕ ㅖ ㅗ ㅘ ㅙ ㅚ ㅛ ㅜ ㅝ ㅞ ㅟ ㅠ ㅡ ㅢ ㅣ
      middleSound: [
        0x314f,
        0x3150,
        0x3151,
        0x3152,
        0x3153,
        0x3154,
        0x3155,
        0x3156,
        0x3157,
        0x3158,
        0x3159,
        0x315a,
        0x315b,
        0x315c,
        0x315d,
        0x315e,
        0x315f,
        0x3160,
        0x3161,
        0x3162,
        0x3163,
      ],
      // 종성(28자) <없음> ㄱ ㄲ ㄳ ㄴ ㄵ ㄶ ㄷ ㄹ ㄺ ㄻ ㄼ ㄽ ㄾ ㄿ ㅀ ㅁ ㅂ ㅄ ㅅ ㅆ ㅇ ㅈ ㅊ ㅋ ㅌ ㅍ ㅎ
      lastSound: [
        0x0000,
        0x3131,
        0x3132,
        0x3133,
        0x3134,
        0x3135,
        0x3136,
        0x3137,
        0x3139,
        0x313a,
        0x313b,
        0x313c,
        0x313d,
        0x313e,
        0x313f,
        0x3140,
        0x3141,
        0x3142,
        0x3144,
        0x3145,
        0x3146,
        0x3147,
        0x3148,
        0x314a,
        0x314b,
        0x314c,
        0x314d,
        0x314e,
      ],

      // **********************
      // 형태소 관련
      // **********************
      // chars: 검색어로 들어오는 문자
      // Morpheme: 형태소 오브젝트
      // **********************
      chars: [],
      Morpheme() {
        this.initialInfo = {
          initial: "",
          initialCheck: false,
        }
        this.middleInfo = {
          middle: "",
          middleCheck: false,
        }
        this.lastInfo = {
          last: "",
          lastCheck: false,
        }

        this.elements = ""
      },

      // **********************
      // 검색 관련
      // **********************
      // keyword: 현재 검색창에 입력된 값
      // result: 검색 반환 값
      // loading: vue 로딩바 (기본 false)
      // **********************
      keyword: "",
      result: [],
      loading: false,

      // **********************
      // 선택 키워드 관련
      // **********************
      // selectKeywords: 화면에 보여주기 위한 선택 키워드 배열
      // **********************
      selectKeywords: [],

      chipStyle: `font-weight: bold; font-size: 0.9rem; color: #725D58; background-color: #DFD8D6;`,
      chipClass: ``,

      TYPE: {
        MEDIA_SHOP: 1,
      },
    }
  },
  created() {
    if (this.info.keywords != undefined) {
      this.selectKeywords = this.info.keywords
    }

    if (this.info.type != this.TYPE.MEDIA_SHOP) {
      this.chipClass = `pr-3 my-1 ml-n4 mr-5`
    } else {
      this.chipClass = `pr-3 mr-2`
    }
  },
  watch: {
    keyword() {
      // readOnly 를 하면 콤보박스가 작동을 안함
      if (
        this.checkNull(this.keyword) === true ||
        this.selectKeywords.length > 4
      ) {
        return
      }

      if (/^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9]+$/.test(this.keyword) === false) {
        return
      }

      this.checkLoading()
    },
    selectKeywords() {
      this.keyword = ``
      this.result = []
      this.selectKeywords.forEach((e) => {
        this.result.push(e)
      })
    },
  },
  methods: {
    checkLoading() {
      if (this._timerId) {
        clearTimeout(this._timerId)
      }

      this._timerId = setTimeout(() => {
        if (/^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9]+$/.test(this.keyword) === true) {
          const keyword = this.keyword
          console.log(keyword, 1)
          if (keyword != null) {
            this.loadKeyword(this.insertKeyword(keyword))
          }
        }
      }, 200)
    },
    insertKeyword(keyword) {
      let chars = []
      let morpheme = new this.Morpheme()

      for (let i = 0; i < keyword.length; i = i + 1) {
        // 해당 문자를 코드로 변환
        chars[i] = keyword.charCodeAt(i)
        // "AC00:가" ~ "D7A3:힣" 에 속한 글자면 분해
        if (chars[i] >= 0xac00 && chars[i] <= 0xd7a3) {
          this.setMorpheme(i, chars, morpheme)
        } else {
          // 초,중성 둘 중 한가지 조건값만 서버에 전달
          // 초,중성을 동시에 활용하여 검색하는 경우가 없음
          if (chars[i] >= 12593 && chars[i] <= 12622) {
            this.setInitial(i, chars, morpheme)
          } else if (this.chars[i] >= 12623 && chars[i] <= 12643) {
            this.setMiddle(i, chars, morpheme)
          } else {
            // 한글이 아닌 경우
            if (chars[i] != 32) {
              morpheme.elements += String.fromCharCode(chars[i])
            }
          }
        }
      }
      return morpheme
    },
    getKeywords() {
      let keywordArr = []

      this.selectKeywords.forEach((keyword) => {
        const morpheme = this.insertKeyword(keyword)

        keywordArr.push(this.getRegFormData(keyword, morpheme))
      })

      return keywordArr
    },
    getRegFormData(keyword, morpheme) {
      let formData = {}

      formData.keywordName = keyword

      formData.chosung = morpheme.initialInfo.initial
      formData.jungsung = morpheme.middleInfo.middle
      formData.jongsung = morpheme.lastInfo.last

      // 등록에 필요 없는 like % 관련된 문자열 전부 제거
      formData.keywordUnits = morpheme.elements.replace(/%/g, "")

      formData.keywordType = this.info.type

      // 테스트 코드
      formData.insertCode = 46

      return formData
    },
    setMorpheme(index, chars, morpheme) {
      // 해당 문자가 몇 번째 한글 인지 계산
      const charOrder = chars[index] - 0xac00

      const initialPartial = charOrder / (21 * 28)
      const remainPartial = charOrder % (21 * 28)
      const middlePartial = remainPartial / 28
      const lastPartial = remainPartial % 28

      let el = String.fromCharCode(
        this.initialSound[Math.floor(initialPartial)]
      )
      morpheme.initialInfo.initial += el
      morpheme.elements += el

      // 복모음 분리
      el = this.middlePartialDivide(middlePartial)
      morpheme.middleInfo.middle += el
      morpheme.elements += el

      // 종성이 0이 아니면, 즉 받침이 있으면
      if (lastPartial !== 0x0000) {
        // 복자음 분리
        el = this.lastPartialDivide(lastPartial)

        morpheme.lastInfo.last += el
        morpheme.elements += el
      }

      // DB like 검색을 위해 % 붙임
      morpheme.elements += "%"
    },
    setInitial(index, chars, morpheme) {
      // 초성
      const initialStr = String.fromCharCode(chars[index])

      // 서버에 초성 데이터 전달을 위해 Check 값 변경
      morpheme.initialInfo.initial += initialStr
      morpheme.initialInfo.initialCheck = true

      morpheme.elements += `%${initialStr}%`
    },
    setMiddle(index, chars, morpheme) {
      // 중성
      const middlePartial = this.middlePartialDivide(chars[index] % 12623)

      // 서버에 중성 데이터 전달을 위해 Check 값 변경
      morpheme.middleInfo.middle += middlePartial
      morpheme.middleInfo.middleCheck = true

      morpheme.elements += `%${middlePartial}%`
    },
    loadKeyword(morpheme) {
      const formData = this.getSearchFormData(morpheme)

      this.loading = true
      this.$BaseCommon.restful
        .call2("/temp/getkeyword", formData)
        .then((res) => {
          this.loadComplete(res)
        })
    },
    getSearchFormData(morpheme) {
      let formData = {}

      if (
        morpheme.initialInfo.initialCheck === false &&
        morpheme.middleInfo.middleCheck === false
      ) {
        // 완성된 단어면 연결된 단어인지 확인을 위해 초,중성 전달
        formData.chosung = morpheme.initialInfo.initial
        formData.jungsung = morpheme.middleInfo.middle
      } else if (
        morpheme.initialInfo.initialCheck === true &&
        morpheme.middleInfo.middleCheck === false
      ) {
        formData.chosung = morpheme.initialInfo.initial
      } else if (
        morpheme.initialInfo.initialCheck === false &&
        morpheme.middleInfo.middleCheck === true
      ) {
        formData.jungsung = morpheme.middleInfo.middle
      }

      formData.keywordUnits = morpheme.elements

      return formData
    },
    loadComplete(res) {
      this.result = []

      let check = false
      // 콜백으로 들어오기 때문에 keyword 값이 없을 수 있다.
      // keyword 값이 없으면 result 값을 비워둔다.
      if (!this.checkNull(this.keyword)) {
        res.forEach((data) => {
          // 검색 키워드 유무 확인
          if (data.keywordName === this.keyword) {
            check = true
          }
          this.result.push(data.keywordName)
        })
        // 검색 키워드가 없으면
        // 검색창에 있는 값을 결과창에 추가
        if (check === false) {
          this.result.unshift(this.keyword)
        }
      }

      this.selectKeywords.forEach((e) => {
        this.result.push(e)
      })

      this.loading = false
    },

    closeChip(key) {
      this.selectKeywords.splice(this.selectKeywords.indexOf(key), 1)
    },
    checkNull(val) {
      if (val === null || val === "" || val === undefined) {
        return true
      } else if (val.replace(/^\s+|\s+$/g, "") == "") {
        return true
      }
      return false
    },
    middlePartialDivide(middlePartial) {
      let el = ""

      switch (Math.floor(middlePartial)) {
        case 9:
          el = "ㅗㅏ"
          break
        case 10:
          el = "ㅗㅐ"
          break
        case 11:
          el = "ㅗㅣ"
          break
        case 14:
          el = "ㅜㅓ"
          break
        case 15:
          el = "ㅜㅔ"
          break
        case 16:
          el = "ㅜㅣ"
          break
        case 19:
          el = "ㅡㅣ"
          break
        default:
          // 복모음이 아닐 경우 추가
          el = String.fromCharCode(this.middleSound[Math.floor(middlePartial)])
      }
      return el
    },
    lastPartialDivide(lastPartial) {
      let el = ""

      switch (Math.floor(lastPartial)) {
        case 3:
          el = "ㄱㅅ"
          break
        case 5:
          el = "ㄴㅈ"
          break
        case 6:
          el = "ㄴㅎ"
          break
        case 9:
          el = "ㄹㄱ"
          break
        case 10:
          el = "ㄹㅁ"
          break
        case 11:
          el = "ㄹㅂ"
          break
        case 12:
          el = "ㄹㅅ"
          break
        case 13:
          el = "ㄹㅌ"
          break
        case 14:
          el = "ㄹㅍ"
          break
        case 15:
          el = "ㄹㅎ"
          break
        case 18:
          el = "ㅂㅅ"
          break
        default:
          // 복자음이 아닐 경우 추가
          el = String.fromCharCode(this.lastSound[Math.floor(lastPartial)])
      }
      return el
    },
  },
}
</script>

<style lang="scss" scoped>
.chip-divider {
  margin-bottom: 0.1rem;
}

.autocomplete-rule {
  font-size: 0.75rem;
  padding-top: 0.6rem;
  padding-bottom: 0.2rem;
  padding-right: 1rem;
}

::v-deep {
  .v-chip {
    border: 2px solid #c6b8b4;
  }

  input::placeholder {
    font-weight: 400;
    font-size: 0.85rem;
    color: #aaa19f !important;
  }

  .v-input__slot {
    border: 1px solid #bdbdbd !important;
  }

  .v-text-field.v-input--dense:not(.v-text-field--outlined) input {
    padding: 12px 0 12px 0 !important;
  }

  .v-input__append-inner {
    padding-top: 3px;
  }
}
</style>
