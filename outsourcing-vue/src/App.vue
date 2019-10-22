<template>
  <div id="app">
    <div id="nav">
    </div>
    <router-view/>
  </div>
</template>

<style lang="scss">
@font-face {
  font-family: NewYahei;
  src: url("./assets/font/myh.ttf");
}

#app {
  font-family: "NewYahei", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  height: 100%;
}

* {
  margin: 0; 
  padding: 0;
  font-size: 14px;
  outline: none;
}

a {
  text-decoration: none;
  outline: none;
  color: #000000;
}

html,
body {
  height: 100%;
  background-color: rgb(246, 246, 246);
}

.card {
  display: block;
  background-color: white;
  transition: 300ms;
  border-radius: 3px;
  box-shadow: rgba(0, 0, 0, 0.2) 0 1px 5px 0px;
}

.card:hover {
  box-shadow: rgba(0, 0, 0, 0.2) 0 1px 3px 2px;
}

.wrapper {
  width: 60%;
  margin: 0 auto;
  min-width: 400px;
  height: 100%;
}

.selection li:hover {
  transition: 300ms;
  cursor: pointer;
  background-color: rgba(0, 0, 0, 0.1);
}

.select-card:hover {
  box-shadow: rgba(0, 0, 0, 0.4) 0px 1px 4px;
}

.select-card-hover:hover {
  border-color: transparent;
  background: #00a1d6;
  box-shadow: rgba(0, 0, 0, 0.4) 0px 1px 4px;
  color: white;
  cursor: pointer;
}

.select-card-selected {
  border-color: transparent;
  background: #00a1d6;
  box-shadow: rgba(0, 0, 0, 0.4) 0px 1px 4px;
  color: white;
}

.select-card-content {
  position: relative;
  top: 5%;
  margin-left: 5%;
}

.select-card-content > div {
  margin-top: 2%;
  margin-bottom: 2%;
}
</style>

