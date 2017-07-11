<template>
<div>

  <div class="ms-doc">

    <el-row>
      <el-col :span="24">
        <div class="grid-content">
          <div class="bg-purple-light">
            <img class="user-logo" src="../../../static/img/img.jpg">
            <h2>{{name}}</h2>
            <h2>{{userId}}</h2>
          </div>

          <!--todo 按钮布局-->
          <button type="button" class="btn btn-primary" @click="openEditor()">编辑</button>
          <button type="button" class="btn btn-primary">取消</button>
          <button type="button" class="btn btn-primary">保存</button>


          <div class="contact">
            <h1>联系方式</h1>
            <hr>
            <el-row :gutter="10">
              <el-col :xs="8" :sm="6" :md="4" :lg="3">
                <div class="item-head">
                  <span>邮箱</span>
                </div>
              </el-col>
              <el-col :xs="4" :sm="6" :md="8" :lg="9">
                <div class="bg-purple-light item-content">
                  <div class="input-message">
                    <span class="email">{{email}}</span>
                  </div>
                </div>
              </el-col>
            </el-row>

            <el-row :gutter="10">
              <el-col :xs="8" :sm="6" :md="4" :lg="3">
                <div class="item-head">
                  <span>手机</span>
                </div>
              </el-col>
              <el-col :xs="4" :sm="6" :md="8" :lg="9">
                <div class="bg-purple-light item-content">
                  <div class="input-message">
                    <span class="phone">{{phone}}</span>
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
                  <span>姓名</span>
                </div>
              </el-col>
              <el-col :xs="4" :sm="6" :md="8" :lg="9">
                <div class="bg-purple-light item-content">
                  <div class="input-message">
                    <span class="realname">{{name}}</span>
                  </div>
                </div>
              </el-col>
            </el-row>

            <el-row :gutter="10">
              <el-col :xs="8" :sm="6" :md="4" :lg="3">
                <div class="item-head">
                  <span>职务</span>
                </div>
              </el-col>
              <el-col :xs="4" :sm="6" :md="8" :lg="9">
                <div class="bg-purple-light item-content">
                  <div class="input-message">
                    <span>{{role}}</span>
                  </div>
                </div>
              </el-col>
            </el-row>
          </div>

          <div class="edit">
            <hr>
            <!-- <el-button :plain="true" type="success">修改资料</el-button> -->
          </div>
        </div>
      </el-col>
    </el-row>
  </div>

</div>
</template>

<script>
import {mapGetters} from 'vuex'
export default {
  data() {
    return {
      name: '我是名字',
      email: '我是邮箱',
      phone: '我是电话',
      role: '我是谁',
    }
  },
  computed: {
    ...mapGetters({
      userId: 'userId'
    })

  },
  methods: {
    getUserInfo: function () {
      this.$http.get(global.server+'/user/id/'+this.userId).then(response => {
        console.log(response.bodyText)
        toastr.success("获取用户信息成功")
      }, response => {
        toastr.error("获取用户信息失败")
      })
    }
  },
  created () {
    this.getUserInfo()
  }
}
</script>

<style scoped>
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
