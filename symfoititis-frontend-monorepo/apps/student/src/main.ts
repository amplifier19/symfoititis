import { createApp } from 'vue';
import App from './app/App.vue';

import router from './router';
import { createPinia } from 'pinia';

const app = createApp(App);

app.use(router);
app.use(createPinia());

app.mount('#root');
