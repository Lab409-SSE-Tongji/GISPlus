import {
	LOG_IN,
  LOG_OUT
} from '../mutation-types'

const state = {
  login: localStorage.login || false,
  username: localStorage.username || '',
  token: localStorage.token || '',
}

const getters = {
  login: state => state.login,
  username: state => state.username,
  token: state => state.token,
}

const actions = {

}

const mutations = {
	[LOG_IN] (state, userData){
	  state.login = true
		state.username = userData.username
    state.token = userData.token

    localStorage.login = state.login
    localStorage.userName = state.userName
    localStorage.token = state.token
	},


	[LOG_OUT] (state){
    state.login = false
		state.username = ""
    state.token = ""

    localStorage.login = state.login
    localStorage.username = state.username
    localStorage.token = state.token
	}
}

export default {
	state,
	getters,
  actions,
	mutations
}
