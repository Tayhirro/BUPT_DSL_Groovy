<template>
  <div>

    <!-- 主内容区域 -->
    <div class="w-screen h-screen overflow-auto fixed top-0 left-0 z-[103] transition-opacity duration-[800ms] opacity-0 pointer-events-none"></div>
    <div class="main">
      <span class="sr-only" tabindex="0" aria-live="polite" aria-atomic="true">OpenAI o1 Hub | OpenAI</span>
      <div>
                                    <form @submit.prevent="register" class="grid gap-4">
                                          <div class="relative">
                                            <label for="username" class="sr-only">Username</label>
                                           <svg class="icon absolute left-3 top-1/2 transform -translate-y-1/2 w-5 h-5"
                                                 xmlns="http://www.w3.org/2000/svg"
                                                 viewBox="0 0 1024 1024"
                                                 width="20"
                                                 height="20">
                                            <path d="M316.3136 347.8784l43.4304 0 0 328.2304-43.4304 0 0-328.2304Z" p-id="4793" fill="#707070"></path><path d="M655.9744 376.0896c-13.2864-11.3408-29.3376-19.1744-48.1408-23.5008-13.4272-3.136-32.9088-4.7104-58.432-4.7104l-113.0752 0 0 328.2304 118.4384 0c19.8528 0 37.4272-1.856 52.736-5.5936 15.296-3.7376 28.352-9.216 39.1808-16.4608 10.816-7.2448 20.7872-17.1776 29.888-29.7856 9.1008-12.608 16.5632-28.544 22.4-47.7952 5.824-19.264 8.7296-41.344 8.7296-66.2784 0-29.248-4.288-55.2704-12.864-78.0288C686.2336 409.4208 673.28 390.72 655.9744 376.0896zM654.2848 572.7872c-5.7472 17.3824-13.76 31.232-24.064 41.536-7.3216 7.3088-17.1264 12.992-29.44 17.024-12.3136 4.032-29.2992 6.0416-50.944 6.0416l-70.08 0L479.7568 386.6112l68.9664 0c25.8304 0 44.544 2.24 56.2048 6.72 16.1152 6.272 29.8112 18.624 41.088 37.056 11.264 18.4448 16.9088 44.8256 16.9088 79.1424C662.912 534.3104 660.032 555.4048 654.2848 572.7872z" p-id="4794" fill="#707070"></path><path d="M512 51.2C257.5104 51.2 51.2 257.5104 51.2 512c0 254.4896 206.3104 460.8 460.8 460.8s460.8-206.3104 460.8-460.8C972.8 257.5104 766.4896 51.2 512 51.2zM512 951.8592C269.4656 951.8592 72.1408 754.5344 72.1408 512 72.1408 269.4656 269.4656 72.1408 512 72.1408S951.8592 269.4656 951.8592 512C951.8592 754.5344 754.5344 951.8592 512 951.8592z" p-id="4795" fill="#707070"></path>
                                           </svg>
                                            <input
                                              v-model="username"
                                              id="username"
                                              type="text"
                                              placeholder="Username"
                                              class="pl-10 border border-gray-300 rounded w-full py-2 px-3"
                                            />
                                          </div>
                                          <div class="relative">
                                            <label for="password" class="sr-only">Password</label>
                                            <!-- 插入 SVG 图标 -->
                                            <svg class="icon absolute left-3 top-1/2 transform -translate-y-1/2 w-5 h-5"
                                                 xmlns="http://www.w3.org/2000/svg"
                                                 viewBox="0 0 1024 1024"
                                                 width="20"
                                                 height="20">
                                          <path d="M313.223529 415.623529h-120.470588V210.823529C192.752941 96.376471 289.129412 6.023529 409.6 6.023529h198.776471c120.470588 0 216.847059 90.352941 216.847058 204.8v204.8h-114.447058V210.823529c0-54.211765-42.164706-96.376471-96.376471-96.37647H409.6c-54.211765 0-96.376471 42.164706-96.376471 96.37647v204.8z" fill="#8a8a8a" p-id="6311"></path><path d="M499.952941 1017.976471c-252.988235 0-457.788235-138.541176-457.788235-481.882353 0-66.258824 60.235294-120.470588 126.494118-120.470589h674.635294c72.282353 0 126.494118 54.211765 126.494117 120.470589 6.023529 367.435294-234.917647 481.882353-469.835294 481.882353zM512 560.188235c-48.188235 0-90.352941 36.141176-90.352941 90.352941 0 30.117647 18.070588 60.235294 42.164706 72.282353v96.376471h96.37647V722.823529c24.094118-18.070588 42.164706-42.164706 42.164706-72.282353 0-48.188235-42.164706-90.352941-90.352941-90.352941z" fill="#8a8a8a" p-id="6312">
                                            </path>
                                            </svg>
                                            <input
                                              v-model="password"
                                              id="password"
                                              type="password"
                                              placeholder="Password"
                                              class="pl-10 border border-gray-300 rounded w-full py-2 px-3"
                                            />
                                          </div>
                                          <button type="submit" class="custom-button">Register</button>
                                        </form>
    </div>
    <p v-if="message" :style="{ color: messageColor }">{{ message }}</p>
  </div>
    </div>

</template>







<script>
import axios from 'axios';

export default {
  name: "Signin",
  data() {
    return {
      username: '',
      password: '',
      message: '',
      messageColor: 'black',
      scrolledClass: '', // 用于存储滚动后的类
    };
  },
  methods: {
    async register() {
      try{
          const username = this.username;
          const password = this.password;
          const response = await axios.post('/api/register', { username, password } ,{ withCredentials: true });
          this.message = response.data.message;
          this.messageColor = 'white';

          console.log(response);
          // 延迟 2 秒重定 向到登录页面
          setTimeout(() => {
            this.$router.push('/Login');
          }, 2000);


          } catch (error) {
          if (error.response) {
            this.message = error.response.data.message;
            this.messageColor = 'red';

          } else {
            this.message = 'An error occurred. Please try again later.';
            this.messageColor = 'red';
          }
      }
    },

  },
}
</script>

<style scoped>
/* 您可以根据需求修改或添加样式 */
.main{
  margin-top: 20px;
  padding: 20px;
}

.navbar {
  background-color: #f8f8f8;
  padding: 1rem;
}

.logo {
  width: 50px;
  height: 50px;
}

.nav-links {
  display: flex;
  gap: 1rem;
}

.nav-item {
  color: #333;
  text-decoration: none;
}

.nav-item:hover {
  color: #555;
}
</style>
