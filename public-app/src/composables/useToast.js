const useToast = (toast) => {

	const showMessage = (message, position) => {
		toast.success(message, { position });
		setTimeout(toast.clear, 3000);
	};

	return { showMessage }
}


export default useToast;