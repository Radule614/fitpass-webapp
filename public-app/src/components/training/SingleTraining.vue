<template>
	<div class="single-training">
		<div class="img-description">
			<img :src="training.imgUrl">
			<p class="text-center lead">{{ training.description }}</p>
		</div>
		<div class="content">
			<div class="details">
				<h1 class="display-4 text-center mb-5">{{ training.name }}</h1>
				<div class="details">
					<div v-if="route.name === 'facilityPage'">
						<p><span class="header">Start:</span> <span class="paint">{{ training.start }}</span></p>
						<p><span class="header">Duration:</span> <span class="paint">{{ training.duration }} min</span></p>
						<p><span class="header">Type:</span> <span class="paint">{{ type }}</span></p>
						<p><span class="header">Trainer:</span><span class="paint">{{ training.trainerUsername }}</span></p>
					</div>
					<div v-if="route.name === 'trainings' && loggedUserType === 'TRAINER'">
						<p><span class="header">Start:</span> <span class="paint">{{ training.start }}</span></p>
						<p><span class="header">Duration:</span> <span class="paint">{{ training.duration }} min</span></p>
						<p><span class="header">Place:</span> <span class="paint">{{ training.facilityName }}</span></p>
						<p><span class="header">Type:</span> <span class="paint">{{ type }}</span></p>
					</div>
					<div v-if="route.name === 'trainings' && loggedUserType === 'CUSTOMER'">
						<p><span class="header">Start:</span> <span class="paint">{{ training.start }}</span></p>
						<p><span class="header">Duration:</span> <span class="paint">{{ training.duration }} min</span></p>
						<p><span class="header">Place:</span> <span class="paint">{{ training.facilityName }}</span></p>
						<p><span class="header">Trainer:</span> <span class="paint">{{ training.trainerUsername }}</span></p>
						<p><span class="header">Type:</span> <span class="paint">{{ type }}</span></p>
					</div>
					<div v-if="route.name === 'trainings' && loggedUserType === 'MANAGER'">

					</div>
				</div>
			</div>
			<div class="button-wrapper my-5" >
				<div v-if="loggedUserType === 'TRAINER' && training.content.type === 'PERSONAL'">
					<CustomButton class="mx-auto" @click="showModal = true">Cancel Training</CustomButton>
				</div>
				<div v-if="loggedUserType === 'CUSTOMER' && route.name !== 'trainings'">
					<CustomButton class="mx-auto" @click="handleJoin">Check In</CustomButton>
				</div>
			</div>
			<Teleport to="body">
				<ModalComponent :simple="true" :show="showModal" buttonText="confirm" :width="320" @close="showModal = false" @confirm="handleCancel">
					<template #body>
						Are you sure?
					</template>
				</ModalComponent>
			</Teleport>
		</div>
	</div>
</template>

<script>
import { computed, inject, ref } from '@vue/runtime-core'
import { useStore } from 'vuex';
import CustomButton from '../utility/CustomButton.vue';
import ModalComponent from '../ModalComponent.vue';
import settings from '@/settings';
import useToast from '@/composables/useToast';
import useCustomerUtilities from '@/composables/useCustomerUtilities';
import { useRoute } from 'vue-router';

export default {
    props: ["training"],
    setup(props) {
        const type = computed(() => props.training.type.replace("and", ", "));
        const store = useStore();
        const loggedUserType = computed(() => store.getters["auth/userType"]);
				const user = store.getters["auth/user"];
				const route = useRoute();
				const showModal = ref(false);
				const { membershipError, terminsError, checkinValidation } = useCustomerUtilities(user);
				const { showMessage, showError, showInfo } = useToast(inject('toast'));

				const handleCancel = async () => {
					const res = await fetch(`${settings.serverUrl}/api/trainings/cancel/${props.training.id}`, {
						method: 'DELETE',
						headers: { 'Data-Type': 'application/json', 'Authorization': 'Bearer ' + store.getters['auth/token'] }
					});
					const data = await res.json();
					if(res.status === 450) {
						showError(data, 'top');
					}
					if(res.ok) {
						store.commit('trainings/removeTraining', { trainingId: props.training.id });
						showMessage("Training canceled successfully", "top");
					}
					showModal.value = false;
				}
				const handleJoin = async () => {
					if(!checkinValidation()) {
						if(membershipError.value) showError("You don't have active membership.", "top");
						if(terminsError.value) showError("You ran out of termins.", "top");
						return;
					}
					const visitedFacility = {
						facilityName: props.training.facilityName,
						checkinDate: new Date(Date.now()).toLocaleString("en-GB", { year: "numeric", month: "2-digit", day: "2-digit" })
					}
					const res = await fetch(`${settings.serverUrl}/api/users/facilityVisited/${props.training.id}/${user.username}`, {
						method: 'PATCH',
						headers: {'Content-Type': 'application/json', 'Data-Type': 'application/json', 'Authorization': 'Bearer ' + store.getters['auth/token']},
						body: JSON.stringify(visitedFacility)
					});
					const data = await res.json();
					if(!res.ok) throw new Error(data);
					if (res.status === 250) {
						showInfo(data, "top");
					} else {
						showMessage(data, "top");
						store.commit('auth/addCustomerVisitedFacilities', visitedFacility.facilityName);
						store.commit('auth/addtrainingToCustomerHistory', props.training);
						store.commit('auth/decrementCustomerAppointmentNumber');
					}
				
				};

        return { type, loggedUserType, showModal, handleCancel, handleJoin, route };
    },
    components: { CustomButton, ModalComponent }
}
</script>

<style lang="scss" scoped>
	.single-training {
		padding: 20px;
		display: grid;
		grid-template-columns: 1fr 1fr;
		gap: 30px;
		background: $light-primary;
		border-radius: 20px;
		box-shadow: 1px 3px 5px rgba(50,50,50,0.2);
		margin-bottom: 20px;
		.img-description {
			display: flex;
			flex-direction: column;
		}
		img {
			width: 100%;
			height: 100%;
			margin-bottom: 10px;
		}
		p {
			margin-bottom: 10px;
			font-size: 1.3rem;
		}
		&:hover {
			transform: translateY(-1px);
		}
	}
	.details {
		padding: 0px 25px;
		p {
			display: flex;
			justify-content: space-between;
		}
		.paint {
			color: $active-primary;
		}
	}
</style>