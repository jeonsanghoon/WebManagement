(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-2d0b90af"],{"30ea":function(t,a,e){"use strict";e.r(a);var o=function(){var t=this,a=t.$createElement,e=t._self._c||a;return e("v-container",[e("v-row",[e("v-col",{attrs:{xs:"12",sm:"12",md:"12"}},[e("h3",{staticClass:"mt-5"},[t._v("동영상파일 업로드")]),e("Vuetify-videoUpload",{attrs:{index:1,isThumbnail:!0},on:{callBack:t.callBack_video}})],1)],1),e("v-row",[e("v-col",{attrs:{xs:"12",sm:"12",md:"12"}},[e("Vuetify-videoUpload",{attrs:{index:2},on:{callBack:t.callBack_video}})],1)],1),e("v-row",[e("v-col",{attrs:{xs:"12",sm:"12",md:"12"}},[e("v-textarea",{attrs:{label:"결과값","auto-grow":"",outlined:"",rows:"1","row-height":"15",value:t.result}})],1)],1),e("v-row",[e("v-col",{attrs:{xs:"12",sm:"12",md:"12",align:"center",justify:"center"}},[e("v-img",{attrs:{src:t.imgSrc,contain:"",width:"150px",height:"150px","aspect-ratio":"1.7"}})],1)],1)],1)},i=[],s={data:function(){return{result:"",imgSrc:""}},created:function(){},methods:{callBack_video:function(t,a){console.log("callback >> ",t,a),this.isShow=!1,this.result=JSON.stringify(t),this.imgSrc=t.pickThumb}}},c=s,r=e("2877"),l=e("6544"),n=e.n(l),d=e("62ad"),u=e("a523"),v=e("adda"),m=e("0fd9"),h=e("a844"),w=Object(r["a"])(c,o,i,!1,null,null,null);a["default"]=w.exports;n()(w,{VCol:d["a"],VContainer:u["a"],VImg:v["a"],VRow:m["a"],VTextarea:h["a"]})}}]);
//# sourceMappingURL=chunk-2d0b90af.js.map