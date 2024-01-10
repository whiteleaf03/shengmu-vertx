<template>
  <div id="heat-map" style="width: 100%;height: 100%"></div>
</template>
<script >
import * as echarts from "echarts";

export default {
  name:'heat-map',
  data(){
    return{
      heatData:{
        xAxis:[],
        yAxis:[],
        //[x,y,value]
        data:[
          [0, 0, 5],
          [0, 1, 20],
          [0, 2, 36],
          [0, 3, 10],
          [0, 4, 10],
          [1, 0, 10],
          [1, 1, 35],
          [1, 2, 30],
          [1, 3, 25],
          [1, 4, 20],
          [2, 0, 15],
          [2, 1, 25],
          [2, 2, 45],
          [2, 3, 50],
          [2, 4, 40],
          [3, 0, 30],
          [3, 1, 35],
          [3, 2, 55],
          [3, 3, 65],
          [3, 4, 45],
          [4, 0, 45],
          [4, 1, 60],
          [4, 2, 70],
          [4, 3, 75],
          [4, 4, 65],
          [0, 0, 5],
          [0, 1, 20],
          [0, 2, 36],
          [0, 3, 10],
          [0, 4, 10],
          [1, 0, 10],
          [1, 1, 35],
          [1, 2, 30],
          [1, 3, 25],
          [1, 4, 20],
          [2, 0, 15],
          [2, 1, 25],
          [2, 2, 45],
          [2, 3, 50],
          [2, 4, 40],
          [3, 0, 30],
          [3, 1, 35],
          [3, 2, 55],
          [3, 3, 65],
          [3, 4, 45],
          [4, 0, 45],
          [4, 1, 60],
          [4, 2, 70],
          [4, 3, 75],
          [4, 4, 65],
        ]
      },
    }
  },
  mounted() {
    setTimeout(() => {
      let areaChar = echarts.init(document.getElementById('heat-map'));
      // const labelFont = 'alibaba45'
      // const labelColor = '#ffffff'
      // const lineColor = '#274f93'
      let option = {
        tooltip: {
          position: 'top'
        },
        animation: false,
        grid: {
          height: '50%',
          y: '10%'
        },
        xAxis: {
          type: 'category',
          data: this.heatData.xAxis
        },
        yAxis: {
          type: 'category',
          data: this.heatData.yAxis
        },
        visualMap: {
          type:'piecewise',
          pieces: [
            {min: 1,max: 10}, // 不指定 max，表示 max 为无限大（Infinity）。
            {min: 10, max: 30},
            {min: 30, max: 40},
            {min: 40, max: 50},
            {min: 60, max: 70, label: '10 到 200（自定义label）'},
            {min: 71, max: 80}     // 不指定 min，表示 min 为无限大（-Infinity）。
          ],
          show:false,
          min: 0,
          max: 100,
          calculable: true,
          realtime: false,
          inRange: {
            color: [
              '#313695',
              '#4575b4',
              '#74add1',
              '#abd9e9',
              '#e0f3f8',
              '#ffffbf',
              '#fee090',
              '#fdae61',
              '#f46d43',
              '#d73027',
              '#a50026'
            ]
          }
        },
        series: [{
          name: 'Gaussian',
          type: 'heatmap',

          data: this.heatData.data,
          // progressive: 1000,
          progressiveThreshold : 20,
          itemStyle:{
            opacity: 0.6
          },
          emphasis: {
            itemStyle: {
              shadowBlur: 10,
              shadowColor: 'rgba(0, 0, 0, 0.5)'
            }
          },
        }]
      };

      areaChar.setOption(option)

      window.addEventListener("resize", function () {
        areaChar.resize();
      });
    },500)
  },
  methods:{

  }
}
</script>

<style scoped lang="less">

</style>