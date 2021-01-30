<template>
  <v-app id="app" ref="usersidebar" class="ondaji Local" :dark="dark">
    <v-dialog v-model="loading" fullscreen>
      <v-container
        fluid
        fill-height
        style="background-color: rgba(255, 255, 255, 0.5);"
      >
        <v-layout justify-center align-center>
          <v-progress-circular indeterminate color="primary">
          </v-progress-circular>
        </v-layout>
      </v-container>
    </v-dialog>
    <mainmenu v-if="isShowBar" />
    <v-content fluid class="usercontent" align="center" justify="center">
      <v-container
        fluid
        class="align-center justify-center fill-height pa-0"
        v-if="align === 'center'"
      >
        <v-row
          align="center"
          justify="center"
          class="fill-height ma-0"
          style="width:100%"
        >
          <v-col class="text-center overflow-y-auto fill-height pa-0">
            <router-view :key="$route.fullPath" class="routerview" />
          </v-col>
        </v-row>
      </v-container>
      <v-container fluid v-else class="usercontent pa-0 fill-height">
        <v-row
          align="start"
          justify="start"
          class="fill-height ma-0"
          style="width:100%"
        >
          <v-col class="overflow-y-auto fill-height  pa-0">
            <router-view
              :key="$route.fullPath"
              class="routerview align-start justify-start fill-height"
            />
          </v-col>
        </v-row>
      </v-container>
    </v-content>
    <v-footer color="lightgrey" v-if="isShowBar">
      <span class="font-weight-bold">&copy; ONDAJI Crop.</span>
    </v-footer>

    <v-snackbar v-model="snackbar" :timeout="timeout" :color="color">
      {{ text }}
      <v-btn text @click="snackbar = false">
        Close
      </v-btn>
    </v-snackbar>
  </v-app>
</template>
<style scoped>
#app {
  background-color: #fafafa;
}
.routerview {
  max-width: 100%;
  margin-left: 0px;
  margin-right: 0px;
  top: 200px;
  background-color: #fafafa;
}
.usercontent {
  padding-top: 0px !important;
}

@media only screen and (max-width: 767px) {
  .usercontent {
    padding-left: 0px !important;
    padding-right: 0px !important;
  }
}
</style>

<script>
import mainmenu from "@/components/layouts/mainmenuUser"

import { editCheckMixin } from "@/assets/scripts/mixin/editCheckMixin.js"
export default {
  mixins: [editCheckMixin],
  components: {
    mainmenu,
  },
  data: () => ({
    dark: false,
    loading: false,
    isShowBar: true,
    snackbar: false,
    timeout: 6000,
    color: "error",
    text: "",
    isFormChange: false,
    isConfirm: false,
    isShowConfirm: false,
    confirmInfo: {
      title: "",
      message: "",
    },
    align: "start",
  }),
  created() {
    /* 리플레시전 확인 메세지 */
    this.$EventBus.$on("isShowBar", (isShowBar) => {
      this.isShowBar = isShowBar
    })

    this.$EventBus.$on("routerViewAlign", (align) => {
      this.align = align
      if (this.align === "center") {
        this.$refs.usersidebar.body.style.backgroundColor = "#E2D9D2"
      }

      if (this.align === "center") {
        document
          .querySelector(".usercontent")
          .classList.add("ondaji_color_brown4")
      } else {
        document
          .querySelector(".usercontent")
          .classList.add("ondaji_color_grey4")
      }
      console.log("approuterViewAlign >")
    })
    this.$EventBus.$on("messageShow", (text, color) => {
      this.snackbar = true
      this.text = text

      if (!(color === undefined || color === null)) {
        this.color = color
      }
    })
    let self = this
    this.$EventBus.$on("confirmMessage", (text, next) => {
      if (this.$Config.isFormChangeCheck) {
        self
          .$alert(this.$Config.msg.checkForm, {
            alertType: "confirm",
          })
          .then((res) => {
            if (res) {
              self.$Config.isFormChange = false
              self.$Config.isFormChangeCheck = false
              next()
            }
          })
      }
    })
  },
  beforeDestroy() {
    console.log("beforeDestroy")
    this.$EventBus.$off("routerViewAlign")
    this.$EventBus.$off("messageShow")
    this.$EventBus.$off("confirmMessage")
  },
  mounted() {
    this.$store.watch(() => {
      this.loading = this.$store.getters["baseStore/getIsLoading"]
      this.isShowBar = this.$store.getters["baseStore/getIsShowBar"]
      console.log("appStore > showbar", this.isShowBar)
    })
  },
  method: () => ({}),
  computed: {
    isDark: function() {
      return this.$vuetify.theme.dark
    },
  },
}
</script>
