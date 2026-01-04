<script setup>
  import { ref } from 'vue'

  const isOpen = ref(false)

  function toggleMenu() {
    isOpen.value = !isOpen.value
  }
</script>

<template>
  <header>
    <div class="header-container">
      <div class="header-left">SEO HELPER</div>

      <div class="header-center">
          <ul>
              <router-link class=" li active-link" to="/">Анализ</router-link>
              <router-link class="li" to="/content/generate">Генерировать контент</router-link>
              <router-link class="li" to="/track-link">Генерировать трек ссылку</router-link>
          </ul>
      </div>

      <div class="burger" @click="toggleMenu">
        <div :class="{ 'line1': true, 'open': isOpen }"></div>
        <div :class="{ 'line2': true, 'open': isOpen }"></div>
        <div :class="{ 'line3': true, 'open': isOpen }"></div>
      </div>

      <nav class="nav-mobile" :class="{ 'open': isOpen }">
        <router-link @click="toggleMenu" to="/">Анализ</router-link>
        <router-link @click="toggleMenu" to="/content/generate">Генерировать контент</router-link>
        <router-link @click="toggleMenu" to="/track-link">генерироват трек ссылку</router-link>
        <router-link @click="toggleMenu" to="/login">Войти</router-link>
      </nav>

      <div class="header-right">
          <button>Войти</button>
      </div>
    </div>
</header>

</template>

<style scoped>
  header {
      display: flex;
      width: 100vw;
      align-items: center;
      justify-content: center;
      background-color: #FFFFFF;
      border-bottom: 2px solid #E5E5E5;
      box-shadow: 0 2px 5px rgba(0,0,0,0.03);
      z-index: 1000;
      left: 0;
      top:0;
      position: fixed;
      font-family: 'Roboto', sans-serif;
  }
  .header-container{
    width:1400px;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 15px 40px;
  }


  .header-left {
      font-family: 'Montserrat', sans-serif;
      font-weight: 700;
      font-size: 1.5rem;
      color: #9B7ED6; 
  }

 
  .header-center ul {
      display: flex;
      list-style: none;
      margin: 0;
      padding: 0;
      gap: 15px;
  }

  .header-center ul .li {
      padding: 10px 10px;
      font-family: 'Montserrat', sans-serif;
      font-weight: 400;
      font-size: 0.95rem; 
      text-decoration: none;
      color: #666666;
      transition: background-color 0.3s, transform 0.2s;
  }
  .header-center ul .active-link{
    background-color: #6F4BB0;
    border-radius: 10px;
    color: #FFFFFF;
  }

  .header-center ul .li:hover {
      background-color: #6829ba0b;
      border-radius: 10px;
      color: #404040;
      cursor: pointer;
      transform: scale(1.025);
  }

  .header-right button {
      padding: 10px 20px;
      font-family: 'Montserrat', sans-serif;
      font-weight: 600;
      font-size: 0.95rem;
      color: #6F4BB0;
      background-color: transparent;
      border: 2px solid #6F4BB0;
      border-radius: 8px;
      cursor: pointer;
      transition: background-color 0.3s, color 0.3s, transform 0.2s;
  }

  .header-right button:hover {
      background-color: #6F4BB0;
      color: #FFFFFF;
      transform: translateY(-2px);
  }

  .burger {
  display: none;
  cursor: pointer;
  flex-direction: column;
  gap: 5px;
  width: 25px;
}

.burger div {
  height: 3px;
  background-color: #6F4BB0;
  transition: all 0.3s ease;
}

.burger .line1.open {
  transform: rotate(45deg) translate(5px, 5px);
}
.burger .line2.open {
  opacity: 0;
}
.burger .line3.open {
  transform: rotate(-45deg) translate(5px, -5px);
}

.nav-mobile {
  display: none;
  flex-direction: column;
  height:100vh;
  background-color: #FFFFFF;
}

.nav-mobile a {
  color: #404040;
  background-color: #FFFFFF;
  border-bottom: 1px solid #6F4BB0;
  padding: 20px 20px 20px 20px;
  min-height: 5vh;
  font-size: 1.2rem;

  text-decoration: none;
}
@media (min-width: 767px) and (max-width: 840px) {

        .header-left {
            font-weight: 600;
            font-size: 1.2rem;
            color: #9B7ED6; 
        }
        .header-center ul li {
          font-size: 0.85rem;
        }
        .header-center ul {
          gap: 10px
        }
}

.nav-mobile a:hover {
  background-color: #555;
}

@media (max-width: 767px) {
  .header-center {
    display: none;
  }
  .header-right{
    display: none;
  }

  .burger {
    display: flex;
  }

  .nav-mobile.open {
    display: flex;
    position: absolute;
    top: 60px; 
    left: 0;
    right: 0;
    flex-direction: column;
    z-index: 1000;
  }
}

  
</style>
