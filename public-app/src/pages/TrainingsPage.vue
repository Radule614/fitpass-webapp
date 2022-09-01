<template>
	<div class="container-lg m-5">
		<div v-if="loggedUserType === 'TRAINER'">
			<CustomButton v-if="!showForm" @click="showForm = true">Add Training</CustomButton>
			<CustomButton v-if="showForm" @click="showForm = false">Close</CustomButton>
			<transition name="addForm" appear>
				<AddTraining v-if="showForm"/>
			</transition>
		</div>
		<div v-if="trainerTrainings.length" class="trainings mt-4">
				<TrainingList :trainings="trainerTrainings"/>
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
        const trainerTrainings = computed(() => store.getters["trainings/getTrainerTrainings"]);
				const showForm = ref(false);
				const loggedUserType = store.getters['auth/userType'];

				console.log(trainerTrainings.value);
        return { trainerTrainings, showForm, loggedUserType };
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

</style>