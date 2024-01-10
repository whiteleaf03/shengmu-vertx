import { addResizeListener, removeResizeListener } from '@/utils/resize-event'
// import _ from "lodash";
const _ = {}
const myProps = {

    // 防抖时间
    debounce: {
        type: Number,
        default: 100
    }

}

const component = {

    name: 'VaeResizeListener',

    props: myProps,

    data() {
        return {
            debounceEmitResize: null
        }
    },

    methods: {

        handleResize({ visible }) {
            if (visible) {
                this.debounceEmitResize()
            }
        }

    },

    created() {
        this.debounceEmitResize = _.debounce(() => {
            this.$emit('resize', this.$el)
        }, this.debounce)
    },

    mounted() {
        addResizeListener(this.$el, this.handleResize)
    },

    beforeDestroy() {
        removeResizeListener(this.$el, this.handleResize)
    },

    render() {
        return (<div class="resize-listener">{this.$slots.default}</div>)
    }
}

export default component