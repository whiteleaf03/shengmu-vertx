<template>
  <div style="width: 100%;height: 100%">
    <sv-header style="position: absolute" :week="week" :date="date" :time="time" :target-date="targetDate"
               @changeDate="updateDate"
               @dateClick="openDateDialog"
               @buttonClick="changeModel">
    </sv-header>
    <!--    <scale-panel-item position="top" :z-index="1100" style="transition:all 0.5s">-->
    <!--     -->
    <!--    </scale-panel-item>-->
    <div class="sv-main">
      <div class="sv-light"></div>
      <div class="sv-transparent"></div>
      <div class="sv-content">
        <div class="sv-content-left">
          <div class="sv-content-left-top">
            <div class="sv-content-subtitle" style="justify-content: space-between">
              <span class="sv-subtitle-text">速度分布图</span>
              <span style="margin-right: 20px">
                  <label style="font-size: 14px;font-family: ysbth;color: rgb(179, 182, 185)">在途：</label>
                  <label class="sv-count">{{ zt }}</label>
                </span>
            </div>
            <div class="sv-panel sv-avg">
              <div class="sv-progress-item">
                <div class="sv-progress-title">
                  <div class="sv-pt-left">
                    <div class="sv-out-ring sv-ring-red">
                      <div class="sv-point" style="background: #ff4d2c"></div>
                    </div>
                    大于140
                  </div>
                  <span>{{ progressData.oneCount }}</span>
                </div>
                <div class="sv-progress-component">
                  <sv-progress :type="'red'" :percentage="progressData.one"></sv-progress>
                </div>

              </div>
              <div class="sv-progress-item">
                <div class="sv-progress-title">
                  <div class="sv-pt-left">
                    <div class="sv-out-ring sv-ring-yellow">
                      <div class="sv-point" style="background: #ffb33a"></div>
                    </div>
                    120 ~ 140
                  </div>
                  <span>{{ progressData.twoCount }}</span>
                </div>
                <div class="sv-progress-component">
                  <sv-progress type="yellow" :percentage="progressData.two"></sv-progress>
                </div>

              </div>
              <div class="sv-progress-item">
                <div class="sv-progress-title">
                  <div class="sv-pt-left">
                    <div class="sv-out-ring sv-ring-blue">
                      <div class="sv-point"></div>
                    </div>
                    80 ~ 120
                  </div>
                  <span>{{ progressData.threeCount }}</span>
                </div>
                <div class="sv-progress-component">
                  <sv-progress :percentage="progressData.three"></sv-progress>
                </div>

              </div>
              <div class="sv-progress-item">
                <div class="sv-progress-title">
                  <div class="sv-pt-left">
                    <div class="sv-out-ring sv-ring-green">
                      <div class="sv-point"></div>
                    </div>
                    60 ~ 80
                  </div>
                  <span>{{ progressData.fourCount }}</span>
                </div>
                <div class="sv-progress-component">
                  <sv-progress type="green" :percentage="progressData.four"></sv-progress>
                </div>

              </div>
              <div class="sv-progress-item">
                <div class="sv-progress-title">
                  <div class="sv-pt-left">
                    <div class="sv-out-ring sv-ring-blue">
                      <div class="sv-point"></div>
                    </div>
                    40 ~ 60
                  </div>
                  <span>{{ progressData.fiveCount }}</span>
                </div>
                <div class="sv-progress-component">
                  <sv-progress type="blue" :percentage="progressData.five"></sv-progress>
                </div>

              </div>
              <div class="sv-progress-item">
                <div class="sv-progress-title">
                  <div class="sv-pt-left">
                    <div class="sv-out-ring sv-ring-red">
                      <div class="sv-point" style="background: #ff4d2c"></div>
                    </div>
                    低于40
                  </div>
                  <span>{{ progressData.sixCount }}</span>
                </div>
                <div class="sv-progress-component">
                  <sv-progress type="red" :percentage="progressData.six"></sv-progress>
                </div>

              </div>
            </div>
          </div>
          <div class="sv-content-left-bottom" style="overflow: hidden">
            <div class="sv-content-subtitle">
              <span class="sv-subtitle-text">异常历史</span>
            </div>
            <div class="sv-panel">
              <error-history></error-history>
            </div>
          </div>
        </div>
        <div class="sv-content-center">
          <div class="sv-content-core">
            <div class="sv-map-tool">
              <div class="sv-map-button" @click="changeMapType(0)"
                   :style="{background:mapType == 0 ? 'linear-gradient(to right, var(--color-yellow), rgba(255, 179, 58, 0.58), #ff000000)':null}">

                <div class="sv-button-content">
                  车速模式
                </div>
              </div>
              <div class="sv-map-button"
                   :style="{background:mapType == 1 ? 'linear-gradient(to right, var(--color-yellow), rgba(255, 179, 58, 0.58), #ff000000)':null}"
                   @click="changeMapType(1)">
                <div class="sv-button-content">
                  车流模式
                </div>
              </div>
            </div>
            <load-map ref="svMap" @mapClick="showFaultVideo = true"></load-map>
          </div>
          <div class="sv-content-path">
            <div class="sv-content-subtitle sv-subtitle-bg" style="justify-content: space-between">
              <div style="display: inline-flex">
                <div class="sv-subtitle-xiushi" style="margin-left: 20px"></div>
                <span class="sv-subtitle-text" style="margin-left:  10px">
                  路线全览光柱图
                </span>
              </div>

              <span style="margin-right: 20px">Km</span>

            </div>
            <div class="sv-panel-2">
              <div class="sv-car">
                <div class="sv-car-3d">

                </div>
                <div class="sv-car-dz">

                </div>
              </div>
              <div class="sv-all">
                <panorama ref="panorama"></panorama>
                <!--                                  <heat-map></heat-map>-->
              </div>
            </div>
          </div>

        </div>
        <div class="sv-content-right">
          <div class="sv-right-top">
            <div class="sv-content-subtitle">
              <span class="sv-subtitle-text">距离-车速图</span>
            </div>
            <div class="sv-panel">
              <area-char :xdata="areaChar1.xdata" :data="areaChar1.data" :colors="areaChar1.color" uuid="1"></area-char>
            </div>
          </div>
          <div class="sv-right-center">
            <div class="sv-content-subtitle">
              <span class="sv-subtitle-text">距离-车流密度图</span>
            </div>
            <div class="sv-panel">
              <area-char type="bar" :xdata="areaChar2.xdata" :data="areaChar2.data" :colors="areaChar2.color"
                         uuid="2"></area-char>
            </div>
          </div>
          <div class="sv-right-bottom">
            <div class="sv-content-subtitle">
              <span class="sv-subtitle-text">实时视频</span>
            </div>
            <div class="sv-panel" style="margin: 10px 7px;width: calc(100% - 14px);height: calc(100% - 46px - 14px)">

              <video-player class="video-player vjs-custom-skin"
                            ref="videoPlayer"
                            :playsinline="true"
                            :options="playerOptions"
                            @ready="playerReadied"
              >
              </video-player>
            </div>
          </div>

        </div>
      </div>

    </div>

    <sv-dialog title="监控点" :visible.sync="showFaultVideo" @close="showFaultVideo = $event" width="900px"
               height="550px">
      <video-player class="video-player vjs-custom-skin"
                    ref="videoPlayer"
                    :playsinline="true"
                    :options="playerOptions"
                    @ready="playerReadied"
      >
      </video-player>
    </sv-dialog>
  </div>
</template>

<script>
import ScalePanelItem from "@/components/scale-panel/scale-panel-item.vue";
import SvHeader from "@/components/header.vue";
import LoadMap from "@/components/map/LoadMap.vue";
import SvProgress from "@/components/progress.vue";
import AreaChar from "@/components/areaChar.vue";
import Panorama from "@/components/panorama.vue";
import ErrorHistory from "@/components/errorHistory.vue";
import HeatMap from "@/components/heatmap.vue";
import SvDialog from "@/components/svDialog.vue";
import api from "@/utils/api";
import dayjs from "dayjs";

const weekday = require('dayjs/plugin/weekday')
dayjs.extend(weekday);
const vi = require('../assets/video.mp4')
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'index',
  data() {
    return {
      zt: 0,
      showDate: false,
      showFaultVideo: false,
      areaChar1: {
        color: [
          {offset: 0, color: '#15d994'},
          {offset: 0.8, color: 'rgba(21,217,148,0.5)'},
          {offset: 1, color: 'rgba(255, 140, 0, 0)'}
        ],
        data: [60, 80, 101, 84, 90, 100, 91],
        xdata: ['1Km', '2Km', '3Km', '4m', '5Km', '6Km', '7Km']
      },
      areaChar2: {
        color: [
          {offset: 0, color: '#ff4d2c'},
          {offset: 0.8, color: 'rgba(255,77,44,0.28)'},
          {offset: 1, color: 'rgba(255, 140, 0, 0)'}
        ],
        data: [320, 202, 251, 134, 190, 330, 280],
        xdata: ['1Km', '2Km', '3Km', '4m', '5Km', '6Km', '7Km']
      },
      playerOptions: {
        playbackRates: [0.5, 1.0, 1.5, 2.0], //播放速度
        autoplay: true, //如果true,浏览器准备好时开始回放。
        muted: true, // 默认情况下将会消除任何音频。
        loop: true, // 导致视频一结束就重新开始。
        preload: 'auto', // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
        language: 'zh-CN',
        aspectRatio: '16:9', // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
        fluid: true, // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
        sources: [{
          type: "video/mp4",
          src: vi //你的视频地址（必填）
        }],
        poster: "", //你的封面地址
        width: document.documentElement.clientWidth,
        notSupportedMessage: '此视频暂无法播放，请稍后再试',
        controlBar: {
          timeDivider: true,
          durationDisplay: true,
          remainingTimeDisplay: false,
          fullscreenToggle: true
        }
      },
      progressData: {
        one: 0,
        two: 0,
        three: 0,
        four: 0,
        five: 0,
        six: 0,
        oneCount: 0,
        twoCount: 0,
        threeCount: 0,
        fourCount: 0,
        fiveCount: 0,
        sixCount: 0
      },
      week: '',
      date: '',
      time: '',
      weekValues: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
      model: 0,
      targetDate: {},
      mapType: 0,
      interval: {}
    }
  },
  mounted() {
    this.targetDate = new Date()
    this.updateDate()
    this.changeData(0)
  },
  methods: {
    updateDate(newDate) {
      if (newDate) {
        this.targetDate = new Date(newDate)
        this.changeData(this.model, this.targetDate)
      }
      this.week = this.weekValues[dayjs().weekday()]
      this.date = dayjs(this.targetDate).format('MM月DD日')
      this.time = dayjs(this.targetDate).format('HH:mm:ss')
      const vm = this
      window.clearInterval(this.interval)
      this.interval  = setInterval(() => {
        vm.targetDate.setSeconds(new Date().getSeconds() + 1);
        vm.time = dayjs(vm.targetDate).format('HH:mm:ss')
        vm.changeData()
        if(vm.time == '08:11:00'){
          // vm.showFaultVideo = true
          vm.$refs.svMap.createBlink()
          vm.$refs.svMap.createTip()
        }
      }, 1000)
    },
    changeModel(model) {
      this.model = model
    },
    changeMapType(type) {
      this.mapType = type
    },
    changeData(cdate) {
      const vm = this
      let date = this.targetDate;
      if (cdate) {
        date = cdate
      }
      // eslint-disable-next-line no-unused-vars
      const queryDate = dayjs(date).format("YYYY-MM-DDTHH:mm:ss")
      api.getDetail(this, this.model, queryDate).then(response => {
        // eslint-disable-next-line no-unused-vars
        const data = response.data
        console.log('')
      })
      api.getGroup(this, this.model, queryDate).then(response => {
        const data = response.data
        if (data) {
          const xAxis = []
          const values = []
          data.forEach(item => {
            xAxis.push(item.no)
            values.push(item.avgSpeed)
          })
          vm.areaChar1.xdata = xAxis
          vm.areaChar1.data = values

          vm.areaChar2.xdata = xAxis
          vm.areaChar2.data = values
        }
      })
      api.getSpeedSummary(this, this.model, queryDate).then(response => {
        const data = response.data
        if (data) {
          let ratio = 0
          data.forEach(item => {
            ratio = item.pct * 100
            switch (item.label) {
              case "大于130" :
                vm.progressData.one = ratio
                vm.progressData.oneCount = item.count
                break
              case "120-130" :
                vm.progressData.two = ratio
                vm.progressData.twoCount = item.count
                break
              case "80-100" :
                vm.progressData.three = ratio
                vm.progressData.threeCount = item.count
                break
              case "60-80" :
                vm.progressData.four = ratio
                vm.progressData.fourCount = item.count
                break
              case "小于60":
                vm.progressData.five = ratio
                vm.progressData.fiveCount = item.count
                break
              case "速度0" :
                vm.progressData.six = ratio
                vm.progressData.sixCount = item.count
                break
            }
          })
        }
      })
      api.getGeoMapData(this, this.model, queryDate).then(response => {
        const data = response.data
        if (data) {
          let zt = 0
          data.forEach(item => {
            zt += item.count
          })
          vm.zt = zt
          const field = vm.mapType == 0 ?'pct_speed':'pct_count'
          vm.$refs.svMap.changeData(data,field)
          vm.$refs.panorama.changeData(data,field)
        }
      })
    },
    // eslint-disable-next-line no-unused-vars
    openDateDialog(e) {
      this.showDate = true
    },
    playerReadied(e) {
      console.log(e)
    },
    refresh() {
      this.progressData.one = 40
      this.progressData.two = 20
    }
  },
  components: {
    // eslint-disable-next-line vue/no-unused-components
    SvDialog,
    // eslint-disable-next-line vue/no-unused-components
    HeatMap,
    ErrorHistory,
    // eslint-disable-next-line vue/no-unused-components
    Panorama,
    AreaChar,
    // eslint-disable-next-line vue/no-unused-components
    SvProgress,
    // eslint-disable-next-line vue/no-unused-components
    LoadMap,
    SvHeader,
    // eslint-disable-next-line vue/no-unused-components
    ScalePanelItem,


  }
}
</script>
<style lang="less">
:root {
  --rw-width: 400px;
  --h1: 30px;
  --h2: 20px;
  --h1-zw: 18px;
  --h1-cw: 14px;
  --title-line-color: #3b8bf7;
  --subtitle-height: 40px;
  --color-yellow: #ffb33a;
  --color-red: #ff4d2c;
  --color-blue: #0a7ffe;
  --color-d-bule: #15bbd9;
  --color-green: #8cfdd5
}

.sv-main {
  height: 100vh;
  background: url(../assets/header/dq.png);
  background-size: cover;
  background-repeat: no-repeat;
  background-position-x: 60%;
  background-position-y: 10%;
}

.sv-container {
  width: 100%;
  height: calc(100% - 80px);
  top: 80px;
  background: aqua;
  position: absolute;

}

.sv-content {
  height: calc(100% - 60px);
  width: 100%;
  display: flex;
  font-family: 'alibaba45';
}

.sv-content-center {
  flex: 1;
  display: flex;
  flex-direction: column;
}


.sv-content-left {
  width: var(--rw-width);
  height: 100%;
  display: flex;
  flex-direction: column;
}

.sv-content-right {
  width: var(--rw-width);
  height: 100%;
  display: flex;
  flex-direction: column;
}

.sv-transparent {
  height: 60px;
  width: 100%
}

.sv-light {
  position: absolute;
  width: 800px;
  height: 100px;
  background: url(../assets/header/gx.png);
  background-repeat: no-repeat;
  background-position: 50% -10px;
  z-index: 1100;
  left: 50%;
  transform: translateX(-50%);
}

.sv-content-left-top {
  height: 350px;
  width: calc(100% - 40px);
  background: url(../assets/panel-bg.png);
  border: 1px solid #0096ff;
  border-radius: 5px;
  margin: 20px;
}

.sv-content-left-bottom {
  flex: 1;
  width: calc(100% - 40px);
  background: url(../assets/panel-bg.png);
  background-size: cover;
  border: 1px solid var(--title-line-color);
  border-radius: 5px;
  margin: 20px;
  margin-top: 0px;
}

.sv-content-subtitle {
  display: flex;
  align-items: center;
  font-size: 16px;
  height: var(--subtitle-height);
  width: 100%;
  background: url(../assets/subtitle.png);
  background-size: 110%;
  background-repeat: no-repeat;
  background-position-y: 5px;
}

.sv-subtitle-bg {
  background: linear-gradient(to top, #095fb7, #031d33e8) !important;
  border-bottom: 2px solid #0d88d9f2;
}


.sv-panel {
  width: calc(100% - 40px);
  height: calc(100% - var(--subtitle-height) - 40px);
  margin: 20px;
}

.sv-subtitle-text {
  margin-left: 55px;
  background-image: -webkit-linear-gradient(bottom, #99eeff, #ffffff);
  background-clip: text;
  -webkit-text-fill-color: transparent;
  filter: drop-shadow(2px 4px 6px black);
  cursor: default;
  font-family: ysbth;
  font-size: 18px;
}

.sv-right-top {
  background: url(../assets/panel-bg.png);
  background-size: cover;
  border: 1px solid var(--title-line-color);
  border-radius: 5px;
  flex: 0.5;
  margin: 20px;
  margin-bottom: 0px;
}

.sv-right-center {
  background: url(../assets/panel-bg.png);
  background-size: cover;
  border: 1px solid var(--title-line-color);
  border-radius: 5px;
  flex: 0.5;
  margin: 20px;
  margin-bottom: 0px;
}

.sv-right-bottom {
  background: url(../assets/panel-bg.png);
  background-size: cover;
  border: 1px solid var(--title-line-color);
  border-radius: 5px;
  width: calc(100% - 40px);
  height: calc(285px - 40px);
  margin: 20px;

}

.sv-content-core {
  overflow: hidden;
  width: calc(100% - 20px);
  margin: 24px 0px 20px 0;
  flex: 1;
  padding: 10px;
  background: #13599569;
  position: relative;
}

.sv-content-path {

  width: 100%;
  height: 245px;
  border: 1px solid var(--title-line-color);
  margin-bottom: 20px;
  background: url(../assets/panel-bg.png) no-repeat;
  background-size: 200% 100%;
  border-radius: 5px;
}

.sv-progress-item {
  width: 100%;
  display: flex;
  flex-direction: column;
}

.sv-avg {
  display: grid;
  grid-template-rows: repeat(6, 1fr);
  row-gap: 10px

}

.sv-progress-title {
  flex: 1;
  width: 100%;
  align-items: center;
  display: flex;
  justify-content: space-between;

}

.sv-progress-component {
  height: 10px;
  width: 100%;
}

.sv-out-ring {
  height: 10px;
  width: 10px;
  display: flex;
  align-items: center;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 10px;
}

.sv-ring-blue {
  border: 1px solid #0f7b90;
  background: radial-gradient(var(--color-blue), transparent);
}

.sv-ring-red {
  border: 1px solid #ff4688;
  background: radial-gradient(var(--color-red), transparent);
}

.sv-ring-yellow {
  border: 1px solid #eee42a;
  background: radial-gradient(var(--color-yellow), transparent);
}

.sv-ring-green {
  border: 1px solid #0f7b90;
  background: radial-gradient(var(--color-green), transparent);
}

.sv-point {
  border-radius: 50%;
  width: 4px;
  height: 4px;
  background: #3b8bf7;
}

.sv-map-tool {
  display: inline-block;
  right: -30px;
  position: absolute;
  //background: aliceblue;
  z-index: 2000;

}

.sv-map-button {
  font-size: 16px;
  padding: 3px 51px 3px 5px;
  display: flex;
  align-items: center;
  font-family: 'ysbth';
  background: linear-gradient(to right, #0f87d7 0, #0f87d738, #ff000000);
  transform: skewX(324deg);
  border-radius: 2px;
  box-shadow: -4px 5px 4px rgba(3, 33, 53, 0.43), 0 0 6px rgba(0, 0, 0, 0.13);
  margin: 20px;
  border: 1px solid #4992c3;
  border-right: none;
  -o-border-image: linear-gradient(to right, #78c3f8 40%, transparent);
  border-image: linear-gradient(to right, #78c3f8 40%, transparent);
  border-image-slice: 1;
  backdrop-filter: blur(5px);
  cursor: pointer;
}

.sv-map-button:hover {
  background: linear-gradient(to right, var(--color-yellow), rgba(255, 179, 58, 0.58), #ff000000);
  border: 1px solid #4992c3;
  border-right: none;
  border-image: linear-gradient(to right, #f2ff4b 40%, transparent);
  border-image-slice: 1;
}

.sv-button-content {
  transform: skewX(-324deg);
  margin-left: 10px;
}

.sv-car {
  width: 280px;
  height: 100%;

  position: relative;
}

.sv-car-3d {
  position: absolute;
  width: 55%;
  height: 100%;
  background: url(../assets/car3d.png) no-repeat;
  background-size: contain;
  left: 49%;
  transform: translateX(-54%);
  background-position-y: 40px;
}

.sv-car-dz {
  width: 100%;
  height: 100%;
  background: url(../assets/3dyuanpan.png) no-repeat;
  background-size: 80%;
  background-position-y: 75%;
  background-position-x: 22%;

}

.sv-panel-2 {
  width: 100%;
  height: calc(100% - var(--subtitle-height));
  display: flex;
}

.sv-all {
  flex: 1;
}

.sv-subtitle-xiushi {
  width: 25px;
  height: 20px;
  background: url(../assets/xiushi.png) no-repeat;
  background-size: 80%;
}

.sv-count {
  font-size: 20px;
  font-family: ysbth;
  background-image: -webkit-linear-gradient(bottom, var(--color-yellow), #ffffff);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.sv-pt-left {
  display: inline-flex;
  align-items: center;
  height: 100%;
}
</style>