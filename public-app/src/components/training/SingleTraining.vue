<template>
	<div class="single-training">
		<div class="img-description">
			<img :src="training.imgUrl">
			<p class="text-center lead">{{ training.description }}</p>
		</div>
		<div class="content">
			<div class="details">
				<h1 class="display-4 text-center mb-5">{{ training.name }}</h1>
				<slot name="details" :type="type">

				</slot>
			</div>
			<div class="button-wrapper my-5" v-if="loggedUserType === 'TRAINER' && training.content.type === 'PERSONAL'">
					<CustomButton class="mx-auto">Cancel Training</CustomButton>
			</div>
		</div>
	</div>
</template>

<script>
import { computed } from '@vue/runtime-core'
import { useStore } from 'vuex';
import CustomButton from '../utility/CustomButton.vue';

export default {
    props: ["training"],
    setup(props) {
        const type = computed(() => props.training.type.replace("and", ", "));
        const store = useStore();
        const loggedUserType = store.getters["auth/userType"];
        return { type, loggedUserType };
    },
    components: { CustomButton }
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
</style>