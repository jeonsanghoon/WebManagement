/**
 * 다국어 메세지 처리
 */
export const msgResource = {
  BaseLanguage: "ko",
  /**
   * 메세지출력
   * @param {* 코드} code
   * @param {* 기본언어(ko)} lang
   */
  getMsg(code, lang) {
    lang = lang || this.BaseLanguage
    if (this[code] === undefined) return "코드없음"
    if (this[code][lang] === undefined) lang = this.BaseLanguage
    return this[code][lang]
  },
  /**
   * @param {* 대상 코드} baseCode
   * @param {* 변경 될 코드} repCode
   */
  formatString(baseCode, repCode) {
    return this.getMsg(baseCode).replaceAll("[0]", this.getMsg(repCode))
  },
  /**
   * @param {*대상코드} baseCode
   * @param  {...any 변경될 코드들} codes
   */
  formatStringArray(baseCode, ...codes) {
    let val1 = this.getMsg(baseCode)
    codes.forEach((code, index) => {
      val1 = val1.replaceAll(`[${index}]`, this.getMsg(code))
    })
    return val1
  },

  W001: {
    ko: "중복확인",
  },
  W002: {
    ko: "북마크",
  },
  W003: {
    ko: "개인정보",
  },
  W004: {
    ko: "비밀번호",
  },
  W005: {
    ko: "비즈니스 계정전환",
  },
  W006: {
    ko: "개인 계정전환",
  },
  W007: {
    ko: "회원 탈퇴",
  },
  W009: {
    ko: "동네정보",
  },
  W010: {
    ko: "로컬러",
  },
  W011: {
    ko: "나의 공간",
  },
  W012: {
    ko: "추가",
  },
  W013: {
    ko: "삭제",
  },
  W014: {
    ko: "기존 비밀번호",
  },
  W015: {
    ko: "새 비밀번호",
  },
  W016: {
    ko: "비밀번호 확인",
  },
  W017: {
    ko: "비고",
  },
  W018: {
    ko: "탈퇴사유",
  },
  W019: {
    ko: "개선사항",
  },
  W020: {
    ko: "안내사항",
  },
  W021: {
    ko: "로컬러님",
  },

  W022: {
    ko: "복수선택가능",
  },
  W023: {
    ko: "이용률 감소",
  },
  W024: {
    ko: "서비스 이용이 불편함",
  },
  W025: {
    ko: "광고 효과 불만족",
  },
  W026: {
    ko: "타서비스 이용",
  },
  W027: {
    ko: "변경하기",
  },
  W028: {
    ko: "사업자 등록번호(고유번호)",
  },
  L001: {
    ko: "중복확인을 해주세요.",
  },
  L002: {
    ko: "연락처를 등록하세요.",
  },
  L003: {
    ko: "주소검색을 하여 주소를 등록하세요.",
  },
  L004: {
    ko: "주소상세를 등록하세요.",
  },
  L005: {
    ko: "사업자등록번호가 유효하지 않습니다.(10자리)",
  },
  L006: {
    ko: "정상등록이 가능합니다.",
  },
  L007: {
    ko: "중복되는 데이터가 있습니다.",
  },
  L008: {
    ko: "8-12자 영문,숫자,특수문자만 가능",
  },
  L009: {
    ko:
      "첫 번째, 사용하고 계신 아이디는 탈퇴할 경우 3개월간 재사용이 불가능합니다.",
  },
  L010: {
    ko:
      "두 번째, 탈퇴 이후 등록한 게시물 등 이용 기록이 모두 삭제되어 복구할 수 없습니다.",
  },
  L011: {
    ko: "지금까지 온닫이와 함께 해주셔서 감사합니다.",
  },
  L012: {
    ko: "온닫이의 부족한 점이 있었다면 너그러운 양해를 부탁드립니다.",
  },
  L013: {
    ko: "아래 사항을 확인하시어 개선점을 남겨주세요.",
  },
  L014: {
    ko: "고객님의 의견을 적극 반영하여 더 좋은 서비스를 위해 노력하겠습니다.",
  },
  L015: {
    ko: "사업자번호(10자리)를 입력하세요",
  },
  L016: {
    ko: "검색중입니다... 잠시만 기다려주세요.",
  },
  B001: {
    ko: "[0]을 등록하세요.",
  },
  B002: {
    ko: "[0]를 등록하세요.",
  },
  B003: {
    ko: "정말로 [0] 하시겠습니까?",
  },
  B004: {
    ko: "[0]을 입력하세요.",
  },
  B005: {
    ko: "[0]를 입력하세요.",
  },
  B006: {
    ko: "[0]을 1개이상 선택하여 주세요",
  },
  B007: {
    ko: "[0]를 1개이상 선택하여 주세요",
  },
}
