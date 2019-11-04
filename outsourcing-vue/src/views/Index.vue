<template>
  <div>
    <Nav></Nav>
    <Ctg></Ctg>
    <router-view />
    <div class="wrapper" style="display: flex;flex-wrap: wrap;">
        <div name="project" class="card left-recommend" style="cursor: pointer;">
            <div style="width: 100%;height: 80%;overflow: hidden;border-top-left-radius: 3px;border-top-right-radius: 3px;">
                <img class="autoimg">
            </div>
            <div style="height: 20%;">
                <div class="pro-name"></div>
                <div class="pro-price" style="line-height: 70px;"></div>
            </div>
        </div>
        <div class="right-recommend">
            <div style="display: flex;flex-wrap: wrap">
                <div name="project" class="card right-cell" style="margin-left: 0;margin-top: 0;cursor: pointer;">
                    <div class="pic">
                        <img class="autoimg">
                    </div>
                    <div style="height: 20%;text-align: center;">
                        <div class="pro-name"></div>
                        <div class="pro-price"></div>
                    </div>
                </div>
                <div name="project" class="card right-cell" style="margin-right: 0;margin-top: 0;cursor: pointer;">
                    <div class="pic">
                        <img class="autoimg">
                    </div>
                    <div style="height: 20%;text-align: center;">
                        <div class="pro-name"></div>
                        <div class="pro-price"></div>
                    </div>
                </div>
                <div name="project" class="card right-cell" style="margin-left: 0;margin-bottom: 0;cursor: pointer;">
                    <div class="pic">
                        <img class="autoimg">
                    </div>
                    <div style="height: 20%;text-align: center;">
                        <div class="pro-name"></div>
                        <div class="pro-price"></div>
                    </div>
                </div>
                <div name="project" class="card right-cell" style="margin-right: 0;margin-bottom: 0;cursor: pointer;">
                    <div class="pic">
                        <img class="autoimg">
                    </div>
                    <div style="height: 20%;text-align: center;">
                        <div class="pro-name"></div>
                        <div class="pro-price"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="wrapper">
        <div class="container" id="container">
        </div>
    </div>
  </div>
</template>

<script>
import Nav from '../components/Nav'
import Ctg from '../components/Ctg'

export default {
  components: {
    Nav,
    Ctg
  }
}
</script>

<style>
a {
  color: black !important;
}
ul {
  padding: 0px !important;
}
</style>
