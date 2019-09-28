<template>
<div class='backimg'>
        <div class="logintitle">
     <ul>
      <router-link to="/"  style='text-decoration:none'>首页</router-link>
      <router-link to="/" style='text-decoration:none'>全部招标</router-link>
      <router-link to="/"  style='text-decoration:none'>全部工作室</router-link>
      </ul>
    <router-view/>
  </div>
    <div class="hover">
        <div class="blur"></div>
        <div class="cover"></div>
        <h1 class="title">人力和项目服务平台</h1>
        <form id="login" ref="ruleForm">
            <div class="content">
                <input v-model="username" name="name" id="account" type="text" placeholder="账号或者E-mail" required>
                <input  v-model="password" name="password" id="password" type="password" placeholder="密码" required>
            </div>
            <div>
                <input v-model='type' type="radio" name="type" value='0' checked>发包公司
                <input v-model='type' type="radio" name="type" value='1'>工作室
                <input v-model='type' type="radio" name="type" value='2'>管理员
            </div>
            <br/>
            <div class='button'>
               <a style="width: 80%;height: 30px;text-decoration:none;color:black;cursor:pointer" v-on:click="login" >登录</a>
            <br/><br/>
           <router-link to="/register" style="width: 80%;height: 30px;text-decoration:none;color:black"><span>注册</span></router-link>
           </div>
        </form>
    </div>
    </div>
</template>
<script>
export default {
        data(){
  return{
  }
    },
  methods: {
    login() {
    if (this.username == null || this.password == null){
            alert("请输入用户名或密码");
      }else{
            let data = {'username':this.username,'password':this.password,'type':this.type}
            this.$http.post('/',data).then((res)=>{
                if (res == "success"){
                    this.$router.push({ path: "/index" });
                }
          })
    }
  }
}
}
</script>
<style scoped lang="scss">
.backimg{
    background-image:url('../assets/backimg.jpg');
    height:100%;
    background-position:center;
    overflow:hidden;
}
.logintitle {
  float: left;
  ul {
    color: #2c3e50;
    padding:0 0 0 35px;
    text-align:center;
  }
}

.logintitle ul {
    list-style: none;
}
.logintitle>ul>a {
    float: left;
    text-align: center;
    line-height: 42px;
    padding-left: 10px;
    padding-right: 10px;
    transition: 200ms;
}
.logintitle>ul>a:hover {
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
    filter: blur(4px);
    background-position: center;
}

.title {
    position: absolute;
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
    width: 80%;
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

.button a {
    padding: 5px 150px;
    border: transparent;
    border-radius: 3px;
    background: rgba(255, 255, 255, 0.4);
    transition: 200ms;
}

.button a:hover {
    background: white;
    box-shadow: rgba(0, 0, 0, 0.4) 0px 1px 4px;
}
.button1 {
    padding: 5px 150px;
    border: transparent;
    border-radius: 3px;
    background: rgba(255, 255, 255, 0.4);
    transition: 200ms;
}

.button1:hover {
    background: white;
    box-shadow: rgba(0, 0, 0, 0.4) 0px 1px 4px;
}
</style>
