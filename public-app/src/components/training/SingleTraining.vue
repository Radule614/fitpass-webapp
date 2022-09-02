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
					<div v-if="loggedUserType == 'TRAINER'">
						<p><span class="header">Start:</span> <span class="paint">{{ training.start }}</span></p>
						<p><span class="header">Duration:</span> <span class="paint">{{ training.duration }} min</span></p>
						<p><span class="header">Place:</span> <span class="paint">{{ training.facilityName }}</span></p>
						<p><span class="header">Individuality:</span> <span class="paint">{{ training.content.type }}</span></p>
						<p><span class="header">Type:</span> <span class="paint">{{ type }}</span></p>
					</div>
					<div v-if="loggedUserType == 'CUSTOMER'">
						<p><span class="header">Start:</span> <span class="paint">{{ training.start }}</span></p>
						<p><span class="header">Duration:</span> <span class="paint">{{ training.duration }} min</span></p>
						<p><span class="header">Type:</span> <span class="paint">{{ type }}</span></p>
						<p><span class="header">Trainer:</span><span class="paint">{{ training.trainerUsername }}</span></p>
					</div>
				</div>
			</div>
			<div class="button-wrapper my-5" >
				<div v-if="loggedUserType === 'TRAINER' && training.content.type === 'PERSONAL'">
					<CustomButton class="mx-auto" @click="showModal = true">Cancel Training</CustomButton>
				</div>
				<div v-if="loggedUserType === 'CUSTOMER'">
					<CustomButton class="mx-auto">Join</CustomButton>
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

export default {
    props: ["training"],
    setup(props) {
        const type = computed(() => props.training.type.replace("and", ", "));
        const store = useStore();
        const loggedUserType = store.getters["auth/userType"];
				const showModal = ref(false);
				const { showError } = useToast(inject('toast'));

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
					}
					showModal.value = false;
				}

        return { type, loggedUserType, showModal, handleCancel };
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