import { ref } from "vue";

const useCustomerUtilities = (customer) => {
	const membershipError = ref(null);
	const terminsError = ref(null);

	const isMembershipActive = () => {
		if(!customer.membership || !customer.membership.active) {
			membershipError.value = "You don't have active membership.";
			return false;
		}

		return true;
	};

	const haveEnoughTermins = () => {
		if(!customer.membership || !customer.membership.appointmentNumber) {
			terminsError.value = "You don't have enough termins.";
			return false;
		}

		return true;
	};

	const checkinValidation = () => {
		resetErrors();

		if(!isMembershipActive()) return false;

		if(!haveEnoughTermins()) return false;

		return true;
	}

	const resetErrors = () => {
		membershipError.value = null;
		terminsError.value = null;
	}

	return { membershipError, terminsError, checkinValidation }
}

export default useCustomerUtilities;