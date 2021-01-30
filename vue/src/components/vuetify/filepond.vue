<template>
  <div class="fileComponent">
    <FilePond
      ref="pond"
      v-if="this.acceptedFileTypes === 'image'"
      image-crop-aspect-ratio="1"
      :server="server"
      @init="filepondInit"
      @onprocessfile="onprocessfile"
      :allowImagePreview="isPreview"
      :allowImageCrop="true"
      :allowRevert="true"
      accepted-file-types="image/bmp, image/gif, image/jpeg, image/png, image/*"
    />

    <FilePond
      ref="pond"
      v-else-if="this.acceptedFileTypes === 'video'"
      :server="server"
      @init="filepondInit"
      @onprocessfile="onprocessfile"
      :allowImagePreview="false"
      :allowRevert="true"
      accepted-file-types="video/mp4"
    />
    <FilePond
      ref="pond"
      v-else-if="
        this.acceptedFileTypes === undefined || this.acceptedFileTypes === null
      "
      @addfile="handleFileUpload"
      @init="filepondInit"
      :server="server"
      :allow-multiple="true"
      @onprocessfile="onprocessfile"
      :allowImagePreview="false"
      :allowRevert="true"
      :accepted-file-types="this.acceptedFileTypes"
    />
    <FilePond
      ref="pond"
      v-else-if="
        !(
          this.acceptedFileTypes === undefined ||
          this.acceptedFileTypes === null
        )
      "
      :server="server"
      @init="filepondInit"
      @onprocessfile="onprocessfile"
      :allowImagePreview="false"
      :allowRevert="true"
      :accepted-file-types="this.acceptedFileTypes"
    />
  </div>
</template>
<style>
.fileComponent .filepond--item {
  width: calc(50% - 0.5em);
}

.fileComponent .filepond--root {
  margin-bottom: 0px;
}
.fileComponent .filepond--label-action {
  text-decoration: unset;
}
.fileComponent .filepond--root > .filepond--panel {
  z-index: 2;
}
.filepond--list.filepond--list .filepond--item {
  width: auto;
}
.filepond--wrapper label {
  cursor: pointer;
}
.fileComponent h4 {
  font-size: 1em !important;
}
.fileComponent .uploadtext .fas {
  font-size: 3em !important;
  color: rgb(134, 133, 133);
}
</style>
<style scoped>
.filepond--wrapper {
  border: 1px solid lightgray;
  cursor: pointer;
  border-radius: 0.5em;
}
.filepond--wrapper label {
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
    index: {
      type: Number,
      default: 1,
    },
    options: {
      type: Object,
    },
    isInit: {
      type: Boolean,
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
    this.$EventBus.$on(
      "videoUpload" + String(this.index) + "_init",
      (isThumb) => {
        console.log("videoUpload" + String(this.index) + "_init", isThumb)
        this.options.params.isThumbnail = isThumb
        this.params = this.$BaseCommon.dataInfo.objectToQuerystring(
          this.options.params
        )
        setTimeout(() => {
          this.setServer()
        }, 500)
      }
    )

    this.acceptedFileTypes =
      this.options.acceptedFileTypes === undefined ||
      this.options.acceptedFileTypes === null
        ? null
        : this.options.acceptedFileTypes

    this.isPreview =
      this.options.isPreview === undefined ? false : this.options.isPreview
    this.timeout =
      this.options.acceptedFiletimeoutTypes === undefined ||
      this.options.timeout === null
        ? this.timeout
        : this.options.timeout
    this.optionsuploadUrl = this.$BaseCommon.stringInfo.isNullEmpty(
      this.optionsuploadUrl
    )
      ? "/file/uploadfilepond"
      : this.optionsuploadUrl
  },
  mounted() {
    console.log("beforeMount")
    this.setServer()
  },
  data() {
    return {
      queryparams: "",
      uploadfile: null,
      isPreview: false,
      timeout: 60000,
      params: "",
      server: null, // This is the same as the instructions object
    }
  },
  methods: {
    setServer() {
      //this.setServer ={};

      this.server = {
        load: (uniqueFileId, load, error, progress, abort, headers) => {
          console.log("로드하였다", error, progress, abort, headers)
        },
        process: {
          url:
            (this.$BaseCommon.stringInfo.isNullEmpty(this.options.uploadUrl)
              ? "/file/uploadfilepond?"
              : this.options.uploadUrl +
                (String(this.options.uploadUrl).indexOf("?") >= 1
                  ? "&"
                  : "?")) +
            this.$BaseCommon.dataInfo.objectToQuerystring(this.options.params),
          method: "POST",
          headers: {},
          timeout: this.timeout,

          onload: (res) => {
            //this.deleteFile(this.uploadfile)

            console.log("업로드완료")
            console.log(res)
            this.$store.dispatch("baseStore/setIsLoading", false)

            try {
              let rtn = JSON.parse(res)
              this.uploadfile = rtn
              console.log(">>>>>>", this.uploadfile)

              this.options.tagName = this.$BaseCommon.stringInfo.isNullEmpty(
                this.options.tagName
              )
                ? "upload"
                : this.options.tagName
              this.$emit("uploadCallback", this.options.tagName, rtn)
            } catch (e) {
              console.log(e)
              this.$emit("uploadCallback", this.options.tagName, "업로드완료")
            }
            if (!this.isPreview && this.options.acceptedFileTypes !== null)
              setTimeout(() => {
                //this.$refs.pond.removeFiles()
                this.getUploadStatus()
              }, 1500)
          },
          onerror: null,
          ondata: (formData) => {
            this.$store.dispatch("baseStore/setIsLoading", true)
            formData.append("Hello", "World")
            return formData
          },
        },
        revert: (uniqueFileId, load, error) => {
          console.log(uniqueFileId, load, error)
          console.log("revert =>", this.uploadfile, this.uploadfile[0])
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
            if (res.resultType.toLowerCase() == "error") {
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
      this.getUploadStatus()
    },
    getUploadStatus() {
      var totalFiles = this.$refs.pond.$el.querySelectorAll(".filepond--item")
        .length
      var completedFiles = this.$refs.pond.$el.querySelectorAll(
        '.filepond--item[data-filepond-item-state="processing-complete"]'
      ).length
      console.log("totalFiles: " + totalFiles)
      console.log("completedFiles: " + completedFiles)
      if (completedFiles == totalFiles) {
        this.$refs.pond.removeFiles()
      }
    },
    filepondInit() {
      this.$refs.pond.$el.querySelector(
        ".filepond--drop-label label"
      ).innerHTML = ""

      let labelImage = "fa-image"
      if (
        this.acceptedFileTypes === undefined ||
        this.acceptedFileTypes === null
      )
        labelImage = "fa-file"
      else if (this.acceptedFileTypes === "image") labelImage = "fa-image"
      else if (this.acceptedFileTypes === "video") labelImage = "fa-video"
      else if (
        this.acceptedFileTypes.indexOf("excel") >= 0 ||
        this.acceptedFileTypes.indexOf("openxmlformats-officed") >= 0
      )
        labelImage = "fa-file-excel"
      else labelImage = "fa-file"

      this.$refs.pond.$el
        .querySelector(".filepond--drop-label")
        .insertAdjacentHTML(
          "beforeend",
          `<div class="uploadtext"><i class="fas ${labelImage} fa-5x"></i></div><div class='uploadtext ml-5'><h4>파일추가</h4><span style='font-size:0.9em;'>파일을 마우스로 끌어올릴 수 있습니다.</span></div>`
        )
    },
    handleFileUpload(error, file) {
      if (error === null) {
        console.log(file)
      }
    },
  },
  beforeDestroy() {
    console.log("beforeDestroy")
    this.$EventBus.$off("videoUpload" + String(this.index) + "_init")
  },
}
</script>
