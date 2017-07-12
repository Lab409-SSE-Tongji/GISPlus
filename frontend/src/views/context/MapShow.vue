<template>
  <div class="row btns white-bg ">
    <div class="col-lg-12 btn-content">
      <button type="button" class="btn btn-success">选择显示图层</button>
      <button type="button" class="btn btn-primary btn-outline" :class="wellLayer.style" style="margin-left: 20px" v-show="wellLayerStatus" @click="toggleWellLayer()">窨井盖</button>
      <button type="button" class="btn btn-primary btn-outline" :class="waterPipeLayer.style" v-show="waterPipeLayerStatus" @click="toggleWaterPipeLayer()">下水管道</button>
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
        mapId: this.$route.params.mapId,
        baseMap: null,
        layers: {
          well: {},
          waterPipe: {},
        },
        wellLayer: {
          style: '',
          wellList: []
        },
        waterPipeLayer: {
          style: '',
          waterPipeList: []
        },
      }
    },
    computed: {
      // 图层存在状态
      wellLayerStatus: function () {
        return (this.layers.well.id !== undefined)
      },
      waterPipeLayerStatus: function () {
        return (this.layers.waterPipe.id !== undefined)
      },
    },
    methods: {
      // 注册点击事件
      registerWellClick: function (self, marker) {
        google.maps.event.addListener(marker, 'click', function() {
          if (marker.statusInfo.show) {
              marker.infoWindow.close(self.baseMap, marker)
            marker.statusInfo.show = false
          } else {
            marker.infoWindow = new google.maps.InfoWindow({
              // todo 下拉选择框
              content: marker.statusInfo.status,
            })
            marker.infoWindow.open(self.baseMap, marker)
            marker.statusInfo.show = true
          }
        })
      },
      registerWaterPipeClick: function (self, polyline) {
        google.maps.event.addListener(polyline, 'click', function() {
          if (polyline.statusInfo.show) {
            polyline.infoWindow.close(self.baseMap, polyline)
            polyline.statusInfo.show = false
          } else {
            polyline.infoWindow = new google.maps.InfoWindow({
              // todo 下拉选择框
              content: polyline.statusInfo.status,
              position: polyline.statusInfo.position,
            })
            polyline.infoWindow.open(self.baseMap, polyline)
            polyline.statusInfo.show = true
          }
        })
      },

      // 初始化
      initWell: function () {
        let wells = this.layers.well.wellDomains
        for (let well of wells) {
          let position = {lat: well.y*1, lng: well.x*1}
          let icon = {
            url: '../../../static/img/well/well_blue.png',
            scaledSize: new google.maps.Size(30, 30)
          }
          let marker = new google.maps.Marker({
            position: position,
            icon: icon,
            animation: google.maps.Animation.DROP,
            statusInfo: {
              show: false,
              status: well.status,
            }
          })
          this.registerWellClick(this, marker)
          this.wellLayer.wellList.push(marker)
        }
      },
      initWaterPipe: function () {
        let waterPipes = this.layers.waterPipe.waterPipeDomains
        for (let waterPipe of waterPipes) {
          let start = new google.maps.LatLng(waterPipe.y1 * 1, waterPipe.x1 * 1)
          let end = new google.maps.LatLng(waterPipe.y2 * 1, waterPipe.x2 * 1)
          let path = [start, end]
          let polyline = new google.maps.Polyline({
            path: path,
            strokeColor: "#0000FF",
            strokeOpacity: 1,
            strokeWeight: 4,
            statusInfo: {
              show: false,
              status: waterPipe.status,
              position: {
                lat: (waterPipe.y1*1 + waterPipe.y2*1) / 2,
                lng: (waterPipe.x1*1 + waterPipe.x2*1) / 2,
              },
            }
          })
          this.registerWaterPipeClick(this, polyline)
          this.waterPipeLayer.waterPipeList.push(polyline)
        }
      },


      // 窨井盖
      getWellLayer: function () {
        this.$http.get(global.server+'/layer/well/'+this.mapId).then(response => {
          // vue 更新对象属性的方式
          if (response.bodyText === '') {
            this.$set(this.layers, 'well', {})
          } else {
            this.$set(this.layers, 'well', JSON.parse(response.bodyText))
            this.initWell()
          }
          toastr.success("获取窨井盖层成功22")
        }, response => {
          toastr.error("获取窨井盖层失败")
        })
      },

      // 下水管道
      getWaterPipeLayer: function () {
        this.$http.get(global.server+'/layer/water/'+this.mapId).then(response => {
          // vue 更新对象属性的方式
          if (response.bodyText === '') {
            this.$set(this.layers, 'waterPipe', {})
          } else {
            this.$set(this.layers, 'waterPipe', JSON.parse(response.bodyText))
            this.initWaterPipe()
          }
          toastr.success("获取下水管道层成功")
        }, response => {
          toastr.error("获取下水管道层失败")
        })
      },

      // 渲染图层
      toggleWellLayer: function () {
        switch (this.wellLayer.style) {
          case '':
            // 显示
            this.wellLayer.style = 'active'
            for (let well of this.wellLayer.wellList) {
              well.setMap(this.baseMap)
            }
            break
          case 'active':
            // 删除
            this.wellLayer.style = ''
            for (let well of this.wellLayer.wellList) {
                well.setMap(null)
            }
            break
        }
      },
      toggleWaterPipeLayer: function () {
        switch (this.waterPipeLayer.style) {
          case '':
            // 显示
            this.waterPipeLayer.style = 'active'
            for (let waterPipe of this.waterPipeLayer.waterPipeList) {
              waterPipe.setMap(this.baseMap)
            }
            break
          case 'active':
            // 删除
            this.waterPipeLayer.style = ''
            for (let waterPipe of this.waterPipeLayer.waterPipeList) {
              waterPipe.setMap(null)
            }
            break
        }
      }
    },
    mounted () {
      // 构建Google地图
      let mapProp = {
        center: new google.maps.LatLng(31.285, 121.215),
        zoom: 17,
        mapTypeId: google.maps.MapTypeId.ROADMAP
      }
      this.baseMap = new google.maps.Map(document.getElementById('map'), mapProp);


      // 获取各个图层
      this.getWellLayer()
      this.getWaterPipeLayer()
    }
  }
</script>
<style>

</style>
