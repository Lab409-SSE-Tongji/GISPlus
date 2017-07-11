<template>
  <div class="row btns white-bg ">
    <!--顶层操作条-->
    <div class="col-lg-12 btn-content">
      <button type="button" class="btn btn-primary"  @click="toggleShowLayers()">显示图层</button>
      <button type="button" class="btn btn-primary"  @click="toggleEditLayers()">编辑图层</button>
      <button type="button" class="btn btn-primary" >{{mapId}}</button>
      <button type="button" class="btn btn-primary" >{{editLayers.editLayerName}}</button>
      <button type="button" class="btn btn-primary" >{{layers.well}}</button>
      <!--<button type="button" class="btn btn-primary" >{{layers.waterPipe}}</button>-->
      <!--<button type="button" class="btn btn-primary" >{{layers.wellList}}</button>-->
      <!--<button type="button" class="btn btn-primary" >{{layers.waterPipeList}}</button>-->
    </div>

    <!--显示条-->
    <div class="col-lg-12 btn-content" v-show="showLayers.show">
      <button type="button" class="btn btn-sm btn-success">选择显示图层</button>
      <button type="button" class="btn btn-sm btn-primary btn-outline" :class="showLayers.wellStyle" style="margin-left: 20px" v-show="wellExist" @click="toggleShowWellLayer()">窨井盖</button>
      <button type="button" class="btn btn-sm btn-primary btn-outline" :class="showLayers.waterPipeStyle" v-show="waterPipeExist" @click="toggleShowWaterPipeLayer()">下水管道</button>
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
          wellList: [],
          waterPipeList: [],
        },
      }
    },
    computed: {
      // 图层存在状态
      wellExist: function () {
        return (this.layers.well.id === undefined) ? false : true
      },
      waterPipeExist: function () {
        return (this.layers.waterPipe.id === undefined) ? false : true
      },


      // 编辑条 图层显示
      editWellStyle: function() {
        return (this.editLayers.editLayerName===this.defaultLayer.well) ? 'active' : ''
      },

      editWaterPipeStyle: function () {
        return (this.editLayers.editLayerName===this.defaultLayer.waterPipe) ? 'active' : ''
      },

    },
    methods: {
      // 渲染图层  op: true 渲染， false 移除
      renderWellLayer: function (op = true) {
        for (let well of this.layers.wellList) {
          if (op) {
            well.setMap(this.baseMap)
          } else {
            well.setMap(null)
          }
        }
      },
      renderWaterPipeLayer: function (op = true) {
        for (let waterPipe of this.layers.waterPipeList) {
          if (op) {
            waterPipe.setMap(this.baseMap)
          } else {
            waterPipe.setMap(null)
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
            // 初始化 窨井盖 markers
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
              })
              this.layers.wellList.push(marker)
            }
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
            // 初始化 polylines
            let waterPipes = this.layers.waterPipe.waterPipeDomains
            for (let waterPipe of waterPipes) {
              let start = new google.maps.LatLng(waterPipe.y1 * 1, waterPipe.x1 * 1)
              let end = new google.maps.LatLng(waterPipe.y2 * 1, waterPipe.x2 * 1)
              let path = [start, end]
              let polyline = new google.maps.Polyline({
                path: path,
                strokeColor: "#0000FF",
                strokeOpacity: 1,
                strokeWeight: 2
              })
              this.layers.waterPipeList.push(polyline)
            }
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
      // todo 如何优雅的接收上传文件
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
