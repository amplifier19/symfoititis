<script setup lang="ts">
import type { Modal } from '../interfaces/Modal'

const props = defineProps<{ content: Modal }>()
</script>

<template>
  <div
    class="pf-v5-c-modal-box"
    role="dialog"
    aria-modal="true"
    aria-labelledby="icon-title"
    aria-describedby="icon-description"
  >
    <div class="pf-v5-c-modal-box__close">
      <button
        @click="$emit('hide')"
        class="pf-v5-c-button pf-m-plain"
        type="button"
        aria-label="Close"
      >
        <i class="fa fa-times" aria-hidden="true"></i>
      </button>
    </div>
    <header class="pf-v5-c-modal-box__header">
      <h1 class="pf-v5-c-modal-box__title pf-m-icon" id="icon-title">
        <span class="pf-v5-c-modal-box__title-icon">
          <i :class="props.content.icon" aria-hidden="true"></i>
        </span>
        <span class="pf-v5-c-modal-box__title-text">{{ props.content.title }}</span>
      </h1>
    </header>
    <div class="pf-v5-c-modal-box__body" id="icon-description">
      <!--  -->
      <form @submit.prevent="$emit(content.event, $event)" class="pf-v5-c-form">
        <div v-for="input in props.content.inputs" class="pf-v5-c-form__group">
          <div class="pf-v5-c-form__group-label">
            <label class="pf-v5-c-form__label" :for="input.name">
              <span class="pf-v5-c-form__label-text">{{ input.label }}</span
              >&nbsp;<span
                v-if="input?.required"
                class="pf-v5-c-form__label-required"
                aria-hidden="true"
                >&#42;</span
              ></label
            >
          </div>
          <div class="pf-v5-c-form__group-control">
            <span class="pf-v5-c-form-control pf-m-required">
              <input
                type="text"
                :id="input.name"
                :name="input.name"
                :value="input.value"
                :placeholder="input?.placeholder"
                :readonly="input?.readonly"
                :required="input?.required"
              />
            </span>
          </div>
        </div>
        <!--  -->
        <div v-if="props.content?.selects && props.content?.selects.length > 0">
          <div v-for="select in props.content.selects" class="pf-v5-c-form__group">
            <div class="pf-v5-c-form__group-label">
              <label class="pf-v5-c-form__label" :for="select.name">
                <span class="pf-v5-c-form__label-text">{{ select.label }}</span
                >&nbsp;<span
                  v-if="select?.required"
                  class="pf-v5-c-form__label-required"
                  aria-hidden="true"
                  >&#42;</span
                ></label
              >
            </div>
            <div class="pf-v5-c-form__group-control">
              <span class="pf-v5-c-form-control pf-m-required">
                <select :name="select.name" :id="select.name" :required="select?.required">
                  <option
                    v-for="option in select.options"
                    :value="
                      Object.values(option)[
                        Number(Object.keys(option).indexOf(select?.valueKey!))
                      ] || option
                    "
                    :selected="
                      Object.values(option)[
                        Number(Object.keys(option).indexOf(select?.valueKey!))
                      ] == select?.selection || option == select?.selection
                    "
                  >
                    {{
                      Object.values(option)[
                        Number(Object.keys(option).indexOf(select?.optionKey!))
                      ] || option
                    }}
                  </option>
                </select>
              </span>
            </div>
          </div>
        </div>

        <!--  -->
        <footer class="pf-v5-c-modal-box__footer">
          <div class="pf-v5-c-overflow-menu__group pf-m-button-group">
            <div v-for="btn in props.content.buttons" class="pf-v5-c-overflow-menu__item">
              <button :type="btn?.type" :class="btn.classes">
                {{ btn.text }}
              </button>
            </div>
            <div class="pf-v5-c-overflow-menu__item">
              <button @click="$emit('hide')" type="button" class="pf-v5-c-button pf-m-secondary">
                Cancel
              </button>
            </div>
          </div>
        </footer>
      </form>
      <!--  -->
    </div>
  </div>
</template>

<style scoped>
.pf-v5-c-modal-box {
  width: 30%;
  position: absolute;
  top: 5%;
  right: 35%;
  z-index: 1;
}
.pf-v5-c-modal-box__footer {
  display: flex;
  justify-content: center;
}
.pf-m-button-group {
  width: 100%;
  display: flex;
  flex-direction: row;
  justify-content: center;
  gap: 8%;
}
</style>
../interfaces/Modal
