<template>
  <v-container class="pa-0 pa-sm-0 pa-lg-10">
    <v-stepper class="stepper pa-0 pa-sm-2 pa-lg-5" alt-labels v-model="page">
      <v-row no-gutters justify="center">
        <v-col cols="12">
          <v-stepper-header class="mb-8">
            <template v-for="(data, index) in stepper">
              <v-stepper-step
                :complete="page > data.page"
                step=""
                :key="data.name"
              >
                <p 
                :style="{
                    'font-weight': selectPage(index, 'w'),
                    color: selectPage(index, 'c'),
                  }"
                >
                  {{ data.title }}
                </p>
              </v-stepper-step>

              <v-divider
                class="head-divider"
                v-if="data.page !== maxPage"
                :key="data.name"
              >
              </v-divider>
            </template>
          </v-stepper-header>
        </v-col>
      </v-row>
      <v-stepper-items>
        <template v-for="data in stepper">
          <v-stepper-content class="pa-0" :step="data.page" :key="data.name">
            <v-card flat>
              <div v-if="data.page === 1">
              <inputInfo></inputInfo>
              </div>   
              <div v-else-if="data.page === 2">
              <acceptTerms></acceptTerms>
              </div>
              <div v-else-if="data.page === 3">
              <regComplete></regComplete>
              </div>      
            </v-card>

            <v-row no-gutters justify="center">
              <v-col v-if="data.page != 3" class="mt-6" cols="6" sm="12" lg="12">
                <div align="center">
                <v-btn
                  id="hover-button"
                  class="white--text mb-2 mx-sm-2 mx-lg-4"
                  rounded
                  width="10em"
                  large
                  text
                  @click="buttonPage(data.page - 1)"
                >
                  뒤로가기
                </v-btn>
                <v-btn
                  id="hover-button"
                  class="white--text mb-2 mx-sm-2 mx-lg-4"
                  rounded
                  width="10em"
                  large
                  text
                  @click="buttonPage(data.page + 1)"
                >
                  확인
                </v-btn>
                </div>
              </v-col>
              <v-col v-else-if="data.page === 3" class="mt-6" cols="12">
                <v-btn
                  id="hover-button"
                  class="white--text"
                  rounded
                  width="15em"
                  large
                  text
                  @click="buttonPage(data.page - 1)"
                >
                  로그인하기
                </v-btn>
              </v-col>
            </v-row>
          </v-stepper-content>
        </template>
      </v-stepper-items>
    </v-stepper>
  </v-container>
</template>

<script>
import inputInfo from './inputInfo'
import acceptTerms from './acceptTerms'
import regComplete from './regComplete'

export default {
  components: {
    inputInfo,
    acceptTerms,
    regComplete,
  },
  data() {
    return {
      page: 1,
      maxPage: 3,
      stepper: [],
    }
  },
  created() {
    for (let i = 0; i < this.maxPage; i++) {
      let info = new Object()

      info.page = i + 1

      switch (i) {
        case 0:
          info.title = '정보 입력'
          break
        case 1:
          info.title = '약관 동의'
          break
        case 2:
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
      this.page = page
    },
  },
}
</script>

<style lang="scss" scoped>
$nomal-btn-color: #aea6a4;
$hover-btn-color: #6c5f5c;

p {
  margin: 0px;
}

.stepper {
  box-shadow: none;
}

.v-stepper__header {
  box-shadow: none;
}

#head-divider {
  border: $nomal-btn-color solid 0.1rem;
}

#hover-button {
  background: $nomal-btn-color;
}

#hover-button:hover {
  background: $hover-btn-color;
}
</style>
