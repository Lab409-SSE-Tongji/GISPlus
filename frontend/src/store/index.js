import Vue from 'vue'
import Vuex from 'vuex'
import * as actions from './actions'
import * as getters from './getters'
import session from './modules/session'

Vue.use(Vuex); //安装store插件

export default new Vuex.Store({
  actions,
  getters,
  modules : {
		session
	}
})
