<template>
  <v-container class="pa-0 ma-0">
    <v-row class="mb-3" align="center" justify="start" no-gutters>
      <v-col class="d-flex justify-start" cols="12" sm="4" lg="3">
        <span>업체명(로컬사업자)</span>
      </v-col>
      <v-col class="mb-2 mb-sm-0 mb-lg-0" cols="12" sm="4" lg="2">
        <v-text-field
          placeholder="상호명"
          hide-details
          filled
          rounded
          dense
        >
        </v-text-field>
      </v-col>
      <v-col class="mb-2 mt-sm-2 mt-lg-0 mb-sm-0 mb-lg-0 ml-lg-2" cols="12" sm="4" lg="2" offset-sm="4" offset-lg="0">
        <v-text-field  
          placeholder="지점"
          hide-details
          filled
          rounded
          dense
        >
        </v-text-field>
      </v-col>
      <v-col
        class="d-flex justify-end justify-sm-start justify-lg-start ml-sm-3 ml-lg-3"
        cols="12"
        sm="2"
        lg="2"
      >
        <v-btn class="hover-button" rounded text>
          중복확인
        </v-btn>
      </v-col>
    </v-row>

    <v-row class="mb-3" align="center" justify="start" no-gutters>
      <v-col class="d-flex justify-start" cols="12" sm="4" lg="3">
        <span>업종</span>
      </v-col>
      <v-col class="mb-2 mb-sm-0 mb-lg-0" cols="12" sm="4" lg="3">
        <v-combobox  
          placeholder="업종"
          v-model="test1"
          :items="testItems1"
          hide-details
          filled
          rounded
          dense
        >
        </v-combobox>
      </v-col>

      <v-col v-if="test1 != ''" class="mb-2 mt-sm-2 mb-sm-0 mt-lg-0 mb-lg-0 ml-lg-2" cols="12" sm="4" lg="3" offset-sm="4" offset-lg="0">
        <v-combobox
          placeholder="중분류"
          v-model="test2"
          :items="testItems2"
          hide-details
          filled
          rounded
          dense
        >
        </v-combobox>
      </v-col>

      <v-col v-if="test2 != ''" class="mb-2 mt-sm-2 mb-sm-0 mt-lg-0 mb-lg-0 ml-lg-2" cols="12" sm="4" lg="3" offset-sm="4" offset-lg="0">
        <v-combobox
          placeholder="소분류"
          v-model="test3"
          :items="testItems3"
          hide-details
          filled
          rounded
          dense
        >
        </v-combobox>
      </v-col>
    </v-row>

    <v-row class="mb-3" align="center" justify="start" no-gutters>
      <v-col class="d-flex justify-start" cols="12" sm="4" lg="3">
        <span>사업자등록번호(고유번호)</span>
      </v-col>
      <v-col class="mb-2 mb-sm-0 mb-lg-0" cols="12" sm="5" lg="4">
        <v-text-field
          placeholder="000-000-000"
          hide-details
          filled
          rounded
          dense
        >
        </v-text-field>
      </v-col>
      <v-col
        class="d-flex justify-end justify-sm-start justify-lg-start ml-sm-3 ml-lg-3"
        cols="12"
        sm="2"
        lg="2"
      >
        <v-btn class="hover-button" rounded text>
          중복확인
        </v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      test1: '',
      testItems1: ['서비스업', '공업', 'IT업'],
      test2: '',
      testItems2: ['호텔', '반도체', '웹서비스'],
      test3: '',
      testItems3: ['사람', '동물', '우주']
    }
  }
}
</script>

<style lang="scss" scoped>
span {
  font-weight: bold;
  color: #6c5f5c;
}

.hover-button{
  color: white;
  background: #aea6a4;
}
.hover-button:hover {
  background: #6c5f5c;
}

::v-deep {
.v-input__icon {
  padding-bottom: 0.5em;
}
}
</style>
