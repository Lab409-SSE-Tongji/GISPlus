<template>
  <div class="row btns white-bg ">
    <div class="col-lg-12 btn-content">
      <button type="button" class="btn btn-primary"  @click="showLayersFun()">显示图层</button>
      <button type="button" class="btn btn-primary"  @click="editLayersFun()">编辑图层</button>
      <button type="button" class="btn btn-primary" >{{mapId}}</button>
      <button type="button" class="btn btn-primary" >{{layers}}</button>
    </div>
    <div class="col-lg-12 btn-content" v-show="showLayers.show">
      <button type="button" class="btn btn-sm btn-primary" :class="showLayers.wellStyle" v-show="wellLayerStatus">窨井盖</button>
      <button type="button" class="btn btn-sm btn-primary" :class="showLayers.waterPipeStyle" v-show="waterPipeLayerStatus">下水管道</button>

    </div>
    <div class="col-lg-12 btn-content" v-show="editLayers.show">
      <button type="button" class="btn btn-sm btn-success" style="margin-right: 10px" data-toggle="modal" data-target="#addLayerModal">新建</button>
      <button type="button" class="btn btn-sm btn-primary" :class="editLayers.wellStyle" v-show="wellLayerStatus" @click="tagEditWellLayer()">窨井盖</button>
      <button type="button" class="btn btn-sm btn-primary" :class="editLayers.waterPipeStyle" v-show="waterPipeLayerStatus">下水管道</button>

      <button type="button" class="btn btn-sm btn-danger" style="float:right; margin-top: 17px; margin-left: 10px"
              data-toggle="modal" data-target="#deleteLayerModal" @click="toggleDeleteLayer()">删除</button>
      <button type="button" class="btn btn-sm btn-info" style="float:right; margin-top: 17px; margin-left: 10px">导出</button>
      <button type="button" class="btn btn-sm btn-info" style="float:right; margin-top: 17px; margin-left: 10px" @click="importFile()">导入</button>

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
          wellStyle: 'btn-outline',
          waterPipeStyle: 'btn-outline',
        },
        editLayers: {
          show: false,
          editLayerName: null,
          wellStyle: 'btn-outline',
          waterPipeStyle: 'btn-outline',
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
    },
    methods: {
      showLayersFun: function () {
        this.showLayers.show = !this.showLayers.show
      },
      editLayersFun: function () {
        this.editLayers.show = !this.editLayers.show
      },

      tagEditWellLayer: function () {
        this.editLayers.editLayerName = this.editLayers.editLayerName? null : this.defaultLayerList.well
        this.editLayers.wellStyle = (this.editLayers.wellStyle==='')? 'btn-outline' : ''

      },

      // 窨井盖
      getWellLayers: function () {
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
      deleteWellLayer: function () {
        this.$http.delete(global.server+'/layer/well/'+this.layers.well.id).then(response => {
          this.getWellLayers()
          toastr.success("删除图层成功")
        }, response => {
          toastr.error("删除图层失败")
        })
      },

      // 通用操作
      addLayer: function () {
        if (this.editLayers.editLayerName === this.defaultLayerList.well) {
          this.addWellLayer()
        } else if (this.editLayers.editLayerName === this.defaultLayerList.waterPipe) {
          // todo
        }
      },
      toggleDeleteLayer: function () {
        if (this.editLayers.editLayerName !== null) {
          this.deleteMessage = '请确认是否删除图层： ' + this.editLayers.editLayerName
        } else {
          this.deleteMessage = '请选择要删除的图层'
        }
      },
      deleteLayer: function () {
        if (this.editLayers.editLayerName === this.defaultLayerList.well) {
          this.deleteWellLayer()
        } else if (this.editLayers.editLayerName === this.defaultLayerList.waterPipe) {
          // todo
        }
      },
      importFile: function () {

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
