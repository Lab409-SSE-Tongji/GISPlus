<template>
  <div class="row btns white-bg ">
    <div class="col-lg-12 btn-content">
      <button type="button" class="btn btn-primary"  @click="showLayersFun()">显示图层</button>
      <button type="button" class="btn btn-primary"  @click="editLayersFun()">编辑图层</button>
      <button type="button" class="btn btn-primary" >{{mapId}}</button>
      <!--<button type="button" class="btn btn-primary" >{{showLayers.well}}</button>-->
      <!--<button type="button" class="btn btn-primary" >{{showLayers.waterPipe}}</button>-->
      <button type="button" class="btn btn-primary" >{{editLayers.editLayerName}}</button>
      <button type="button" class="btn btn-primary" >{{layers}}</button>
    </div>

    <div class="col-lg-12 btn-content" v-show="showLayers.show">
      <button type="button" class="btn btn-sm btn-primary btn-outline" :class="showLayers.wellStyle" v-show="wellLayerStatus" @click="toggleShowWellLayer()">窨井盖</button>
      <button type="button" class="btn btn-sm btn-primary btn-outline" :class="showLayers.waterPipeStyle" v-show="waterPipeLayerStatus" @click="toggleShowWaterPipeLayer()">下水管道</button>

    </div>
    <div class="col-lg-12 btn-content" v-show="editLayers.show">
      <button type="button" class="btn btn-sm btn-success" style="margin-right: 10px" data-toggle="modal" data-target="#addLayerModal">新建</button>
      <button type="button" class="btn btn-sm btn-primary btn-outline" :class="EditWellStyle" v-show="wellLayerStatus" @click="tagEditWellLayer()">窨井盖</button>
      <button type="button" class="btn btn-sm btn-primary btn-outline" :class="EditWaterPipeStyle" v-show="waterPipeLayerStatus" @click="tagEditWaterPipeLayer()">下水管道</button>

      <button type="button" class="btn btn-sm btn-danger" style="float:right; margin-top: 17px; margin-left: 10px"
              data-toggle="modal" data-target="#deleteLayerModal" @click="toggleDeleteLayer()">删除</button>
      <button type="button" class="btn btn-sm btn-info" style="float:right; margin-top: 17px; margin-left: 10px">导出</button>
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
              <option>{{defaultLayerList.well}}</option>
              <option>{{defaultLayerList.waterPipe}}</option>
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
            <label>{{deleteMessage}}</label>
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
        map: null,
        mapId: this.$route.params.mapId,
        defaultLayerList: {
          addLayerName: '',
          well: '窨井盖',
          waterPipe: '下水管道'
        },
        showLayers: {
          show: false,
          well: false,
          waterPipe: false,
          wellStyle: '',
          waterPipeStyle: '',
        },
        editLayers: {
          show: false,
          editLayerName: null,
        },
        layers: {
          well: {},
          waterPipe: {},
        },
        deleteMessage: '',
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

      // 编辑条 图层状态
      /**
       * @return {string}
       */
      EditWellStyle: function() {
        return (this.editLayers.editLayerName===this.defaultLayerList.well) ? 'active' : ''
      },
      /**
       * @return {string}
       */
      EditWaterPipeStyle: function () {
        return (this.editLayers.editLayerName===this.defaultLayerList.waterPipe) ? 'active' : ''
      },

    },
    methods: {
      showLayersFun: function () {
        this.showLayers.show = !this.showLayers.show
      },
      editLayersFun: function () {
        this.editLayers.show = !this.editLayers.show
        this.editLayers.editLayerName = null
      },

      // 编辑条 操作
      tagEditWellLayer: function () {
        this.editLayers.editLayerName = (this.editLayers.editLayerName===this.defaultLayerList.well) ? null : this.defaultLayerList.well
      },
      tagEditWaterPipeLayer: function () {
        this.editLayers.editLayerName = (this.editLayers.editLayerName===this.defaultLayerList.waterPipe) ? null : this.defaultLayerList.waterPipe
      },

      // 显示条 操作
      toggleShowWellLayer: function () {
        this.showLayers.well = !this.showLayers.well
        this.showLayers.wellStyle = this.showLayers.well ? 'active' : ''
      },
      toggleShowWaterPipeLayer: function () {
        this.showLayers.waterPipe = !this.showLayers.waterPipe
        this.showLayers.waterPipeStyle = this.showLayers.waterPipe ? 'active' : ''
      },

      // 窨井盖
      getWellLayer: function () {
        this.$http.get(global.server+'/layer/well/'+this.mapId).then(response => {
          // vue 更新对象属性的方式
          if (response.bodyText === '') {
            this.$set(this.layers, 'well', {})
          } else {
            this.$set(this.layers, 'well', JSON.parse(response.bodyText))
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

      // 下水管道
      getWaterPipeLayer: function () {
        this.$http.get(global.server+'/layer/water/'+this.mapId).then(response => {
          // vue 更新对象属性的方式
          if (response.bodyText === '') {
            this.$set(this.layers, 'waterPipe', {})
          } else {
            this.$set(this.layers, 'waterPipe', JSON.parse(response.bodyText))
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
      importWaterPipeLayer: function () {

      },

      // 通用操作
      addLayer: function () {
        switch (this.editLayers.editLayerName) {
          case this.defaultLayerList.well:
            this.addWellLayer()
            break
          case this.defaultLayerList.waterPipe:
            this.addWaterPipeLayer()
            break
          default:
            toastr.error("请选择：要添加的图层")
            break
        }
      },
      toggleDeleteLayer: function () {
        if (this.editLayers.editLayerName) {
          this.deleteMessage = '请确认是否删除图层： ' + this.editLayers.editLayerName
        } else {
          this.deleteMessage = '请选择：要删除的图层'
        }
      },
      deleteLayer: function () {
        switch (this.editLayers.editLayerName) {
          case this.defaultLayerList.well:
            this.deleteWellLayer()
            break
          case this.defaultLayerList.waterPipe:
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
          case this.defaultLayerList.well:
            this.importWellLayer(file)
            break
          case this.defaultLayerList.waterPipe:
            this.importWaterPipeLayer(file)
            break
          default:
            toastr.error("请选择：要导入的图层")
            break
        }
      }
    },

    // 初始化
    mounted () {
      // 构建Google地图
      let mapProp = {
        center: new google.maps.LatLng(31.285, 121.215),
        zoom: 17,
        mapTypeId: google.maps.MapTypeId.ROADMAP
      }
      new google.maps.Map(document.getElementById('map'), mapProp);

      // 获取各个图层
      this.getWellLayer()
      this.getWaterPipeLayer()
    }


  }
</script>

<style>

</style>
