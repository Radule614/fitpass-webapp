export default {
	namespaced: true,
	state() {
		return {
			userTypes: [
				{ type: 'Bronze', requiredPoints: 1500, discount: 1.5 },
				{ type: 'Silver', requiredPoints: 2500, discount: 2.5 },
				{ type: 'Gold', requiredPoints	: 4500, discount: 5 }
			]
		}
	}
}