<template>

  <div class="text-center animated fadeInDown">
    <div>
      <div>
        <h1 class="logo-name">WebGIS</h1>
      </div>
      <h3>注册WebGIS账号</h3>

      <form class="m-t middle-box loginscreen ">
        <div class="form-group ">
          <input  type="text" class="form-control" placeholder="账号" v-model="username" name="username" v-validate data-vv-rules="required" >
          <span v-show="errors.has('username')" class="help is-danger" style="color: #ed5565">{{ errors.first('username') }}</span>
        </div>
        <div class="form-group">
          <input type="text" class="form-control" placeholder="昵称" v-model="name" name="name" v-validate data-vv-rules="required">
          <span v-show="errors.has('name')" class="help is-danger" style="color: #ed5565">{{ errors.first('name') }}</span>
        </div>
        <div class="form-group">
          <input type="password" class="form-control" placeholder="密码" v-model="password" name="password" v-validate data-vv-rules="required">
          <span v-show="errors.has('password')" class="help is-danger" style="color: #ed5565">{{ errors.first('password') }}</span>
        </div>
        <div class="form-group">
          <input type="password" class="form-control" placeholder="确认密码" v-model="passwordRepeat">
          <span v-show="!passwordEqual" class="help is-danger" style="color: #ed5565">The password should be the same.</span>
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
        name: '',
        username: '',
        password: '',
        passwordRepeat: '',
      }
    },
    computed: {
      passwordEqual: function () {
        return this.password === this.passwordRepeat
      }
    },
    methods: {
      register: function () {
        let registerInfo = {
          name: this.name,
          username: this.username,
          password: this.password
        }
        this.$http.post(global.server+'/auth/register', registerInfo).then(response => {
          toastr.success("注册成功")
          this.$router.push('login')
        }, response => {
          toastr.success("请检查输入")
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
