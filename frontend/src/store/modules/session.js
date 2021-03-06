import {
	LOG_IN,
  LOG_OUT,
  USER_INIT,
} from '../mutation-types'

const state = {
  login: localStorage.login === 'true' || false,
  username: localStorage.username || '',
  token: localStorage.token || '',
  userId: localStorage.userId || '',
  roles: localStorage.roles || '',
  organId: localStorage.organId || '',
}

const getters = {
  login: state => state.login,
  username: state => state.username,
  token: state => state.token,
  userId: state => state.userId,
  roles: state => state.roles,
  organId: state => state.organId,
}

const actions = {

}

const mutations = {
	[LOG_IN] (state, userData) {
	  state.login = true
		state.username = userData.username
    state.token = userData.token

    localStorage.login = state.login
    localStorage.username = state.username
    localStorage.token = state.token
    localStorage.roles = state.roles
	},

	[LOG_OUT] (state) {
    state.login = false
		state.username = ""
    state.token = ""
    state.roles = ""

    localStorage.login = state.login
    localStorage.username = state.username
    localStorage.token = state.token
    localStorage.roles = state.roles
	},

  // 补全 user session 信息
  [USER_INIT] (state, userInfo) {
    state.userId  = userInfo.id
    state.roles  = userInfo.roles
    state.organId  = userInfo.organId

    localStorage.userId = state.userId
    localStorage.roles = state.roles
    localStorage.organId = state.organId
  }
}

export default {
	state,
	getters,
  actions,
	mutations
}
