<template>
  <div class="row btns white-bg ">
    <div class="col-lg-12 btn-content">
      <button type="button" class="btn btn-success">选择显示图层</button>
      <button type="button" class="btn btn-primary btn-outline" :class="wellLayer.style" style="margin-left: 20px" v-show="wellLayerStatus" @click="toggleWellLayer()">窨井盖</button>
      <button type="button" class="btn btn-primary btn-outline" :class="waterPipeLayer.style" v-show="waterPipeLayerStatus" @click="toggleWaterPipeLayer()">下水管道</button>

      <button type="button" class="btn btn-sm btn-info" :class="statusStyle" style="float:right; margin-top: 17px; margin-left: 10px"v-show="rightOpShow" @click="showStatusBar()">状态</button>
      <button type="button" class="btn btn-sm btn-info" :class="style3D" style="float:right; margin-top: 17px; margin-left: 10px" v-show="rightOpShow3D" @click="show3DFun()">3D</button>

    </div>
    <!--状态选择条-->
    <div class="col-lg-12 btn-content" v-show="statusBar.show">
      <button type="button" class="btn btn-sm btn-success">选择显示状态</button>
      <button type="button" class="btn btn-sm btn-primary btn-outline" :class="statusBar.goodStyle" style="margin-left: 36px" @click="showGoodStatus()">正常</button>
      <button type="button" class="btn btn-sm btn-danger btn-outline" :class="statusBar.brokenStyle" @click="showBrokenStatus()">损坏</button>
      <button type="button" class="btn btn-sm btn-default btn-outline" :class="statusBar.lostStyle" @click="showLostStatus()">丢失</button>
    </div>


    <!--窨井盖信息窗-->
    <div class="hide" id="map-msg-point-parent">
      <div id="map-msg-point">
        <div class="form-group form-group-sm ">
          <i class="fa fa-map-marker icon"></i>
          <label>坐标</label>
          <br>
          <span>{{lat}}, {{lng}}</span>
        </div>
        <div class="form-group form-group-sm">
          <span>状态</span>
          <!--<select v-model="curPointStatus" class="form-control" :disabled="justShow">-->
            <!--<option value="0">选择状态</option>-->
            <!--<option value="GOOD">GOOD</option>-->
            <!--<option value="BROKEN">BROKEN</option>-->
            <!--<option value="LOST">LOST</option>-->
          <!--</select>-->
        </div>
        <div class="form-group form-group-sm">
          <button type="button" class="btn btn-danger btn-half-right" id="pointMapMsgBtn">关闭</button>
        </div>
      </div>
    </div>

    <!--下水管道信息窗-->
    <!--<div class="hide" id="map-msg-line-parent">-->
      <!--<div id="map-msg-line">-->
        <!--<div class="form-group form-group-sm ">-->
          <!--<i class="fa fa-map-marker icon"></i>-->
          <!--<label>起点</label>-->
          <!--<span>{{linex1}}, {{liney1}}</span>-->
        <!--</div>-->
        <!--<div class="form-group form-group-sm ">-->
          <!--<i class="fa fa-map-marker icon"></i>-->
          <!--<label>终点</label>-->
          <!--<span>{{linex2}}, {{liney2}}</span>-->
        <!--</div>-->
        <!--<div class="form-group form-group-sm">-->
          <!--<i class="fa fa-line-chart icon"></i>-->
          <!--<label>长度</label>-->
          <!--<span>{{lineDis}}</span>-->
        <!--</div>-->
        <!--<div class="form-group form-group-sm">-->
          <!--<span>状态</span>-->
          <!--&lt;!&ndash;<select v-model="curLineStatus" class="form-control" disabled="justShow">&ndash;&gt;-->
            <!--&lt;!&ndash;<option value="0">选择状态</option>&ndash;&gt;-->
            <!--&lt;!&ndash;<option value="GOOD">GOOD</option>&ndash;&gt;-->
            <!--&lt;!&ndash;<option value="BROKEN">BROKEN</option>&ndash;&gt;-->
            <!--&lt;!&ndash;<option value="LOST">LOST</option>&ndash;&gt;-->
          <!--&lt;!&ndash;</select>&ndash;&gt;-->
        <!--</div>-->

        <!--<div class="form-group form-group-sm">-->
          <!--<button type="button" class="btn btn-danger btn-half-right" id="lineMapMsgBtn">关闭</button>-->
        <!--</div>-->
      <!--</div>-->
    <!--</div>-->



    <!--todo 自适应地图高度-->
    <div class="ibox-content" style="position: relative; padding: 0 0 0 0">
      <div id="main-canvas" style="position: absolute; height: 700px; width:50%; z-index: 1; border-right: 3px solid dimgrey; background-color: rgba(238, 238, 238, 0.6);" v-show="show3D">
      </div>
      <div id="map" style="position: relative; height: 700px">
      </div>
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

        lat: null,
        lng: null,
        linex1:null,
        liney1:null,
        linez1:0,
        linex2:null,
        liney2:null,
        linez2:0,
        lineDis:null,

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
        style3D: '',
        show3D: false,
        statusStyle: '',
        statusBar: {
          show: false,
          goodStyle: 'active',
          brokenStyle: 'active',
          lostStyle: 'active',
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
      // 右侧操作按钮显示
      rightOpShow: function () {
        return (this.wellLayer.style==='active') || (this.waterPipeLayer.style==='active')
      },
      rightOpShow3D: function () {
        return this.waterPipeLayer.style==='active'
      },
    },
    methods: {
      // 注册点击事件
      registerWellClick: function (self, marker) {
        google.maps.event.addListener(marker, 'click', function() {
          if (marker.statusInfo.show) {
            document.getElementById('map-msg-point-parent').append(document.getElementById('map-msg-point'));
            marker.infoWindow.close(self.baseMap, marker);
            marker.statusInfo.show = false
          } else {
            if(self.curPoint !== null){
              self.curPoint.infoWindow.ownClose();
              self.curPoint.statusInfo.show = false;
              self.curPoint = null;
              self.curPointStatus = null;
            }
            self.curPointStatus = marker.statusInfo.status;
            self.curPoint = marker;
            self.lat = marker.position.lat().toFixed(3);
            self.lng = marker.position.lng().toFixed(3);
            marker.infoWindow = new google.maps.InfoWindow({
              content: document.getElementById('map-msg-point')
            })
            marker.infoWindow.open(self.baseMap, marker);

            marker.infoWindow.ownClose = () => {
              document.getElementById('map-msg-point-parent').append(document.getElementById('map-msg-point'))
              marker.infoWindow.close(self.baseMap, marker)
            }

            google.maps.event.addListener(marker.infoWindow, 'domready', function () {
              var closeBtn = $('.gm-style-iw').next()
              closeBtn.hide()
              $('#pointMapMsgBtn').on('click', function (event) {
                marker.infoWindow.ownClose()
                marker.statusInfo.show = false
              })
            })
            marker.statusInfo.show = true
          }
        })
      },
      registerWaterPipeClick: function (self, polyline) {
        google.maps.event.addListener(polyline, 'click', function(event) {
          if (polyline.statusInfo.show) {
            document.getElementById('map-msg-line-parent').append(document.getElementById('map-msg-line'));
            polyline.infoWindow.close(self.baseMap, polyline)
            polyline.statusInfo.show = false
          } else {
            if(self.curLine !== null){
              self.curLine.infoWindow.ownClose();
              self.curLine.statusInfo.show = false;
              self.curLine = null;
              self.curLineStatus = null;
            }
            self.curLineStatus = polyline.statusInfo.status;
            self.curLine = polyline;

            let vertices = polyline.getPath();
            self.linex1 = vertices.getAt(0).lat().toFixed(3);
            self.liney1 = vertices.getAt(0).lng().toFixed(3);
            self.linez1 = polyline.originInfo.z1;
            self.linex2 = vertices.getAt(1).lat().toFixed(3);
            self.liney2 = vertices.getAt(1).lng().toFixed(3);
            self.linez2 = polyline.originInfo.z2;
            self.lineDis = self.getTwoPointsDis(vertices.getAt(0),vertices.getAt(1));

            polyline.infoWindow = new google.maps.InfoWindow({
              // todo 下拉选择框
//              content: polyline.statusInfo.status,
              content: document.getElementById('map-msg-line'),
              position: polyline.statusInfo.position,
            })
            polyline.infoWindow.open(self.baseMap, polyline);

            polyline.infoWindow.ownClose = () => {
              document.getElementById('map-msg-line-parent').append(document.getElementById('map-msg-line'));
              polyline.infoWindow.close(self.baseMap, polyline);
            }

            google.maps.event.addListener(polyline.infoWindow, 'domready', function () {
              var closeBtn = $('.gm-style-iw').next();
              closeBtn.hide();
              $('#lineMapMsgBtn').on('click', function (event) {
                polyline.infoWindow.ownClose()
                polyline.statusInfo.show = false
              })
            })

            polyline.statusInfo.show = true

            let originPoint = {x: event.latLng.lng(), y: event.latLng.lat()};
            $("#main-canvas").html("");
            render3D(originPoint, self.layers.waterPipe.waterPipeDomains)
          }

        })
      },

      // 初始化
      initWell: function () {
        let wells = this.layers.well.wellDomains
        for (let well of wells) {
          let position = {lat: well.y*1, lng: well.x*1}
          let url = null
          switch (well.status) {
            case 'GOOD':
                url = '../../../static/img/well/well_blue.png'
                  break
            case 'BROKEN':
                url = '../../../static/img/well/well_red.png'
                  break
            case 'LOST':
                url = '../../../static/img/well/well_black.png'
                  break
            default:
                url = '../../../static/img/well/well_blue.png'  //default GOOD
                  break
          }
          let icon = {
            url: url,
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
          let strokeColor = null
          switch (waterPipe.status) {
            case 'GOOD':
                strokeColor = "#0000FF"
                  break
            case 'BROKEN':
                strokeColor = "#ff2a39"
                  break
            case 'LOST':
                strokeColor = "#1e1e1e"
                  break
            default:
                strokeColor = "#0000FF"
                  break
          }
          let polyline = new google.maps.Polyline({
            path: path,
            strokeColor: strokeColor,
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
          // toastr.success("获取窨井盖层成功22")
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
          // toastr.success("获取下水管道层成功")
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
      },

      // 状态显示
      showStatusBar: function () {
        this.statusBar.show = !this.statusBar.show
        if (this.statusBar.show) {
          this.statusStyle = 'active'
        } else {
          this.statusStyle = ''
        }
      },
      showGoodStatus: function () {
        // 触发状态变更
        this.statusBar.goodStyle = (this.statusBar.goodStyle === '') ? 'active' : ''
        // 根据变更后的状态(当前状态)，执行处理
        if (this.wellLayer.style === 'active') {
          if (this.statusBar.goodStyle === 'active') {
            for (let googleWell of this.wellLayer.wellList) {
              if (googleWell.statusInfo.status === 'GOOD') {
                googleWell.setMap(this.baseMap)
              }
            }
          } else {
            for (let googleWell of this.wellLayer.wellList) {
              if (googleWell.statusInfo.status === 'GOOD') {
                googleWell.setMap(null)
              }
            }
          }
        }
        if (this.waterPipeLayer.style === 'active') {
          if (this.statusBar.goodStyle === 'active') {
            for (let googleWaterPipe of this.waterPipeLayer.waterPipeList) {
              if (googleWaterPipe.statusInfo.status === 'GOOD') {
                googleWaterPipe.setMap(this.baseMap)
              }
            }
          } else {
            for (let googleWaterPipe of this.waterPipeLayer.waterPipeList) {
              if (googleWaterPipe.statusInfo.status === 'GOOD') {
                googleWaterPipe.setMap(null)
              }
            }
          }
        }
      },
      showBrokenStatus: function () {
        this.statusBar.brokenStyle = (this.statusBar.brokenStyle === '') ? 'active' : ''
        if (this.wellLayer.style === 'active') {
          if (this.statusBar.brokenStyle === 'active') {
            for (let googleWell of this.wellLayer.wellList) {
              if (googleWell.statusInfo.status === 'BROKEN') {
                googleWell.setMap(this.baseMap)
              }
            }
          } else {
            for (let googleWell of this.wellLayer.wellList) {
              if (googleWell.statusInfo.status === 'BROKEN') {
                googleWell.setMap(null)
              }
            }
          }
        }
        if (this.waterPipeLayer.style === 'active') {
          if (this.statusBar.brokenStyle === 'active') {
            for (let googleWaterPipe of this.waterPipeLayer.waterPipeList) {
              if (googleWaterPipe.statusInfo.status === 'BROKEN') {
                googleWaterPipe.setMap(this.baseMap)
              }
            }
          } else {
            for (let googleWaterPipe of this.waterPipeLayer.waterPipeList) {
              if (googleWaterPipe.statusInfo.status === 'BROKEN') {
                googleWaterPipe.setMap(null)
              }
            }
          }
        }
      },
      showLostStatus: function () {
        this.statusBar.lostStyle = (this.statusBar.lostStyle === '') ? 'active' : ''
        if (this.wellLayer.style === 'active') {
          if (this.statusBar.lostStyle === 'active') {
            for (let googleWell of this.wellLayer.wellList) {
              if (googleWell.statusInfo.status === 'LOST') {
                googleWell.setMap(this.baseMap)
              }
            }
          } else {
            for (let googleWell of this.wellLayer.wellList) {
              if (googleWell.statusInfo.status === 'LOST') {
                googleWell.setMap(null)
              }
            }
          }
        }
        if (this.waterPipeLayer.style === 'active') {
          if (this.statusBar.lostStyle === 'active') {
            for (let googleWaterPipe of this.waterPipeLayer.waterPipeList) {
              if (googleWaterPipe.statusInfo.status === 'LOST') {
                googleWaterPipe.setMap(this.baseMap)
              }
            }
          } else {
            for (let googleWaterPipe of this.waterPipeLayer.waterPipeList) {
              if (googleWaterPipe.statusInfo.status === 'LOST') {
                googleWaterPipe.setMap(null)
              }
            }
          }
        }
      },

      show3DFun: function () {
        this.style3D = (this.style3D==='') ? 'active' : ''
        this.show3D = !this.show3D
      }
    },
    mounted () {
      // 构建Google地图
      let mapProp = {
        center: new google.maps.LatLng(31.285, 121.215),
        zoom: 17,
        mapTypeId: google.maps.MapTypeId.ROADMAP
      }
      this.baseMap = new google.maps.Map(document.getElementById('map'), mapProp)

      // 获取各个图层
      this.getWellLayer()
      this.getWaterPipeLayer()
    }
  }
</script>
<style>

</style>
