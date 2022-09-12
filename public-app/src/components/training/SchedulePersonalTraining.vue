<template :date="date" :time="time" :duration="duration" :type="type">
	<form>
		<div class="row align-items-center mb-3">
			<div class="col header">
				Date:
			</div>
			<div class="col input">
				<input type="date" v-model="date" :min="minDate">
			</div>
		</div>
		<div class="row align-items-center mb-2">
			<div class="col header">
				Time:
			</div>
			<div class="col input">
				<input type="time" v-model="time">
			</div>
		</div>
		<div class="row align-items-center mb-2">
			<div class="col header">
				Duration(minutes):
			</div>
			<div class="col input">
				<input type="number" class="form-control" min="1" max="600" v-model="duration">
			</div>
		</div>
		<div class="row align-items-center mb-2">
			<div class="col header">
				Type:
			</div>
			<div class="col input">
				<select class="form-select" v-model="type">
					<option value="aerobic">Aerobic</option>
					<option value="strength">Strength</option>
					<option value="balance&stability">Balance & Stability</option>
					<option value="coordination&agility">Coordination & Agility</option>
					<option value="flexibility&mobility">Flexibility & Mobility</option>
				</select>
			</div>
		</div>
		<div class="row align-item-center">
			<div class="col mt-4">
				<CustomButton class="mx-auto inverse" @click="handleSchedule">Schedule</CustomButton>
				<div class="error mt-2 text-center">{{ error }} {{ durationError }}</div>
			</div>
		</div>
	</form>
</template>

<script>
import { computed, inject, ref } from 'vue';
import CustomButton from '../utility/CustomButton.vue';
import useDate from '@/composables/useDate';
import settings from '@/settings';
import { useStore } from 'vuex';
import useToast from '@/composables/useToast';

export default {
    props: ["content"],
    setup(props, context) {
        const date = ref("");
        const time = ref("");
        const duration = ref(1);
        const type = ref("aerobic");
				const error = ref(null);
				const durationError = ref(null);
				const { formateDateForRequest } = useDate();
				const store = useStore();
				const { showError, showMessage } = useToast(inject('toast'));
				const minDate = computed(() => {
					const today = new Date();
					const minDate = new Date(today.setDate(today.getDate() + 4));
					return minDate.toISOString().split('T')[0];
				});

				const handleSchedule = async () => {
					if(!validateInput()) return;

					const newTraining = {
						name: 'Personal Training',
						date: formateDateForRequest(date.value),
						time: time.value,
						duration: duration.value,
						type: type.value,
						contentId: props.content.id,
						facilityName: props.content.facility_id,
						description: '',
						trainerUsername: props.content.trainer.username
					};
					
					const res = await fetch(`${settings.serverUrl}/api/trainings/addPersonal/${store.getters['auth/user'].username}`, {
						method: 'POST',
						headers: { 'Content-Type': 'application/json', 'Data-Type': 'application/json', 'Authorization': 'Bearer ' + store.getters['auth/token']},
						body: JSON.stringify(newTraining)
					});

					const data = await res.json();
					if(!res.ok) throw new Error(data);
					if(res.status === 250) {
						showError(data, "top");
					} else {
						store.commit('auth/addtrainingToCustomerHistory', data);
						store.commit('auth/addCustomerVisitedFacilities', data.facilityName);
						store.commit('trainings/addTraining', { training: data });
						store.commit('auth/decrementCustomerAppointmentNumber');
						showMessage("Your training has been scheduled successfully.", "top");
					}
					context.emit('closeModal');
					console.log(data);
				};

				const validateInput = () => {
					resetErrors();
					if(date.value === "" || time.value === "")  {
						error.value = 'Plase fill all information.'
						return false;
					}
					if(duration.value > 600 || duration.value < 1) {
						durationError.value = 'Duration must be between 1 and 600 minutes.';
						return false;
					}

					return true;
				};

				const resetErrors = () => {
					error.value = null;
					durationError.value = null;
				};

        return { date, time, duration, type, handleSchedule, error, durationError, minDate };
    },
    components: { CustomButton }
}
</script>

<style scoped lang="scss">
	form{
		input {
			margin-bottom: 0 !important;
			min-width: 150px;
			max-width: 150px;
		}
	}
</style>