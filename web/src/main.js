import Vue from 'vue'
import App from './App.vue'
import router from './router'
require('vue-video-player/src/custom-theme.css')
require('video.js/dist/video-js.css')
import VideoPlayer from 'vue-video-player'
Vue.use(VideoPlayer)
Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
