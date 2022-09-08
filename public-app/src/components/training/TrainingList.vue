<template>
	<div class="training-list">
		<transition-group name="list" appear>
			<div v-for="training in trainings" :key="training.id">
				<SingleTraining :training="training" />
			</div>
		</transition-group>
	</div>
</template>

<script>
import SingleTraining from './SingleTraining.vue';

export default {
	components: { SingleTraining },
	props: ["trainings"],
	setup(props) {
	},

}
</script>

<style scoped lang="scss">
	.list-enter-from {
		transform: scale(0.4);
		opacity: 0;
	}
	.list-enter-to {
		transform: scale(1);
		opacity: 1;
	}
	.list-move {
		transition: all 0.8s ease;
	}
	.training-list {
		position: relative;
	}
	
</style>