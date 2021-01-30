<template>
  <v-container fill-height>
    <v-row justify="center" align="center">
      <v-col cols="12" lg="4" sm="6">
        <div id="app">
          <vue-dropzone
            ref="myVueDropzone"
            id="dropzone"
            :options="dropzoneOptions"
            :useCustomSlot="true"
          >
            <div class="dropzone-custom-content">
              <h3 class="dropzone-custom-title">
                Drag and drop to upload content!
              </h3>
              <div class="subtitle">
                ...or click to select a file from your computer
              </div>
            </div>
          </vue-dropzone>
          <v-btn @click="fileDownload">파일다운로드</v-btn>

          <Vuetify-selectsCommon
            :options="selectOption"
          ></Vuetify-selectsCommon>
          <v-menu
            ref="menu"
            :close-on-content-click="false"
            v-model="menu"
            :nudge-right="40"
            :return-value.sync="date"
            lazy
            transition="scale-transition"
            offset-y
            full-width
            min-width="290px"
          >
            <v-text-field
              slot="activator"
              v-model="date"
              label="Picker in menu"
              prepend-icon="event"
              readonly
            ></v-text-field>
            <v-date-picker v-model="date" no-title scrollable>
              <v-spacer></v-spacer>
              <v-btn flat color="primary" @click="menu = false">Cancel</v-btn>
              <v-btn flat color="primary" @click="$refs.menu.save(date)"
                >OK</v-btn
              >
            </v-date-picker>
          </v-menu>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import vue2Dropzone from "vue2-dropzone"
import "vue2-dropzone/dist/vue2Dropzone.min.css"

export default {
  name: "App",
  components: {
    vueDropzone: vue2Dropzone,
  },
  data: function() {
    return {
      dropzoneOptions: {
        url: "/file/upload",
        thumbnailWidth: 300,
        maxFilesize: 2,
        headers: { "My-Awesome-Header": "header value" },
        init: function() {
          this.on("success", function(files, response) {
            console.log(files)
            console.log(response)
          })
        },
      },
      inputOptions: {
        label: "라벨",
        icon: "home",
        rule: "email",
      } /*essential, email */,
      selectOption: { mainCode: "A001", hide: false, label: "권한", value: 10 },
      formData: {
        inputValue: "테스트당",
      },
      date: new Date().toISOString().substr(0, 10),
      menu: false,
    }
  },
  methods: {
    fileDownload() {
      let filename = "b55626b6-c79a-4f09-b62d-0239672c5fb0.zip"
      let arrFile = filename.split(".")
      const url = window.URL.createObjectURL(
        new Blob(["/file/download/" + filename])
      )
      const link = document.createElement("a")
      link.href = url
      link.setAttribute("download", "file." + arrFile[1]) //or any other extension
      document.body.appendChild(link)
      link.click()
      //this.$router.push("/download/6b151d06-e9e2-4ad8-8fdd-f6097528709b.png");
    },
  },
}
</script>
