export default {
	namespaced: true,
	state() {
		return {
			prices: [
				{title: "Small", color: "#CD7F32", amount:5000, duration: 1, appointmentNumber: 20, text: "20 training sessions per month in some of sport facilities that we offer", bold: false},
				{title: "Medium", color: "#C0C0C0", amount:13500, duration: 3, appointmentNumber: 22, text: "22 training sessions per month in some of sport facilities that we offer", bold: false},
				{title: "Large", color: "#FFD700", amount:53000, duration: 12, appointmentNumber: 24, text: "24 training sessions per month in some of sport facilities that we offer", bold: true}
			]
		}
	},
	getters: {
		getPriceWithDiscount: (state) => (title, discount) => {
			const amount = state.prices.find(price => price.title === title).amount;
			return amount / 100 * (100 - discount);  
		},
		getPriceByAmount: (state) => (amount) => {
			return state.prices.filter(price => price.amount === amount)[0];
		} 
	}
}