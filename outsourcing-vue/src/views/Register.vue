<template>
  <div class="register">
    <div id="nav">
      <ul>
        <li><a href="./index.html">首页</a></li>
        <li><a href="./search.html?type=0">全部招标</a></li>
        <li><a href="./search.html?type=1">全部工作室</a></li>
      </ul>
    </div>
    <div class="hover">
      <div class="blur"></div>
      <div class="cover"></div>
      <i
        class="material-icons arrow_back"
        onclick="window.location.href = './login.html';"
        style="cursor: pointer;padding-left: 10%;padding-top: 10%;color: grey"
      ></i>
      <div class="title">
        <button
          id="company"
          checked
        >发包公司</button>
        <button
          name="type"
          id="studio"
        >工作室</button>
        <button
          name="type"
          id="manager"
        >管理员</button>
      </div>

      <form
        id="register_company"
        onsubmit="return isEmpty(0)"
        class="content"
      >
        <input
          name="account"
          placeholder="公司名"
          type="text"
          required
        >
        <input
          name="name"
          placeholder="联系人姓名"
          type="text"
          required
        >
        <input
          name="phone"
          placeholder="电话"
          type="text"
          required
        >
        <input
          name="password"
          placeholder="密码"
          type="password"
          required
        >
        <input
          name="repeatpassword"
          placehrolder="重复密码"
          onblur="checkpassword(0)"
          type="password"
          required
        >
        <input
          name="email"
          placeholder="邮箱"
          type="email"
          required
        >
        <button type="submit">注册</button>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: 'register'
}
</script>
