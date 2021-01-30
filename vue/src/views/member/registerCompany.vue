<template>
  <v-container>
    <v-row justify="center" no-gutters>
      <v-col cols="12" lg="11" xl="8">
        <v-stepper alt-labels v-model="page">
          <v-row justify="center" no-gutters>
            <v-col class="pb-6" cols="12" lg="10">
              <v-stepper-header>
                <template v-for="(data, index) in stepper">
                  <v-stepper-step
                    :complete="page > data.page"
                    step=""
                    :key="data.name"
                  >
                    <p
                      :style="{
                        'font-weight': selectPage(index, 'w'),
                        color: selectPage(index, 'c')
                      }"
                    >
                      {{ data.title }}
                    </p>
                  </v-stepper-step>

                  <v-divider v-if="data.page !== MAX_PAGE" :key="data.name">
                  </v-divider>
                </template>
              </v-stepper-header>
            </v-col>
          </v-row>

          <v-stepper-items>
            <v-row justify="center" align="center" no-gutters>
              <v-col class="pa-0" cols="12" lg="10">
                <template v-for="data in stepper">
                  <v-stepper-content
                    class="py-10 px-0"
                    :step="data.page"
                    :key="data.name"
                  >
                    <div v-if="data.page === 1">
                      <inputInfo></inputInfo>
                    </div>
                    <div v-else-if="data.page === 2">
                      <inputCompanyInfo></inputCompanyInfo>
                    </div>
                    <div v-else-if="data.page === 3">
                      <acceptTerms></acceptTerms>
                    </div>
                    <div v-else-if="data.page === 4">
                      <regComplete></regComplete>
                    </div>
                  </v-stepper-content>
                </template>
              </v-col>
            </v-row>
          </v-stepper-items>
        </v-stepper>
          
        <v-row class="pt-6" justify="center" no-gutters>
          <!-- 2020.04.16 작성자: 권용현 -->
          <!-- v-col 입력하면 버튼이 왼쪽으로 붙는 버그가 있음 -->
          <!-- 사이드바가 없을 때는 해당 현상 발생 안함 -->
          <!-- <v-col class="view-border pt-6" cols="12"> -->
            <v-btn
              class="hover-button mb-2 mx-4 mx-sm-4 mx-lg-8"
              rounded
              width="12em"
              large
              text
              @click="buttonPage(-1)"
            >
              뒤로가기
            </v-btn>
            <v-btn
              class="hover-button mb-2 mx-4 mx-sm-4 mx-lg-8"
              rounded
              width="12em"
              large
              text
              @click="buttonPage(+1)"
            >
              확인
            </v-btn>
          <!-- </v-col> -->
        </v-row>

      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import inputInfo from './inputInfo'
import inputCompanyInfo from './inputCompanyInfo'
import acceptTerms from './acceptTerms'
import regComplete from './regComplete'

export default {
  components: {
    inputInfo,
    inputCompanyInfo,
    acceptTerms,
    regComplete
  },
  data() {
    return {
      page: 1,
      MAX_PAGE: 4,
      stepper: []
    }
  },
  created() {
    for (let i = 0; i < this.MAX_PAGE; i++) {
      let info = new Object()

      info.page = i + 1

      switch (i) {
        case 0:
          info.title = '정보 입력'
          break
        case 1:
          info.title = '업체 정보'
          break
        case 2:
          info.title = '약관 동의'
          break
        case 3:
          info.title = '가입 완료'
          break
      }

      this.stepper.push(info)
    }
  },
  computed: {},
  methods: {
    selectPage(page, type) {
      page = page + 1

      if (this.page === page) {
        if (type === 'w') {
          return 'bold'
        } else if (type === 'c') {
          return '#231815'
        }
      } else {
        if (type === 'w') {
          return 'normal'
        } else if (type === 'c') {
          return '#AEA09E'
        }
      }
    },
    buttonPage(page) {
      this.page += page

      if (this.page <= 1) {
        this.page = 1
      } else if (this.page >= this.MAX_PAGE) {
        this.page = this.MAX_PAGE
      }
    }
  }
}
</script>

<style lang="scss" scoped>
span {
  font-weight: bold;
  color: #6c5f5c;
}

.v-stepper,
.v-stepper__header {
  box-shadow: none;
}

.v-divider {
  border: 2px solid black;
}

.view-border {
  border: 1px solid black;
}

.hover-button {
  color: white;
  background: #aea6a4;
}

.hover-button:hover {
  background: #6c5f5c;
}
</style>
