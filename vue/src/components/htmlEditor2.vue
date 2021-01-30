<template lang="pug">
  div(ref="editor" class="editor  ma-0 pa-0")
    vue-editor( id="editor" useCustomImageHandler @image-added="handleImageAdded" v-model="htmlForEditor"
                :editorOptions="editorSettings"   @focus="onEditorFocus" @blur="onEditorBlur" @change="onEditChange"
                @text-change="onTextChange"       @selection-change="onSelectionChange")
    span(style="float:left;" v-if="htmlForEditor === ''" class="text-left caption mt-1 red--text accent-2" v-text="emptyMessage")
</template>
<style scoped>
.quillWrapper {
  text-align: left;
}
.editError {
  border: 1px solid #ff5252 !important ;
}
</style>

<script>
import { VueEditor, Quill } from "vue2-editor"
import { ImageDrop } from "quill-image-drop-module"
import ImageResize from "quill-image-resize-module"

Quill.register("modules/imageDrop", ImageDrop)
Quill.register("modules/imageResize", ImageResize)

//import axios from "axios";
export default {
  props: {
    value: {
      type: String,
    },
    height: {
      type: String,
      default: "500px",
    },
    isImageBaseMode: {
      /**이미지업로드시점 false : 이미지 추가시 바로 업로드(파일 or Drag)
             *                 true : 데이터 저장시 이미지 저장후 콜백 처리( 콜백함수 @imageToServerCallback)
        
               */
      type: Boolean,
      default: false,
    },
    emptyMessage: {
      type: String,
      default: "내용을 입력하세요",
    },
  },
  components: {
    VueEditor,
  },
  created() {
    this.htmlForEditor = this.value
    this.loadHtmlData = this.value
    console.log("htmlForEditor", this.htmlForEditor)
    let self = this
    this.$EventBus.$on("imageToServer", () => {
      self.isSaveCheck = false
      self.imageToServer()
    })
  },
  mounted() {
    document.querySelector(".ql-editor").style.minHeight = this.height
  },
  watch: {
    value(val) {
      this.htmlForEditor = val
      this.loadHtmlData = val
      console.log("htmlForEditor watch", this.htmlForEditor)
      this.$emit("onEditTextChange", this.htmlForEditor)
    },
  },
  data() {
    return {
      isImageSave: false,
      htmlForEditor: "",
      loadHtmlData: "",
      editorSettings: {
        modules: {
          imageDrop: true,
          imageResize: {},
        },
      },
      isSaveCheck: true,
    }
  },

  methods: {
    onEditorBlur(quill) {
      console.log(quill)
    },
    onTextChange() {
      this.changeError()
      this.$emit("input", this.htmlForEditor)

      if (this.isSaveCheck) {
        this.$Config.isFormChange = true
        this.$Config.isFormChangeCheck = true
        if (this.loadHtmlData === this.htmlForEditor) {
          this.$Config.isFormChange = false
          this.$Config.isFormChangeCheck = false
        }
      }
      if (this.isImageBaseMode === true) {
        return
      }

      this.imageToServer()
    },
    imageToServer() {
      try {
        let arrImage = this.$refs.editor.querySelectorAll(".ql-editor img")
        let arrSaveIndex = new Array()
        let arrSaveData = new Array()

        for (let i = 0; i < arrImage.length; i++) {
          if (String(arrImage[i].src).indexOf("data:") >= 0) {
            arrSaveIndex.push(i)
            arrSaveData.push(arrImage[i].src.split(",")[1])
          }
        }

        if (arrSaveData.length === 0 || this.isImageSave === true) {
          this.callback()

          console.log("imageToServerCallback_1")
          return
        }

        this.isImageSave = true
        let self = this

        this.$BaseCommon.restful
          .imageDataUpload(arrSaveData)
          .then((res) => {
            res.forEach((data, index) => {
              arrImage[arrSaveIndex[index]].src = "/files/" + data.attachmentUrl
            })

            this.$emit("input", this.htmlForEditor)

            console.log("imageToServerCallback_2")
            self.isImageSave = false
            self.callback()
          })
          .catch((e) => {
            console.log(e)

            self.callback()
            console.log("imageToServerCallback_3")
          })
      } catch (e) {
        this.callback()
        console.log("imageToServerCallback_4")
      }
    },
    onEditChange() {
      console.log("onEditChange")
    },
    onEditorFocus(quill) {
      console.log("editor focus!", quill)
    },

    onSelectionChange(range) {
      console.log("selection change!", range)
    },
    handleImageAdded: function (file, Editor, cursorLocation, resetUploader) {
      // An example of using FormData
      // NOTE: Your key could be different such as:
      // formData.append('file', file)
      var formData = new FormData()
      formData.append("image", file)
      console.log(file)
      this.encodeBase64ImageFile(file).then((data) => {
        console.log("base64", data)
        Editor.insertEmbed(cursorLocation, "image", data)
        resetUploader()
        this.$emit("onEditTextChange", this.htmlForEditor)
      })
    },
    encodeBase64ImageFile(image) {
      return new Promise((resolve, reject) => {
        let reader = new FileReader()
        // convert the file to base64 text
        reader.readAsDataURL(image)
        // on reader load somthing...
        reader.onload = (event) => {
          resolve(event.target.result)
        }
        reader.onerror = (error) => {
          reject(error)
        }
      })
    },
    callback() {
      if (this.emptyMessage === "") {
        this.$emit("imageToServerCallback")
        return
      }

      this.htmlForEditor =
        this.htmlForEditor === undefined ? "" : this.htmlForEditor
      if (this.htmlForEditor !== "") {
        this.$emit("imageToServerCallback")
      }
      this.changeError()
    },
    changeError() {
      if (this.emptyMessage !== "") {
        if (this.htmlForEditor === "")
          this.$refs.editor.classList.add("editError")
        else this.$refs.editor.classList.remove("editError")
      }
    },
  },
  beforeDestroy() {
    console.log("beforeDestroy")
    this.$EventBus.$off("imageToServer")
  },
}
</script>
