<template>
  <div id="app-wrapper">
  <!--<sideBar></sideBar>-->
  <div id="page-wrapper" class="gray-bg">
    <headBar></headBar>
    <router-view name="context"></router-view>
    <footBar></footBar>
    </div>
  </div>
</template>

<script>
  import HeadBar from '@/views/bar/headBar'
  import SideBar from '@/views/bar/sideBar'
  import FootBar from '@/views/bar/footBar'

  export default {
    name: 'mainpage',
    components: {'headBar': HeadBar,
      'sideBar': SideBar,
      'footBar': FootBar}
  }
</script>
