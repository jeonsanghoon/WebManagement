(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-3e9fba44"],{"1a3e":function(t,e,a){"use strict";var s=a("c5f8"),r=a.n(s);r.a},"1ed1":function(t,e,a){},"21f8":function(t,e,a){"use strict";a.r(e);var s=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("v-form",{ref:"form",model:{value:t.valid,callback:function(e){t.valid=e},expression:"valid"}},[a("v-container",[a("v-row",[a("v-col",{staticClass:"componentPage"},[a("v-card",{staticClass:"text-left pa-10",attrs:{flat:"",outlined:""}},[a("div",{staticClass:"ondaji_font_16_medium_grey_70"},[t._v(" "+t._s(t.logininfo.member.memberName)+" "+t._s(t.$msg.getMsg("W021"))+" ")]),a("div",{staticClass:"ondaji_font_16_thin_grey_70 pt-4"},[t._v(" "+t._s(t.$msg.getMsg("L011"))+" ")]),a("div",{staticClass:"ondaji_font_16_thin_grey_70"},[t._v(" "+t._s(t.$msg.getMsg("L012"))+" ")]),a("div",{staticClass:"ondaji_font_16_thin_grey_70 pt-4"},[t._v(" "+t._s(t.$msg.getMsg("L013"))+" ")]),a("div",{staticClass:"ondaji_font_16_thin_grey_70"},[t._v(" "+t._s(t.$msg.getMsg("L014"))+" ")]),a("div",{staticClass:"ondaji_font_16_medium_brown_80 pt-6 pb-1"},[t._v(" "+t._s(t.$msg.getMsg("W020"))+" ")]),a("v-divider"),a("div",[a("div",{staticClass:"ondaji_font_16_thin_grey_70 pt-1"},[t._v(" "+t._s(t.$msg.getMsg("L009"))+" ")]),a("div",{staticClass:"ondaji_font_16_thin_grey_70"},[t._v(" "+t._s(t.$msg.getMsg("L010"))+" ")])]),a("div",{staticClass:"ondaji_font_16_medium_brown_80 pt-6 pb-1"},[t._v(" "+t._s(t.$msg.getMsg("W018"))+" ("+t._s(t.$msg.getMsg("W022"))+") ")]),a("v-divider"),a("div",[a("v-checkbox",{staticClass:"ondaji_font_16_thin_grey_70",attrs:{"hide-details":"",color:"#3e2723",label:t.$msg.getMsg("W023")},model:{value:t.formData.is_reason1,callback:function(e){t.$set(t.formData,"is_reason1",e)},expression:"formData.is_reason1"}}),a("v-checkbox",{staticClass:"ondaji_font_16_thin_grey_70",attrs:{"hide-details":"",color:"#3e2723",label:t.$msg.getMsg("W024")},model:{value:t.formData.is_reason2,callback:function(e){t.$set(t.formData,"is_reason2",e)},expression:"formData.is_reason2"}}),a("v-checkbox",{staticClass:"ondaji_font_16_thin_grey_70",attrs:{"hide-details":"",color:"#3e2723",label:t.$msg.getMsg("W025")},model:{value:t.formData.is_reason3,callback:function(e){t.$set(t.formData,"is_reason3",e)},expression:"formData.is_reason3"}}),a("v-checkbox",{staticClass:"ondaji_font_16_thin_grey_70",attrs:{color:"#3e2723","hide-details":"",label:t.$msg.getMsg("W026")},model:{value:t.formData.is_reason4,callback:function(e){t.$set(t.formData,"is_reason4",e)},expression:"formData.is_reason4"}}),t.formData.is_reason1||t.formData.is_reason2||t.formData.is_reason3||t.formData.is_reason4||!t.isCheck?t._e():a("div",{staticClass:"v-messages__message error--text pt-1"},[t._v(" "+t._s(t.$msg.formatString("B007","W018"))+" ")])],1),a("div",{staticClass:"ondaji_font_16_medium_brown_80 pt-6 pt-2"},[t._v(" "+t._s(t.$msg.getMsg("W019"))+" ")]),a("v-divider"),a("v-textarea",{staticClass:"ondajiInput pt-2",attrs:{outlined:"",color:"#3e2723",rules:t.checkRule.remarkRule},model:{value:t.formData.remark,callback:function(e){t.$set(t.formData,"remark",e)},expression:"formData.remark"}}),a("div",{staticClass:"ondaji_font_16_medium_brown_80"},[t._v(" "+t._s(t.$msg.getMsg("W004"))+" ")]),a("v-divider"),a("v-text-field",{staticClass:"ondajiInput pt-2",attrs:{type:"password",color:"#3e2723",dense:"",outlined:"",rules:t.checkRule.passwordRules},model:{value:t.formData.memberPw,callback:function(e){t.$set(t.formData,"memberPw",e)},expression:"formData.memberPw"}}),a("v-card-actions",{staticClass:"bottomButtons"},[a("Buttons-default",{attrs:{btnType:"primary",size:40,btnText:"탈퇴하기"},on:{click:t.doSave}})],1)],1)],1)],1)],1)],1)},r=[],i={created:function(){this.logininfo=this.$BaseCommon.memberInfo.getMember()},watch:{formData:function(){this.isCheck=!0}},data:function(){var t=this;return{valid:!0,isCheck:!1,logininfo:null,formData:{is_reason1:!1,is_resaon2:!1,is_reason3:!1,is_reason4:!1,remark:"",memberPw:"",waiting_day:0},checkRule:{remarkRule:[function(e){return!!e||t.$msg.formatString("B002","W017")}],passwordRules:[function(e){return!!e||t.$msg.formatString("B005","W004")}],selectRules:[function(e){return!!t.is_reason1&&!!t.is_reason2&&!!t.is_reason3&&!!e||t.$msg.formatString("B007")}]}}},methods:{doSave:function(){var t=this;this.isCheck=!0,this.$refs.form.validate()&&this.$alert(this.$msg.formatString("B003","W007"),{alertType:"del",yesBtnName:this.$msg.getMsg("W007")}).then((function(e){e?(console.log("콜백처리 True",e),t.afterSave()):console.log("콜백처리 False",e)}))},afterSave:function(){var t=this;this.$BaseCommon.restful.call2("/memberwithdraw/save",this.formData).then((function(e){"Error"===e.resultType&&t.$alert(e.message)})).catch((function(e){t.$alert(e)}))}}},n=i,o=(a("1a3e"),a("5fd0"),a("2877")),c=a("6544"),l=a.n(c),u=a("b0af"),d=a("99d9"),f=a("ac7c"),_=a("62ad"),m=a("a523"),g=a("ce7e"),h=a("4bd4"),v=a("0fd9"),p=a("8654"),b=a("a844"),$=Object(o["a"])(n,s,r,!1,null,"07217ab4",null);e["default"]=$.exports;l()($,{VCard:u["a"],VCardActions:d["a"],VCheckbox:f["a"],VCol:_["a"],VContainer:m["a"],VDivider:g["a"],VForm:h["a"],VRow:v["a"],VTextField:p["a"],VTextarea:b["a"]})},"4bd4":function(t,e,a){"use strict";var s=a("58df"),r=a("7e2b"),i=a("3206");function n(t,e){var a=Object.keys(t);if(Object.getOwnPropertySymbols){var s=Object.getOwnPropertySymbols(t);e&&(s=s.filter((function(e){return Object.getOwnPropertyDescriptor(t,e).enumerable}))),a.push.apply(a,s)}return a}function o(t){for(var e=1;e<arguments.length;e++){var a=null!=arguments[e]?arguments[e]:{};e%2?n(Object(a),!0).forEach((function(e){c(t,e,a[e])})):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(a)):n(Object(a)).forEach((function(e){Object.defineProperty(t,e,Object.getOwnPropertyDescriptor(a,e))}))}return t}function c(t,e,a){return e in t?Object.defineProperty(t,e,{value:a,enumerable:!0,configurable:!0,writable:!0}):t[e]=a,t}e["a"]=Object(s["a"])(r["a"],Object(i["b"])("form")).extend({name:"v-form",inheritAttrs:!1,props:{lazyValidation:Boolean,value:Boolean},data:function(){return{inputs:[],watchers:[],errorBag:{}}},watch:{errorBag:{handler:function(t){var e=Object.values(t).includes(!0);this.$emit("input",!e)},deep:!0,immediate:!0}},methods:{watchInput:function(t){var e=this,a=function(t){return t.$watch("hasError",(function(a){e.$set(e.errorBag,t._uid,a)}),{immediate:!0})},s={_uid:t._uid,valid:function(){},shouldValidate:function(){}};return this.lazyValidation?s.shouldValidate=t.$watch("shouldValidate",(function(r){r&&(e.errorBag.hasOwnProperty(t._uid)||(s.valid=a(t)))})):s.valid=a(t),s},validate:function(){return 0===this.inputs.filter((function(t){return!t.validate(!0)})).length},reset:function(){this.inputs.forEach((function(t){return t.reset()})),this.resetErrorBag()},resetErrorBag:function(){var t=this;this.lazyValidation&&setTimeout((function(){t.errorBag={}}),0)},resetValidation:function(){this.inputs.forEach((function(t){return t.resetValidation()})),this.resetErrorBag()},register:function(t){this.inputs.push(t),this.watchers.push(this.watchInput(t))},unregister:function(t){var e=this.inputs.find((function(e){return e._uid===t._uid}));if(e){var a=this.watchers.find((function(t){return t._uid===e._uid}));a&&(a.valid(),a.shouldValidate()),this.watchers=this.watchers.filter((function(t){return t._uid!==e._uid})),this.inputs=this.inputs.filter((function(t){return t._uid!==e._uid})),this.$delete(this.errorBag,e._uid)}}},render:function(t){var e=this;return t("form",{staticClass:"v-form",attrs:o({novalidate:!0},this.attrs$),on:{submit:function(t){return e.$emit("submit",t)}}},this.$slots.default)}})},"5fd0":function(t,e,a){"use strict";var s=a("1ed1"),r=a.n(s);r.a},c5f8:function(t,e,a){}}]);
//# sourceMappingURL=chunk-3e9fba44.js.map