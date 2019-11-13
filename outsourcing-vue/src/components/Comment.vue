<template>
  <v-card
    class="mx-auto"
    max-width="600"
    max-height="400"
    outlined
  >
    <v-list-item three-line>
      <v-list-item-content>
        <div class="satisfaction">
          <ul>
            <li style="text-align:center">
              <a style="font-size:16px;letter-spacing:3px;cursor:auto">给对方打分:</a>
              <v-rating
                v-model="rating"
                background-color="orange lighten-3"
                color="orange"
                size="35"
                half-increments
              ></v-rating>
            </li>
            <li style="text-align:center">
              <v-btn
                outlined
                class="btn"
                color="primary"
                @click="submit"
              >提交</v-btn>
            </li>
          </ul>
        </div>
      </v-list-item-content>
    </v-list-item>
  </v-card>
</template>
<style scoped>
.xingxing {
  width: 5%;
  margin-left: 10px;
  cursor: pointer;
}
.btn {
  margin-top: 20px;
}
</style>
<script>
export default {
  props: {
    type: Number,
    prjinfo: Object
  },
  data () {
    return {
      rating: 0
    }
  },
  methods: {
    submit () {
      this.$emit('submit', this.type === 0 ? 10 : 11, { credit: this.rating }, () => { this.prjinfo.state += 1 })
    }
  }
}
</script>
