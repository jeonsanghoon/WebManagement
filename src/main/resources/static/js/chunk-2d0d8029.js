(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d0d8029"],{"78f8":function(l,o,e){"use strict";e.r(o);var a=function(){var l=this,o=l.$createElement,e=l._self._c||o;return e("div",[e("h3",{staticClass:"mt-5"},[l._v("Html 에디터")]),e("htmlEditor",{attrs:{htmlContent:l.htmlContent},on:{ontextChange:l.htmlEditor_onChange}}),e("h3",{staticClass:"mt-5"},[l._v("엑셀업로드")]),e("p",[l._v(" 정의한 클래스와 엑셀의 값을 자동으로 매칭(1행은 클래스의 변수명과 같음) ")]),e("p",[l._v("=> 예제 클래스 com.mrc.db.dto.common.t_address_area_excel")]),e("Vuetify-filepond",{attrs:{options:l.fileuploadOption},on:{uploadCallback:l.componentCallback_uploadCallback,deleteCallback:l.componentCallback_deletefileCallback}}),e("h3",{staticClass:"mt-5"},[l._v("엑셀업로드")]),e("p",[l._v("엑셀 데이터 형식에 관계없이 업로드(첫째행 제외)")]),e("p",[l._v(" 리턴 : 무조건 com.mrc.db.dto.common.ExcelUploadData 클래스로 리턴 {F1:'',F2:'',F3:''.... F12:''} ")]),e("Vuetify-filepond",{attrs:{options:l.fileuploadOption2},on:{uploadCallback:l.componentCallback2_uploadCallback,deleteCallback:l.componentCallback2_deletefileCallback}}),e("h3",{staticClass:"mt-5"},[l._v("동영상파일 업로드")]),l._m(0),e("Vuetify-filepond",{attrs:{options:l.fileuploadOption3},on:{uploadCallback:l.componentCallback3_uploadCallback,deleteCallback:l.componentCallback3_deletefileCallback}}),e("v-btn",{on:{click:l.sortClick}},[l._v(" click")])],1)},t=[function(){var l=this,o=l.$createElement,e=l._self._c||o;return e("p",[l._v(" 리턴 : com.mrc.db.dto.common.UploadFiles "),e("br"),l._v(" {fileName:'업로드파일명',oriFileName:'원본파일명',fileSize:0, fileContentType:'콘텐츠유형',attachmentUrl:'업로드url',thumbNailImage:'썸네일url'} ")])}],n={data:function(){return{htmlContent:"<p>안녕하세요</p>",fileuploadOption:{tagName:"excelupload",uploadUrl:"/file/addressAreaexcelUpload",acceptedFileTypes:"excel"},fileuploadOption2:{tagName:"excelupload2",uploadUrl:"/file/excelUpload",acceptedFileTypes:"excel"},fileuploadOption3:{tagName:"videoUPload",uploadUrl:"/file/uploadfilepond",acceptedFileTypes:"video"}}},created:function(){},methods:{sortClick:function(){var l=[5,7,1,2,6,3,8,9,1];l=this.quickSort(l,0,l.length-1),console.log(l)},htmlEditor_onChange:function(l){console.log("htmlEditor_onChange",l)},componentCallback_uploadCallback:function(l,o){console.log(l),console.log(o)},componentCallback_deletefileCallback:function(l){console.log(l)},componentCallback2_uploadCallback:function(l,o){console.log(l),console.log(o)},componentCallback2_deletefileCallback:function(l){console.log(l)},quickSort:function(l,o,e){var a=Math.floor((o+e)/2),t=o,n=e;console.log(a,t,n),o<a-1&&this.quickSort(l,o,a-1),a<e-1&&this.quickSort(l,a,e)},quickSortDataChange:function(l,o,e){var a=l[o];return l[o]=l[e],l[e]=a,l}}},c=n,i=e("2877"),p=e("6544"),d=e.n(p),u=e("8336"),s=Object(i["a"])(c,a,t,!1,null,null,null);o["default"]=s.exports;d()(s,{VBtn:u["a"]})}}]);
//# sourceMappingURL=chunk-2d0d8029.js.map