<template>
  <div class="wrapper wrapper-content animated fadeInRight">

  <!--普通用户列表-->
  <div class="col-lg-3" v-for="(user, index) in userList" @click="">
    <div class="contact-box center-version">
      <a href="#">
        <img alt="image" class="img-circle" src="../../assets/rename-icon.png">
        <h3 class="m-b-xs"><strong>{{user.username}}</strong></h3>
        <address class="m-t-md">
          <strong>邮箱: {{user.email}}</strong><br>
          <abbr>电话: {{user.phone}}</abbr><br>
          <abbr>公司: {{user.organ}}</abbr><br>
          <abbr>角色: {{user.roles}}</abbr>
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
        <img alt="image" class="img-circle" style="margin-top: 50px" src="../../assets/rename-icon.png">
      </a>
      <div class="contact-box-footer" style="margin-top: 38px; margin-bottom: 12px">
        <div class="m-t-xs btn-group">
          <abbr>新建普通用户</abbr>
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
          <h4 class="modal-title">修改基础用户信息</h4>
        </div>
        <div class="modal-body">
          <div class="form-group has-success">
            <span>用户名</span>
            <input class="form-control" v-model="addUser.username" :placeholder="addUser.username"/>
            <span>密码</span>
            <input type="password" class="form-control" v-model="addUser.password" :placeholder="addUser.password"/>
            <span>组织</span>
            <select v-model="addUser.organ" class="form-control">
              <!--<option value="0" selected>选择组织</option>-->
              <option :value="organ.id" v-for="(organ, index) in organList">{{organ.name}}</option>
            </select>
            <span>身份</span>
            <select v-model="addUser.roles" class="form-control">
              <!--<option value="0" selected>选择角色</option>-->
              <option value="user">用户</option>
              <option value="admin">管理员</option>
              <option value="superAdmin">超级管理员</option>
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
          <h4 class="modal-title">修改基础用户信息</h4>
        </div>
        <div class="modal-body">
          <div class="form-group has-success">
            用户名<input class="form-control" v-model="editUserInfo.username" :placeholder="editUserInfo.username"/>
            密码<input class="form-control" v-model="editUserInfo.password" :placeholder="editUserInfo.password"/>
            邮箱<input class="form-control" v-model="editUserInfo.email" :placeholder="editUserInfo.email"/>
            电话<input class="form-control" v-model="editUserInfo.phone" :placeholder="editUserInfo.phone"/>
          </div>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" data-dismiss="modal">&nbsp;取消&nbsp;</button>
          <button type="button" class="btn btn-primary" data-dismiss="modal" @click="updateUserInfo()">&nbsp;确认&nbsp;</button>
        </div>
      </div>
    </div>
  </div>

    <!--删除图层模态框-->
    <div class="modal fade" id="deleteUserInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
              aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">删除普通用户</h4>
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
        addUser: {
          username: '',
          password: '',
          organ: '',
          roles: '',
        },
        editUserInfo: {
          index: '',
          id: '',
          username: '',
          password: '******',
          email: '',
          phone: '',
        },
        userList: [],
        organList: [],
      }
    },
    computed: {
      ...mapGetters({
        userId: 'userId'
      })
    },
    methods: {
      toggleEditUser: function (index) {
        this.editUserInfo.index = index
        this.editUserInfo.username = this.userList[index].username
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
          console.log(this.userList)
        }, response => {

        })
      },
      addUserInfo: function () {
        let userInfo = {
          username: this.addUser.username,
          password: this.addUser.password,
          organ: this.addUser.organ,
          roles: [this.addUser.roles],
        }
        console.log(userInfo)
        this.$http.post(global.server+'/user', userInfo).then(response => {
            console.log(response)
          if (response.bodyText === 'EXIT') {
            toastr.warning("用户名已经存在")
            return
          }
          // toastr.success("添加普通用户成功")
          this.getAllUserInfo()
        }, response => {
          toastr.error("添加普通用户失败")
        })
      },
      updateUserInfo: function () {
        let userInfo = {
          id: this.userList[this.editUserInfo.index].id,
          username: this.editUserInfo.username,
          password: this.editUserInfo.password,
          email: this.editUserInfo.email,
          phone: this.editUserInfo.phone,
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
          console.log(this.organList)
        }, response => {

        })
      }
    },
    created () {
      this.getAllUserInfo()
      this.getAllOrganInfo()
    }
  }

</script>

<style>

</style>
