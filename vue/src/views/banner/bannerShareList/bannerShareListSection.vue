<template>
  <v-container class="pa-0 ma-0" fluid>
    <ul class="section-wrap ondaji_font_14_medium_brown">
      <li
        :class="section.class"
        v-for="(section, i) in sections"
        @click="clickSection(i)"
        :key="i"
      >
        <v-icon>{{ section.icon }}</v-icon>
        {{ section.text }}
      </li>
    </ul>
  </v-container>
</template>

<script>
export default {
  data() {
    return {
      sections: [
        {
          class: 'active',
          icon: 'send',
          text: '발신 공유배너',
        },
        {
          class: '',
          icon: 'inbox',
          text: '수신 공유배너',
        },
      ],
    }
  },
  methods: {
    clickSection(sectionType) {
      for (let section of this.sections) {
        section.class = ''
      }

      this.sections[sectionType].class = 'active'

      this.$emit('selectSection', sectionType)
    },
  },
}
</script>

<style lang="scss" scoped>
ul {
  padding: 0;
  list-style: none;
}

.section-wrap {
  display: flex;
  justify-content: flex-start;
  color: #938783;
  border-bottom: 1px solid #e0e0e0;
  margin-right: 12px;

  .v-icon {
    color: #938783;
    margin-right: 4px;
  }

  li {
    cursor: pointer;
  }

  li:first-child {
    margin: 0 20px;
  }

  .active {
    color: #3e2723;
    padding-bottom: 2px;
    border-bottom: 3px solid #3e2723;

    .v-icon {
      color: #3e2723;
    }
  }
}

@include mobile {
  .section-wrap {
    li:first-child {
      margin-left: 0;
    }
  }
}
</style>
