<template>
  <div class="row btns white-bg ">
    <div class="col-lg-12 btn-content">
      <button type="button" class="btn btn-primary"  @click="showLayersFun()">显示图层</button>
      <button type="button" class="btn btn-primary"  @click="addLayersFun()">创建图层</button>
      <button type="button" class="btn btn-primary" >{{mapId}}</button>
    </div>
    <div class="col-lg-12 btn-content" v-show="showLayers">
      <!--<button type="button" class="btn" :class="wellStatus">窨井盖</button>-->
      <!--<button type="button" class="btn" :class="waterPipeStatus">下水管道</button>-->

    </div>
    <div class="col-lg-12 btn-content" v-show="addLayers">
      <button type="button" class="btn" :class="" @click="addWellLayer()">窨井盖</button>
      <button type="button" class="btn" :class="">下水管道</button>
    </div>

    <!--todo 自适应地图高度-->
    <div class="ibox-content" id="map" style="position: relative; height: 700px">
    </div>
  </div>
</template>

<script>
  export default {
    name: 'mapEditor',
    data () {
      return {
        map: null,
        mapId: this.$route.params.mapId,
        showLayers: false,
        addLayers: false,
        layers: {},
        }
    },
    computed: {

    },
    methods: {
      showLayersFun: function () {
        this.showLayers = !this.showLayers
      },
      addLayersFun: function () {
        this.addLayers = !this.addLayers
      },
      getWellLayers: function () {
        this.$http.get(global.server+'/layer/well/'+this.mapId).then(response => {
          console.log(response)
          toastr.success("获取窨井盖层成功")
        }, response => {
          toastr.error("获取窨井盖层失败")
        })
      },
      addWellLayer: function () {
//        if (this.layerStatus.well === false) {
//          toastr.error("图层已经存在")
//          return
//        }
        this.$http.post(global.server+'/layer', {mapId: this.mapId}).then(response => {
            toastr.success("添加图层成功")
          }, response => {
            toastr.error("添加图层失败")
          }
        )
      }
    },
    mounted () {
      // 初始化地图
      let mapProp = {
        center: new google.maps.LatLng(31.285, 121.215),
        zoom: 17,
        mapTypeId: google.maps.MapTypeId.ROADMAP
      }
      new google.maps.Map(document.getElementById('map'), mapProp);

      // 获取窨井盖图层
      this.getWellLayers()
    }


  }
</script>

<style>

</style>
