<template>
	<form @submit.prevent="handleSearch">
		<input type="text" name="name" v-model="facilityName" placeholder="Facility name...">
		<button><fa-icon icon="magnifying-glass"></fa-icon></button>
	</form>
	<div class="sort mt-4 d-flex">
		<h5>Sort:</h5>
		<div class="form-check ms-3">
			<input type="radio" class="form-check-input" name="facilityNameSort" v-model="sortByFacName" @change="handleSearch">
			<label class="form-check-label" for="facilityNameSort">Facility Name</label>
		</div>
		<div class="form-check ms-3">
			<input type="checkbox" class="form-check-input" name="reverse" v-model="reverse" @change="handleSearch">
			<label class="form-check-label" for="reverse">Reverse</label>
		</div>
	</div>
	<div class="filter d-flex gap-3 align-items-center mt-3">
		<h5>Filter:</h5>
		<select class="form-select" v-model="facilityType" @change="handleSearch">
			<option value="" selected>Facility Type</option>
			<option value="gym">Gym</option>
			<option value="pool">Pool</option>
			<option value="dance_studio">Dance Studio</option>
			<option value="sports_center">Sports Center</option>
			<option value="other">Other</option>
		</select>
		<select class="form-select" v-model="trainingType" @change="handleSearch">
			<option value="" selected>Training Type</option>
			<option value="strength">Strength</option>
			<option value="aerobic">Aerobic</option>
			<option value="balance&stability">Balance & Stability</option>
			<option value="coordination&agility">Coordination & Agility</option>
			<option value="flexibility&mobility">Flexibility & Mobility</option>
		</select>
	</div>
</template>

<script>
import { ref } from 'vue';
import { useStore } from 'vuex';
import CustomButton from '../utility/CustomButton.vue';

export default {
    setup(props, context) {
        const facilityName = ref("");
        const store = useStore();
        const username = store.getters["auth/username"];
        const sortByFacName = ref(false);
        const reverse = ref(false);
        const facilityType = ref("");
        const trainingType = ref("");

        const handleSearch = async () => {
						const search = {
							facilityName: facilityName.value
						};
						const sort = sortByFacName.value ? {
							reverse: reverse.value
						} : null;
						const filter = (facilityType.value !== '' || trainingType.value !== '') ? {
							facilityType: facilityType.value.toUpperCase(),
							trainingType: trainingType.value.toUpperCase().replace('&', 'and')
						} : null;
            const payload = {
                username,
                filters: { search, sort, filter }
            };
						store.dispatch("trainings/fetchUserTrainings", payload);
        };
        return { facilityName, handleSearch, sortByFacName, reverse, facilityType, trainingType };
    },
    components: { CustomButton }
}
</script>

<style scoped lang="scss">
	form {
		display: grid;
		grid-template-columns: 8fr 1fr 10fr;
		input {
			width: 100% !important;
			margin-bottom: 0 !important;
			border-radius: 0 !important;
			padding: 15px 10px !important;
			&:nth-of-type(2) {
				border-left: 3px solid $active-primary !important;
			}
		}
		button {
			border: 0;
			background: $active-primary;
			color: #fff;
		}
	}
	.filter {
		select {
			width: 250px;
		}
		h5 {
			margin: 0;
		}
	}
</style>