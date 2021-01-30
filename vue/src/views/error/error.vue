<template>
  <div id="notfound">
    <div class="notfound">
      <v-container fluid class="pt-10 ">
        <v-row>
          <v-col cols="12">
            <table class="pc">
              <tr>
                <td class="text-left errorTitle">
                  죄송합니다.<br />
                  요청하신 페이지를 찾을 수 없습니다.
                </td>
                <td class="image">
                  <div >
                  <v-img
                    height="18rem"
                    width="18rem"
                    class="pl-10  ml-3"
                    src="@/assets/images/404.svg"
                  ></v-img>
                  </div>
                </td>
              </tr>
              <tr>
                <td class="content text-left" colspan="2">
                  온닫이에서 페이지가 변경 또는 삭제되었거나 존재하지 않는
                  주소를 입력하셨을 수도 있습니다
                  <br />다시 검색하거나 주소를 확인해 주세요.
                </td>
              </tr>
              <tr height="200px">
                <td colspan="2" class="text-center pt-10">
                   <Vuetify-button
                    rounded
                    class=" button"
                    large
                    width="15rem"
                    :btntype="this.$Config.vuetify.btn.type.confirm"
                    :text="'온닫이 홈으로'"
                    @click="moveHome"
                  ></Vuetify-button>
                </td>
              </tr>
            </table>

            <table class="mobile ">
              <tr>
                <td class="image">
                  <v-img
                    height="12rem"
                    width="12rem"
                    class="image"
                    src="@/assets/images/404.svg"
                  ></v-img>
                </td>
              </tr>
              <tr class="d-lg-none d-xl-none">
                <td class="text-left errorTitle">
                  죄송합니다.<br />
                  요청하신 페이지를 찾을 수 없습니다.
                </td>
              </tr>
              <tr>
                <td class="content text-left pt-4" colspan="2">
                  온닫이에서 페이지가 변경 또는 삭제되었거나  <br />존재하지 않는
                  주소를 입력하셨을 수도 있습니다 <br />
                 다시 검색하거나 주소를 확인해 주세요.
                </td>
              </tr>
              <tr>
                <td class="text-center pt-10">
                   <Vuetify-button
                    rounded
                    class="ma-2 button large"
                    width="12rem"
                    small
                    :btntype="this.$Config.vuetify.btn.type.confirm"
                    :text="'온닫이 홈으로'"
                    @click="moveHome"
                  ></Vuetify-button>
                </td>
              </tr>
            </table>
          </v-col>
        </v-row>
      </v-container>
    </div>
  </div>
</template>
<style scoped lang="scss">
#notfound {
  height: 100%;
  width: 100%;
  line-height: 1.4;
  padding: 0px 15px;
  background-color: rgb(248, 248, 248);
  .notfound {
    position: absolute;
    left: 50%;
    top: 50%;
    width: 100%;
    text-align: left;
    -webkit-transform: translate(-50%, -50%);
    -ms-transform: translate(-50%, -50%);
    transform: translate(-50%, -50%);
  }
  .errorTitle {
    font-size: 2.25rem;
    color:#000000;
    font-weight: bold;
  }
  .image {
      margin-left: auto !important;
      margin-right: auto !important;
     
    }
  .mobile {
    .errorTitle {
      font-size: 1.125rem;
    }
    
  }
 
}
.pc {
  margin-top:10em;
  margin-bottom:auto;
  margin-left: auto !important;
  margin-right: auto !important;
  display: block;
  width: fit-content;
  .image {
    position: relative;
    left:-4.0rem;
    top:-4.5rem;

  }
  .content {
    font-size: 1.25rem!important;
    font-weight: 400;
    position: relative;
    top:-2.5em;    
    color:#938783;
  }
   .button{
    font-size:1.125rem;
  }
}
.mobile {
  max-width:30rem;
  margin-left:auto;
  margin-right:auto;
  display: none;
  width: fit-content;
  .errorTitle{
    font-size:1.5rem;
  }

  .content {
    font-size: 1.125rem!important;
    font-weight: 400;
  }
   .button{
    font-size:1rem;
  }
}
.button{

}
@media only screen and (max-width: 767px) {
  .pc {
    display: none;
  }
  .mobile {
    display: block;
  }
}
</style>
<script>
export default {
  props: {
    code: {
      type: Number,
      default: 404,
    },
    msg: {
      type: String,
      default: "페이지를 찾을 수 없습니다.",
    },
  },
  data() {
    return {
      date: this.$moment(new Date(), "YYYYY-MM-DD"),
    }
  },
  methods: {
    moveHome() {
      this.$router.push("/")
    },
  },
}
</script>
