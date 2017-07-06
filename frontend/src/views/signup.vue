<template>

  <div class="text-center animated fadeInDown">
    <div>
      <div>
        <h1 class="logo-name">WebGIS</h1>
      </div>
      <h3>注册WebGIS账号</h3>

      <form class="m-t middle-box loginscreen" role="form">
        <div class="form-group">
          <input v-validate data-vv-rules="required|email" type="text" class="form-control" placeholder="账号" v-model="username" >
        </div>
        <div class="form-group">
          <input type="text" class="form-control" placeholder="昵称" v-model="name">
        </div>
        <div class="form-group">
        <input type="password" class="form-control" placeholder="密码" v-model="password">
        </div>
        <div class="form-group">
          <input type="password" class="form-control" placeholder="确认密码" v-model="passwordRepeat">
        </div>
        <div class="form-group">
          <div class="checkbox i-checks"><label> <input type="checkbox"><i></i> 同意相关条款与政策 </label></div>
        </div>
        <button type="button" class="btn btn-primary block full-width m-b" @click="register">注册</button>

        <p class="text-muted text-center"><small>已有账号?</small></p >
        <router-link class="btn btn-sm btn-white btn-block"  to="/login">登录</router-link >
      </form>

    </div>
  </div>
</template>



<script>
  // todo 表达验证
  export default{
    name: "signup",
    data () {
      return {
        requestUrl: global.server + '/auth/register',
        name: '',
        username: '',
        password: '',
        passwordRepeat: '',
      }
    },
    methods: {
      register: function () {
        let formData = {
          name: this.name,
          username: this.username,
          password: this.password
        }
        this.$http.post(this.requestUrl, formData).then(response => {
          toastr.success("注册成功")
        }, response => {
          toastr.success("注册失败")
        })
      }
    }
  }

</script>

<style scoped>
  .logo-name {
    font-size: 130px;
  }
</style>
