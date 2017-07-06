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
}

const getters = {
  login: state => state.login,
  username: state => state.username,
  token: state => state.token,
  userId: state => state.userId,
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
	},

	[LOG_OUT] (state) {
    state.login = false
		state.username = ""
    state.token = ""

    localStorage.login = state.login
    localStorage.username = state.username
    localStorage.token = state.token
	},

  [USER_INIT] (state, userInfo) {
	  state.userId  = userInfo.id

    localStorage.userId = state.userId
  }

}

export default {
	state,
	getters,
  actions,
	mutations
}
