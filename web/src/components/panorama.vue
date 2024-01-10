<template>
  <div id="panorama">
    <div class="panorama-line">
    </div>
    <div class="panorama-distribution">
      <div class="panorama-tip">
        <div class="panorama-liner"  :key="item.id" v-for="item in serviceAreas"
             :style="{width:100/serviceAreas.length+'%',filter:`blur(3px) opacity(${item[field]}) `}" ></div>
      </div>
      <div class="panorama-vline" >
        <div class="vline" v-for="item in serviceAreas" :style="{width:100/serviceAreas.length+'%'}"
             :key="item.id">
          <div   class="point-tip">
            {{ (item.distance / 1000).toFixed(2) }}
          </div>
<!--          <div :class="['line-point','xz'] ">-->
<!--            <div class="line-point-in"></div>-->
<!--          </div>-->
          <div class="line-item"></div>
          <div class="panorama-count">
            {{item.count}}
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "panorama",
  data(){
    return{
      serviceAreas:[
        {id:1,count:0,show:false,distance:1000},
        {id:2,count:0,show:false,distance:1000},
        {id:3,count:0,show:true,distance:1000},
        {id:4,count:0,show:false,distance:1000},
        {id:5,count:0,show:false,distance:1000},
        {id:6,count:0,show:true,distance:1000},
        {id:7,count:0,show:false,distance:1000},
        {id:8,count:0,show:true,distance:1000},
        {id:9,count:0,show:false,distance:1000},
        {id:10,count:0,show:false,distance:1000},
      ],
      field:0
    }
  },
  mounted() {
  },
  methods:{
    changeData(data,field){
      this.serviceAreas = data
      this.field = field
    }
  }
}
</script>


<style scoped lang="less">
#panorama {
  position: relative;
  display: flex;
  align-items: center;
  width: 100%;
  height: 100%;
}

.panorama-line {

  width: calc(100% - 20px);
  height: 15px;
  background: linear-gradient(to right, #309dfe, #044da0 50%, #309dfe);
  margin: 0;
  border: 1px dashed;
  border-radius: 2px;
  margin-right: 20px;
}

.panorama-distribution {
  position: absolute;
  width: 100%;
  height: 100%;
  z-index: 1;
}

.panorama-tip {
  width: calc(100% - 40px);
  height: calc(50% - 40px);
  position: relative;
  top: 25px;
  display: flex;
  margin-right: 20px;
  align-items: flex-end;
  padding: 20px;
}
.panorama-vline{
  width: calc(100% - 20px);
  height: 50%;
  display: flex;
  margin-right: 20px;
  display: flex;
  align-items: center;
  justify-content: center;
}
.vline{
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
}
.line-item{
  margin-top: -10px;
  margin-bottom: 20px;
  height: calc(100% - 47px);
  width: 2px;
  background: linear-gradient(to bottom, #2990ef, #073e77);
}
.line-point{
  width: 20px;
  height: 20px;
  position: absolute;
  background: aliceblue;
  top: 90px;
  border-radius: 50%;
  background: radial-gradient(transparent,#abe0fd );
  border: 2px solid #98bff7;
  display: flex;
  align-items: center;
  justify-content: center;
  backdrop-filter:blur(2px);
}
.line-point:before{
  content: "";
  position: absolute;
  left: 10px;
  width: 13px;
  height: 0;
  border-left: 0.1px solid transparent;
  border-right: 0.1px solid transparent;
  border-bottom: 8px solid;
  border-image: linear-gradient(to left, transparent 0%, #ffb23f 50%, transparent 100%);
  border-image-slice: 1;

}
.line-point-in{
  width: 50%;
  height: 50%;
  background: #ceddf3;
  border-radius: 50%;
}
.point-tip{
  top: 40px;
  padding: 3px 5px;
  position: absolute;
  background: rgba(230, 177, 101, 0);
  backdrop-filter: blur(2px);
  border-radius: 20px;
  box-shadow: 0 2px 4px rgb(0 0 0), 0 0 6px rgb(0 0 0);
  color: #e0a849;
  font-size: 16px;
  font-family: 'ysbth';
  cursor: pointer;
}
.point-tip:hover{
  //position: relative;
  z-index: 3200;
  background: black;
}

@keyframes  rotate{
  0% {
    transform: rotate(0deg);
  }
  50% {
    transform: rotate(180deg);
  }
  100%{
    transform: rotate(360deg);
  }

}
.xz{
  z-index: 1;
  animation:  rotate linear 2s infinite;
}
.panorama-count{
  position: absolute;
  bottom: 20px;
}
.panorama-liner{
  width: 100%;
  height: 10px;
  background: var(--color-yellow);
  filter: blur(8px);
  cursor: pointer;
}

</style>