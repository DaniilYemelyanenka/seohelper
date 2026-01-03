<script setup>
import { computed, ref, onMounted } from 'vue'
import Header from './Header.vue'
import { useRouter } from 'vue-router';
import { useAnalysysStore } from '../store/analysisStore';

const store = useAnalysysStore();

const analysis = ref({
  url: store.url,
  score: store.score,
  recommendations: store.recommendations
})

const animatedScore = ref(0)
const progressPercentage = computed(() => (animatedScore.value / 10) * 100)
const router = useRouter();

const recommendationList = computed(() =>
  analysis.value.recommendations
    .split('\n')
    .filter(r => r.trim() !== '')
)

function splitRecommendation(text) {
  const match = text.match(/^(.*?\d+\.)(.*)/)
  if (match) {
    return {
      valuePart: match[1].trim(), 
      textPart: match[2].trim()     
    }
  } else {
    return { valuePart: text, textPart: '' }
  }
}

function goToMain(){
    router.push("/")
}

const progressColors = computed(() => {
  const s = analysis.value.score
  if (s <= 4) return ['#e74c3c','#c0392b']       
  if (s <= 7) return ['#f39c12','#e67e22']      
  return ['#2ecc71','#27ae60']                    
})

onMounted(() => {
  let current = 0
  const target = analysis.value.score
  const interval = setInterval(() => {
    if (current < target) {
      current += 0.1
      animatedScore.value = current
    } else {
      animatedScore.value = target
      clearInterval(interval)
    }
  }, 15)
})
</script>

<template>
    <Header></Header>
    <div class="analysis-page">
        <h1 class="title">Анализ ссылки: {{ analysis.url }}</h1>

            
        <div class="progress-container">
            <svg viewBox="0 0 100 50" class="progress-circle">
                <path
                class="bg"
                d="M10,50 A40,40 0 0,1 90,50"
                fill="transparent"
                stroke="#eee"
                stroke-width="10"
                />

                <defs>
                <linearGradient id="gradient" x1="0%" y1="0%" x2="100%" y2="0%">
                    <stop offset="0%" :stop-color="progressColors[0]" />
                    <stop offset="100%" :stop-color="progressColors[1]" />
                </linearGradient>
                </defs>
                <path
                class="progress"
                d="M10,50 A40,40 0 0,1 90,50"
                fill="transparent"
                stroke="url(#gradient)"
                stroke-width="10"
                stroke-linecap="round"
                :stroke-dasharray="progressPercentage + ', 100'"
                />
            </svg>
            <div class="score" :style = "{color:progressColors[0]}">{{ analysis.score }}/10</div>
        </div>

        <div class="recommendations">
        <h2>Рекомендации:</h2>
            <ul>
                <li v-for="(rec, index) in recommendationList" :key="index">
                    <span class="value-part" :style="{ color: progressColors[0] }">
                        {{ splitRecommendation(rec).valuePart }}
                    </span>
                    <span class="text-part">
                        {{ splitRecommendation(rec).textPart }}
                    </span>
                </li>
            </ul>
        </div>
        <button @click="goToMain">Вернуться на главную</button>
    </div>
</template>

<style scoped>
    .analysis-page {
    max-width: 90vw;
    height: 80vh;
    font-family: 'Montserrat', sans-serif;
    padding: 10vh 30px 10vh 20px;
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: #FFFFFF;
    border-radius: 20px;
    box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
    gap: 5vh;
    }

    .title {
    text-align: center;
    font-size: 1.8rem;
    font-weight: 700;
    color: #6F4BB0;
    margin-bottom: 40px;
    }

    .progress-container {
    position: relative;
    width: 200px;
    height: 100px;
    margin: 0 auto 40px;
    }

    .progress-circle {
    width: 100%;
    height: 100%;
    
    }

    .bg {
    stroke-linecap: round;
    }

    .progress {
    transition: stroke-dasharray 0.3s linear;
    }

    .score {
    position: absolute;
    bottom: 0;
    left: 50%;
    transform: translateX(-50%);
    font-size: 1.5rem;
    font-weight: 700;
    }

    .recommendations h2 {
    font-size: 1.3rem;
    margin-bottom: 15px;
    }

    .recommendations ul {
    list-style: none;
    padding-left: 20px;
    font-size: 1.2rem;
    }

    .recommendations li {
    margin-bottom: 20px;
    line-height: 1.5;
    }
    .text-part{
        margin-top: 5px;
        text-indent: 10px;
        display: block;
    }
    button {
        padding: 15px 30px;
        font-family: 'Montserrat', sans-serif;
        font-weight: 600;
        font-size: 1rem;
        width: 300px;
        height: 50px;
        color: #FFFFFF;
        background-color: #6F4BB0;
        border: 2px solid #6F4BB0; 
        border-radius: 10px; 
        cursor: pointer;
        transition: background-color 0.3s, transform 0.2s;
    }
     button:hover {
        background-color: #8C63C2;
    }

    @media(max-width: 1280px) and (min-width:710px){
      .title {
        font-size: 1.5rem;
      }
      button{
         padding: 10px 20px;
          width: 250px;
          font-weight: 500;
          height: 40px;
      }
      .recommendations li{
        font-size: 0.9rem;
      }
      .progress-container{
        width:175px;
        height: 80px;
      }
      .analysis-page{
        padding: 5vh 2vh;
        gap: 3vh;
      }
    }
    @media(max-width: 710px) and (min-width:500px){
      .title {
        font-size: 1.3rem;
      }
      button{
         padding: 8px 16px;
          width: 230px;
          font-weight: 500;
          height: 35px;
      }
      .recommendations li{
        font-size: 0.8rem;
      }
      .progress-container{
        width:150px;
        height: 75px;
      }
      .analysis-page{
        padding: 5vh 2vh;
        gap: 3vh;
      }
    }


    @media (max-width: 500px) {
   .title {
        font-size: 1.2rem;
      }
      button{
         padding: 6px 12px;
          width: 210px;
          font-weight: 400;
          height: 30px;
      }
      .recommendations li{
        font-size: 0.8rem;
      }
      .progress-container{
        width:130px;
        height: 70px;
      }
      .analysis-page{
        padding: 5vh 2vh;
        gap: 3vh;
      }
    }
</style>
