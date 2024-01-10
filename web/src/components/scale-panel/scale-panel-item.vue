<template>
  <div class="scale-panel-item" :class="itemClass" :style="itemStyle">
    <slot></slot>
  </div>
</template>

<script>
// import _ from "lodash";
const _ = {}
export default {
  name: 'ScalePanelItem',
  props: {
    // 元素位置
    position: {
      default: 'center',
      validator: function (value) {
        return ['fullscreen', 'center', 'top', 'bottom', 'left', 'right'].indexOf(value) !== -1
      }
    },
    // 是否禁用scale功能
    disabled: {
      type: Boolean,
      default: false
    },
    // 元素的宽度
    width: {
      type: Number,
      default: 0
    },
    // 元素的高度
    height: {
      type: Number,
      default: 0
    },
    // 左边距
    left: {
      type: Number,
      default: 0
    },
    // 右边距
    right: {
      type: Number,
      default: 0
    },
    // 上边距
    top: {
      type: Number,
      default: 0
    },
    // 下边距
    bottom: {
      type: Number,
      default: 0
    },
    // 层级
    zIndex: {
      type: Number,
      default() {
        return this.position == 'fullscreen' ? -1 : 0
      }
    }
  },
  inject: ['dispatchResizeEvent'],
  data() {
    return {
      itemDisabled: false,
      scaleHeight: 1,
      scaleWidth: 1,
      designHeight: 0,
      designWidth: 0
    }
  },
  methods: {
    /**
     * 响应容器尺寸变化
     * @param disabled
     * @param clientHeight
     * @param clientWidth
     * @param designHeight
     * @param designWidth
     */
    handlePanelResize({ disabled, clientHeight, clientWidth, designHeight, designWidth }) {
      this.itemDisabled = disabled || this.disabled
      this.designHeight = designHeight
      this.designWidth = designWidth
      this.scaleHeight = this.itemDisabled ? 1 : clientHeight / designHeight
      this.scaleWidth = this.itemDisabled ? 1 : clientWidth / designWidth
    }
  },
  computed: {
    itemClass() {
      return `is-${this.position}`
    },
    itemStyle() {
      return this[`itemStyle_${this.position}`]
    },
    itemStyle_center() {
      const baseStyle = {
        height: (this.height || this.designHeight) + 'px',
        width: (this.width || this.designWidth) + 'px',
        zIndex: this.zIndex
      }
      if (this.itemDisabled) {
        return baseStyle
      } else {
        const scale = _.min([this.scaleHeight, this.scaleWidth])
        const leftOffset = (this.scaleWidth * this.designWidth - (this.width || this.designWidth)) / 2
        const topOffset = (this.scaleHeight * this.designHeight - (this.height || this.designHeight)) / 2
        return _.assign(baseStyle, {
          transform: `scale(${scale})`,
          left: leftOffset + 'px',
          top: topOffset + 'px',
          position: 'relative'
        })
      }
    },
    itemStyle_fullscreen() {
      return {
        height: '100%',
        width: '100%',
        zIndex: this.zIndex
      }
    },
    itemStyle_left() {
      const height = this.height || this.designHeight
      const top = (this.designHeight - height) / 2 * this.scaleHeight + 'px'
      const baseStyle = {
        height: height + 'px',
        width: (this.width || 100) + 'px',
        left: this.left * this.scaleHeight + 'px',
        top,
        zIndex: this.zIndex
      }
      return _.assign(baseStyle, this.itemDisabled ? {} : {
        transform: `scale(${this.scaleHeight})`,
        transformOrigin: '0% 0%'
      })
    },
    itemStyle_right() {
      const height = this.height || this.designHeight
      const top = (this.designHeight - height) / 2 * this.scaleHeight + 'px'
      const baseStyle = {
        height: height + 'px',
        width: (this.width || 100) + 'px',
        right: this.right * this.scaleHeight + 'px',
        top,
        zIndex: this.zIndex
      }
      return _.assign(baseStyle, this.itemDisabled ? {} : {
        transform: `scale(${this.scaleHeight})`,
        transformOrigin: '100% 0%'
      })
    },
    itemStyle_top() {
      const width = this.width || this.designWidth
      const left = (this.designWidth - width) / 2 * this.scaleWidth + 'px'
      const baseStyle = {
        height: (this.height || 50) + 'px',
        width: width + 'px',
        top: this.top * this.scaleWidth + 'px',
        left,
        zIndex: this.zIndex
      }
      return _.assign(baseStyle, this.itemDisabled ? {} : {
        transform: `scale(${this.scaleWidth})`,
        transformOrigin: '0% 0%'
      })
    },
    itemStyle_bottom() {
      const width = this.width || this.designWidth
      const left = (this.designWidth - width) / 2 * this.scaleWidth + 'px'
      const baseStyle = {
        height: (this.height || 50) + 'px',
        width: width + 'px',
        bottom: this.bottom * this.scaleWidth + 'px',
        left,
        zIndex: this.zIndex
      }
      return _.assign(baseStyle, this.itemDisabled ? {} : {
        transform: `scale(${this.scaleWidth})`,
        transformOrigin: '0% 100%'
      })
    }
  },
  mounted() {
    this.dispatchResizeEvent?.call()
  }
}
</script>

<style lang="less">
.scale-panel-item {
  position: absolute;

  &.is-fullscreen {
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
  }
}
</style>