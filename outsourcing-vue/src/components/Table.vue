<template>
  <div>
    <v-data-table
      v-model="selected"
      :headers="headers"
      :items="data"
      :item-key="itemkey"
      class="elevation-1"
      :show-select="!isCompany"
      :disable-sort="!issort"
      hide-default-footer
      :items-per-page="100"
    >
      <template
        v-if="isCompany"
        v-slot:item.price="props"
      >
        <v-edit-dialog
          :return-value.sync="props.item.price"
          large
          persistent
        >
          <v-btn
            v-if="!props.item.price"
            outlined
            color="primary"
          >设定进度款</v-btn>
          <div v-else>{{props.item.price}}</div>
          <template v-slot:input>
            <v-text-field
              v-model="props.item.price"
              :rules="[v => typeof v =='number' && v > 0 || '必须超过0']"
              label="Edit"
              single-line
              counter
              autofocus
            ></v-text-field>
          </template>
        </v-edit-dialog>
      </template>
    </v-data-table>
  </div>
</template>

<script>
export default {
  props: {
    data: Array,
    isCompany: {
      type: Boolean,
      default: false
    },
    isPriceCol: Boolean,
    headers: Array,
    itemkey: String,
    issort: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      selected: []
    }
  },
  created () {
    if (this.isPriceCol === true)
      this.headers.push({ text: '进度款', value: 'price' })
  }
}
</script>
