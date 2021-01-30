(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-24988796"],{"0798":function(t,e,s){"use strict";s("0c18");var i=s("10d2"),r=s("afdd"),n=s("9d26"),a=s("f2e7"),o=s("7560"),c=s("f40d"),l=s("58df"),u=s("d9bd");function d(t,e){var s=Object.keys(t);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(t);e&&(i=i.filter((function(e){return Object.getOwnPropertyDescriptor(t,e).enumerable}))),s.push.apply(s,i)}return s}function m(t){for(var e=1;e<arguments.length;e++){var s=null!=arguments[e]?arguments[e]:{};e%2?d(Object(s),!0).forEach((function(e){h(t,e,s[e])})):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(s)):d(Object(s)).forEach((function(e){Object.defineProperty(t,e,Object.getOwnPropertyDescriptor(s,e))}))}return t}function h(t,e,s){return e in t?Object.defineProperty(t,e,{value:s,enumerable:!0,configurable:!0,writable:!0}):t[e]=s,t}e["a"]=Object(l["a"])(i["a"],a["a"],c["a"]).extend({name:"v-alert",props:{border:{type:String,validator:function(t){return["top","right","bottom","left"].includes(t)}},closeLabel:{type:String,default:"$vuetify.close"},coloredBorder:Boolean,dense:Boolean,dismissible:Boolean,icon:{default:"",type:[Boolean,String],validator:function(t){return"string"===typeof t||!1===t}},outlined:Boolean,prominent:Boolean,text:Boolean,type:{type:String,validator:function(t){return["info","error","success","warning"].includes(t)}},value:{type:Boolean,default:!0}},computed:{__cachedBorder:function(){if(!this.border)return null;var t={staticClass:"v-alert__border",class:h({},"v-alert__border--".concat(this.border),!0)};return this.coloredBorder&&(t=this.setBackgroundColor(this.computedColor,t),t.class["v-alert__border--has-color"]=!0),this.$createElement("div",t)},__cachedDismissible:function(){var t=this;if(!this.dismissible)return null;var e=this.iconColor;return this.$createElement(r["a"],{staticClass:"v-alert__dismissible",props:{color:e,icon:!0,small:!0},attrs:{"aria-label":this.$vuetify.lang.t(this.closeLabel)},on:{click:function(){return t.isActive=!1}}},[this.$createElement(n["a"],{props:{color:e}},"$cancel")])},__cachedIcon:function(){return this.computedIcon?this.$createElement(n["a"],{staticClass:"v-alert__icon",props:{color:this.iconColor}},this.computedIcon):null},classes:function(){var t=m(m({},i["a"].options.computed.classes.call(this)),{},{"v-alert--border":Boolean(this.border),"v-alert--dense":this.dense,"v-alert--outlined":this.outlined,"v-alert--prominent":this.prominent,"v-alert--text":this.text});return this.border&&(t["v-alert--border-".concat(this.border)]=!0),t},computedColor:function(){return this.color||this.type},computedIcon:function(){return!1!==this.icon&&("string"===typeof this.icon&&this.icon?this.icon:!!["error","info","success","warning"].includes(this.type)&&"$".concat(this.type))},hasColoredIcon:function(){return this.hasText||Boolean(this.border)&&this.coloredBorder},hasText:function(){return this.text||this.outlined},iconColor:function(){return this.hasColoredIcon?this.computedColor:void 0},isDark:function(){return!(!this.type||this.coloredBorder||this.outlined)||o["a"].options.computed.isDark.call(this)}},created:function(){this.$attrs.hasOwnProperty("outline")&&Object(u["a"])("outline","outlined",this)},methods:{genWrapper:function(){var t=[this.$slots.prepend||this.__cachedIcon,this.genContent(),this.__cachedBorder,this.$slots.append,this.$scopedSlots.close?this.$scopedSlots.close({toggle:this.toggle}):this.__cachedDismissible],e={staticClass:"v-alert__wrapper"};return this.$createElement("div",e,t)},genContent:function(){return this.$createElement("div",{staticClass:"v-alert__content"},this.$slots.default)},genAlert:function(){var t={staticClass:"v-alert",attrs:{role:"alert"},class:this.classes,style:this.styles,directives:[{name:"show",value:this.isActive}]};if(!this.coloredBorder){var e=this.hasText?this.setTextColor:this.setBackgroundColor;t=e(this.computedColor,t)}return this.$createElement("div",t,[this.genWrapper()])},toggle:function(){this.isActive=!this.isActive}},render:function(t){var e=this.genAlert();return this.transition?t("transition",{props:{name:this.transition,origin:this.origin,mode:this.mode}},[e]):e}})},"0c18":function(t,e,s){},"0c54":function(t,e,s){"use strict";var i=s("4d0e"),r=s.n(i);r.a},"4bd4":function(t,e,s){"use strict";var i=s("58df"),r=s("7e2b"),n=s("3206");function a(t,e){var s=Object.keys(t);if(Object.getOwnPropertySymbols){var i=Object.getOwnPropertySymbols(t);e&&(i=i.filter((function(e){return Object.getOwnPropertyDescriptor(t,e).enumerable}))),s.push.apply(s,i)}return s}function o(t){for(var e=1;e<arguments.length;e++){var s=null!=arguments[e]?arguments[e]:{};e%2?a(Object(s),!0).forEach((function(e){c(t,e,s[e])})):Object.getOwnPropertyDescriptors?Object.defineProperties(t,Object.getOwnPropertyDescriptors(s)):a(Object(s)).forEach((function(e){Object.defineProperty(t,e,Object.getOwnPropertyDescriptor(s,e))}))}return t}function c(t,e,s){return e in t?Object.defineProperty(t,e,{value:s,enumerable:!0,configurable:!0,writable:!0}):t[e]=s,t}e["a"]=Object(i["a"])(r["a"],Object(n["b"])("form")).extend({name:"v-form",inheritAttrs:!1,props:{lazyValidation:Boolean,value:Boolean},data:function(){return{inputs:[],watchers:[],errorBag:{}}},watch:{errorBag:{handler:function(t){var e=Object.values(t).includes(!0);this.$emit("input",!e)},deep:!0,immediate:!0}},methods:{watchInput:function(t){var e=this,s=function(t){return t.$watch("hasError",(function(s){e.$set(e.errorBag,t._uid,s)}),{immediate:!0})},i={_uid:t._uid,valid:function(){},shouldValidate:function(){}};return this.lazyValidation?i.shouldValidate=t.$watch("shouldValidate",(function(r){r&&(e.errorBag.hasOwnProperty(t._uid)||(i.valid=s(t)))})):i.valid=s(t),i},validate:function(){return 0===this.inputs.filter((function(t){return!t.validate(!0)})).length},reset:function(){this.inputs.forEach((function(t){return t.reset()})),this.resetErrorBag()},resetErrorBag:function(){var t=this;this.lazyValidation&&setTimeout((function(){t.errorBag={}}),0)},resetValidation:function(){this.inputs.forEach((function(t){return t.resetValidation()})),this.resetErrorBag()},register:function(t){this.inputs.push(t),this.watchers.push(this.watchInput(t))},unregister:function(t){var e=this.inputs.find((function(e){return e._uid===t._uid}));if(e){var s=this.watchers.find((function(t){return t._uid===e._uid}));s&&(s.valid(),s.shouldValidate()),this.watchers=this.watchers.filter((function(t){return t._uid!==e._uid})),this.inputs=this.inputs.filter((function(t){return t._uid!==e._uid})),this.$delete(this.errorBag,e._uid)}}},render:function(t){var e=this;return t("form",{staticClass:"v-form",attrs:o({novalidate:!0},this.attrs$),on:{submit:function(t){return e.$emit("submit",t)}}},this.$slots.default)}})},"4d0e":function(t,e,s){},"50b4":function(t,e,s){},"6fb9":function(t,e,s){"use strict";var i=s("50b4"),r=s.n(i);r.a},fbe8:function(t,e,s){"use strict";s.r(e);var i=function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("v-form",{ref:"form",model:{value:t.valid,callback:function(e){t.valid=e},expression:"valid"}},[s("v-container",[s("v-row",[s("v-col",{staticClass:"componentPage"},[s("v-card",{staticClass:"mCard",attrs:{flat:"",outlined:"","px-2":""}},[s("v-alert",{staticClass:"alertMsg",attrs:{outlined:""}},[s("div",[t._v("비즈니스(기업·기관)계정으로 전환합니다.")])]),s("div",{staticClass:"ondaji_font_16_bold_brown_80 cardTitle"},[t._v(" 닉네임(로컬러) ")]),s("v-divider",{staticClass:"mx-4"}),s("div",{staticClass:"ondaji_font_16_thin_grey_80 cardContent"},[s("v-container",{staticClass:"pa-0"},[s("v-row",[s("v-col",{staticClass:"py-0",attrs:{cols:"12",md:"4"}},[s("v-text-field",{staticClass:"ondajiInput",attrs:{dense:"",outlined:"",rules:t.checkRule.companyNameRules},model:{value:t.formData.companyName,callback:function(e){t.$set(t.formData,"companyName",e)},expression:"formData.companyName"}})],1),s("v-col",{staticClass:"px-3 pl-md-2 py-0",attrs:{cols:"12",md:"4"}},[s("v-text-field",{staticClass:"ondajiInput",attrs:{dense:"",outlined:"",rules:t.checkRule.branchNameRules},model:{value:t.formData.branchName,callback:function(e){t.$set(t.formData,"branchName",e)},expression:"formData.branchName"}})],1),s("v-col",{staticClass:"px-3 pl-md-2 pt-0",attrs:{cols:"12",md:"4"}},[s("Buttons-default",{attrs:{btnType:"primary",size:40,btnText:"중복확인"},on:{click:t.duplicatenickname}})],1)],1)],1)],1),s("div",{staticClass:"ondaji_font_16_bold_brown_80 cardTitle pt-3 pt-md-0"},[t._v(" 업종 ")]),s("v-divider",{staticClass:"mx-4"}),s("div",{staticClass:"ondaji_font_16_thin_grey_80 cardContent"},[s("Vuetify-selBusinessType",{attrs:{error:t.isBusinessError},model:{value:t.formData.businessType,callback:function(e){t.$set(t.formData,"businessType",e)},expression:"formData.businessType"}})],1),s("div",{staticClass:"ondaji_font_16_bold_brown_80 pt-6 cardTitle"},[t._v(" 사업자 등록번호(고유번호) ")]),s("v-divider",{staticClass:"mx-4"}),s("div",{staticClass:"ondaji_font_16_thin_grey_80 cardContent"},[s("v-container",{staticClass:"px-0 py-0"},[s("v-row",[s("v-col",{staticClass:"py-0",attrs:{cols:"12",md:"4"}},[s("v-text-field",{directives:[{name:"mask",rawName:"v-mask",value:"###-##-#####",expression:"'###-##-#####'"}],staticClass:"ondajiInput",attrs:{dense:"",outlined:"",rules:t.checkRule.businessNumberRules},model:{value:t.businessNumber,callback:function(e){t.businessNumber=e},expression:"businessNumber"}})],1),s("v-col",{staticClass:"pb-3 pt-0 py-md-0",attrs:{cols:"12",md:"4"}},[s("Buttons-default",{attrs:{btnType:"primary",size:40,btnText:"중복확인"},on:{click:t.duplicateBusinessNumber}})],1)],1)],1)],1),s("div",{staticClass:"ondaji_font_16_bold_brown_80 cardTitle pt-md-0"},[t._v(" 비밀번호 ")]),s("v-divider",{staticClass:"mx-4"}),s("div",{staticClass:"ondaji_font_16_thin_grey_80 cardContent"},[s("v-text-field",{staticClass:"ondajiInput",attrs:{type:"password",dense:"",rules:t.checkRule.passwordRules,outlined:""},model:{value:t.formData.memberPw,callback:function(e){t.$set(t.formData,"memberPw",e)},expression:"formData.memberPw"}})],1),s("div",{staticClass:"cardContent"},[s("Buttons-default",{attrs:{btnType:"request",rounded:!1,size:40,btnText:"전환하기"},on:{click:t.doSave}})],1),s("Vuetify-snackbar",{model:{value:t.snackbar,callback:function(e){t.snackbar=e},expression:"snackbar"}})],1)],1)],1)],1)],1)},r=[],n={name:"changebusiness",props:{value:{}},created:function(){this.logininfo=this.$BaseCommon.memberInfo.getMember(),this.formData.companyName=this.logininfo.member.companyName,this.formData.branchName=this.logininfo.member.branchName,this.formData.businessType=this.logininfo.member.businessType,this.businessNumber=this.logininfo.member.businessNumber,this.oriCompanyName=this.companyName,this.oriBranchName=this.branchName},data:function(){return{valid:!0,logininfo:{},oriCompanyName:"",oriBranchName:"",isBusinessError:!1,businessNumber:"",chkDuplicate:!1,chkDuplicateBusinessNumber:!1,formData:{companyName:"",branchName:"",businessType:"",businessNumber:"",memberPw:""},checkRule:{companyNameRules:[function(t){return!!t||"닉네임(로컬러)1을 입력하세요"}],branchNameRules:[function(t){return!!t||"닉네임(로컬러)2를 입력하세요"}],businessNumberRules:[function(t){return!!t||"사업자번호를 입력하세요"}],passwordRules:[function(t){return!!t||"비밀번호를 입력하세요"}]},snackbar:{isView:!1,text:""}}},methods:{duplicatenickname:function(){this.duplicateCheck({companyName:this.formData.companyName,branchName:this.formData.branchName,chkMemberCode:this.logininfo.member.memberCode})},duplicateBusinessNumber:function(){this.formData.businessNumber=this.businessNumber.replaceAll("-",""),this.duplicateCheck({businessNumber:this.businessNumber.replaceAll("-",""),chkMemberCode:this.logininfo.member.memberCode})},duplicateCheck:function(t){var e=this;this.$BaseCommon.restful.call2("/member/dupconfirmcompany",t).then((function(s){1===s?(e.$alert(e.$msg.getMsg("L007")),void 0===t.businessNumber?e.chkDuplicate=!1:e.chkDuplicateBusinessNumber=!0):(e.$alert(e.$msg.getMsg("L006")),void 0===t.businessNumber?(e.oriCompanyName=e.formData.companyName,e.oriBranchName=e.formData.branchName,e.chkDuplicate=!0):(e.oriBusinessNumber=e.formData.businessNumber,e.chkDuplicateBusinessNumber=!0))})).catch((function(t){console.log(t)}))},doSave:function(){var t=this;if(null===this.formData.businessType&&(this.isBusinessError=!0),this.$refs.form.validate())return this.chkDuplicate?void(this.chkDuplicateBusinessNumber?(this.formData.businessNumber=this.businessNumber.replaceAll("-",""),this.$BaseCommon.restful.call2("/membercompany/changeBusiness",this.formData).then((function(e){"Error"===e.resultType?t.$alert(e.message):(console.log(e),t.$BaseCommon.memberInfo.getMyInfo().then((function(e){t.logininfo=e,t.$alert("비즈니스 계정전환이 되었습니다.").then((function(){t.$router.push("/")}))})))}))):this.snackbar={isView:!0,text:"사업자번호 중복체크를 하여 주시기 바랍니다.",title:"<span style='color:red'>경고</span>",isError:!0}):(this.snackbar={isView:!0,text:"닉네임 중복체크를 하여 주시기 바랍니다.",title:"<span style='color:red'>경고</span>",isError:!0},void(this.oriPassword=""))}}},a=n,o=(s("0c54"),s("6fb9"),s("2877")),c=s("6544"),l=s.n(c),u=s("0798"),d=s("b0af"),m=s("62ad"),h=s("a523"),f=s("ce7e"),p=s("4bd4"),b=s("0fd9"),v=s("8654"),y=Object(o["a"])(a,i,r,!1,null,"21871ae4",null);e["default"]=y.exports;l()(y,{VAlert:u["a"],VCard:d["a"],VCol:m["a"],VContainer:h["a"],VDivider:f["a"],VForm:p["a"],VRow:b["a"],VTextField:v["a"]})}}]);
//# sourceMappingURL=chunk-24988796.js.map