<template>
  <form class="w-full min-h-[60px]" type="button" aria-haspopup="dialog" data-state="closed" @submit.prevent="submitMessage">
    <div class="relative flex h-full max-w-full flex-1 flex-col">
      <div class="absolute bottom-full left-0 right-0 z-20"></div>
      <div class="relative h-0">
        <div class="absolute bottom-3 space-y-2 z-20"></div>
      </div>
      <div class="group relative flex w-full items-center">
        <div
          id="composer-background"
          class="flex w-full flex-row items-center transition-colors contain-inline-size cursor-text rounded-3xl px-2.5 py-1 bg-[#f4f4f4] dark:bg-token-main-surface-secondary"
          style="background-color: #5d5d5db0;"
        >
          <div class="flex min-h-[22px] flex-1 items-center px-2">
            <div class="max-w-full flex-1">
              <div class="_prosemirror-parent_15ceg_1 text-token-text-primary max-h-[25dvh] max-h-52 overflow-auto default-browser">

                <textarea
                  class="block h-10 w-full resize-none border-0 bg-transparent px-0 py-2 text-token-text-primary placeholder:text-token-text-secondary"
                  placeholder="给“DSLbot”发送消息"
                  style="display: none;"
                ></textarea>
                <div
                  contenteditable="true"
                  id="prompt-textarea"
                  class="ProseMirror"
                  @focus="handleFocus"
                  @blur="handleBlur"
                  @input="handleInput"
                >
                </div>
              </div>
            </div>
          </div>
          <!-- 自定义发送按钮 -->
          <div class="min-w-8">
            <span data-state="closed">
              <button
                :disabled="isEmpty"
                @click="submitMessage"
                aria-label="发送提示"
                data-testid="send-button"
                class="flex h-8 w-8 items-center justify-center rounded-full transition-colors hover:opacity-70 focus-visible:outline-none focus-visible:outline-black disabled:text-[#f4f4f4] disabled:hover:opacity-100 dark:focus-visible:outline-white disabled:dark:bg-token-text-quaternary dark:disabled:text-token-main-surface-secondary bg-black text-white dark:bg-white dark:text-black disabled:bg-[#D7D7D7]"
              >
                <svg width="32" height="32" viewBox="0 0 32 32" fill="none" xmlns="http://www.w3.org/2000/svg" class="icon-2xl">
                  <path fill-rule="evenodd" clip-rule="evenodd" d="M15.1918 8.90615C15.6381 8.45983 16.3618 8.45983 16.8081 8.90615L21.9509 14.049C22.3972 14.4953 22.3972 15.2189 21.9509 15.6652C21.5046 16.1116 20.781 16.1116 20.3347 15.6652L17.1428 12.4734V22.2857C17.1428 22.9169 16.6311 23.4286 15.9999 23.4286C15.3688 23.4286 14.8571 22.9169 14.8571 22.2857V12.4734L11.6652 15.6652C11.2189 16.1116 10.4953 16.1116 10.049 15.6652C9.60265 15.2189 9.60265 14.4953 10.049 14.049L15.1918 8.90615Z" fill="currentColor"></path>
                </svg>
              </button>
            </span>
          </div>
        </div>
      </div>
    </div>
  </form>
</template>

<script>
export default {
  props: {
    placeholderText: {
      type: String,
      default: '给“DSLbot”发送消息',
    },
  },
  data() {
    return {
      localMessage: this.message || "",
      isEmpty: true,
    };
  },
  methods: {
    handleFocus(event) {
      if (this.isEmpty) {
        event.target.innerHTML = '<p><br></p>';
        this.isEmpty = false;
      }
    },
    handleBlur(event) {
      if (event.target.textContent.trim() === '') {
        this.isEmpty = true;
        event.target.innerHTML = `<p class="placeholder">${this.placeholderText}</p>`;
      }
    },
    handleInput(event) {
      if (this.isEmpty && event.target.textContent.trim() !== '') {
        this.isEmpty = false;
        const placeholder = event.target.querySelector('.placeholder');
        if (placeholder) {
          placeholder.remove();
        }
      } else if (event.target.textContent.trim() === '') {
        this.isEmpty = true;
      }
    },
    submitMessage() {
      const textArea = document.getElementById('prompt-textarea');
      this.localMessage = textArea.textContent.trim();
      if (this.localMessage) {
        this.$emit("sendMessage", this.localMessage);
        this.localMessage = ""; // 清空输入框
        textArea.innerHTML = `<p class="placeholder">${this.placeholderText}</p>`;
        this.isEmpty = true;
      }
    },
  },
  mounted() {
    this.$nextTick(() => {
      const textarea = document.getElementById('prompt-textarea');
      if (textarea && textarea.textContent.trim() === '') {
        textarea.innerHTML = `<p class="placeholder">${this.placeholderText}</p>`;
      }
    });
  }
};
</script>

<style scoped>
.chat-input {
  max-width: 800px;
  margin: 0 auto;
}


.dark\:bg-token-main-surface-secondary:is(.dark *) {
    background-color: var(--main-surface-secondary);
}
.ProseMirror{
  outline: none; /* 禁用聚焦时的轮廓 */
  color: #8b8b8b;
}
#prompt-textarea {
  min-height: 22px; /* 或根据需求设置 */
}




</style>
