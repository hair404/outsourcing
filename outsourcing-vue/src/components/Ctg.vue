<template>
  <div class="wrapper">
    <div
      class="categories nav"
      style="overflow-x: auto"
    >
      <ul id="ctg">
        <li
          v-for="(item,i) in ctg"
          :key="i"
        >
          <router-link :to="{name:'search',params:{ctg: i}}">{{item.name}}</router-link>
          <ul
            class="selection card"
            style="width: 100px;position: absolute"
          >
            <li
              v-for="(subctg,j) in item.subctg"
              :key="subctg"
            >
              <router-link :to="{name:'search',params:{ctg: i,subctg: j + 1}}">{{subctg}}</router-link>
            </li>
          </ul>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Ctg',
  props: {
    ctg: Array
  }
}
</script>

<style lang="scss">
.categories ul li {
  padding-left: 20px;
  padding-right: 20px;
}

.categories ul li a {
  padding: 0;
  display: inline;
  transition: 300ms;
  line-height: 39px;
}

.categories > ul > li:hover > a {
  color: #00a1d6 !important;
  border-bottom: 3px solid #00a1d6;
}

.categories ul li ul {
  display: none;
  z-index: 20;
}

.categories ul li:hover ul {
  display: block;
}
.nav {
  float: left;
}

.nav ul {
  list-style: none;
  display: inline-flex;
}

.nav > ul > li,
.nav > ul > div > li {
  text-align: center;
  height: 42px;
  transition: 300ms;
  display: inline-block;
  width: max-content;
}

.nav > ul > li > a,
.nav > ul > div > li > a {
  display: block;
  padding-left: 10px;
  padding-right: 10px;
}

.nav > ul > li:hover,
.nav > ul > div > li:hover {
  cursor: pointer;
  background-color: rgba(255, 255, 255, 0.2);
}
</style>
