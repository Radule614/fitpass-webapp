<script>
import ModalComponent from '@/components/ModalComponent.vue';
import Settings from '@/settings';
import CustomButton from '../../utility/CustomButton.vue';
import settings from '@/settings';

  export default {
    props: {
        price: {
            title: String,
            color: String,
            amount: Number,
            duration: Number,
            text: String,
            bold: Boolean
        }
    },
		data() {
			return {
				showModal: false,
				promoCode: '',
				newAmount: null,
				showNewAmount: false,
				promoCodeError: null,
				priceRef: null
			}
		},
    computed: {
        period() {
            return this.price.duration === 1 ? "month" : "months";
        },
				amountWithDiscount() {
					const user = this.$store.getters['auth/user'];
					let discount = 0;
					if( user && user.type != null) {
						discount = user.type.discount;
					}
					return this.$store.getters['memberships/getPriceWithDiscount'](this.price.title, discount);
				},
				userType() {
					return this.$store.getters['auth/user'].type.type;
				},
				getTypeColor() {
					const type = this.$store.getters['auth/user'].type.type;
					switch(type) {
						case 'BRONZE': return '#CD7F32';
						case 'SILVER': return '#C0C0C0';
						case 'GOLD': return '#FFD700';
						default: return '#000';
					}
				}
    },
    created() {
        window.addEventListener("scroll", this.handleScroll);
    },
    mounted() {
        this.checkAnimations();
				console.log(this.$store.getters['auth/user']);
    },
    unmounted() {
        window.removeEventListener("scroll", this.handleScroll);
    },
    methods: {
        handleScroll(event) {
            this.checkAnimations();
        },
        checkAnimations() {
            const element = this.$el;
            let windowHeight = window.innerHeight;
            let distanceFromTop = element.getBoundingClientRect().top;
            if (distanceFromTop - windowHeight + element.offsetHeight <= 0) {
                element.classList.add("appear");
            }
        },
				async handlePayment(price) {
					// napravi metodu da nadje expirationDate
					const paymentDate = new Date();
					const expirationDate = this.getMembershipExpirationDate(paymentDate, price.duration);
					const username = this.$store.getters['auth/user'].username;
					const newMembership = {
						type: price.duration == 12 ? 'YEARLY' : 'MONTHLY',
						paymentDate: paymentDate.toLocaleDateString('en-GB', { year: "numeric", month: "2-digit", day: "2-digit"}),
						expirationDate: expirationDate.toLocaleDateString('en-GB', { year: "numeric", month: "2-digit", day: "2-digit"}),
						cost: parseInt(price.amount),
						customerUsername: username,
						active: true,
						appointmentNumber: price.appointmentNumber * price.duration,
						usedAppointments: 0
					};
					// ako je postojala prethodna clanarina ona prestaje da vazi, izbrisi je
					await this.removeMembership();
					// posalji request da dodas novu clanarinu
					await this.addMembership(newMembership);

					this.showModal = false;

					this.showSuccessMessage();
					
					console.log(this.$store.getters['auth/user']);
				},
				getMembershipExpirationDate(paymentDate, duration) {
					let expirationYear = paymentDate.getFullYear();
					let expirationMonth = paymentDate.getMonth() + duration;
					if(expirationMonth > 12) {
						expirationMonth = expirationMonth % 12;
						expirationYear += 1;
					}
					const expirationD = paymentDate.getDate();
					const expirationDate = new Date(expirationYear, expirationMonth, expirationD, 
						paymentDate.getHours(), paymentDate.getMinutes(), paymentDate.getSeconds(), paymentDate.getMilliseconds());

					return expirationDate;
				},
				async removeMembership() {
					const user = this.$store.getters['auth/user'];
					if(user.membership) {
						const res = await fetch(`${Settings.serverUrl}/api/memberships/remove/${user.username}/${user.membership.id}`, {
							method: 'DELETE'
						});
						const message = await res.json();
						console.log(message);
					}
				},
				async addMembership(newMembership) {
					const res = await fetch(`${Settings.serverUrl}/api/memberships/create`, {
						method: 'POST',
						headers: { 'Content-Type': 'application/json', 'Data-Type': 'application/json' },
						body: JSON.stringify(newMembership)
					});
					const membership = await res.json();
					this.updateUser(membership);
				},
				updateUser(membership) {
					const user = this.$store.getters['auth/user'];
					const newUser = {...user};
					newUser.membership = membership;
					this.$store.commit('auth/setUserData', { user: newUser } );
				},
				showSuccessMessage() {
					this.$toast.success(`You have successfully bought ${this.price.title.toLowerCase()} plan.`, {
            position: "top"
          });
          setTimeout(this.$toast.clear, 2000);
				},
				async validatePromoCode() {
					this.promoCodeError = null;
					this.newAmount = null;
					this.showNewAmount = false;
					if(this.promoCode.trim() === '') return;
					const res = await fetch(`${settings.serverUrl}/api/coupon/validate`, {
						method: 'POST',
						headers: { 'Content-Type': 'application/json', 'Data-Type': 'application/json', 'Authorization': 'Bearer ' + this.$store.getters['auth/token']},
						body: JSON.stringify(this.promoCode)
					});
					const valid = await res.json();
					if(!res.ok) throw new Error('Error validating promo code');
					if(valid) {
						this.newAmount = Math.floor(this.price.amount / 100 * 95);
						this.$refs.priceRef.style.textDecoration = 'line-through';
						this.showNewAmount = true;
					} else {
						this.promoCodeError = 'Invalid promo code.';
						this.$refs.priceRef.style.textDecoration = 'none';
					}
				}
    },
    components: { ModalComponent, CustomButton }
}
</script>

<template>
  <div class="item nohighlight" v-bind:class="{ 'bold-item': price.bold }">
    <div class="header" v-bind:style="{ backgroundColor: price.color }">{{price.title}}</div>
		<div class="duration">{{price.duration}} <span class="period">{{period}}</span></div>
    <div class="text">{{price.text}}</div>
    <div class="price" :class="{ 'invalid': price.amount != amountWithDiscount}">{{price.amount}} <span class="unit">RSD</span></div>
    <div class="price" v-if="price.amount != amountWithDiscount">{{amountWithDiscount}}<span class="unit">RSD</span> <br/> <span class="info">based on <span :style="{ color: getTypeColor }">{{userType}}</span> type</span></div>
    <custom-button v-bind:class="{ 'inverse': price.bold }" @click="showModal = !showModal" v-if="this.$store.getters['auth/user'] && this.$store.getters['auth/user'].userType === 'CUSTOMER'">Buy</custom-button>
		<teleport to="#app">
			<ModalComponent buttonText="Buy" :show="showModal" :width="600" @close="showModal = false" @confirm="handlePayment(price)">
				<template #header>
					{{price.title}} Membership	<br/>
					{{ price.duration }} {{ period }} 
				</template>
				<template #body>
					{{price.text}} <br/><br/>
					Price: <span ref="priceRef">{{amountWithDiscount}}</span> rsd <br/><br/>
					<div class="d-flex align-items-center mb-3">
						<input class="me-3" type="text" v-model="promoCode" placeholder="Promo code(optional)">
						<span v-if="showNewAmount" class="text-success">
							New price: {{ newAmount }}
						</span>
						<span class="error">
							{{ promoCodeError }}
						</span>
					</div>
					<CustomButton @click="validatePromoCode">validate</CustomButton>
				</template>
			</ModalComponent>
		</teleport>
  </div>
</template>

<style scoped lang="scss">
  .item{
    position: relative;
    width: 250px;
    min-height: 250px;
    max-width: 500px;
    background-color:$light-primary;
    box-shadow: 0px 0px 3px rgba(0, 0, 0, 0.3);
    border-radius: 12px;
    margin-left: 50px;
    transition: box-shadow 0.1s;
    text-align: center;
    opacity: 0;
    &.appear{
      animation: magnify 0.4s ease-out forwards;
      &:nth-child(2){
        animation-delay: 0.1s;
      }
      &:nth-child(3){
      animation-delay: 0.2s;
      }
    }
    &:first-child {
        margin-left: 0px;
    }
    &:hover{
      box-shadow: 0px 0px 12px rgba(240, 84, 84, 0.4);
    }
    .header{
      border-radius: 12px 12px 0px 0px;
      line-height: 45px;
      text-transform: uppercase;
      color: $dark-primary;
      font-size: 22px;
    }
    .text{
      font-size: 19px;
      padding: 16px 8px 5px;
    }
    .price{
      font-size: 34px;
      font-weight: bold;
      .unit{
        font-size: 22px;
      }
			.info {
				font-size: 14px;
				color: $dark-secondary;
			}
    }
    .timespan{
      line-height: 40px;
      font-size: 23px;
      font-style: italic;
      text-transform: lowercase;
      opacity: 0.7;
    }
    button{
      margin: 60px auto 30px auto;
      font-size: 18px;
    }
		.duration {
			font-size: 48px;
			font-weight: bold;
			.period {
				font-size: 14px;
				display: block;
				color: $dark-secondary;
				font-weight: normal;
			}
		}
  }
	.item .price.invalid {
		text-decoration: line-through;
		font-size: 20px;
		color: #aaa;
	}
	input {
		margin-bottom: 0 !important;
	}
</style>