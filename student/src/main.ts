import "./assets/main.css"

import "./assets/patternfly.css";
import "./assets/patternfly-addons.css";
import "./assets/font-awesome.css";

import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";

const app = createApp(App);

app.use(createPinia());
app.use(router);

app.mount("#app");
