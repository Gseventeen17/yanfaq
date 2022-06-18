<template>
  <router-view></router-view>
<!--  <Main/>-->
<!--  <First/>-->
</template>
<script>


import Main from "@/components/Main";
import First from "@/components/First";
export default {
  name: 'App',
  components: {
    Main,
    First

  }
}
</script>

<style>
html,body{
  width: 100%;
  height: 100%
}

.manager{
  margin-top: 10px;
}
</style>
