<template>
  <v-container class="pa-0 ma-0">
    <v-expansion-panels flat>
        <template v-for="(data, index) in expansion">
      <v-expansion-panel :key="data.title">
        <v-row justify="start" no-gutters>
          <v-col cols="12">
            <v-expansion-panel-header class="pa-0">
              <span>{{data.title}}</span>
            </v-expansion-panel-header>
          </v-col>
        </v-row>
        
        <v-col class="content-border-bottom mt-0 pa-0" cols="12">
        <v-expansion-panel-content>
          <v-textarea
          class="mt-0 pa-0"
            no-resize
            loading="grey lighten-4"
            label
            rows="3"
            hide-details
            :value="data.content"
            single-line
            readonly
          ></v-textarea>
        </v-expansion-panel-content>
        </v-col>

      </v-expansion-panel>
      <v-row class="" no-gutters :key="index">
        <v-col class="d-flex justify-end align-center ml-2" cols="12">
          <span v-if="index != 2" class="accept-string mb-1 mr-2"
            >서비스 이용약관 동의(필수)</span
          >
        <span v-if="index === 2" class="accept-string mb-1 mr-2"
            >서비스 이용약관 동의(선택)</span
          >
          <v-checkbox v-model="selected" :value="index" color="#6c5f5c"></v-checkbox>
        </v-col>
      </v-row>
      </template>
    </v-expansion-panels>
    
    <v-row no-gutters>
        <v-col class="d-flex align-center justify-end  ml-2" cols="12">
          <span class="mb-1 mr-2"
            >전체동의</span
          >
          <v-checkbox v-model="allSelected" color="#6c5f5c" ></v-checkbox>
        </v-col>
      </v-row>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      MAX_INFO_CNT: 3,
      expansion: [],
      selected: [],
      allSelected: false,
    }
  },
  created() {
    let title = ''
    let content = ''
    for (let i = 0; i < this.MAX_INFO_CNT; i++) {
      switch (i) {
        case 0:
          title = '이용약관 동의'
          content =`1장 총칙 
제1조(목적) 이 지침은 「개인정보 보호법」(이하 "법"이라 한다) 제12조제1항에 따른 개인정보의 처리에 관한 기준, 개인정보 침해의 유형 및 예방조치 등에 관한 세부적인 사항을 규정함을 목적으로 한다.
제2조(용어의 정의) 이 지침에서 사용하는 용어의 뜻은 다음과 같다.
 1. "개인정보 처리"란 개인정보를 수집, 생성, 연계, 연동, 기록, 저장, 보유, 가공, 편집, 검색, 출력, 정정(訂正), 복구, 이용, 제공, 공개, 파기(破棄), 그 밖에 이와 유사한 행위를 말한다.
 2. "개인정보처리자"란 업무를 목적으로 법 제2조제4호에 따른 개인정보파일을 운용하기 위하여 개인정보를 처리하는 모든 공공기관, 영리목적의 사업자, 협회·동창회 등 비영리기관·단체, 개인 등을 말한다.
 3. "공공기관"이란 법 제2조제6호 및 「개인정보 보호법 시행령」(이하 "영"이라 한다) 제2조에 따른 기관을 말한다.
 4. "친목단체"란 학교, 지역, 기업, 인터넷 커뮤니티 등을 단위로 구성되는 것으로서 자원봉사, 취미, 정치, 종교 등 공통의 관심사나 목표를 가진 사람간의 친목도모를 위한 각종 동창회, 동호회, 향우회, 반상회 및 동아리 등의 모임을 말한다.`
          break
        case 1:
          title = '개인정보 수집 및 제3자 제공동의'
          content =
            'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'
          break
        case 2:
          title = '온닫이 정보수신 동의(선택)'
          content =
            'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.'
          break
      }
      this.initExpansion(title, content)
    }
  },
  watch: {
      allSelected(val) {
          this.selected = []

          if(val === true) {
              this.selected.push(0, 1, 2)
          }
      }
  },
  methods: {
    initExpansion(title, content) {
      let data = new Object()

      data.title = title
      data.content = content

      this.expansion.push(data)
    }
  }
}
</script>

<style lang="scss" scoped>
span {
  font-weight: bold;
  color: #6c5f5c;
}

.view-border {
  border: 2px solid black;
}

.content-border-bottom {
  border-bottom: 2px solid #F5F5F5;
  background-color: #F5F5F5;
}

.accept-string {
  font-size: 0.8em;
}

::v-deep {
  .v-expansion-panel-content__wrap {
    padding: 0px;
  }

#input-130{
  text-align: justify;
}

  .v-text-field__slot {
    padding-left: 0.6em;
  }
}
</style>
