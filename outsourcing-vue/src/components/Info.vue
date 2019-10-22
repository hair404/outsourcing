<template>
  <div>
    <v-form class="wrapper mt-4 mb-4">
      <v-text-field
        name="username"
        type="text"
        label="公司名"
        required
        outlined
      />
      <v-text-field
        name="name"
        type="text"
        label="真实名字"
        required
        outlined
      />
      <v-text-field
        name="password"
        type="password"
        label="密码"
        outlined
      />
      <div style="width: 800px;display: none;">
        <select
          id="select"
          style="width: 100px;height: 50px;"
          onchange=""
        >
          <option value="0">请选择</option>
        </select>
      </div>
      <v-text-field
        name="email"
        type="email"
        label="邮箱"
        required
        outlined
      />
      <v-text-field
        name="phone"
        type="text"
        label="电话"
        required
        outlined
      />
      <v-textarea
        outlined
        name="info"
        label="介绍"
      />
      <v-btn
        text
        color="warning"
        class="mr-4"
      >重置</v-btn>
      <v-btn color="primary">
        保存
      </v-btn>
    </v-form>
  </div>
</template>

<script>
export default {
  name: 'Info'
}
</script>
