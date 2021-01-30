<template>
  <v-container class="fluid fill-height align-start justify-start">
    <v-form ref="form" v-model="valid" lazy-validation style="width: 100%">
      <v-row>
        <v-col :cols="12">
          <Vuetify-menutitle
            :title="'공지사항 등록'"
            :icon="'fa fa-bullhorn'"
          />
        </v-col>
      </v-row>
      <v-row>
        <v-col class="pb-0">
          <v-text-field
            label="제목"
            placeholder="제목을 입력하세요"
            v-model="formData.title"
            :rules="checkRule.titleRules"
            outlined
            dense
          ></v-text-field>
        </v-col>
      </v-row>
      <v-row class="fill-height align-start justify-start">
        <v-col class="pt-0">
          <htmlEditor2
            ref="editor"
            v-model="formData.content"
            :isImageBaseMode="true"
            @imageToServerCallback="doSave"
          >
          </htmlEditor2>
        </v-col>
      </v-row>
      <v-row>
        <v-col cols="12" md="6">
          <Vuetify-filepond
            :options="allFileOption"
            @uploadCallback="uploadCallback"
            @deleteCallback="deletefileCallback"
          />
        </v-col>
        <v-col>
          <v-card outlined min-height="80px">
            <div class="subtitle-1 pt-5" v-if="fileList.length === 0">
              저장된 파일이 없습니다.
            </div>
            <v-simple-table>
              <template v-slot:default>
                <tbody>
                  <tr v-for="item in fileList" :key="item.fileCode">
                    <td class="text-left">
                      <a @click="fileDdown(item)">
                        {{ item.fileName }}
                        (
                        {{ $BaseCommon.numberInfo.getFileSize(item.fileSize) }}
                        )
                      </a>
                      <v-btn icon color="red" small @click="delFile(item)">
                        <v-icon>fas fa-times-circle</v-icon>
                      </v-btn>
                    </td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>
          </v-card></v-col
        >
      </v-row>
      <v-row>
        <v-col class="text-md-right pa-0 mt-0">
          <Vuetify-button
            class="ma-2"
            :name="'btnList'"
            :btntype="this.$Config.vuetify.btn.type.list"
            :text="'목록'"
            @click="moveList"
          ></Vuetify-button>
          <Vuetify-button
            class="ma-2"
            :name="'btnDel'"
            :btntype="this.$Config.vuetify.btn.type.delete"
            :text="'삭제'"
            @click="doDel"
          ></Vuetify-button>
          <Vuetify-button
            class="ma-2"
            :name="'btnSave'"
            :btntype="this.$Config.vuetify.btn.type.save"
            :text="'저장'"
            @click="doSaveCheck"
          ></Vuetify-button>
        </v-col>
      </v-row>
    </v-form>
  </v-container>
</template>

<script>
export default {
  mounted() {
    this.$BaseCommon.settingInfo.pageInit({ el: this.$el, isEditCheck: true })
  },
  created() {
    let self = this
    //console.log("board_reg")
    this.$EventBus.$on("boardLoad", (obj) => {
      //this.$EventBus.$off('boardLoad')
      console.log("boardLoad", obj)
      //self.formData = Object.assign({}, obj);
      self.doQuery(obj)
    })

    /*this.$EventBus.$on('imageToServerCallback', (obj) => {
            console.log('imageToServerCallback_on', obj)
            this.doSave()
        })*/

    this.formData = this.$route.params
    if (this.formData === null)
      this.formData = {
        boardType: 1,
        title: "",
        content: "",
        boardCode: 0,
      }
    console.log("formData", this.formData)

    this.doQuery(this.formData)
  },
  data() {
    return {
      valid: true,

      formData: {
        boardType: 1,
        title: "",
        content: "",
        boardCode: 0,
        uploadFiles: [],
      },
      fileList: [],
      title: "",
      content: "",
      bImageSave: false,
      checkRule: {
        titleRules: [(v) => !!v || "제목을 입력하세요"],
        contentRules: "내용을 입력하세요",
      },
      allFileOption: {
        tagName: "fileupload2",
        uploadUrl: "/file/fileupload",
      },
      rtnData: "",
    }
  },
  methods: {
    htmlEditor_onTextChange(val) {
      console.log("htmlEditor_onTextChange", val)
    },
    doQuery(cond) {
      this.$BaseCommon.restful.call2("/board/getlist", cond).then((res) => {
        if (res.length == 1) {
          this.formData = {}
          this.formData.boardType = cond.boardType
          this.formData.boardCode = res[0].boardCode
          this.formData.title = res[0].title
          this.formData.content = res[0].content
          console.log("검색완료", this.formData)
          this.doRead()
          this.getFileList()
        }
        console.log(res)
      })
    },
    getFileList() {
      this.$BaseCommon.restful
        .call2("/file/getList", {
          tableName: "t_board",
          tableKey: String(this.formData.boardCode),
        })
        .then((res) => {
          this.fileList = res
        })
    },

    delFile(data) {
      this.$alert("선택한 파일을 정말로 삭제하시겠습니까?", {
        alertType: "del",
      }).then((res) => {
        if (res) {
          this.$BaseCommon.restful.call2("/file/delete", data).then((res2) => {
            console.log(res2)
            this.getFileList()
          })
        }
      })
    },
    doSaveCheck() {
      if (!this.$refs.form.validate()) {
        return
      }
      if (this.formData === null) return

      this.$EventBus.$emit("imageToServer")
    },
    doSave() {
      this.$BaseCommon.restful
        .call("/board/save", this.formData)
        .then((res) => {
          this.$EventBus.$emit("announceChange")
          this.moveList()
          console.log("announce_doSave", res)
        })
        .catch((e) => {
          console.log(e)
        })
    },
    doRead() {
      if (!this.$BaseCommon.memberInfo.getMember().isLogin) return
      if (this.formData === null) return
      this.$BaseCommon.restful
        .call("/board/read", this.formData)
        .then((res) => {
          console.log(res)
          this.$EventBus.$emit("announceChange")
        })
    },
    doDel() {
      if (this.formData === null) return
      this.$alert("데이터를 정말로 삭제하시겠습니까?", {
        alertType: "del",
      }).then((res) => {
        if (res) {
          this.$BaseCommon.restful
            .call("/board/delete", this.formData)
            .then((res1) => {
              this.$EventBus.$emit("announceChange")
              this.moveList()
              console.log(res1)
            })
        }
      })
    },
    moveList() {
      this.$BaseCommon.hostInfo.redirect("/board/list")
    },
    uploadCallback: function (name, res) {
      console.log("uploadCallback >> ", name)
      console.log("uploadCallback >> ", res)
      this.rtnData = JSON.stringify(res)

      let idx =
        this.fileList.length === 0
          ? 1
          : this.fileList[this.fileList.length - 1].idx + 1
      let addFile = {
        idx: idx,
        fileName: res[0].oriFileName,
        fileSize: res[0].fileSize,
        fileUrl: res[0].attachmentUrl,
        fileExt: res[0].fileContentType,
      }
      this.fileList.push(addFile)
      res[0].idx = idx
      this.formData.boardCode =
        this.formData.boardCode === undefined ? 0 : this.formData.boardCode
      if (this.formData.boardCode === 0) {
        this.formData.uploadFiles =
          this.formData.uploadFiles === undefined
            ? []
            : this.formData.uploadFiles
        this.formData.uploadFiles.push(res[0])
      } else {
        this.addFileExec(addFile)
      }
    },
    /** 수정화면에서 파일 등록 시 */
    addFileExec(file) {
      file.tableKey = String(this.formData.boardCode)
      this.$BaseCommon.restful.call("/board/addFile", file).then((res) => {
        console.log(res)
        this.getFileList()
      })
    },
    deletefileCallback: function (res) {
      console.log("deletefileCallback >> ", res)
      this.rtnData = JSON.stringify(res)
    },
    fileDdown(file) {
      //alert("/file/download/" + file.fileUrl)
      this.$BaseCommon.formInfo.filedownload2(file.fileUrl)
    },
  },
  beforeDestroy() {
    console.log("beforeDestroy")
    this.$EventBus.$off("boardLoad")
  },
}
</script>
