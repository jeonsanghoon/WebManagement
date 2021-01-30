(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-0955da38"],{"3e25":function(t,e,a){"use strict";var r=a("666e"),i=a.n(r);i.a},"4bd4":function(t,e,a){"use strict";var r=a("58df"),i=a("7e2b"),s=a("3206");function n(t,e){var a=Object.keys(t);if(Object.getOwnPropertySymbols){var r=Object.getOwnPropertySymbols(t);e&&(r=r.filter((function(e){return Object.getOwnPropertyDescriptor(t,e).enumerable}))),a.push.apply(a,r)}return a}function o(t){for(var e=1;e<arguments.length;e++){var a=null!=arguments[e]?arguments[e]:{};e%2?n(Object(a),!0).forEach((function(e){l(t,e,a[e])})):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(a)):n(Object(a)).forEach((function(e){Object.defineProperty(t,e,Object.getOwnPropertyDescriptor(a,e))}))}return t}function l(t,e,a){return e in t?Object.defineProperty(t,e,{value:a,enumerable:!0,configurable:!0,writable:!0}):t[e]=a,t}e["a"]=Object(r["a"])(i["a"],Object(s["b"])("form")).extend({name:"v-form",inheritAttrs:!1,props:{lazyValidation:Boolean,value:Boolean},data:function(){return{inputs:[],watchers:[],errorBag:{}}},watch:{errorBag:{handler:function(t){var e=Object.values(t).includes(!0);this.$emit("input",!e)},deep:!0,immediate:!0}},methods:{watchInput:function(t){var e=this,a=function(t){return t.$watch("hasError",(function(a){e.$set(e.errorBag,t._uid,a)}),{immediate:!0})},r={_uid:t._uid,valid:function(){},shouldValidate:function(){}};return this.lazyValidation?r.shouldValidate=t.$watch("shouldValidate",(function(i){i&&(e.errorBag.hasOwnProperty(t._uid)||(r.valid=a(t)))})):r.valid=a(t),r},validate:function(){return 0===this.inputs.filter((function(t){return!t.validate(!0)})).length},reset:function(){this.inputs.forEach((function(t){return t.reset()})),this.resetErrorBag()},resetErrorBag:function(){var t=this;this.lazyValidation&&setTimeout((function(){t.errorBag={}}),0)},resetValidation:function(){this.inputs.forEach((function(t){return t.resetValidation()})),this.resetErrorBag()},register:function(t){this.inputs.push(t),this.watchers.push(this.watchInput(t))},unregister:function(t){var e=this.inputs.find((function(e){return e._uid===t._uid}));if(e){var a=this.watchers.find((function(t){return t._uid===e._uid}));a&&(a.valid(),a.shouldValidate()),this.watchers=this.watchers.filter((function(t){return t._uid!==e._uid})),this.inputs=this.inputs.filter((function(t){return t._uid!==e._uid})),this.$delete(this.errorBag,e._uid)}}},render:function(t){var e=this;return t("form",{staticClass:"v-form",attrs:o({novalidate:!0},this.attrs$),on:{submit:function(t){return e.$emit("submit",t)}}},this.$slots.default)}})},"666e":function(t,e,a){},9303:function(t,e,a){"use strict";a.r(e),a.d(e,"axios",(function(){return l}));var r=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("v-container",{staticClass:"ondijiBackground fullContent",attrs:{"fill-height":""}},[a("v-row",{staticStyle:{"margin-left":"0px","margin-right":"0px"},attrs:{justify:"center",align:"center"}},[a("v-col",{staticClass:"findID pa-0",attrs:{cols:"12",justify:"center",align:"center"}},[a("carousel",{ref:"carousel",attrs:{"per-page":1,indicators:!1,slideOnSwipe:!1,scrollPerPage:!1,touchDrag:!1,mouseDrag:!1,paginationPosition:"top"}},[a("Slide",{attrs:{name:"slide1"}},[a("v-card",{staticClass:"cardForm",attrs:{outlined:""}},[a("v-form",{ref:"form",model:{value:t.valid,callback:function(e){t.valid=e},expression:"valid"}},[a("v-card-title",{staticClass:"card-title"},[a("v-spacer"),a("v-btn",{attrs:{small:"",icon:""},on:{click:t.close}},[a("v-icon",[t._v("fas fa-times")])],1)],1),a("v-card-text",{staticClass:"card-text"},[a("v-container",[a("v-row",[a("v-col",{staticClass:"titleOndaji font-weight-bold",attrs:{cols:"12"}},[t._v(" 비밀번호 재설정 ")])],1),a("v-row",{staticClass:"cardDiscription"},[a("v-col",{staticClass:"caption",attrs:{cols:"12"}},[t._v(" 비밀번호를 잊으셨나요? "),a("br"),t._v("아래의 정보를 입력해 주세요.")])],1),a("v-row",[a("v-col",{staticClass:"card-input",attrs:{cols:"12"}},[a("v-text-field",{directives:[{name:"mask",rawName:"v-mask",value:"XXXXXXX",expression:"'XXXXXXX'"}],staticClass:"text-left body-2",attrs:{label:"이름",width:"50px",placeholder:"이온닫",color:"brown darken-4",rules:t.checkRule.nameRules},on:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.sendAuthEmail(!1)}},model:{value:t.memberName,callback:function(e){t.memberName=e},expression:"memberName"}})],1)],1),a("v-row",[a("v-col",{staticClass:"card-input",attrs:{cols:"12"}},[a("v-input",{staticClass:"pt-0 pb-0"},[a("v-text-field",{staticClass:"text-left body-2",attrs:{label:"이메일",placeholder:"ondaji@ondaji.com",width:"50px",color:"brown darken-4",rules:t.checkRule.emailRules},on:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.sendAuthEmail(!1)}},model:{value:t.email,callback:function(e){t.email=e},expression:"email"}}),a("v-btn",{staticClass:"ml-2 font-weight-bold btnAuth",attrs:{small:"",rounded:"",depressed:"",width:"100px"},on:{click:function(e){return t.sendAuthEmail(!1)}}},[t._v("인증번호받기")])],1)],1)],1)],1)],1),a("v-card-actions",{staticClass:"card-actions_left"},[""!=t.errorMessage?a("span",{staticClass:"red--text caption ",domProps:{innerHTML:t._s(t.errorMessage)}}):t._e()])],1)],1)],1),a("slide",{attrs:{name:"slide2"}},[a("v-card",{staticClass:"cardForm",attrs:{outlined:""}},[a("v-form",{ref:"form2_1",model:{value:t.valid,callback:function(e){t.valid=e},expression:"valid"}},[a("v-card-title",{staticClass:"card-title"},[a("v-spacer"),a("v-btn",{attrs:{small:"",icon:""},on:{click:t.close}},[a("v-icon",[t._v("fas fa-times")])],1)],1),a("v-card-text",{staticClass:"card-text"},[a("v-container",[a("v-row",[a("v-col",{staticClass:"titleOndaji font-weight-bold",attrs:{cols:"12"}},[t._v(" 비밀번호 재설정 ")])],1),a("v-row",{staticClass:"cardDiscription"},[a("v-col",{staticClass:"caption",attrs:{cols:"12"}},[t._v("인증번호가 "),a("b",[t._v(t._s(t.memberName))]),t._v("님의 메일("),a("strong",{staticClass:"blue--text"},[t._v(t._s(t.displayEmail))]),t._v(")로 발송되었습니다.")])],1),a("v-row",[a("v-col",{staticClass:"card-input"},[a("v-input",{staticClass:"pt-0 pb-0"},[a("v-text-field",{directives:[{name:"mask",rawName:"v-mask",value:"######",expression:"'######'"}],staticClass:"mr-2",attrs:{label:"인증번호",color:"brown darken-4",placeholder:"6자리 숫자",disabled:t.isDisableAuthNumer,required:"",rules:t.checkRule.authNumberRules,"error-messages":t.errorMessage2},model:{value:t.authNumber,callback:function(e){t.authNumber=e},expression:"authNumber"}}),a("span",{staticClass:"pa-2 timer",domProps:{textContent:t._s(t.nTimer)}}),a("v-btn",{staticClass:"font-weight-bold btnAuth",attrs:{small:"",rounded:"",depressed:"",width:"110px"},on:{click:function(e){return t.sendAuthEmail(!0)}}},[t._v("인증번호 재발송")])],1)],1)],1),a("v-row",[a("v-col",{staticClass:"card-input pt-5"},[a("span",{staticClass:"caption"},[a("v-tooltip",{attrs:{right:""},scopedSlots:t._u([{key:"activator",fn:function(e){var r=e.on,i=e.attrs;return[a("v-icon",t._g(t._b({attrs:{small:""},on:{click:function(e){t.tooltipshow=!t.tooltipshow}}},"v-icon",i,!1),r),[t._v("fas fa-question-circle")]),t._v(" 메일이 오지 않나요? ")]}}]),model:{value:t.tooltipshow,callback:function(e){t.tooltipshow=e},expression:"tooltipshow"}},[a("div",{staticClass:"caption pl-0 pr-4 pt-0"},[t._v(" 온닫이가 발송한 메일이 스팸 메일로 "),a("br"),t._v(" 분류된 것은 아닌지 확인해 주세요. ")]),a("div",{staticClass:"caption pt-2 pr-4"},[t._v(" 스팸 메일함에도 메일이 없다면 "),a("br"),t._v("‘인증번호 재발송’을 눌러 주세요. ")])]),a("br")],1)])],1)],1)],1),a("v-card-actions",{staticClass:"card-actions"},[a("Vuetify-button",{staticClass:"ma-2",attrs:{rounded:"",width:"80px",btntype:this.$Config.vuetify.btn.type.confirm,text:"다음"},on:{click:t.authConfirm}})],1)],1)],1)],1),a("Slide",{attrs:{name:"slide3"}},[a("v-card",{staticClass:"cardForm",attrs:{outlined:""}},[a("v-form",{ref:"form1",model:{value:t.valid,callback:function(e){t.valid=e},expression:"valid"}},[a("v-card-title",{staticClass:"card-title"},[a("v-spacer"),a("v-btn",{attrs:{small:"",icon:""},on:{click:t.close}},[a("v-icon",[t._v("fas fa-times")])],1)],1),a("v-card-text",{staticClass:"card-text"},[a("v-container",[a("v-row",[a("v-col",{staticClass:"titleOndaji font-weight-bold",attrs:{cols:"12"}},[t._v(" 비밀번호 재설정 ")])],1),a("v-row",{staticClass:"cardDiscription"},[a("v-col",{staticClass:"caption",attrs:{cols:"12"}},[t._v("새롭게 사용할 비밀번호를 입력해 주세요.")])],1),a("v-row",[a("v-col",{staticClass:"card-input"},[a("v-text-field",{ref:"memberPw",staticClass:"pt-0",attrs:{label:"새 비밀번호",placeholder:"영문,숫자,특수문자 혼용 8자 이상",type:"password",color:"brown darken-4",required:"",rules:t.checkRule.passwordRules},on:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.login(e)}},model:{value:t.memberPw,callback:function(e){t.memberPw=e},expression:"memberPw"}})],1)],1),a("v-row",[a("v-col",{staticClass:"card-input"},[a("v-text-field",{ref:"memberPwConfirm",staticClass:"pt-0",attrs:{label:"새 비밀번호 확인",placeholder:"동일한 비밀번호를 입력하세요",type:"password",color:"brown darken-4",required:"",rules:t.checkRule.pwdConfirmRules},on:{keyup:function(e){return!e.type.indexOf("key")&&t._k(e.keyCode,"enter",13,e.key,"Enter")?null:t.login(e)}},model:{value:t.memberPwConfirm,callback:function(e){t.memberPwConfirm=e},expression:"memberPwConfirm"}})],1)],1)],1)],1),a("v-card-actions",{staticClass:"card-actions"},[a("Vuetify-button",{staticClass:"ma-2",attrs:{rounded:"",width:"80px",btntype:this.$Config.vuetify.btn.type.confirm,text:"다음"},on:{click:t.changePw}})],1)],1)],1)],1),a("Slide",{attrs:{name:"slide4"}},[a("v-card",{staticClass:"cardForm",attrs:{outlined:""}},[a("v-form",{ref:"form2",model:{value:t.valid,callback:function(e){t.valid=e},expression:"valid"}},[a("v-card-title",{staticClass:"card-title"},[a("v-spacer"),a("v-btn",{attrs:{small:"",icon:""},on:{click:t.close}},[a("v-icon",[t._v("fas fa-times")])],1)],1),a("v-card-text",{staticClass:"card-text"},[a("v-container",[a("v-row",[a("v-col",{staticClass:"titleOndaji font-weight-bold",attrs:{cols:"12"}},[t._v(" 비밀번호 재설정 완료 ")])],1),a("v-row",{staticClass:"cardDiscription"},[a("v-col",{staticClass:"caption",attrs:{cols:"12"}},[a("div",[t._v(" 고객님의 비밀번호가 성공적으로 재설정되었습니다. ")])])],1)],1)],1),a("v-card-actions",{staticClass:"card-actions"},[a("Vuetify-button",{staticClass:"ma-2",attrs:{rounded:"",btntype:this.$Config.vuetify.btn.type.confirm,text:"로그인"},on:{click:t.close}})],1)],1)],1)],1)],1)],1)],1)],1)},i=[],s=a("bc3a"),n=a.n(s),o=a("0a63"),l=n.a,c={components:{Carousel:o["Carousel"],Slide:o["Slide"]},data:function(){var t=this;return{tooltipshow:!1,valid:!0,birthDate:"",authNumber:"",memberCode:0,memberPw:"",memberPwConfirm:"",memberName:"",email:"",displayEmail:"",errorMessage:"",errorMessage2:"",nTimer:"3:00",timer:null,nAuthTime:180,defaultTime:180,isDisableAuthNumer:!0,checkRule:{emailRules:[function(t){return!!t||"이메일을 입력하세요"},function(t){return/.+@.+\..+/.test(t)||"이메일 형식이 맞지 않습니다."}],nameRules:[function(t){return!!t||"이름을 입력하세요"}],passwordRules:[function(t){return!!t||"비밀번호를 입력하세요"},function(t){return/^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{8,16}$/.test(t)||"영문자, 숫자, 특수문자 혼용 8자리 이상을 입력하세요"}],pwdConfirmRules:[function(t){return!!t||"비밀번호를 확인하세요"},function(e){return e===t.memberPw||"동일한 비밀번호를 입력해주세요"}],authNumberRules:[function(t){return 6===t.length||"인증번호(6자리)를 입력하세요"}]}}},created:function(){var t=this.$BaseCommon.memberInfo.getMember().loginId;this.memberId=void 0!=t?t:this.memberId},mounted:function(){this.$BaseCommon.settingInfo.pageInit({el:this.$el,isBar:!1,isTop:!1}),this.$BaseCommon.stringInfo.isNullEmpty(localStorage.memberId)?this.isIDRemember=!1:(this.isIDRemember=!0,this.memberId=localStorage.memberId)},methods:{close:function(){this.$BaseCommon.memberInfo.moveLogin()},sendAuthEmail:function(t){var e=this;this.$refs.form.validate()&&(t=void 0!==t&&t,this.displayEmail=this.$BaseCommon.dataInfo.displayEmail(this.email),this.$BaseCommon.restful.call("/member/resetPasswordEmail",{memberName:this.memberName,memberId:this.email}).then((function(a){console.log(a),"info"==a.resultType.toLowerCase()?(e.memberCode=Number(a.data),e.errorMessage="",e.next(1),clearTimeout(e.timer),e.timer=null,t?e.$alert("인증번호가 이메일로 재발송 되었습니다.",{yesBtnName:"확인"}).then((function(t){console.log(t),e.startTimer()})):e.startTimer()):e.errorMessage=a.message})).catch((function(t){console.log("/member/resetPasswordEmail >> ",t)})))},startTimer:function(){this.nAuthTime=this.defaultTime,this.isDisableAuthNumer=!1,this.authTimeExec()},changePw:function(){if(this.$refs.form1.validate()){var t=this;this.$BaseCommon.restful.call("/member/changepw",{memberCode:this.memberCode,memberPwReg:this.memberPw}).then((function(e){"info"==e.resultType.toLowerCase()?(console.log(e),t.next(3)):console.log("변경에러")})).catch((function(t){console.log(t)}))}},next:function(t){this.$refs.carousel.currentPage=t,this.$refs.carousel.goToPage(t)},authConfirm:function(){var t=this;this.$refs.form.validate()&&(6==this.authNumber.length?this.$BaseCommon.restful.call("/member/memberAuthConfirm",{memberCode:this.memberCode,authNumber:this.authNumber}).then((function(e){console.log(e),"info"==e.resultType.toLowerCase()?(t.next(2),null!==t.timer&&clearTimeout(t.timer),t.errorMessage2=""):t.errorMessage2=e.message})):this.errorMessage2="인증번호 6자리를 입력해 주세요")},authTimeExec:function(){var t=this;this.timer=setTimeout((function(){var e=String(t.nAuthTime%60);e=(1===e.length?"0":"")+e,t.nTimer="".concat(String(Math.floor(t.nAuthTime/60)),":").concat(e),t.nAuthTime>0?t.authTimeExec():(t.isDisableAuthNumer=!0,t.$alert("인증번호를 재발송하여 다시 인증받으세요",{title:"인증시간 초과",yesBtnName:"확인"}).then((function(e){t.nTimer="3:00",console.log(e)}))),t.nAuthTime--}),1e3)}}},u=c,m=(a("3e25"),a("b2a5"),a("2877")),d=a("6544"),f=a.n(d),v=a("8336"),h=a("b0af"),p=a("99d9"),b=a("62ad"),C=a("a523"),w=a("4bd4"),g=a("132d"),y=a("b675"),k=a("0fd9"),x=a("2fa4"),_=a("8654"),P=a("3a2f9"),O=Object(m["a"])(u,r,i,!1,null,"15f571e4",null);e["default"]=O.exports;f()(O,{VBtn:v["a"],VCard:h["a"],VCardActions:p["a"],VCardText:p["b"],VCardTitle:p["c"],VCol:b["a"],VContainer:C["a"],VForm:w["a"],VIcon:g["a"],VInput:y["a"],VRow:k["a"],VSpacer:x["a"],VTextField:_["a"],VTooltip:P["a"]})},b2a5:function(t,e,a){"use strict";var r=a("ed2c"),i=a.n(r);i.a},ed2c:function(t,e,a){}}]);
//# sourceMappingURL=chunk-0955da38.js.map