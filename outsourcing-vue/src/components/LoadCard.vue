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
          @click="$router.push({name:'detail',params:{id:item.id}})"
          width="95%"
        >
          <v-img
            class="white--text align-end"
            height="200"
            :src="'Platform' + item.img"
          >
            <v-card-title>
              <v-avatar v-if="type === 0">
                <img :src="'Platform'+item.avatar" />
              </v-avatar>
              {{item.prjname?item.prjname:item.username}}
            </v-card-title>
          </v-img>
          <v-card-text class="text--primary">
            <v-rating
              v-if="item.credit"
              v-model="item.credit"
              color="orange"
              background-color="orange"
              readonly
              half-increments
            />
            <v-card-subtitle>类别</v-card-subtitle>
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
              class="d-flex flex-wrap"
            >
              <span>{{utils.ctg[item.tag].name}}{{item.subtag == 0 ? '': '-'+utils.ctg[item.tag].subctg[item.subtag - 1]}}</span>
              <v-spacer />
              <span style="color:red;font-size: 20px;">{{'￥'+item.price}}</span>
            </div>
          </v-card-text>
          <v-card-actions>
            <v-btn
              v-if="btnText"
              color="primary"
              @click="callback"
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
      :length="Math.ceil(total/number)"
      circle
    ></v-pagination>
  </div>
</template>

<script>
import Axios from 'axios'
import utils from '../js/utils'

export default {
  name: 'LoadCard',
  props: {
    isActiveLoad: Boolean, // 是否瀑布流式加载，否则分页式加载
    isLoaded: {
      type: Boolean,
      default: false // 是否已经存在数据
    },
    cards: {
      type: Array,
      default: () => { return [] }
    },
    number: Number, // 一次加载数目
    type: Number, // 卡片类型
    address: String, // 接口
    extraParam: String, // 额外参数
    btnText: String, // 按钮文字
    callback: Function// 按钮回调
  },
  data () {
    return {
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
      Axios
        .post('/Platform/' + this.address, 'first=' + this.index + '&end=' + (this.index + number - 1) + utils.getReal(this.extraParam, '', a => { return '&' + a }))
        .then(response => {
          this.loaded = true
          this.cards.push.apply(this.cards, response.data)
          this.index += number
        })
        .catch(error => { console.log(error) })
    },
    update () {
      Axios
        .post('/Platform/' + this.address, 'first=' + this.index + '&number=' + this.number + utils.getReal(this.extraParam, '', a => { return '&' + a }))
        .then(response => {
          this.loaded = true
          this.total = response.data[0]
          this.cards = response.data.slice(1)
          this.index += this.number
        })
        .catch(error => { console.log(error) })
    }
  },
  mounted () {
    if (!this.isLoaded)
      if (this.isActiveLoad) {
        this.load(4 * 4)
        window.addEventListener('scroll', () => {
          if (!this.isDelay && (document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop) + window.innerHeight >= (document.body.scrollHeight - 100)) {
            this.load(4 * 4)
            this.isDelay = true
            setTimeout(() => {
              this.isDelay = false
            }, 2000)
          }
        })
      } else
        this.update()
    else
      this.loaded = true
  },
  watch: {
    extraParam: function () {
      if (!this.isActiveLoad) {
        this.loaded = false
        this.cards.length = 0
        this.index = 0
        this.update()
      }
    }
  },
  destroyed () {
    window.removeEventListener('scroll', this.load)
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
