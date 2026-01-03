import './style.css'
import { createApp } from 'vue'
import {createRouter, createWebHistory} from 'vue-router'
import App from './App.vue'
import HomeView from './components/HomeView.vue'
import AnalyzeView from './components/AnalyzeView.vue'
import HistoryAnalyzeView from './components/HistoryAnalyzeView.vue'
import { createPinia } from 'pinia'

const pinia = createPinia();
const router = createRouter({
    routes:[
        {
            path: '/',
            name: 'Home',
            component: HomeView
        },
        {
            path: "/seo/analyze",
            name: "Analyze",
            component: AnalyzeView
        },
        {
            path: "/seo/history",
            name: "SeoHistory",
            component: HistoryAnalyzeView
        }
    ],
    history: createWebHistory()
})

createApp(App)
    .use(pinia)
    .use(router)
    .mount('#app')
