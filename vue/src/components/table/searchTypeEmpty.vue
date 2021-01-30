<template>
  <v-container class="px-0 py-1">
    <v-row
      class="empty-background"
      align="center"
      justify="center"
      no-gutters
    >
      <v-col cols="12">
        <v-row no-gutters>
          <v-col cols="12">
            <v-img
              contain
              height="180"
              src="@/assets/images/banner_icons/empty.svg"
            >
            </v-img>
          </v-col>
        </v-row>
        <v-row class="pt-3 pb-2" no-gutters>
          <v-col cols="12">
            <span class="empty-title">제작된 배너가 없습니다.</span>
          </v-col>
        </v-row>
        <v-row no-gutters>
          <v-col cols="12">
            <span class="empty-sub-title"
              ><router-link to="/banner/reg">배너제작</router-link>에서 새로운 배너를 만들어보세요.</span
            >
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {}
</script>

<style lang="scss" scoped>
.empty-background {
  height: 24.313rem;
  background-color: #f2f2f2;
}

.empty-title {
  font-weight: bold;
  font-size: 1.125rem;
  color: #757575;
}

.empty-sub-title {
  font-weight: bold;
  font-size: 0.75rem;
  color: #AEAEAE;
}

.v-application a {
    color: #AEAEAE;
}
</style>
