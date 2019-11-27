<template>
  <div class="d-flex flex-wrap mx-auto">
    <div
      v-if="!loaded"
      class="d-flex flex-wrap justify-start"
      style="width: 100%"
    >
      <v-skeleton-loader
        v-for="i in 4"
        :key="i"
        class="mx-auto"
        min-width="170"
        width="24%"
        type="card"
      ></v-skeleton-loader>
    </div>
    <v-hover
      v-else
      v-for="(item,i) in cards"
      :key="i"
      v-slot:default="{ hover }"
    >
      <div class="grid">
        <v-card
          :elevation="hover ? 12 : 2"
          class="mx-auto my-2"
          @click="$router.push(type === 0?{name:'display',params:{id:item.id}}:{name:'detail',params:{id:item.solr_id}})"
          width="95%"
          min-height="200"
        >
          <v-img
            class="white--text align-end"
            height="170"
            :src="utils.baseURL + item.img"
          >
            <v-card-title>
              <v-avatar v-if="type === 0">
                <img :src="utils.baseURL+item.avatar" />
              </v-avatar>
              <div style="overflow-x: auto;">
                <div style="display: inline-flex">
                  <div
                    class="title"
                    style="white-space: nowrap"
                  >{{item.prjname?item.prjname:item.username}}</div>
                </div>
              </div>
            </v-card-title>
          </v-img>
          <v-card-text class="text--primary">
            <div
              v-if="item.credit"
              style="overflow-x: auto;"
            >
              <div style="display: inline-flex">
                <v-rating
                  v-model="item.credit"
                  color="orange"
                  background-color="orange"
                  readonly
                  half-increments
                  dense
                />
              </div>
            </div>
            <v-card-subtitle class="pa-0">类别</v-card-subtitle>
            <div
              v-if="type === 0"
              style="overflow-x: auto;"
            >
              <div style="display: inline-flex">
                <v-chip
                  outlined
                  class="ma-1"
                  v-for="(tag ,i) in item.tag"
                  :key="i"
                >{{utils.ctg[tag].name}}</v-chip>
              </div>
            </div>
            <div
              v-else
              class="d-flex"
            >
              <div style="overflow-x: auto;height: 30px">
                <div style="display: inline-flex">
                  <div style="white-space: nowrap">{{utils.ctg[item.tag].name}}{{item.subtag == 0 ? '': '-'+utils.ctg[item.tag].subctg[item.subtag - 1]}}</div>
                </div>
              </div>
              <v-spacer />
              <span style="color:red;font-size: 20px;">{{'￥'+item.price}}</span>
            </div>
          </v-card-text>
          <v-card-actions v-if="extraText && btnText">
            <div v-if="extraText">{{extraText(item)}}</div>
            <v-spacer></v-spacer>
            <v-btn
              v-if="btnText"
              color="primary"
              @click.stop="callback(item.id)"
              text
            >{{btnText}}</v-btn>
          </v-card-actions>
        </v-card>
      </div>
    </v-hover>
    <v-pagination
      v-if="!isActiveLoad"
      v-model="page"
      style="height: 84px"
      :length="total !== 0?Math.ceil(totalProps?totalProps:total/number):1"
      circle
    ></v-pagination>
  </div>
</template>

<script>
import axios from 'axios'
import utils from '../js/utils'

export default {
  name: 'LoadCard',
  props: {
    isActiveLoad: Boolean, // 是否瀑布流式加载，否则分页式加载
    isLoaded: {
      type: Boolean,
      default: false // 是否已经存在数据
    },
    cardsProp: {
      type: Array,
      default: () => { return [] }
    },
    number: Number, // 一次加载数目
    totalProps: Number,
    type: Number, // 卡片类型
    address: String, // 接口
    extraParam: String, // 额外参数
    extraText: Function,
    btnText: String, // 按钮文字
    callback: Function// 按钮回调
  },
  data () {
    return {
      cards: [],
      utils: utils,
      loaded: false,
      index: 0,
      isDelay: false,
      total: 0,
      page: 1
    }
  },
  methods: {
    load (number) {
      if (this.isActiveLoad)
        axios
          .post(this.utils.baseURL + '/' + this.address, 'first=' + this.index + '&end=' + (this.index + number - 1) + utils.getReal(this.extraParam, '', a => { return '&' + a }))
          .then(response => {
            this.loaded = true
            this.cards.push.apply(this.cards, response.data)
            this.index += number
          })
          .catch(error => { console.log(error) })
      else
        window.removeEventListener('scroll', this.init)
    },
    init () {
      if (!this.isDelay && (document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop) + window.innerHeight >= (document.body.scrollHeight - 100)) {
        this.load(4 * 4)
        this.isDelay = true
        setTimeout(() => {
          this.isDelay = false
        }, 2000)
      }
    },
    update () {
      axios
        .post(this.utils.baseURL + '/' + this.address, 'first=' + this.index + '&number=' + this.number + utils.getReal(this.extraParam, '', a => { return '&' + a }))
        .then(response => {
          this.total = response.data[0]
          this.cards = response.data.slice(1)
          this.index += this.number
          this.loaded = true
        })
        .catch(error => { console.log(error) })
    }
  },
  mounted () {
    if (this.address)
      if (this.isActiveLoad) {
        this.load(4 * 4)
        window.addEventListener('scroll', this.init)
      } else
        this.update()
    else {
      this.cards = this.cardsProp
      this.loaded = true
    }
  },
  watch: {
    extraParam: function () {
      if (!this.isActiveLoad) {
        this.loaded = false
        this.cards.length = 0
        this.index = 0
        this.update()
      }
    },
    isLoaded: function () {
      this.cards = this.cardsProp
    },
    page: function () {
      this.update()
    }
  },
  destroyed () {
    window.removeEventListener('scroll', this.init)
  },
  deactivated () {
    window.removeEventListener('scroll', this.init)
  }
}
</script>

<style scoped>
.grid {
  width: 25%;
}

@media screen and (max-width: 960px) {
  .grid {
    width: 50%;
  }
}
</style>
