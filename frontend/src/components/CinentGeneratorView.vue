<script setup>
    import Header from './Header.vue';
    import { ref } from 'vue';
    import  api  from '../api/axios'

    const errorMessage = ref('');
    const formErrors = ref({});

    const platforms = [
        { value: 'TELEGRAM', label: 'Telegram' },
        { value: 'SITE', label: 'Сайт' }
    ];

    const contentTypes = [
        { value: 'DESCRIPTION', label: 'Описание' },
        { value: 'POST', label: 'Пост' }
    ];
    const result = ref({
        topic: '',
        content: ''
    })

    const form = ref({
        contentType: '',
        platform: '',
        topic: '',
        language: 'ru'
    });

    function validateForm() {
        formErrors.value = {}; 

        if (!form.value.contentType) {
            formErrors.value.contentType = 'Выберите тип контента';
        }

        if (!form.value.platform) {
            formErrors.value.platform = 'Выберите платформу';
        }

        if (!form.value.topic || form.value.topic.trim().length < 3) {
            formErrors.value.topic = 'Тема должна быть не меньше 3 символов';
        }

        if (form.value.topic && form.value.topic.length > 255) {
            formErrors.value.topic = 'Тема не может быть длиннее 255 символов';
        }

        return Object.keys(formErrors.value).length === 0; 
    }


    async function sendData(){
        errorMessage.value = '';
        if(!validateForm()) return;

        try{
            const response = await api.post("/content/generate",form.value);
            result.value = response.data.data;
            if (!response.data.success) {
                console.log(response.data.data)
                throw new Error(response.data.data.message || 'Ошибка генерации');
            }
        }catch(err){
            errorMessage.value = 'Упс... Что-то пошло не так. Попробуйте еще раз!'
        }
        
    }

    function copyContent() {
        navigator.clipboard.writeText(result.value.content);
        alert('Скопировано в буфер обмена!');
    }

</script>
<template>
    <Header></Header>
    <div class="generate-page">
    <h1 class="title">Генерация контента</h1>


    <div class="content">
    <div class="card">
      <div class="field">
        <label>Тип контента</label>
        <select v-model="form.contentType" :class="{ 'input-error': formErrors.contentType }">
          <option disabled value="">Выберите тип</option>
          <option v-for="item in contentTypes" :key="item.value" :value="item.value">
            {{ item.label }}
          </option>
        </select>
        <span v-if="formErrors.contentType" class="error">{{ formErrors.contentType }}</span>
      </div>

      <div class="field">
        <label>Платформа</label>
        <select v-model="form.platform" :class="{ 'input-error': formErrors.platform }">
          <option disabled value="">Выберите платформу</option>
          <option v-for="item in platforms" :key="item.value" :value="item.value">
            {{ item.label }}
          </option>
        </select>
        <span v-if="formErrors.platform" class="error">{{ formErrors.platform }}</span>
      </div>

      <div class="field">
        <label>Тема</label>
        <input type="text" v-model="form.topic" placeholder="Например: SEO для интернет-магазина" :class="{ 'input-error': formErrors.topic }"/>
        <span v-if="formErrors.topic" class="error">{{ formErrors.topic }}</span>
      </div>

      <div class="field">
        <label>Язык генерации</label>

        <div class="language-switch">
          <span :class="{ active: form.language === 'ru' }">RU</span>

          <div class="switch" @click="form.language = form.language === 'ru' ? 'en' : 'ru'">
            <div class="circle" :class="form.language"></div>
          </div>

          <span :class="{ active: form.language === 'en' }">EN</span>
        </div>
      </div>

      <button class="generate-btn" @click="sendData">
        Сгенерировать
      </button>
    </div>
    <div class="result-card">
        <div v-if="errorMessage" class="result-error">
            {{ errorMessage }}
        </div>
        <div v-else-if="!result.content" class="result-placeholder">
            Тут будет выведен ваш сгенерированный контент
        </div>
        <div v-else class="result-wrapper">
            <div class="result-topic">
                {{ result.topic }}
            </div>
            <div class="result">
                {{ result.content }}
            </div>
            <button class="copy-btn" @click="copyContent">
                Скопировать
            </button>
    </div>
  </div>
  </div>
  </div>
</template>
<style scoped>
    .generate-page {
        width: 80vw;
        height: 80vh;
        padding: 2vw;
        background: #fcfcfc64;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        font-family: 'Montserrat', sans-serif;
        border-radius: 15px;
        box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
    }

    .title {
        text-align: center;
        color: #6F4BB0;
        margin-bottom: 30px;
    }

    .card {
        width: 420px;
        height: 60vh;
        margin: 0 auto;
        background: #fff;
        border-radius: 20px;
        padding: 30px;
        box-shadow: 0 15px 35px rgba(0,0,0,0.1);
        position: relative;
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
        color: #444;
    }

    input,select {
        padding: 12px;
        border-radius: 10px;
        border: 1px solid #ddd;
        font-size: 0.95rem;
        outline: none;
    }

    input:focus,select:focus {
        border-color: #6F4BB0;
    }


    .language-switch {
        display: flex;
        align-items: center;
        gap: 12px;
    }

    .language-switch span {
        font-weight: 600;
        color: #aaa;
    }

    .language-switch span.active {
        color: #6F4BB0;
    }

    .switch {
        width: 50px;
        height: 26px;
        background: #ddd;
        border-radius: 20px;
        position: relative;
        cursor: pointer;
    }

    .circle {
        width: 22px;
        height: 22px;
        background: #fff;
        border-radius: 50%;
        position: absolute;
        top: 2px;
        left: 2px;
        transition: transform 0.25s ease;
    }

    .circle.en {
        transform: translateX(24px);
    }
    .content {
        display: flex;
        position: relative;
    }

    .result-card {
        display: flex;
        align-items: center;
        justify-content: center;
        width:40vw;
        height: 60vh;
        border-top-right-radius: 15px;
        border-bottom-right-radius: 15px;
        box-shadow: 0 15px 35px rgba(0,0,0,0.1);
        padding: 20px;
        background: #faf8ff;
    }

    .result-placeholder {
        color: #999;
        font-size: 0.95rem;
        text-align: center;
        padding: 20px;
    }
    .result-wrapper {
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        padding: 2vw;
        height: 100%;
        width: 100%;
    }

    .result-topic {
        font-weight: 600;
        color: #6F4BB0;
        font-size: 1.05rem;
        margin-bottom: 12px;
        text-align: center;
    }
    .result{
        color: #424242;
        font-size: 1rem;
        text-align: center;
        padding: 20px;
    }
    .result-error{
        color: #e74c3c;
        font-size: 13px;
    }
    .error { 
        color: #e74c3c;
        font-size: 0.85rem;
        margin-top: 4px;
        display: block; 
    }
    .input-error {
        border: 2px solid #e74c3c !important;
    }
    .copy-btn {
        width:250px;
        margin-top: 12px;
        padding: 12px;
        border-radius: 10px;
        border: none;
        background: #e1d4fc;
        color: #6F4BB0;
        font-weight: 600;
        cursor: pointer;
        transition: background 0.2s ease;
    }

    .copy-btn:hover {
        background: #d0bdf5;
    }
    .generate-btn {
        width: 100%;
        padding: 14px;
        border-radius: 12px;
        border: none;
        background: #6F4BB0;
        color: #fff;
        font-size: 1rem;
        font-weight: 600;
        cursor: pointer;
        transition: background 0.2s ease;
    }

    .generate-btn:hover {
        background: #8C63C2;
    }

    @media(max-width: 1280px) and (min-width:840px){
        .generate-page {
            padding:10px;
            width:100vw;
            height:100vh;
        }
        .result-card {
            width:500px;
            height: 60vh;
            padding: 20px;
        }
        .card {
            width: 320px;
            height: 60vh;
            margin: 0 auto;
            background: #fff;
            border-radius: 20px;
            padding: 30px;
            box-shadow: 0 15px 35px rgba(0,0,0,0.1);
        }
        }
        @media(max-width: 840px) and (min-width:767px){
            .generate-page {
                padding:10px;
                width:100vw;
                height:100vh;
            }
            .result-card {
                width:400px;
                height: 500px;
                padding: 15px;
            }
            .card {
                width: 300px;
                height: 500px;
                padding: 20px;
            }
        }
        @media(max-width:767px) and (min-width:500px){
            .generate-page {
                padding:100px 50px 0vh 50px;
                justify-content: flex-start;
                width:100vw;
                height:100vh;
                overflow: scroll;
            }
            .content {
                display: flex;
                flex-direction: column;
                width:70vw;
            }
            .card {
                width: 70vw;
                height: 500px;
                padding: 20px;
                border-bottom-right-radius: 0px;
                border-bottom-left-radius: 0px;
            }
            .result-card {
                margin-left: 0px;
                width:70vw;
                height:500px;
                border-top-left-radius: 0px;
                border-top-right-radius: 0px;
                padding: 15px;
            }
        }
        @media(max-width:500px) and (min-width:250px){
            .title{
                font-size: 1.3rem;
            }
             .generate-page {
                padding:100px 30px 0vh 30px;
                justify-content: flex-start;
                width:100vw;
                height:100vh;
                overflow: scroll;
            }
            .content {
                display: flex;
                flex-direction: column;
            }
            .card {
                width: 70vw;
                height: 500px;
                padding: 20px;
                border-bottom-right-radius: 0px;
                border-bottom-left-radius: 0px;
            }
            .result-card {
                margin-left: 0px;
                width:70vw;
                min-height: fit-content;
                border-top-left-radius: 0px;
                border-top-right-radius: 0px;
                padding: 50px 10px 10px 10px;
            }
        }
</style>