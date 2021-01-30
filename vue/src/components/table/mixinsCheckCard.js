export const checkCard = {
    methods: {
        checkItem(bannerList, checkInfo) {
            const len = bannerList.length - 1

            const beforeIndex = checkInfo.index - 1 < 0 ? 0 : checkInfo.index - 1
            const afterIndex = checkInfo.index + 1 > len ? len : checkInfo.index + 1

            if (checkInfo.check === false) {
                if (
                    bannerList[beforeIndex].check === false &&
                    bannerList[afterIndex].check === false
                ) {
                    bannerList.forEach((banner, i) => {
                        if (checkInfo.index != i) {
                            banner.check = false
                        }
                    })
                }

                return bannerList
            }

            if (checkInfo.check === true) {
                if (
                    bannerList[beforeIndex].check === true &&
                    checkInfo.index != 0
                ) {
                    bannerList.slice(checkInfo.index + 1).forEach((banner) => {
                        banner.check = false
                    })
                }

                return bannerList
            }
        },
    },
}