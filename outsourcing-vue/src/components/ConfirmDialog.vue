<template>
  <v-dialog
    max-width="400"
    v-model="CDialog.open"
  >
    <v-card>
      <v-card-title>是否继续</v-card-title>
      <v-card-text>操作不可逆</v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>
        <v-btn
          color="primary"
          text
          @click="CDialog.open=false"
        >取消</v-btn>
        <v-btn
          color="red"
          text
          @click="CDialog.callback()"
        >确认</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<script>
export default {
  props: {
    CDialog: {
      open: Boolean,
      callback: Function
    }
  }
}
</script>
