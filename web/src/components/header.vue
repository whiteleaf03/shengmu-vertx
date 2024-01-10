<template>
  <div class="sv-header">
    <div class="sv-header-left">
      <div class="sv-header-button" :style="{ filter: model == 0 ? 'saturate(1.5)':null}" style="margin-left: 20px"
           @click="buttonClick(0)">全部
      </div>
      <div class="sv-header-button" :style="{ filter: model == 1 ? 'saturate(1.5)':null}" @click="buttonClick(1)">A-B
      </div>
      <div class="sv-header-button" :style="{ filter: model == -1 ? 'saturate(1.5)':null}" @click="buttonClick(-1)">
        B-A
      </div>
    </div>
    <label class="sv-header-title">甬金高速综合看板</label>
    <div class="sv-header-right">
      <div class="sv-tq"></div>

      <div style="margin-right: 15px;">
        <span style="font-size: 18px">14°~ 20°</span>
        <div style="font-size: 12px;color: #b3b6b9  ">
          <span style="margin-right: 10px">浙江省</span>
          <span>晴天</span>
        </div>
      </div>
      <div style=" width: 2px;  height: 20px; background: #546d84; margin-right: 15px;">
      </div>
      <div id="sv-ctime" style="margin-right: 24px;width: 90px;cursor: pointer" @mouseenter="openTimeSet">
        <span style="font-size: 18px">{{ time }}</span>
        <div style="font-size: 12px;color: #b3b6b9  ">
          <span style="margin-right: 10px">{{ date }}</span>
          <span>{{ week }}</span>
        </div>
        <div v-if="showTimeSet" @mouseleave="closeTimeSet" class="sv-time">
          <div class="sv-time-set">
            <div class="sv-time-set-item">
              <div @click="timeChange('-','hour')">
                <div></div>
              </div>
              <input v-model="timeSet.hour"/>
              <div style="transform: rotate(180deg);">
                <div @click="timeChange('+','hour')"></div>
              </div>
            </div>
            <div class="sv-time-set-item">
              <div @click="timeChange('-','minute')">
                <div></div>
              </div>
              <input v-model="timeSet.minute"/>
              <div style="transform: rotate(180deg);">
                <div @click="timeChange('+','minute')"></div>
              </div>
            </div>
            <div class="sv-time-set-item">
              <div @click="timeChange('-','second')">
                <div></div>
              </div>
              <input v-model="timeSet.second"/>
              <div style="transform: rotate(180deg);">
                <div @click="timeChange('+','second')"></div>
              </div>
            </div>
          </div>
          <div class="sv-fixed-time" @click="changeDate($event,'2023-10-24 08:10')">
            2023-10-24 08:10
          </div>
          <div class="sv-time-tools">
<!--            <div style="height: 100%;color: rgb(179, 182, 185);margin-right: 10px">-->
<!--              取消-->
<!--            </div>-->
            <div style="height: 100%;color: #0087dd" @click="changeDate">确定</div>
          </div>
        </div>
      </div>

    </div>
  </div>
</template>
<script>
import dayjs from "dayjs";

const weekday = require('dayjs/plugin/weekday')
dayjs.extend(weekday)
export default {
  name: 'sv-header',
  props:{
    // eslint-disable-next-line vue/require-prop-type-constructor
    time: '',
    // eslint-disable-next-line vue/require-prop-type-constructor
    date: '',
    // eslint-disable-next-line vue/require-prop-type-constructor
    week: '',
    targetDate:{
      type:[Date,Object]
    }
  },
  data() {
    return {
      model: 0,
      showTimeSet: false,
      mouseTarget: {},
      timeSet: {
        hour: 0,
        minute: 0,
        second: 0
      },
      dateType:0
    }
  },
  created() {

  },
  methods: {
    timeChange(type, field) {
      const value = this.timeSet[field]
      let isNoChange = false
      if (type == '+') {
        switch (field) {
          case 'hour':
            isNoChange = value == 23
            break
          case 'minute':
            isNoChange = value == 59
            break
          case 'second':
            isNoChange = value == 59
            break
        }
        if(!isNoChange){
          this.timeSet[field] = value + 1
        }
      } else {
        if(value > 0){
          this.timeSet[field] = value - 1
        }
      }
    },
    // eslint-disable-next-line no-unused-vars
    openTimeSet(e) {
      this.showTimeSet = true
      this.mouseTarget = e.target
      let timeArr =  this.time.split(":")
      this.timeSet.hour = Number(timeArr[0])
      this.timeSet.minute = Number(timeArr[1])
      this.timeSet.second = Number(timeArr[2])
    },
    // eslint-disable-next-line no-unused-vars
    closeTimeSet(e) {
      this.showTimeSet = false
    },
    changeDate(e,fixed){
      let date = dayjs(this.targetDate).format("YYYY-MM-DD")+" "+this.timeSet.hour+":"+this.timeSet.minute+":"+this.timeSet.second
      if(fixed){
        date = fixed
      }
      this.$emit('changeDate',date)
      this.closeTimeSet()
    },
    changeAll(e) {
      this.$emit('changeAll', e)
    },
    dateClick(e) {
      this.$emit('dateClick', e)
    },
    buttonClick(e) {
      this.model = e
      this.$emit('buttonClick', e)

    }
  }
}
</script>
<style scoped lang="less">
.sv-header {
  width: 100%;
  height: 80px;
  background: url(../assets/header/headerbg2.png);
  display: flex;
  align-items: center;
  justify-content: center;
  //background-size: 80%;
  background-size: 1600px;
  background-repeat: no-repeat;
  background-position: 50% 4px;
  font-family: 'ysbth';
}

.sv-header-title {
  font-size: var(--h1);
  background-image: -webkit-linear-gradient(bottom, #99eeff, #ffffff);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.sv-header-left, .sv-header-right {
  flex: 0.5;
  height: 100%;
}

.sv-header-left {
  display: flex;
  font-family: ysbth;
}

.sv-header-right {
  color: #ffffff;
  display: flex;
  align-items: center;
  justify-content: flex-end;
}

.sv-header-left::before {
  content: '';
  position: absolute;
  top: 64px;
  height: 1px;
  width: 34%;
  border-bottom: 1px solid transparent;
  border-image: linear-gradient(to left, var(--title-line-color), transparent) 1;
}

.sv-header-left::after {
  content: '';
  position: absolute;
  height: 50%;
  width: 30px;
  top: 14px;
  left: 34%;
  border-bottom: 1px solid var(--title-line-color);
  transform: skewy(319deg);
}

.sv-header-right::before {
  content: '';
  position: absolute;
  top: 64px;
  right: 0;
  height: 1px;
  width: 34%;
  border-bottom: 1px solid transparent;
  border-image: linear-gradient(to right, var(--title-line-color), transparent) 1;
}

.sv-header-right::after {
  content: '';
  height: 30%;
  width: 30px;
  position: absolute;
  top: 30px;
  right: 34%;
  border-bottom: 1px solid var(--title-line-color);
  transform: skewy(38deg);
}

.sv-tq {
  background: url(../assets/tiqnqi.png);
  height: 24px;
  width: 24px;
  background-size: cover;
  margin-right: 15px;
}

.sv-header-button {
  position: relative;
  z-index: 3000;
  background: url(../assets/headerbtn.png);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 15px;
  padding-top: 2px;
  width: 120px;
  height: 28px;
  margin-right: 0px;
  margin-top: 20px;
  background-size: cover;
  background-position-y: -2px;
  filter: saturate(0.5);
  background-repeat: no-repeat;
  cursor: pointer !important;
}

.sv-header-button:hover {
  filter: saturate(1.5);

}

.sv-time {
  position: absolute;
  width: 200px;
  display: inline-block;
  background: rgba(0, 0, 0, 0.76);
  z-index: 30002;
  right: 20px;
  top: 78px;
  backdrop-filter: blur(3px);
  filter: drop-shadow(0px 0px 1px #0096ff) drop-shadow(0px 0px 1px #0096ff)
}

.sv-time:before {
  content: '';
  position: absolute;
  border-left: 8px solid transparent;
  border-right: 8px solid transparent;
  border-bottom: 10px solid rgba(0, 0, 0, 0.76);
  top: -10px;
  right: 40px;
}

.sv-time-set {
  width: calc(100% - 30px);
  margin: 15px;
  margin-bottom: 0px;
  display: inline-flex;
  justify-content: space-between;
}

.sv-time-set-item {
  width: calc(33.33% - 8px);
  display: inline-flex;
  flex-direction: column;

  input {
    background: rgba(255, 255, 255, 0.1);
    border: none;
    text-align: center;
    outline: none;
    border-radius: 2px;
    color: #ffffff;
    padding: 5px 0px;
    font-weight: 600;
    font-size: 16px;
    user-select: none;
    -ms-user-select: none;
    -webkit-user-select: none;
    -moz-user-select: none;
  }

  div {
    height: 20px;
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: center;

    div {
      width: 0px;
      height: 0px;
      border-left: 6px solid transparent;
      border-right: 6px solid transparent;
      border-bottom: 5px solid #ffffff;
    }
  }
}

.sv-fixed-time {
  padding: 0px 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  margin-bottom: 15px;
  margin-top: 8px;
}

.sv-fixed-time:hover {
  background: #f0f8ff30;
}

.sv-time-tools {
  width: calc(100% - 30);
  display: flex;
  justify-content: flex-end;
  padding: 10px 15px;
  border-top: 1px solid rgba(0, 150, 255, 0.76);
  font-size: 16px;
}
</style>