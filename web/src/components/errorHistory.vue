<template>
  <div class="history-list">
    <div class="history-line"></div>
    <div class="history-container">
      <div :class="[ 'history-item']" v-for="(error,index) in errorData" :key="error.id">
        <div class="history-icon">
          <span class="history-icon-text">{{ '0' + (index + 1) }}</span>
        </div>
        <div class="history-content">
          <div class="history-date">{{ error.date }}</div>
          <div class="history-content-detail">
            <span>事发性质：</span>
            <span class="history-error-text">{{ error.sfxz }}</span>
          </div>
          <div class="history-content-detail">
            <span> 事故地点：</span>
            <span class="history-error-text">{{ error.sfdd }}</span>
          </div>
          <div class="history-content-detail" >
            <span>  处理情况：</span>
            <span :class="['history-error-text',`type-${error.type}`]">{{ error.clqk }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "errorHistory",
  data() {
    return {
      errorData: [
        {
          id: 1,
          date: '10月21日 10:33:07',
          sfxz: '驾驶员的违法驾驶，如超速行驶、低速行驶、违法停车等导致交通事故。',
          sfdd: '湖高速公路大队辖区G22青兰高速1576KM至1580KM（西安方向）',
          clqk: '已处理',
          type: 0
        },
        {
          id: 2,
          date: '10月21日 10:33:07',
          sfxz: '机动车发生故障致交通事故',
          sfdd: '湖高速公路大队辖区G22青兰高速1576KM至1580KM（西安方向）',
          clqk: '待处理',
          type: 1
        },
        {
          id: 3,
          date: '10月21日 10:33:07',
          sfxz: '天气影响、道路破坏等导致交通事故',
          sfdd: '湖高速公路大队辖区G22青兰高速1576KM至1580KM（西安方向）',
          clqk: '已处理',
          type: 0
        },
        {
          id: 4,
          date: '10月21日 10:33:07',
          sfxz: '机动车发生故障致交通事故',
          sfdd: '湖高速公路大队辖区G22青兰高速1576KM至1580KM（西安方向）',
          clqk: '处理中',
          type: 1
        },
        {
          id: 5,
          date: '10月21日 10:33:07',
          sfxz: '机动车发生故障致交通事故',
          sfdd: '湖高速公路大队辖区G22青兰高速1576KM至1580KM（西安方向）',
          clqk: '已处理',
          type: 0
        },
        {
          id: 6,
          date: '10月21日 10:33:07',
          sfxz: '机动车发生故障致交通事故',
          sfdd: '湖高速公路大队辖区G22青兰高速1576KM至1580KM（西安方向）',
          clqk: '已处理',
          type: 0
        },
        {
          id: 7,
          date: '10月21日 10:33:07',
          sfxz: '机动车发生故障致交通事故',
          sfdd: '湖高速公路大队辖区G22青兰高速1576KM至1580KM（西安方向）',
          clqk: '已处理',
          type: 0
        },
      ]
    }
  },
  mounted() {
    const imgLen = document.querySelectorAll('.history-container > div').length;
    const scrollContainer =  document.querySelector(".history-container");

    scrollContainer.style.animationDuration = imgLen * 15 + 's';
    document.querySelector('.history-list').innerHTML += scrollContainer.outerHTML;
  }
}
</script>

<style scoped lang="less">
.history-list {
  width: 100%;
  height: 100%;
  display: inline-flex;
  overflow: hidden;
  flex-direction: column;
  position: relative;

}
.history-scroll{
  //animation: scroll 20s linear infinite;
}

.history-item {
  display: inline-flex;
  align-items: flex-start;
  margin-bottom: 10px;
  cursor: pointer;
  border-radius: 3px;
  transform: translateZ(0);
  //animation: scroll 30s linear infinite normal;
}
.history-item:hover{
  background: linear-gradient(to left,#041229,transparent);
}
.history-icon {
  background: url(../assets/detailLogo_.png) no-repeat;
  background-size: 70%;
  width: 60px;
  height: 60px;
}

.history-icon-text {
  font-size: 18px;
  font-family: 'ysbth';
  float: right;
  margin-right: 5px;
  margin-top: 10px;
  position: relative;
}
.history-icon-text:before{
  content: '';
  right: -15px;
  height: 10px;
  width: 10px;
  position: absolute;
  top: 7px;
  border-radius: 50px;
  background: #fff;
}
.history-content {
  display: inline-block;
  width: calc(100% - 60px - 20px);
  padding-top: 10px;
  margin-left: 20px;
}

.history-date {
  font-size: var(--h1-zw);
  font-family: 'ysbth';
  background-image: -webkit-linear-gradient(bottom, #18c3e5, #ffffff);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  margin-bottom: 10px;
}

.history-content-detail {
  margin-bottom: 10px;
}
.history-error-text{
  color: #C0C4CC;
}
.type-0{
  color: var(--color-green);
  padding: 2px 15px;
  background: #12877d66;
  border-radius: 15px;
  font-weight: 600;
}
.type-1{
  color: var(--color-yellow);
  padding: 2px 15px;
  background: rgba(230, 177, 101, 0.22);
  border-radius: 15px;
  font-weight: 600;
}
.type-2{
  color: var(--color-red);
  padding: 2px 15px;
  background: rgba(135, 18, 112, 0.4);
  border-radius: 15px;
  font-weight: 600;
}
.history-line{
  position: absolute;
  width: 1px;
  height: calc(100% - 30px);
  border-left: 1px dashed #5e6b84;
  left: 65px;
  margin: 15px 0px;
}

@keyframes scroll {
  0% { transform: translateY(0px); }
  100% { transform: translateY(-700px); }
}
.history-container{
  display: inline-block;
  animation: scroll 15s linear infinite;
}

</style>