<script setup>
    import { onMounted,ref,computed } from 'vue';
    import { useAnalysysStore } from '../store/analysisStore';
    import { useRouter } from 'vue-router';
    import Header from './Header.vue';

    const store = useAnalysysStore();
    const router = useRouter();

    const currentPage = ref(1);
    const perPage = 8;

    onMounted(() => {
        store.fetchHistory();
        console.log(store.history)
    })

    const totalPages = computed(() => Math.ceil(store.history.length / perPage))

    const paginatedHistory = computed(() => {
        const start = (currentPage.value - 1) * perPage
        return store.history.slice(start, start + perPage);
    }) 


    function scoreClass(score) {
        if (score <= 4) return 'red'
        if (score <= 7) return 'orange'
        return 'green'
    }

    function goToPage(page) {
        if (page < 1 || page > totalPages.value) return
            currentPage.value = page
    }
    function goToDetails(id){
        const record = store.history.find(item => item.id === id);
        if(record){
            store.setResult(record);
            router.push("/seo/analyze");
        } 
    }

</script>
<template>
    <Header></Header>
    <div class="history-page">
    <h1 class="title">История анализов</h1>

    <div v-if="!store.history.length" class="empty">
      История анализов пуста
    </div>

    <div class="list">
      <div v-for="item in paginatedHistory"
        :key="item.id"
        class="card">

        <div class="card-header">
            <div class="history-info">
                <span class="url">
                    Анализ сайта : <span class="value">{{ item.url }}</span>
                </span>

                <span class="score">
                    итоговый результат :
                    <span class="score-number" :class="scoreClass(item.score)">
                        {{ item.score }}/10
                    </span>
                </span>
            </div>
            <div class="history-actions">
                <button class="btn-details" @click="goToDetails(item.id)">
                    Подробнее
                </button>

                <button class="btn-delete" @click="deleteAnalysis(item.id)">
                    Удалить
                </button>
            </div>
        </div>
       </div>
    </div>

    <div v-if="totalPages > 1" class="pagination">
        <button :disabled="currentPage === 1" @click="goToPage(currentPage - 1)">
        ‹
        </button>

        <button v-for="page in totalPages" :key="page" :class="{ active: page === currentPage }" @click="goToPage(page)">
            {{ page }}
        </button>

        <button :disabled="currentPage === totalPages" @click="goToPage(currentPage + 1)">
            ›
        </button>
</div>


  </div>
</template>
<style scoped>
    .hidden{
        display: none;
    }
    .history-page {
        width: 80vw;
        height: 80vh;
        margin: 40px auto;
        padding: 5vh 40px 5vh 40px;
        background-color: #FFFFFF;
        border-radius: 20px;
        box-shadow: 0 10px 25px rgba(0, 0, 0, 0.1);
        font-family: 'Montserrat', sans-serif;
    }

    .title {
        text-align: center;
        color: #6F4BB0;
        margin-bottom: 30px;
    }

    .list {
        display: flex;
        flex-direction: column;
        height: 60vh;
        overflow: scroll;
        gap: 20px;
    }

    .card {
        background: #fff;
        border-radius: 16px;
        padding: 20px;
        box-shadow: 0 10px 25px rgba(0,0,0,0.08);
    }

    .card-header {
        display: flex;
        justify-content: space-between;
        padding-left: 5vw;
        padding-right: 5vh;
    }
    .history-info, .history-actions{
        display: flex;
        justify-content: space-between;
        gap: 15px;
    }
    button {
        padding: 5px;
        font-family: 'Montserrat', sans-serif;
        font-weight: 500;
        font-size: 0.6rem;
        width: 75px;
        height: 30px;
        color: #FFFFFF;
        background-color: #6F4BB0;
        border: 2px solid #6F4BB0; 
        border-radius: 7px; 
        cursor: pointer;
        transition: background-color 0.3s, transform 0.2s;
    }
     button:hover {
        background-color: #8C63C2;
    }
    button.btn-delete {
        background-color: #da6053;
         border: 1px solid #da6053;
    }
    button.btn-delete:hover {
        background-color: #fb9387;
    }

    .url {
        font-weight: 600;
        word-break: break-all;
    }

    .score {
        font-weight: 700;
    }

    .score-number.red { color: #e74c3c; }
    .score-number.orange { color: #f39c12; }
    .score-number.green { color: #2ecc71; }

    .recommendations {
        padding-left: 20px;
    }

    .pagination {
        display: flex;
        justify-content: center;
        gap: 8px;
        margin-top: 25px;
    }

    .pagination button {
        width: 36px;
        height: 36px;
        border-radius: 8px;
        font-size: 0.85rem;
        background: #f0ecfa;
        color: #6F4BB0;
        border: none;
        cursor: pointer;
        transition: background 0.2s ease;
    }

    .pagination button.active {
        background: #6F4BB0;
        color: #fff;
    }

    .pagination button:disabled {
        opacity: 0.4;
        cursor: not-allowed;
    }

    @media (max-width: 780px) and (min-width: 590px) {
    .title{
        font-size: 1.6rem;
    }
    .history-page {
        width: 95vw;
        padding: 5vh 10px;
        font-size: 0.9rem;
    }

    .card-header {
        flex-direction: row;
        gap: 12px;
    }

    .history-actions {
        flex-direction: column;
        justify-content: space-evenly;
    }

    .history-info {
        flex-direction: column;
        gap: 6px;
    }

    button {
        font-size: 0.75rem;
        width: 100%;
        max-width: 120px;
    }
    }
    @media(max-width: 590px){
    .title{
        font-size: 1.4rem;
    }
    .card{
        padding: 5px;
    }
    .history-page {
        width: 95vw;
        padding: 10px;
        font-size: 0.9rem;
    }

    .card-header {
        flex-direction: column;
        padding:5px;
        gap: 10px;
    }

    .history-actions {
        flex-direction: row;
        justify-content: space-evenly;
    }

    .history-info {
        flex-direction: column;
        gap: 4px;
    }

    button {
        font-size: 0.75rem;
        width: 100%;
        max-width: 120px;
    }
    }

    @media (min-width: 780px) and (max-width: 1230px) {
        .title{
            font-size: 1.8rem;
        }
        .history-info{
            flex-direction: column;
            gap: 10px;
        }
        .history-page {
            width: 90vw;
            padding: 5vh 20px;
            font-size: 0.9rem;
        }

        .card-header {
            flex-direction: row;
            flex-wrap: wrap;
            justify-content: space-between;
        }

        .history-actions {
            gap: 12px;
        }

        button {
            font-size: 0.8rem;
            width: auto;
            padding: 6px 12px;
        }
    }

    @media (min-width: 1230px) {
    .history-page {
        width: 80vw;
        padding: 5vh 40px;
    }

    .card-header {
        flex-direction: row;
        justify-content: space-between;
    }

    button {
        font-size: 0.85rem;
        width: 75px;
        height: 30px;
    }
    }
</style>