<template>
  <v-container class="wrap">
    <ul>
      <li>
        <v-row class="content-wrap">
          <!-- 체크 박스 -->
          <v-col class="col-wrap d-sm-flex justify-sm-center" cols="12" sm="2">
            <div @click.capture="check">
              <v-checkbox
                v-model="localer.check"
                class="ma-0"
                color="#3E2723"
                hide-details
                dense
              />
            </div>
          </v-col>

          <!-- NO -->
          <v-col class="col-wrap" cols="12" sm="1">
            <p class="ondaji_font_14_bold_grey_100">
              {{ localer.no }}
            </p>
          </v-col>

          <!-- 로컬러 -->
          <v-col class="col-wrap d-sm-flex justify-sm-start" cols="12" sm="3">
            <div class="profile-icon-wrap">
              <v-img
                v-if="!checkNull(localer.profileImg)"
                class="profile-icon"
                :src="`/files/${localer.profileImg}`"
                width="22"
                height="22"
              />
              <v-icon v-else class="profile-icon">far fa-user-circle</v-icon>
            </div>
            <p class="text-truncate ondaji_font_14_medium_grey_60">
              {{ localer.name }}
            </p>
          </v-col>

          <!-- 업종 -->
          <v-col class="col-wrap" cols="12" sm="2">
            <p class="text-truncate ondaji_font_14_medium_grey_60">
              {{ localer.company }}
            </p>
          </v-col>

          <!-- 위치 -->
          <v-col class="col-wrap d-sm-flex justify-sm-start" cols="12" sm="4">
            <p class="text-truncate ondaji_font_14_medium_grey_60">
              {{ localer.addr }}
            </p>
          </v-col>
        </v-row>
      </li>
    </ul>
  </v-container>
</template>

<script>
import { commonMediaShop } from "@/views/mediaShop/mixins/commonMixins"

export default {
  mixins: [commonMediaShop],

  props: {
    localer: {
      type: Object,
      required: false,
    },
  },
  data() {
    return {
      searchList: [],
    }
  },
  methods: {
    check() {
      this.$emit("check", this.localer)
    },
  },
}
</script>

<style lang="scss" scoped>
ul {
  padding: 0;
  list-style: none;
}

p {
  padding: 0;
  margin: 0;
  line-height: 23px;
  text-align: center;
}

li:not(:last-child) {
  border-bottom: 4px solid #fafafa;
}

.wrap {
  padding: 0;
}

.content-wrap {
  background-color: white;
  padding: 12px 0;
  margin: 0;
  border-bottom: 4px solid #fafafa;
}

.profile-icon-wrap {
  margin-right: 10px;

  .profile-icon {
    display: flex;
    background: #3e2723;
    color: #aaaaaa;
    font-size: 18px;
    border-radius: 50%;
    border: 2px solid #3e2723;
  }
}

@media screen and (max-width: $grid-breakpoints_sm) {
  .wrap {
    border-top: 1px solid #e2d9d2;
  }

  li:not(:last-child) {
    border-bottom: 1px solid #f2f2f2;
  }

  .content-wrap {
    padding: 0;
    border-bottom: none;
  }

  .col-wrap {
    display: flex;
    align-items: center;
    justify-content: flex-start;
    padding: 0 0 0 10px;
    height: 60px;
  }
}

::v-deep {
  .v-input {
    padding: 0;
  }
  .v-input--selection-controls__input {
    margin-right: 0;
  }
}
</style>
