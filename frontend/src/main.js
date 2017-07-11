// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import store from './store'
import router from './router'
import VueResource from 'vue-resource'
import VeeValidate from 'vee-validate'
import ElementUI from 'element-ui'
import '../static/css/theme-green/index.css';       // 浅绿色主题

import './global'

Vue.use(VueResource);
Vue.use(VeeValidate);
Vue.use(ElementUI);
Vue.config.productionTip = false

// Vue.http.interceptors.push(function(request, next) {
//
//   // modify method
//   // request.method = 'POST';
//
//   // modify headers
//   request.headers.set('X-CSRF-TOKEN', 'TOKEN');
//   request.headers.set('Authorization', 'Bearer TOKEN');
//
//   // continue to next interceptor
//   next();
// });

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App }
})
