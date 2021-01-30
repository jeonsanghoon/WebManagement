export const componentsBanner = {
    data() {
        return
    },
    methods: {
        setBtn(arr, info) {
            arr.forEach((btn) => {
                if (info.width != undefined) {
                    btn.width = info.width
                }

                if (info.height != undefined) {
                    btn.height = info.height
                }

                if (info.rounded != undefined) {
                    btn.rounded = info.rounded
                }

                if (info.check != undefined) {
                    btn.check = info.check
                }
            })
        },
    },
}