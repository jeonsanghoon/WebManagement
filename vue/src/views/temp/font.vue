<template>
  <v-container class="fluid align-start justify-start fontTable">
    <v-row>
      <v-col cols="12">
        <Vuetify-menutitle title="폰트정의" />
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12"  lg="6" class="cssData" v-for="(sizeData, sizeIdx) in font.size" :key="sizeIdx">
        <div >
          <div v-for="(weightData, weightIdx) in font.weight" :key="weightIdx">
            <div v-for="(colorData, colorIdx) in font.colors" :key="colorIdx">
              <v-card
                v-if="
                  (colorData.key.indexOf('white') < 0 &&
                    colorData.key.indexOf('grey') < 0 &&
                    colorData.key.indexOf('green1') < 0 &&
                    colorData.key.indexOf('amber1') < 0 &&
                    colorData.key.indexOf('amber1') < 0 &&
                    colorData.key.indexOf('blue2') < 0) ||
                    (colorData.key.indexOf('grey') >= 0 )
                "
                :class="
                  'ondaji_font_' +
                    sizeData.key +
                    '_' +
                    weightData.key +
                    '_' +
                    colorData.key
                "
              >
                {{
                  "ondaji_font_" +
                    sizeData.key +
                    "_" +
                    weightData.key +
                    "_" +
                    colorData.key
                }}
                온닫이 폰트
              </v-card>
              <v-card
                v-else
                :class="
                  'grey darken-3 ondaji_font_' +
                    sizeData.key +
                    '_' +
                    weightData.key +
                    '_' +
                    colorData.key
                "
              >
                {{
                  "ondaji_font_" +
                    sizeData.key +
                    "_" +
                    weightData.key +
                    "_" +
                    colorData.key
                }}
                온닫이 폰트
              </v-card>
            </div>
          </div>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<style lang="scss">
.cssData .v-card {
  margin-bottom: 0.875rem;
  padding: 1rem;
  text-align: left;
}
</style>

<script>
export default {
  mounted() {
    /*this.$alert("다른 로컬 사업자로부터 배너 공유 요청이 수신되었습니다.<br/>배너리스트를 확인해 주세요.",{badgeText:"New"})
        .then((res) => {
          console.log(res)
        })*/
  },
  data() {
    return {
      font: {
        size: [
          { key: 30, val: "1.875rem" },
          { key: 24, val: "1.5rem" },
          { key: 20, val: "1.25rem" },
          { key: 18, val: "1.125rem" },
          { key: 16, val: "1rem" },
          { key: 14, val: "0.875rem" },
          { key: 13, val: "0.813rem" },
          { key: 12, val: "0.75rem" },
 
        ],
        weight: [
          { key: "lthin", val: 300 },
          { key: "thin", val: 400 },
          { key: "medium", val: 500 },
          { key: "bold", val: 600 },
        ],
        colors: [
{ key:"grey_100", val:"#000000"},
{ key:"grey_90", val:"#242424"},
{ key:"grey_80", val:" #424242"},
{ key:"grey_70", val:"#616161"},
{ key:"grey_60", val:"#757575"},
{ key:"grey_50", val:"#AAAAAA"},
{ key:"grey_40", val:"#BDBDBD"},
{ key:"grey_30", val:"#C4C4C4"},
{ key:"grey_20", val:"#E0E0E0"},
{ key:"grey_10", val:"#F2F2F2"},
{ key:"grey_5", val:"#FAFAFA"},
{ key:"white", val:"#ffffff"},
{ key:"brown_100", val:"#231815"},
{ key:"brown_90", val:"#403532"},
{ key:"brown_80", val:"#3E2723"},
{ key:"brown_70", val:"#938783"},
{ key:"brown_60", val:"#C0AFAA"},
{ key:"brown_50", val:"#C6B8B4"},
{ key:"brown_40", val:"#C2B7B3"},
{ key:"brown_30", val:"#D1C8C6"},
{ key:"brown_20", val:"#E2D9D2"},
{ key:"brown_10", val:"#F5EEE8"},
{ key:"tealgreen_100", val:"#005D3B"},
{ key:"tealgreen_90", val:"#007A56"},
{ key:"tealgreen_80", val:"#028A64"},
{ key:"tealgreen_70", val:"#069B73"},
{ key:"tealgreen_60", val:"#0BA880"},
{ key:"tealgreen_50", val:"#24B791"},
{ key:"tealgreen_40", val:"#50C5A4"},
{ key:"tealgreen_30", val:"#84D6BE"},
{ key:"tealgreen_20", val:" #B4E6D7"},
{ key:"tealgreen_10", val:" #E1F5F0"},
{ key:"green_100", val:" #2E7D32"},
{ key:"green_40", val:" #00C853"},
{ key:"green_10", val:" #E0F8E2"},
{ key:"blue_90", val:" #1565C0"},
{ key:"blue_70", val:" #2979FF"},
{ key:"blue_30", val:" #64B5F6"},
{ key:"blue_20", val:" #BBDEFB"},
{ key:"yellow_80", val:" #F57F17"},
{ key:"yellow_10", val:" #FFECB3"},
{ key:"red_60", val:" #E53935"},
        ],
      },
    }
  },
}
</script>
