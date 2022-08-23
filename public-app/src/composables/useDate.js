import { ref } from "vue";

const useDate = () => {
	const formatedDate = ref(null);

	const formateDateFromResponse = (date) => {
		const parts = date.split('/');
		const day = parts[0];
		const month = parts[1];
		const year = parts[2];
		formatedDate.value = `${year}-${month}-${day}`;

		return formatedDate.value;
	};

	const formateDateForRequest = (date) => {
		const parts = date.split('-');
		const day = parts[2];
		const month = parts[1];
		const year = parts[0];
		formatedDate.value = `${day}/${month}/${year}`;

		return formatedDate.value;
	}

	return { formateDateFromResponse, formateDateForRequest }
}

export default useDate;