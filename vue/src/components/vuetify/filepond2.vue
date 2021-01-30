<template>
  <div class="uploadPond">
    <FilePond
      v-if="fileType2 === 0"
      :Class="'upload_' + index + ' content filePondAll'"
      ref="pond"
      :server="server"
      @init="filepondInit"
      @onprocessfile="onprocessfile"
      :allowImagePreview="false"
      :allowRevert="true"
      accepted-file-types="image/gif, image/jpg, image/jpeg, image/png,video/mp4"
    />
    <FilePond
      v-else-if="fileType2 === 1"
      :Class="'upload_' + index + ' content filePondImage'"
      ref="pond"
      :server="server"
      @init="filepondInit"
      @onprocessfile="onprocessfile"
      :allowImagePreview="false"
      :allowRevert="true"
      accepted-file-types="image/gif, image/jpg, image/jpeg, image/png"
    />
    <FilePond
      v-else-if="fileType2 === 2"
      :Class="'upload_' + index + ' content filePondVideo'"
      ref="pond"
      :server="server"
      @init="filepondInit"
      @onprocessfile="onprocessfile"
      :allowImagePreview="false"
      :allowRevert="true"
      accepted-file-types="video/mp4"
    />
  </div>
</template>
<style>
.filepond--item {
  width: calc(50% - 0.5em);
}

.filepond--root {
  margin-bottom: 0px;

  background-size: contain;
  opacity: 1;
  display: flex;
  align-items: center;
}
.filepond--root * {
  cursor: pointer;
}
.filepond--label-action {
  text-decoration: unset;
}
.filepond--root > .filepond--panel {
  z-index: 2;
}
.filepond--list.filepond--list .filepond--item {
  width: auto;
}

h4 {
  font-size: 1.2em !important;
  margin-bottom: 0.7em;
}
.uploadPond {
  text-align: center;
}
.uploadPond .fas {
  color: rgb(134, 133, 133);
}

.uploadPond .fas.fa-5x {
  font-size: 3.3em;
}
.uploadPond .fas.fa-slash {
  font-size: 1.3em !important;
  vertical-align: super;
  margin-right: 0.2em;
}
.uploadPond .filepond--root :not(text) {
  font-size: initial;
}
.uploadPond .filepond--root {
  background-size: contain, contain;
  background-position: center;
}

.uploadPond .filepond--root .filepond--drop-label {
  height: 100% !important;
  display: grid;
}

.uploadPond .filepond--panel-root {
  background-color: transparent;
}
.uploadPond .content .filepond--root {
  margin-left: auto;
  margin-right: auto;
}
.uploadPond .fileCard {
  background-color: #f5eee8;
  border-radius: 5px;
  border: solid #bdbdbd 1px;
  text-align: center;
  margin-left: auto;
  margin-right: auto;
  width: 100%;
  display: flex;
  background-size: contain;
  align-items: center;
  background-position: center; /* Center the image */
}
.fileCard img {
  margin-left: auto;
  margin-right: auto;
}
</style>

<style lang="scss" scoped>
.filepond--wrapper {
  border: 1px solid lightgray;
  cursor: pointer;
  border-radius: 0.5em;
}
.filepond--wrapper label {
  cursor: pointer;
}
.fas.fa-rotate-90 {
  -ms-transform: rotate(90deg); /* Internet Explorer 9 */
  -webkit-transform: rotate(90deg); /* Chrome, Safari, Opera */
  transform: rotate(90deg); /* Standard syntax */
}

.content {
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center;
}
.content .filepond--drop-label {
  background-size: contain;
  background-repeat: no-repeat;
  background-position: center;
}
.uploadtext {
  cursor: pointer;
}
</style>
<script>
import vueFilePond from "vue-filepond"
import FilePondPluginImagePreview from "filepond-plugin-image-preview"
// Import plugins
import FilePondPluginFileValidateType from "filepond-plugin-file-validate-type/dist/filepond-plugin-file-validate-type.esm.js"
// Import styles
import "filepond/dist/filepond.min.css"
import "filepond-plugin-image-preview/dist/filepond-plugin-image-preview.min.css"
import FilePondPluginImageCrop from "filepond-plugin-image-crop"
// create() to turn it into a pond
export default {
  props: {
    imageUrl: {
      type: String,
    },
    index: {
      type: Number,
      default: 1,
    },
    options: {
      type: Object,
      default: null,
    },
    isInit: {
      type: Boolean,
    },
    fileType: {
      type: Number,
      default: 0,
    },

    height: {
      type: Number,
      default: 500,
    },
    uploadSize: {
      type: String,
      default: "9:16",
    },
  },
  components: {
    FilePond: vueFilePond(
      FilePondPluginImagePreview,
      FilePondPluginFileValidateType,
      FilePondPluginImageCrop
    ),
  },

  created() {
    this.uploadOption =
      this.options == null
        ? {
            tagName: "upload",
            uploadUrl: "/file/uploadfilepond",
            params: { isThumbnail: true, size: 300 },
          }
        : Object.assign({}, this.options)

    let self = this
    self.$EventBus.$on(
      "videoUpload" + String(self.index) + "_init",
      (isThumb) => {
        console.log("videoUpload" + String(self.index) + "_init", isThumb)
        self.uploadOption.params.isThumbnail = isThumb
        self.params = self.$BaseCommon.dataInfo.objectToQuerystring(
          self.uploadOption.params
        )
        self.setServer()
      }
    )

    this.isPreview =
      this.uploadOption.isPreview === undefined
        ? false
        : this.uploadOption.isPreview
    this.timeout =
      this.uploadOption.acceptedFiletimeoutTypes === undefined ||
      this.uploadOption.timeout === null
        ? this.timeout
        : this.uploadOption.timeout
    this.fileType2 = this.fileType
  },
  mounted() {
    console.log("beforeMount")
    this.isPreview =
      this.uploadOption.isPreview === undefined
        ? false
        : this.uploadOption.isPreview
    this.timeout =
      this.uploadOption.acceptedFiletimeoutTypes === undefined ||
      this.uploadOption.timeout === null
        ? this.timeout
        : this.uploadOption.timeout
    this.setServer()

    setTimeout(() => {
      this.displayInit(this)
    }, 1000)
  },
  watch: {
    fileType: {
      handler: function(val) {
        console.log(val)
        this.fileType2 = this.fileType
        this.filepondInit()
      },
    },
    imageUrl: {
      handler: function(val) {
        console.log("imageUrl>>>>>>", val)
        this.filepondInit(val)
      },
    },
    uploadSize: {
      handler: function(val) {
        console.log("imageUrl>>>>>>", val)
        this.displayInit(this)
      },
    },
  },
  data() {
    return {
      fileType2: Number(0),
      uploadOption: null,
      queryparams: "",
      uploadfile: null,
      isPreview: false,
      timeout: 1000000,
      params: "",
      imgUrl2: "",
      cardLayout: {
        height: 200,
        width: 200,
      },
      server: null, // This is the same as the instructions object
    }
  },
  methods: {
    displayInit(self) {
      console.log("displayInit, ", self.uploadSize)
      if (self.$refs.pond === undefined) return
      let sleObj = self.$refs.pond.$el.querySelector(".content .filepond--root")

      sleObj.style.height = self.height

      self.cardLayout.height = self.height

      if (self.uploadSize == "9:16") {
        sleObj.style.height = String(self.height) + "px"
        self.cardLayout.height = self.height
        self.cardLayout.width = (self.height * 9) / 16.0
      } else if (self.uploadSize == "16:9") {
        sleObj.style.height = String(self.height) + "px"
        self.cardLayout.width = self.$refs.pond.$el.querySelector(
          ".upload_" + String(self.index) + " .filepond--root"
        ).clientWidth
        self.cardLayout.height = (self.cardLayout.width * 9) / 16.0
      } else {
        sleObj.style.height = String(self.height - 270) + "px"
        self.cardLayout.height = self.height - 270
        self.cardLayout.width = self.cardLayout.height
      }

      if (self.imgUrl2 !== "") {
        if (
          self.$refs.pond.$el.querySelector(
            ".upload_" + String(self.index) + " .fileCard"
          ) === null
        ) {
          sleObj.insertAdjacentHTML(
            "beforeend",
            ` <div class="fileCard" style="width: 100%;
                        height:${self.cardLayout.height}px;
                        width:${self.cardLayout.width}px;

                        ">
                        <!--<img style="
                        max-height:${self.cardLayout.height - 2}px;
                        max-width:${self.cardLayout.width - 2}px;"
                        onload="this.style.display='block'"
                        onerror="this.style.display='none'" >-->

                        
                        <div class="movie" style="
                        top: 5px;
                       
                        height: 100%;
                        width:100%;
                        text-align:right;
                        display:none;
                "><img style="width:30px;height:30px;margin-top:4px;margin-right:4px" src="/images/movie.svg"></div></div>`
          )
        } else {
          self.$refs.pond.$el.querySelector(
            ".upload_" + String(self.index) + " .fileCard"
          ).style.height = String(self.cardLayout.height) + "px"
          /* self.$refs.pond.$el.querySelector(
            ".upload_" + String(self.index) + " .fileCard img"
          ).style.height = String(self.cardLayout.height) + "px"*/

          self.$refs.pond.$el.querySelector(
            ".upload_" + String(self.index) + " .fileCard"
          ).style.width = String(self.cardLayout.width) + "px"
          /* self.$refs.pond.$el.querySelector(
            ".upload_" + String(self.index) + " .fileCard img"
          ).style.width = String(self.cardLayout.width) + "px"*/
        }
      }
    },
    setServer() {
      //this.setServer ={};

      this.server = {
        load: (uniqueFileId, load, error, progress, abort, headers) => {
          console.log("로드하였다", error, progress, abort, headers)
        },
        process: {
          url:
            (this.$BaseCommon.stringInfo.isNullEmpty(
              this.uploadOption.uploadUrl
            )
              ? "/file/uploadfilepond?"
              : this.uploadOption.uploadUrl +
                (String(this.uploadOption.uploadUrl).indexOf("?") >= 0
                  ? "&"
                  : "?")) +
            this.$BaseCommon.dataInfo.objectToQuerystring(
              this.uploadOption.params
            ),
          method: "POST",
          headers: {},
          timeout: this.timeout,

          onload: (res) => {
            this.deleteFile(this.uploadfile)

            this.displayInit(this)
            console.log("업로드완료")
            console.log(res)
            let rtn = JSON.parse(res)
            this.uploadfile = rtn
            console.log(">>>>>>", this.uploadfile)

            //let tag = this.$refs.pond.$el.querySelector('.content .filepond--root .img')

            let imgUrl = ""
            if (this.uploadOption.params.isThumbnail) {
              if (rtn[0].fileContentType === "video/mp4") {
                this.fileType2 = 2
              } else {
                this.fileType2 = 1
                imgUrl = rtn[0].attachmentUrl
              }
            }

            this.$store.dispatch("baseStore/setIsLoading", false)

            this.$emit("uploadCallback", rtn[0], this.index)

            this.$refs.pond.removeFile()
            this.filepondInit(imgUrl)
          },
          onerror: (formData) => {
            console.log(formData)
            this.$store.dispatch("baseStore/setIsLoading", false)
          },
          ondata: (formData) => {
            this.$store.dispatch("baseStore/setIsLoading", true)
            formData.append("Hello", "World")
            return formData
          },
        },
        revert: (uniqueFileId, load, error) => {
          console.log(uniqueFileId, load, error)
          console.log("uploadfile =>", this.uploadfile, this.uploadfile[0])
          load()
          this.deleteFile(this.uploadfile)
        },
      }
    },
    deleteFile(dellist) {
      if (dellist === null) return

      let fileInfo = dellist[0]
      this.$BaseCommon.restful
        .call("/file/deletefile", fileInfo)
        .then((res) => {
          if (res != null) {
            if (res.ResultType.toLowerCase() == "error") {
              this.$alert(res.message)
            } else {
              this.$emit("deleteCallback", this.uploadfile[0])
              ///this.uploadfile = null
            }
          }
        })
    },
    onprocessfile(response) {
      console.log(response)
    },
    filepondInit(imgurl) {
      //this.uploadfile = null

      this.fileType2 = this.fileType
      console.log("filepondInit >>", this.fileType2)

      this.$refs.pond.$el.querySelector(
        ".filepond--drop-label label"
      ).innerHTML = ""
      if (this.fileType2 === 0) {
        this.$refs.pond.$el
          .querySelector(".filepond--drop-label label")
          .insertAdjacentHTML(
            "beforeend",
            `<div class="text-center"><i class="fas fa-photo-video fa-5x"></i></div>
                     <div class='text-center'><h4>파일추가</h4>
                    <span style='font-size:0.9em;'>이미지/영상 업로드가 필요합니다.</span></div><div></div>`
          )
      } else if (this.fileType2 === 1) {
        this.$refs.pond.$el
          .querySelector(".filepond--drop-label label")
          .insertAdjacentHTML(
            "beforeend",
            `<div class="text-center"><i class="fas fa-image fa-5x"></i></div>
                        <div class='text-center'><h4>파일추가</h4>
                    <span style='font-size:0.9em;'>이미지 업로드가 필요합니다.</span></div><div></div>`
          )
      } else {
        this.$refs.pond.$el
          .querySelector(".filepond--drop-label label")
          .insertAdjacentHTML(
            "beforeend",
            `<div class="text-center"><i class="fas fa-video fa-5x"></i></div>
                        <div class='text-center'><h4>파일추가</h4>
                    <span style='font-size:0.9em;'>영상 업로드가 필요합니다.</span></div><div></div>`
          )
      }

      this.imgUrl2 = imgurl !== undefined ? imgurl : this.imageUrl
      this.imgUrl2 = this.$BaseCommon.stringInfo.replaceAll(
        this.imgUrl2,
        "/files/",
        ""
      )
      this.imgUrl2 = +this.imgUrl2 === "" ? "" : "/files/" + this.imgUrl2
      let self = this
      setTimeout(() => {
        self.displayInit(self)
        try {
          self.$refs.pond.$el.querySelector(".fileCard .movie").style.display =
            "none"

          this.$BaseCommon.formInfo.checkImage(
            self.imgUrl2,
            () => {
              self.$refs.pond.$el.querySelector(
                ".filepond--drop-label label"
              ).style.opacity = 0
              self.$refs.pond.$el.querySelector(
                ".fileCard"
              ).style.backgroundImage = "url('" + self.imgUrl2 + "')"
              if (self.fileType === 2)
                self.$refs.pond.$el.querySelector(
                  ".fileCard .movie"
                ).style.display = "block"
            },
            () => {
              self.$refs.pond.$el.querySelector(
                ".fileCard"
              ).style.backgroundImage = null
              self.$refs.pond.$el.querySelector(
                ".filepond--drop-label label"
              ).style.opacity = 1
              if (self.fileType === 2)
                self.$refs.pond.$el.querySelector(
                  ".fileCard .movie"
                ).style.display = "none"
            }
          )
        } catch (e) {
          console.log(e)
        }

        console.log("이미지 바인딩 >>> ", self.imgUrl2)
      }, 300)

      setTimeout(() => {
        try {
          document.querySelector(
            ".upload_" +
              String(this.index) +
              ".content .filepond--root .text-center"
          ).style.opacity = 0.4
        } catch (e) {
          console.log(e)
        }
      }, 200)
    },
  },
  beforeDestroy() {
    console.log("beforeDestroy")
    this.$EventBus.$off("videoUpload" + String(this.index) + "_init")
  },
}
</script>
