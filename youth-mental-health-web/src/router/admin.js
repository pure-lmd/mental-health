// 管理端路由配置
import AdminLayout from '@/layouts/AdminLayout.vue'

export default [
  {
    path: '/admin',
    component: AdminLayout,
    redirect: '/admin/home',
    children: [
      {
        path: 'home',
        name: 'AdminHome',
        component: () => import('@/views/admin/home/index.vue'),
        meta: { title: '首页', icon: 'home' }
      },
      {
        path: 'statistics',
        name: 'Statistics',
        component: () => import('@/views/admin/statistics/index.vue'),
        meta: { title: '数据统计', icon: 'statistics' }
      },
      {
        path: 'banner',
        name: 'BannerManage',
        component: () => import('@/views/admin/banner/index.vue'),
        meta: { title: '轮播图管理', icon: 'banner' }
      },
      {
        path: 'category',
        name: 'CategoryManage',
        component: () => import('@/views/admin/category/index.vue'),
        meta: { title: '心理分类管理', icon: 'category' }
      },
      {
        path: 'knowledge',
        name: 'KnowledgeManage',
        component: () => import('@/views/admin/knowledge/index.vue'),
        meta: { title: '心理科普管理', icon: 'knowledge' }
      },
      {
        path: 'publicity',
        name: 'PublicityManage',
        component: () => import('@/views/admin/publicity/index.vue'),
        meta: { title: '心理宣传管理', icon: 'publicity' }
      },
      {
        path: 'news',
        name: 'NewsManage',
        component: () => import('@/views/admin/news/index.vue'),
        meta: { title: '资讯管理', icon: 'news' }
      },
      {
        path: 'notice',
        name: 'NoticeManage',
        component: () => import('@/views/admin/notice/index.vue'),
        meta: { title: '通知公告管理', icon: 'notice' }
      },
      {
        path: 'test',
        name: 'TestManage',
        component: () => import('@/views/admin/test/index.vue'),
        meta: { title: '心理测试管理', icon: 'test' }
      },
      {
        path: 'test/question/:id',
        name: 'QuestionManage',
        component: () => import('@/views/admin/test/question.vue'),
        meta: { title: '题目管理', icon: 'test', hidden: true }
      },
      {
        path: 'activity',
        name: 'ActivityManage',
        component: () => import('@/views/admin/activity/index.vue'),
        meta: { title: '活动管理', icon: 'activity' }
      },
      {
        path: 'registration',
        name: 'RegistrationManage',
        component: () => import('@/views/admin/registration/index.vue'),
        meta: { title: '报名管理', icon: 'registration' }
      },
      {
        path: 'collect',
        name: 'CollectManage',
        component: () => import('@/views/admin/collect/index.vue'),
        meta: { title: '收藏管理', icon: 'collect' }
      },
      {
        path: 'comment',
        name: 'CommentManage',
        component: () => import('@/views/admin/comment/index.vue'),
        meta: { title: '评论管理', icon: 'comment' }
      },
      {
        path: 'feedback',
        name: 'FeedbackManage',
        component: () => import('@/views/admin/feedback/index.vue'),
        meta: { title: '反馈管理', icon: 'feedback' }
      },
      {
        path: 'admin',
        name: 'AdminManage',
        component: () => import('@/views/admin/admin/index.vue'),
        meta: { title: '管理员管理', icon: 'admin' }
      },
      {
        path: 'user',
        name: 'UserManage',
        component: () => import('@/views/admin/user/index.vue'),
        meta: { title: '用户管理', icon: 'user' }
      },
      {
        path: 'profile',
        name: 'AdminProfile',
        component: () => import('@/views/admin/profile/index.vue'),
        meta: { title: '个人信息', icon: 'user', hidden: true }
      }
    ]
  }
]
