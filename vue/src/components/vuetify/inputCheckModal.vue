<template>
  <v-dialog
    class="check-input-dialog ma-0"
    v-model="dialog"
    max-width="340"
    persistent
  >
    <v-card class="py-5 px-2 px-sm-7">
      <div class="check-title mb-7">
        필수 입력 항목을 확인해 주세요.
      </div>

      <div class="content-box pa-5" cols="12">
        <div v-for="(input, i) in info" :key="i">
          <div v-if="i != info.length - 1" class="check-content mb-2">
            <v-icon small>done</v-icon>
            {{ input.text }}
          </div>
          <div v-else class="check-content">
            <v-icon small>done</v-icon>
            {{ input.text }}
          </div>
        </div>
      </div>

      <div class="check-back text-end mt-7" @click="close">
        돌아가기
      </div>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  props: {
    info: {
      type: Array,
      required: true,
    },
    dialog: {
      type: Boolean,
      required: true,
      default: false,
    },
  },
  methods: {
    close() {
      this.$emit('update:dialog', false)
    },
  },
}
</script>

<style lang="scss" scoped>
.check-title {
  font-weight: normal;
  color: #5c4c47;
}

.content-box {
  border: 2px solid #e0e0e0;
}

.check-content {
  font-weight: normal;
  color: #a9a09e;
}

.check-back {
  font-weight: bold;
  color: #9f9693;
  cursor: pointer;
}

::v-deep {
  .v-icon {
    margin-right: 1rem;
  }
}
</style>
