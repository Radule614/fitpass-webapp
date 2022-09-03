<template>
	<div class="rate">
		<h4 class="banner my-5">RATE US</h4>
		<div class="buttons">
			<div class="control-group">
				<input type="radio" v-model="grade" value="1">1
			</div>
			<div class="control-group">
				<input type="radio" v-model="grade" value="2">2
			</div>
			<div class="control-group">
				<input type="radio" v-model="grade" value="3">3
			</div>
			<div class="control-group">
				<input type="radio" v-model="grade" value="4">4
			</div>
			<div class="control-group">
				<input type="radio" v-model="grade" value="5">5
			</div>	
		</div>
	</div>
	<CustomButton @click="handleRate" ref="rateBtn">Rate</CustomButton>
</template>

<script>
import { inject, ref } from 'vue';
import CustomButton from '../utility/CustomButton.vue';
import Settings from '@/settings';
import { useStore } from 'vuex';
import { useRoute } from 'vue-router';
import useToast from '@/composables/useToast';

export default {
    setup() {
        const grade = ref(null);
				const store = useStore();
				const route = useRoute();
				const { showMessage } = useToast(inject('toast'));
				const rateBtn = ref(null);

				const handleRate = async () => {
					const data = {
						grade: grade.value,
						username: store.getters['auth/user'].username,
						facilityName: route.params.id
					};
					const res = await fetch(`${Settings.serverUrl}/api/grades/add`, {
						method: 'POST',
						headers: {'Content-Type': 'application/json', 'Data-Type': 'applicaiton/json'},
						body: JSON.stringify(data)
					});
					const resData = await res.json();
					showMessage('Thank you for raiting our facility.', 'top');
					calldown(); // da ne bi mogle da se spamuju poruke
				}

				function calldown() {
					rateBtn.value.$el.disabled = true;
					setTimeout(() => {
						rateBtn.value.$el.disabled = false;
					}, 3000);
				}

        return { grade, handleRate, rateBtn };
    },
    components: { CustomButton }
}
</script>

<style scoped lang="scss">
	.rate {
		margin: 30px 0;
		.control-group {
			display: inline-block;
			margin-right: 25px;
		}
		input {
			margin-right: 5px;
		}
		.buttons {
			font-size: 1.4rem;
		}
		.banner {
			background: $active-primary;
			color: $light-primary;
			font-size: 72px;
			padding: 30px 60px;
			width: 104vw;
			transform: translateX(-120px);
		}
	}
</style>