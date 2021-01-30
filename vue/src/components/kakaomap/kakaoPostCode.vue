<template lang="pug">
    vue-daum-postcode(@complete="complete")
</template>
<script>
export default {
  props: ["name"],
  data() {
    return {
      kakaoMap_name: "kakaoMap",
    }
  },
  created() {
    this.kakaoMap_name = this.name
  },
  methods: {
    complete: function (data) {
      console.log("handleAddress", data)
      this.$emit("kakaoPostCodeCallback", data)
      let fullAddress = data.address
      let extraAddress = ""
      if (data.addressType === "R") {
        if (data.bname !== "") {
          extraAddress += data.bname
        }
        if (data.buildingName !== "") {
          extraAddress +=
            extraAddress !== "" ? `, ${data.buildingName}` : data.buildingName
        }
        fullAddress += extraAddress !== "" ? ` (${extraAddress})` : ""
      }
      console.log(fullAddress) // e.g. '서울 성동구 왕십리로2길 20 (성수동1가)'
    },
  },
}
</script>
