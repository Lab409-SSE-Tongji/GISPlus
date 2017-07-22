// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import store from './store'
import router from './router'
import VueResource from 'vue-resource'
import VeeValidate from 'vee-validate'
import ElementUI from 'element-ui'
import '../static/css/theme-green/index.css'       // 浅绿色主题
import messages from './assets/zh_CN'

import './global'

Vue.use(VueResource);

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


const config = {
  errorBagName: 'errors', // change if property conflicts.
  fieldsBagName: 'fields',
  delay: 0,
  locale: 'zh_CN',
  dictionary: null,
  strict: true,
  enableAutoClasses: false,
  classNames: {
    touched: 'touched', // the control has been blurred
    untouched: 'untouched', // the control hasn't been blurred
    valid: 'valid', // model is valid
    invalid: 'invalid', // model is invalid
    pristine: 'pristine', // control has not been interacted with
    dirty: 'dirty' // control has been interacted with
  },
  events: 'input|blur',
  inject: true
};

Vue.use(VeeValidate, config);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  template: '<App/>',
  components: { App }
})
