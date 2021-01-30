<template>
  <v-container class="pa-0">
    <div class="search-wrap">
      <input
        type="text"
        v-model="searchText"
        :placeholder="info.placeholder"
        @keydown.enter="search"
      />

      <div class="search-icon-wrap d-flex">
        <div v-if="closeIconSwitch === false" class="search-icon-empty"></div>
        <v-img
          v-else
          class="search-icon"
          src="@/assets/images/icons/close_round.svg"
          contain
          :max-width="ICON.SIZE"
          :max-height="ICON.SIZE"
          @click="searchText = ``"
        ></v-img>
        <div class="search-divider-bar"></div>
        <v-img
          class="search-icon mx-2 mx-sm-4"
          src="@/assets/images/banner_icons/search.svg"
          contain
          :max-width="ICON.SIZE"
          :max-height="ICON.SIZE"
          @click="search"
        ></v-img>
      </div>
    </div>
  </v-container>
</template>

<script>
export default {
  props: {
    info: {
      type: Object,
      required: false,
    },
  },
  data() {
    return {
      searchText: ``,
      SEARCH_TEXT_MAX_LEN: 22,

      closeIconSwitch: false,

      ICON: {
        SIZE: `0.938rem`,
      },
    }
  },
  watch: {
    searchText() {
      if (this.searchText != ``) {
        this.closeIconSwitch = true
      } else {
        this.closeIconSwitch = false
      }

      if (this.searchText.length > this.SEARCH_TEXT_MAX_LEN) {
        this.searchText = this.searchText.slice(0, this.SEARCH_TEXT_MAX_LEN)
        return
      }
    },
  },
  methods: {
    search() {
      this.$emit('search', this.searchText)
    },
  },
}
</script>

<style lang="scss" scoped>
@mixin input-font-style {
  font: {
    weight: 400;
    size: 0.75rem;
  }
}

.search-wrap {
  position: relative;
}

input {
  @include input-font-style;
  color: #3e2723;
  min-width: 100%;
  padding: 0.5rem 1.25rem;
  display: inline-block;
  border: 0.125rem solid #d1c8c6;
  border-radius: 1.25rem;
  background-color: white;
}

input:focus {
  box-shadow: 0 0 0 0.313rem #f5eee8;
  outline: none;
}

input::placeholder {
  @include input-font-style;
  color: #757575;
}

.search-icon-empty {
  width: 0.938rem;
}

.search-icon {
  cursor: pointer;
}

.search-icon-wrap {
  position: absolute;
  min-width: 5rem;
  top: 11px;
  right: 6px;
}

.search-divider-bar {
  height: 0.938rem;
  border: 0.063rem solid #d1c8c6;
  margin-left: 0.625rem;
}

@media only screen and (max-width: $grid-breakpoints_sm) {
  .search-icon-wrap {
    right: -6px;
  }
}
</style>
