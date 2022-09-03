<template>
	<div class="container-lg my-3">
		<div v-if="loggedUserType == 'CUSTOMER'" class="customer-layout">
			<div class="form">
				<ProfileForm :user="user" />
			</div>
			<div class="membership-type">
				<div class="membership">
					<h3 class="display-5 text-center mb-4">Membership</h3>
					<div v-if="user.membership" style="width: 100%;">
						<div class="card text-center">
							<div class="card-header fw-bold membership-header" style="font-size: 1.3rem;">
								{{ price.title.toUpperCase() }}
							</div>
							<div class="card-body">
								<ul class="list-group">
									<li class="list-group-item">Active:<span class="paint">{{user.membership.active ? 'Yes' : 'No' }}</span></li>
									<li class="list-group-item">Appointment Number:<span class="paint">{{user.membership.appointmentNumber}}</span></li>
								</ul>
							</div>
							<div class="card-footer text-muted">
								Expiration Date: {{ user.membership.expirationDate }}
							</div>
						</div>
					</div>
					<div v-else>
						You don't have membership yet.
					</div>
				</div>
				<div class="type mt-4" style="width: 100%;">
					<h3 class="display-5 text-center mb-4">Type</h3>
					<div v-if="user.type" style="width: 100%;">
						<div class="card text-center" style="width: 100%;">
							<div class="card-header fw-bold" :style="{background: price.color}">
								{{ user.type.type }}
							</div>
							<div class="card-body">
								<ul class="list-group">
									<li class="list-group-item">Required Points:<span class="paint">{{user.type.requiredPoints}}</span></li>
									<li class="list-group-item">Your Points:<span class="paint">{{user.points}}</span></li>
									<li class="list-group-item">Discount:<span class="paint">{{user.type.discount}}%</span></li>
								</ul>
							</div>
						</div>
					</div>
					<div v-else>
						You have <span class="paint">{{ user.points }}</span> points which is not enough for any type, collect more points to get a type and discount on every buy. You gain points when your membership expire.
					</div>
				</div>
			</div>
		</div>
		<div v-else>
			<ProfileForm :user="user" />
		</div>
	</div>
</template>

<script>
import ProfileForm from '@/components/users/ProfileForm.vue';
import { useStore } from 'vuex';
export default {
    components: { ProfileForm },
		setup() {
			const store = useStore();
			const loggedUserType = store.getters['auth/userType'];
			const user = store.getters['auth/user'];
			let price;
			if(user.userType === 'CUSTOMER') {
				price = store.getters['memberships/getPriceByAmount'](user.membership.cost);
			}

			return { loggedUserType, user, price }
		}
}
</script>

<style scoped lang="scss">
	.customer-layout {
		display: grid;
		grid-template-columns: 1fr 1fr;
		justify-content: center;	
		.form {
			width: 100%;
		}
		.membership {
			width: 100%;
			.membership-header {
				background: $active-primary;
				color: $light-primary;
			}
			.card-header {
				font-weight: bold;
				letter-spacing: 1px;
			}
		}
		.membership-type {
			display: flex;
			flex-direction: column;
			align-items: center;
			.card-body {
				background: $light-primary;
			}
			.list-group-item {
				display: flex;
				justify-content: space-between;
				.paint {
					margin-left: 15px;
				}
			}
		}
		.type {
			.card-header {
				font-size: 1.3rem;
			}
		}
	}
	.paint {
		color: $active-primary;
		font-weight: bold;
	}
</style>