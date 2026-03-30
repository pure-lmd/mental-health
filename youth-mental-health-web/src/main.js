import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import 'element-plus/dist/index.css'
import App from './App.vue'
import router from './router'
import './assets/styles/index.scss'

// 导入SVG图标
import SvgIcon from './components/SvgIcon/index.vue'
import { setupSvgIcons } from './components/icons'

const app = createApp(App)

// 注册全局SVG图标组件
app.component('SvgIcon', SvgIcon)

app.use(createPinia())
app.use(router)
app.use(ElementPlus, {
  locale: zhCn,
})

// 初始化SVG图标
setupSvgIcons()

app.mount('#app')
