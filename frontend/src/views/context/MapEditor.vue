<template>
  <div class="row btns white-bg ">
    <div class="col-lg-12 btn-content">
      <button type="button" class="btn btn-primary"  @click="showLayersFun()">显示图层</button>
      <button type="button" class="btn btn-primary"  @click="addLayersFun()">编辑图层</button>
      <button type="button" class="btn btn-primary" >{{mapId}}</button>
    </div>
    <div class="col-lg-12 btn-content" v-show="showLayers">
      <!--<button type="button" class="btn" :class="wellStatus">窨井盖</button>-->
      <!--<button type="button" class="btn" :class="waterPipeStatus">下水管道</button>-->

    </div>
    <div class="col-lg-12 btn-content" v-show="addLayers">
      <button type="button" class="btn btn-sm" :class="wellBtnStatus" @click="addWellLayer()">窨井盖</button>
      <button type="button" class="btn btn-sm" :class="waterPipeBtnStatus">下水管道</button>

      <button type="button" class="btn btn-sm btn-info" style="float:right; margin-top: 17px; margin-left: 10px" @click="importFile()">导入</button>
      <button type="button" class="btn btn-sm btn-info" style="float:right; margin-top: 17px; margin-left: 10px">导出</button>
      <button type="button" class="btn btn-sm btn-danger" style="float:right; margin-top: 17px; margin-left: 10px">删除</button>
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
      wellBtnStatus: function () {
        if (this.layers.well !== undefined) {
            return 'btn-success'
        } else {
            return ''
        }
      },
      waterPipeBtnStatus: function () {
        if (this.layers.waterPipe !== undefined) {
          return 'btn-success'
        } else {
          return ''
        }
      },
    },
    methods: {
      showLayersFun: function () {
        this.showLayers = !this.showLayers
      },
      addLayersFun: function () {
        this.addLayers = !this.addLayers
      },

      // 窨井盖
      getWellLayers: function () {
        this.$http.get(global.server+'/layer/well/'+this.mapId).then(response => {
          // vue 更新对象属性的方式
          this.$set(this.layers, 'well', JSON.parse(response.bodyText))
          toastr.success("获取窨井盖层成功")
        }, response => {
          toastr.error("获取窨井盖层失败")
        })
      },
      addWellLayer: function () {
        this.$http.post(global.server+'/layer/well/'+this.mapId).then(response => {
            if (response.bodyText === 'EXITS') {
              toastr.warning("图层已经存在")
              return
            }
            this.getWellLayers()
            toastr.success("添加图层成功")
          }, response => {
            toastr.error("添加图层失败")
          }
        )
      },
      importWellLayer: function () {

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
