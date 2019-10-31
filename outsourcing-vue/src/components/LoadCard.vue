<template>
  <div class="d-flex flex-wrap">
    <v-hover
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
          src="https://cdn.vuetifyjs.com/images/cards/cooking.png"
        >
          <v-card-title>{{item.prjname}}</v-card-title>
        </v-img>
        <v-card-subtitle>类别</v-card-subtitle>
        <v-card-text>
          <div>{{item.tag[0]}}{{item.subtag==null ? 0: item.subtag}}</div>
        </v-card-text>
        <v-card-actions>
          <v-btn
            color="primary"
            text
          >
            Reserve
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-hover>
  </div>
</template>

<script>
import Axios from 'axios'

export default {
  name: 'LoadCard',
  data () {
    return {
      type: 0,
      index: 1,
      cards: []
    }
  },
  methods: {
    load (number) {
      Axios
        .post('/Platform/recommend', 'first=' + this.index + '&end=' + (this.index + number - 1))
        .then(response => {
          this.cards.push.apply(this.cards, response.data)
        })
        .catch(error => { console.log(error) })
    }
  },
  created () {
    this.load(4 * 4)
    window.addEventListener('scroll', () => {
      if ((document.documentElement.scrollTop || window.pageYOffset || document.body.scrollTop) + window.innerHeight >= (document.body.scrollHeight - 100)) {
        this.load(4 * 4)
      }
    })
  },
  destroyed () {
    window.removeEventListener('scroll', this.load)
  }
}
</script>
