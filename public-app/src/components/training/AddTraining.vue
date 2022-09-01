<template>
	<form class="mt-4">
		<div class="container-fluid px-0">
			<div class="row">
				<div class="col-md-6 col-lg-4">
					<div class="form-floating mb-3">
						<input class="form-control" type="text" placeholder="name" v-model="name" required>
						<label>Name</label>
					</div>
					<div class="form-floating mb-3">
						<input type="number" class="form-control" placeholder="Duration in minutes" v-model="duration" required>
						<label>Duration in minutes</label>
					</div>
				</div>
				<div class="col-md-6 col-lg-4">		
					<div class="form-check">
						<input type="radio" class="form-check-input" name="individuality" value="personal" v-model="individuality">
						<label class="form-check-label">Personal</label>
					</div>
					<div class="form-check">
						<input type="radio" class="form-check-input" name="individuality" value="group" v-model="individuality">
						<label class="form-check-label">Group</label>
					</div>
					<div class="mt-3">
						<label class="form-label">Picture</label>
						<input type="file" class="form-control" @change="handleImgChange" ref="fileRef" required>
						<span class="error form-text" v-if="imgTypeError">{{ imgTypeError }}</span>
					</div>
				</div>
				<div class="col-md-6 col-lg-4">
					<label class="form-label">Description</label>
					<textarea class="form-control" v-model="description"></textarea>
				</div>
			</div>
			<div class="row">		
				<div class="col-12">
					<label class="form-label">Type</label>
					<select class="form-select" v-model="type" required>
						<option value="aerobic">Aerobic</option>
						<option value="strength">Strength</option>
						<option value="balance&stability">Balance & Stability</option>
						<option value="coordination&agility">Coordination & Agility</option>
						<option value="flexibility&mobility">Flexibility & Mobility</option>
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
import { ref } from 'vue';
import CustomButton from '../utility/CustomButton.vue';
import { useStore } from 'vuex';

export default {
    setup() {
        const name = ref("");
        const duration = ref("");
        const description = ref("");
        const type = ref("");
        const individuality = ref("personal");
        const allowedImgTypes = ["image/jpeg", "image/png"];
        const imgTypeError = ref(null);
        const formData = new FormData();
				const formError = ref(null);
				const store = useStore();
				const fileRef = ref(null);

        const handleImgChange = (e) => {
            imgTypeError.value = null;
            const img = e.target.files[0];
            if (!allowedImgTypes.includes(img.type)) {
                imgTypeError.value = "Image must be png or jpg";
                return;
            }
            formData.append("image", img);
        };
        const handleSubmit = (e) => {
						e.preventDefault();
						formError.value = null;
						validateInput();
						if(!formError.value) {
							formData.append("name", name.value);
							formData.append("duration", duration.value);
							formData.append("type", type.value);
							formData.append("individuality", individuality.value);
							formData.append("description", description.value);
							formData.append("trainerUsername", store.getters['auth/username']);
							resetForm();
							store.dispatch("trainings/addTraining", { formData });
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
					individuality.value = 'personal';
					description.value = '';
					fileRef.value.value = '';
				}
        return { name, duration, description, type, individuality, handleImgChange, imgTypeError, handleSubmit, formError, fileRef };
    },
    components: { CustomButton }
}
</script>

<style lang="scss" scoped>
	input[type='text'] {
		min-width: 300px;
		max-width: 300px;
	}
	textarea {
		width: 100%;
	}
</style>