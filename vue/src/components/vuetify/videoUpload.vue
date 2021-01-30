<template>
  <v-row justify="center" class="pa-0">
    <v-col class="pa-0">
      <v-card
        fluid
        class="pa-0 ondajiColor removeLine"
        outlined
        width="100%"
        max-width="1200"
        ref="videoupload"
      >
        <v-row v-if="isUpload">
          <v-col xs="12" md="12">
            <v-card outlined>
              <Vuetify-filepond
                :options="videoUploadOption"
                :isInit="isFileInit"
                :index="index"
                @uploadCallback="uploadCallback"
                @deleteCallback="deletefileCallback"
              />
            </v-card>
          </v-col>
        </v-row>
        <v-row>
          <v-col
            xs="12"
            md="12"
            align="center"
            justify="center"
            class="pt-0 pb-0"
          >
            <div class="dvVideo">
              <v-card>
                <div class="pl-3 pt-3 pb-3 pr-3">
                  <div style="width:100%;height:100%;background-color:#f5eee8">
                    <video
                      ref="videoPlayer"
                      class="video-js vjs-default-skin mr-2"
                      style="background-color:#f5eee8;display:contain"
                      width="auto"
                      max-width="480px"
                      height="350px"
                      :controls="false"
                      preload="auto"
                      data-setup="{}"
                      @seeked="videoSyncBySlider"
                    >
                      <source type="video/mp4" />
                    </video>
                  </div>
                </div>
              </v-card>
            </div>
          </v-col>
        </v-row>

        <v-row v-if="isThumbnail">
          <v-col xs="12" md="12" align="center" justify="center">
            <v-card>
              <div class="dvSliderBoder">
                <div class="mySlider pr-12 pl-12">
                  <v-slider
                    inverse-label
                    v-model="slider"
                    class="pa-0"
                    color="#F5F5F5"
                    style="width:100%"
                    :max="videoMax"
                    :min="0"
                    @change="click"
                  ></v-slider>
                </div>
              </div>
            </v-card>
          </v-col>
        </v-row>
        <v-row v-if="isThumbnail">
          <v-col xs="12" md="12" align="right" justify="right">
            <Buttons-save @callback="confirm" />
          </v-col>
        </v-row>
      </v-card>
    </v-col>
  </v-row>
</template>
<style scoped>
.theme--light.v-card.v-card--outlined.removeLine {
  border: 0px;
}
</style>
<style scoped>
.dvVideo {
  padding: 0px;
  border: 0px solid darkgray;
}
.dvSliderBoder {
  padding: 0px;
  border: 1px solid darkgray;
}
.mySlider {
  width: 100%;
  background-color: #000000;
}
</style>
<style>
.mySlider .v-slider.v-slider--horizontal {
  width: 100%;
  height: 100px;
  margin: 0px !important;
  background-position: 0px 0px, 100px 0px;
  background-repeat: no-repeat;
}

.mySlider .v-input__slot {
  margin-bottom: 0px;
}
.mySlider .v-messages {
  min-height: 0px;
}
.mySlider .v-slider__thumb {
  height: 100px;
  width: 104px;
  left: -50px;
  border-radius: 0%;
  background-color: gray;
  z-index: 10;
  border: 0px dotted black !important;
  padding-top: 6px;
  padding-left: 6px;
  box-shadow: 0 10px 20px rgba(0, 0, 0, 0.19), 0 6px 6px rgba(0, 0, 0, 0.23);
  border-radius: 3px;
}
.mySlider .v-slider--horizontal .v-slider__track-container {
  height: 0px;
}
.mySlider .v-slider__thumb:before {
  display: none;
  background-color: gray;
}
.mySlider .v-slider.v-slider--horizontal {
  height: 100px;
}
.mySlider .slideImage {
  margin-top: 5px;
  border-radius: 2px;
}
.mySlider #sliderVideo {
  background-color: #000000;
}
</style>
<script>
export default {
  props: {
    index: {
      type: Number,
      default: 1,
    },
    isThumbnail: {
      type: Boolean,
      default: false,
    },
    isUpload: {
      type: Boolean,
      default: true,
    },
    data: {
      type: Object,
    },
  },
  created() {
    //this.$EventBus.$emit('videoUpload' + String(this.index) + '_init',this.isThumbnail)
    this.videoIndex = this.index
    let self = this
    this.$EventBus.$on("videoUploadInit", (index, data) => {
      self.videoIndex = index
      self.videoUploadInit(index, data)
    })
  },
  mounted() {
    this.$EventBus.$emit(
      "videoUpload" + String(this.index) + "_init",
      this.isThumbnail
    )
    this.isInit = true
  },
  watch: {
    data: {
      handler: function(val) {
        this.videoUploadInit(val)
      },
    },
    index: {
      handler: function(val) {
        this.videoIndex = val
      },
    },
  },
  data() {
    return {
      dialog: false,
      isFileInit: false,

      videoUploadOption: {
        tagName: "videoUPload",
        uploadUrl: "/file/uploadfilepond",
        acceptedFileTypes: "video",
        params: { isThumbnail: true, size: 300, timeout: 100000 },
      },
      slider: 0,
      nVideo: 0,
      videoUrl: "",
      uploadData: {},
      videoMax: 1000.0,
      videoIndex: 1,
    }
  },
  methods: {
    videoUploadInit(data) {
      let self = this
      self.slider = 0
      self.nVideo = 0
      self.$refs.videoPlayer.src = "/files/" + data.videoUrl
      self.videoUrl = data.videoUrl

      self.sliderVideoAdd("/files/" + data.videoUrl)
      self.sliderImageAdd(data.arrThumbNailImage)
      self.uploadData.pickThumb = "/files/" + data.arrThumbNailImage[0]
    },
    init() {
      let objTag = this.$refs.videoupload.$el
      this.slider = 0
      this.uploadData = null
      this.isFileInit = true
      this.isFileInit = false

      this.videoUploadOption.params.isThumbnail = this.isThumbnail
      if (objTag.querySelector("#sliderVideo") !== null) {
        try {
          objTag.querySelector("#sliderVideo").src = null
        } catch (e) {
          console.log(e)
        }
      }
      if (this.$refs.videoPlayer !== null) {
        try {
          this.$refs.videoPlayer.src = null
        } catch (e) {
          console.log(e)
        }
      }
      if (
        !(
          objTag.querySelector(".v-slider--horizontal") === undefined ||
          objTag.querySelector(".v-slider--horizontal") === null ||
          objTag.querySelector(".v-slider--horizontal").length === 0
        )
      ) {
        const removeElements = (elms) => elms.forEach((el) => el.remove())
        removeElements(objTag.querySelectorAll(".slideImage"))
      }
    },
    uploadCallback: function(name, res) {
      console.log("video >> ", name)
      console.log("video >> ", res)
      this.rtnData = "video upload >> " + JSON.stringify(res)
      this.$refs.videoPlayer.src = "/files/" + res[0].attachmentUrl
      this.videoUrl = res[0].attachmentUrl
      this.uploadData = res[0]

      if (this.isThumbnail) {
        this.sliderVideoAdd("/files/" + res[0].attachmentUrl)
        this.sliderImageAdd(res[0].arrThumbNailImage)
        this.uploadData.pickThumb = res[0].arrThumbNailImage[0]
      }
      this.$emit("callBack", this.uploadData, this.videoIndex)
    },
    deletefileCallback: function(res) {
      console.log("video Delete >> ", res)
      this.rtnData = JSON.stringify(res[0])
    },
    videoSyncBySlider() {
      let objTag = this.$refs.videoupload.$el
      this.slider =
        (this.$refs.videoPlayer.currentTime / this.$refs.videoPlayer.duration) *
        this.videoMax
      objTag.querySelector(
        "#sliderVideo"
      ).currentTime = this.$refs.videoPlayer.currentTime
    },
    click(event) {
      console.log(event)

      let objTag = this.$refs.videoupload.$el

      try {
        console.log("currentTime", this.$refs.videoPlayer.currentTime)
        let duaration = this.$refs.videoPlayer.duration
        console.log(event, this.slider)

        //  this.$refs.videoPlayer.currentTime = duaration * (this.slider / this.videoMax)
        //   if (this.nVideo === 0) this.sliderVideoAdd()
        if (
          objTag.querySelector("#sliderVideo").src === undefined ||
          String(objTag.querySelector("#sliderVideo").src).indexOf(
            "undefined" > 0
          )
        ) {
          objTag.querySelector("#sliderVideo").src = this.$refs.videoPlayer.src
          objTag
            .querySelector("#sliderVideo")
            .addEventListener("mousemove", (e) => {
              try {
                // let duaration = this.$refs.videoPlayer.duration
                console.log(e, this.slider, duaration)
                this.$refs.videoPlayer.currentTime =
                  duaration * (this.slider / this.videoMax)

                objTag.querySelector(
                  "#sliderVideo"
                ).currentTime = this.$refs.videoPlayer.currentTime
              } catch (e) {
                console.log(e)
              }
            })
        } else
          objTag.querySelector(
            "#sliderVideo"
          ).currentTime = this.$refs.videoPlayer.currentTime

        this.nVideo++
      } catch (e) {
        console.log(e)
      }
    },
    sliderVideoAdd(src) {
      let objTag = this.$refs.videoupload.$el
      if (objTag.querySelector("#sliderVideo") === null) {
        var x = document.createElement("video")
        x.setAttribute("id", "sliderVideo")
        if (x.canPlayType("video/mp4")) {
          x.setAttribute("src", src)
        } else {
          x.setAttribute("src", src)
        }

        x.setAttribute("width", "92")
        x.setAttribute("height", "89")
        x.setAttribute("background-color", "#ffffff")

        objTag.querySelectorAll(".v-slider__thumb")[0].appendChild(x)
      } else {
        objTag.querySelector("#sliderVideo").src = src
      }
    },
    sliderImageAdd(arrImage) {
      let objTag = this.$refs.videoupload.$el

      let imageSlideList = objTag.querySelectorAll(".slideImage")
      if (!(imageSlideList === null || imageSlideList.length === 0)) {
        for (let element of imageSlideList) {
          element.remove()
        }
      }
      let nAddLeft =
        objTag.querySelectorAll(".v-slider--horizontal")[0].clientWidth /
        ((arrImage.length - 1) * 1.0)
      let nLeft =
        (-1 *
          objTag.querySelectorAll(".mySlider .v-slider__thumb")[0]
            .clientWidth) /
        2.0

      for (let i = 0; i < arrImage.length; i++) {
        let div = document.createElement("div")
        div.style.setProperty("left", String(nLeft) + "px")
        div.style.position = "absolute"
        let image = `<img class="slideImage" src="/files/${arrImage[i]}" width="100px" height="100px" style="opacity:0.4"/>`
        div.innerHTML = image
        objTag.querySelectorAll(".v-slider--horizontal")[0].appendChild(div)
        nLeft = nLeft + nAddLeft
      }
    },

    confirm_after(res) {
      this.$emit("callBack", res, this.videoIndex)
      //this.$EventBus.$emit('uploadImage_' + String(this.videoIndex), res, this.videoIndex)
    },
    confirm() {
      let self = this
      this.$BaseCommon.restful
        .call("/file/getVideoTakePicture", {
          filePath: this.videoUrl,
          second: this.$refs.videoPlayer.currentTime,
          size: 300,
        })
        .then((res) => {
          console.log(res)

          self.uploadData.pickThumb = "/files/" + res
          self.$refs.videoupload.$el.querySelector("#sliderVideo").src = null
          self.$refs.videoPlayer.src = null

          self.$refs.videoPlayer.removeAttribute("src") // empty source
          self.$refs.videoPlayer.load()

          self.$refs.videoupload.$el
            .querySelector("#sliderVideo")
            .removeAttribute("src")
          self.$refs.videoupload.$el.querySelector("#sliderVideo").load()

          self.confirm_after(self.uploadData)
        })
        .catch((error) => {
          console.log(error)
        })
    },
  },
  beforeDestroy() {
    console.log("beforeDestroy")
    this.$EventBus.$off("videoUploadInit")
  },
}
</script>
