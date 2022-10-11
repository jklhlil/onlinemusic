import Vue from 'vue'
import Router from 'vue-router'


Vue.use(Router)

export default new Router({
	// mode: 'history',
	routes: [
		{ path: '/', name: '登录', redirect: '/login' },

		{ path: '/m', name: 'm', component: () => import('../components/Music.vue') },

		{ path: '/login', name: '登录', component: () => import('../views/Login.vue') },
		{ path: '/register', name: '注册', component: () => import('../views/Register.vue') },
		{
			path: '/manage/', name: '管理', component: () => import('../views/Manage.vue'),
			children: [
				// together下的路由
				{ path: 'home', name: '主页', component: () => import('../views/together/Home.vue') },
				{ path: 'square', name: '活动', component: () => import('../views/together/Square.vue') },
				{ path: 'my', name: '我的', component: () => import('../views/together/My.vue') },
			]
		},

		// function下的路由
		{ path: '/function/play', name: '播放', component: () => import('../views/function/Play.vue') },
		{ path: '/function/transfer', name: '转换', component: () => import('../views/function/Transfer.vue') },
		{ path: '/function/search', name: '搜索', component: () => import('../views/function/Search.vue') },
		{ path: '/function/modifyuser', name: '修改信息', component: () => import('../views/function/ModifyUser.vue') },

		//content下的路由
		{ path: '/content/activity', name: '活动详情', component: () => import('../views/content/Activity.vue') },
		{ path: '/w', component: () => import('../views/content/Wan.vue') },

		// list下的路由
		{ path: '/list/searchmusic', name: '搜索列表', component: () => import('../views/list/SearchMusic.vue') },
		{ path: '/list/hot/:id', name: '热榜', component: () => import('../views/list/Hot.vue') },
		{ path: '/list/collectsubscribe/:id', name: '收藏订阅', component: () => import('../views/list/CollectSubscribe.vue') },
		{ path: '/list/musiccomments', name: '音乐评论', component: () => import('../views/list/MusicComments.vue') },
		{ path: '/list/mycomments', name: '我的评论', component: () => import('../views/list/MyComments.vue') },


		// 管理员
		{ path: '/admin', name: '管理员登录', redirect: '/admin/login' },
		{ path: '/admin/login', name: '管理员登录', component: () => import('../views/admin/Login.vue') },
		{
			path: '/admin/home', name: '管理员首页', component: () => import('../views/admin/Home.vue'),
			children: [
				{ path: '/admin/user', name: '用户管理', component: () => import('../views/admin/User.vue') },
				{ path: '/admin/music', name: '音乐管理', component: () => import('../views/admin/Music.vue') },
				{ path: '/admin/comment', name: '评论管理', component: () => import('../views/admin/Comment.vue'), },
				{ path: '/admin/setting', name: '系统设置', component: () => import('../views/admin/Setting.vue'), },
			]

		},



	]
})






const originalPush = Router.prototype.push

Router.prototype.push = function push(location) {
	return originalPush.call(this, location).catch(err => err)
}
