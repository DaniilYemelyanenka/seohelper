import { defineStore } from "pinia";
import  api  from "../api/axios";

export const useAnalysysStore = defineStore('',{
    state: () => ({
        result: null,
        history:[],
        platromList:[],
        contentTypeList:[],
    }),
    getters: {
        id: (state)  => state.result?.id ?? 0,
        url: (state) => state.result?.url ?? '',
        score: (state) => state.result?.score ?? 0,
        recommendations: (state) => state.result?.recommendations ?? ''
    },
    actions:{
        setResult(data){
            this.result = data;
        },
        clearResult(){
            this.result = null;
        },

        async fetchHistory() {
            const response  = await api.get('/seo/history');
            this.history = response.data;
        },
        async fetchPlatform(){
            const response = await  api.get("/plsatforms");
            this.platromList = response.data;
        },
        async fetchContentType(){
            const response = await api.get("/contentTypes");
            this.contentTypeList = response.data;
        }

    }
})