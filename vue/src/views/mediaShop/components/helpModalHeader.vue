<template>
  <v-container class="pa-0">
    <v-row class="modal-wrap" no-gutters>
      <v-col cols="11">
        <v-row no-gutters>
          <div class="header-wrap d-flex align-center">
            <span class="ondaji_font_14_bold_brown_100">
              {{ info.title }}
            </span>
          </div>
        </v-row>
        <v-row no-gutters>
          <div class="content-wrap ondaji_font_12_medium_brown_100">
            {{ info.content }}
          </div>
        </v-row>
      </v-col>

      <v-col class="btn-wrap" cols="1">
        <v-btn text icon color="ondaji_color_grey_70" x-small @click="close">
          <v-icon>
            fas fa-times
          </v-icon>
        </v-btn>
      </v-col>
    </v-row>

    <div class="line-wrap" />
  </v-container>
</template>

<script>
export default {
  props: {
    info: {
      type: Object,
      required: true,
    },
  },
  methods: {
    close() {
      this.$emit('close')
    },
  },
}
</script>

<style lang="scss" scoped>
.modal-wrap {
  padding-left: 20px;
  padding-right: 12px;
}

.header-wrap {
  background: white;
  margin: 16px 0 10px 0;
  width: 80%;
}

.btn-wrap {
  padding-top: 14px;
}

.content-wrap {
  margin-bottom: 14px;
  text-align: start;
}

.line-wrap {
  border: 0.01rem solid #e0e0e0;
}
</style>
