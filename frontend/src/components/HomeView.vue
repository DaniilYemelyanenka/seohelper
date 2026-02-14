<script setup>
    import Header from './Header.vue';
    import { ref } from 'vue';
    import api from "../api/axios"
    import { useAnalysysStore } from '../store/analysisStore';
    import { useRouter } from 'vue-router';

    const url = ref('');
    const errorMessage = ref('');

    const store = useAnalysysStore();
    const router = useRouter();

    function validateUrl(){
        errorMessage.value = '';

        if(!url.value) {
            errorMessage.value = 'Вы не ввели ссылку!';
            return false;
        }

        try{
            const parsedUrl = new URL(url.value)

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

    function submit(){
        if(validateUrl()){
            loadData();
        }
        
    }
    async function loadData(){
        try{
            const response = (await api.post("seo/analyze",
                                        { "url": url.value},
                                ))
            
            store.setResult(response.data.data);
            router.push("/seo/analyze");

        }catch(err){
            console.error("Axios error:",err.message)
            errorMessage.value = 'Упс... Что-то пошло не так. Поробуйте еще раз'
        }
        
    }

</script>

<template>
    <Header></Header>
    <div class="main-container">
        <div class="content-container">
            <h1>Анализируйте свой сайт за секунды</h1>
            <div class="input-group">
                <input  v-model="url" type="text" placeholder="Введите ссылку на сайт" @input="errorMessage = ''" :class="{ error: errorMessage }" >
                <button @click="submit">Анализ</button>
            </div>
            <p class="subtext">Мы проведем быстрый анализ и покажем возможности для SEO-продвижения</p>
            <div v-if="errorMessage" class="error-block">
                {{ errorMessage }}
            </div>
        </div>
    </div>
</template>

<style scoped>
    .main-container{
        display: flex;
        justify-content: center;
        align-items: center;
        height: 100vh;
    }
    .content-container{
        text-align: center;
        max-width: 700px;
        width: 90%;
        padding: 20px;
    }
    h1{
        font-family: 'Montserrat', sans-serif;
        font-weight: 700;
        font-size: 2rem;
        color: #6F4BB0;
        margin-bottom: 40px;
    }
    .input-group {
        display: flex;
        width: 100%;
        max-width: 500px;
        margin: 0 auto;
    }

    .input-group input[type="text"] {
        flex: 1;
        padding: 15px 20px;
        font-size: 1rem;
        border: 2px solid #8C63C2;
        border-radius: 10px 0 0 10px; 
        outline: none;
        transition: border-color 0.3s, box-shadow 0.3s;
    }

    .input-group input[type="text"]:focus {
        border-color: #6F4BB0;
        box-shadow: 0 0 10px rgba(111, 75, 176, 0.3);
    }

    .input-group button {
        padding: 15px 30px;
        font-family: 'Montserrat', sans-serif;
        font-weight: 600;
        font-size: 1rem;
        color: #FFFFFF;
        background-color: #6F4BB0;
        border: 2px solid #6F4BB0; 
        border-left: none; 
        border-radius: 0 10px 10px 0; 
        cursor: pointer;
        transition: background-color 0.3s, transform 0.2s;
    }
    .input-group button:hover {
        background-color: #8C63C2;
    }
    .subtext {
        margin-top: 15px;
        font-size: 0.9rem;
        color: #666666;
    }

    .input-group input[type="text"].error {
        border-color: #fc6656 ;
    }

    .error-block {
        margin-top: 30px;
        padding: 10px;
        border-radius: 6px;
        background-color: #fdecea;
        color: #e74c3c;
        font-size: 13px;
    }


    @media(max-width: 767px){
        .input-group input[type="text"] {
        padding: 10px 15px;
        font-size: 0.90rem;
    }
     h1{
        font-size: 1.75rem;
     }
     .input-group input[type="text"]{
        width:400px;
        line-height: 1.4rem;
        border-radius: 10px;
     }
     .input-group {
        flex-direction: column;
        align-items: center;
        gap: 15px;
     }

     .input-group button {
        width: 300px;
        border-radius: 10px;
        height: 50px;
     }

     @media(max-width:480px){
        h1{
            font-size: 1.65rem;
        }
        .input-group input[type="text"]{
            width:340px;
            line-height: 1.43rem;
        }

    }
    }
</style>