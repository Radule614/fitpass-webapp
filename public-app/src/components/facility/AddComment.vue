<template>
	<form>
		<h5>Comment</h5>
		<textarea v-model="content"></textarea>
		<div class="error">{{ error }}</div>
		<CustomButton @click="handleSubmit">Send</CustomButton>
	</form>
</template>

<script>
import { inject, ref } from "vue";
import { useStore } from "vuex";
import CustomButton from "../utility/CustomButton.vue";

export default {
    components: { CustomButton },
		props: ['facility_id'],
		setup(props) {
			const store = useStore();
			const error = ref(null);
			const content = ref('');
			const toast = inject('toast');

			const showApprovalMessage = () => {
				toast.success("Your comment has been successfully added and it is waiting to be approved", {
					position: "top"
				});
				setTimeout(toast.clear, 3000);
			}

			const handleSubmit = () => {
				if(content.value.trim() === '') {
					error.value = 'Comment cannot be empty.';
					return;
				}
				const newComment = {
					facility_id: props.facility_id,
					user_id: store.getters['auth/user'].username,
					content: content.value,
					postedOn: new Date().toLocaleDateString('en-GB', { year: 'numeric', month: '2-digit', day: '2-digit' }),
					approved: false
				}
				store.dispatch('comments/addComment', newComment);
				content.value = '';
				showApprovalMessage();
			}

			return { handleSubmit, content, error }
		}
}
</script>

<style scoped lang="scss">
	textarea {
		width: 100%;
		border-radius: 5px;
		height: 150px;
		outline: none;
		border-color: $light-secondary;
		width: 600px;
		display: block;
	}

	button {
		margin: 15px 0;
	}

	form {
		margin-top: 40px;
	}
</style>