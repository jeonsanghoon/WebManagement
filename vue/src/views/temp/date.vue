<template>
  <v-container class="fluid align-start justify-start dateTempleate">
    <v-row>
      <v-col cols="12">
        <Vuetify-menutitle title="DatePicker" />
      </v-col>

      <v-col cols="12" class="text-left">
        <h3>참고 : <a href="https://vcalendar.io/">v-calendar</a></h3>
      </v-col>

      <v-col cols="6" style="height:300px;">
        <div class="title">1. datePicker</div>
        <date-picker v-model="date" mode="single" />
      </v-col>

      <v-col cols="6">
        <div class="title">2. date multiple Picker</div>
        <date-picker mode="multiple" v-model="dates" :width="'400px'" />
      </v-col>

      <v-col cols="6">
        <div class="title">3. datePicker Range</div>
        <date-picker mode="range" v-model="daterange2" />
      </v-col>
      <v-col cols="6">
        <div class="title">4. datePicker Range clear 추가(Custom)</div>
        <date-picker mode="rangeD" v-model="daterange" />
      </v-col>
      <v-col cols="12" class="text-left">
        <Vuetify-searchBar2 />

        <vc-date-picker v-model="date" />
      </v-col>
    </v-row>
  </v-container>
</template>
<style scoped>
.title {
  text-align: left;
}
</style>

<script>
export default {
  watch: {
    date(val) {
      console.log("1. datePicker>>", val)
    },
    dates(val) {
      console.log("2. date multiple Picker>>", val)
    },
    daterange2(val) {
      console.log("3. datePicker Range>>", val)
    },
    daterange(val) {
      console.log("4. datePicker Range clear 추가(Custom)>>", val)
    },
  },
  data() {
    return {
      date: new Date(2018, 1, 29),
      dates: [
        new Date(2018, 0, 1), // Jan 1st, 2018
        new Date(2018, 0, 15), // Jan 15th, 2018
        new Date(2018, 0, 29), // Jan 29th, 2018
      ],
      daterange: {
        start: new Date(2018, 1, 16), // Jan 16th, 2018
        end: new Date(2018, 1, 19), // Jan 19th, 2018
      },
      daterange2: {
        start: new Date(2020, 7, 16), // Jan 16th, 2018
        end: new Date(2020, 7, 19), // Jan 19th, 2018
      },
    }
  },
}
</script>
