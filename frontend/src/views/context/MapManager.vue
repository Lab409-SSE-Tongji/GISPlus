<template>
  <div id="wrapper">

    <!--顶部按钮条-->
    <div class="row btns white-bg ">
      <div class="col-lg-12 btn-content">
        <button type="button" class="btn btn-primary" id="showtoast" v-on:click="" data-toggle="modal" data-target="#addMapModal">新建地图</button>
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

      <!--通用弹出框-->
      <div class="modal fade" id="addMapModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
              <h4 class="modal-title" id="myModalLabel">{{addMapModal.title}}</h4>
            </div>
            <div class="modal-body">
              <input class="form-control" v-model="addMapModal.mapName" v-bind:placeholder="addMapModal.message"/>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default" data-dismiss="modal">&nbsp;取消&nbsp;</button>
              <button type="button" class="btn btn-primary" data-dismiss="modal" v-on:click="addMap()">&nbsp;确认&nbsp;</button>
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
      loading: false,
      addMapModal: {
        title: '新建地图',
        mapName: '',
        message: '请输入地图名称',
      }
    }
  },
  computed: {
    ...mapGetters({
      userId: 'userId'
    })
  },
  methods: {
    addMap: function () {
      let mapInfo = {
        userId : this.userId,
        mapName: this.addMapModal.mapName,
      }
      this.addMapModal.mapName = ''
      this.$http.post(global.server+'/map', mapInfo).then(response => {
        toastr.success("添加成功")
      }, response => {
        toastr.error("添加失败")
      })

    }
  }
}
</script>

<style>

</style>
