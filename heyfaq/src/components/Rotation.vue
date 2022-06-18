<template>
  <div >
    <el-carousel height="89vh" direction="vertical" :autoplay="true" :pause-on-hover="true" >
      <el-carousel-item >
        <el-image class="faq" :src="require(`@/assets/1708669762-01.png`)" ></el-image>
      </el-carousel-item>

      <el-carousel-item >
        <el-image :src="require(`@/assets/1773579569.png`)" ></el-image>
      </el-carousel-item>

      <el-carousel-item >
        <el-image :src="require(`@/assets/366046730.png`)"></el-image>
      </el-carousel-item>
    </el-carousel>
  </div>

  <div class="right">
    <el-button @click="indexmain" style="width: 150px;height: 50px" type="primary" icon="ArrowRightBold" round size="large" color="#7e57c2" >点击进入</el-button>
  </div>

</template>

<script>
import {useRouter} from "vue-router";

export default {
  name: "Rotation",
  setup(){
    const router=useRouter()

    function indexmain() {
      router.push({
        name: "mainview",
      });
    }

    return{
      indexmain
    }
  }
}
</script>

<style scoped>
.right{
  position: fixed;
  right: 50px;
  bottom: 50px;
}

.el-carousel{
  padding: 0;
}

.faq{
  background-position: -100px 0;
}
</style>
