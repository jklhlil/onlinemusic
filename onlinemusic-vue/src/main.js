import Vue from 'vue'
import App from './App.vue'
import router from './router/index.js'

import './plugins/vant.js'
import './plugins/element.js'
import './http/axios'

Vue.config.productionTip = false
Vue.prototype.global = {}

router.beforeEach((to, from, next) => {/*路由发生变化修改页面title */
  document.title = to.name;
  next()
})



new Vue({
  router,
  render: h => h(App),

}).$mount('#app')
