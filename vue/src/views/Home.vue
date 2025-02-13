<template class="dark bg-gray">
  <div class="composer-parent flex h-full flex-col focus-visible:outline-0 bg-gray-800" tabindex="0">
    <!-- 消息显示区域 -->
    <div class="flex-1 overflow-hidden">
      <div class="h-full">
        <div class="h-full relative">
          <div
            style="height: 100%; overflow-y: auto; width: 100%;"
            class="flex bg-gray-800 text-gray-100"
            ref="messageContainer"
          >
            <div class="m-auto text-base py-[18px] px-3 w-full max-w-[500px]">
              <div class="flex flex-col gap-6 text-base">
                <!-- 消息循环渲染 -->
                <div v-for="(message, index) in messages" :key="index">
                  <!-- 横线分割 -->
                  <hr v-if="index > 0" class="border-t border-gray-700 my-2" />
                  <div
                    class="flex items-start gap-4"
                    :class="{
                      'flex-row-reverse': message.sender === 'user', // 用户消息从右边显示
                      'flex-row': message.sender === 'bot' // 机器人消息从左边显示
                    }"
                  >
                    <!-- 头像 -->
                    <div class="flex-shrink-0">
                      <svg
                        v-if="message.sender === 'bot'"
                        xmlns="http://www.w3.org/2000/svg"
                        class="h-8 w-8 text-gray-500"
                        viewBox="0 0 24 24"
                        fill="none"
                        stroke="currentColor"
                        stroke-width="2"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      >
                        <circle cx="12" cy="12" r="10" />
                        <path d="M8 12h.01M16 12h.01M9 16c.864-1.148 2.136-1.148 3 0" />
                      </svg>
                      <svg
                        v-else
                        xmlns="http://www.w3.org/2000/svg"
                        class="h-8 w-8 text-grey-300"
                        viewBox="0 0 24 24"
                        fill="none"
                        stroke="currentColor"
                        stroke-width="2"
                        stroke-linecap="round"
                        stroke-linejoin="round"
                      >
                        <circle cx="12" cy="12" r="10" />
                        <path d="M15 12h.01M9 12h.01M12 15c.864-1.148 2.136-1.148 3 0" />
                      </svg>
                    </div>
                    <!-- 消息气泡 -->
                    <div
                      class="relative max-w-70  px-4 py-2 text-sm"
                      :class="{
                        'bg-grey-500 text-white rounded-3xl': message.sender === 'user', // 用户消息带圆角气泡
                        'bg-gray-800 text-white': message.sender === 'bot' // 机器人消息无圆角
                      }"
                    >
                      {{ message.text }}
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 输入框 -->
    <div class="w-full">
      <div class="m-auto text-base px-3 max-w-3xl mx-auto">
        <ChatInputs :message="message" :placeholderText="placeholderText" @sendMessage="submitMessage" />
      </div>
    </div>
  </div>
</template>


<script>
import ChatInputs from "@/components/ChatInputs.vue";
import axios from "axios";

export default {
  data() {
    return {
      messages: [], // 空的消息数组
      placeholderText: '给“DSLbot”发送消息',
    };
  },
  components: {
    ChatInputs,
  },
  methods: {
    async submitMessage(userMessage) {
      // 用户消息加入 messages 数组
      this.messages.push({
        sender: 'user',
        text: userMessage,
      });

      // 滚动到底部
      this.scrollToBottom();

      // 调用后端接口获取回复
      try {
        axios.defaults.withCredentials = true; // 自动携带 Cookies
        const response = await axios.post('/api/home/postquestion', { messages: userMessage });
        // 将后端返回的消息加入 messages 数组
        this.messages.push({
          sender: 'bot',
          text: response.data.response, // 假设后端返回的回复字段是 reply
        });

        // 滚动到底部
        this.scrollToBottom();
      } catch (error) {
        console.error("消息发送失败：", error);
        this.messages.push({
          sender: 'bot',
          text: '抱歉，DSLbot 无法回复您的消息。',
        });
        this.scrollToBottom();
      }
    },
    scrollToBottom() {
      // 自动滚动到底部
      this.$nextTick(() => {
        const container = this.$refs.messageContainer;
        if (container) {
          container.scrollTop = container.scrollHeight;
        }
      });
    },
    async initializeSession() {
      try {
        // 调用后端获取初始消息（如果需要）
        const response = await axios.get('/api/home');

        // 假设后端返回的初始化消息在 response.data.message
        if (response.data.message) {
          this.messages.push({
            sender: 'bot',
            text: response.data.message,
          });
        }
      } catch (error) {
        console.error('初始化会话失败：', error);
      }
    },
  },
  mounted() {
    // 初始化会话逻辑
    this.initializeSession();
  },
};
</script>
<style scoped>
/* 整体背景颜色 */
.bg-gray-800 {
  background-color: rgb(45, 45, 45); /* 深灰色背景 */
  color: rgb(120 120 120);
}

/* 消息背景样式 */
.bg-grey-500 {
  background-color:#5c5c5c;
  color:rgb(219 219 219);
}

/* 分割线样式 */
.border-gray-700 {
  border-color: rgb(67, 67, 67); /* 分割线颜色 */
}

/* 消息气泡圆角 */
.rounded-3xl {
  border-radius: 1.5rem;
}

/* 头像样式 */
svg {
  width: 32px;
  height: 32px;
  margin: 8px;
}

/* 排列方向 */
.flex-row-reverse {
  flex-direction: row-reverse; /* 用户消息从右边显示 */
}

.flex-row {
  flex-direction: row; /* 机器人消息从左边显示 */
}

/* 消息间距 */
.message {
  margin-bottom: 10px;
}

/* 小文字 */
.text-sm {
  font-size: 0.875rem;
  line-height: 1.25rem;
}

/* 限制消息气泡宽度 */
.max-w-70 {
  max-width: 70%;
}
</style>
