<template>
  <div class="text-center">
    <v-dialog
      v-model="bDialog"
      stent
      persistent
      min-width="300px"
      width="100%"
      height="90%"
    >
      <v-card>
        <v-card-text class="pa-0">
          <div class="dvImageEditor">
            <tui-image-editor
              ref="tuiImageEditor"
              :include-ui="useDefaultUI"
              :options="options"
              @addText="addText"
              @objectMoved="objectMoved"
              @crop="crop"
              @mousedownimageEditor="mousedownimageEditor"
              @objectScaled="objectScaled"
              @redoStackChanged="redoStackChanged"
              @objectActivated="objectActivated"
            >
            </tui-image-editor>
          </div>
        </v-card-text>
        <v-divider></v-divider>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="gray lighten-1" @click="btnConfirm(false)" class="mr-3">
            취소
          </v-btn>
          <v-btn
            color="blue darken-1"
            style="color:white;"
            @click="btnConfirm(true)"
          >
            확인
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
// Load Style Code
import "tui-code-snippet"
import "fabric"
import "tui-image-editor"
import { ImageEditor } from "@toast-ui/vue-image-editor"
// To use the basic UI, the svg files for the icons is required.

import "tui-image-editor/dist/tui-image-editor.css"
export default {
  props: {
    dialog: {
      type: Boolean,
      required: true,
    },
    imagePath: {
      type: String,
      required: true,
    },
  },
  watch: {
    dialog: {
      handler(n, o) {
        console.log("dialog => watch >> ", n, o)
        this.bDialog = n
      },
    },
    imagePath: {
      handler(n, o) {
        console.log(" imagePath => watch >> ", n, o)
        this.imagePath = n
        this.imageSaveData = n
      },
    },
  },
  components: {
    "tui-image-editor": ImageEditor,
  },

  data() {
    const icona = require("tui-image-editor/dist/svg/icon-a.svg")
    const iconb = require("tui-image-editor/dist/svg/icon-b.svg")
    const iconc = require("tui-image-editor/dist/svg/icon-c.svg")
    const icond = require("tui-image-editor/dist/svg/icon-d.svg")
    var whiteTheme = {
      "menu.normalIcon.path": icond,
      "menu.activeIcon.path": iconb,
      "menu.disabledIcon.path": icona,
      "menu.hoverIcon.path": iconc,
      "submenu.normalIcon.path": icond,
      "submenu.activeIcon.path": iconb,

      "common.bisize.width": "251px",
      "common.bisize.height": "21px",
      "common.backgroundColor": "#fff",
      "common.border": "1px solid #c1c1c1",
    }
    return {
      bDialog: false,

      imageSaveData: "",
      useDefaultUI: true,

      options: {
        includeUI: {
          theme: whiteTheme, // or whiteTheme
          menuBarPosition: "bottom",
          menu: [
            "crop",
            "flip",
            "rotate",
            "draw",
            "shape",
            "icon",
            "text",
            "mask",
            "filter",
          ],
          loadImage: {
            path: this.imagePath,
            name: "SampleImage",
          },
          initMenu: "crop",
        },
        cssMaxWidth: "1000",
        cssMaxHeight: "550",
        theme: whiteTheme, // or whiteTheme
        menuBarPosition: "bottom",
        selectionStyle: {
          cornerSize: 50,
          rotatingPointOffset: 100,
        },
      },
    }
  },
  created() {
    this.imageSaveData = this.imagePath
    this.bDialog = this.dialog

    //this.BaseCommon.formInfo.removeElement(".tui-image-editor-header-logo")
    //this.$refs.tuiImageEditor
  },
  mounted() {
    console.log("mounted")
  },
  methods: {
    addText(pos) {
      console.log("onAddText", pos)
    },
    objectActivated(props) {
      console.log("onObjectActivated", props)

      this.imageSaveData = this.$refs.tuiImageEditor
        .invoke("toDataURL")
        .split(",")[1]
    },
    objectMoved(props) {
      console.log("onObjectMoved", props)
    },
    mousedownimageEditor(proevent, originPointer) {
      console.log("mousedownimageEditor", event, originPointer)
    },
    objectScaled(props) {
      console.log("objectScaled", props)
    },
    redoStackChanged(length) {
      console.log("redoStackChanged", length)
    },
    textEditing() {
      console.log("text editing")
    },
    crop(left, top, width, height) {
      console.log("crop", left, top, width, height)
    },
    btnConfirm(bSave) {
      this.bDialog = false

      try {
        if (bSave && this.imageSaveData != "") {
          this.$BaseCommon.restful
            .imageDataUpload(
              this.$refs.tuiImageEditor.invoke("toDataURL").split(",")[1]
            )
            .then((res) => {
              console.log("이미지업로드완료", res)
              this.$emit("imageSaveCallback", res)
              return
            })
        } else {
          console.log(bSave)
        }
      } catch (e) {
        console.log(e)
      }
      this.$emit("imageSaveCallback", null)
    },
  },
}
</script>

<style lang="scss" scoped>
@media screen and (max-width: 768px) {
  .dvImageEditor {
    height: 90vh;
  }

  .tui-image-editor-container .tui-image-editor-header-logo,
  .tui-image-editor-container .tui-image-editor-controls-logo {
    display: none;
  }
  .tui-image-editor-header-buttons {
    float: left;
  }
}

@media screen and (min-width: 769px) {
  .dvImageEditor {
    height: 84vh;
  }
  .tui-image-editor-header-logo {
    display: none;
  }
}

.tui-image-editor-header-logo {
  display: none;
}
</style>
