<template>
  <div>
    <h3 class="mt-5">이미지파일 업로드</h3>

    <p>
      리턴 : com.mrc.db.dto.common.UploadFiles <br />
      {fileName:'업로드파일명',oriFileName:'원본파일명',fileSize:0,
      fileContentType:'콘텐츠유형',attachmentUrl:'업로드url',thumbNailImage:'썸네일url'}
    </p>
    <Vuetify-filepond
      :options="imageUploadOption"
      @uploadCallback="imageupload_uploadCallback"
      @deleteCallback="imageuoload_deletefileCallback"
    />

    <h3 class="mt-5">동영상파일 업로드</h3>

    <p>
      리턴 : com.mrc.db.dto.common.UploadFiles <br />
      {fileName:'업로드파일명',oriFileName:'원본파일명',fileSize:0,
      fileContentType:'콘텐츠유형',attachmentUrl:'업로드url',thumbNailImage:'썸네일url'}
    </p>
    <Vuetify-filepond
      :options="videoUploadOption"
      @uploadCallback="videoUpload_uploadCallback"
      @deleteCallback="videoUpload_deletefileCallback"
    />
    <h3 class="mt-5">엑셀업로드</h3>
    <p>
      일반엑셀파일
      <v-btn
        @click="$BaseCommon.formInfo.filedownload2('202003/202003_test.xls')"
        >예제파일 다운로드(xls파일)</v-btn
      >
    </p>
    <p>리턴 : Map&lt;string,Object&gt; 1로우씩 리턴</p>
    <Vuetify-filepond
      :options="excelUploadOption"
      @uploadCallback="excelUpload_uploadCallback"
      @deleteCallback="excelUpload_deletefileCallback"
    />

    <h3 class="mt-5">대용량 엑셀업로드</h3>
    <p>
      엑셀 첫번째주은 키값
      <v-btn
        @click="$BaseCommon.formInfo.filedownload2('202003/202003_test.xlsx')"
        >예제파일 다운로드(xlsx파일만 가능)</v-btn
      >
    </p>
    <p>리턴 : Map&lt;string,Object&gt; 1로우씩 리턴</p>
    <Vuetify-filepond
      :options="excelUpload2Option"
      @uploadCallback="excelUpload2_uploadCallback"
      @deleteCallback="excelUpload2_deletefileCallback"
    />

    <h3 class="mt-5">위치정보 업데이트(텍스트파일 업데이트)</h3>
    <p>
      엑셀 첫번째주은 키값
      <v-btn
        @click="$BaseCommon.formInfo.filedownload2('202003/entrc_busan.txt')"
        >예제파일 다운로드(utf-8 형식이어야 함)</v-btn
      >
    </p>
    <Vuetify-filepond
      :options="addressLocationOption"
      @uploadCallback="addressLocation_uploadCallback"
      @deleteCallback="addressLocation_deletefileCallback"
    />

    <h3 class="mt-5">모든파일</h3>

    <Vuetify-filepond
      :options="allFileOption"
      @uploadCallback="uploadCallback"
      @deleteCallback="deletefileCallback"
    />

    <v-textarea
      label="결과값"
      auto-grow
      outlined
      rows="5"
      row-height="50"
      style="width: 100%"
      class="mt-5"
      shaped
      :value="rtnData"
    >
    </v-textarea>
  </div>
</template>

<script>
export default {
  data() {
    return {
      rtnData: "",
      imageUploadOption: {
        tagName: "imageUPload",
        uploadUrl: "/file/uploadfilepond",
        acceptedFileTypes: "image",
        isPreview: false,
        params: { isThumbnail: true, size: 150 },
      },
      videoUploadOption: {
        tagName: "videoUPload",
        uploadUrl: "/file/uploadfilepond",
        acceptedFileTypes: "video",
        params: { isThumbnail: true, size: 150 },
      },
      excelUploadOption: {
        tagName: "excelupload",
        uploadUrl: "/file/addressAreaexcelUpload",
        acceptedFileTypes: "application/vnd.ms-excel",
        params: { isThumbnail: false },
      },
      excelUpload2Option: {
        tagName: "excelupload2",
        uploadUrl: "/file/excelUpload",
        acceptedFileTypes:
          "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
        params: { isThumbnail: false },
      },
      addressLocationOption: {
        tagName: "fileLocation",
        uploadUrl: "/file/addressLocationfilepond",
        acceptedFileTypes: "text/plain",
      },
      allFileOption: {
        tagName: "fileupload2",
        uploadUrl: "/file/fileupload",
      },
    }
  },
  methods: {
    imageupload_uploadCallback: function(name, res) {
      console.log("image >> ", name)
      console.log("image >> ", res)
      this.rtnData = "image upload >> " + JSON.stringify(res)
    },
    imageuoload_deletefileCallback: function(res) {
      console.log("image >> ", res)
      this.rtnData = "image Delete >> " + JSON.stringify(res)
    },
    videoUpload_uploadCallback: function(name, res) {
      console.log("video >> ", name)
      console.log("video >> ", res)
      this.rtnData = "video upload >> " + JSON.stringify(res)
    },
    videoUpload_deletefileCallback: function(res) {
      console.log("video Delete >> ", res)
      this.rtnData = JSON.stringify(res)
    },
    excelUpload_uploadCallback: function(name, res) {
      console.log("excelupload >> ", name)
      console.log("excelupload >> ", res)
      this.rtnData = JSON.stringify(res)
    },
    excelUpload_deletefileCallback: function(res) {
      console.log("excelupload >> ", res)
    },
    excelUpload2_uploadCallback: function(name, res) {
      console.log("excelUpload2 >> ", name)
      console.log("excelUpload2 >> ", res)
      this.rtnData = JSON.stringify(res)
    },
    excelUpload2_deletefileCallback: function(res) {
      console.log("excelUpload2 >> ", res)
      this.rtnData = JSON.stringify(res)
    },
    addressLocation_uploadCallback: function(name, res) {
      console.log("addressLocation >> ", name)
      console.log("addressLocation >> ", res)
      this.rtnData = JSON.stringify(res)
    },
    addressLocation_deletefileCallback: function(res) {
      console.log("addressLocation >> ", res)
      this.rtnData = JSON.stringify(res)
    },
    uploadCallback: function(name, res) {
      console.log("uploadCallback >> ", name)
      console.log("uploadCallback >> ", res)
      this.rtnData = JSON.stringify(res)
    },
    deletefileCallback: function(res) {
      console.log("deletefileCallback >> ", res)
      this.rtnData = JSON.stringify(res)
    },
  },
}
</script>
