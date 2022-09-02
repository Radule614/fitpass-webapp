<template>
	<form class="mt-4">
		<div class="container-fluid px-0">
			<div class="row justify-content-between">
				<div class="col-md-6 col-lg-3">
					<div class="form-group mb-3">
						<label class="form-label">Name</label>
						<input class="form-control" type="text" placeholder="name" v-model="name" required>
					</div>
					<div class="form-group mb-3">
						<label class="form-label">Duration in minutes</label>
						<input type="number" class="form-control" placeholder="Duration in minutes" v-model="duration" required>
					</div>
				</div>
				<div class="col-md-6 col-lg-5">		
					<div class="row">
						<div class="col-md-6">
							<div class="form-group">
								<label class="form-label">Date</label>
								<input type="date" name="date" class="form-control" v-model="date" required>
							</div>
						</div>
						<div class="col-md-6">
							<div class="form-group">
								<label class="form-label">Time</label>
								<input type="time" name="time" class="form-control" v-model="time" required>
							</div>
						</div>
					</div>
					
					<div class="mb-3">
						<label class="form-label">Picture</label>
						<input type="file" class="form-control" @change="handleImgChange" ref="fileRef" required>
						<span class="error form-text" v-if="imgTypeError">{{ imgTypeError }}</span>
					</div>
				</div>
				<div class="col-md-6 col-lg-3">
					<label class="form-label">Description</label>
					<textarea class="form-control" v-model="description"></textarea>
				</div>
			</div>
			<div class="row justify-content-around">		
				<div class="col-4">
					<label class="form-label">Type</label>
					<select class="form-select" v-model="type" required>
						<option value="aerobic">Aerobic</option>
						<option value="strength">Strength</option>
						<option value="balance&stability">Balance & Stability</option>
						<option value="coordination&agility">Coordination & Agility</option>
						<option value="flexibility&mobility">Flexibility & Mobility</option>
					</select>
				</div>
				<div class="col-4">
					<label class="form-label">Content</label>
					<select name="content" class="form-select" v-model="selectedContent" required>
						<option v-for="item in content" :key="item.id" :value="item">{{ item.name }} - {{ item.type }}</option>
					</select>
				</div>
			</div>
			<div class="row mt-4">
				<div class="col-2">
					<CustomButton @click="handleSubmit">Add Training</CustomButton>
					<div class="error" v-if="formError">{{ formError }}</div>
				</div>
			</div>
		</div>
	</form>
</template>

<script>
import { onMounted, ref } from 'vue';
import CustomButton from '../utility/CustomButton.vue';
import { useStore } from 'vuex';
import Settings from '@/settings';
import useDate from '@/composables/useDate';

export default {
    setup() {
        const name = ref("");
        const duration = ref("");
        const description = ref("");
        const type = ref("");
				const date = ref("");
				const time = ref("");
        const allowedImgTypes = ["image/jpeg", "image/png"];
        const imgTypeError = ref(null);
				const formError = ref(null);
				let formData = new FormData();
				const store = useStore();
				const fileRef = ref(null);
				const content = ref(null);
				const selectedContent = ref('');
				const trainer = store.getters['auth/user'];
				const { formateDateForRequest } = useDate();

				const fetchContent = async () => {
					const res = await fetch(`${Settings.serverUrl}/api/content/trainer/group/${trainer.username}`, {
						method: 'GET',
						headers: { 'Authorization': 'Bearer ' + store.state.auth.token}
					});
					const data = await res.json();
					if(res.ok) {
						content.value = data;
					}
				};

				onMounted(() => {
					fetchContent();
				})

        const handleImgChange = (e) => {
						formData = new FormData();
            imgTypeError.value = null;
            const img = e.target.files[0];
            if (!allowedImgTypes.includes(img.type)) {
                imgTypeError.value = "Image must be png or jpg";
                return;
            }
            formData.append("image", img);
        };
        const handleSubmit = async (e) => {
						e.preventDefault();
						formError.value = null;
						validateInput();
						if(!formError.value) {
							formData.append("name", name.value);
							formData.append("duration", duration.value);
							formData.append("type", type.value);
							formData.append("content", selectedContent.value.id);
							formData.append("facilityName", selectedContent.value.facility_id);
							formData.append("date", formateDateForRequest(date.value));
							formData.append("time", time.value);
							formData.append("description", description.value);
							formData.append("trainerUsername", store.getters['auth/username']);
							const newTraining = await store.dispatch("trainings/addTraining", { formData });
							resetForm();
						}	
        };
				const validateInput = () => {
					if(name.value === "" || duration.value === "" || type.value === "" || !formData.get("image")) {
						formError.value = "Please fill all fields";
					}
				};
				const resetForm = () => {
					name.value = '';
					duration.value = '';
					type.value = '';
					description.value = '';
					fileRef.value.value = '';
					time.value = '';
					date.value = '';
					selectedContent.value = '';
				}
        return { name, duration, description, type, handleImgChange, imgTypeError, handleSubmit, formError, fileRef, date, time, 
					content, 	selectedContent };
    },
    components: { CustomButton }
}
</script>

<style lang="scss" scoped>
	input[type='text'], 
	input[type='number'] {
		min-width: 230px;
		max-width: 230px;
	}
	textarea {
		width: 100%;
		height: 73%;
	}
	input[type='time'] {
		width: 200px;
	}
	input[type='file'] {
		width: 350px;
	}
</style>