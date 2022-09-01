<template>
	<div class="single-training">
		<div class="img-description">
			<img :src="training.imgUrl">
			<p class="text-center lead">{{ training.description }}</p>
		</div>
		<div class="details">
			<h1 class="display-4 text-center">{{ training.name }}</h1>
			<slot name="details" :individuality="individuality" :type="type">

			</slot>
		</div>
	</div>
</template>

<script>
import { computed } from '@vue/runtime-core';
export default {
	props: ['training'],
	setup(props) {
		const individuality = computed(() => props.training.type.split('_')[0]);
		const type = computed(() => props.training.type.toLowerCase().split('_')[1].split('and').join(', '));

		return { individuality, type }
	}
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