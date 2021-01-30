export const regCheck = {
    data() {
        return {
            // 경고문구
            nameError: '한글,영문만 입력해주세요.',
            nickError: '한글,영문,숫자만 입력해주세요.',
            emailIdError: '이메일 ID를 입력해주세요.',
            emailInputError: '메일을 선택 혹은 입력해주세요.',
            emailDotError: 'xxx.xxx 형식으로 입력해주세요.',
            passwordError:
                '영문, 숫자, 특수문자 각 1자리 이상을 사용하여 8자리 이상을 입력해주세요.',
            passwordConfirmError: '동일한 비밀번호를 입력해주세요.',
            birthDateError: '생년월일을 선택해주세요.',
            typeError: '업종을 선택해주세요.',
            businessNumberError: '숫자 10자리를 입력해주세요.',
            falseBusinessNumberError: '사업자등록번호 형식이 잘못되었습니다.',
            acceptTermsError: '필수 동의 항목을 모두 동의해주시기 바랍니다.',
        }
    },
    methods: {
        checkNull(val) {
            if (val === '' || val === null || val === undefined) {
                return true
            }
            return false
        },
        checkLen(val, min, max) {
            if (val.length < min || val.length > max) {
                return `${min} - ${max}자로 입력해주세요.`
            }

            return true
        },
        checkName(val) {
            if (
                this.checkNull(val) === true ||
                /^[ㄱ-ㅎ|가-힣|a-z|A-Z]+$/.test(val) === false
            ) {
                return this.nameError
            }

            return this.checkLen(val, 2, 12)
        },
        checkNick(val, errConfirm) {
            if (
                this.checkNull(val) === true ||
                /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9]+$/.test(val) === false
            ) {
                errConfirm.err = false
                return this.nickError
            }

            const err = this.checkLen(val, 2, 12)

            if (err != true) {
                errConfirm.err = false
                return err
            }

            errConfirm.err = true
            return true
        },
        checkEmailId(val, errConfirm) {
            if (this.checkNull(val) === true) {
                errConfirm.idErr = false
                errConfirm.err = false

                return this.emailIdError
            }

            const err = this.checkLen(val, 1, 24)

            if (err != true) {
                errConfirm.idErr = false
                errConfirm.err = false

                return err
            }

            errConfirm.idErr = true
            errConfirm.err = errConfirm.idErr && errConfirm.dotErr

            return true
        },
        checkEmailDot(val, errConfirm, init) {
            if (init === false) {
                return true
            }

            if (this.checkNull(val) === true || /.+\../.test(val) === false) {
                errConfirm.dotErr = false
                errConfirm.err = false

                return this.emailDotError
            }

            const err = this.checkLen(val, 2, 24)

            if (err != true) {
                errConfirm.dotErr = false
                errConfirm.err = false

                return err
            }

            errConfirm.dotErr = true
            errConfirm.err = errConfirm.idErr && errConfirm.dotErr

            return true
        },
        checkPassword(val) {
            if (
                this.checkNull(val) === true ||
                /^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9])/.test(val) === false
            ) {
                return this.passwordError
            }

            return this.checkLen(val, 8, 16)
        },
        checkPasswordConfirm(val, pw, confirm) {
            if (this.checkNull(val) === true) {
                return this.passwordConfirmError
            }
            if (val === pw) {
                confirm.check = true
            } else {
                confirm.check = false
                return this.passwordConfirmError
            }

            return true
        },
        checkbirthDate(val) {
            if (this.checkNull(val) === true) {
                return this.birthDateError
            }

            return true
        },
        checkCompanyName(val, errConfirm) {
            if (
                this.checkNull(val) === true ||
                /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9]+$/.test(val) === false
            ) {
                errConfirm.nameErr = false
                errConfirm.err = false

                return this.nameError
            }

            const err = this.checkLen(val, 2, 12)

            if (err != true) {
                errConfirm.nameErr = false
                errConfirm.err = false

                return err
            }

            errConfirm.nameErr = true
            errConfirm.err = errConfirm.nameErr && errConfirm.placeErr

            return true
        },

        checkPlace(val, errConfirm) {
            if (
                this.checkNull(val) === true ||
                /^[ㄱ-ㅎ|가-힣|a-z|A-Z|0-9]+$/.test(val) === false
            ) {
                errConfirm.placeErr = false
                errConfirm.err = false

                return this.nameError
            }

            const err = this.checkLen(val, 2, 12)

            if (err != true) {
                errConfirm.placeErr = false
                errConfirm.err = false

                return err
            }

            errConfirm.placeErr = true
            errConfirm.err = errConfirm.nameErr && errConfirm.placeErr

            return true
        },
        checkType(val) {
            if (this.checkNull(val) === true) {
                return this.typeError
            }

            return true
        },
        checkBusinessNumber(val, errConfirm) {
            if (this.checkNull(val) === true || val.length != 12) {
                errConfirm.err = false

                return this.businessNumberError
            }

            const err = this.checkTrueBusinessNumber(val)
            
            if(err === true) {
                errConfirm.err = true
            }

            return err
        },
        checkTrueBusinessNumber(val) {
            let valueMap = val.replace(/-/gi, '').split('').map(function(item) {
                return parseInt(item, 10)
            });
        
            if (valueMap.length === 10) {
                let multiply = new Array(1, 3, 7, 1, 3, 7, 1, 3, 5)
                let checkSum = 0;
        
                for (let i = 0; i < multiply.length; ++i) {
                    checkSum += multiply[i] * valueMap[i]
                }
        
                checkSum += parseInt((multiply[8] * valueMap[8]) / 10, 10)

                const res = Math.floor(valueMap[9]) === (10 - (checkSum % 10))

                if(res === true) {
                    return true
                }
            }

            return this.falseBusinessNumberError
        }
    },
}