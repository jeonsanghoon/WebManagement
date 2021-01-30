<template lang="pug">
  div(:loadComplete="loadComplete")
    vue-editor( id="editor" useCustomImageHandler @image-added="handleImageAdded"
                v-model="htmlForEditor" :editorOptions="editorSettings" @focus="onEditorFocus"
                @blur="onEditorBlur"    @selection-change="onSelectionChange" @text-change="onEditorTextChange")
</template>

<script>
import { VueEditor, Quill } from "vue2-editor"
import { ImageDrop } from "quill-image-drop-module"
import ImageResize from "quill-image-resize-module"

Quill.register("modules/imageDrop", ImageDrop)
Quill.register("modules/imageResize", ImageResize)

export default {
  props: {
    htmlContent: {
      type: String,
    },
    content: {
      type: String,
    },
    loadComplete: {
      type: Boolean,
    },
  },
  components: {
    VueEditor,
  },
  created() {
    this.htmlForEditor = this.htmlContent
  },
  data() {
    return {
      htmlForEditor: "",
      htmlBefore: "",
      editorSettings: {
        modules: {
          imageDrop: true,
          imageResize: {},
        },
      },
    }
  },
  watch: {
    loadComplete() {
      if (this.loadComplete === true) {
        this.htmlForEditor = this.content
      }
    },
  },
  methods: {
    onEditorBlur(quill) {
      this.$emit("ontextChange", this.htmlForEditor, quill)
    },
    onEditorFocus(quill) {
      this.$emit("onFocus", this.htmlForEditor, quill)
    },
    onEditorTextChange(quill) {
      this.$emit("onEditTextChange", this.htmlForEditor, quill)
    },
    onSelectionChange() {},
    handleImageAdded: function (file, Editor, cursorLocation, resetUploader) {
      // base64 형식으로 이미지 추가 후
      // 저장할 때 url 형태로 변환
      this.encodeBase64ImageFile(file).then((data) => {
        Editor.insertEmbed(cursorLocation, "image", data)
        resetUploader()
      })
    },
    encodeBase64ImageFile(image) {
      return new Promise((resolve, reject) => {
        let reader = new FileReader()
        reader.readAsDataURL(image)
        reader.onload = (event) => {
          resolve(event.target.result)
        }
        reader.onerror = (error) => {
          reject(error)
        }
      })
    },
    refHtmlContent(val) {
      this.htmlForEditor = val
    },
  },
}
</script>
