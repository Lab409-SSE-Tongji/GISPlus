<template>
  <div id="wrapper">

    <!--顶部按钮条-->
    <div class="row btns white-bg ">
      <div class="col-lg-12 btn-content">
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addMapModal">新建地图</button>
      </div>
    </div>

    <!--主体展示块-->
    <div class="wrapper-content animated fadeInRight  white-bg">
      <!--loading 部分-->
      <div class="loading" v-if="loading">
        <div class="sk-circle">
          <div class="sk-circle1 sk-child"></div>
          <div class="sk-circle2 sk-child"></div>
          <div class="sk-circle3 sk-child"></div>
          <div class="sk-circle4 sk-child"></div>
          <div class="sk-circle5 sk-child"></div>
          <div class="sk-circle6 sk-child"></div>
          <div class="sk-circle7 sk-child"></div>
          <div class="sk-circle8 sk-child"></div>
          <div class="sk-circle9 sk-child"></div>
          <div class="sk-circle10 sk-child"></div>
          <div class="sk-circle11 sk-child"></div>
          <div class="sk-circle12 sk-child"></div>
        </div>
      </div>

      <!--文件-->
      <div class="ibox-div-map">
        <div class="ibox ibox-map" v-for="(map, index) in maps">
          <div class="img-box img-box-map" @click="enterMap(index)">
            <img class="map-img" src="../../assets/defaultMap.jpg">
          </div>
          <label class="name">{{map.mapName}}</label>
          <div class="checkBox-map">
          </div>
          <div class="op-map" @click="openOp(index)">
            <img class="op-icon" src="../../assets/op-icon.png">
          </div>
          <!--文件操作-->
          <ul class="op-list" v-show="map.opDisplay" style="left:113px; top:12px; display:block">
            <li data-toggle="modal" data-target="#editMapModal" ><img src="../../assets/rename-icon.png"><label>编辑</label></li>
            <li @click="deleteMap(index)"><img src="../../assets/delete-icon.png"><label>删除</label></li>
            <li data-toggle="modal" data-target="#deliverMap" @click="toggleDeliver(index)" v-show="deliverShow"><img src="../../assets/manage-icon.png"><label>分配</label></li>
          </ul>
        </div>
      </div>

      <!--新建地图模态框-->
      <div class="modal fade" id="addMapModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">新建地图</h4>
            </div>
            <div class="modal-body">
              <input class="form-control" v-model="mapName" placeholder="请输入地图名称" name="mapName" v-validate data-vv-rules="required" >
              <span v-show="errors.has('mapName')" class="help is-danger" style="color: #ed5565">请输入地图名称</span>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">&nbsp;取消&nbsp;</button>
              <button type="button" class="btn btn-primary" data-dismiss="modal" @click="addMap()">&nbsp;确认&nbsp;</button>
            </div>
          </div>
        </div>
      </div>

      <!--编辑地图模态框-->
      <div class="modal fade" id="editMapModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">编辑地图信息</h4>
            </div>
            <div class="modal-body">
              <input class="form-control" v-model="mapName" :placeholder="editName"/>
            </div>
            <div class="modal-footer">
              <button @click="closeOp()" type="button" class="btn btn-default" data-dismiss="modal">&nbsp;取消&nbsp;</button>
              <button @click="updateMapInfo()" type="button" class="btn btn-primary" data-dismiss="modal">&nbsp;确认&nbsp;</button>
            </div>
          </div>
        </div>
      </div>

      <!--分配用户模态框-->
      <div class="modal fade" id="deliverMap" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">分配地图</h4>
            </div>
            <div class="modal-body">
              <span>请选择用户</span>
              <select v-model="deliverUserId" class="form-control">
                <option v-for="(user, index) in userList" :value="user.id">{{user.name}}</option>
              </select>
            </div>
            <div class="modal-footer">
              <button @click="closeOp()" type="button" class="btn btn-default" data-dismiss="modal">&nbsp;取消&nbsp;</button>
              <button type="button" class="btn btn-primary" data-dismiss="modal" @click="deliverMap()">&nbsp;确认&nbsp;</button>
            </div>
          </div>
        </div>
      </div>

    </div>
  </div>

</template>

<script>
import {mapGetters} from 'vuex'
export default {
  name: 'manManager',
  data () {
    return {
      loading: true,
      operation: false,
      mapName: '',
      maps: [],
      editIndex: null,
      editId: null,
      editName: null,
      deliverUserId: null,
      userList: [],
    }
  },
  watch: {
    userList: function (value) {
      let index = value.findIndex((user) => user.id === this.userId)
      if (index > -1) {
        value.splice(index, 1)
      }
    }
  },
  computed: {
    ...mapGetters({
      userId: 'userId',
      organId: 'organId',
      roles: 'roles',
    }),
    deliverShow: function () {
      return this.roles !== 'user' && this.roles[0] !== 'user'
    }
  },
  methods: {
    // 超级管理员获取地图
    getAllMaps: function () {
      this.$http.get(global.server+'/map/allMaps').then(response => {
        this.maps = [...JSON.parse(response.bodyText)].map(ob => {ob.opDisplay=false; return ob})
        this.loading = false
        // toastr.success("获取用户地图成功")
      }, response => {
        toastr.error("获取用户地图失败")
      })
    },
    // 组织管理员获取地图
    getMapsByOrganId: function () {
      let params = {'organId': this.organId}
      this.$http.get(global.server+'/map/organMaps', {params: params}).then(response => {
        this.maps = [...JSON.parse(response.bodyText)].map(ob => {ob.opDisplay=false; return ob})
        this.loading = false
        this.getDelierMaps(this.userId)
        // toastr.success("获取用户地图成功")
      }, response => {
        toastr.error("获取用户地图失败")
      })
    },
    // 用户获取地图
    getMapsByUserId: function () {
      let params = {'userId': this.userId}
      this.$http.get(global.server+'/map/userMaps', {params: params}).then(response => {
        this.maps = [...JSON.parse(response.bodyText)].map(ob => {ob.opDisplay=false; return ob})
        this.loading = false
        this.getDelierMaps(this.userId)
        // toastr.success("获取用户地图成功")
      }, response => {
        toastr.error("获取用户地图失败")
      })
    },
    // 获取分配的地图
    getDelierMaps: function () {
      let params = {'userId': this.userId}
      this.$http.get(global.server+'/map/deliverMaps', {params: params}).then(response => {
        let maps = [...JSON.parse(response.bodyText)].map(ob => {ob.opDisplay=false; return ob})
        this.maps = this.maps.concat(maps)
      })
    },

    addMap: function () {
      let mapInfo = {
        userId : this.userId,
        organId : this.organId,
        mapName: this.mapName,
      }
      this.mapName = ''
      this.$http.post(global.server+'/map', mapInfo).then(response => {
        // toastr.success("添加成功")
        this.getMaps()
      }, response => {
        toastr.error("添加失败")
      })
    },
    getMaps: function () {
      if (typeof this.roles === 'string') {
        switch (this.roles) {
          case 'superAdmin':
            this.getAllMaps()
            break
          case 'admin':
            this.getMapsByOrganId()
            break
          case 'user':
            this.getMapsByUserId()
            break
        }
      } else {
        switch (this.roles[0]) {
          case 'superAdmin':
            this.getAllMaps()
            break
          case 'admin':
            this.getMapsByOrganId()
            break
          case 'user':
            this.getMapsByUserId()
            break
        }
      }
    },
    openOp: function (index) {
      this.maps[index].opDisplay = !this.maps[index].opDisplay
      this.editIndex = index
      this.editId = this.maps[index].id
      this.editName = this.maps[index].mapName
    },
    closeOp: function () {
      this.maps[this.editIndex].opDisplay = false
      this.editIndex = null
    },
    updateMapInfo: function () {
      let editIndex = this.editIndex
      let mapInfo = {
        id: this.maps[editIndex].id,
        mapName: this.mapName,
      }
      this.$http.put(global.server+'/map', mapInfo).then(response => {
        this.maps[editIndex].mapName = this.mapName
        // toastr.success("更新地图信息成功")
        this.mapName = null
      }, response => {
        toastr.error("更新地图信息失败")
      })
      this.closeOp()
    },
    deleteMap: function (index) {
      let id = this.maps[index].id
      this.$http.delete(global.server+'/map/'+id).then(response => {
        this.getMaps()
        // toastr.success("放入回收站成功")
      }, response => {
        toastr.error("放入回收站失败")
      })
      this.closeOp()
    },

    toggleDeliver: function (index) {
      this.editIndex = index
    },
    deliverMap: function () {
      let mapId = this.maps[this.editIndex].id
      let formData = new FormData()
      formData.append('mapId', mapId)
      formData.append('userId', this.deliverUserId)

      this.$http.post(global.server+'/map/deliver', formData).then(response => {
        if (response.bodyText === 'EXIT') {
          toastr.warning("地图已被分配给该用户")
        }
      })
      this.closeOp()
    },

    enterMap: function (index) {
      this.$router.push('/edit/'+this.maps[index].id)
    },

    getAllUserInfo: function () {
      this.$http.get(global.server+'/user/users').then(response => {
        this.userList = JSON.parse(response.bodyText)
      }, response => {

      })
    },
    getAllUserInfoByOrganId: function () {
      let params = {'organId':this.organId}
      this.$http.get(global.server+'/user/organUsers', {params:params}).then(response => {
        this.userList = JSON.parse(response.bodyText)
      }, response => {

      })
    },
    getUserInfo: function () {
      if (typeof this.roles === 'string') {
        switch (this.roles)
        {
          case 'superAdmin':
            this.getAllUserInfo()
            break
          case 'admin':
            this.getAllUserInfoByOrganId()
            break
          case 'user':
            break
        }
      } else {
        switch (this.roles[0]) {
          case 'superAdmin':
            this.getAllUserInfo()
            break
          case 'admin':
            this.getAllUserInfoByOrganId()
            break
          case 'user':
            break
        }
      }
    }
  },
  created () {
    this.getMaps()
    this.getUserInfo()
  }
}
</script>

<style>

</style>
