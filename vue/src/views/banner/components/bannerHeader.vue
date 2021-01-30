<template>
  <v-container :class="info.class" :style="info.style" fluid>
    <div class="d-flex">
      <v-icon v-if="info.icon != undefined" class="mx-2">
        {{ info.icon }}
      </v-icon>
      <div :class="info.classTitle" :style="info.styleTitle">{{ info.title }}</div>
      <v-icon
        v-if="info.closeIcon != undefined"
        :class="info.classCloseIcon"
        @click="close"
        :style="info.styleCloseIcon"
      >
        {{ info.closeIcon }}
      </v-icon>
    </div>

    <v-divider v-if="info.divider != undefined" class="mt-2"></v-divider>
  </v-container>
</template>

<script>
export default {
  props: {
    info: {
      type: Object,
      required: true,
      default: null,
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
</style>

