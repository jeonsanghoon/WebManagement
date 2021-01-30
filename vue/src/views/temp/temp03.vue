<template>
  <div>
    <h3 class="mt-5">Html 에디터</h3>
    <htmlEditor
      :htmlContent="htmlContent"
      @ontextChange="htmlEditor_onChange"
    ></htmlEditor>

    <h3 class="mt-5">엑셀업로드</h3>
    <p>
      정의한 클래스와 엑셀의 값을 자동으로 매칭(1행은 클래스의 변수명과 같음)
    </p>
    <p>=> 예제 클래스 com.mrc.db.dto.common.t_address_area_excel</p>
    <Vuetify-filepond
      :options="fileuploadOption"
      @uploadCallback="componentCallback_uploadCallback"
      @deleteCallback="componentCallback_deletefileCallback"
    />

    <h3 class="mt-5">엑셀업로드</h3>
    <p>엑셀 데이터 형식에 관계없이 업로드(첫째행 제외)</p>
    <p>
      리턴 : 무조건 com.mrc.db.dto.common.ExcelUploadData 클래스로 리턴
      {F1:'',F2:'',F3:''.... F12:''}
    </p>
    <Vuetify-filepond
      :options="fileuploadOption2"
      @uploadCallback="componentCallback2_uploadCallback"
      @deleteCallback="componentCallback2_deletefileCallback"
    />

    <h3 class="mt-5">동영상파일 업로드</h3>

    <p>
      리턴 : com.mrc.db.dto.common.UploadFiles <br />
      {fileName:'업로드파일명',oriFileName:'원본파일명',fileSize:0,
      fileContentType:'콘텐츠유형',attachmentUrl:'업로드url',thumbNailImage:'썸네일url'}
    </p>
    <Vuetify-filepond
      :options="fileuploadOption3"
      @uploadCallback="componentCallback3_uploadCallback"
      @deleteCallback="componentCallback3_deletefileCallback"
    />
    <v-btn @click="sortClick"> click</v-btn>
  </div>
</template>
<script>
export default {
  data() {
    return {
      htmlContent: "<p>안녕하세요</p>",
      fileuploadOption: {
        tagName: "excelupload",
        uploadUrl: "/file/addressAreaexcelUpload",
        acceptedFileTypes: "excel",
      },
      fileuploadOption2: {
        tagName: "excelupload2",
        uploadUrl: "/file/excelUpload",
        acceptedFileTypes: "excel",
      },

      fileuploadOption3: {
        tagName: "videoUPload",
        uploadUrl: "/file/uploadfilepond",
        acceptedFileTypes: "video",
      },
    }
  },
  created() {},
  methods: {
    sortClick() {
      let list = [5, 7, 1, 2, 6, 3, 8, 9, 1]
      list = this.quickSort(list, 0, list.length - 1)
      console.log(list)
    },
    htmlEditor_onChange: (val) => {
      console.log("htmlEditor_onChange", val)
    },
    componentCallback_uploadCallback: function (name, response) {
      console.log(name)
      console.log(response)
    },
    componentCallback_deletefileCallback: function (res) {
      console.log(res)
    },
    componentCallback2_uploadCallback: function (name, response) {
      console.log(name)
      console.log(response)
    },
    componentCallback2_deletefileCallback: function (res) {
      console.log(res)
    },
    quickSort(list, left, right) {
      let pivot = Math.floor((left + right) / 2)

      let lpos = left
      let rpos = right

      console.log(pivot, lpos, rpos)

      if (left < pivot - 1) this.quickSort(list, left, pivot - 1)
      if (pivot < right - 1) this.quickSort(list, pivot, right)
    },

    quickSortDataChange(list, fr, to) {
      let tmp = list[fr]
      list[fr] = list[to]
      list[to] = tmp
      return list
    },
  },
}
</script>
