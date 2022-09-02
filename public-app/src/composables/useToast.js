const useToast = (toast) => {

	const showMessage = (message, position) => {
		toast.success(message, { position });
		setTimeout(toast.clear, 3000);
	};

	const showError = (message, position) => {
		toast.error(message, { position });
		setTimeout(toast.clear, 3000);
	}

	return { showMessage, showError }
}


export default useToast;