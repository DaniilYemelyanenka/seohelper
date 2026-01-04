<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const errorCode = ref('404');
const errorMessage = ref('Страница не найдена');

function goHome() {
  router.push('/');
}
</script>

<template>
  <div class="error-page">
    <div class="error-container">
      <h1 class="code">{{ errorCode }}</h1>
      <h2 class="message">{{ errorMessage }}</h2>
      <p class="description">
        Похоже, что страница, которую вы ищете, не существует или была перемещена.
      </p>
      <button @click="goHome">Вернуться на главную</button>
    </div>
    <div class="error-illustration">
      <img src="https://cdn-icons-png.flaticon.com/512/4076/4076549.png" alt="Error" />
    </div>
  </div>
</template>

<style scoped>
.error-page {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100vh;
  width: 100vw;
  font-family: 'Montserrat', sans-serif;
  padding: 20px;
  box-sizing: border-box;
  flex-direction: column;
}

.error-container {
  text-align: center;
  max-width: 500px;
  margin-bottom: 30px;
}

.code {
  font-size: 8rem;
  font-weight: 700;
  color: #6F4BB0;
  margin: 0;
}

.message {
  font-size: 2rem;
  color: #444;
  margin: 10px 0;
}

.description {
  color: #666;
  font-size: 1rem;
  margin-bottom: 20px;
}

button {
  padding: 12px 24px;
  font-size: 1rem;
  font-weight: 600;
  color: #fff;
  background-color: #6F4BB0;
  border: none;
  border-radius: 10px;
  cursor: pointer;
  transition: background-color 0.3s, transform 0.2s;
}

button:hover {
  background-color: #8C63C2;
  transform: translateY(-2px);
}

.error-illustration img {
  width: 300px;
  max-width: 80vw;
}

@media (max-width: 600px) {
  .code {
    font-size: 5rem;
  }
  .message {
    font-size: 1.5rem;
  }
}
</style>
