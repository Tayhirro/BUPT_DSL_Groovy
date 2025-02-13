<template>
    <div class="video-container " >
    <!-- 视频背景 -->
    <video autoplay muted loop class="background-video">
      <source src="../assets/65560-515098344.mp4" type="video/mp4">
      您的浏览器不支持 HTML5 视频标签。
    </video>

    <!-- 主内容区域 -->
     <span class="sr-only" tabindex="0" aria-live="polite" aria-atomic="true">OpenAI o1 Hub | OpenAI</span>
      <div class="mt-2xl">
        <div class="relative mb-l">
          <div class="max-w-container grid grid-cols-12 mt-2xl">
                <div class="col-span-12 m:col-span-12">
                  <div class="flex flex-col relative items-center text-center">
                    <h1 class="text-h1 max-w-[62.5rem] text-balance text-white">Introducing Robot-DSL</h1>
                  </div>
                </div>
                <div class="col-span-12 m:col-span-12">
                  <div class="flex flex-col relative items-center text-center">
                    <p class="col-span-12 m:col-span-8 m:col-start-3 text-p1-desktop text-balance m:col-span-8 m:col-start-3 text-white">
                      Robot-DSL is a domain-specific language for guest-service.You can use it to create a chatbot that can help guests with their questions and requests.
                    </p>
                  </div>
                </div>

          </div>
        </div>
        <div class="max-w-container grid grid-cols-12 gap-xs m:mt-xl -mt-m">

            <div class="m:col-span-8 m:col-start-3 col-span-12 flex flex-col relative items-center text-left gap-xs py-xs">
          <div class="flex justify-end w-full z-10"><button type="button" class="text-copy-primary flex gap-3xs items-center border border-transparent bg-transparent hover:enabled:bg-btn-secondary-base--hover data-[state=open]:bg-dropdown-base data-[state=open]:hover:bg-dropdown-base data-[state=open]:border-gray-40 transition duration-200 ease-curve-a bg-btn-primary-base text-btn-primary-label rounded-full px-xs text-nowrap min-h-button flex items-center justify-center gap-[0.3em] text-cta enabled:hover:bg-btn-primary-base--hover disabled:bg-btn-primary-base--disabled disabled:text-btn-primary-label--disabled" aria-haspopup="dialog" aria-expanded="false" aria-controls="radix-:r0:" data-state="closed">Sort<svg xmlns="http://www.w3.org/2000/svg" width="10" viewBox="0 0 10 6" fill="none"><path fill-rule="evenodd" clip-rule="evenodd" d="M0.292893 0.292893C0.683418 -0.0976311 1.31658 -0.0976311 1.70711 0.292893L5 3.58579L8.29289 0.292894C8.68342 -0.0976307 9.31658 -0.0976307 9.70711 0.292894C10.0976 0.683418 10.0976 1.31658 9.70711 1.70711L5.70711 5.70711C5.31658 6.09763 4.68342 6.09763 4.29289 5.70711L0.292893 1.70711C-0.0976311 1.31658 -0.0976311 0.683417 0.292893 0.292893Z" fill="currentColor"></path></svg></button></div>
              <div class="red-sofa-extended-cut relative flex flex-col group w-full backface-hidden transform-gpu will-change-transform transition duration-400 ease-curve-b max-m:scale-1">
                  <div class="w-full p-xs rounded-xl bg-surface-card relative overflow-hidden">
                 <div class="z-10 absolute backface-hidden pointer-events-none w-full h-[200%] -top-[140%] scale-[1.5] bottom-0 left-0 right-0 will-change-transform transition-transform duration-[1000ms] ease-curve-b bg-gradient-to-b from-surface-card--transparent from-10% to-60% translate-y-[200%]"></div>

                  <div class="w-full pt-s">
                    <div class="flex flex-col w-full mx-auto transition-opacity ease-curve-c duration-normal max-w-media relative">
                        <div class="relative w-full">
                              <div tabindex="0" class=" grid gap-4 relative flex  flex-row w-full overflow-hidden h-full group outline-none aspect-16/9 rounded-s ">
                                  <form @submit.prevent="submitForm" class="grid gap-4">
                        <!-- 用户名输入框 -->
                        <div class="relative">
                          <label for="username" class="sr-only">Username</label>
                          <input
                            v-model="username"
                            id="username"
                            type="text"
                            placeholder="Username"
                            class="pl-10 border border-gray-300 rounded w-full py-2 px-3"
                          />

                        </div>
                        <!-- 密码输入框 -->
                        <div class="relative" v-if="isForgetPassword !== 1">
                          <label for="password" class="sr-only">Password</label>
                          <input
                            v-model="password"
                            id="password"
                            type="password"
                            placeholder="Password"
                            class="pl-10 border border-gray-300 rounded w-full py-2 px-3"
                          />
                        </div>
                        <!-- 新密码输入框 -->
                        <div class="relative" v-if="isForgetPassword === 1">
                          <label for="newPassword" class="sr-only">New Password</label>
                          <input
                            v-model="newPassword"
                            id="newPassword"
                            type="password"
                            placeholder="New Password"
                            class="pl-10 border border-gray-300 rounded w-full py-2 px-3"
                          />
                        </div>
                        <!-- 确认新密码输入框 -->
                        <div class="relative" v-if="isForgetPassword === 1">
                          <label for="confirmPassword" class="sr-only">Confirm Password</label>
                          <input
                            v-model="confirmPassword"
                            id="confirmPassword"
                            type="password"
                            placeholder="Confirm Password"
                            class="pl-10 border border-gray-300 rounded w-full py-2 px-3"
                          />
                        </div>
                        <!-- 按钮 -->
                        <div class="grid grid-cols-12">
                          <button type="submit" class="custom-button" style="grid-column: 1;" >
                            {{ isForgetPassword === 1 ? 'Reset Password' : 'Login' }}
                          </button>
                          <button
                            type="button"
                            @click="toggleForgetPassword"
                            class="custom-button"
                            style="grid-column: 12;"
                          >
                            {{ isForgetPassword === 1 ? 'Cancel' : 'Forget?' }}
                          </button>
                        </div>
                      </form>
                                <div>


                                </div>
                              </div>
                        </div>
                    </div>
                  </div>

               </div>
                <p v-if="message" :style="{ color: messageColor }">{{ message }}</p>
              </div>
              <div class="relative flex flex-col group w-full backface-hidden transform-gpu will-change-transform transition duration-400 ease-curve-b max-m:scale-[0.95]" >
                <div class="w-full p-xs rounded-xl bg-surface-card relative overflow-hidden">
                  <div class="red-sofa-extended-cut relative flex flex-col group w-full backface-hidden transform-gpu will-change-transform transition duration-400 ease-curve-b max-m:scale-1">
                    <div class="w-full flex flex-row justify-between">
                          <div class="w-full flex flex-row items-center gap-5xs text-caption">
                            <div>September 12</div>
                            <svg width="0.15625rem" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                              <path d="M22 12C22 17.5228 17.5228 22 12 22C6.47715 22 2 17.5228 2 12C2 6.47715 6.47715 2 12 2C17.5228 2 22 6.47715 22 12Z" fill="currentColor">

                              </path>
                            </svg>
                            <div>Product</div>
                          </div>
                          <div class="relative overflow-visible transition-[color] duration-normal ease-curve-d text-copy-secondary group-hover:text-copy-primary">
                            <button type="button" class="cursor-pointer w-[44px] h-[44px] absolute top-0 right-0 z-100 -translate-y-1/4 flex justify-end items-center group/svg" aria-label="Copy link to clipboard.">
                              <div class="flex items-center justify-center focus-visible:outline-offset-0 rounded-s hover:bg-btn-secondary-base--hover group-hover/svg:bg-btn-secondary-base--hover w-[32px] h-[24px] rounded-4xs p-6xs">
                                <svg width="24" height="17" viewBox="0 0 16 17" fill="none" xmlns="http://www.w3.org/2000/svg">
                                  <g clip-path="url(#clip0_1356_1880)">
                                    <path d="M10.0013 5.24662H12.0013C12.439 5.24662 12.8725 5.33284 13.2769 5.50036C13.6813 5.66787 14.0488 5.9134 14.3583 6.22293C14.6679 6.53246 14.9134 6.89992 15.0809 7.30434C15.2484 7.70876 15.3346 8.14221 15.3346 8.57995C15.3346 9.01769 15.2484 9.45115 15.0809 9.85556C14.9134 10.26 14.6679 10.6274 14.3583 10.937C14.0488 11.2465 13.6813 11.492 13.2769 11.6596C12.8725 11.8271 12.439 11.9133 12.0013 11.9133H10.0013M6.0013 11.9133H4.0013C3.56356 11.9133 3.13011 11.8271 2.72569 11.6596C2.32127 11.492 1.95381 11.2465 1.64428 10.937C1.01916 10.3119 0.667969 9.46401 0.667969 8.57995C0.667969 7.6959 1.01916 6.84805 1.64428 6.22293C2.2694 5.59781 3.11725 5.24662 4.0013 5.24662H6.0013" stroke="currentColor" stroke-width="1.66667" stroke-linecap="round" stroke-linejoin="round"></path>
                                    <path d="M5.33203 8.57996H10.6654" stroke="currentColor" stroke-width="1.66667" stroke-linecap="round" stroke-linejoin="round"></path>
                                  </g>
                                </svg>
                              </div>
                            </button>
                            <div class="transition p-4xs w-max absolute -bottom-[6px] right-0 z-[100] translate-y-full text-right bg-surface-primary--invert text-copy-primary--invert rounded-s opacity-0" style="font-size: 0.84375rem; font-weight: 400; line-height: 144%;">Link copied to clipboard!<div class="pointer-events-none absolute -top-[3px] right-[13px] w-[6px] h-[6px] bg-surface-primary--invert transform rotate-45"></div>
                            </div>
                          </div>
                        </div>
                  <div class="w-full pt-s">
                    <div class="flex flex-col w-full mx-auto transition-opacity ease-curve-c duration-normal max-w-media relative">
                      <div class="relative w-full">
                        <div tabindex="0" class="relative flex w-full overflow-hidden h-full group outline-none aspect-16/9 rounded-s">
                          <div class="h-full w-full">
                            <iframe src="https://player.vimeo.com/video/1008886029?h=d0d4fbf765&amp;badge=0&amp;autopause=0&amp;player_id=0&amp;app_id=58479" width="100%" height="100%" allow="autoplay; fullscreen; picture-in-picture; clipboard-write" class="aspect-16/9 min-w-full min-h-full absolute left-1/2 top-1/2 -translate-x-1/2 -translate-y-1/2" data-ready="true" style="height: 900%;">
                            </iframe>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="text-left text-caption pt-s"><div class=""><p><span>DSL (Domain-Specific Language) is a programming language designed specifically for a particular domain or application. Unlike general-purpose programming languages (e.g., Python, Java), DSLs are typically more concise and focused on solving problems in a specific area.<br><br>Host: Tayhirro</span></p></div></div>
                </div>
                </div>
              </div>

          </div>
        </div>
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
      scaleClass: 'scale-reduced', // 初始状态的类名，使用更适合 Vue 绑定的命名
      screenHeight:0,
      previousScrollTop: 0, // 初始化上一次滚动位置
      isForgetPassword: 0,
      newPassword: '',
      confirmPassword:''
    };
  },
  methods: {
  submitForm() {
    if (this.isForgetPassword === 1) {
      this.resetPassword();
    } else {
      this.login();
    }
  },

  async check(username) {
  try {
      const response = await axios.get('http://localhost:5000/api/query', {
        params: {
          username: username  // 这里将用户名作为查询参数传递
        }
      });

      console.log(response.data);  // 处理响应数据
    } catch (error) {
      console.error('Error fetching user info:', error);
    }
  },

    async resetPassword() {
      // 重置密码逻辑
      if (this.newPassword !== this.confirmPassword) {
    this.message = 'Passwords do not match!';
    this.messageColor = 'red';
} else {
    const username = this.username;
    const newPassword = this.newPassword;
    try {
        const response = await axios.post('api/update-password', { username, newPassword });
        // 处理成功响应
        if (response.data.status === 'success') {
            this.message = response.data.message;
            this.messageColor = 'green';  // 成功时显示绿色
            this.isForgetPassword =0;
        } else {
            // 处理失败响应（例如密码更新失败）
            this.message = response.data.message || 'Something went wrong';
            this.messageColor = 'red';
        }
    } catch (error) {
        // 根据后端返回的错误状态码进行处理
        if (error.response) {
            const status = error.response.status;
            switch (status) {
                case 400:
                    this.message = error.response.data.message || 'Bad request. Please check your input.';
                    break;
                case 404:
                    this.message = error.response.data.message || 'User not found.';
                    break;
                case 500:
                    this.message = error.response.data.message || 'Server error. Please try again later.';
                    break;
                default:
                    this.message = 'An unknown error occurred.';
            }
            this.messageColor = 'red';  // 错误时显示红色
        } else {
            // 如果没有收到响应，显示网络或其他错误
            this.message = 'Network error. Please try again later.';
            this.messageColor = 'red';
        }
            }
        }


    },
    toggleForgetPassword() {
      // 切换忘记密码状态
      this.isForgetPassword = this.isForgetPassword === 1 ? 0 : 1;
    },

   async login() {
     try {
       const username = this.username;
       const password = this.password;
       const response = await axios.post('/api/login', {username, password});
       this.message = response.data.message;
       this.messageColor = 'white';


       localStorage.setItem('username', username);
       localStorage.setItem('auth', 'true');
       setTimeout(()=>{
         this.$router.push('/Home');
       },2000)
     }catch (error){
       if(error.response){
            this.message = error.response.data.message;
            this.messageColor = 'red';
       }else{
            this.message= 'An error occurred. Please try again later.';
            this.messageColor = 'red';
       }
     }
   },

     handleScroll() {
  const currentScrollTop = document.documentElement.scrollTop || window.scrollY; // 当前滚动位置
  const screenHeight = window.innerHeight; // 视口高度

  // 获取目标元素（改为通过类选择器选择）
  const element = document.querySelector(".red-sofa-extended-cut");
  const gradientElement = document.querySelector(".z-10");

  if (element) {
    // 获取目标元素的边界信息
    const rect = element.getBoundingClientRect();

    // 判断是否在视口下方四分之一时开始放大
    const isVisible = rect.top < screenHeight * 0.75 && rect.bottom > 0;

    if (isVisible) {
      const scrollRatio = Math.min((screenHeight * 0.75 - rect.top) / (screenHeight * 0.75), 1); // 计算滚动比例
      const minScale = 1; // 最小缩放
      const maxScale = 1.03; // 最大缩放
      const scaleValue = Math.min(maxScale, minScale + scrollRatio * (maxScale - minScale)); // 动态缩放值
      element.style.transform = `scale(${scaleValue})`;
    } else {
      // 如果未达到激活条件，恢复默认缩放
      element.style.transform = "scale(1)";
    }
  }

  if (gradientElement) {
    const translateY = Math.max(0, 200 - 200 * (currentScrollTop / screenHeight)); // 动态计算位移
    const opacity = Math.max(0, 1 - (currentScrollTop / screenHeight)); // 动态透明度
    gradientElement.style.transform = `translateY(${translateY}%)`;
    gradientElement.style.opacity = opacity;
  }
},

  },
  mounted() {
    this.screenHeight = window.innerHeight; // 获取视口高度
    window.addEventListener("scroll", this.handleScroll);
    window.addEventListener("click", this.handleClick);  // 监听点击事件
  },
  beforeDestroy() {
    // 组件销毁前移除滚动事件监听器，防止内存泄漏
    window.removeEventListener('scroll', this.handleScroll);
  },


}
</script>



<style scoped>

@media (min-width: 48rem) {
        .prose .text-h1, .prose h1, .text-h1 {
        font-size: 4.5rem;
        line-height: 110%;
        letter-spacing: -.02em;
        font-weight: 400;
    }
      .m\:mt-xl {
        margin-top: 5.05625rem;
    }
    .max-w-container {
        max-width: 68rem;
    }
   .m\:col-span-8 {
        grid-column: span 8 / span 8;
    }
    .m\:col-start-3 {
        grid-column-start: 3;
    }
}


.video-container {
  position: relative;
  width: 100%;
  height: auto; /* 高度根据内容扩展 */
  overflow: hidden;
}


.background-video {
  position: fixed; /* 改为 absolute */
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  object-fit: cover;
  z-index: -1;
}



/* 您可以根据需求修改或添加样式 */
.main{
  margin-top: 20px;
  padding: 20px;
}



.ease-curve-b {
    animation-timing-function: cubic-bezier(.25,0,.75,1);
}

.custom-button {
  text-align: left; /* 确保文本靠左对齐 */
}


.prose .text-h1, .prose h1, .text-h1 {
    font-size: 3.003125rem;
    line-height: 110%;
    letter-spacing: -.0167em;
    font-weight: 400;
}
.text-p1-desktop, .text-p1-mobile {
    font-size: 1.125rem;
    line-height: 150%;
    font-weight: 400;
}







.max-w-media {
    max-width: 108rem;
}


.z-10 {
  transition: transform 1s ease, opacity 1s ease; /* 平滑过渡 */
}

.red-sofa-extended-cut {
  transition: transform 0.4s ease-in-out; /* 平滑缩放动画 */
  opacity: 0.9; /* 设置透明度，0.5为半透明 */
}

</style>
