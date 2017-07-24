<template>
  <div class="wrapper wrapper-content animated fadeInRight">

  <!--普通用户列表-->
  <div class="col-lg-3" v-for="(user, index) in userList" @click="">
    <div class="contact-box center-version">
      <a>
        <img alt="image" class="img-circle" src="../../assets/user-icon.png">
        <h3 class="m-b-xs"><strong>{{user.name}}</strong></h3>
        <address class="m-t-md">
          <!--<strong>邮箱: {{user.email}}</strong><br>-->
          <!--<abbr>电话: {{user.phone}}</abbr><br>-->
          <abbr>公司: {{showOrgan(user.organId)}}</abbr><br>
          <abbr>角色: {{user.roles | showRoles}}</abbr>
        </address>
      </a>
      <div class="contact-box-footer">
        <div class="m-t-xs btn-group">
          <a class="btn btn-sm btn-white" data-toggle="modal" data-target="#modifyUserInfo" @click="toggleEditUser(index)"><i class="fa fa-asterisk"></i> 修改 </a>
          <a class="btn btn-sm btn-white" data-toggle="modal" data-target="#deleteUserInfo" @click="toggleDeleteUser(index)"><i class="fa fa-trash-o"></i> 删除 </a>
        </div>
      </div>
    </div>
  </div>

  <!--添加普通用户按钮-->
  <div class="col-lg-3">
    <div class="contact-box center-version">
      <a data-toggle="modal" data-target="#addUser">
        <!--todo 图片改成 + 号-->
        <img alt="image" class="img-circle" style="margin-top: 50px" src="../../assets/add-icon.png">
      </a>
      <div class="contact-box-footer" style="margin-top: 38px; margin-bottom: 12px">
        <div class="m-t-xs btn-group">
          <abbr>新建用户</abbr>
        </div>
      </div>
    </div>
  </div>


  <!--各类模态框-->
  <!--添加普通用户模态框-->
  <div class="modal fade" id="addUser" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
            aria-hidden="true">&times;</span></button>
          <h4 class="modal-title">用户信息</h4>
        </div>
        <div class="modal-body">
          <div class="form-group has-success">
            <span>用户名</span>
            <input class="form-control" v-model="addUser.username" placeholder="请输入用户名" name="username" v-validate data-vv-rules="required" >
            <span v-show="errors.has('username')" class="help is-danger" style="color: #ed5565">请输入用户名</span><br>
            <span>密码</span>
            <input type="password" class="form-control" placeholder="请输入密码" v-model="addUser.password" name="password" v-validate data-vv-rules="required" >
            <span v-show="errors.has('password')" class="help is-danger" style="color: #ed5565">请输入密码</span><br>
            <span>确认密码</span>
            <input type="password" class="form-control" placeholder="确认密码" v-model="addUser.passwordRepeat">
            <span v-show="!passwordEqual" class="help is-danger" style="color: #ed5565">两次输入的密码不一致！</span><br>
            <span>姓名</span>
            <input class="form-control" v-model="addUser.name" placeholder="请输入姓名" name="name" v-validate data-vv-rules="required" >
            <span v-show="errors.has('name')" class="help is-danger" style="color: #ed5565">请输入姓名</span><br>
            <span v-show="organShow">组织</span>
            <select v-model="addUser.organId" class="form-control" v-show="organShow">
              <!--<option value="0" selected>选择组织</option>-->
              <option :value="organ.id" v-for="(organ, index) in organList">{{organ.name}}</option>
            </select>
            <span>身份</span>
            <select v-model="addUser.roles" class="form-control">
              <!--<option value="0" selected>选择角色</option>-->
              <option value="user">用户</option>
              <option value="admin" v-show="showAddAdmin">管理员</option>
              <option value="superAdmin" v-show="showAddSuperAdmin">超级管理员</option>
            </select>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">&nbsp;取消&nbsp;</button>
          <button type="button" class="btn btn-primary" data-dismiss="modal" @click="addUserInfo()">&nbsp;确认&nbsp;</button>
        </div>
      </div>
    </div>
  </div>

  <!--修改用户信息模态框-->
  <div class="modal fade" id="modifyUserInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
            aria-hidden="true">&times;</span></button>
          <h4 class="modal-title">用户信息</h4>
        </div>
        <div class="modal-body">
          <div class="form-group has-success">
            用户名<input class="form-control" v-model="editUserInfo.username" disabled/>
            姓名<input class="form-control" v-model="editUserInfo.name" placeholder="姓名" />
            密码<input class="form-control" v-model="editUserInfo.password" placeholder="密码"/>
            邮箱<input class="form-control" v-model="editUserInfo.email" placeholder="邮箱"/>
            电话<input class="form-control" v-model="editUserInfo.phone" placeholder="电话"/>
            <span v-show="organShow">组织</span>
            <select v-model="editUserInfo.organ" class="form-control" v-show="organShow">
              <!--<option value="0" selected>选择组织</option>-->
              <option :value="organ.id" v-for="(organ, index) in organList">{{organ.name}}</option>
            </select>
            <span>身份</span>
            <select v-model="editUserInfo.roles" class="form-control">
              <!--<option value="0" selected>选择角色</option>-->
              <option value="user">用户</option>
              <option value="admin">管理员</option>
              <option value="superAdmin">超级管理员</option>
            </select>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">&nbsp;取消&nbsp;</button>
          <button type="button" class="btn btn-primary" data-dismiss="modal" @click="updateUserInfo()">&nbsp;确认&nbsp;</button>
        </div>
      </div>
    </div>
  </div>

    <!--删除模态框-->
    <div class="modal fade" id="deleteUserInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
              aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">删除用户</h4>
          </div>
          <div class="modal-body">
            <label>请确认是否删除用户：{{editUserInfo.username}}</label>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">&nbsp;取消&nbsp;</button>
            <button type="button" class="btn btn-primary" data-dismiss="modal" @click="deleteUserInfo()">&nbsp;确认&nbsp;</button>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
  import {mapGetters} from 'vuex'
  export default {
    name: 'userManager',
    data () {
      return {
        showAddAdmin: false,
        showAddSuperAdmin: false,
        addUser: {
          username: '',
          password: '',
          passwordRepeat: '',
          name: '',
          organId: '',
          roles: '',
        },
        editUserInfo: {
          index: '',
          id: '',
          username: '',
          password: '******',
          name: '',
          email: '',
          phone: '',
          organ: '',
          roles: '',
        },
        userList: [],
        organList: [],
      }
    },
    watch: {
      userList: function (value) {
        let index = value.findIndex((user) => user.id === this.userId)
        if (index > -1) {
            value.splice(index, 1)
        }
      }
    },
    computed: {
      ...mapGetters({
        roles: 'roles',
        userId: 'userId',
        organId: 'organId',
      }),
      organShow: function () {
        return this.organList.length !== 0
      },
      passwordEqual: function () {
        return this.addUser.password === this.addUser.passwordRepeat
      }
    },
    filters: {
      showRoles: function (value) {
        if (typeof value === 'string')
        {
          switch (value) {
            case 'superAdmin':
              return '超级管理员'
            case 'admin':
              return '管理员'
            case 'user':
              return '用户'
          }
        } else {
          switch (value[0]) {
            case 'superAdmin':
              return '超级管理员'
            case 'admin':
              return '管理员'
            case 'user':
              return '用户'
          }
        }

      },
    },
    methods: {
      showOrgan: function (organId) {
        if (organId) {
          let organ = this.organList.find((n) => n.id === organId)
          if (organ) {
            return organ.name
          }
        } else {
            return 'Root'
        }
      },
      toggleEditUser: function (index) {
        this.editUserInfo.index = index
        this.editUserInfo.username = this.userList[index].username
        this.editUserInfo.name = this.userList[index].name
//        this.editUserInfo.password = this.userList[index].password
        this.editUserInfo.email = this.userList[index].email
        this.editUserInfo.phone = this.userList[index].phone
      },
      toggleDeleteUser: function (index) {
        this.editUserInfo.index = index
        this.editUserInfo.id = this.userList[index].id
        this.editUserInfo.username = this.userList[index].username
      },

      getAllUserInfo: function () {
        this.$http.get(global.server+'/user/users').then(response => {
          this.userList = JSON.parse(response.bodyText)
        }, response => {

        })
      },
      getAllUserInfoByOrganId: function () {
        let params = {'organId':this.organId}
        this.$http.get(global.server+'/user/organUsers', {params:params}).then(response => {
          this.userList = JSON.parse(response.bodyText)
        }, response => {

        })
      },
      addUserInfo: function () {
        let userInfo = {
          username: this.addUser.username,
          password: this.addUser.password,
          name: this.addUser.name,
          organId: this.addUser.organId || this.organId,
          roles: [this.addUser.roles],
        }
        this.$http.post(global.server+'/user', userInfo).then(response => {
          if (response.bodyText === 'EXIT') {
            toastr.warning("用户名已经存在")
            return
          }
          // toastr.success("添加普通用户成功")
          this.getAllUserInfo()
          this.addUser.username = null
          this.addUser.password = null
          this.addUser.passwordRepeat = null
          this.addUser.name = null
          this.addUser.organ = null
          this.addUser.roles = null
        }, response => {
          toastr.error("添加普通用户失败")
        })
      },
      updateUserInfo: function () {
        let userInfo = {
          id: this.userList[this.editUserInfo.index].id,
          username: this.editUserInfo.username,
          password: this.editUserInfo.password,
          name: this.editUserInfo.name,
          email: this.editUserInfo.email,
          phone: this.editUserInfo.phone,
          organId: this.editUserInfo.organ,
          roles: [this.editUserInfo.roles],
        }
        this.$http.put(global.server+'/user/id/'+userInfo.id, userInfo).then(response => {
          // toastr.success("更新普通用户信息成功")
          this.getAllUserInfo()
        }, response => {
          toastr.error("更新普通用户信息失败")
        })
      },
      deleteUserInfo: function () {
        this.$http.delete(global.server+'/user/id/'+this.editUserInfo.id).then(response => {
          // toastr.success("删除普通用户信息成功")
          this.getAllUserInfo()
        }, response => {
          toastr.error("删除普通用户信息失败")
        })
      },

      getAllOrganInfo: function () {
        this.$http.get(global.server+'/organ/organs').then(response => {
          this.organList = JSON.parse(response.bodyText)
        }, response => {

        })
      },
      getOrganInfo: function () {
        this.$http.get(global.server+'/organ/'+this.organId).then(response => {
          if (response.bodyText !== '') {
            this.organList[0] = JSON.parse(response.bodyText)
          }
        }, response => {

        })
      },
      getUserInfo: function () {
        if (typeof this.roles === 'string') {
          switch (this.roles)
          {
            case 'superAdmin':
              this.getAllUserInfo()
              this.getAllOrganInfo()
              this.showAddAdmin = true
              this.showAddSuperAdmin = true
              break
            case 'admin':
              this.getAllUserInfoByOrganId()
              this.getOrganInfo()
              this.showAddAdmin = true
              break
            case 'user':
              break
          }
        } else {
          switch (this.roles[0]) {
            case 'superAdmin':
              this.getAllUserInfo()
              this.getAllOrganInfo()
              this.showAddAdmin = true
              this.showAddSuperAdmin = true
              break
            case 'admin':
              this.getAllUserInfoByOrganId()
              this.getOrganInfo()
              this.showAddAdmin = true
              break
            case 'user':
              break
          }
        }
      }
    },



    created () {
      this.getUserInfo()
    }
  }

</script>

<style>

</style>
