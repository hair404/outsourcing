<template>
  <div class="wrapper">
    <!-- <div class="wrapper mt-4">
      <v-card outlined>
        <div
          id="basicinfo"
          class="select-card-content"
          style="top: 10px;"
        >
          <img style="float: left;max-width: 130px;margin: 3.5px;border-radius: 200px;">
          <div
            class="name"
            style="margin-top: 0;margin-bottom: 0;"
          ></div>
          <div class="name-right">
            <div
              class="select-card select-card-hover"
              style="width: 100px;"
              onclick="window.location.href='./display.html?id='+ myid;"
            >切换到展示页</div>
            <div
              id="edit"
              class="select-card select-card-hover"
              style="width: 100px;"
            >编辑资料</div>
          </div>
        </div>
        <div style="background-color: #EBEBEB;height: 42px;position: absolute;bottom: 0px;width: 100%;">
          <div
            class="categories nav tab"
            style="margin-left: 5%;"
          >
            <ul id="tab">
              <li value="0"><a>项目招标中</a></li>
              <li value="1"><a>项目进行中</a></li>
              <li value="2"><a>项目已完成</a></li>
            </ul>
            <div id="cursor"></div>
          </div>
        </div>
      </v-card>
    </div>
    <div class="wrapper">
      <div
        id="container"
        class="container"
        style="top: 20px;"
      ></div>

      <div
        id="modal"
        style="display: none"
      >
        <div
          class="modal_backgrd"
          onclick="$('modal').children[0].setAttribute('style','background: rgba(0, 0, 0, 0);');$('modal').children[1].setAttribute('style','top: 150%');$('modal').setAttribute('style','display: none;')"
        ></div>
        <div class="card modal">
          <div style="position: absolute;top: 25px;width: 100%;text-align: center;font-size: 16px;">项目注册</div>
          <form
            id="change"
            class="content"
            style="margin-top: 70px;padding-left: 10%;"
          >
            <input
              name="name"
              type="text"
              placeholder="项目名称"
              style="width: 100%"
              required
            >
            <div style="width: 100%">
              <select
                id="select_ctg"
                style="width: 49%;height: 50px;"
              ></select>
              <select
                id="select_subctg"
                style="width: 49%;height: 50px;"
              ></select>
            </div>
            <input
              type="button"
              class="select-card select-card-hover"
              onclick="$('fileInput').click()"
              style="width: 100%"
              value="上传头图"
            >
            <input
              type="file"
              style="display: none;"
              id="fileInput"
              name="upload"
              onchange="$('change').children[2].value=this.files[0].name;"
              accept="image/jpg,image/jpeg,image/png"
            >
            <input
              name="releaseTime"
              onfocus="this.setAttribute('type','date')"
              type="text"
              placeholder="发布时间"
              style="width: 100%"
              required
            >
            <input
              name="deadline"
              onfocus="this.setAttribute('type','date')"
              type="text"
              placeholder="完成日期"
              style="width: 100%"
              required
            >
            <textarea
              name="info"
              type="text"
              placeholder="相关信息"
              style="width: 100%"
              required
            ></textarea>
            <input
              name="price"
              type="number"
              placeholder="价格"
              style="width: 100%"
              required
            >
            <input
              type="submit"
              class="select-card select-card-hover"
              style="width: 100px;height: 42px;"
              value="创建"
            >
          </form>
        </div>
      </div>

      <div
        class="card"
        id="state1"
        style="display: none"
      >
        <div
          class="card"
          style="height: 106px;"
        >
          <div style="margin-left: 5%;font-size: 20px;line-height: 64px;display: inline-block;">资金流列表</div>
          <div
            id="title"
            style="width: 90%;margin: 0 auto;"
          >
            <div class="filename">资金去向</div>
            <div class="date">金额大小</div>
            <div class="passed">当前状态</div>
            <div style="line-height: 42px;display: inline-block;">操作</div>
          </div>
        </div>
        <table
          id="table"
          class="table"
        >
        </table>
      </div>

    </div> -->
    <v-container>
      <v-row v-resize="onResize">
        <v-col :cols="cols">
          <MyInfo
            :infoLoaded="infoLoaded"
            :info="info"
          />
        </v-col>
        <v-col>
          <v-card
            class="mx-auto"
            width="100%"
            height="400"
          ></v-card>
        </v-col>
      </v-row>
      <v-row>
        <v-col :cols="12">
          <v-card
            class="mx-auto my-2"
            width="100%"
            height="400"
          >
            <v-app-bar
              absolute
              color="primary"
              dark
            >
              <v-toolbar-title>我的项目</v-toolbar-title>
            </v-app-bar>
            <v-tabs>
              <v-tab>推荐工作室</v-tab>
              <v-tab>推荐项目</v-tab>
              <v-tab-item></v-tab-item>
            </v-tabs>
          </v-card>
        </v-col>
      </v-row>
    </v-container>

    <v-bottom-navigation
      class="d-flex d-md-none"
      color="primary"
      :value="activeBtn"
      shift
      absolute
      style="position: fixed;"
    >
      <v-btn>
        <span>数据中心</span>
        <v-icon>mdi-database</v-icon>
      </v-btn>

      <v-btn>
        <span>我的项目</span>
        <v-icon>mdi-progress-wrench</v-icon>
      </v-btn>

      <v-btn>
        <span>我的收藏</span>
        <v-icon>mdi-history</v-icon>
      </v-btn>
    </v-bottom-navigation>
  </div>
</template>

<script>
import MyInfo from '../components/MyInfo'

export default {
  components: {
    MyInfo
  },
  props: {
    info: Object,
    infoLoaded: Boolean
  },
  data () {
    return {
      cols: 3,
      activeBtn: 1
    }
  },
  methods: {
    onResize () {
      if (window.innerWidth < 800)
        this.cols = 12
      else
        this.cols = 3
    }
  }
}
</script>
