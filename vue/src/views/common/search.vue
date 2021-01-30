<template>
    <v-container>
    <v-row>
        <v-col>
            <Vuetify-menutitle/>
            조회화면
        </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
     mounted() {
        this.$store.watch(() => {
            this.$store.dispatch('baseStore/setIsShowBar', true)
        })
        }
}
</script>