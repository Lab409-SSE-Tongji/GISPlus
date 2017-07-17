<template>
  <div class="wrapper wrapper-content animated fadeInRight">
    <!--普通用户列表-->
    <div class="col-lg-3" v-for="(organ, index) in organList" @click="">
      <div class="contact-box center-version">
        <a>
          <img alt="image" class="img-circle" src="../../assets/rename-icon.png">
          <h3 class="m-b-xs"><strong>{{organ.name}}</strong></h3>
          <address class="m-t-md">
            <!--<strong>邮箱: {{organ.email}}</strong><br>-->
            <!--<abbr>电话: {{organ.phone}}</abbr>-->
          </address>
        </a>
        <div class="contact-box-footer">
          <div class="m-t-xs btn-group">
            <a class="btn btn-sm btn-white" data-toggle="modal" data-target="#modifyOrganInfo" @click="toggleEditor(index)"><i class="fa fa-asterisk"></i> 修改 </a>
            <a class="btn btn-sm btn-white" data-toggle="modal" data-target="#deleteOrganInfo" @click="toggleDeleter(index)"><i class="fa fa-trash-o"></i> 删除 </a>
          </div>
        </div>
      </div>
    </div>

    <!--添加组织按钮-->
    <div class="col-lg-3">
      <div class="contact-box center-version">
        <a data-toggle="modal" data-target="#addOrgan">
          <!--todo 图片改成 + 号-->
          <img alt="image" class="img-circle" style="margin-top: 50px; margin-bottom: 50px" src="../../assets/rename-icon.png">
        </a>
        <!--<div class="contact-box-footer" style="margin-top: 38px; margin-bottom: 12px">-->
          <!--<div class="m-t-xs btn-group">-->
            <!--<abbr>新建公司组织</abbr>-->
          <!--</div>-->
        <!--</div>-->
      </div>
    </div>

    <!--添加组织模态框-->
    <div class="modal fade" id="addOrgan" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
              aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">修改基础用户信息</h4>
          </div>
          <div class="modal-body">
            <div class="form-group has-success">
              用户名<input class="form-control" v-model="adder.name" :placeholder="addOrgan.username"/>
              <!--密码<input class="form-control" v-model="addUser.password" :placeholder="name.password"/>-->
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">&nbsp;取消&nbsp;</button>
            <button type="button" class="btn btn-primary" data-dismiss="modal" @click="addOrgan()">&nbsp;确认&nbsp;</button>
          </div>
        </div>
      </div>
    </div>

    <!--修改组织模态框-->
    <div class="modal fade" id="modifyOrganInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
              aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">修改基础用户信息</h4>
          </div>
          <div class="modal-body">
            <div class="form-group has-success">
              公司组织名称：<input class="form-control" v-model="editor.name" :placeholder="editor.name"/>
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">&nbsp;取消&nbsp;</button>
            <button type="button" class="btn btn-primary" data-dismiss="modal" @click="updateOrgan()">&nbsp;确认&nbsp;</button>
          </div>
        </div>
      </div>
    </div>

    <!--删除图层模态框-->
    <div class="modal fade" id="deleteOrganInfo" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
              aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">删除普通用户</h4>
          </div>
          <div class="modal-body">
            <label>请确认是否删除组织：{{editor.name}}</label>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">&nbsp;取消&nbsp;</button>
            <button type="button" class="btn btn-primary" data-dismiss="modal" @click="deleteOrgan()">&nbsp;确认&nbsp;</button>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
  export default {
    name: 'userManager',
    data () {
      return {
        organList: [],
        adder: {
          name: null,
        },
        editor: {
          id: null,
          name: null,
        }
      }
    },
    methods: {
      toggleEditor: function (index) {
        this.editor.id = this.organList[index].id
        this.editor.name = this.organList[index].name
      },
      toggleDeleter: function (index) {
        this.editor.id = this.organList[index].id
        this.editor.name = this.organList[index].name
      },

      getOrgans: function () {
        this.$http.get(global.server+'/organ/organs', this.addOrgan).then(response => {
          this.organList = JSON.parse(response.bodyText)
          this.adder.name = null
        }, response => {

        })
      },
      addOrgan: function () {
        this.$http.post(global.server+'/organ', this.adder).then(response => {
          this.getOrgans()
        }, response => {

        })
      },
      updateOrgan: function () {
        this.$http.put(global.server+'/organ', this.editor).then(response => {
          this.getOrgans()
        }, response => {

        })
      },
      deleteOrgan: function () {
        this.$http.delete(global.server+'/organ/'+this.editor.id).then(response => {
          this.getOrgans()
        }, response => {

        })
      }
    },
    created () {
      this.getOrgans()
    }
  }
</script>

<style>

</style>
