<script setup>
    import Header from './Header.vue';
    import { ref } from 'vue';
    import api from "../api/axios";

    function copyTrackLink() {
        navigator.clipboard.writeText(trackUrl.value)
        alert('Трек ссылка скопирована')
    }

    const originalUrl = ref('')
    const trackUrl = ref('')
    const errorMessage = ref('')

    async function generateTrackLink() {
        if(!validateUrl()) return
        try{
            const response = await api.post("track-link",{"originalUrl": originalUrl.value},)
            trackUrl.value = response.data;
        }catch(err){
            console.log("ERROR: " + err);
        }

        
    }

    function validateUrl(){
        errorMessage.value = '';

        if(!originalUrl.value) {
            errorMessage.value = 'Вы не ввели ссылку!';
            return false;
        }

        try{
            const parsedUrl = new URL(originalUrl.value)

            if(!['http:','https:'].includes(parsedUrl.protocol)){
                errorMessage.value = 'Ссылка доллжна начинаться с http или https';
                return false;
            }
        }catch{
            errorMessage.value = 'Некорректный формат ссылки';
            return false;
        }

        return true;

    }
</script>
<template>
    <Header></Header>
    <div class="track-page">
    <h1 class="title">Генерация трек ссылки</h1>

    <div class="content">
      <div class="card">
        <div class="field">
          <label>Исходная ссылка</label>
          <input type="text" v-model="originalUrl" placeholder="https://example.com" :class="{ error: errorMessage }" />
          <span v-if="errorMessage" class="error-text">{{ errorMessage }}</span>
        </div>

        <div class="description">
          <h3>Что собирается по трек ссылке?</h3>
          <p>
            При переходе по сгенерированной трек ссылке сохраняется информация
            о пользователе:
          </p>
          <ul>
            <li>IP-адрес</li>
            <li>Страна</li>
            <li>Устройство</li>
            <li>Браузер</li>
          </ul>
          <p class="note">
            Эти данные используются для дальнейшего анализа трафика.
          </p>
        </div>

        <button class="generate-btn" @click="generateTrackLink">
          Сгенерировать трек ссылку
        </button>
      </div>

      <div class="result-card">
        <div v-if="!trackUrl" class="placeholder">
          Тут будет сгенерированная вами трек ссылка
        </div>

        <div v-else class="result">
          <div class="track-url">{{ trackUrl }}</div>

          <button class="copy-btn" @click="copyTrackLink">
            Скопировать
          </button>
        </div>
      </div>
    </div>
  </div>
</template>
<style scoped>
    .track-page {
        width: 80vw;
        height: 80vh;
        padding: 2vw;
        font-family: 'Montserrat', sans-serif;
    }

    .title {
        text-align: center;
        color: #6F4BB0;
        margin-bottom: 30px;
    }

    .content {
        display: flex;
        position: relative;
    }

    .card {
        width: 420px;
        background: #fff;
        border-radius: 20px;
        border-top-right-radius: 0px;
        border-bottom-right-radius: 0px;
        padding: 30px;
        box-shadow: 0 15px 35px rgba(0,0,0,0.1);
        z-index: 2;
    }

    .field {
        display: flex;
        flex-direction: column;
        margin-bottom: 20px;
    }

    label {
        font-weight: 600;
        margin-bottom: 8px;
    }

    input {
        padding: 12px;
        border-radius: 10px;
        border: 1px solid #ddd;
        font-size: 0.95rem;
    }

    input.error {
        border-color: #e74c3c;
    }

    .error-text {
        color: #e74c3c;
        font-size: 0.85rem;
        margin-top: 5px;
    }

    .description {
        margin-bottom: 20px;
    }

    .description h3 {
        margin-bottom: 8px;
        color: #6F4BB0;
    }
    .description p{
        color: #777;
        font-size: 0.95rem;
    }

    .description ul {
        padding-left: 18px;
        margin: 10px 0;
        color: #777;
        font-size: 0.95rem;
    }

    .note {
        font-size: 0.75rem;
        color: #8e8e8e;
    }

    .generate-btn {
        width: 100%;
        padding: 14px;
        border-radius: 12px;
        border: none;
        background: #6F4BB0;
        color: #fff;
        font-weight: 600;
        cursor: pointer;
    }

    .generate-btn:hover {
        background: #8C63C2;
    }

    .result-card {
        flex: 1;
        background: #faf8ff;
        border-radius: 20px;
        padding: 30px;
        box-shadow: 0 15px 35px rgba(0,0,0,0.1);
        border-top-left-radius: 0px;
        border-bottom-left-radius: 0px;
        display: flex;
        align-items: center;
        justify-content: center;
    }

    .placeholder {
        color: #999;
        text-align: center;
    }

    .result {
        width: 100%;
        text-align: center;
    }

    .track-url {
        word-break: break-all;
        font-weight: 600;
        color: #6F4BB0;
        margin-bottom: 20px;
    }

    .copy-btn {
        padding: 12px 20px;
        border-radius: 10px;
        border: none;
        background: #e1d4fc;
        color: #6F4BB0;
        font-weight: 600;
        cursor: pointer;
    }

    .copy-btn:hover {
        background: #d0bdf5;
    }

    @media (max-width: 767px) {
        .content {
            flex-direction: column;
        }

        .card {
            width: 100%;
        }
    }
</style>