<template>
  <v-container class="pt-0 pb-12">
    <header>
      <v-col v-if="info.len === 1" class="pt-5 ml-n3 ml-sm-n3 ml-lg-n5" cols="12">
        <span :class="info.class" style="color: #757575;" >{{text.title}} {{ info.index }}</span>
      </v-col>
      <v-col v-else class="pt-5 pl-0" cols="12">
        <span :class="info.class" style="color: #757575;">{{text.title}} {{ info.index }}</span>
      </v-col>
    </header>

    <content>
      <v-card class="pb-1 mx-auto" max-width="295">
        <!-- image -->
        <v-img :src="info.src" contain height="350"></v-img>
        <!-- middle-bar -->
        <v-system-bar>
          <div class="mx-auto">
            <span v-for="circleIcon in circleIconArr" :key="circleIcon.index">
              <v-icon class="circle-icon">{{ circleIcon }}</v-icon>
            </span>
          </div>
        </v-system-bar>
        <!-- content-header -->
        <v-list-item>
          <v-list-item-avatar></v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-title class="title">
              <span>{{ info.nick }}</span>
            </v-list-item-title>
            <v-list-item-subtitle>
              <span class="sub-title">
                {{ info.place }}
              </span>
            </v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
        <!-- content-in -->
        <v-divider class="mx-4"></v-divider>
        <div class="mt-2 mb-n1 ml-4">
          <span>{{ info.title }}</span>
        </div>
        <v-card-text>
          <span class="font-weight-medium">{{ info.content }} </span>
        </v-card-text>
      </v-card>
    </content>
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
  data() {
    return {
      text: {
        title: `모바일 배너 - `
      },
      circleIconArr: [],
    }
  },
  created() {
    let circleName = new String()

    for (let i = 0; i < this.info.len; i++) {
      if (this.info.index - 1 === i) {
        circleName = `fas fa-circle`
      } else {
        circleName = `far fa-circle`
      }

      this.circleIconArr.push(circleName)
    }
  },
}
</script>

<style lang="scss" scoped>
.container {
  padding: 0.405em;
}

.v-card {
  background-color: white;
}

.v-system-bar {
  background-color: #F5F5F5;
}

.v-avatar {
  background-color: #F5F5F5;
}

.sub-title {
  color: black;
  font-weight: bold;
}

.circle-icon {
  font-size: 0.6em;
}
</style>
