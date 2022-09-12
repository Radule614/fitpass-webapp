const useToast = (toast) => {

	const showMessage = (message, position) => {
		toast.success(message, { position, maxToasts: 1 });
		setTimeout(toast.clear, 3000);
	};

	const showError = (message, position) => {
		toast.error(message, { position, maxToasts: 1 });
		setTimeout(toast.clear, 3000);
	}

	const showInfo = (message, position) => {
		toast.info(message, { position, maxToasts: 1 });
		setTimeout(toast.clear, 3000);
	}

	return { showMessage, showError, showInfo }
}


export default useToast;