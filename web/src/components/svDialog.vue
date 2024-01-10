<template>
  <div v-if="visible" class="sv-dialog" :style="{width: width,height:height}">
    <div class="sv-dialog-header">
      <div class="sv-dialog-titlebg">
        <div class="sv-dialog-title-prefix"></div>
        <label>{{ title }}</label>
      </div>
      <div class="sv-dialog-headerbg">
        <div class="sv-dialog-close" @click="close">
        </div>
      </div>
    </div>
    <div class="sv-dialog-content">
      <div class="sv-dialog-linear">
        <slot></slot>
      </div>
      <div class="sv-dialog-bottom">

      </div>
    </div>

  </div>


</template>
<script>
export default {
  name: "sv-dialog",
  props: {
    title: {
      type: String,
      default: '标题栏'
    },
    visible: {
      type: Boolean,
      default: false
    },
    width: {
      type: String,
      default: '30%'
    },
    height: {
      type: String,
      default: '30%'
    }
  },
  methods:{
    // eslint-disable-next-line no-unused-vars
    close(e){
      // eslint-disable-next-line vue/no-mutating-props
      this.visible = false
      this.$emit("close", this.visible)
    }
  }
}

</script>


<style scoped lang="less">
.sv-dialog {
  z-index: 3000;
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);
  filter: drop-shadow(2px 3px 4px black);
}

.sv-dialog-header {
  height: 40px;
  width: 100%;
  position: relative;
  border-bottom: 1px solid #017BEA;

}

.sv-dialog-content {
  width: 100%;
  height: calc(100% - 40px);
  background: rgba(12, 41, 71, 0.85);
  backdrop-filter: blur(2px);
  border-right: 3px solid #0096ff;
}

.sv-dialog-bottom {
  height: 40px;
  width: 100%;
}

.sv-dialog-titlebg {
  position: absolute;
  z-index: 30001;
  background: url(../assets/dialog-title.png) no-repeat;
  background-size: cover;
  width: 300px;
  font-size: var(--h1-zw);
  height: 100%;
  font-family: ysbth;
  filter: brightness(1.1);
  display: flex;
  align-items: center;
  padding-left: 20px;

  label {
    background-image: -webkit-linear-gradient(bottom, #99eeff, #ffffff);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    filter: drop-shadow(2px 4px 6px black);
  }
}

.sv-dialog-headerbg {
  background: var(--color-blue);
  position: absolute;
  width: 100%;
  height: 80%;
  background: linear-gradient(to bottom, #0d3f97, #2a66d0, rgba(42, 102, 208, 0.82));
  bottom: 0;
  border-right: 3px solid #0096ff;
  display: flex;
  justify-content: flex-end;
  align-items: center;
}

.sv-dialog {
  border: 3px solid #0096ff;
  border-top: none;
  border-right: none;
}

.sv-dialog-linear {
  width: 100%;
  height: 100%;
  background: url(../assets/panel-bg.png);
  background-repeat: no-repeat;
  background-size: cover;
}

.sv-dialog-close {
  width: 30px;
  height: 30px;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer
}
.sv-dialog-close:before{
  content: '';
  width: 8px;
  height: 1px;
  border-top: 3px solid #ffffff;
  display: block;
  transform: skewY(42deg);
  position: relative;
  left: -1.5px;
  top: 1.3px;

}
.sv-dialog-close:after{
  content: '';
  width: 8px;
  height: 1px;
  border-top: 3px solid #ffffff;
  display: block;
  transform: skewY(317deg);
  position: relative;
  left: -10px;
  top:1px
}
.sv-dialog-close:hover{
  color: var(--color-yellow);
}
.sv-dialog-title-prefix{
  width: 25px;
  height: 20px;
  background: url(../assets/xiushi.png);
  background-repeat: no-repeat;
  background-size: 70% ;
}

</style>