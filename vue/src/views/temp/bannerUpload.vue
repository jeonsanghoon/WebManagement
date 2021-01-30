<template>
  <v-container class="bannerInfo" ref="bannerInfo" fluid>
    <v-row>
      <v-col style="height:20px;">
        <p class="caption text-left ma-0">
          * 서브배너는 최대 {{ maxItemCount }}개까지만 등록 가능합니다.
        </p>
      </v-col>
    </v-row>

    <v-row>
      <v-col
        v-for="(data, index) in uploadList"
        cols="12"
        :key="data.seq"
        xs="12"
        sm="6"
        md="4"
        xl="3"
      >
        <v-card class="pa-2" outlined tile>
          <v-container class="pa-0">
            <v-row style="height:20px;">
              <v-col cols="12" sm="6" lg="6" align="start" class="pt-1">
                <div class="pl-1 subtitle font-weight-bold">
                  {{ index === 0 ? "메인배너" : "서브배너" }} {{ data.seq }}
                </div>
              </v-col>
              <v-col cols="12" sm="6" lg="6">
                <div
                  style="position:absolute; top:10px;right:10px;z-index:99"
                  v-if="index !== 0"
                >
                  <v-btn
                    text
                    x-small
                    @click="deleteBanner(data.seq)"
                    class="pa-0 red--text"
                    ><i class="fas fa-minus fa-2x"></i
                  ></v-btn>
                </div>
              </v-col>
            </v-row>
            <v-row>
              <v-col>
                <Vuetify-filepond2
                  :index="data.seq"
                  v-model="data.fileData"
                  :options="uploadOption"
                  :file_type="
                    data.playType === null
                      ? 'image/*,video/mp4'
                      : data.playType === 1
                      ? 'image/*'
                      : 'video/mp4'
                  "
                  @uploadCallback="uploadCallback(data.seq)"
                  @deleteCallback="deletefileCallback(data.seq)"
                />
              </v-col>
            </v-row>
            <v-row>
              <v-col class="pb-1">
                <v-textarea
                  v-if="index !== 0"
                  no-resize
                  v-model="data.content"
                  outlined
                  name="input-7-4"
                  :label="label"
                  value=""
                  :maxlength="400"
                  class="pb-0"
                >
                </v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-card>
      </v-col>
      <v-col
        cols="12"
        xs="12"
        sm="6"
        md="4"
        xl="3"
        v-if="maxItemCount + 1 > uploadList.length"
      >
        <v-hover v-slot:default="{ hover }">
          <v-card
            :elevation="hover ? 8 : 0"
            height="520"
            class="justify-center align-center pa-2"
            style="vertical-align:middle;line-height: 520px;"
            @click="addBanner"
            tile
          >
            <i class="fas fa-plus fa-4x"></i>
          </v-card>
        </v-hover>

        <v-dialog v-model="isShow" max-width="1000px">
          <v-card>
            <v-card-title>
              <Vuetify-menutitle :title="'동영상 썸네일'" />
            </v-card-title>
            <v-card-text>
              <v-container>
                <Vuetify-videoUpload
                  :index="popIndex"
                  :isUpload="false"
                  :isThumbnail="true"
                  @callBack="videoPick"
                />
              </v-container>
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="close">닫기</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-btn @click="confirmInfo">정보확인</v-btn>
      </v-col>
    </v-row>
    <v-row>
      <v-col>
        <v-textarea
          label="정보확인 결과값"
          v-model="rtnData"
          outlined
        ></v-textarea>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  props: {
    maxItemCount: {
      type: Number,
      default: 4,
    },
    maxLength: {
      type: Number,
      default: 400,
    },
    loadList: {
      type: Array,
      default: () => [],
    },
  },
  data() {
    return {
      isShow: false,
      popIndex: 1,
      uploadCount: 2,
      label: "",
      rtnData: "",
      fileData: {},
      uploadData: {
        bannerCode: null,
        subIdx: null,
        seq: 0,
        playType: null,
        thumbImageUrl: null,
        bannerImageUrl: null,
        bannerMovieUrl: null,
        content: null,
        fileData: null,
      },
      uploadList: [],
      uploadOption: {
        tagName: "upload",
        uploadUrl: "/file/uploadfilepond",
        params: { isThumbnail: true, size: 300 },
      },
      phone_number: "",
      //defaultVideo:{videoUrl:null, arrThumbNailImage:[]},
      RuleTextArea: [
        (v) =>
          v.length <= this.maxLength ||
          `최대 ${this.maxLength}자까지 입력가능합니다.`,
      ],
    }
  },
  mounted() {
    for (let i = 1; i <= this.uploadCount; i++) {
      this.$EventBus.$emit(
        "videoUpload" + String(i) + "_init",
        this.uploadOption.params.isThumbnail
      )
    }
  },
  created() {
    this.label = "상세내용(최대 " + this.maxLength + "자)"
    if (this.loadList.length === 0) this.uploadList.push(this.uploadData)
    else this.uploadList = [...this.loadList]
  },
  methods: {
    uploadCallback: function(seq) {
      let idx = this.uploadList.findIndex((w) => w.seq === seq)
      console.log("uploadCallback", seq, this.uploadList[idx].fileData)

      let fileData = this.uploadList[idx].fileData
      if (fileData.fileContentType == "video/mp4") {
        this.uploadList[idx].playType = 2
        this.uploadList[idx].bannerMovieUrl = this.uploadList[
          idx
        ].fileData.attachmentUrl
        this.isShow = true
        let self = this
        setTimeout(() => {
          self.$EventBus.$emit("videoUploadInit", idx, {
            videoUrl: this.uploadList[idx].bannerMovieUrl,
            arrThumbNailImage: this.uploadList[idx].fileData.arrThumbNailImage,
          })
        }, 500)
      } else {
        this.uploadList[idx].playType = 1
        this.uploadList[idx].bannerImageUrl = this.uploadList[
          idx
        ].fileData.attachmentUrl
      }
      this.uploadList[idx].thumbImageUrl = this.uploadList[
        idx
      ].fileData.thumbNailImage
      this.popIndex = idx
      console.log("uploadCallback total", idx, this.uploadList)
    },
    deletefileCallback: function(idx) {
      console.log("deletefileCallback >> ", idx)
    },
    addBanner() {
      if (this.uploadList.length < this.maxItemCount + 1) {
        let data = Object.assign({}, this.uploadData)
        data.seq =
          this.uploadList.length === 0
            ? 1
            : this.uploadList[this.uploadList.length - 1].seq + 1
        data.content = ""
        this.uploadList.push(data)
      }
    },
    deleteBanner(seq) {
      let delData = this.uploadList.filter((w) => w.seq == seq)
      if (delData.fileData !== null) {
        this.$BaseCommon.restful
          .call("/file/deletefile", delData.fileData)
          .then((res) => {
            if (res != null) {
              if (res.resultType.toLowerCase() == "error") {
                this.messageInfo.value = true
                this.messageInfo.message = res.message
              } else {
                this.messageInfo.value = false
                this.messageInfo.message = ""
                ///this.uploadfile = null
              }
            }
          })
      }

      console.log("삭제 데이터 >> ", delData)
      this.uploadList = [...this.uploadList.filter((w) => w.seq !== seq)]
    },
    confirmInfo() {
      let rtnTmp = [...this.uploadList]

      rtnTmp.forEach(function(data, index) {
        delete rtnTmp[index]["fileData"]
      })
      this.rtnData = JSON.stringify(rtnTmp)
    },
    close() {
      this.isShow = false
    },
    save() {
      this.isShow = false
    },
    videoPick(res, index) {
      console.log("썸네일 만든후", res, index)
      this.isShow = false
      this.uploadList[index].thumbImageUrl = res.pickThumb
    },
  },
}
</script>
