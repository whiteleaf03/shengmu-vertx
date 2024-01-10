<template>
  <div id="map-container" style="background:rgb(0 111 255);filter:hue-rotate(346deg) brightness(1.3) "></div>
</template>
<script>
import L from "leaflet";
import 'leaflet/dist/leaflet.css';

L.blinkMarker = function (point, property) {
  // 使用js标签,便于操作,这个temDivEle的作用是将divEle通过innerHTML的方式获取为字符串
  var tempDivEle = document.createElement("div");
  var divEle = document.createElement("div");
  var spanEl = document.createElement("span");
  var aEl = document.createElement("a");
  tempDivEle.append(divEle);
  divEle.append(spanEl);
  spanEl.append(aEl);
  // 设置上基础的样式
  spanEl.classList.add("pulse-icon");
  aEl.classList.add("dive-icon");
  // 操作样式

  // 主体颜色
  if (property) {
    if (property.color) {
      spanEl.style.background = property.color;
      if (!property.diveColor) {
        aEl.style.boxShadow = "0 0 6px 2px " + property.color;
      }
    }
    // 标记大小
    if (property.iconSize) {
      spanEl.style.width = property.iconSize[0] + "px";
      spanEl.style.height = property.iconSize[1] + "px";
    }
    // 发散的color
    if (property.diveColor) {
      // 发散的重度
      if (property.level) {
        aEl.style.boxShadow = "0 0 " + (property.level * 3) + "px " + property.level + "px " + property.diveColor;
      } else {
        aEl.style.boxShadow = "0 0 6px 2px " + property.diveColor;
      }
    }
    // 发散的重度
    if (property.level) {
      if (property.diveColor) {
        aEl.style.boxShadow = "0 0 " + (property.level * 3) + "px " + property.level + "px " + property.diveColor;
      } else if (property.color) {
        aEl.style.boxShadow = "0 0 " + (property.level * 3) + "px " + property.level + "px " + property.color;
      } else {
        aEl.style.boxShadow = "0 0 " + (property.level * 3) + "px " + property.level + "px red";
      }
    }

    // 闪烁的速度
    if (property.speedTime) {
      aEl.style.setProperty("animation", "pulsate " + property.speedTime + "s infinite")
    } else {
      aEl.style.setProperty("animation", "")
    }
  }
  let myIcon = L.divIcon({className: 'my-div-icon', html: tempDivEle.innerHTML});
  let marker = L.marker(point, {icon: myIcon, title: property.title, zIndex: 1,});

  return marker;
},
    require('leaflet-hotline')
const sxt = require('../../assets/sxt.png')
import lineData from '@/assets/data.json' // 模拟数据
import 'leaflet.tilelayer.colorfilter'

export default {
  name: 'LoadMap',
  data() {
    return {
      blinkLayer: {},
      dataArr: [],
      map: {},
      hotlineLayer: {},
      test: [
        [121.33859297, 29.69236637],
        [121.33797089, 29.69212995],
        [121.33837913, 29.69109981],
        [121.33834024, 29.6904412],
        [121.33765013, 29.68967281],
        [121.33678507, 29.68905642],
        [121.33450918, 29.68818385],
        [121.33299005, 29.68760278],
        [121.33144825, 29.68699216],
        [121.32970239, 29.68626336],
        [121.32825577, 29.68562586],
        [121.32676799, 29.68497961],
        [121.32511749, 29.68413139],
        [121.32331949, 29.68319726],
        [121.3215821, 29.68221934],
        [121.31957555, 29.68119889],
        [121.317569, 29.68007214],
        [121.31597844, 29.67922175],
        [121.31429001, 29.6783501],
        [121.31230795, 29.67737214],
        [121.31033918, 29.67639401],
        [121.30820484, 29.67540612],
        [121.30504827, 29.67409179],
        [121.30227228, 29.6729775],
        [121.29865512, 29.67157749],
        [121.29433223, 29.66989012],
        [121.28946344, 29.66797249],
        [121.28535136, 29.6663865],
        [121.28222535, 29.66562085],
        [121.2793511, 29.66554793],
        [121.27565862, 29.66600368],
        [121.27275448, 29.66607518],
        [121.26850437, 29.66556105],
        [121.26526175, 29.66509317],
        [121.26183071, 29.66456877],
        [121.25924186, 29.66367378],
        [121.25732111, 29.66257318],
        [121.25570981, 29.66127163],
        [121.25452263, 29.65983359],
        [121.25311962, 29.65734823],
        [121.25184252, 29.65492536],
        [121.2500922, 29.65172513],
        [121.24827581, 29.64835295],
        [121.24672709, 29.64539922],
        [121.24513534, 29.64278193],
        [121.24268319, 29.64027676],
        [121.23915554, 29.63795849],
        [121.23489656, 29.63530363],
        [121.23404426, 29.63512038],
        [121.23339986, 29.63514113],
        [121.23318505, 29.63540044],
        [121.23319698, 29.6357531],
        [121.23344758, 29.63608501],
        [121.23437837, 29.63684219]
      ]

    }
  },
  mounted() {
    let vm = this;
    // console.log('生成数据---',this.calcDistance(this.test))
    const t = setTimeout(() => {
      vm.initMap()
      window.clearTimeout(t)
    }, 500)
  },
  methods: {
    initMap() {
      let i = 1
      lineData.data.forEach((row) => {

        let item = row[0]
        row[0] = row[1] //经度
        row[2] = i //  控制颜色
        row[1] = item //维度
        i += 0.3;
        this.dataArr.push(row)
      })
      this.map = L.map("map-container", {
        center: [29.68, 121.31], // 地图中心
        zoom: 14, //缩放比列
        zoomControl: false, //禁用 + - 按钮
        doubleClickZoom: true, // 禁用双击放大
        attributionControl: true // 移除右下角leaflet标识
      });
      let myFilter = [
        // 'blur:0px',
        'blur:0px',
        'brightness:95%',
        'contrast:120%',
        'grayscale:15%',
        'hue:15deg',
        'opacity:70%',
        'invert:100%',
        'saturate:50%',
        'sepia:10%',

      ];
      L.tileLayer.colorFilter(
          "http://webrd01.is.autonavi.com/appmaptile?lang=zh_cn&size=1&scale=1&style=7&x={x}&y={y}&z={z}",
          {
            filter: myFilter,
          }
      ).addTo(this.map);

      //创建hotline对象，dataArr的值是[x值、y值、z值]
      this.hotlineLayer = L.hotline(this.dataArr, {
        min: 0,//z值的最小值
        max: 1,//z值的最大值
        palette: {
          0.0: '#f85802', // blue
          0.5: '#f8d940', // purple
          1: '#15e587'  // red,
        },
        weight: 7,//线条粗细
        outlineColor: 'rgba(188,234,223,0.49)',//边框线颜色
        outlineWidth: 1//边框线粗细
      });
      this.map.fitBounds(this.hotlineLayer.getBounds());
      this.hotlineLayer.addTo(this.map);

      this.makerPoint([29.68285611, 121.32262293])
      this.makerPoint([29.68899909, 121.33673448])
      this.makerPoint([29.67745697, 121.31224338])
      this.makerPoint([29.67104846, 121.29758736])
      // this.createTip()
      const zoomTimeout = setTimeout(() => {
        this.map.setZoom(13)
        clearTimeout(zoomTimeout)
      }, 200)
    },
    changeData(data, field) {
      let mapData = new Array()
      data.forEach(item => {
        //pct_count车流
        mapData.push([item.lat, item.lng, item[field]])
      })
      console.log(mapData)
      this.hotlineLayer.setLatLngs(mapData)
      this.hotlineLayer._update()
    },
    makerPoint(center) {
      let vm = this;
      let maker = L.marker(center, {
        clickable: true, // 是否可以点击
        zIndexOffset: 10,
        icon: L.icon({
          iconUrl: sxt, // 标点图标地址
          iconSize: [35, 35] // 图标大小
        })
      }).addTo(this.map)
      maker.on("click", e => {
        vm.$emit('mapClick', e)
      })
    },
    createBlink() {
      if (this.blinkLayer) {
        this.map.removeLayer(this.blinkLayer)
      }
      // [
      //   121.32185456,
      //   29.682364
      // ]
      // [29.68285611, 121.32102293]
      this.blinkLayer = L.blinkMarker([29.68285611, 121.32102293], {
        iconSize: [25, 25],
        color: 'radial-gradient(#ff2828, transparent)',
        speedTime: 1
      }).addTo(this.map);
    },

    calcDistance(data) {
      const finalData = []
      if (data) {
        let distance = 0
        for (let i = 0; i < data.length; i++) {
          let item = data[i];
          if (i + 1 == data.length) {
            break
          }
          let itemNext = data[i + 1]
          let latlng = L.latLng(item[1], item[0]);
          let latlng2 = L.latLng(itemNext[1], itemNext[0]);
          distance += latlng.distanceTo(latlng2);
          finalData.push({lng: item[0], lat: item[1], distance})
        }
      }
      return JSON.stringify(finalData)
    },
    // eslint-disable-next-line no-unused-vars
    createTip(center) {
      L.tooltip([29.68285611, 121.32262293],{
        content:'<div class="sv-map-tip"><div class="sv-tip-title">' +
            '<div class="sv-tip-title-split"></div>' +
            '<div class="sv-tip-title-text"> <svg width="18" height="15" viewBox="0 0 21 19" fill="none" xmlns="http://www.w3.org/2000/svg">\n' +
            '<path d="M18.5 18.03H2.49999C1.89999 18.03 1.29999 17.73 0.999994 17.13C0.699994 16.63 0.699994 15.93 0.999994 15.43L8.99999 1.73001C9.49999 0.930008 10.5 0.630002 11.3 1.13C11.6 1.23 11.8 1.53001 11.9 1.73001L19.9 15.43C20.2 15.93 20.2 16.63 19.9 17.13C19.7 17.63 19.2 18.03 18.5 18.03ZM10.5 13.43C9.99999 13.43 9.59999 13.83 9.59999 14.23C9.59999 14.73 10 15.13 10.4 15.13C10.9 15.13 11.3 14.73 11.3 14.33C11.3 13.83 11 13.43 10.5 13.43ZM9.7 7.23001V12.33H11.4V7.23001H9.7Z" fill="url(#paint0_linear_316_25)"/>\n' +
            '<defs>\n' +
            '<linearGradient id="paint0_linear_316_25" x1="10.5432" y1="0.780426" x2="10.5432" y2="17.8807" gradientUnits="userSpaceOnUse">\n' +
            '<stop stop-color="white"/>\n' +
            '<stop offset="1" stop-color="#FFCECE"/>\n' +
            '</linearGradient>\n' +
            '</defs>\n' +
            '</svg>\n' +
            '<span style="margin-left: 10px">2023年10月24日 08:11:00</span>' +
            '</div>' +
            ' </div>' +
            '<div class="sv-tip-context"> ' +
            '<div class="sv-tip-item"><span style="margin-right: 10px">事发性质:</span><span class="sv-tip-item-h">机动车发生故障导致交通事故</span></div>  ' +
            '<div class="sv-tip-item"><span style="margin-right: 10px">事故地点:</span><span  class="sv-tip-item-h">浙江高速公路大队G1512辖区1500KM至1800KM</span></div> ' +
            '<div class="sv-tip-item" style="margin-bottom: 0"><span style="margin-right: 10px">处理情况:</span><span style="color: var(--color-yellow);font-weight: 600">处理中</span></div> ' +
            '</div>' +
            '</div>',
        permanent:true,
        direction:'top',
        offset:[-10,-20]
      }).addTo(this.map);
    }
  },

}
</script>
<style scoped lang="less">
#map-container {
  width: 100%;
  height: 100%;
}
::v-deep .leaflet-tooltip{
  padding: 0;
  border-color: #ff4d2c;
  background: black;
  display: inline-block;
}
::v-deep .leaflet-tooltip:before{
  border-top-color: #ff4d2c;
}
</style>