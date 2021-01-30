<template>
  <vue-dropzone
    ref="myVueDropzone"
    id="dropzone"
    :options="dropzoneOptions"
    :useCustomSlot="true"
    @vdropzone-success="imageUploadSuccess"
    @vdropzone-max-files-exceeded="maxfilesexceeded"
  >
    <div class="dropzone-custom-content">
      <h3 class="dropzone-custom-title">
        Drag and drop
      </h3>
      <div class="subtitle">
        ...or click
      </div>
    </div>
  </vue-dropzone>
</template>
<script>
import vue2Dropzone from 'vue2-dropzone'
import 'vue2-dropzone/dist/vue2Dropzone.min.css'
export default {
  components: {
    vueDropzone: vue2Dropzone
  },
  props: ['options'],
  data () {
    return {
      files: [],
      dropzoneOptions: {
        url: '/file/upload',
        acceptedFiles: 'image/jpeg,image/png,image/gif',
        thumbnailWidth: 300,
        addRemoveLinks: true,
        uploadMultiple: false,
        removeAllFiles: true,
        maxFilesize: 200000,
        maxFiles: 1,
        headers: { 'My-Awesome-Header': 'header value' },
        init: function () {}
      }
    }
  },
  methods: {
    imageUploadSuccess: function (file, response) {
      console.log(file)
      console.log(response)
    
      if (response.length > 0) {
        this.$emit('uploadCallback', this.options.tagName,  response)
        /*this.formData.member_img = response[0].fileName
        this.formData.member_imgsize = file.size*/
      }
    },
    maxfilesexceeded: function (file) {
      try {
   
        this.removeAllFiles()
        this.addFile(file)
      } catch (e) {
        console.log(e)
      }
    }
  }
}
</script>
