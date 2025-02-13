<template class="dark">
       <!-- 顶端导航栏 -->
    <header class="z-[102] text-nav-label top-0 fixed m-auto w-full m:py-0 h-10 transform-gpu transition-header ease-curve-d duration-300 bg-color-300" aria-label="Main navigation">
      <nav aria-label="Main" data-orientation="horizontal" dir="ltr" class="z-50 relative h-full">
        <div class="transform-gpu absolute top-0 left-1/2 w-[200%] -translate-x-[100vw] blur-[200px] z-[-1] ease-out-cubic pointer-events-none -translate-y-[100%] opacity-0 transition-[opacity,transform] duration-300"></div>
        <div class="transition ease-curve-d duration-300 max-w-container flex items-center h-full justify-between relative">
            <a aria-label="Robot" class="transition ease-curve-a duration-250" href="/login">
             <svg class="w-10 h-auto" viewBox="0 0 80 23" fill="blue" xmlns="http://www.w3.org/2000/svg">
               <g transform="translate(0, 0)">
                  <path d="M20.2712 9.50347C20.5186 8.76122 20.6011 7.97773 20.5255 7.20111C20.4499 6.42449 20.1956 5.67537 19.8039 5.00184C19.2197 3.98468 18.3262 3.18057 17.2541 2.69948C16.182 2.21839 14.9861 2.09468 13.8384 2.3421C13.3229 1.75791 12.6837 1.29057 11.969 0.974425C11.2542 0.658279 10.4845 0.493334 9.70098 0.500206C8.52575 0.500206 7.38487 0.864461 6.42956 1.55861C5.48112 2.24588 4.77323 3.2218 4.40898 4.33519C3.64611 4.49326 2.92447 4.8094 2.29218 5.26988C1.65989 5.73035 1.13069 6.31453 0.745815 6.99493C0.154761 8.01209 -0.092653 9.18733 0.0310561 10.3557C0.154765 11.5241 0.642724 12.6237 1.43309 13.4965C1.18567 14.2388 1.1032 15.0223 1.1788 15.7989C1.26127 16.5755 1.50869 17.3246 1.90044 17.9982C2.48462 19.0153 3.37807 19.8194 4.45021 20.3005C5.52236 20.7816 6.71821 20.9053 7.86596 20.6579C8.38141 21.2421 9.02057 21.7094 9.73534 22.0256C10.4501 22.3417 11.2198 22.5067 12.0033 22.4998C13.1786 22.4998 14.3263 22.1355 15.2748 21.4414C16.2232 20.7541 16.9311 19.7782 17.2953 18.6579C18.0582 18.4999 18.7798 18.1837 19.4121 17.7232C20.0444 17.2628 20.5668 16.6786 20.9585 15.9982C21.5496 14.981 21.797 13.8058 21.6733 12.6374C21.5496 11.4691 21.0616 10.3694 20.2712 9.50347ZM12.0515 21.0565C10.9587 21.0565 10.1065 20.7198 9.37109 20.1012C9.40545 20.0806 9.46043 20.0531 9.50167 20.0256L13.8796 17.4965C13.9896 17.4346 14.0789 17.3453 14.1408 17.2353C14.2026 17.1253 14.237 17.0016 14.237 16.8779V10.7062L16.0857 11.7783C16.0857 11.7783 16.1064 11.7921 16.1132 11.799C16.1132 11.8058 16.1201 11.8196 16.127 11.8265V16.9398C16.127 19.2559 14.1957 21.0634 12.0515 21.0634V21.0565ZM3.15127 17.2765C2.67018 16.4449 2.49149 15.4621 2.66331 14.5137C2.69767 14.5343 2.75266 14.5687 2.79389 14.5893L7.17182 17.1184C7.28178 17.1803 7.40549 17.2147 7.5292 17.2147C7.65291 17.2147 7.77661 17.1803 7.88657 17.1184L13.2336 14.0326V16.17C13.2336 16.17 13.2336 16.1906 13.2336 16.2044C13.2336 16.2113 13.2198 16.225 13.2129 16.2319L8.78691 18.7885C7.83847 19.3315 6.71822 19.4827 5.65982 19.2009C4.60142 18.9191 3.70109 18.225 3.15815 17.2834L3.15127 17.2765ZM1.99666 7.71657C2.47775 6.87809 3.24062 6.23893 4.14094 5.90904V11.1186C4.14094 11.2423 4.17531 11.366 4.23716 11.4759C4.29901 11.5859 4.38836 11.6753 4.49833 11.7371L9.8453 14.823L7.99654 15.8951C7.99654 15.8951 7.97593 15.902 7.96905 15.9089C7.95531 15.9089 7.94843 15.9089 7.93469 15.9089L3.50865 13.3522C2.56022 12.8024 1.87295 11.9021 1.59117 10.8505C1.30939 9.79213 1.45371 8.67187 1.99666 7.72344V7.71657ZM17.206 11.256L11.859 8.17017L13.7078 7.10489C13.7078 7.10489 13.7284 7.09802 13.7353 7.09115H13.7696L18.1957 9.6478C18.8761 10.0395 19.4259 10.6169 19.7901 11.311C20.1544 12.0051 20.3056 12.7886 20.2437 13.5653C20.175 14.3487 19.8864 15.091 19.4121 15.7095C18.9379 16.335 18.2988 16.8092 17.5634 17.0772V11.8677C17.5634 11.744 17.529 11.6203 17.4672 11.5103C17.4053 11.4003 17.316 11.311 17.206 11.2491V11.256ZM19.0479 8.47944C19.0479 8.47944 18.9585 8.42446 18.9173 8.40384L14.5394 5.87468C14.4294 5.81282 14.3057 5.77846 14.182 5.77846C14.0583 5.77846 13.9277 5.81282 13.8246 5.87468L8.47763 8.96053V6.82311C8.47763 6.82311 8.47763 6.80249 8.47763 6.78875C8.47763 6.78188 8.49138 6.76813 8.49826 6.76126L12.9243 4.2046C13.6047 3.81286 14.3744 3.62042 15.1579 3.65479C15.9414 3.68915 16.6974 3.94344 17.3366 4.39017C17.9757 4.83689 18.4774 5.46231 18.7798 6.18395C19.0822 6.90558 19.1716 7.70282 19.041 8.47257L19.0479 8.47944ZM7.46735 12.2938L5.61858 11.2217C5.61858 11.2217 5.59796 11.2079 5.59109 11.201C5.59109 11.1942 5.58422 11.1804 5.57735 11.1735V6.06024C5.57735 5.27675 5.80414 4.51388 6.22338 3.84722C6.64261 3.18744 7.24054 2.65824 7.95531 2.32835C8.6632 1.99846 9.45356 1.87475 10.2302 1.97097C11.0068 2.07406 11.7422 2.39021 12.3401 2.89191C12.3057 2.91253 12.2508 2.94002 12.2095 2.96751L7.8316 5.49668C7.72164 5.55853 7.63229 5.64788 7.57043 5.75784C7.50858 5.8678 7.47422 5.99151 7.47422 6.11522V12.2869L7.46735 12.2938ZM8.47076 10.1289L10.8487 8.75435L13.2267 10.1289V12.878L10.8487 14.2525L8.47076 12.878V10.1289Z" fill="currentColor"></path>
               </g>
                 <text x="25" y="19" font-family="Arial, Helvetica, sans-serif" font-size="20" fill="currentColor">
                  Robot
               </text>

             </svg>
            </a>
          <div class="flex items-center justify-center gap-[0.5rem] m:hidden ml-auto">
            <button type="button" aria-label="Open Search" class="transition-opacity duration-300 ease-out">
              <div class="w-6 h-6">
                <!-- 搜索图标省略 -->
              </div>
            </button>
             <button
                type="button"
                class="w-[1.5rem] h-[1.5rem] flex flex-col items-center justify-center gap-[0.1875rem]"
                aria-controls="main-menu"
                aria-label="Open Menu"
                @click="toggleMenu"
                :aria-expanded="isMenuOpen"
              >
              <div class="w-[0.9375rem] h-[0.125rem] bg-white rounded-full"></div>
              <div class="w-[0.9375rem] h-[0.125rem] bg-white rounded-full"></div>
              <div class="w-[0.9375rem] h-[0.125rem] bg-white rounded-full"></div>
              </button>
          </div>
        </div>
      </nav>
    </header>

    <!-- 主内容区域 -->
    <main :class="{ 'blurred': isMenuOpen }"
    class="pt-navHeight outline-none relative transition-[opacity,transform,filter] ease-out-cubic duration-[400ms] h-screen"
    style="transform-origin: 50% 0;"
    >
        <router-view></router-view>
    </main>
    <!-- 使用 Vue 过渡包裹菜单内容 -->
<transition name="fade-content">
  <div v-if="isMenuOpen" class="menu-overlay fixed inset-0 flex items-center justify-center z-50">
    <div class="text-center">
      <h1 class="text-3xl mb-4">菜单内容</h1>
      <div v-if="currentMenu === 'home'">
        <!-- 首页菜单 -->
        <ul>
          <li><button @click="logout" class="px-4 py-2 bg-red-500 hover:bg-red-700 rounded text-white">注销账号</button></li>
        </ul>
      </div>
      <div v-else-if="currentMenu === 'profile'">
        <!-- 个人资料页面菜单 -->
        <ul>
          <li>Profile Option 1</li>
          <li>Profile Option 2</li>
        </ul>
      </div>
      <div v-else>
        <!-- 默认菜单 -->
        <ul>
          <li>Default Option 1</li>
          <li>Default Option 2</li>
        </ul>
      </div>
      <button @click="toggleMenu" class="px-4 py-2 bg-blue-500 hover:bg-blue-700 rounded">关闭菜单</button>
    </div>
  </div>
</transition>
</template>

<script>
import { gsap } from "gsap";
import axios from "axios";
export default {
  name: 'App',
  data() {
    return {
      isMenuOpen: false, // 菜单状态
    };
  },
  methods: {
    toggleMenu() {
      this.isMenuOpen = !this.isMenuOpen; // 切换菜单状态
    },
    startAnimation() {
      const customEase = "linear(0,.01942 1.83%,.07956 4.02%,.47488 13.851%)";
      gsap.to(".box", {
        x: 300,
        duration: 2,
        ease: customEase,
      });
    },
        updateMenuContent() {
      // 根据当前路由名称设置菜单内容
      const routeName = this.$route.name;
      if (routeName === 'home') {
        this.currentMenu = 'home';
      } else if (routeName === 'profile') {
        this.currentMenu = 'profile';
      } else {
        this.currentMenu = 'default';
      }
    },
    async logout() {
      try {
         const username = localStorage.getItem('username'); // 从 localStorage 获取用户名
          if (!username) {
            alert("未登录或没有用户名信息！");
            return;
          }
        const response = await axios.post('/api/deleteaccount',{username}); // 调用后端注销API
        if (response.data.status === "success") {
          alert(response.data.message); // 提示成功信息
          this.$router.push('/login'); // 跳转到登录页面

        } else {
          alert(`注销失败: ${response.data.message}`);
        }

      } catch (error) {
        console.error("注销失败：", error);
        alert("注销失败，请稍后再试！");
      }finally {
         this.isMenuOpen=false
      }
    },
  },
    watch: {
    // 监听路由变化更新菜单内容
    $route: 'updateMenuContent',
  },
  mounted() {
    this.updateMenuContent(); // 初始化菜单内容
  }

};



</script>

<style scoped>

/* 1. 主内容的模糊效果 */
.blurred {
  filter: blur(8px); /* 模糊效果 */
}

/* 2. 菜单内容的渐入渐出动画 */
.fade-content-enter-active,
.fade-content-leave-active {
  transition: opacity 0.8s ease, transform 0.8s ease; /* 同时过渡透明度和缩放 */
}


.fade-content-enter-from {
  opacity: 0;
  transform: scale(0.95); /* 初始状态 */
}

.fade-content-enter-to {
  opacity: 1;
  transform: scale(1); /* 最终状态 */
}

.fade-content-leave-from {
  opacity: 1;
  transform: scale(1); /* 初始状态 */
}

.fade-content-leave-to {
  opacity: 0;
  transform: scale(0.95); /* 离开时状态 */
}

/* 3. 覆盖层不带颜色，只应用模糊 */
.menu-overlay {
  backdrop-filter: blur(1px); /* 背景模糊效果 */
  -webkit-backdrop-filter: blur(15px); /* Safari 支持 */
}





.navbar {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  display: flex;
  align-items: center;
  background-color: #181818;
  padding: 10px 20px;
  color: white;
  box-shadow: 0px 2px 5px rgba(0, 0, 0, 0.1); /* 添加阴影使其更有层次感 */
}

.logo {
  margin-right: 15px;
}

.nav-links {
  display: flex;
  gap: 20px; /* 控制导航项之间的距离 */
}

.nav-item {
  color: white;
  text-decoration: none;
  font-weight: bold;
  transition: color 0.3s ease;
}

.nav-item:hover {
  color: #ffeb3b; /* 鼠标悬浮时改变颜色 */
}


.bg-color-300{
  background-color: rgb(45, 45, 45);
}


.main {
  display: block;
  unicode-bidi: isolate;
}

.ease-out-cubic {
    animation-timing-function: cubic-bezier(.33,1,.68,1);
}

.ease-out-cubic {
    transition-timing-function: cubic-bezier(.33,1,.68,1);
}
.pt-navHeight {
    padding-top: 2.5rem;
}


</style>
