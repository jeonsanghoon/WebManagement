<template>
  <v-container class="pa-0">
    <div class="modal-wrap" v-if="viewModal === true">
      <div v-if="page === 0">
        <modalHeader :info="headerInfo[0]" @close="close" />
        <ol>
          <li v-for="(content, index) in contents" :key="index">
            <v-col
              cols="11"
              class="text-start ondaji_font_12_medium_brown_100 pa-0"
            >
              <div v-if="index != 1">
                {{ content }}
              </div>
              <div v-else>
                2. 원하는 방향의
                <span
                  class="arrow-text ondaji_font_12_medium_tealgreen_40"
                  @click="clickArrow"
                  >화살표 아이콘
                </span>
                을 선택하거나 배너 카드를 드래그하여 순서 조정을 합니다.
              </div>
            </v-col>
          </li>
        </ol>
      </div>
      <div v-else>
        <modalHeader :info="headerInfo[1]" @close="close" />
        <ol>
          <li v-for="(content, index) in arrowContents" :key="index">
            <v-col
              cols="11"
              class="d-flex justify-start ondaji_font_12_medium_brown_100 pa-0"
            >
              <v-img
                contain
                max-width="17"
                height="17"
                :src="require(`@/assets/images/icons/${content.icon}`)"
              />
              <div class="ml-4">{{ content.text }}</div>
            </v-col>
          </li>
        </ol>
      </div>
    </div>
    <div class="btn-wrap">
      <v-btn
        color="#3E2723"
        rounded
        depressed
        width="85"
        height="34"
        @click="viewModal = !viewModal"
      >
        <span class="ondaji_font_12_bold_white">도움말 </span>
        <div class="help-icon-wrap ondaji_font_12_bold_white">
          <v-icon v-if="viewModal === false" class="ml-2" size="13"
            >fas fa-question</v-icon
          >
          <v-icon v-else small class="ml-2">fas fa-times</v-icon>
        </div>
      </v-btn>
    </div>
  </v-container>
</template>

<script>
import modalHeader from '@/views/mediaShop/components/helpModalHeader'

export default {
  components: {
    modalHeader,
  },
  data() {
    return {
      viewModal: false,

      headerInfo: [
        {
          title: `배너 순서 설정`,
          content: `로컬박스에서 노출시킬 항목들의 순서를 정하고 미리 볼 수
          있습니다.`,
        },
        {
          title: `화살표 버튼`,
          content: `순서를 조정할 수 있는 버튼으로 다음과 같은 기능이 있습니다.`,
        },
      ],

      page: 0,

      contents: [
        `1. 이동하고 싶은 배너 리스트 항목의 체크박스를 선택합니다.`,
        ``,
        `3. 미리 보기 버튼을 통해 확인합니다.`,
        `4. 업로드 버튼을 누르면 로컬박스에 변동 사항이 적용됩니다.`,
      ],

      arrowContents: [
        { icon: `top.svg`, text: `맨 위로` },
        {
          icon: `onespace_top.svg`,
          text: `한 칸 위로`,
        },
        {
          icon: `onespace_down.svg`,
          text: `한 칸 아래로`,
        },
        {
          icon: `down.svg`,
          text: `맨 아래로`,
        },
      ],
    }
  },
  methods: {
    clickArrow() {
      this.page = 1
    },
    close() {
      this.viewModal = !this.viewModal
      this.page = 0
    },
  },
}
</script>

<style lang="scss" scoped>
ol {
  padding: 24px 12px 26px 20px;
  list-style: none;
}

li {
  & + & {
    margin-top: 12px;
  }
}

.btn-wrap {
  position: relative;
}

.modal-wrap {
  position: absolute;
  width: 240px;
  background-color: white;
  border: 2px solid #e0e0e0;
  bottom: 45px;
  right: 0px;
  box-shadow: 0 0.125rem 0.25rem 0 #00000050;
}

.arrow-text {
  text-decoration: underline;
  cursor: pointer;
}

.help-icon-wrap {
  display: inline-block;
  margin-top: 1px;
}
</style>
