<template>
  <v-dialog
    ref="alert"
    value="true"
    persistent
    min-width="25rem"
    max-width="30rem"
    @keydown.esc="cancel"
  >
    <v-card class="alert">
      <v-card-title :class="'alert_title' + (badgeText !== '' ? ' ' : '')">
        <div v-if="badgeText !== ''">
          <div class="tri"></div>
          <span class="tritext">{{ badgeText }}</span>
        </div>
        {{ title }}</v-card-title
      >
      <v-card-text
        :class="'alert_text ' + (badgeText !== '' ? ' pl-10' : '')"
        v-html="message2"
      ></v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn color="Brown Darken-4" text @click="confirm"
          ><span :class="confirmClassName">{{ confirmText }}</span></v-btn
        >
        <v-btn
          color="Brown Darken-4"
          v-if="alertType !== 'alert'"
          text
          @click="cancel"
          ><span class="alert_calcelbtn">{{ noBtnName }}</span></v-btn
        >
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<style scoped lang="scss">
.tri {
  width: 0px;
  height: 0px;
  border-top: 0px solid transparent;
  border-left: 65px solid #50c5a4;
  border-bottom: 60px solid transparent;
  position: absolute;
  top: 0rem;
  left: 0rem;
  &text {
    color: #fff;
    position: absolute;
    top: 0rem;
    left: 0.5rem;
    font-size: 0.75rem;
  }
}
</style>
<script>
export default {
  props: {
    dark: { type: Boolean, default: false },
    alertType: {
      type: String,
      default: "alert" /** alert, del, confirm */,
    },
    badgeColor: {
      type: String,
      default: "#50C5A4",
    },
    badgeText: {
      typs: String,
      default: "",
    },
    title: {
      type: String,
      default: "",
    },
    message: {
      type: String,
    },
    yesBtnName: {
      type: String,
      default: "",
    },
    noBtnName: {
      type: String,
      default: "취소",
    },
  },
  created() {
    this.$vuetify.theme = {}
    this.$vuetify.theme.dark = this.isDark

    this.$vuetify.breakpoint = {}
    this.$vuetify.breakpoint.smAndUp = false
    this.$vuetify.breakpoint.smAndDown = false
    this.message2 = this.message.replace("|", "<br/>")
    if (this.alertType === "del") {
      this.confirmClassName = "alert_delbutton"
      this.confirmText = this.yesBtnName === "" ? "삭제" : this.yesBtnName
    } else {
      this.confirmText = this.yesBtnName === "" ? "확인" : this.yesBtnName
    }
  },
  data() {
    return {
      confirmClassName: "alert_button",
      value: false,
      message2: "",
    }
  },
  mounted() {
    if (this.badgeText !== "")
      document.querySelector(
        ".alert .tri"
      ).style.borderLeftColor = this.badgeColor
  },
  destroyed() {
    //document.removeEventListener('keyup', this.onEnterPressed)
  },
  methods: {
    cancel() {
      this.value = false
      this.$emit("result", this.value)
      this.$destroy()
    },
    confirm() {
      this.value = true
      this.$emit("result", this.value)

      this.$destroy()
    },
  },
}
</script>
