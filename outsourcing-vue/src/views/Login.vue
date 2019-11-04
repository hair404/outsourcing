<template>
  <div class="body">
    <Head></Head>
    <div class="hover">
      <div class="blur"></div>
      <div class="cover"></div>
      <h1 class="title">人力和项目服务平台</h1>
      <form id="login" @submit.prevent="submit">
        <div class="content">
          <input v-model='username' name="name" id="account" type="text" placeholder="账号或者E-mail" required />
          <input v-model='password' name="password" id="password" type="password" placeholder="密码" required />
        </div>
        <div>
          <input  v-model='type' type="radio" name="type" value="0" checked>发包公司
          <input v-model='type'  type="radio" name="type" value="1" >工作室
          <input  v-model='type' type="radio" name="type" value="2" >管理员
        </div>
        <div>
          <button type="submit" style="width: 80%;margin-top: 20px;height: 30px">登录</button>
        </div>
        <div class="register" @click='this.$router.push("./Register")'>
          <span>注册</span>
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import Head from "@/components/Head.vue";
import axios from "axios";
export default {
  name: "Login",
  components: {
    Head
  },
  data() {
    return {
      username: "",
      password: "",
      type: ""
    };
  },
  methods: {
    submit: function(event) {
      let data = new FormData();
      data.append("username", this.username);
      data.append("password", this.password);
      data.append("type", this.type);
      axios
        .post("./Login?", data)
        .then(response => {
          if ((response = "success")) {
            this.$router.push({ path: "/index" });
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    }
  }
}
</script>
<style scoped lang="scss">
.body{
    background-image: url("../assets/backimg.jpg");
  height: 100%;
  background-position: center;
  overflow: hidden;
}
* {
    padding: 0;
    outline: none;
}

a {
    text-decoration: none;
    outline: none;
    color: #000000;
}

.nav {
    float: left;
}

.nav ul {
    list-style: none;
}

.nav>ul>li {
    float: left;
    text-align: center;
    line-height: 42px;
    padding-left: 10px;
    padding-right: 10px;
    transition: 200ms;
}

.nav>ul>li:hover {
    cursor: pointer;
    background-color: rgba(255, 255, 255, 0.2);
}

.hover {
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);
    height: 500px;
    width: 400px;
}

.cover {
    position: absolute;
    height: 100%;
    width: 100%;
    z-index: -1;
    transition: 200ms;
    background-color: rgba(255, 255, 255, 0.4);
    border: transparent;
    border-radius: 3px;
    box-shadow: grey 0px 1px 4px;
}

.cover:hover {
    box-shadow: grey 0px 1px 10px;
}

.blur {
    position: absolute;
    overflow: hidden;
    height: 100%;
    width: 100%;
    z-index: -2;
    background-image: url('../assets/backimg.jpg');
    filter: blur(4px);
    background-position: center;
}

.title {
    position: absolute;
    font-size:30px;
    left: 50%;
    top: 20%;
    transform: translate(-50%, -50%);
    width: 300px;
    text-align: center;
}

form {
    position: absolute;
    top: 60%;
    transform: translate(0, -50%);
    width: 100%;
    text-align: center;
}

input {
    border: transparent;
    background: rgba(255, 255, 255, 0.4);
    border-radius: 3px;
    transition: 200ms;
}

input:hover {
    background: white;
    box-shadow: grey 0px 1px 4px;
}

.content {
    width: 90%;
    padding-left: 10%;
    padding-bottom: 20px;
    height: fit-content;
}

.content input {
    margin-top: 20px;
    display: block;
    width: 100%;
    height: 50px;
}

.register {
    padding: 5px;
    border: transparent;
    border-radius: 3px;
    background: rgba(255, 255, 255, 0.4);
    transition: 200ms;
    cursor:pointer;
    width: 80%;
    margin-left:40px;
    margin-top:5px
}
.register:hover{
    background: white;
    box-shadow: rgba(0, 0, 0, 0.4) 0px 1px 4px;
}

.img {
    cursor: pointer;
}

button {
    padding: 5px;
    border: transparent;
    border-radius: 3px;
    background: rgba(255, 255, 255, 0.4);
    transition: 200ms;
}

button:hover {
    background: white;
    box-shadow: rgba(0, 0, 0, 0.4) 0px 1px 4px;
}
</style>
