import { createApp } from 'vue';
import App from './App.vue';
import "bootstrap/dist/css/bootstrap.min.css";
import Toaster from "@meforma/vue-toaster";

import router from './router.js';
import store from './store/index.js';

import { library } from '@fortawesome/fontawesome-svg-core';
import { fas } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
library.add(fas);

import CustomButton from './components/utility/CustomButton.vue';
import CustomLink from './components/utility/CustomLink.vue';

const app = createApp(App);
app.use(router);
app.use(store);
app.use(Toaster);
app.component('fa-icon', FontAwesomeIcon);
app.component('custom-button', CustomButton);
app.component('custom-link', CustomLink);
app.mount('#app');



