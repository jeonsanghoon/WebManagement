<template>
    <v-text-field
    class="numberInput"
        v-model="numValue"
        :label="label"
        pattern="\d*"
        align="text-right"
        @keypress="keypressEvent"
        @keyup="keyupEvent"
        @focus.native="focusevent"
        @change="numberChange"
        :filled="filled"
        :rounded="rounded"
        :dense="dense"
        :outlined="outlined"
        :clearable="clearable"
        :solo="solo"
        :rules="rules"
    ></v-text-field>
</template>
<style>
.numberInput input[align='text-right'] {
    text-align: right;
}
</style>
<script>
export default {
    props: {
        label: {
            type: String,
        },
        value: {
            type:Number,
        },
        max: {
            type: Number,
            default: 100000000000,
        },
        min: {
            type: Number,
            default: -100000000000,
        },
        step: {
            type: Number,
            default: 1,
        } /** 체크 */,
        rules: {},
        /**스타일 관련 */
        filled: {
            type: Boolean,
            default: false,
        },
        rounded: {
            type: Boolean,
            default: false,
        },
        dense: {
            type: Boolean,
            default: false,
        },
        outlined: {
            type: Boolean,
            default: false,
        },
        clearable: {
            type: Boolean,
            default: false,
        },
        solo: { type: Boolean, default: false },
     
    },
    created() {
        this.numValue = this.value
    },
    watch: {
        value(val) {
            this.numValue = val
        },
    },
    data() {
        return {
            numValue: null,
        }
    },
    methods: {
        keypressEvent() {

            if (event.keyCode === 38 || event.keyCode === 40) {
                event.returnValue = true
                return
            } else {
                if (event.keyCode !== 45 && (event.keyCode < 48 || event.keyCode > 57)) event.returnValue = false
                if (event.keyCode === 45 && event.target.value.length > 0) event.returnValue = false
                if (event.keyCode === 45 && event.target.value === '-') event.returnValue = false
            }

            /** 최대값 체크 */

            if (event.target.value.replace(/(,)/g, '') >= this.max) {
                event.returnValue = false
            }
            /** 최소값 체크 */

            if (Number(event.target.value.replace(/(,)/g, '')) < this.min) {
                event.returnValue = false
            }
            if (event.target.value === '-' && event.key > 1) event.returnValue = false
        },
        focusevent() {
            event.target.select()
        },
        keyupEvent() {

            if (event.keyCode === 38) {
                event.target.value = Number(event.target.value.replace(/(,)/g, '')) + this.step
            } else if (event.keyCode === 40) {
                event.target.value = Number(event.target.value.replace(/(,)/g, '')) - this.step
            }
            event.target.value = this.max < event.target.value ? this.max : event.target.value
            event.target.value = this.min > event.target.value ? this.min : event.target.value
            this.setValue()
        },
        numberChange() {
            this.setValue()
        },
        setValue() {
            this.numValue = String(event.target.value)
                .replace(/(,)/g, '')
                .replace(/\B(?=(\d{3})+(?!\d))/g, ',')
            this.$emit('input', this.numValue.replace(/(,)/g, ''))
        },
    },
}
</script>
