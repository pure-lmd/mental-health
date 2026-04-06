// 用户端路由配置
import FrontLayout from '@/layouts/FrontLayout.vue'

export default [
  {
    path: '/front',
    component: FrontLayout,
    redirect: '/front/home',
    children: [
      {
        path: 'home',
        name: 'FrontHome',
        component: () => import('@/views/front/home/index.vue'),
        meta: { title: '首页' }
      },
      {
        path: 'knowledge',
        name: 'FrontKnowledge',
        component: () => import('@/views/front/knowledge/index.vue'),
        meta: { title: '心理科普' }
      },
      {
        path: 'knowledge/:id',
        name: 'KnowledgeDetail',
        component: () => import('@/views/front/knowledge/detail.vue'),
        meta: { title: '科普详情' }
      },
      {
        path: 'test',
        name: 'FrontTest',
        component: () => import('@/views/front/test/index.vue'),
        meta: { title: '心理测试' }
      },
      {
        path: 'test/answer/:id',
        name: 'TestAnswer',
        component: () => import('@/views/front/test/answer.vue'),
        meta: { title: '心理测试答题' }
      },
      {
        path: 'test/result/:id',
        name: 'TestResult',
        component: () => import('@/views/front/test/result.vue'),
        meta: { title: '测试结果' }
      },
      {
        path: 'news',
        name: 'FrontNews',
        component: () => import('@/views/front/news/index.vue'),
        meta: { title: '文化资讯' }
      },
      {
        path: 'news/:id',
        name: 'NewsDetail',
        component: () => import('@/views/front/news/detail.vue'),
        meta: { title: '资讯详情' }
      },
      {
        path: 'publicity',
        name: 'FrontPublicity',
        component: () => import('@/views/front/publicity/index.vue'),
        meta: { title: '心理宣传' }
      },
      {
        path: 'publicity/:id',
        name: 'PublicityDetail',
        component: () => import('@/views/front/publicity/detail.vue'),
        meta: { title: '宣传详情' }
      },
      {
        path: 'activity',
        name: 'FrontActivity',
        component: () => import('@/views/front/activity/index.vue'),
        meta: { title: '心理活动' }
      },
      {
        path: 'activity/:id',
        name: 'ActivityDetail',
        component: () => import('@/views/front/activity/detail.vue'),
        meta: { title: '活动详情' }
      },
      {
        path: 'notice',
        name: 'FrontNotice',
        component: () => import('@/views/front/notice/index.vue'),
        meta: { title: '通知公告' }
      },
      {
        path: 'notice/:id',
        name: 'NoticeDetail',
        component: () => import('@/views/front/notice/detail.vue'),
        meta: { title: '公告详情' }
      },
      {
        path: 'ai-chat',
        name: 'AiChat',
        component: () => import('@/views/front/aiChat/index.vue'),
        meta: { title: 'AI助手' }
      },
      {
        path: 'feedback',
        name: 'FrontFeedback',
        component: () => import('@/views/front/feedback/index.vue'),
        meta: { title: '意见反馈' }
      },
      {
        path: 'user/profile',
        name: 'UserProfile',
        component: () => import('@/views/front/user/profile.vue'),
        meta: { title: '个人信息' }
      },
      {
        path: 'user/collect',
        name: 'UserCollect',
        component: () => import('@/views/front/user/collect.vue'),
        meta: { title: '我的收藏' }
      },
      {
        path: 'user/registration',
        name: 'UserRegistration',
        component: () => import('@/views/front/user/registration.vue'),
        meta: { title: '我的预约' }
      },
      {
        path: 'user/feedback',
        name: 'UserFeedback',
        component: () => import('@/views/front/user/feedback.vue'),
        meta: { title: '我的反馈' }
      },
      {
        path: 'user/test-record',
        name: 'UserTestRecord',
        component: () => import('@/views/front/user/testRecord.vue'),
        meta: { title: '测试记录' }
      },
      {
        path: 'user/growth',
        name: 'UserGrowth',
        component: () => import('@/views/front/user/growth.vue'),
        meta: { title: '成长轨迹' }
      }
    ]
  }
]
