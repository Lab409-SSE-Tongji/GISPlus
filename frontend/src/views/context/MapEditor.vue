<template>
  <div class="row btns white-bg ">
    <!--顶层操作条-->
    <div class="col-lg-12 btn-content">
      <button type="button" class="btn btn-primary"  @click="toggleShowLayers()">显示图层</button>
      <button type="button" class="btn btn-primary"  @click="toggleEditLayers()">编辑图层</button>
    </div>

    <!--显示条-->
    <div class="col-lg-12 btn-content" v-show="showLayers.show">
      <button type="button" class="btn btn-sm btn-success">选择显示图层</button>
      <button type="button" class="btn btn-sm btn-primary btn-outline" :class="showLayers.wellStyle" style="margin-left: 20px" v-show="wellExist" @click="toggleShowWellLayer()">窨井盖</button>
      <button type="button" class="btn btn-sm btn-primary btn-outline" :class="showLayers.waterPipeStyle" v-show="waterPipeExist" @click="toggleShowWaterPipeLayer()">下水管道</button>

      <button type="button" class="btn btn-sm btn-info" :class="statusStyle" style="float:right; margin-top: 17px; margin-left: 10px"v-show="rightOpShow" @click="showStatusBar()">状态</button>
      <button type="button" class="btn btn-sm btn-info" :class="style3D" style="float:right; margin-top: 17px; margin-left: 10px" v-show="rightOpShow" @click="show3DFun()">3D</button>
    </div>

    <!--状态选择条-->
    <div class="col-lg-12 btn-content" v-show="statusBar.show">
      <button type="button" class="btn btn-sm btn-success">选择显示状态</button>
      <button type="button" class="btn btn-sm btn-primary btn-outline" :class="statusBar.goodStyle" style="margin-left: 36px" @click="showGoodStatus()">正常</button>
      <button type="button" class="btn btn-sm btn-danger btn-outline" :class="statusBar.brokenStyle" @click="showBrokenStatus()">损坏</button>
      <button type="button" class="btn btn-sm btn-default btn-outline" :class="statusBar.lostStyle" @click="showLostStatus()">丢失</button>
    </div>

    <!--操作条-->
    <div class="col-lg-12 btn-content" v-show="editLayers.show">
      <button type="button" class="btn btn-sm btn-success" style="margin-right: 10px" data-toggle="modal" data-target="#addLayerModal">新建</button>
      <button type="button" class="btn btn-sm btn-primary btn-outline" :class="editWellStyle" v-show="wellExist" @click="toggleEditWellLayer()">窨井盖</button>
      <button type="button" class="btn btn-sm btn-primary btn-outline" :class="editWaterPipeStyle" v-show="waterPipeExist" @click="toggleEditWaterPipeLayer()">下水管道</button>

      <button type="button" class="btn btn-sm btn-danger" style="float:right; margin-top: 17px; margin-left: 10px"
              data-toggle="modal" data-target="#deleteLayerModal" @click="toggleDeleteLayer()">删除</button>
      <button type="button" class="btn btn-sm btn-info" style="float:right; margin-top: 17px; margin-left: 10px" @click="exportFile()">导出</button>
      <input id="fileUpLoader" type="file" style="display: none" @change="importFile()" ref="input"/>
      <label for="fileUpLoader" class="btn btn-sm btn-info" style="float:right; margin-top: 17px; margin-left: 10px">导入</label>
      <button type="button" class="btn btn-sm btn-primary" style="float:right; margin-top: 17px; margin-left: 10px; margin-right: 20px" v-show="submitChangeShow" @click="submitChange()">提交更改</button>
      <button type="button" class="btn btn-sm btn-primary" style="float:right; margin-top: 17px; margin-left: 10px;" v-show="addLineShow" @click="addLine()">添加线</button>
      <button type="button" class="btn btn-sm btn-primary" style="float:right; margin-top: 17px; margin-left: 10px;" v-show="addPointShow" @click="addPoint()">添加点</button>
    </div>

    <!--添加图层模态框-->
    <div class="modal fade" id="addLayerModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
              aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">请选择要添加的图层</h4>
          </div>
          <div class="modal-body">
            <!--<select class="form-control m-b" >-->
            <select class="form-control m-b" v-model="editLayers.editLayerName">
              <!--todo 小bug 初始值问题-->
              <option disabled value="">请选择</option>
              <option>{{defaultLayer.well}}</option>
              <option>{{defaultLayer.waterPipe}}</option>
              <!--<option>option 3</option>-->
              <!--<option>option 4</option>-->
            </select>
            <!--<span>{{addLayerName}}</span>-->
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">&nbsp;取消&nbsp;</button>
            <button type="button" class="btn btn-primary" data-dismiss="modal" @click="addLayer()">&nbsp;确认&nbsp;</button>
          </div>
        </div>
      </div>
    </div>

    <!--删除图层模态框-->
    <div class="modal fade" id="deleteLayerModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
              aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">删除图层</h4>
          </div>
          <div class="modal-body">
            <label>{{editLayers.deleteMessage}}</label>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">&nbsp;取消&nbsp;</button>
            <button type="button" class="btn btn-primary" data-dismiss="modal" @click="deleteLayer()">&nbsp;确认&nbsp;</button>
          </div>
        </div>
      </div>
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
          <select v-model="curPointStatus" class="form-control">
            <option value="0">选择状态</option>
            <option value="GOOD">GOOD</option>
            <option value="BROKEN">BROKEN</option>
            <option value="LOST">LOST</option>
          </select>
        </div>

        <div class="form-group form-group-sm">
          <button type="button" @click="deletePointBtnClick()" class="btn btn-info btn-half-left">删除</button>
          <button type="button" class="btn btn-danger btn-half-right" id="pointMapMsgBtn">关闭</button>
        </div>
      </div>
    </div>

    <!--下水管道信息窗-->
    <div class="hide" id="map-msg-line-parent">
      <div id="map-msg-line">
        <div class="form-group form-group-sm ">
          <i class="fa fa-map-marker icon"></i>
          <label>起点</label>
          <span>{{linex1}}, {{liney1}},</span>
          <input id="linez1" v-model="linez1" type="number" step="0.01" style="width: 40px">
        </div>
        <div class="form-group form-group-sm ">
          <i class="fa fa-map-marker icon"></i>
          <label>终点</label>
          <span>{{linex2}}, {{liney2}},</span>
          <input id="linez2" v-model="linez2" type="number" step="0.01" style="width: 40px">
        </div>
        <div class="form-group form-group-sm">
          <i class="fa fa-line-chart icon"></i>
          <label>长度</label>
          <span>{{lineDis}}</span>
        </div>
        <div class="form-group form-group-sm">
          <select v-model="curLineStatus" class="form-control">
            <option value="0">选择状态</option>
            <option value="GOOD">GOOD</option>
            <option value="BROKEN">BROKEN</option>
            <option value="LOST">LOST</option>
          </select>
        </div>

        <div class="form-group form-group-sm">
          <button type="button" @click="deleteLineBtnClick()" class="btn btn-info btn-half-left">删除</button>
          <button type="button" class="btn btn-danger btn-half-right" id="lineMapMsgBtn">关闭</button>
          <!--<button type="button" class="btn btn-half-right" id="lineMapCommitBtn">提交</button>-->
        </div>
      </div>
    </div>

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
        clickTime:0,
        lineDis:null,
        curPoint: null,
        curPointStatus: null,
        curLine: null,
        curLineStatus: null,
        defaultLayer: {
          well: '窨井盖',
          waterPipe: '下水管道'
        },
        showLayers: {
          show: false,
          wellStyle: '',
          waterPipeStyle: '',
        },
        editLayers: {
          show: false,
          editLayerName: null,
          deleteMessage: '',
        },
        layers: {
          well: {},
          waterPipe: {},
          wellList: [],  //google 同步用
          waterPipeList: [],
        },
        curWellList: [],
        curWaterPipeList: [], //更新数据库用
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
      wellExist: function () {
        return (this.layers.well.id !== undefined)
      },
      waterPipeExist: function () {
        return (this.layers.waterPipe.id !== undefined)
      },

      // 编辑条 图层显示
      editWellStyle: function() {
        return (this.editLayers.editLayerName===this.defaultLayer.well) ? 'active' : ''
      },

      editWaterPipeStyle: function () {
        return (this.editLayers.editLayerName===this.defaultLayer.waterPipe) ? 'active' : ''
      },

      // 右侧操作按钮显示
      rightOpShow: function () {
        return (this.showLayers.wellStyle==='active') || (this.showLayers.waterPipeStyle==='active')
      },

      // 编辑按钮显示
      addPointShow: function () {
        return (this.editLayers.editLayerName===this.defaultLayer.well)
      },
      addLineShow: function () {
        return (this.editLayers.editLayerName===this.defaultLayer.waterPipe)
      },
      submitChangeShow: function () {
        return (this.editLayers.editLayerName===this.defaultLayer.well) || (this.editLayers.editLayerName===this.defaultLayer.waterPipe)
      }


    },
    watch: {
      rightOpShow: function () {
        if (!this.rightOpShow) {
            this.statusBar.show = false
        }
      },
      curPointStatus:function (val) {
        if(val === "GOOD"){
            this.curPoint.icon.url = '../../../static/img/well/well_blue.png';
        }else if(val === "BROKEN"){
            this.curPoint.icon.url = '../../../static/img/well/well_red.png';
        }else{
            this.curPoint.icon.url = '../../../static/img/well/well_black.png';
        }
        this.curPoint.statusInfo.status = val;
        this.curPoint.setMap(null);
        this.curPoint.setMap(this.baseMap);
        let i = this.findIndexFromListByValue(this.curWellList,this.curPoint.originInfo);
        this.curWellList[i].status = val;
      },
      curLineStatus:function (val) {
        if(val === "GOOD"){
          this.curLine.strokeColor = "#0000FF";
        }else if(val === "BROKEN"){
          this.curLine.strokeColor = "#ff2a39";
        }else{
          this.curLine.strokeColor = "#1e1e1e"
        }
        this.curLine.statusInfo.status = val;
        this.curLine.setMap(null);
        this.curLine.setMap(this.baseMap);
        let i = this.findIndexFromListByValue(this.curWaterPipeList,this.curLine.originInfo);
        this.curWaterPipeList[i].status = val;
      },
      linez1:function (val) {
        let i = this.findIndexFromListByValue(this.curWaterPipeList,this.curLine.originInfo);
        this.curWaterPipeList[i].z1 = val;
        this.curLine.originInfo.z1 = val;
      },
      linez2:function (val) {
        let i = this.findIndexFromListByValue(this.curWaterPipeList,this.curLine.originInfo);
        this.curWaterPipeList[i].z2 = val;
        this.curLine.originInfo.z2 = val;
      }
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

      // 同步Google对象
      syncGoogleWell: function () {
        for (let well of this.layers.well.wellDomains) {
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
            },
            originInfo: well,
          })
          this.registerWellClick(this, marker)
          this.layers.wellList.push(marker)
        }
      },
      syncGoogleWaterPipe: function () {
        for (let waterPipe of this.layers.waterPipe.waterPipeDomains) {
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
            },
            originInfo: waterPipe,
          })
          this.registerWaterPipeClick(this, polyline)
          this.layers.waterPipeList.push(polyline)
        }
      },

      // 渲染图层  op: true 渲染， false 移除
      renderWellLayer: function (op = true) {
        for (let well of this.layers.wellList) {
          if (op) {
            well && well.setMap(this.baseMap)
          } else {
            well && well.setMap(null)
          }
        }
      },
      renderWaterPipeLayer: function (op = true) {
        for (let waterPipe of this.layers.waterPipeList) {
          if (op) {
            waterPipe && waterPipe.setMap(this.baseMap)
          } else {
            waterPipe && waterPipe.setMap(null)
          }
        }
      },

      // 一级菜单 操作
      toggleShowLayers: function () {
        this.showLayers.show = !this.showLayers.show
        this.editLayers.show = false
        this.editLayers.editLayerName = null
        this.renderWellLayer(false)
        this.renderWaterPipeLayer(false)
      },
      toggleEditLayers: function () {
        this.editLayers.show = !this.editLayers.show
        this.editLayers.editLayerName = null
        this.showLayers.show = false
        this.renderWellLayer(false)
        this.renderWaterPipeLayer(false)
      },

      // 二级菜单 显示栏 操作
      toggleShowWellLayer: function () {
        this.showLayers.well = !this.showLayers.well
        if (this.showLayers.well) {
          this.showLayers.wellStyle = 'active'
          this.renderWellLayer()
        } else {
          this.showLayers.wellStyle = ''
          this.renderWellLayer(false)
        }
      },
      toggleShowWaterPipeLayer: function () {
        this.showLayers.waterPipe = !this.showLayers.waterPipe
        if (this.showLayers.waterPipe) {
          this.showLayers.waterPipeStyle = 'active'
          this.renderWaterPipeLayer()
        } else {
          this.showLayers.waterPipeStyle = ''
          this.renderWaterPipeLayer(false)
        }
      },

      // 二级菜单 编辑栏 操作
      toggleEditWellLayer: function () {
        this.editLayers.editLayerName = (this.editLayers.editLayerName===this.defaultLayer.well) ? null : this.defaultLayer.well
        if (this.editLayers.editLayerName === this.defaultLayer.well) {
          this.renderWellLayer()
          this.renderWaterPipeLayer(false)
        } else {
          this.renderWellLayer(false)
          this.renderWaterPipeLayer(false)
        }
      },
      toggleEditWaterPipeLayer: function () {
        this.editLayers.editLayerName = (this.editLayers.editLayerName===this.defaultLayer.waterPipe) ? null : this.defaultLayer.waterPipe
        if (this.editLayers.editLayerName === this.defaultLayer.waterPipe) {
          this.renderWellLayer(false)
          this.renderWaterPipeLayer()
        } else {
          this.renderWellLayer(false)
          this.renderWaterPipeLayer(false)
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
            this.syncGoogleWell();
            this.curWellList = this.layers.well.wellDomains.concat();
          }
          toastr.success("获取窨井盖层成功")
        }, response => {
          toastr.error("获取窨井盖层失败")
        })
      },
      addWellLayer: function () {
        this.$http.post(global.server+'/layer/well/'+this.mapId).then(response => {
            if (response.bodyText === 'EXITS') {
              toastr.warning("窨井盖图层已经存在")
              return
            }
            toastr.success("添加窨井盖图层成功")
            this.getWellLayer()
          }, response => {
            toastr.error("添加窨井盖图层失败")
          }
        )
      },
      deleteWellLayer: function () {
        this.$http.delete(global.server+'/layer/well/'+this.layers.well.id).then(response => {
          toastr.success("删除窨井盖图层成功")
          this.getWellLayer()
        }, response => {
          toastr.error("删除窨井盖图层失败")
        })
      },
      importWellLayer: function (file) {
        let formData = new FormData();
        formData.append('file', file)
        this.$http.post(global.server+'/layer/well/excel/'+this.layers.well.id, formData).then(response => {
          toastr.success("上传窨井盖图层成功")
          this.getWellLayer()
        }, response => {
          toastr.error("上传窨井盖图层失败")
        })
      },
      exportWellLayer: function () {
        this.$http.get(global.server+'/layer/well/excel/'+this.layers.well.id).then(response => {
          // 下载文件
          let hiddenElement  = document.createElement('a')
          hiddenElement.href = response.bodyText
          hiddenElement.target  = '_blank'
          hiddenElement.download =  response.bodyText.split('/').slice(-1)[0]
          hiddenElement.click()
          toastr.success("导出窨井盖图层成功")
        }, response => {
          toastr.error("导出窨井盖图层失败")
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
            this.syncGoogleWaterPipe()
            this.curWaterPipeList = this.layers.waterPipe.waterPipeDomains.concat();
          }
          toastr.success("获取下水管道层成功")
        }, response => {
          toastr.error("获取下水管道层失败")
        })
      },
      addWaterPipeLayer: function () {
        this.$http.post(global.server+'/layer/water/'+this.mapId).then(response => {
            if (response.bodyText === 'EXITS') {
              toastr.warning("下水管道图层已经存在")
              return
            }
            toastr.success("添加下水管道图层成功")
            this.getWaterPipeLayer()
          }, response => {
            toastr.error("添加下水管道图层失败")
          }
        )
      },
      deleteWaterPipeLayer: function () {
        this.$http.delete(global.server+'/layer/water/'+this.layers.waterPipe.id).then(response => {
          toastr.success("删除下水管道图层成功")
          this.getWaterPipeLayer()
        }, response => {
          toastr.error("删除下水管道图层失败")
        })
      },
      importWaterPipeLayer: function (file) {
        let formData = new FormData();
        formData.append('file', file)
        this.$http.post(global.server+'/layer/water/excel/'+this.layers.waterPipe.id, formData).then(response => {
          toastr.success("上传下水管道图层成功")
          this.getWaterPipeLayer()
        }, response => {
          toastr.error("上传下水管道图层失败")
        })
      },
      exportWaterPipeLayer: function () {
        this.$http.get(global.server+'/layer/water/excel/'+this.layers.waterPipe.id).then(response => {
          // 下载文件
          let hiddenElement  = document.createElement('a')
          hiddenElement.href = response.bodyText
          hiddenElement.target  = '_blank'
          hiddenElement.download =  response.bodyText.split('/').slice(-1)[0]
          hiddenElement.click()
          toastr.success("导出下水管道图层成功")
        }, response => {
          toastr.error("导出下水管道图层失败")
        })
      },

      // 通用操作
      addLayer: function () {
        switch (this.editLayers.editLayerName) {
          case this.defaultLayer.well:
            this.addWellLayer()
            break
          case this.defaultLayer.waterPipe:
            this.addWaterPipeLayer()
            break
          default:
            toastr.error("请选择：要添加的图层")
            break
        }
      },
      toggleDeleteLayer: function () {
        if (this.editLayers.editLayerName) {
          this.editLayers.deleteMessage = '请确认是否删除图层： ' + this.editLayers.editLayerName
        } else {
          this.editLayers.deleteMessage = '请选择：要删除的图层'
        }
      },
      deleteLayer: function () {
        switch (this.editLayers.editLayerName) {
          case this.defaultLayer.well:
            this.deleteWellLayer()
            break
          case this.defaultLayer.waterPipe:
            this.deleteWaterPipeLayer()
            break
          default:
            toastr.error("请选择：要删除的图层")
            break
        }
        this.editLayers.editLayerName = null
      },
      // todo @onchange 缺陷　两次上传同一文件不会触发
      importFile: function () {
        let file = this.$refs.input.files[0]
        switch (this.editLayers.editLayerName) {
          case this.defaultLayer.well:
            this.importWellLayer(file)
            break
          case this.defaultLayer.waterPipe:
            this.importWaterPipeLayer(file)
            break
          default:
            toastr.error("请选择：要导入的图层")
            break
        }
      },
      exportFile: function () {
        switch (this.editLayers.editLayerName) {
          case this.defaultLayer.well:
            this.exportWellLayer()
            break
          case this.defaultLayer.waterPipe:
            this.exportWaterPipeLayer()
            break
          default:
            toastr.error("请选择：要导出的图层")
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
        if (this.showLayers.wellStyle === 'active') {
          if (this.statusBar.goodStyle === 'active') {
            for (let googleWell of this.layers.wellList) {
              if (googleWell.statusInfo.status === 'GOOD') {
                googleWell.setMap(this.baseMap)
              }
            }
          } else {
            for (let googleWell of this.layers.wellList) {
              if (googleWell.statusInfo.status === 'GOOD') {
                googleWell.setMap(null)
              }
            }
          }
        }
        if (this.showLayers.waterPipeStyle === 'active') {
          if (this.statusBar.goodStyle === 'active') {
            for (let googleWaterPipe of this.layers.waterPipeList) {
              if (googleWaterPipe.statusInfo.status === 'GOOD') {
                googleWaterPipe.setMap(this.baseMap)
              }
            }
          } else {
            for (let googleWaterPipe of this.layers.waterPipeList) {
              if (googleWaterPipe.statusInfo.status === 'GOOD') {
                googleWaterPipe.setMap(null)
              }
            }
          }
        }
      },
      showBrokenStatus: function () {
        this.statusBar.brokenStyle = (this.statusBar.brokenStyle === '') ? 'active' : ''
        if (this.showLayers.wellStyle === 'active') {
          if (this.statusBar.brokenStyle === 'active') {
            for (let googleWell of this.layers.wellList) {
              if (googleWell.statusInfo.status === 'BROKEN') {
                googleWell.setMap(this.baseMap)
              }
            }
          } else {
            for (let googleWell of this.layers.wellList) {
              if (googleWell.statusInfo.status === 'BROKEN') {
                googleWell.setMap(null)
              }
            }
          }
        }
        if (this.showLayers.waterPipeStyle === 'active') {
          if (this.statusBar.brokenStyle === 'active') {
            for (let googleWaterPipe of this.layers.waterPipeList) {
              if (googleWaterPipe.statusInfo.status === 'BROKEN') {
                googleWaterPipe.setMap(this.baseMap)
              }
            }
          } else {
            for (let googleWaterPipe of this.layers.waterPipeList) {
              if (googleWaterPipe.statusInfo.status === 'BROKEN') {
                googleWaterPipe.setMap(null)
              }
            }
          }
        }
      },
      showLostStatus: function () {
        this.statusBar.lostStyle = (this.statusBar.lostStyle === '') ? 'active' : ''
        if (this.showLayers.wellStyle === 'active') {
          if (this.statusBar.lostStyle === 'active') {
            for (let googleWell of this.layers.wellList) {
              if (googleWell.statusInfo.status === 'LOST') {
                googleWell.setMap(this.baseMap)
              }
            }
          } else {
            for (let googleWell of this.layers.wellList) {
              if (googleWell.statusInfo.status === 'LOST') {
                googleWell.setMap(null)
              }
            }
          }
        }
        if (this.showLayers.waterPipeStyle === 'active') {
          if (this.statusBar.lostStyle === 'active') {
            for (let googleWaterPipe of this.layers.waterPipeList) {
              if (googleWaterPipe.statusInfo.status === 'LOST') {
                googleWaterPipe.setMap(this.baseMap)
              }
            }
          } else {
            for (let googleWaterPipe of this.layers.waterPipeList) {
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
      },

      /* utils */
      removeListByValue: function (arr, val) {
        for(let i=0; i<arr.length; i++) {
          if(arr[i] === val) {
            arr.splice(i, 1);
            break;
          }
        }
      },

      findIndexFromListByValue: function (arr, val) {
        for(let i=0; i<arr.length; i++) {
          if(arr[i] === val) {
            return i;
          }
        }
      },

      deleteLineBtnClick: function () {
        this.curLine.infoWindow.ownClose();
        this.curLine.setMap(null);
        this.removeListByValue(this.curWaterPipeList,this.curLine.originInfo);
        this.curLine = null;
      },

      deletePointBtnClick: function () {
          this.curPoint.infoWindow.ownClose();
          this.curPoint.setMap(null);
          this.removeListByValue(this.curWellList,this.curPoint.originInfo);
          this.curPoint = null;
      },

      submitChange: function(){
        let well = this.layers.well;
        well.wellDomains = this.curWellList.concat();

        let waterPipe = this.layers.waterPipe;
        waterPipe.waterPipeDomains = this.curWaterPipeList.concat();

        this.$http.put(global.server+'/layer/well/update', well).then(response => {
          // vue 更新对象属性的方式
          if (response.bodyText === '') {

          } else {

          }
          toastr.success("更新窨井盖成功")
        }, response => {
          toastr.error("更新窨井盖层失败")
        });

        this.$http.put(global.server+'/layer/water/update', waterPipe).then(response => {
          // vue 更新对象属性的方式
          if (response.bodyText === '') {

          } else {

          }
          toastr.success("更新下水管成功")
        }, response => {
          toastr.error("更新下水管失败")
        })
      },

      addPoint: function () {
        this.baseMap.setOptions({ draggableCursor: 'crosshair' });
        let radius = 1;

        var mapClickListener = google.maps.event.addListener(this.baseMap, 'click', (event) => {
          this.baseMap.setOptions({ draggableCursor: '' });
          var latLng = event.latLng;
          this.lng = latLng.lng();
          this.lat = latLng.lat();

          let originInfo = {x:this.lng,y:this.lat,z:0,status:"GOOD"};
          if(this.checkPointConflict(latLng, radius)){
            google.maps.event.removeListener(mapClickListener);

            toastr.error('当前窨井盖与其他窨井盖有覆盖关系，无法创建！');
            return;
          }

          let position = {lat: this.lat, lng: this.lng};
          let url = '../../../static/img/well/well_blue.png';
          let icon = {
            url: url,
            scaledSize: new google.maps.Size(30, 30)
          };
          let marker = new google.maps.Marker({
            position: position,
            icon: icon,
            animation: google.maps.Animation.DROP,
            statusInfo: {
              show: false,
              status: "GOOD",
            },
            originInfo: originInfo,
          });
          marker.setMap(this.baseMap);
          this.registerWellClick(this, marker)
          this.layers.wellList.push(marker)
          this.curWellList.push(originInfo)

          google.maps.event.removeListener(mapClickListener);
        });
      },
      addLine: function () {
        let self = this;
        this.baseMap.setOptions({ draggableCursor: 'crosshair' });
        let mapClickListener = google.maps.event.addListener(this.baseMap, 'click', (event) => {
          if(self.clickTime ===0){
            var latLng = event.latLng;
            self.linex1 = latLng.lng();
            self.liney1 = latLng.lat();
            self.clickTime =1;
          }else{
            var latLng = event.latLng;
            self.linex2 = latLng.lng();
            self.liney2 = latLng.lat();
            let originInfo = {
              x1:self.linex1,
              x2:self.linex2,
              y1:self.liney1,
              y2:self.liney2,
              z1:self.linez1,
              z2:self.linez2,
              status:"GOOD"
            }

            let start = new google.maps.LatLng(self.liney1 * 1, self.linex1 * 1)
            let end = new google.maps.LatLng(self.liney2 * 1, self.linex2 * 1)
            let path = [start, end]
            let strokeColor = "#0000FF"
            let polyline = new google.maps.Polyline({
              path: path,
              strokeColor: strokeColor,
              strokeOpacity: 1,
              strokeWeight: 4,
              statusInfo: {
                show: false,
                status: "GOOD",
                position: {
                  lat: (self.liney1*1 + self.liney2*1) / 2,
                  lng: (self.linex1*1 + self.linex2*1) / 2,
                },
              },
              originInfo: originInfo,
            })
            polyline.setMap(self.baseMap);
            self.registerWaterPipeClick(self, polyline)
            self.layers.waterPipeList.push(polyline)
            self.curWaterPipeList.push(originInfo)
            self.clickTime = 0;
            this.baseMap.setOptions({ draggableCursor: '' });
            google.maps.event.removeListener(mapClickListener);
          }

        });
      },
      checkPointConflict: function (point, radius) {
        let self = this;
        let flag = false;

        this.layers.wellList.forEach(function (point2) {
          if(self.getTwoPointsDis(point2.getPosition(), point) <= Math.abs(point2.radius+radius))
            flag = true;
        });
        return flag;
      },
      /* google map utils */
      getTwoPointsDis: function (point1, point2 ) {
        return google.maps.geometry.spherical.computeDistanceBetween(point1, point2);
      },

    },

    // 初始化
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
