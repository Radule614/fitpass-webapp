<template>
	<div class="training-list">
		<transition-group name="list" tag="div" appear>
			<div v-for="training in trainings" :key="training.id">
				<SingleTraining :training="training">
					<template v-slot:details="slotProps">
						<p><span class="header">Duration:</span> <span class="paint">{{ training.duration }} min</span></p>
						<p><span class="header">Individuality:</span> <span class="paint">{{ slotProps.individuality }}</span></p>
						<p><span class="header">Type:</span> <span class="paint">{{ slotProps.type }}</span></p>
					</template>
				</SingleTraining>
			</div>
		</transition-group>
	</div>
</template>

<script>
import SingleTraining from './SingleTraining.vue';

export default {
	components: { SingleTraining },
	props: ["trainings"],
	setup() {
		
	},

}
</script>

<style scoped lang="scss">
	.list-enter-from {
		transform: scale(0.6);
		opacity: 0;
	}
	.list-enter-to {
		transform: scale(1);
		opacity: 1;
	}
	.list-move {
		transition: all 0.5s ease;
	}
	.training-list {
		position: relative;
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