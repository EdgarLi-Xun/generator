import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'
import generator from '@/views/generator'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'generator',
      component: generator
    }
  ]
})
