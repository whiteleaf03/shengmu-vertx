<template>
  <div :id="uuid" class="sv-char">
  </div>
</template>

<script>
import * as echarts from 'echarts';

export default {
  name: "area-char",
  props: {
    uuid: {
      type:String
    },
    colors: {
      type: Array,
    },
    type:{
      type:String,
      default:'line'
    },
    data:[],
    xdata:[],
  },
  data() {
    return {
      char: null
    }
  },
  mounted() {
    let vm = this;
    setTimeout(() => {
      vm.char = echarts.init(document.getElementById(this.uuid));
      vm.refresh()
      window.addEventListener("resize", function () {
        vm.char.resize();
      });
    },500)

  },
  methods:{
    refresh() {
      if(this.char){
        const labelFont = 'alibaba45'
        const labelColor = '#ffffff'
        const lineColor = '#274f93'
        let option = {
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              type: 'cross',
              label: {
                backgroundColor: '#6a7985',
                textStyle: {
                  color: labelColor,
                  fontFamily: labelFont
                }
              }
            }
          },
          grid: {
            left: '1%',
            right: '1%',
            bottom: '1%',
            top: '2%',
            containLabel: true
          },
          xAxis: [
            {
              type: 'category',
              boundaryGap: false,
              data: this.xdata,
              axisLabel: {
                textStyle: {
                  color: labelColor,
                  fontFamily: labelFont
                }
              },
              axisLine: {
                lineStyle: {
                  color: lineColor // 设置x轴刻度线的颜色为蓝色
                }
              }
            }
          ],
          yAxis: [
            {
              type: 'value',
              axisLabel: {
                textStyle: {
                  color: labelColor,
                  fontFamily: labelFont
                }
              },
              splitLine:{
                lineStyle:{
                  color: lineColor
                }
              }
            }
          ],
          series: [
            {
              name: '面积图',
              type: this.type,
              smooth: true,
              showSymbol:false,
              barWidth:'30%',
              areaStyle: {},
              data: this.data,
              // 设置颜色渐变
              itemStyle: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1,  this.colors),
                lineStyle: {
                  color: 'blue'
                }
              }
            }
          ]
        };
        this.char.setOption(option)
      }

    }
  },
  watch:{
    // eslint-disable-next-line no-unused-vars
    data(val){
       this.refresh()
    }
  }

}
</script>

<style scoped lang="less">
.sv-char {
  width: 100%;
  height: 100%;
}
</style>