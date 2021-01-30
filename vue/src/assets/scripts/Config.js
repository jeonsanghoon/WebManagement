/** Coinfig 정보 */
export const Config = {
  /** 카카오 api 키(admin) */
  /** 기존키*/
  //kakaoApiKey: "0abb521fbfea1e9cccf7057079e82af4",
  kakaoApiKey: '2c17b01e1519ef3f121fa8972ea0773d', //adminkey
  
  isFormChange: false,
  isFormChangeCheck: false,
  Images: {
    get: (url) => {
      return require("../../assets/images/" + url)
    },
  },
  vuetify: {
    btn: {
      type: {
        delete: "delete",
        cancel: "cancel",
        new: "new",
        request: "request",
        save: "save",
        confirm: "confirm",
        excel: "excel",
        list: "list",
        print: "print",
        default: "default",
      },
      delete: {
        color: "red",
        textColor: "white",
      },
      cancel: {
        color: "orange",
        textColor: "white",
      },
      new: {
        color: "teal ",
        textColor: "white",
      },
      request: {
        color: "lime darken-4",
        textColor: "white",
      },
      save: {
        color: "brown darken-4",
        textColor: "white",
      },
      confirm: {
        color: "brown darken-4",
        textColor: "white",
      },
      excel: {
        color: "green",
        textColor: "white",
      },
      list: {
        color: "buttonDefault",
        textColor: "white",
      },
      print: {
        color: "brown",
        textColor: "white",
      },
      default: {
        color: "grey lighten-3",
        textColor: "black",
      },
    },
  },
  Button: {
    type: {
      primary: "primary",
      secondary: "secondary",
      text: "text",
      request: "request",
    },
    size: {
      size40: 40,
      size36: 36,
      size28: 28,
      size26: 26,
      size24: 24,
    },
  },
  msg: {
    checkForm: "변경된 데이터가 있습니다. 페이지를 이동하시겠습니까?",
  },
}
