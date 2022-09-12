<template>
	<div class="container-lg m-5">
		<CustomButton @click="showFilters = true" v-if="!showFilters">Show Filters</CustomButton>
		<CustomButton @click="showFilters = false" v-if="showFilters">Close Filters</CustomButton>	
		<transition name="filters">
			<div v-if="showFilters" class="mt-4">
				<SearchTraining/>
			</div>
		</transition>
		
		<div v-if="loggedUserType === 'TRAINER'">
			<div class="mt-4">
				<CustomButton v-if="!showForm" @click="showForm = true">Add Training</CustomButton>
				<CustomButton v-if="showForm" @click="showForm = false">Close</CustomButton>
			</div>
			<transition name="addForm" appear>
				<AddTraining v-show="showForm"/>
			</transition>
		</div>
		<!-- CUSTOMER, TRAINER -->
		<div v-if="loggedUserType === 'TRAINER' || loggedUserType === 'CUSTOMER'">
			<div v-if="userTrainings.length" class="trainings mt-4">
				<TrainingList :trainings="userTrainings"/>
			</div>
			<p class="mt-3" v-else>You don't have any trainings.</p>
		</div>
	</div>
</template>

<script>
import { onMounted, ref } from '@vue/runtime-core'
import { useStore } from 'vuex'
import TrainingList from '@/components/training/TrainingList.vue';
import CustomButton from '../components/utility/CustomButton.vue';
import AddTraining from '../components/training/AddTraining.vue';
import { computed } from '@vue/reactivity';
import SearchTraining from '@/components/training/SearchTraining.vue';

export default {
		components: { TrainingList, CustomButton, AddTraining, SearchTraining },
    setup() {
        const store = useStore();
				const showForm = ref(false);
				const loggedUserType = store.getters['auth/userType'];
				const filters = ref([]);
				const showFilters = ref(false);
				const userTrainings = computed(() => store.getters['trainings/getUserTrainings']);

				onMounted(async () => {
					await store.dispatch('trainings/fetchUserTrainings', { username: store.getters['auth/username'] });
				});

        return { showForm, loggedUserType, filters, showFilters, userTrainings };
    },
   
}
</script>

<style scoped>
	
	/* Add Form */

	.addForm-enter-from,
	.addForm-leave-to {
		/* transform: translateX(-200px); */
		opacity: 0;
		max-height: 0px;
	}

	.addForm-enter-to,
	.addForm-leave-from {
		/* transform: translateX(0px); */
		opacity: 1;
		max-height: 300px;
	}

	.addForm-enter-active {
		transition: all 0.3s ease-out;
	}

	.addForm-leave-active {
		transition: all 0.3s ease-in;
	}

	/* Filters */

	.filters-enter-from,
	.filters-leave-to {
		max-height: 0;
		opacity: 0;
	}
	.filters-enter-active {
		transition: all 0.3s ease-out;
	}

	.filters-leave-active {
		transition: all 0.3s ease-in;
	}

	.filters-enter-to,
	.filters-leave-from {
		max-height: 200px;
		opacity: 1;
	}
</style>