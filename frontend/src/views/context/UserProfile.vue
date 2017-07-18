<template>
<div>

  <div class="ms-doc">

    <el-row>
      <el-col :span="24">
        <div class="grid-content">
          <div class="bg-purple-light">
            <img class="user-logo" src="../../../static/img/img.jpg">
            <h2>{{name}}</h2>
          </div>
          <div class="contact">
            <h1>联系方式</h1>
            <hr>
            <el-row :gutter="10">
              <el-col :xs="8" :sm="6" :md="4" :lg="3">
                <div class="item-head">
                  <span>邮箱<span style="color:red;" v-show="isShow">*</span></span>
                </div>
              </el-col>
              <el-col :xs="4" :sm="6" :md="8" :lg="9">
                <div class="bg-purple-light item-content">
                  <div class="input-message">
                    <input type="text" :disabled="disabled" class="form-control" v-model="email">
                  </div>
                </div>
              </el-col>
            </el-row>

            <el-row :gutter="10">
              <el-col :xs="8" :sm="6" :md="4" :lg="3">
                <div class="item-head">
                  <span>手机<span style="color:red;" v-show="isShow">*</span></span>
                </div>
              </el-col>
              <el-col :xs="4" :sm="6" :md="8" :lg="9">
                <div class="bg-purple-light item-content">
                  <div class="input-message">
                    <input type="text" :disabled="disabled" class="form-control" v-model="phone">
                  </div>
                </div>
              </el-col>
            </el-row>
          </div>

          <div class="detail">
            <h1>个人资料</h1>
            <hr>
            <el-row :gutter="10">
              <el-col :xs="8" :sm="6" :md="4" :lg="3">
                <div class="item-head">
                  <span>姓名<span style="color:red;" v-show="isShow">*</span></span>
                </div>
              </el-col>
              <el-col :xs="4" :sm="6" :md="8" :lg="9">
                <div class="bg-purple-light item-content">
                  <div class="input-message">
                    <input type="text" :disabled="disabled" class="form-control" v-model="name">
                  </div>
                </div>
              </el-col>
            </el-row>

            <el-row :gutter="10">
              <el-col :xs="8" :sm="6" :md="4" :lg="3">
                <div class="item-head">
                  <span>职务<span style="color:red;" v-show="isShow">*</span></span>
                </div>
              </el-col>
              <el-col :xs="4" :sm="6" :md="8" :lg="9">
                <div class="bg-purple-light item-content">
                  <div class="input-message">
                    <input type="text" disabled class="form-control" v-model="showRoles">
                  </div>
                </div>
              </el-col>
            </el-row>

            <el-row :gutter="10" v-show="isShow">
              <el-col :xs="8" :sm="6" :md="4" :lg="3">
                <div class="item-head">
                  <span>密码<span style="color:red;" v-show="isShow">*</span></span>
                </div>
              </el-col>
              <el-col :xs="4" :sm="6" :md="8" :lg="9">
                <div class="bg-purple-light item-content">
                  <div class="input-message">
                    <input type="text" :disabled="disabled" class="form-control" v-model="password">
                  </div>
                </div>
              </el-col>
            </el-row>

            <el-row :gutter="10" v-show="isShow">
              <el-col :xs="8" :sm="6" :md="4" :lg="3">
                <div class="item-head">
                  <span>确认密码<span style="color:red;" v-show="isShow">*</span></span>
                </div>
              </el-col>
              <el-col :xs="4" :sm="6" :md="8" :lg="9">
                <div class="bg-purple-light item-content">
                  <div class="input-message">
                    <input type="text" :disabled="disabled" class="form-control" v-model="passwordRepeat">
                  </div>
                </div>
              </el-col>
            </el-row>

          </div>

          <div class="edit">
            <hr>
            <!-- <el-button :plain="true" type="success">修改资料</el-button> -->
            <!--todo 按钮布局-->
            <button type="button" class="btn btn-primary" v-show="!isShow" @click="openEditor()" style="float: right;">编辑</button>
            <button type="button" class="btn btn-primary" v-show="isShow" @click="save()" style="float: right;">保存</button>
            <button type="button" class="btn btn-primary" v-show="isShow" @click="cancel()" style="float: right; margin-right: 20px;">取消</button>

          </div>
        </div>
      </el-col>
    </el-row>
  </div>

</div>
</template>

<script>
import {mapGetters} from 'vuex';
export default {
  data() {
    return {
      name: '我是名字',
      email: 'Lucy@tongji.edu.cn',
      phone: '13122607767',
      password: '******',
      passwordRepeat: '******',
      role: '我是谁',
      isShow: false,
      disabled: true,
    }
  },
  filters: {
  },
  computed: {
    ...mapGetters({
      userId: 'userId',
      organId: 'organId',
      roles: 'roles',
    }),
    showRoles: function () {
      if (typeof this.roles === 'string') {
        switch (this.roles)
        {
          case 'superAdmin':
            return '超级管理员'
          case 'admin':
            return '管理员'
          case 'user':
            return '用户'
        }
      } else {
        switch (this.roles[0]) {
          case 'superAdmin':
            return '超级管理员'
          case 'admin':
            return '管理员'
          case 'user':
            return '用户'
        }
      }
    }
  },
  methods: {
    getUserInfo: function () {
      this.$http.get(global.server + '/user/id/' + this.userId).then(response => {
        let userInfo = JSON.parse(response.bodyText)
        this.name = userInfo.name
//        this.phone = userInfo.phone
//        this.email = userInfo.email
        // toastr.success("获取用户信息成功")
      }, response => {
        toastr.error("获取用户信息失败")
      })
    },
    updateUserInfo: function () {
      let formData = {
        id: this.userId,
        name: this.name,
        email: this.email,
        phone: this.phone,
        password: this.password,
      }
      this.$http.put(global.server+'/user/id/'+this.userId, formData).then(response => {
        this.getUserInfo()
      })
    },
    openEditor: function () {
      this.disabled = false
      this.isShow = true
    },
    cancel: function () {
      this.disabled = true
      this.isShow = false
      this.getUserInfo()
    },
    save: function () {
      this.disabled = true
      this.isShow = false
      this.updateUserInfo()
    }
  },
  created () {
    this.getUserInfo()
  }
}
</script>

<style scoped>
.editable-span{
	background: blue;
}
.notEditable-span{

}
.ms-doc {
  margin-top: 3%;
  width: 100%;
  max-width: 720px;
  font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Helvetica, Arial, sans-serif;
}

.el-row {
  margin-bottom: 20px;
  margin-left: 10%;
  &:last-child {
    margin-bottom: 0;
  }
}

.el-col {
  border-radius: 4px;
}

.bg-purple-light {
  background: #f3f3f4;
}

.user-logo {
  display: inline-block;
  vertical-align: middle;
  height: 8%;
  width: 8%;
  border-radius: 50%;
}

h2 {
  display: inline-block;
  margin-left: 5%;
}

h1 {
  font-size: 1.7rem;
  color: #2d3238;
  margin: 1em 0 0;
  font-weight: 400;
  line-height: 1.7;
}

hr {
  margin: .8em 0 1em;
  display: block;
  height: 1px;
  border: 0;
  border-top: 1px solid rgb(209, 229, 229);
  padding: 0;
}

.item-head {
  margin-bottom: 1em;
  padding-top: .75em;
  flex: 2;
  font-size: 14px;
  color: #333;
}

.item-content {
  flex: 6;
  padding-left: 5em;
  font-size: 14px;
  color: #333;
}

.input-message {
  float: left;
  min-width: 18em;
}

.input-message span {
  display: block;
  padding-top: .75em;
}
</style>
