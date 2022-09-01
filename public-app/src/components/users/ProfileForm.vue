<template>
	<form>
		<div class="container-lg profile justify-content-end">
			<div class="row g-5 justify-content-center">
				<div class="col-8 col-xl-8">
					<form>
						<label>Username</label>
						<input type="text" name="username" v-model="username" disabled>
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
						<div class="row justify-content-center mt-5 buttons">
							<CustomButton class="save-button" @click="handleSave">Save</CustomButton>
							<CustomButton class="change-password-button" @click="showModal = true">Change Password</CustomButton>
						</div>
					</form>
				</div>
			</div>
		</div>
	</form>
	<modal-component :show="showModal" buttonText="change" :responsive="true" :width="400" @close="showModal = false" @confirm="handlePasswordChange()">
		<template #header>
			Change Password
		</template>
		<template #body>
			<form class="changePasswordForm">
				<div class="d-flex flex-column align-items-center">
					<div class="form-floating mb-3">
						<input type="password" class="form-control" placeholder="Old password" v-model="oldPassword">
						<label>Old password</label>
					</div>
					<div class="form-floating mb-3">
						<input type="password" class="form-control" placeholder="New password" v-model="newPassword">
						<label>New password</label>
					</div>
					<div v-if="passwordChangeError" class="error"> {{ passwordChangeError }}</div>
				</div>
			</form>
		</template>
	</modal-component>
</template>

<script>
import { inject, ref } from 'vue';
import { useStore } from 'vuex'
import CustomButton from '../utility/CustomButton.vue'
import useDate from '@/composables/useDate'
import useValidation from '@/composables/useValidation'
import ModalComponent from '@/components/ModalComponent.vue';
import useToast from '@/composables/useToast';

export default {
		components: { ModalComponent, CustomButton },
		props: ['user'],
    setup(props) {
        const store = useStore();
				const { formateDateFromResponse } = useDate();
				const { validateProfileData } = useValidation();
        const user = ref(props.user);
				const username = ref(user.value.username);
				const firstName = ref(user.value.firstname);
				const lastName = ref(user.value.lastname);
				const dateOfBirth = ref(formateDateFromResponse(user.value.dateOfBirth));
				const oldPassword = ref('');
				const newPassword = ref('');
				const gender = ref(user.value.gender);
				const errors = ref(null);
				const errorState = ref(false); // true if data is in error state
				const showModal = ref(false);
				const { showMessage } = useToast(inject('toast'));
				const passwordChangeError = ref(null);

				const handleSave = async () => {
					errorState.value = false;
					errors.value = validateProfileData({
						firstName: firstName.value,
						lastName: lastName.value
					});
					if(errors.value.firstName || errors.value.lastName) {
						errorState.value = true;
					} else {
						const updatedUser = await store.dispatch('users/updateUser', {
							username: user.value.username,
							firstname: firstName.value,
							lastname: lastName.value,
							dateOfBirth: dateOfBirth.value,
							gender: gender.value
						});
						store.commit('auth/setUserData', { user: updatedUser });
						console.log(store.getters['auth/user']);
						showMessage("Your profile has been updated successfully", 'top');
					}
				};

				const handlePasswordChange = async () => {
					passwordChangeError.value = null;
					if(!oldPassword.value || !newPassword.value) {
						passwordChangeError.value = "All input fields are required.";
						return;
					}
					const payload = {
						oldPassword: oldPassword.value,
						newPassword: newPassword.value,
						username: user.value.username
					};
					try {
						await store.dispatch('users/changeUserPassword', payload);
						showModal.value = false;
						console.log(store.getters['auth/user']);
						showMessage("Your password has been updated successfully.", 'top');
					} catch(ex) {
						passwordChangeError.value = ex.message;
					}
					oldPassword.value = '';
					newPassword.value = '';
				};

        return { username, firstName, lastName, dateOfBirth, gender, handleSave, errors, errorState, showModal, handlePasswordChange 
					,passwordChangeError, oldPassword, newPassword };
    }
}
</script>

<style scoped lang="scss">
	.profile {
		text-align: center;
		form {
			padding: 30px 40px;
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
					margin: 0;
					margin-right: 6px;
				}
				input {
					display: inline;
					width: auto;
					margin: 0;
					margin-right: 20px;
				}
			}
			.buttons {
				display: grid;
				grid-template-columns: 1fr 1fr;
				flex-wrap: wrap;
				gap: 20px;
				.save-button, .change-password-button {
					padding: 5px;
				}
			}
		}
	}
	.changePasswordForm {
		.form-floating {
			width: 100%;
		}
		input {
			width: 100%;
			font-size: 1.6rem;
		}
		button {
			width: 150px;
		}
	}
</style>