<template>
	<div class="container-lg m-5">
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
		<div v-if="trainerFilteredTrainings.length" class="trainings mt-4">
				<TrainingList :trainings="trainerFilteredTrainings"/>
		</div>
		<p class="mt-3" v-else>You don't have any trainings yet.</p>
	</div>
</template>

<script>
import { ref } from '@vue/runtime-core'
import { useStore } from 'vuex'
import TrainingList from '@/components/training/TrainingList.vue';
import CustomButton from '../components/utility/CustomButton.vue';
import AddTraining from '../components/training/AddTraining.vue';
import { computed } from '@vue/reactivity';

export default {
		components: { TrainingList, CustomButton, AddTraining },
    setup() {
        const store = useStore();
        const trainerFilteredTrainings = computed(() => store.getters["trainings/getTrainerTrainings"]);
				const showForm = ref(false);
				const loggedUserType = store.getters['auth/userType'];
				const filters = ref([]);
				const showFilters = ref(false);

				const fetchTrainings = async () => {
					try {
						await store.dispatch('trainings/fetchTrainings', filters.value);
					} catch(ex) {
						console.log(ex.message);
					}
					console.log(store.getters['trainings/getTrainerTrainings']);
				}

        return { trainerFilteredTrainings, showForm, loggedUserType, filters, showFilters ,fetchTrainings };
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

</style>