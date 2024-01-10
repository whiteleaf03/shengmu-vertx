<template>
  <resize-listener class="scale-panel" @resize="dispatchResizeEvent">
    <slot></slot>
  </resize-listener>
</template>

<script>

// 搜集VaeScalePanelItem
// import _ from "lodash";
const _ = {}
const collectPanelItem = component => {
  let result = []
  const isTargetItem = element => {
    const name = element.$vnode.componentOptions.Ctor.options.name
    return name && _.startsWith(name, 'ScalePanelItem')
  }
  const recurTree = src => {
    if (Array.isArray(src)) {
      src.forEach(e => {
        if (isTargetItem(e)) {
          result.push(e)
        } else if (Array.isArray(e.$children)) {
          recurTree(e.$children)
        }
      })
    }
  }
  recurTree(component?.$children)
  return result
}
import ResizeListener from "@/components/resize-listener/resize-listener";
export default {
  name: 'ScalePanel',
  components:{
    ResizeListener
  },
  props: {
    // 是否禁用 scale
    disabled: {
      type: Boolean,
      default: false
    },
    // 设计稿高度，单位px
    designHeight: {
      type: Number,
      default: 1080
    },
    // 设计稿宽度，单位px
    designWidth: {
      type: Number,
      default: 1920
    },
  },
  provide() {
    return { dispatchResizeEvent: this.dispatchResizeEvent }
  },
  methods: {
    // 向下级组件分发resize事件
    dispatchResizeEvent(el) {
      const { clientHeight, clientWidth } = el || this.$el
      const items = collectPanelItem(this)
      for (let i = 0; i < items.length; i++) {
        const item = items[i]
        const name = item.$vnode.componentOptions.Ctor.options.name
        if (name && _.startsWith(name, 'ScalePanelItem')) {
          const resizeFunciton = item.handlePanelResize
          if (resizeFunciton && _.isFunction(resizeFunciton)) {
            resizeFunciton({
              clientHeight,
              clientWidth,
              designHeight: this.designHeight,
              designWidth: this.designWidth,
              disabled: this.disabled
            })
          }
        }
      }
    }
  },
  mounted() {
    this.dispatchResizeEvent(this.$el)
  }
}
</script>

<style lang="less">
.scale-panel {
  height: 100%;
  width: 100%;
  position: relative;
}
</style>
