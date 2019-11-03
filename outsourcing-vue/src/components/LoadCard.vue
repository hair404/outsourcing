<template>
  <div class="d-flex flex-wrap">
    <div
      v-if="!loaded"
      class="d-flex flex-wrap"
      style="width: 100%"
    >
      <v-skeleton-loader
        v-for="i in 4"
        :key="i"
        class="mx-auto"
        min-width="200"
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
      <v-card
        :elevation="hover ? 12 : 2"
        class="mx-auto my-2"
        min-width="200"
        width="24%"
      >
        <v-img
          class="white--text align-end"
          height="250"
          :src="'Platform' + item.img"
        >
          <v-card-title>
            <v-avatar v-if="type === 0">
              <img :src="'Platform'+item.avatar">
            </v-avatar>
            {{item.prjname}}
          </v-card-title>
        </v-img>
        <v-card-subtitle>类别</v-card-subtitle>
        <v-card-text class="text--primary">
          <div v-if="type === 0">
            <span
              v-for="(tag ,i) in item.tag"
              :key="i"
            >{{utils.translate_ctg(tag)}} </span>
          </div>
          <div
            v-else
            class="d-flex flex-wrap"
          >
            <span>{{utils.translate_ctg(item.tag)}}-{{item.subtag == null ? 0: utils.translate_subctg(item.tag[0], item.subtag)}}</span>
            <v-spacer />
            <span style="color:red;font-size: 20px;">{{item.price}}</span>
          </div>
        </v-card-text>
        <v-card-actions>
          <v-btn
            color="primary"
            text
          >
            查看详情
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-hover>
  </div>
</template>

<script>
import Axios from 'axios'
import utils from '../js/utils'

export default {
  name: 'LoadCard',
  props: {
    type: Number
  },
  data () {
    return {
      utils: utils,
      loaded: false,
      index: 1,
      cards: [],
      isDelay: false
    }
  },
  methods: {
    load (number) {
      Axios
        .post('/Platform/' + (this.type === 0 ? 'recommend_studio' : 'recommend_program'), 'first=' + this.index + '&end=' + (this.index + number - 1))
        .then(response => {
          this.loaded = true
          this.cards.push.apply(this.cards, response.data)
          this.index += number
        })
        .catch(error => { console.log(error) })
    }
  },
  mounted () {
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
  },
  destroyed () {
    window.removeEventListener('scroll', this.load)
  }
}
</script>
