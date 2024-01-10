<template>
  <div class="progress-bg">
    <div class="progress-texture"></div>
    <div :id="id" :class="[`progress-${type}`]"></div>
  </div>
</template>

<script>
import {gsap} from "gsap/dist/gsap";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: 'sv-progress',
  data() {
    return {
      id: this.uuid()
    }
  },
  props: {
    type: {
      type: String,
      default: 'primary',
    },
    percentage: {
      type: Number,
      default: 0
    }
  },
  mounted() {
    this.startAnimation('0%', `${this.percentage}%`)
  },
  methods: {
    startAnimation(start, end) {
      const target = document.getElementById(`${this.id}`)
      gsap.fromTo(target, {
        width: start,
      }, {
        duration: 1,
        width: end,
        delay: 0.5,
        ease: 'power3.inOut',
      })
    },
    uuid() {
      return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
        var r = Math.random() * 16 | 0,
            v = c == 'x' ? r : (r & 0x3 | 0x8);
        return v.toString(16);
      });
    }
  },
  watch: {
    percentage(newval,oldval) {
      this.startAnimation(`${oldval}%`, `${newval}%`)
    }
  }

}
</script>


<style scoped lang="less">
.progress-bg {
  width: 100%;
  height: 100%;
  background: rgba(218, 218, 218, 0.2);
  position: relative;
  display: flex;
  border-radius: 20px;
}

.progress-texture {
  background: linear-gradient(90deg, #3aa9dd1f, #3aa9dd26, rgba(0, 0, 0, 0) 59%);
  background-size: 10%;
  transform: skewX(323deg);
  height: 100%;
  border-radius: 20px;
  width: 100%;
  position: absolute;
}

.progress-primary {
  width: 100%;
  height: calc(100% - 4px);
  background: linear-gradient(to left, var(--color-blue), transparent);
  border-radius: 20px;
  margin: 2px;
}

.progress-primary {
  width: 100%;
  height: calc(100% - 4px);
  background: linear-gradient(to left, var(--color-blue), transparent);
  border-radius: 20px;
  margin: 2px;
}

.progress-red {
  width: 100%;
  height: calc(100% - 4px);
  background: linear-gradient(to left, var(--color-red), transparent);
  border-radius: 20px;
  margin: 2px;
}

.progress-yellow {
  width: 100%;
  height: calc(100% - 4px);
  background: linear-gradient(to left, var(--color-yellow), transparent);
  border-radius: 20px;
  margin: 2px;
}

.progress-blue {
  width: 100%;
  height: calc(100% - 4px);
  background: linear-gradient(to left, var(--color-d-bule), transparent);
  border-radius: 20px;
  margin: 2px;
}

.progress-green {
  width: 100%;
  height: calc(100% - 4px);
  background: linear-gradient(to left, var(--color-green), transparent);
  border-radius: 20px;
  margin: 2px;
}

@keyframes progress {
  from {
    width: 0%;
  }
  to {
    width: 100%;
  }
}

.progress {
  animation: progress 5s linear;
}
</style>