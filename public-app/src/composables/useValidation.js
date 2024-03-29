import { ref } from "vue";

const capitalPattern = /^[A-Z]{1}.*$/;		
const digitPattern = /[0-9]+/;

const useValidation = () => {
	const valid = ref(false);
	const usernameError = ref(null);
	const passwordError = ref(null);
	const firstNameError = ref(null);
	const lastNameError = ref(null);
	
	const validateProfileData = (user) => {
		// Clear errors
		firstNameError.value = null;
		lastNameError.value = null;

		const firstName = user.firstName;
		const lastName = user.lastName;

		firstNameValidation(firstName);
		lastNameValidation(lastName);
		
		const errors = {
			firstName: firstNameError.value,
			lastName: lastNameError.value
		};

		return errors;
	}

	const lengthValidation = (text) => {
		return text.length > 5;
	}

	const digitsValidation = (text) => {
		return digitPattern.test(text);
	}

	const capitalValidation = (text) => {
		return capitalPattern.test(text);
	}

	const passwordValidation = (password) => {
		if(!lengthValidation(password) || !digitsValidation(password)) {
			passwordError.value = 'Password must be at least 6 characters long and must contain at least 1 digit';
			return false;
		}
		return true;
	}

	const firstNameValidation = (firstName) => {
		if(!capitalValidation(firstName)) {
			firstNameError.value = 'Firstname must start with capital letter';
		}
	}

	const lastNameValidation = (lastName) => {
		if(!capitalValidation(lastName)) {
			lastNameError.value = 'Lastname must start with capital letter';
		}
	}

	return { validateProfileData }
}

export default useValidation;