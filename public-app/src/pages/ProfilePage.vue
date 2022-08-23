<template>
	<form>
		<div class="container-lg profile justify-content-end">
			<div class="row g-5 justify-content-center">
				<div class="col-sm-10 col-md-8 col-lg-6 col-xl-5">
					<form>
						<label>Username</label>
						<input type="text" name="username" v-model="username" disabled>
						<label>Password</label>
						<input type="text" name="password" v-model="password">
						<div class="error" v-if="errorState">{{ errors.password }}</div>
						<label>Firstname</label>
						<input type="text" name="firstName" v-model="firstName">
						<div class="error" v-if="errorState">{{ errors.firstName }}</div>
						<label>Lastname</label>
						<input type="text" name="lastName" v-model="lastName">
						<div class="error" v-if="errorState">{{ errors.lastName }}</div>
						<label>Date Of Birth</label>
						<input type="date" name="dateOfBirth" v-model="dateOfBirth">
						<label>Gender</label>
						<div class="gender-radios d-flex align-items-center">
							<label>Male</label>
							<input type="radio" name="gender" value="MALE" v-model="gender">
							<label>Female</label>
							<input type="radio" name="gender" value="FEMALE" v-model="gender">
						</div>
						<div class="row justify-content-center">
							<CustomButton class="save-button" @click="handleSave">Save</CustomButton>
						</div>
					</form>
				</div>
			</div>
	</div>
	</form>
</template>

<script>
import { ref } from 'vue';
import { useStore } from 'vuex'
import CustomButton from '../components/utility/CustomButton.vue'
import useDate from '@/composables/useDate'
import useValidation from '@/composables/useValidation'

export default {
    setup() {
        const store = useStore();
				const { formateDateFromResponse, formateDateForRequest } = useDate();
				const { validateProfileData } = useValidation();
        const user = store.state.auth.user;
				const username = ref(user.username);
				const password = ref(user.password);
				const firstName = ref(user.firstname);
				const lastName = ref(user.lastname);
				const dateOfBirth = ref(formateDateFromResponse(user.dateOfBirth));
				const gender = ref(user.gender);
				const errors = ref(null);
				const errorState = ref(false); // true if data is in error state

				const handleSave = async () => {
					console.log(dateOfBirth.value);
					errorState.value = false;
					errors.value = validateProfileData({
						password: password.value,
						firstName: firstName.value,
						lastName: lastName.value
					});
					if(errors.value.password || errors.value.firstName || errors.value.lastName) {
						errorState.value = true;
					} else {
						const updatedUser = await store.dispatch('users/updateUser', {
							username: username.value,
							password: password.value,
							firstname: firstName.value,
							lastname: lastName.value,
							dateOfBirth: dateOfBirth.value,
							gender: gender.value
						});
						store.commit('auth/setUserData', { user: updatedUser });
					}
				}

        return { username, password, firstName, lastName, dateOfBirth, gender, handleSave, errors, errorState };
    },
    components: { CustomButton }
}
</script>

<style scoped lang="scss">
	.profile {
		text-align: center;
		form {
			padding: 30px 40px;
			margin-top: 40px;
			border: 1px solid $light-primary;
			box-shadow: 3px 5px 7px rgba(50,50,50,0.1);
			border-radius: 10px;
			box-sizing: border-box;
			.error {
				text-align: left;
			}
			label {
				margin: 20px 15px 8px 0px;
				display: block;
				text-align: left;
			}
			input {
				display: block;
				width: 100%;
				margin-bottom: 5px;
			}
			.gender-radios {
				label {
					display: inline;
					margin-right: 6px;
					margin-bottom: 0;
				}
				input {
					display: inline;
					width: auto;
					margin-right: 20px;
				}
			}
			.save-button {
				width: 150px;
				margin-top: 35px;
			}
		}
	}
</style>