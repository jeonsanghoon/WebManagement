<template>
  <v-container class="grey lighten-5 pt-12">
    <v-row justify="center" no-gutters>
      <v-col sm="3">
        <Kakaomap-PostCode
          :name="this.name"
          @kakaoPostCodeCallback="kakaoPostCodeCallback"
          :dialog="dialog"
        ></Kakaomap-PostCode>
        <v-btn class="mb-5" @click="btnModalClick">영역지정</v-btn>

        <v-textarea
          label="결과값"
          auto-grow
          outlined
          rows="5"
          row-height="50"
          style="width:500px"
          shaped
          :value="rtnData"
        ></v-textarea>

        <div style="height:800px" class="ondajiStepper">
          <ul style="height:100%">
            <li>
              <div class="circle active"></div>
              <div class="line active"></div>
            </li>
            <li>
              <div class="circle active"></div>
              <div class="line active"></div>
            </li>
            <li>
              <div class="circle active"></div>
              <div class="line active"></div>
            </li>
          </ul>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>
<style lang="scss" scoped>
.ondajiStepper {
  ul {
    list-style-type: none;
    padding: 0px;
    height: 100%;
  }
  max-width: 36px;
  min-width: 36px;

  .circle {
    border-radius: 50%;
    cursor: pointer;
    min-width: 36px;
    min-height: 36px;
    width: 36px;
    height: 36px;
    font-size: 14px;
    font-weight: 700;
    display: table-cell;
    vertical-align: middle;
    &.active {
      background-color: $default-color !important;
      color: #ffffff;
    }
    &.next {
      border: 3px solid $default-color;
      color: $default-color;
    }
    &.unactive {
      border: 3px solid $disable-color;
      pointer-events: none;
    }
  }
  .line {
    width: 3px;
    min-height: 30px;
    height: 100%;
    margin-right: auto;
    margin-left: auto;
    &.active {
      background-color: $default-color !important;
    }
    &.unactive {
      background-color: $disable-color !important;
    }
  }
}
</style>
<script>
export default {
  data: () => {
    return {
      name: "kakaopost",
      dialog: false,
      rtnData: "",
    }
  },
  methods: {
    kakaoPostCodeCallback: function(res) {
      console.log("kakaoPostCodeCallback", res)
      this.rtnData = JSON.stringify(res)
      this.dialog = false
    },
    btnModalClick: function() {
      this.dialog = true
    },
  },
}
</script>
