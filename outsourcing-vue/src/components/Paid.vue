<template>
  <div>
    <v-card
      class="mx-auto"
      width="100%"
      height="200"
      outlined
    >

      <v-card-title
        style="color: red;padding: 20px"
        ref="text"
      >
        <Countdown :deadline="prjinfo.payDeadline"></Countdown>
      </v-card-title>
      <div style="padding:16px 0 5px 16px">请确认项目信息,确认无误后点击按钮支付押金和首付款,之后请耐心等待对方付款</div>

      <v-card-actions class="py-3 ">
        <v-icon
          v-if="(prjinfo.hasPaid || prjinfo.isdeposit) != 0"
          color="green"
        >mdi-check</v-icon>
        <v-btn
          :disabled="(prjinfo.hasPaid || prjinfo.isdeposit) != 0"
          color="primary"
          text
          outlined
          @click="$emit('submit',info.type === 0?4:5,{},()=>{prjinfo.hasPaid = 1;prjinfo.isdeposit = 1})"
        >支付押金(￥{{parseInt( prjinfo.price * 0.1)}})</v-btn>

        <v-icon
          v-if="!(info.type === 0 && prjinfo.ispia === 0) && info.type === 0"
          color="green"
        >mdi-check</v-icon>
        <v-btn
          :disabled="!(info.type === 0 && prjinfo.ispia === 0)"
          color="primary"
          text
          outlined
          @click="$emit('submit',5,{},()=>{prjinfo.ispia = 1})"
        >支付首付歀({{parseInt( prjinfo.payinadvance)}})</v-btn>
      </v-card-actions>
    </v-card>
  </div>
</template>
<script>
import Countdown from './Countdown'

export default {
  components: {
    Countdown
  },
  props: {
    info: Object,
    prjinfo: Object
  }
}
</script>
