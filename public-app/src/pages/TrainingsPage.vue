<template>
	<div class="container-lg m-5">
		<CustomButton @click="showFilters = true" v-if="!showFilters">Show Filters</CustomButton>
		<CustomButton @click="showFilters = false" v-if="showFilters">Close Filters</CustomButton>	
		<transition name="filters">
			<div v-if="showFilters" class="mt-4">
				<SearchTraining />
			</div>
		</transition>
		
		<div v-if="loggedUserType === 'TRAINER'">
			<CustomButton v-if="!showForm" @click="showForm = true">Add Training</CustomButton>
			<CustomButton v-if="showForm" @click="showForm = false">Close</CustomButton>
			<transition name="addForm" appear>
				<AddTraining v-show="showForm"/>
			</transition>
			<!-- <div class="button mt-5">
				<CustomButton v-if="!showFilters" @click="showFilters = true">Filter</CustomButton>
				<CustomButton v-if="showFilters" @click="showFilters = false">Close</CustomButton>
			</div> -->
			<transition name="filter" v-if="showFilters" appear>
				<div class="radio-wrapp px-2">
					<div class="form-check my-2">
						<input class="form-check-input" type="checkbox" value="GROUP" id="check-group" @change="fetchTrainings" v-model="filters">
						<label class="form-check-label" for="check-group">
							Group
						</label>
					</div>
					<div class="form-check"> 
						<input class="form-check-input" type="checkbox" value="PERSONAL" id="check-personal" @change="fetchTrainings" v-model="filters">
						<label class="form-check-label" for="check-personal">
							Personal
						</label>
					</div>
				</div>
			</transition>
		</div>
		<!-- TRAINER -->
		<div v-if="loggedUserType === 'TRAINER'">
			<div v-if="trainerFilteredTrainings && trainerFilteredTrainings.length" class="trainings mt-4">
				<TrainingList :trainings="trainerFilteredTrainings"/>
			</div>
			<p class="mt-3" v-else>You don't have any trainings.</p>
		</div>
		<!-- CUSTOMER -->
		<div v-if="loggedUserType === 'CUSTOMER'">
			<div v-if="customerTrainings.length" class="training mt-4">
				<TrainingList :trainings="customerTrainings"/>
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
        const trainerFilteredTrainings = computed(() => store.getters["trainings/getTrainerTrainings"]);
				const showForm = ref(false);
				const loggedUserType = store.getters['auth/userType'];
				const filters = ref([]);
				const showFilters = ref(false);
				const customerTrainings = loggedUserType === 'CUSTOMER' ? computed(() => store.getters['trainings/getUserTrainings']) : null;

				onMounted(() => {
					store.dispatch('trainings/fetchUserTrainings', { username: store.getters['auth/username'] });
				});

				const fetchTrainings = async () => {
					try {
						await store.dispatch('trainings/fetchTrainings', filters.value);
					} catch(err) {
						console.log(err.message);
					}
				}

        return { trainerFilteredTrainings, showForm, loggedUserType, filters, showFilters ,fetchTrainings, customerTrainings };
    },
   
}
</script>

<style scoped>
	
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

	.filter-enter-from,
	.filter-leave-to {
		opacity: 0.7;
		max-height: 0;
	}

	.filter-enter-to,
	.filter-leave-from {
		max-height: 100px;
		opacity: 1;
	}

	.filter-enter-active {
		transition: all 0.8s ease-out;
	}

	.filter-leave-active {
		transition: all 0.8s ease-in;
	}

	/* Filters */
	.filters-enter-from,
	.filters-leave-to {
		max-height: 0;
	}
	.filters-enter-active,
	.filters-leave-active {
		transition: all 0.3s linear;
	}
	.filters-enter-to,
	.filters-leave-from {
		max-height: 200px;
	}
</style>