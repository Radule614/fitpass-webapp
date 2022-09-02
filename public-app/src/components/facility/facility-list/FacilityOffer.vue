<template>
	<div class="offer my-5">
		<div class="content">
			<h1 class="display-3 title">OFFER</h1>
			<p class="lead">CHOSE TRAINING FROM OUR WIDE COLLECTION.</p>
		</div>
	</div>
	<div class="facility-offer row mt-4 mb-5 justify-content-center g-2">
		<div class="col-11">
			<h4 class="display-4 text-center offer-title">Group Trainings</h4>
			<TrainingList :trainings="facilityTrainings" type="CUSTOMER" class="list p-5">
			
			</TrainingList>
			<h4 class="display-4 text-center offer-title">Personal Content</h4>
			<div class="list d-flex justify-content-around flex-wrap">
				<div v-for="single in facilityContentWithTrainers" :key="single.id">
					<div class="single">
						<div class="header text-center">
							{{ single.name }}
						</div>
						<div class="body d-grid align-items-center">
							<div class="group d-flex justify-content-between">
								<span>Trainer:</span>
								<span class="paint">{{ single.trainer.username }}</span>
							</div>
							<div class="button-wrapper">
								<CustomButton class="mx-auto">Schedule</CustomButton>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</template>

<script>
import { computed } from '@vue/runtime-core';
import { useStore } from 'vuex';
import TrainingList from '@/components/training/TrainingList.vue';
import CustomButton from '../../utility/CustomButton.vue';

export default {
    props: ["facility"],
    setup(props) {
        const store = useStore();
        const facilityTrainings = computed(() => {
            console.log(store);
            const trainings = store.getters["trainings/getAll"];
            return trainings.filter(training => training.facilityName === props.facility.name);
        });
        const facilityContentWithTrainers = computed(() => {
						console.log(props.facility.content);
            return props.facility.content.filter(single => single.trainer);
        });
				console.log(facilityContentWithTrainers.value);
        return { facilityTrainings, facilityContentWithTrainers };
    },
    components: { TrainingList, CustomButton }
}
</script>

<style lang="scss" scoped>
	.offer {
		background: $active-primary;
		color: $light-primary;
		font-size: 72px;
		padding: 30px 60px;
		width: 100vw;
		transform: translateX(-120px);
	}
	.content {
		.title {
			margin-bottom: 0;
			font-weight: bold;
			letter-spacing: 1px;
		}
		.lead {
			color: $dark-primary;
			font-size: 1.8rem;
			font-weight: bold;
			letter-spacing: 1px;
		}
	}
	.facility-offer {
		.offer-title {
			color: $active-primary;
			background: $dark-primary;
			padding: 40px 50px;
			font-weight: bold;
			letter-spacing: 1px;
			border-radius: 8px;
			margin-bottom: 0;
		}
		.list {
			background: $light-primary;
			border-radius: 8px;
		}
		.single {
			min-width: 300px;
			margin: 20px;
			border: 1px solid $light-secondary;
			box-shadow: 1px 3px 5px rgba(0,0,0,0.1);
			.header {
				padding: 30px 0px;
				background: $active-primary;
				font-size: 1.5rem;
				min-height: 50px;
				color: $light-primary;
				font-weight: bold;
				letter-spacing: 1px;
			}
			.body {
				padding: 20px 40px;
				font-size: 1.2rem;
				color: $dark-primary;
				min-height: 200px;
				.paint {
					color: $active-primary;
				}
			}
		}
	}
</style>