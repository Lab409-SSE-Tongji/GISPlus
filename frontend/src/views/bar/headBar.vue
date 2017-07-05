<template>
  <div class="row border-bottom">
      <nav class="navbar navbar-static-top white-bg" role="navigation" style="margin-bottom: 0">
          <div class="navbar-header">
              <a class="minimalize-styl-2 btn btn-primary" @click="toggleMenu"><i class="fa fa-bars"></i> </a>
          </div>
          <ul class="nav navbar-top-links navbar-right">
              <li>
                  <router-link to="/" class="fa fa-sign-out" @click.native="logout">登出</router-link>
              </li>
          </ul>

      </nav>
  </div>
</template>

<script>
import {mapGetters, mapMutations} from 'vuex'

export default {
  name: 'headBar',
  methods: {
    ...mapMutations({
      s_logout: 'LOG_OUT'
    }),

    logout: function () {
      this.s_logout()
      this.$router.push('/');
    },

    toggleMenu: function () {
      $("body").toggleClass("mini-navbar");
    }
  },
//  created () {
//    this.toggleMenu()
//  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>

</style>
