<template>
  <v-container class="pa-0">
    <v-row class="mb-6" justify="center" no-gutters>
      <!-- 2020.04.16 작성자: 권용현 -->
      <!-- v-col 입력하면 버튼이 왼쪽으로 붙는 버그가 있음 -->
      <!-- 사이드바가 없을 때는 해당 현상 발생 안함 -->
      <!-- <v-col class="mb-6" cols="12"> -->
      <span>온닫이 이웃이 되어 주셔서 감사합니다!</span>
      <!-- </v-col> -->
    </v-row>

    <v-row justify="center">
      <v-col
        class="complete-background flex-shrink-1 px-6 px-sm-12 px-lg-12"
        cols="10"
        sm="auto"
        lg="auto"
      >
        <v-row
          class="pt-6 px-sm-6 px-lg-12"
          no-gutters
          justify="start"
          justify-sm="start"
          justify-lg="start"
        >
          <v-col
            class="text-start mr-sm-12 mr-lg-12"
            cols="12"
            sm="auto"
            lg="auto"
          >
            <span>이메일</span>
          </v-col>
          <v-col class="ml-sm-4 ml-lg-6" cols="auto">
            <span>ondaji</span>
          </v-col>
          <v-col class="mx-1" cols="auto">
            <span>@</span>
          </v-col>
          <v-col cols="auto">
            <span>ondaji.com</span>
          </v-col>
        </v-row>
        <v-row
          class="text-start py-6 px-sm-6 px-lg-12"
          no-gutters
          justify="start"
        >
          <v-col cols="12" sm="auto" lg="auto">
            <span>로컬사업자명</span>
          </v-col>
          <v-col class="text-start ml-sm-4 ml-lg-6" cols="auto">
            <span>이온닫</span>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<style lang="scss" scoped>
span {
  font-weight: bold;
  color: #6c5f5c;
}

.complete-background {
  border: solid 1px #aea6a4;
  background-color: #f5f5f5;
  border-radius: 0.3em;
}
</style>
