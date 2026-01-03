<script setup>
    import { isGlobalLoading } from "./store/loading"
</script>

<template>
    <router-view />

    <div v-if="isGlobalLoading" class="overlay">
        <div class="loader"></div>
    </div>
</template>

<style scoped>
    .overlay {
    position: fixed;
    inset: 0;
    background-color: rgba(0, 0, 0, 0.4);
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 9999;
    }

    .loader {
    width: 48px;
    height: 48px;
    border: 4px solid rgba(255,255,255,0.3);
    border-top: 4px solid white;
    border-radius: 50%;
    animation: spin 0.8s linear infinite;
    }

    @keyframes spin {
    to {
        transform: rotate(360deg);
    }
    }
</style>
