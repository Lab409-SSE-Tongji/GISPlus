<template>
  <div id="app-wrapper">
  <navBar></navBar>
  <div id="page-wrapper" class="gray-bg">
    <headBar></headBar>
    <router-view name="context"></router-view>
    <footBar></footBar>
    </div>
  </div>
</template>

<script>
  import HeadBar from '@/views/bar/HeadBar'
  import NavBar from '@/views/bar/NavBar'
  import FootBar from '@/views/bar/FootBar'

  export default {
    name: 'mainpage',
    components: {'headBar': HeadBar,
      'navBar': NavBar,
      'footBar': FootBar}
  }
</script>
