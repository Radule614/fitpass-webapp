<template>
	<div class="buttons">
		<CustomButton class="single-button" @click="sortName">
			Name
			<fa-icon class="icon asc" v-if="sortedAscName" :icon="['fas', 'sort-up']"/>
			<fa-icon class="icon desc" v-if="!sortedAscName" :icon="['fas', 'sort-down']"/>
		</CustomButton>
		<CustomButton class="single-button" @click="sortLocation">
			Location
			<fa-icon class="icon asc" v-if="sortedAscLocation" :icon="['fas', 'sort-up']"/>
			<fa-icon class="icon desc" v-if="!sortedAscLocation" :icon="['fas', 'sort-down']"/>
		</CustomButton>
		<CustomButton class="single-button" @click="sortAvgGrade">
			Average Grade
			<fa-icon class="icon asc" v-if="sortedAscAvgGrade" :icon="['fas', 'sort-up']"/>
			<fa-icon class="icon desc" v-if="!sortedAscAvgGrade" :icon="['fas', 'sort-down']"/>
		</CustomButton>
	</div>
</template>

<script>
import { ref } from "vue";
import CustomButton from "../../utility/CustomButton.vue";
import { useStore } from "vuex";

export default {
		components: { CustomButton },
		emits: ['sortName', 'sortLocation', 'sortAvgGrade'],
    setup(props, context) {
			const store = useStore();
			const sortedAscName = ref(true);
			const sortedAscLocation = ref(true);
			const sortedAscAvgGrade = ref(true);

			const sortName = () => {
				sortedAscName.value = !sortedAscName.value;
				sortedAscName.value ? context.emit('sortName', true) : context.emit('sortName', false);
			};

			const sortLocation = () => {
				sortedAscLocation.value = !sortedAscLocation.value;
				sortedAscLocation.value ? context.emit('sortLocation', true) : context.emit('sortLocation', false);
			};

			const sortAvgGrade = () => {
				sortedAscAvgGrade.value = !sortedAscAvgGrade.value;
				sortedAscAvgGrade.value ? context.emit('sortAvgGrade', true) : context.emit('sortAvgGrade', false);
			};

			return { sortName, sortLocation, sortAvgGrade, sortedAscName, sortedAscLocation, sortedAscAvgGrade }
    }
}
</script>

<style scoped>
	.icon {
		font-size: 1.6rem;
		transition: all 0.4s ease;
	}

	.icon.asc {
		transform: translateY(7px);
	}

	.buttons {
		padding: 20px 10px;
		display: flex;
	}

	.buttons .single-button {
		margin-right: 15px;
	}

</style>