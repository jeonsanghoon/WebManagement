<template>
    <v-container>
        <v-row align="start" justify="start">
            <v-col cols="12">
                <Vuetify-menutitle  />
            </v-col>
       
            <v-col cols="12" class="mt-0 pt-0">
        <v-alert :color="this.color" :class="this.btnTextColor + '--text'" icon="check_circle">
          {{ loginmessage }}
        </v-alert>
        <Vuetify-button class="ma-2" :name="'btnConfirm'"    :btntype="this.$Config.vuetify.btn.type.confirm"    :text="'확인'" @btnSave_Click="btnClick(Config.vuetify.btn.type.confirm)"></Vuetify-button>
        

        <Vuetify-button class="ma-2" :name="'btnQuery'"   :btntype="this.$Config.vuetify.btn.type.default" :text="'조회'" @btnQuery_Click="btnClick(Config.vuetify.btn.type.default)"></Vuetify-button>
        <Vuetify-button class="ma-2" :name="'btnSave'"    :btntype="this.$Config.vuetify.btn.type.save"    :text="'저장'" @btnSave_Click="btnClick(Config.vuetify.btn.type.save)"></Vuetify-button>
        
        <Vuetify-button class="ma-2" :name="'btnDel'"     :btntype="this.$Config.vuetify.btn.type.delete"  :text="'삭제'" @btnDel_Click="btnClick(Config.vuetify.btn.type.delete)"></Vuetify-button>
        <Vuetify-button class="ma-2" :name="'btnCancel'"  :btntype="this.$Config.vuetify.btn.type.cancel"  :text="'취소'" @btnCancel_Click="btnClick(Config.vuetify.btn.type.cancel)"></Vuetify-button>
        <Vuetify-button class="ma-2" :name="'btnNew'"     :btntype="this.$Config.vuetify.btn.type.new"     :text="'신규'" @btnNew_Click="btnClick(Config.vuetify.btn.type.new)"></Vuetify-button>
        <Vuetify-button class="ma-2" :name="'btnRequest'" :btntype="this.$Config.vuetify.btn.type.request" :text="'요청'" @btnRequest_Click="btnClick(Config.vuetify.btn.type.request)"></Vuetify-button>
        <Vuetify-button class="ma-2" :name="'btnExcel'"   :btntype="this.$Config.vuetify.btn.type.excel"   :text="'엑셀'" @btnExcel_Click="btnClick(Config.vuetify.btn.type.excel)"></Vuetify-button>
        <Vuetify-button class="ma-2" :name="'btnList'"    :btntype="this.$Config.vuetify.btn.type.list"    :text="'목록'" @btnList_Click="btnClick(Config.vuetify.btn.type.list)"></Vuetify-button>
        <Vuetify-button class="ma-2" :name="'btnPrint'"   :btntype="this.$Config.vuetify.btn.type.print"   :text="'인쇄'" @btnPrint_Click="btnClick(Config.vuetify.btn.type.print)"></Vuetify-button>

        <Buttons-default/>
      
            </v-col>
        </v-row>
    </v-container>
</template>
<script>

export default {
    data(){
        return{
            loginmessage : "",
            color : "white",
            btnTextColor:"white",
            Config:this.$Config
        }
    },
    methods:{
       btnClick(btnType){
   
            this.color = this.$Config.vuetify.btn[btnType].color
            this.btnTextColor = "white"
            switch(btnType)
            {
                case "delete":
                    this.loginmessage = '삭제버튼이 클릭되었습니다.'
                    break;
                case "cancel":
                    this.loginmessage = '취소버튼이 클릭되었습니다.'
                    break;
                case "new":
                    this.loginmessage = '신규버튼이 클릭되었습니다.'
                    break;
                case "request":
                    this.loginmessage = '요청버튼이 클릭되었습니다.'
                    break;
                case "save":
                    this.loginmessage = '저장버튼이 클릭되었습니다.'
                    break;
                case "excel":
                    this.loginmessage = '엑셀버튼이 클릭되었습니다.'
                    break;
                 case "list":
                    this.loginmessage = '목록버튼이 클릭되었습니다.'
                    break;
                case "print":
                    this.loginmessage = '인쇄버튼이 클릭되었습니다.'
                    break;
                case "default":
                    this.loginmessage = '조회버튼이 클릭되었습니다.'
                    this.btnTextColor = "black"
                    break;
                default:
                    break;
            }
        }
    }
}
</script>